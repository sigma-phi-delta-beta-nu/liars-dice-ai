package org.spdcalpoly;

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

    // The number of dice the AI currently has.
    private int aiDiceCount;

    /**
     * This constructor initializes all game data without using wilds.
     * @param numPlayers The number of players in the game.
     * @param numDice The number of dice each player starts with.
     */
    BasicGame(int numPlayers, int numDice) {
        aiDiceCount = numDice;
    }

    /**
     * This constructor initializes all game data using wilds.
     * @param numPlayers The number of players in the game.
     * @param numDice The number of dice each player starts with.
     * @param wildValues The dice values used as wild.
     */
    BasicGame(int numPlayers, int numDice, int[] wildValues) {
        this(numPlayers, numDice);
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

        // Decrement number of dice AI has so the game will end eventually.
        aiDiceCount -= 1;

    }

}
