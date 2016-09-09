package org.spdcalpoly;

/**
 * Game.java
 *
 * The Game interface is used to set basic guidelines for how the GameManager
 * will interact with an game of Liar's Dice. It is used primarily to allow
 * different data to be collected in commands.
 *
 * @author Brandon Kelley
 * @since 09/09/2016
 * @version 1.0
 */
interface Game {

    /**
     * Determines if the game is still playable.
     * @return Whether the game is still playable.
     */
    boolean isPlayable();

    /**
     * Allows the game to handle the commands that the AI player enters.
     * @param command The command the AI player entered.
     */
    void processCommand(String command);

}
