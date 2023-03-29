package org.ferbator.ui;

import org.ferbator.Application;
import org.ferbator.Note;
import org.ferbator.NoteList;
import org.jetbrains.annotations.NotNull;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;
import java.awt.image.BufferedImage;
import java.io.*;

public class GraphicalUI implements UI {
    private JFrame frame;
    private JTextPane noteEditor;
    private final NoteList notes;
    private final Application app;

    private class NoteMenu extends JPanel {
        private final JList<Note> noteList;
        private final DefaultListModel<Note> listModel;

        private NoteMenu() {
            super(new BorderLayout());

            listModel = new DefaultListModel<>();

            for (Note note : notes.getNotes()) {
                listModel.addElement(note);
            }

            noteList = new JList<>(listModel);
            noteList.setSelectionMode(ListSelectionModel.SINGLE_SELECTION);
            JScrollPane listScrollPane = new JScrollPane(noteList);

            JMenuBar menuBar = new JMenuBar();
            JMenu m1 = new JMenu("Setting");

            JMenuItem mi1 = new JMenuItem("Сохранить");
            JMenuItem mi2 = new JMenuItem("Добавить заметку");
            JMenuItem mi3 = new JMenuItem("Удалить заметку");

            mi1.addActionListener(e -> {
                int index = noteList.getSelectedIndex();
                if (index >= 0) {
                    Note note = listModel.get(index);
                    note.setContent(noteEditor.getText());
                    notes.add(note);
                }
            });
            mi2.addActionListener(e -> {
                Note note = new Note("New File", "Hello world!");
                notes.add(note);
                listModel.addElement(note);
            });
            mi3.addActionListener(e -> {
                int index = noteList.getSelectedIndex();
                if (index >= 0) {
                    listModel.remove(index);
                    notes.remove(index);
                }
            });
            m1.add(mi1);
            m1.add(mi2);
            m1.add(mi3);
            menuBar.add(m1);


            add(listScrollPane, BorderLayout.CENTER);
            add(menuBar, BorderLayout.NORTH);
        }
    }

    public GraphicalUI(NoteList notes, Application app) {
        this.notes = notes;
        this.app = app;
    }


    @Override
    public void start() {
        frame = new JFrame("Заметки");


        NoteMenu noteMenu = new NoteMenu();
        noteMenu.noteList.addListSelectionListener(e -> {
            if (!e.getValueIsAdjusting()) {
                Note note = noteMenu.noteList.getSelectedValue();
                if (note != null) {
                    showNoteInEditor(note);
                }
            }
        });

        frame.add(noteMenu, BorderLayout.EAST);


        noteEditor = new JTextPane();
        frame.add(new JScrollPane(noteEditor), BorderLayout.CENTER);

        JMenuBar menuBar = new JMenuBar();
        JMenu m1 = new JMenu("File");

        JMenuItem mi1 = new JMenuItem("New");
        JMenuItem mi2 = new JMenuItem("Open");
        JMenuItem mi3 = new JMenuItem("Save");

        mi1.addActionListener(e -> noteEditor.setText(""));
        mi2.addActionListener(e -> openFile());
        mi3.addActionListener(e -> saveFile());

        m1.add(mi1);
        m1.add(mi2);
        m1.add(mi3);

        JMenu m2 = new JMenu("Edit");

        JMenuItem mi4 = new JMenuItem("cut");
        JMenuItem mi5 = new JMenuItem("copy");
        JMenuItem mi6 = new JMenuItem("paste");
        JMenuItem mi7 = new JMenuItem("paste imagine");

        mi4.addActionListener(e -> noteEditor.cut());
        mi5.addActionListener(e -> noteEditor.copy());
        mi6.addActionListener(e -> noteEditor.paste());
        mi7.addActionListener(e -> insertImage());

        m2.add(mi4);
        m2.add(mi5);
        m2.add(mi6);
        m2.add(mi7);

        menuBar.add(m1);
        menuBar.add(m2);

        frame.setJMenuBar(menuBar);

        frame.setSize(800, 600);
        frame.setLocationRelativeTo(null);
        frame.setDefaultCloseOperation(JFrame.DO_NOTHING_ON_CLOSE);
        frame.addWindowListener(new WindowAdapter() {
            public void windowClosing(WindowEvent e) {
                frame.setVisible(false);
                app.stop();
                System.exit(0);
            }
        });

        frame.setVisible(true);
    }

    private void showNoteInEditor(@NotNull Note note) {
        noteEditor.setText(note.getContent());
    }

    private void insertImage() {
        JFileChooser fileChooser = new JFileChooser();
        int result = fileChooser.showOpenDialog(frame);
        if (result == JFileChooser.APPROVE_OPTION) {
            File file = fileChooser.getSelectedFile();
            try {
                BufferedImage image = ImageIO.read(file);
                ImageIcon icon = new ImageIcon(image);
                noteEditor.insertIcon(icon);
            } catch (IOException ex) {
                ex.printStackTrace();
            }
        }
    }

    private void openFile() {
        JFileChooser j = new JFileChooser("f:");

        int r = j.showOpenDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {
            File fi = new File(j.getSelectedFile().getAbsolutePath());

            try {
                String s1;
                StringBuilder sl;

                FileReader fr = new FileReader(fi);

                BufferedReader br = new BufferedReader(fr);

                sl = new StringBuilder(br.readLine());

                while ((s1 = br.readLine()) != null) {
                    sl.append("\n").append(s1);
                }

                noteEditor.setText(sl.toString());
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(frame, evt.getMessage());
            }
        } else JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
    }

    private void saveFile() {
        JFileChooser j = new JFileChooser("f:");

        int r = j.showSaveDialog(null);

        if (r == JFileChooser.APPROVE_OPTION) {

            File fi = new File(j.getSelectedFile().getAbsolutePath());

            try {
                FileWriter wr = new FileWriter(fi, false);

                BufferedWriter w = new BufferedWriter(wr);

                w.write(noteEditor.getText());

                w.flush();
                w.close();
            } catch (Exception evt) {
                JOptionPane.showMessageDialog(frame, evt.getMessage());
            }
        } else JOptionPane.showMessageDialog(frame, "the user cancelled the operation");
    }
}