package org.spdcalpoly;

import java.util.ArrayList;
import java.util.Scanner;

/**
 * DialogManager.java
 *
 * The DialogManager class is used to communicate with the AI player. It allows
 * an easy interface for outputting messages to the user and retrieving any
 * necessary input.
 *
 * @author Brandon M. Kelley
 * @since 09/08/2016
 * @version 1.0
 */
class DialogManager {

    // Used to read user input.
    private Scanner userInput;

    // The index where the last array prompt ended.
    // Accessor: protected getLastArrayPromptIndex()
    private int lastArrayPromptIndex;

    /**
     * Empty constructor, initializes all instance variables.
     */
    DialogManager() {
        userInput = new Scanner(System.in);
        lastArrayPromptIndex = -1;
    }

    void print(String text) {
        System.out.print(text);
    }

    void println(String text) {
        System.out.println(text);
    }

    /**
     * Retrieves a generic object from the AI player using the provided prompt
     * and type parser. Returns null if the input could not be parsed to the
     * given type.
     * @param prompt The string to output asking for the object.
     * @param type The user-friendly string representing the generic datatype.
     * @param typeParser The parser used to create the object from the input.
     * @param <T> The type to parse the input into.
     * @return The object of the specified type that was parsed.
     */
    private <T> T prompt(String prompt, String type, TypeParser<T> typeParser) {

        // Input collected from the AI player.
        String input;

        // The generic object parsed from the AI player's input.
        T result = null;

        // Loop until an object is found.
        while (result == null) {

            // Prompt the AI player.
            System.out.print(prompt);

            // Retrieve AI player's input.
            input = userInput.nextLine();

            // Attempt to parse the input into the generic object.
            result = typeParser.parse(input);

            // If the parsing failed, print a message.
            if (result == null) {
                System.out.println("Sorry, \"" + input + "\" is not a valid "
                        + type + ", please try again.");
            }

        }

        return result;

    }

    /**
     * Retrieves an integer from the AI player using the provided prompt.
     * Returns null if the input could not be parsed to an integer.
     * @param prompt The string to output asking for an integer.
     * @return The integer received from the AI player.
     */
    int promptInteger(String prompt) {

        // Call the generic prompt and pass it a function for parsing integers.
        return this.prompt(prompt, "number", (String input) -> {

            // The parsing result.
            Integer found = null;

            // Try to parse it into an integer.
            try {
                found = Integer.parseInt(input);
            }

            // If parsing to an integer fails, oh well.
            catch (NumberFormatException e) {
                // Do nothing.
            }

            // Return what we found.
            return found;

        });
    }

    /**
     * Retrieves a integer array (delimited by spaces) from the AI player using
     * the provided prompt. Returns null if the input could not be parsed to an
     * integer array.
     * @param prompt The string to output asking for an integer array.
     * @param size The maximum number of entries the AI player can input.
     * @param exact Whether to require exactly size integers or just allow
     *     that many.
     * @return The integer array received from the AI player.
     */
    ArrayList<Integer> promptIntegerArray(String prompt, int size,
                                          boolean exact) {

        // Call the generic prompt and pass it a function for parsing integer
        // arrays that satisfy the size and exact parameter requirements.
        return this.prompt(prompt, "list of numbers", (String input) -> {

            ArrayList<Integer> found = new ArrayList<Integer>();
            String[] tokens = input.split("\\s+");

            // If the number of tokens is not exact and it was supposed to be,
            // just stop now.
            if (tokens.length != size && exact) {
                return null;
            }

            // Loop through tokens and try to parse each.
            for (String token : tokens) {

                // Try to parse it into an integer.
                try {
                    found.add(Integer.parseInt(token));
                    lastArrayPromptIndex += 1;
                }

                // If parsing to an integer fails, oh well.
                catch (NumberFormatException e) {
                    return null;
                }

            }

            return found;

        });

    }

    /**
     * Retrieves a boolean from the AI player using the provided prompt. Returns
     * null if the input could not be parsed to a boolean.
     * @param prompt The string to output asking for a boolean.
     * @return The boolean received from the AI player.
     */
    boolean promptBoolean(String prompt) {

        // Call the generic prompt and pass it a function for parsing booleans.
        return this.prompt(prompt, "option", (String input) -> {

            // The parsing result.
            Boolean found = null;

            // Try to parse it into a boolean.
            found = input.equals("yes");
            if (!found && !input.equals("no")) {
                return null;
            }

            // Return what we found.
            return found;

        });

    }

    /**
     * Retrieves a string from the AI player using the provided prompt.
     * @param prompt The string to output asking for a string.
     * @return The string retrieved from the AI player.
     */
    String promptString(String prompt) {
        // Let's keep it simple.
        System.out.print(prompt);
        return userInput.nextLine();
    }

    /**
     * An accessor for the lastArrayPromptIndex instance variable.
     * @return The last index where a value was added to a prompted array.
     */
    int getLastArrayPromptIndex() {
        return lastArrayPromptIndex;
    }

    /**
     * An interface for a parser that creates a given type from a string.
     * @param <T> The type to create from the input string.
     */
    private interface TypeParser<T> {

        /**
         * Creates an object of the class type from the input string.
         * @param input The string to parse into an object.
         * @return The object parsed from the input string.
         */
        T parse(String input);

    }

}
