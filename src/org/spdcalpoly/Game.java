package org.spdcalpoly;

import java.util.ArrayList;
import java.util.Collections;

/**
 * Game.java
 *
 * The BasicGame class is used to manage all data related to a game of Liar's
 * Dice. It is created at the beginning of each game by the GameManager.
 *
 * @author Brandon M. Kelley
 * @since 09/08/2016
 * @version 1.0
 */
class Game {

    enum InstanceIndices {

    }

    // The dialog manager used to communicate with the AI player.
    private DialogManager dialogManager;

    // The number of players still playing in the game
    private int numPlayers;

    // The number of dice each player has
    private ArrayList<Integer> playerHandSizes;

    // The AI position within the players
    private int aiPosition;

    // The total number of dice still left in the game.
    private int totalDiceCount;

    // The dice numbers the AI player is currently holding.
    private ArrayList<Integer> aiDice;

    // Whether wilds are allowed
    private boolean usingWilds;

    // The dice numbers that are considered wild.
    private ArrayList<Integer> wilds;

    // AI object that will be predicting
    private AI beepboop;

    // Position of the player who's turn it is currently
    private int currentPlayer;

    /**
     * This constructor initializes all game data without using wilds.
     * @param players The number of players in the game.
     * @param numDice The number of dice each player starts with.
     */
    Game(DialogManager dm, int players, int numDice, AI robot, int robotPosition, int playerStart) {
        dialogManager = dm;
        numPlayers = players;
        playerHandSizes = new ArrayList<Integer>(Collections.nCopies(numPlayers, numDice));
        aiPosition = robotPosition;
        beepboop = robot;
        totalDiceCount = numPlayers * numDice;
        aiDice = new ArrayList<Integer>();
        usingWilds = false;
        wilds = new ArrayList<Integer>();
        if (playerStart >= players) {
            playerStart = players - 1;
        }
        else if (playerStart < 0)
        {
            playerStart = 0;
        }
        currentPlayer = playerStart;
    }

    /**
     * This constructor initializes all game data using wilds.
     * @param numPlayers The number of players in the game.
     * @param numDice The number of dice each player starts with.
     * @param wildValues The dice values used as wild.
     */
    Game(DialogManager dm, int numPlayers, int numDice, ArrayList<Integer> wildValues, AI robot, int robotPosition, int playerStart) {
        this(dm, numPlayers, numDice, robot, robotPosition, playerStart);
        usingWilds = true;
        wilds.addAll(wildValues);
    }

    /**
     * Determines if this game is still playable.
     * @return Whether the game is still playable.
     */
    public boolean isPlayable() {

        // As long as the AI has dice, it can still play.
        return aiPosition != -1 && playerHandSizes.size() > 1;

    }

    /**
     * Allows this game to handle the commands that the AI player enters.
     * @param command The command the AI player entered.
     */
    public void processCommand(String command) {

        String whichPlayer;

        switch (command) {

            case "h":
            case "help":
                outputHelp();
                break;

            case "c":
            case "call":
                int numDice = dialogManager.promptInteger("Enter number of dice called: ");
                int diceVal = dialogManager.promptInteger("Enter the value of dice called: ");

                currentPlayer++;
                break;

            case "p":
            case "pass":
                currentPlayer++;
                break;

            case "l":
            case "lost a die":
                whichPlayer = dialogManager.promptString("Last or current player? ");
                switch (whichPlayer) {

                    case "l":
                    case "last player":
                    case "last":
                        playerHandSizes.set(currentPlayer - 1, playerHandSizes.get(currentPlayer - 1) - 1);
                        break;

                    case "c":
                    case "current player":
                    case "current":
                        playerHandSizes.set(currentPlayer, playerHandSizes.get(currentPlayer) - 1);
                        break;
                }

                totalDiceCount -= 1;
                break;

            case "g":
            case "gain a die":
                whichPlayer = dialogManager.promptString("Last or current player? ");
                switch (whichPlayer) {

                    case "l":
                    case "last player":
                    case "last":
                        playerHandSizes.set(currentPlayer - 1, playerHandSizes.get(currentPlayer - 1) + 1);
                        break;

                    case "c":
                    case "current player":
                    case "current":
                        playerHandSizes.set(currentPlayer, playerHandSizes.get(currentPlayer) + 1);
                        break;
                }

                totalDiceCount += 1;
                break;

            case "r":
            case "roll":
                aiDice.clear();
                aiDice = dialogManager.promptIntegerArray("Enter the numbers rolled (delimited by spaces) ",
                        playerHandSizes.get(aiPosition), true);
                break;

            case "your turn":
                dialogManager.println("You should say: " + beepboop.runAI());
                currentPlayer++;
                break;

            default:
                dialogManager.println("Command not found. Please try again.");
                break;

        }

        // TODO: account for when a player is out of the game here
        // TODO: account for when there is a single player left who is the winner
        if (currentPlayer == numPlayers) {
            currentPlayer = 0;
        }

        // Save current player hands and AI position
        ArrayList<Integer> oldHands = playerHandSizes;
        int oldAiPosition = aiPosition;

        // Generate a new list of player hands after checking if all players
        // have a hand still.
        playerHandSizes = new ArrayList<Integer>();
        for (int i = 0; i < oldHands.size(); i++) {
            if (oldHands.get(i) == 0) {
                numPlayers -= 1;
                if (i < oldAiPosition) {
                    aiPosition -= 1;
                }
                else if (i == oldAiPosition) {
                    aiPosition =- 1;
                }
            }
            else {
                playerHandSizes.add(oldHands.get(i));
            }
        }

    }

    private void outputHelp() {
        dialogManager.println("Available commands:");
        dialogManager.println("  help [h]");
        dialogManager.println("  call [c]");
        dialogManager.println("  pass [p]");
        dialogManager.println("  lost a die [l]");
        dialogManager.println("  gain a die [g]");
        dialogManager.println("  roll [r]");
        dialogManager.println("  your turn\n");
    }
}
