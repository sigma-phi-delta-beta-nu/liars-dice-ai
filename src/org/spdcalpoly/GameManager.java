package org.spdcalpoly;

/**
 * GameManager.java
 *
 * The GameManager class is the brain of the Liar's Dice AI and controls all
 * interactions between the terminal client and the logic of the AI.
 *
 * @author Brandon M. Kelley
 * @since 09/08/2015
 * @version 1.0
 */
class GameManager {

    // Used to communicate with the AI player.
    private DialogManager dialogManager;

    // The game the AI is currently playing.
    private Game currentGame;

    /**
     * An empty constructor. Initializes all instance variables.
     */
    GameManager() {
        dialogManager = new DialogManager();
        currentGame = startGame();
    }

    /**
     * Runs the AI based on input from the user.
     */
    void startAI() {

        do {
            // Play the game.
            dialogManager.promptString("\nAI @> ");
        }
        while (dialogManager.promptBoolean("Play another game (yes or no)? "));

    }

    /**
     * Creates a new game from settings the AI player inputs.
     * @return The new game that was created.
     */
    private Game startGame() {

        // The game being started.
        Game newGame;

        // Display a friendly welcome message.
        dialogManager.welcomePlayer();

        // Ask the user for important information and create the game.
        int numPlayers = dialogManager.promptInteger("How many players are there? ");
        int numDice = dialogManager.promptInteger("How many dice does each player get to start? ");
        boolean usingWilds = dialogManager.promptBoolean("Are you playing with wild numbers (yes or no)? ");
        if (usingWilds) {
            int[] wilds = dialogManager.promptIntegerArray("Which numbers are wild (delimit with spaces)? ", 6, false);
            newGame = new Game(numPlayers, numDice, wilds);
        }
        else {
            newGame = new Game(numPlayers, numDice);
        }

        return newGame;

    }

}
