package uk.ac.ucl.model;

import com.fasterxml.jackson.databind.node.ArrayNode;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.JsonNode;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.Iterator;

public class Category extends DataHandler{
    public Category(String fileName) {
        super(fileName);
    }

    public ArrayNode getCategoryArray(){
        return getArray("categories");
    }

    public void newCategory(ArrayNode categoryArray, String categoryName) throws IOException {
        ObjectNode category = objectMapper.createObjectNode();
        category.put("name", categoryName);
        categoryArray.add(category);
        updateFile("categories", categoryArray);
    }

    public void addToCategory(ArrayNode categoryArray, ArrayNode notesArray, String categoryId, String noteId) throws IOException{
        int index = matchItemById(categoryArray, categoryId);
        if (index != -1) {
            ObjectNode category = (ObjectNode) categoryArray.get(index);

            int j = matchItemById(notesArray, noteId);
            ObjectNode note = (ObjectNode) notesArray.get(j);
            note.put("category", category.get("name"));
            category.put(note.get("name").asText(), note.get("name").asText());

            updateFile("notes", notesArray);
            updateFile("categories", categoryArray);
        }
    }

    public List<String> getCategoryNotes(ArrayNode categoryArray, String categoryId) {
        List<String> notes = new ArrayList<>();
        int index = matchItemById(categoryArray, categoryId);

        if (index != -1) {
            ObjectNode category = (ObjectNode) categoryArray.get(index);

            if (category == null || category.size() < 2) {
                return new ArrayList<>();
            }

            Iterator<Map.Entry<String, JsonNode>> fields = category.fields();

            while (fields.hasNext()) {
                Map.Entry<String, JsonNode> field = fields.next();
                if (!field.getKey().equals("name")) {
                    notes.add(field.getValue().asText());
                }
            }
        }
        return notes;
    }

    public void editNoteNameInCategory(ArrayNode categoryArray, ArrayNode notesArray, String noteId, String newContent) throws IOException {
        for (int i = 0; i < categoryArray.size(); i++){
            if (categoryArray.get(i).has(noteId)){
                ObjectNode category = (ObjectNode) categoryArray.get(i);
                category.remove(noteId);
                category.put(newContent, newContent);
                categoryArray.set(i, category);
            }
        }
        updateFile("categories", categoryArray);
        updateFile("notes", notesArray);
    }

    public void deleteNoteInCategory(ArrayNode categoryArray, ArrayNode notesArray, String noteId) throws IOException {
        for (int i = 0; i < categoryArray.size(); i++){
            if (categoryArray.get(i).has(noteId)){
                ObjectNode category = (ObjectNode) categoryArray.get(i);
                category.remove(noteId);
                categoryArray.set(i, category);
            }
        }
        updateFile("categories", categoryArray);
        updateFile("notes", notesArray);
    }

    public void removeNoteInCategory(ArrayNode categoryArray, String categoryId, String noteId) throws IOException {
        int i = matchItemById(categoryArray, categoryId);
        if (i != -1) {
            ObjectNode category = (ObjectNode) categoryArray.get(i);
            category.remove(noteId);
            categoryArray.set(i, category);
            updateFile("categories", categoryArray);
        }
    }

    public void deleteCategory(ArrayNode categoryArray, String categoryId) {
        try {
            int i = matchItemById(categoryArray, categoryId);
            categoryArray.remove(i);
            updateFile("categories", categoryArray);
        } catch (IOException e) {
            throw new RuntimeException("Error processing JSON file", e);
        }
    }
}
