package org.ferbator;

import java.util.ArrayList;
import java.util.List;

public class NoteList {
    public List<Note> notes = new ArrayList<>();

    public NoteList() {
    }

    public void add(Note note) {
        notes.add(note);
    }

    public void remove(int index) {
        notes.remove(index);
    }

    public List<Note> getNotes() {
        return notes.stream().toList();
    }

    public Note getNote(int index) {
        return notes.get(index);
    }

    public int size() {
        return notes.size();
    }

    public void displayAll() {
        int counter = 0;
        for (Note note : notes) {
            System.out.println((counter + 1) + ". " + "<date:" + note.getCreated() + "> " + note.getTitle());
            counter++;
        }
    }
}

