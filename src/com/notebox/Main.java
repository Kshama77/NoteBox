package com.notebox;

import java.util.Scanner;

public class Main {
    private static final Scanner scanner = new Scanner(System.in);
    private static final NoteManager manager = new NoteManager();

    public static void main(String[] args) {
        boolean running = true;
        System.out.println("===== NoteBox =====");

        while (running) {
            System.out.println("\n1. Add Note");
            System.out.println("2. View Notes");
            System.out.println("3. Search Notes");
            System.out.println("4. Update Note");
            System.out.println("5. Delete Note");
            System.out.println("6. Exit");
            System.out.print("Choose: ");

            switch (scanner.nextLine()) {
                case "1" -> add();
                case "2" -> manager.viewAll();
                case "3" -> search();
                case "4" -> update();
                case "5" -> delete();
                case "6" -> running = false;
                default -> System.out.println("Invalid option.");
            }
        }
        System.out.println("Goodbye!");
    }

    private static void add() {
        System.out.print("Title: ");
        String title = scanner.nextLine();
        System.out.print("Content: ");
        String content = scanner.nextLine();
        manager.add(title, content);
    }

    private static void search() {
        System.out.print("Keyword: ");
        manager.search(scanner.nextLine());
    }

    private static void update() {
        try {
            System.out.print("Note ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.print("New title: ");
            String title = scanner.nextLine();
            System.out.print("New content: ");
            String content = scanner.nextLine();
            System.out.println(manager.update(id, title, content) ? "Note updated." : "Note not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }

    private static void delete() {
        try {
            System.out.print("Note ID: ");
            int id = Integer.parseInt(scanner.nextLine());
            System.out.println(manager.delete(id) ? "Note deleted." : "Note not found.");
        } catch (NumberFormatException e) {
            System.out.println("Invalid ID.");
        }
    }
}
