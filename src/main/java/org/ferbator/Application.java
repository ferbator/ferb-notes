package org.ferbator;

import org.ferbator.ui.ConsoleUI;
import org.ferbator.ui.GraphicalUI;
import org.ferbator.ui.UI;

import java.io.*;
import java.nio.file.Path;
import java.nio.file.Paths;

public class Application {
    private static final Boolean DEBUG = false;
    public static Boolean FIRST_START;
    private NoteList noteList;
    private final Path filePath = Paths.get("src/main/resources/conf.bin");
    private UI ui;

    public void init() {
        FileOutputStream fos;
        try {
            fos = new FileOutputStream(filePath.toFile());
            ObjectOutputStream oos = new ObjectOutputStream(fos);

            oos.writeInt(1);
            noteList.add(new Note("Hello", "Hello World!"));
            oos.writeObject(noteList.getNote(0));

            fos.close();
        } catch (IOException e) {
            throw new RuntimeException(e);
        }


    }

    public boolean isFileEmpty(File file) {
        return file.length() == 0;
    }

    public void start() {
        try {
            if (!isFileEmpty(filePath.toFile())) {
                FIRST_START = false;
                FileInputStream fis = new FileInputStream(filePath.toFile());
                ObjectInputStream ois = new ObjectInputStream(fis);

                int notesCount = ois.readInt();

                for (int i = 0; i < notesCount; i++) {
                    noteList.add((Note) ois.readObject());
                }
            } else {
                FIRST_START = true;
                init();
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
            service.ui = new ConsoleUI(service.noteList, service);
            service.ui.start();
        } else {
            service.ui = new GraphicalUI(service.noteList, service);
            service.ui.start();
        }
    }
}
