package org.spdcalpoly;

/**
 * Game.java
 *
 * The Game class is used to manage all data related to a game of Liar's
 * Dice. It is created at the beginning of each game by the GameManager.
 *
 * @author Brandon M. Kelley
 * @since 09/08/2016
 * @version 1.0
 */
class Game {

    // The number of values on a die.
    private static final int NUM_DIE_VALUES = 6;

    // The number of players in this round.
    private int numPlayers;

    // The total number of dice left in the game.
    private int totalDice;

    // A map of which die values are wild.
    private boolean[] wilds;

    // The AI's current dice.
    private int[] aiDice;

    // The number of dice the AI currently has.
    private int aiDiceCount;

    // The number of dice the previous player currently has.
    private int prevPlayerDiceCount;

    /**
     * This constructor initializes all game data without using wilds.
     * @param numPlayers The number of players in the game.
     * @param numDice The number of dice each player starts with.
     */
    Game(int numPlayers, int numDice) {
        this.numPlayers = numPlayers;
        this.totalDice = numDice * numPlayers;
        wilds = new boolean[NUM_DIE_VALUES];
        aiDice = new int[numDice];
        aiDiceCount = numDice;
        prevPlayerDiceCount = numDice;
    }

    /**
     * This constructor initializes all game data using wilds.
     * @param numPlayers The number of players in the game.
     * @param numDice The number of dice each player starts with.
     * @param wildValues The dice values used as wild.
     */
    Game(int numPlayers, int numDice, int[] wildValues) {
        this(numPlayers, numDice);
        for (int wildValue : wildValues) {
            if (wildValue != -1) {
                this.wilds[wildValue] = true;
            }
        }
    }

}
