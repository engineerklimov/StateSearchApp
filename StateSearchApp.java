import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Scanner;

public class StateSearchApp {

    private static final String[] STATES = {
        "Alabama", "Alaska", "Arizona", "Arkansas", "California", "Colorado", "Connecticut", "Delaware",
        "Florida", "Georgia", "Hawaii", "Idaho", "Illinois", "Indiana", "Iowa", "Kansas", "Kentucky", 
        "Louisiana", "Maine", "Maryland", "Massachusetts", "Michigan", "Minnesota", "Mississippi", 
        "Missouri", "Montana", "Nebraska", "Nevada", "New Hampshire", "New Jersey", "New Mexico", 
        "New York", "North Carolina", "North Dakota", "Ohio", "Oklahoma", "Oregon", "Pennsylvania", 
        "Rhode Island", "South Carolina", "South Dakota", "Tennessee", "Texas", "Utah", "Vermont", 
        "Virginia", "Washington", "West Virginia", "Wisconsin", "Wyoming"
    };

    public static void main(String[] args) {
        Scanner scanner = new Scanner(System.in);
        boolean running = true;

        while (running) {
            System.out.println("\nMenu:");
            System.out.println("1. Display the text");
            System.out.println("2. Search");
            System.out.println("3. Exit program");
            System.out.print("Select an option: ");
            int choice = scanner.nextInt();
            scanner.nextLine(); // Consume newline

            switch (choice) {
                case 1:
                    displayText();
                    break;
                case 2:
                    System.out.print("Enter a pattern to search for: ");
                    String pattern = scanner.nextLine();
                    searchPattern(pattern);
                    break;
                case 3:
                    running = false;
                    System.out.println("Exiting program.");
                    break;
                default:
                    System.out.println("Invalid option. Please try again.");
            }
        }

        scanner.close();
    }

    private static void displayText() {
        System.out.println("\nList of 50 U.S. States:");
        for (String state : STATES) {
            System.out.println(state);
        }
    }

    private static void searchPattern(String pattern) {
        System.out.println("Searching for pattern: \"" + pattern + "\"");
        List<Integer> matches = new ArrayList<>();

        // Search for the pattern in each state using the Boyer-Moore bad character rule
        for (int i = 0; i < STATES.length; i++) {
            int index = boyerMooreSearch(STATES[i], pattern);
            if (index != -1) {
                matches.add(i); // Store the index of the state where the match was found
            }
        }

        // Display the results
        if (matches.isEmpty()) {
            System.out.println("No matches found for \"" + pattern + "\".");
        } else {
            System.out.println("Pattern found in the following states:");
            for (int i : matches) {
                System.out.println("State: " + STATES[i] + " (Index: " + i + ")");
            }
        }
    }

    private static int boyerMooreSearch(String text, String pattern) {
        int m = pattern.length();
        int n = text.length();

        if (m == 0) return 0; // An empty pattern is found at index 0

        // Preprocess pattern to create the bad character rule map
        Map<Character, Integer> badCharTable = buildBadCharTable(pattern);

        int skip;
        for (int i = 0; i <= n - m; i += skip) {
            skip = 0;
            for (int j = m - 1; j >= 0; j--) {
                if (pattern.charAt(j) != text.charAt(i + j)) {
                    skip = Math.max(1, j - badCharTable.getOrDefault(text.charAt(i + j), -1));
                    break;
                }
            }
            if (skip == 0) {
                return i; // Found a match
            }
        }
        return -1; // No match found
    }

    private static Map<Character, Integer> buildBadCharTable(String pattern) {
        Map<Character, Integer> table = new HashMap<>();
        for (int i = 0; i < pattern.length(); i++) {
            table.put(pattern.charAt(i), i);
        }
        return table;
    }
}