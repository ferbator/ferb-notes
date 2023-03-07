package org.ferbator;

import org.ferbator.ui.ConsoleUI;
import org.ferbator.ui.GraphicalUI;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    private static final Boolean DEBUG = true;
    public static Boolean FIRST_START;
    private NoteList noteList;
    private final Path filePath = Paths.get("src/main/resources/conf.bin");

    public void init() {

    }

    public void start() {
        try {
            FileInputStream fis = new FileInputStream(filePath.toFile());
            ObjectInputStream ois = new ObjectInputStream(fis);

            int notesCount = ois.readInt();

            for (int i = 0; i < notesCount; i++) {
                noteList.add((Note) ois.readObject());
            }

        } catch (IOException | ClassNotFoundException e) {
            throw new RuntimeException(e);
        }
    }

    public void stop() {
        try {
            FileOutputStream fos = new FileOutputStream(filePath.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeInt(noteList.size());

            for (int i = 0; i < noteList.size(); i++) {
                oos.writeObject(noteList.getNote(i));
            }
            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }

    public static void main(String[] args) {
        var service = new Application();
        service.noteList = new NoteList();
        service.start();
        if (DEBUG) {
            ConsoleUI consoleUI = new ConsoleUI(service.noteList, service);
            consoleUI.start();
        } else {
            GraphicalUI graphicalUI = new GraphicalUI(service.noteList, service);
        }
    }
}
