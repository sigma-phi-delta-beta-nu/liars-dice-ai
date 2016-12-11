package org.spdcalpoly;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ProbabilityAI.java
 *
 * The ProbabilityAI class is used to make decisions based on probabilities
 * drawn from the game's data.
 *
 * @author Brandon Kelley
 * @since 09/10/2016
 * @version 1.0
 */
public class ProbabilityAI implements AI {

    private static final double CALL_THRESHOLD = 0.3;
    private static final double EXACT_THRESHOLD = 0.05;

    private Game game;
    private HashMap<Double, Double> factorials = new HashMap<Double, Double>();

    public void setGame(Game game) {
        this.game = game;
        this.factorials = new HashMap<Double, Double>();
    }

    /**
     * Run the AI and make a prediction.
     *
     * @return The prediction string.
     */
    @Override
    public String runAI() {

        int numDiceCall = game.getCurrentNumDiceCall();
        int diceValCall = game.getCurrentDiceValCall();
        int probabilityNumDice = numDiceCall;
        int probabilityTotalDice = game.getTotalDiceCount();
        ArrayList<Integer> aiDice = game.getAiDice();

        for (int die : aiDice) {
            if (die == diceValCall) {
                probabilityNumDice -= 1;
                probabilityTotalDice -= 1;
            }
        }

        if (probabilityNumDice <= 0) {
            return incrementFrom(numDiceCall, diceValCall, aiDice);
        }

        double allDiceValues = Math.pow(6, probabilityTotalDice);
        double invalidDiceValues = 0;

        while (--probabilityNumDice >= 0) {
            invalidDiceValues += nCr(probabilityTotalDice, probabilityNumDice);
        }

        double probability = invalidDiceValues / allDiceValues;

        if (probability < CALL_THRESHOLD - EXACT_THRESHOLD) {
            return "Call out the previous player";
        }
        else if (probability < CALL_THRESHOLD + EXACT_THRESHOLD) {
            return "Call exact";
        }
        return incrementFrom(numDiceCall, diceValCall, aiDice);

    }

    private String incrementFrom(int numDice, int diceVal, ArrayList<Integer> currentDice) {
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
        if (diceVal < 6) {
            maxDieCount = determineDieWithMaxOccurrence(diceCounts, diceVal, 6);
            if (maxDieCount != -1) {
                return "" + numDice + " " + maxDieCount + "s";
            }
        }
        maxDieCount = determineDieWithMaxOccurrence(diceCounts, 1, 6);
        return "" + (numDice + 1) + " " + maxDieCount + "s";
    }

    private int determineDieWithMaxOccurrence(HashMap<Integer, Integer> diceCounts, int start, int end) {
        int maxOccurrences = 0;
        for (int die = start; die <= end; die++) {
            if (diceCounts.containsKey(die)) {
                if (diceCounts.get(die) > maxOccurrences) {
                    maxOccurrences = die;
                }
            }
        }
        return (maxOccurrences == 0) ? -1 : maxOccurrences;
    }

    private double nCr(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    private double factorial(double num) {
        if (num <= 1) {
            return 1;
        }
        if (factorials.containsKey(num)) {
            return factorials.get(num);
        }
        double result = num * factorial(num - 1);
        factorials.put(num, result);
        return result;
    }

}
