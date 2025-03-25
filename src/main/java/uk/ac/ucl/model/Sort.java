package uk.ac.ucl.model;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Map;

public class Sort {

    public void sortNotesByTime(List<Map<String, String>> allNotes, String sortId) {
        boolean ascendingTime = sortId.equals("0");

        for (int i = 0; i < allNotes.size() - 1; i++){
            boolean swapped = false;
            for (int j = 0; j < allNotes.size() - i - 1; j++){
                swapped = sortByTime(allNotes, j, ascendingTime);
            }
            if (!swapped){
                break;
            }
        }
    }

    public boolean sortByTime(List<Map<String, String>> allNotes, int index, boolean ascendingTime){
        Map<String, String> note = allNotes.get(index);
        Map<String, String> nextNote = allNotes.get(index + 1);
        LocalDateTime noteTime = LocalDateTime.parse(note.get("timeAdded"));
        LocalDateTime nextNoteTime = LocalDateTime.parse(nextNote.get("timeAdded"));
        if (ascendingTime && noteTime.isAfter(nextNoteTime) || !ascendingTime && noteTime.isBefore(nextNoteTime)){
            allNotes.set(index, nextNote);
            allNotes.set(index + 1, note);
            return true;
        }
        return false;
    }

    public void sortNotesByAlphabet(List<Map<String, String>> allNotes, String sortId) {

        boolean ascendingOrder = sortId.equals("2");

        for (int i = 0; i < allNotes.size() - 1; i++){
            boolean swapped = false;
            for (int j = 0; j < allNotes.size() - i - 1; j++){
                swapped = sortByAlpha(allNotes, j, ascendingOrder);
            }
            if (!swapped){
                break;
            }
        }
    }

    public boolean sortByAlpha(List<Map<String, String>> allNotes, int index, boolean ascendingOrder){
        Map<String, String> note = allNotes.get(index);
        Map<String, String> nextNote = allNotes.get(index + 1);
        String noteName = note.get("name").toLowerCase();
        String nextNoteName = nextNote.get("name").toLowerCase();
        if (ascendingOrder && noteName.compareTo(nextNoteName) > 0 || !ascendingOrder && noteName.compareTo(nextNoteName) < 0){
            allNotes.set(index, nextNote);
            allNotes.set(index + 1, note);
            return true;
        }
        return false;
    }
}
