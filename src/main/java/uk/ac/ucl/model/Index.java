package uk.ac.ucl.model;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.JsonNode;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class Index {
    private final ArrayNode notesArray;

    public Index(ArrayNode notesArray) {
        this.notesArray = notesArray;
    }

    public List<String> getIndex(){
        List<String> notes = new ArrayList<>();
        for (JsonNode note : notesArray){
            notes.add(note.get("name").asText());
        }
        return notes;
    }

    public List<String> searchNote(List<Map<String, String>> notes, String query) {
        List<String> result = new ArrayList<>();
        for (Map<String, String> note : notes){
            String text = note.get("name").toLowerCase();
            if (text.contains(query.toLowerCase())){
                result.add(note.get("name"));
            }
        }
        return result;
    }
}
