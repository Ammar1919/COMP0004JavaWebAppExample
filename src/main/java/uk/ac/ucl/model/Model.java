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

public class Model
{
  public Note note = new Note("data" + File.separator + "notes.json");

  // The example code in this class should be replaced by your Model class code.
  // The data should be stored in a suitable data structure.

  public List<String> getPatientNames() throws IOException {
    return readJFile("data"+File.separator+"notes.json");
  }

  // This method illustrates how to read csv data from a file.
  // The data files are stored in the root directory of the project (the directory your project is in),
  // in the directory named data.

  public List<String> getIndex() throws IOException {return readJFile("data"+File.separator+"notes.json");}

  public ArrayNode getNotesArray() throws IOException{
    ObjectMapper objectMapper = new ObjectMapper();
    JsonNode rootNode = objectMapper.readTree(new File("data" + File.separator + "notes.json"));
    ArrayNode notesArray;
    try {
      notesArray = (ArrayNode) rootNode.get("notes");
    }
    catch (NullPointerException e){
      throw new RuntimeException("'notes' field does not exist in data/notes.json");
    }
    return notesArray;
  }

  public List<String> readJFile(String fileName) throws IOException {
    List<String> notes = new ArrayList<>();
    ArrayNode notesArray = getNotesArray();//Formerly JsonNode

    for (JsonNode note : notesArray){
      notes.add(note.get("name").asText());
    }

    return notes;
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

  public void addNote(String fileName, Map<String, String> fields) throws IOException {
    ObjectMapper objectMapper = new ObjectMapper();

    ArrayNode notesArray = getNotesArray();

    try {
      ObjectNode file = (ObjectNode) objectMapper.readTree(new File(fileName));
      ObjectNode newNote = newNoteObject(objectMapper, fields);
      notesArray.add(newNote);
      file.set("notes", notesArray);
      objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), file);

    } catch (IOException e) {
        throw new RuntimeException(e);
    }
  }

  public void deleteNote(String fileName, String noteId) throws IOException {
      ObjectMapper objectMapper = new ObjectMapper();
      ArrayNode notesArray = getNotesArray();
      ObjectNode file = (ObjectNode) objectMapper.readTree(new File("data"+File.separator+"notes.json"));


    try {
        // Load JSON from file

        // Find and remove the matching note
        boolean deleted = false;
        for (int i = 0; i < notesArray.size(); i++) {
          JsonNode note = notesArray.get(i);
          if (note.has("name") && note.get("name").asText().equals(noteId)) {
            notesArray.remove(i);
            deleted = true;
            break;
          }
        }

        if (deleted) {
          // Update JSON file with modified notes
          file.set("notes", notesArray);
          objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), file);
          System.out.println("Note deleted successfully: " + noteId);
        } else {
          System.out.println("Note not found: " + noteId);
        }

      } catch (IOException e) {
        throw new RuntimeException("Error processing JSON file", e);
      }
    }

    public void editNote(String noteId, String editType, String newContent) throws IOException {
      ObjectMapper objectMapper = new ObjectMapper();
      ArrayNode notesArray = getNotesArray();

      try {
        ObjectNode file = (ObjectNode) objectMapper.readTree(new File("data"+File.separator+"notes.json"));
        ArrayNode editedNotes = notesArray;
        if (editType.equals("0")){
          editedNotes = renameNote(notesArray, noteId, newContent);
        }
        else {
          editedNotes = editNoteBody(notesArray, noteId, newContent);
        }
        notesArray = editedNotes;
        file.set("notes", notesArray);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File("data"+File.separator+"notes.json"), file);
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
          JsonNode editedNote = (JsonNode) note;
          notes.set(i, editedNote);
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
            JsonNode editedNote = (JsonNode) note;
            notes.set(i, editedNote);
            return notes;
          }
        }
        return notes;
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



  public List<String> readFile(String fileName)
  {
    List<String> data = new ArrayList<>();

    try (Reader reader = new FileReader(fileName);
         CSVParser csvParser = new CSVParser(reader, CSVFormat.DEFAULT))
    {
      for (CSVRecord csvRecord : csvParser)
      {
        // The first row of the file contains the column headers, so is not actual data.
        data.add(csvRecord.get(0));
      }
    } catch (IOException e)
    {
      e.printStackTrace();
    }
    return data;
  }

  // This also returns dummy data. The real version should use the keyword parameter to search
  // the data and return a list of matching items.
  public List<String> searchFor(String keyword)
  {
    return List.of("Search keyword is: "+ keyword, "result1", "result2", "result3");
  }
}
