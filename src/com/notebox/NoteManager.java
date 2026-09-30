package com.notebox;

import java.io.*;
import java.util.ArrayList;
import java.util.List;

public class NoteManager {
    private final List<Note> notes = new ArrayList<>();
    private final String filePath = "data/notes.txt";

    public NoteManager() { loadNotes(); }

    public void add(String title, String content) {
        notes.add(new Note(nextId(), title, content));
        save();
        System.out.println("Note added.");
    }

    public void viewAll() {
        if (notes.isEmpty()) {
            System.out.println("No notes found.");
            return;
        }
        notes.forEach(System.out::println);
    }

    public void search(String keyword) {
        boolean found = false;
        for (Note n : notes) {
            if (n.getTitle().toLowerCase().contains(keyword.toLowerCase())
                    || n.getContent().toLowerCase().contains(keyword.toLowerCase())) {
                System.out.println(n);
                found = true;
            }
        }
        if (!found) System.out.println("No matching notes found.");
    }

    public boolean update(int id, String title, String content) {
        for (Note n : notes) {
            if (n.getId() == id) {
                n.setTitle(title);
                n.setContent(content);
                save();
                return true;
            }
        }
        return false;
    }

    public boolean delete(int id) {
        for (Note n : notes) {
            if (n.getId() == id) {
                notes.remove(n);
                save();
                return true;
            }
        }
        return false;
    }

    private int nextId() {
        int max = 0;
        for (Note n : notes) max = Math.max(max, n.getId());
        return max + 1;
    }

    private void save() {
        try {
            File file = new File(filePath);
            file.getParentFile().mkdirs();
            try (PrintWriter out = new PrintWriter(new FileWriter(file))) {
                for (Note n : notes) {
                    out.println(n.getId() + "|" + clean(n.getTitle()) + "|" + clean(n.getContent()));
                }
            }
        } catch (IOException e) {
            System.out.println("Could not save notes.");
        }
    }

    private void loadNotes() {
        File file = new File(filePath);
        if (!file.exists()) return;

        try (BufferedReader in = new BufferedReader(new FileReader(file))) {
            String line;
            while ((line = in.readLine()) != null) {
                String[] p = line.split("\\|", 3);
                if (p.length == 3) {
                    try {
                        notes.add(new Note(Integer.parseInt(p[0]), p[1], p[2]));
                    } catch (NumberFormatException ignored) {}
                }
            }
        } catch (IOException e) {
            System.out.println("Could not load notes.");
        }
    }

    private String clean(String value) {
        return value.replace("|", "/").replace("\n", " ").replace("\r", " ");
    }
}
