package org.ferbator;

import org.ferbator.utils.FileUtils;
import org.ferbator.utils.InputUtils;

public class Main {
    public static void main(String[] args) {
        NoteList noteList = FileUtils.loadNotes();

        while (true) {
            System.out.println("1 - Добавить заметку");
            System.out.println("2 - Удалить заметку");
            System.out.println("3 - Показать список заметок");
            System.out.println("4 - Сохранить заметки в файл");
            System.out.println("5 - Открыть заметку");
            System.out.println("6 - Выход");

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
                        FileUtils.saveNotes(noteList);
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
                    FileUtils.saveNotes(noteList);
                    System.out.println("Заметки сохранены в файл.");
                }
                case 5 -> {
                    int index = InputUtils.getInt("Введите номер заметки для вывода: ") - 1;
                    if (index >= 0 && index < noteList.size()) {
                        System.out.println(noteList.getNote(index).toString());
                    } else {
                        System.out.println("Некорректный номер заметки.");
                    }
                }
                case 6 -> {
                    System.out.println("Выход из программы.");
                    System.exit(0);
                }
                default -> System.out.println("Некорректный выбор.");
            }
        }
    }
}