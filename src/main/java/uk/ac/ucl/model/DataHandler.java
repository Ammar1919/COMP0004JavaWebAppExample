package uk.ac.ucl.model;

import java.io.IOException;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.node.ObjectNode;
import com.fasterxml.jackson.databind.node.ArrayNode;

import java.io.File;

public abstract class DataHandler {
    protected final ObjectMapper objectMapper;
    protected final String fileName;
    protected ObjectNode file;

    public DataHandler(String fileName){
        this.objectMapper = new ObjectMapper();
        this.fileName = fileName;
        try {
            this.file = (ObjectNode) objectMapper.readTree(new File(fileName));
        }
        catch (IOException e){
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
    }

    //Returns ArrayNode of either notes or categories depending on  fieldName
    public ArrayNode getArray(String fieldName){
        ArrayNode array;
        try {
            file = (ObjectNode) objectMapper.readTree(new File(fileName));
            array = (ArrayNode) file.get(fieldName);
        }
        catch (IOException e){
            throw new RuntimeException("Error reading file: " + fileName, e);
        }
        return array;
    }

    //Returns the index of the note or category in their respective ArrayNode
    public int matchItemById(ArrayNode array, String itemId){
        for (int i = 0; i < array.size(); i++){
            if (array.get(i).get("name").asText().equals(itemId)){
                return i;
            }
        }
        return -1;
    }

    //Updates the "notes.json" file upon changes made to an ArrayNode an its respective field
    public void updateFile(String field, ArrayNode array) throws IOException {
        file.set(field, array);
        objectMapper.writerWithDefaultPrettyPrinter().writeValue(new File(fileName), file);
    }
}
