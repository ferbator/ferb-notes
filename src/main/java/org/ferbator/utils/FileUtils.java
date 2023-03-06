package org.ferbator.utils;

import org.ferbator.Note;
import org.ferbator.NoteList;

import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.Date;
import java.util.Scanner;

public class FileUtils {
    private static final String FILE_NAME = "notes.txt";

    public static void saveNotes(NoteList noteList) {
        try (PrintWriter writer = new PrintWriter(new FileWriter(FILE_NAME))) {
            for (Note note : noteList.getNotes()) {
                writer.println(note.getTitle());
                writer.println(note.getContent());
                writer.println(note.getCreated().getTime());
            }
        } catch (IOException e) {
            System.err.println("Ошибка при сохранении заметок: " + e.getMessage());
        }
    }

    public static NoteList loadNotes() {
        NoteList noteList = new NoteList();
        try (Scanner scanner = new Scanner(new FileReader(FILE_NAME))) {
            while (scanner.hasNextLine()) {
                String title = scanner.nextLine();
                String content = scanner.nextLine();
                long createdTime = Long.parseLong(scanner.nextLine());
                Note note = new Note(title, content, new Date(createdTime));
                noteList.add(note);
            }
        } catch (IOException e) {
            System.err.println("Ошибка при загрузке заметок: " + e.getMessage());
        }
        return noteList;
    }
}
