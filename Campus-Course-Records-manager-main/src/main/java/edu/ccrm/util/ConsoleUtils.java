package edu.ccrm.util;

import java.util.Scanner;

public final class ConsoleUtils {
    private static final Scanner SC = new Scanner(System.in);

    private ConsoleUtils() {}

    public static String readLine(String prompt) {
        System.out.print(prompt);
        return SC.nextLine().trim();
    }

    public static void printHeader(String title) {
        System.out.println("\n=== " + title + " ===");
    }

    public static void pause() {
        System.out.println("\n(Press Enter)");
        SC.nextLine();
    }
}
