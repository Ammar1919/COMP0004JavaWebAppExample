package uk.ac.ucl.model;

import java.io.FileNotFoundException;
import java.io.Reader;
import java.io.FileReader;
import java.io.IOException;
import java.util.*;

import com.fasterxml.jackson.core.JsonProcessingException;
import jakarta.servlet.RequestDispatcher;
import org.apache.commons.csv.CSVFormat;
import org.apache.commons.csv.CSVParser;
import org.apache.commons.csv.CSVRecord;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import javax.management.RuntimeErrorException;
import java.io.File;
import java.util.Iterator;

import java.time.LocalDateTime;

public class Note {
    private final ObjectMapper objectMapper;
    private final String fileName;

    public Note(String fileName){
        this.objectMapper = new ObjectMapper();
        this.fileName = fileName;
    }

    public ArrayNode getNotesArray() throws IOException{
        JsonNode rootNode = objectMapper.readTree(new File(fileName));
        ArrayNode notesArray;
        try {
            notesArray = (ArrayNode) rootNode.get("notes");
        }
        catch (NullPointerException e){
            throw new RuntimeException("'notes' field does not exist in " + fileName);
        }
        return notesArray;
    }

    public Map<String, String> getNote(String noteId) throws IOException {
        //Formerly JsonNode
        ArrayNode notesArray = getNotesArray();
        Map<String, String> noteContent = new HashMap<String, String>();
        for (JsonNode note : notesArray) {
            if (note.get("name").asText().equals(noteId)) {
                noteContent.put("name", note.get("name").asText());
                noteContent.put("text", note.get("text").asText());
                noteContent.put("url", note.get("URL").asText());
                noteContent.put("timeAdded", note.get("timeAdded").asText());
                break;
            }
        }
        return noteContent;
    }

    public List<Map<String, String>> getAllNotes() throws IOException{
        ArrayNode notesArray = getNotesArray();
        List<Map<String, String>> allNotes = new ArrayList<>();
        for (JsonNode note : notesArray){
            String noteId = note.get("name").asText();
            allNotes.add(getNote(noteId));
        }
        return allNotes;
    }

    public void addNote(Map<String, String> fields) throws IOException {
        ArrayNode notesArray = getNotesArray();

        try {
            ObjectNode file = (ObjectNode) objectMapper.readTree(new File(fileName));
            ObjectNode newNote = newNoteObject(objectMapper, fields);
            notesArray.add(newNote);
            updateNote(file, notesArray);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public void deleteNote(String noteId) throws IOException {
        ArrayNode notesArray = getNotesArray();
        ObjectNode file = (ObjectNode) objectMapper.readTree(new File(fileName));
        try {
            for (int i = 0; i < notesArray.size(); i++) {
                JsonNode note = notesArray.get(i);
                if (note.get("name").asText().equals(noteId)) {
                    notesArray.remove(i);
                    break;
                }
            }
            updateNote(file, notesArray);
        } catch (IOException e) {
            throw new RuntimeException("Error processing JSON file", e);
        }
    }

    public void editNote(String noteId, String editType, String newContent) throws IOException {
        ArrayNode notesArray = getNotesArray();
        try {
            ObjectNode file = (ObjectNode) objectMapper.readTree(new File(fileName));
            ArrayNode editedNotes;
            if (editType.equals("0")){
                editedNotes = renameNote(notesArray, noteId, newContent);
            }
            else {
                editedNotes = editNoteBody(notesArray, noteId, newContent);
            }
            updateNote(file, editedNotes);
        } catch (IOException e){
            throw new RuntimeException("Error processing JSON file", e);
        }
    }

    public ArrayNode editNoteBody(ArrayNode notes, String noteId, String newContent) throws IOException{
        for (int i = 0; i < notes.size(); i++){
            ObjectNode note = (ObjectNode) notes.get(i);
            if (note.get("name").asText().equals(noteId)){
                note.put("text", newContent);
                note.put("timeEdited", String.valueOf(LocalDateTime.now()));
                notes.set(i, (JsonNode) note);
                return notes;
            }
        }
        return notes;
    }

    public ArrayNode renameNote(ArrayNode notes, String noteId, String newContent) throws IOException {
        for (int i = 0; i < notes.size(); i++){
            ObjectNode note = (ObjectNode) notes.get(i);
            if (note.get("name").asText().equals(noteId)){
                note.put("name", newContent);
                note.put("timeEdited", String.valueOf(LocalDateTime.now()));
                notes.set(i, (JsonNode) note);
                return notes;
            }
        }
        return notes;
    }

    public void updateNote(ObjectNode file, ArrayNode notesArray) throws IOException{
        file.set("notes", notesArray);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), file);
    }

    public ObjectNode newNoteObject(ObjectMapper objectMapper, Map<String, String> fields){
        ObjectNode newNote = objectMapper.createObjectNode();
        newNote.put("name", fields.get("name"));
        newNote.put("text", fields.get("text"));
        newNote.put("URL", fields.get("URL"));
        newNote.put("imageURL", fields.get("imageURL"));
        newNote.put("timeAdded", fields.get("timeAdded"));
        newNote.put("timeEdited", fields.get("timeEdited"));
        return newNote;
    }
}
