package org.spdcalpoly;

import java.util.ArrayList;

/**
 * BasicGame.java
 *
 * The BasicGame class is used to manage all data related to a game of Liar's
 * Dice. It is created at the beginning of each game by the GameManager.
 *
 * @author Brandon M. Kelley
 * @since 09/08/2016
 * @version 1.0
 */
class BasicGame implements Game {

    enum InstanceIndices {

    }

    // The dialog manager used to communicate with the AI player.
    private DialogManager dialogManager;

    // The number of dice the AI player currently has.
    private int aiDiceCount;

    // The number of dice the player before the AI player currently has.
    private int prevPlayerDiceCount;

    // The total number of dice still left in the game.
    private int totalDiceCount;

    // The dice numbers the AI player is currently holding.
    private ArrayList<Integer> aiDice;

    // Whether wilds are allowed
    private boolean usingWilds;

    // The dice numbers that are considered wild.
    private ArrayList<Integer> wilds;

    /**
     * This constructor initializes all game data without using wilds.
     * @param numPlayers The number of players in the game.
     * @param numDice The number of dice each player starts with.
     */
    BasicGame(DialogManager dm, int numPlayers, int numDice) {
        dialogManager = dm;
        aiDiceCount = numDice;
        prevPlayerDiceCount = numDice;
        totalDiceCount = numPlayers * numDice;
        aiDice = new ArrayList<Integer>();
        usingWilds = false;
        wilds = new ArrayList<Integer>();
    }

    /**
     * This constructor initializes all game data using wilds.
     * @param numPlayers The number of players in the game.
     * @param numDice The number of dice each player starts with.
     * @param wildValues The dice values used as wild.
     */
    BasicGame(DialogManager dm, int numPlayers, int numDice, ArrayList<Integer> wildValues) {
        this(dm, numPlayers, numDice);
        usingWilds = true;
        wilds.addAll(wildValues);
    }

    /**
     * Determines if this game is still playable.
     * @return Whether the game is still playable.
     */
    public boolean isPlayable() {

        // As long as the AI has dice, it can still play.
        return aiDiceCount != 0;

    }

    /**
     * Allows this game to handle the commands that the AI player enters.
     * @param command The command the AI player entered.
     */
    public void processCommand(String command) {

        switch (command) {

            case "help":
                outputHelp();
                break;

            case "ai lost a die":
                aiDiceCount -= 1;
                totalDiceCount -= 1;
                break;

            case "ai gained a die":
                aiDiceCount += 1;
                totalDiceCount += 1;

            case "previous player lost a die":
                prevPlayerDiceCount -= 1;
                totalDiceCount -= 1;
                break;

            case "previous player gained a die":
                prevPlayerDiceCount += 1;
                totalDiceCount += 1;
                break;

            case "player lost a die":
                totalDiceCount -= 1;
                break;

            case "player gained a die":
                totalDiceCount += 1;
                break;

            case "roll":
                aiDice.clear();
                aiDice = dialogManager.promptIntegerArray("Enter the numbers rolled (delimited by spaces) ",
                        aiDiceCount, true);
                break;

            //case "your turn":
            //    dialogManager.println("You should say: " + runModel());
            //    break;

        }

    }

    private void outputHelp() {
        dialogManager.println("Available commands:");
        dialogManager.println("  help");
        dialogManager.println("  ai lost a die");
        dialogManager.println("  ai gained a die");
        dialogManager.println("  previous player lost a die");
        dialogManager.println("  previous player gained a die");
        dialogManager.println("  player lost a die");
        dialogManager.println("  player gained a die");
        dialogManager.println("  roll");
        dialogManager.println("  your turn\n");
    }
}
