package org.ferbator.ui;

import org.ferbator.Application;
import org.ferbator.Note;
import org.ferbator.NoteList;
import org.ferbator.utils.InputUtils;

public class ConsoleUI implements UI {

    private final NoteList noteList;
    private final Application app;

    public ConsoleUI(NoteList noteList, Application app) {
        this.noteList = noteList;
        this.app = app;
    }

    @Override
    public void start() {
        while (true) {
            System.out.println("1 - Добавить заметку");
            System.out.println("2 - Удалить заметку");
            System.out.println("3 - Показать список заметок");
            System.out.println("4 - Открыть заметку");
            System.out.println("5 - Выход");

            int choice = InputUtils.getInt("Выберите действие: ");
            switch (choice) {
                case 1 -> {
                    String title = InputUtils.getString("Введите заголовок заметки: ");
                    String content = InputUtils.getString("Введите текст заметки: ");
                    Note note = new Note(title, content);
                    noteList.add(note);
                    System.out.println("Заметка добавлена.");
                }
                case 2 -> {
                    int index = InputUtils.getInt("Введите номер заметки для удаления: ") - 1;
                    if (index >= 0 && index < noteList.size()) {
                        noteList.remove(index);
                        System.out.println("Заметка удалена.");
                    } else {
                        System.out.println("Некорректный номер заметки.");
                    }
                }
                case 3 -> {
                    System.out.println("Список заметок:");
                    noteList.displayAll();
                }
                case 4 -> {
                    int index = InputUtils.getInt("Введите номер заметки для вывода: ") - 1;
                    if (index >= 0 && index < noteList.size()) {
                        System.out.println(noteList.getNote(index).toString());
                    } else {
                        System.out.println("Некорректный номер заметки.");
                    }
                }
                case 5 -> {
                    System.out.println("Выход из программы.");
                    app.stop();
                    System.out.println("Заметки сохранены в файл.");
                    System.exit(0);
                }
                default -> System.out.println("Некорректный выбор.");
            }
        }
    }
}