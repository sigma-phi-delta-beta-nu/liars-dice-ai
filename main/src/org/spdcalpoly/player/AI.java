package org.spdcalpoly.player;

import org.spdcalpoly.game.DialogManager;
import org.spdcalpoly.game.GameData;

import java.util.ArrayList;
import java.util.HashMap;

public abstract class AI extends Player {

    private ArrayList<Integer> dice;
    public ArrayList<Integer> getDice() {
        return dice;
    }

    public AI() {
        super();
        dice = new ArrayList<Integer>();
    }

    @Override
    public boolean processCommand(String input, DialogManager dm, GameData data) {
        switch (input) {
            case "r":
            case "roll":
                dice = dm.promptIntegerArray("Enter the numbers rolled (delimited by spaces) ",
                        this.getDiceCount(), true);
                return false;
            case "s":
            case "suggest":
                dm.println("You should: " + runAI(data));
                return true;
            default:
                return super.processCommand(input, dm, data);
        }
    }

    public abstract String runAI(GameData data);

    /**
     * Calculate what combination of dice is logical to call after the previous
     * call.
     * @param numDice The number of dice the previous player called.
     * @param diceVal The value of the dice the previous player called.
     * @param currentDice The dice the AI has.
     * @return The call that the AI should make.
     */
    protected String incrementDiceFrom(int numDice, int diceVal, ArrayList<Integer> currentDice) {

        // Read in the AI's current dice into a map of dice values -> count.
        HashMap<Integer, Integer> diceCounts = new HashMap<Integer, Integer>();
        int maxDieCount;
        for (int die : currentDice) {
            if (diceCounts.containsKey(die)) {
                diceCounts.put(die, diceCounts.get(die) + 1);
            }
            else {
                diceCounts.put(die, 1);
            }
        }

        // If the dice value alone can be incremented, do so.
        if (diceVal < 6) {
            maxDieCount = determineDieWithMaxOccurrence(diceCounts, diceVal + 1, 6);
            if (maxDieCount != -1) {
                return "Say " + numDice + " " + maxDieCount + "s";
            }
        }

        // Increment the number of dice by one and use the dice value that
        // occurs most frequently.
        maxDieCount = determineDieWithMaxOccurrence(diceCounts, 1, 6);
        return "Say " + (numDice + 1) + " " + maxDieCount + "s";

    }

    /**
     * Determine which die value the AI has the most of.
     * @param diceCounts Dice counts the AI has.
     * @param start The starting dice value to search.
     * @param end The ending dice value to search.
     * @return The die value that the AI has the most of.
     */
    private int determineDieWithMaxOccurrence(HashMap<Integer, Integer> diceCounts, int start, int end) {
        int maxOccurrences = -1;
        for (int die = start; die <= end; die++) {
            if (diceCounts.containsKey(die)) {
                if (diceCounts.get(die) > maxOccurrences) {
                    maxOccurrences = die;
                }
            }
        }
        return maxOccurrences;
    }

}
