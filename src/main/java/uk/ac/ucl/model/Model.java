package uk.ac.ucl.model;

import java.io.IOException;
import java.util.*;
import jakarta.servlet.http.Part;

import com.fasterxml.jackson.databind.node.ArrayNode;

public class Model
{
  private Note note;
  private Category category;

  public Model(String fileName) throws IOException {
    this.note = new Note(fileName);
    this.category = new Category(fileName);
  }

  public List<String> getIndex() {
    ArrayNode notesArray = note.getNotesArray();
    Index index = new Index(notesArray);
    return index.getIndex();
  }

  public Map<String, String> getNote(String noteId) {
    ArrayNode notesArray = note.getNotesArray();
    return note.getNote(notesArray, noteId);
  }

  public List<Map<String, String>> getAllNotes(String sortId) {
    ArrayNode notesArray = note.getNotesArray();
    return note.getAllNotes(notesArray, sortId);
  }

  public void addNote(Map<String, String> fields) {
    ArrayNode notesArray = note.getNotesArray();
    note.addNote(notesArray, fields);
  }

  public void deleteNote(String noteId) throws IOException {
    ArrayNode notesArray = note.getNotesArray();
    note.deleteNote(notesArray, noteId);
    ArrayNode categoryArray = category.getCategoryArray();
    category.deleteNoteInCategory(categoryArray, notesArray, noteId);
  }

  public void editNote(String noteId, String editType, String newContent, Part image) throws IOException {
    ArrayNode notesArray = note.getNotesArray();
    note.editNote(notesArray, noteId, editType, newContent, image);
    if (editType.equals("0")){
      ArrayNode categoryArray = category.getCategoryArray();
      category.editNoteNameInCategory(categoryArray, notesArray, noteId, newContent);
    }
  }

  public List<String> categoryIndex() {
    ArrayNode categoryArray = category.getCategoryArray();
    Index index = new Index(categoryArray);
    return index.getIndex();
  }

  public void newCategory(String categoryName) throws IOException {
    ArrayNode categoryArray = category.getCategoryArray();
    category.newCategory(categoryArray, categoryName);
  }

  public void addToCategory(String categoryId, String noteId) throws IOException {
    ArrayNode notesArray = note.getNotesArray();
    ArrayNode categoryArray = category.getCategoryArray();
    category.addToCategory(categoryArray, notesArray, categoryId, noteId);
  }

  public List<String> getCategoryNotes(String categoryId) {
    ArrayNode categoryArray = category.getCategoryArray();
    return category.getCategoryNotes(categoryArray, categoryId);
  }

  public void removeNoteInCategory(String categoryId, String noteId) throws IOException {
    ArrayNode categoryArray = category.getCategoryArray();
    category.removeNoteInCategory(categoryArray, categoryId, noteId);
  }

  public void deleteCategory(String categoryId) {
    ArrayNode categoryArray = category.getCategoryArray();
    category.deleteCategory(categoryArray, categoryId);
  }

  public List<String> searchNotes(String query) {
    ArrayNode notesArray = note.getNotesArray();
    List<Map<String, String>> allNotes = note.getAllNotes(notesArray, null);
    Index index = new Index(notesArray);
    return index.searchNote(allNotes, query);
  }

  public String searchInNote(String noteId, String query) {
    ArrayNode notesArray = note.getNotesArray();
    return note.searchInNote(notesArray, noteId, query);
  }

  public String saveImage(Part filePart) throws IOException {
    return note.saveImage(filePart);
  }

}
