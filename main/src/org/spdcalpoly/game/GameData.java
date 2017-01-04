package org.spdcalpoly.game;

import org.spdcalpoly.player.Player;

import java.util.ArrayList;

/**
 * GameData.java
 *
 * The GameData class is used to store and update data used to play the Liar's
 * Dice game.
 */

public class GameData {

    // The list of players (including AIs) playing the game.
    private ArrayList<Player> players;

    /**
     * Accessor for list of players.
     * @return an ArrayList of the players playing the game.
     */
    ArrayList<Player> getPlayers() {
        return players;
    }

    /**
     * Get the current dice count of all players in the game.
     * @return
     */
    public int getTotalDice() {
        int total = 0;
        for (Player player : players) {
            total += player.getDiceCount();
        }
        return total;
    }

    // The number of dice stated by the last player.
    private int numDiceCall;

    /**
     * Accessor for the number of dice stated by the last player.
     * @return the number of dice stated by the last player.
     */
    public int getNumDiceCall() {
        return numDiceCall;
    }

    /**
     * Mutator for the number of dice stated by the last player.
     * @param numDiceCall the new number of dice.
     */
    public void setNumDiceCall(int numDiceCall) {
        this.numDiceCall = numDiceCall;
    }

    // The dice value stated by the last player.
    private int diceValCall;

    /**
     * Accessor for the dice value stated by the last player.
     * @return the dice value stated by the last player.
     */
    public int getDiceValCall() {
        return diceValCall;
    }

    /**
     * Mutator for the dice value stated by the last player.
     * @param diceValCall the new dice value.
     */
    public void setDiceValCall(int diceValCall) {
        this.diceValCall = diceValCall;
    }

    // The position of the current player in the rotation.
    private int currentPlayer;
    public Player getPrevPlayer() {
        return players.get((currentPlayer - 1) % players.size());
    }

    /**
     * Retrieve the next player to go in the game.
     * @return the next player.
     */
    Player getNextPlayer() {
        Player nextPlayer;
        do {
            nextPlayer = players.get(++currentPlayer % players.size());
        } while (nextPlayer.getDiceCount() == 0);
        return nextPlayer;
    }

    /**
     * Constructor that adds a specific number of non-AI players to the game.
     * @param numPlayers the number of non-AI players to add to the game.
     */
    GameData(int numPlayers) {
        players = new ArrayList<Player>();
        while (numPlayers-- > 0) {
            players.add(new Player());
        }
        currentPlayer = -1;
    }

}
