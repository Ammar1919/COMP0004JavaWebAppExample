package uk.ac.ucl.model;

import java.io.IOException;
import java.util.*;
import java.nio.file.*;

import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;

import java.time.LocalDateTime;

import java.util.UUID;
import jakarta.servlet.http.Part;
import java.io.InputStream;


public class Note extends DataHandler{
    private static final String IMAGE_UPLOAD_PATH = "src/main/webapp/resources/uploads/images";

    public Note(String fileName) {
        super(fileName);
        File uploadDir = new File(IMAGE_UPLOAD_PATH);
        if (!uploadDir.exists()) {
            uploadDir.mkdirs();
        }
    }

    public ArrayNode getNotesArray() {
        return getArray("notes");
    }

    public Map<String, String> getNote(ArrayNode notesArray, String noteId) {
        Map<String, String> noteContent = new HashMap<>();
        int i = matchItemById(notesArray, noteId);
        if (i == -1 || i >= notesArray.size()) {
            noteContent.put("name", "Note note found");
            return noteContent;
        }
        ObjectNode note = (ObjectNode) notesArray.get(i);
        noteContent.put("name", note.get("name").asText());
        noteContent.put("text", note.get("text").asText());
        noteContent.put("imageURL", note.get("imageURL").asText());
        noteContent.put("timeAdded", note.get("timeAdded").asText());
        noteContent.put("timeEdited", note.get("timeEdited").asText());

        return noteContent;
    }

    public List<Map<String, String>> getAllNotes(ArrayNode notesArray, String sortId) {
        List<Map<String, String>> allNotes = new ArrayList<>();
        Sort sort = new Sort();
        for (JsonNode note : notesArray){
            String noteId = note.get("name").asText();
            allNotes.add(getNote(notesArray, noteId));
        }
        if (sortId!=null){
            if (Integer.parseInt(sortId) < 2){
                sort.sortNotesByTime(allNotes, sortId);
            }
            else {
                sort.sortNotesByAlphabet(allNotes, sortId);
            }
        }
        return allNotes;
    }

    public String saveImage(Part filePart) throws IOException {
        if (filePart == null || filePart.getSize() == 0) {
            return null;
        }
        String fileName = UUID.randomUUID() + getFileExtension(filePart);
        String filePath = IMAGE_UPLOAD_PATH + fileName;
        try (InputStream input = filePart.getInputStream()) {
            Files.copy(input, Paths.get(filePath), StandardCopyOption.REPLACE_EXISTING);
        }
        return filePath;
    }

    private String getFileExtension(Part part) {
        String contentDisp = part.getHeader("content-disposition");
        String[] items = contentDisp.split(";");
        for (String item : items) {
            if (item.trim().startsWith("filename")) {
                String fileName = item.substring(item.indexOf("=") + 2, item.length() - 1);
                int dotIndex = fileName.lastIndexOf(".");
                if (dotIndex > 0) {
                    return fileName.substring(dotIndex);
                }
            }
        }
        return ".jpg"; 
    }


    public void addNote(ArrayNode notesArray, Map<String, String> fields) {
        try {
            ObjectNode newNote = newNoteObject(fields);
            notesArray.add(newNote);
            updateFile("notes", notesArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteNote(ArrayNode notesArray, String noteId) {
        try {
            int i = matchItemById(notesArray, noteId);
            notesArray.remove(i);
            updateFile("notes", notesArray);
        } catch (IOException e) {
            throw new RuntimeException("Error processing JSON file", e);
        }
    }

    public void editNote(ArrayNode notesArray, String noteId, String editType, String newContent, Part editImage) {
        try {
            int i = matchItemById(notesArray, noteId);
            ObjectNode note = (ObjectNode) notesArray.get(i);
            editNoteHelper(note, editType, newContent, editImage);
            notesArray.set(i, note);
            updateFile("notes", notesArray);
        } catch (IOException e){
            throw new RuntimeException("Error processing JSON file", e);
        }
    }

    public void editNoteHelper(ObjectNode note, String editType, String newContent, Part editImage) throws IOException {
        if (editType.equals("0")){
            note.put("name", newContent);
        }
        else if (editType.equals("1")){
            note.put("text", newContent);
        }
        else {
            String imageURL = null;
            if (editImage != null && editImage.getSize() > 0) {
                imageURL = saveImage(editImage);
            }
            note.put("imageURL", imageURL);
        }
        note.put("timeEdited", String.valueOf(LocalDateTime.now()));
    }

    public ObjectNode newNoteObject(Map<String, String> fields){
        ObjectNode newNote = objectMapper.createObjectNode();
        newNote.put("name", fields.get("name"));
        newNote.put("text", fields.get("text"));
        newNote.put("imageURL", fields.get("imageURL"));
        newNote.put("timeAdded", fields.get("timeAdded"));
        newNote.put("timeEdited", fields.get("timeEdited"));
        return newNote;
    }

    public String searchInNote(ArrayNode notesArray, String noteId, String query) {
        int index = matchItemById(notesArray, noteId);
        if (index == -1){
            return "Note not found";
        }
        ObjectNode note = (ObjectNode) notesArray.get(index);
        String text = note.get("text").asText().toLowerCase();
        if (text.contains(query.toLowerCase())){
            return "Found: " + query;
        }
        return query + " is not in this note";
    }
}
