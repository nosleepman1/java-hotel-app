package Views;

import java.time.LocalDate;
import java.util.Scanner;

public class InputHelper {

    private final Scanner scanner;

    public InputHelper() {
        this.scanner = new Scanner(System.in);
    }

    public String readString(String prompt) {
        System.out.print(prompt);
        return scanner.nextLine().trim();
    }

    public int readInt(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Integer.parseInt(line);
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mErreur : Veuillez entrer un nombre entier valide.\u001B[0m");
            }
        }
    }

    public double readDouble(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return Double.parseDouble(line);
            } catch (NumberFormatException e) {
                System.out.println("\u001B[31mErreur : Veuillez entrer un nombre décimal valide.\u001B[0m");
            }
        }
    }

    public boolean readBoolean(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim().toLowerCase();
            if (line.equals("o") || line.equals("y") || line.equals("oui") || line.equals("yes") || line.equals("true")) {
                return true;
            } else if (line.equals("n") || line.equals("non") || line.equals("no") || line.equals("false")) {
                return false;
            } else {
                System.out.println("\u001B[31mErreur : Veuillez répondre par 'o' (oui) ou 'n' (non).\u001B[0m");
            }
        }
    }

    public LocalDate readDate(String prompt) {
        while (true) {
            System.out.print(prompt);
            String line = scanner.nextLine().trim();
            try {
                return LocalDate.parse(line);
            } catch (java.time.format.DateTimeParseException e) {
                System.out.println("\u001B[31mErreur : Date invalide. Utilisez le format AAAA-MM-JJ (ex: 2026-06-12).\u001B[0m");
            }
        }
    }

    public void close() {
        scanner.close();
    }
}
