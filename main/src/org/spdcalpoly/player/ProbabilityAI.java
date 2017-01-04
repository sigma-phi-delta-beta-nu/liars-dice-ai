package org.spdcalpoly.player;

import org.spdcalpoly.game.GameData;

import java.util.ArrayList;
import java.util.HashMap;

/**
 * ProbabilityAI.java
 *
 * The ProbabilityAI class is an implementation of an AI that uses probability
 * calculation to predict what action to take on the AI's turn.
 *
 * @author Brandon M. Kelley
 * @since 12/29/2016
 * @version 1.0
 */

public final class ProbabilityAI extends AI {

    // Used as the threshold to determine whether the AI should call out
    // a previous player based on game data.
    private static final double CALL_THRESHOLD = 0.25;

    // Used as the threshold to determine whether the AI should call exact.
    private static final double EXACT_THRESHOLD = 0.005;

    // Factorial data to improve the speed of probability calculations.
    private HashMap<Double, Double> factorials;

    /**
     * Empty constructor.
     */
    public ProbabilityAI() {
        super();
        factorials = new HashMap<Double, Double>();
    }

    /**
     * Make a decision on what the AI player should do.
     * @param data Information about the state of the game.
     * @return The action to take as an AI turn.
     */
    @Override
    public String runAI(GameData data) {

        // Retrieve call information from the previous player's turn.
        int numDiceCall = data.getNumDiceCall();
        int diceValCall = data.getDiceValCall();

        // Track the actual numbers to calculate probability with.
        int probabilityNumDice = numDiceCall;
        int probabilityTotalDice = data.getTotalDice();

        // Figure out which dice the AI has.
        ArrayList<Integer> aiDice = this.getDice();

        // Remove the dice that the AI already has from the calculation.
        for (int die : aiDice) {
            if (die == diceValCall) {
                probabilityNumDice -= 1;
                probabilityTotalDice -= 1;
            }
        }

        // If the AI had enough dice to validate the call, just increment the
        // previous call.
        if (probabilityNumDice <= 0) {
            return this.incrementDiceFrom(numDiceCall, diceValCall, aiDice);
        }

        // Calculate the probability.
        double probability = findProbability(probabilityNumDice, probabilityTotalDice);

        // If the probability is just too low, call them out.
        if (probability < CALL_THRESHOLD - EXACT_THRESHOLD) {
            return "Call out the previous player";
        }

        // If the probability is right within the exact threshold, call exact.
        else if (probability < CALL_THRESHOLD + EXACT_THRESHOLD) {
            return "Call exact";
        }

        // Otherwise, just increment the previous call.
        return this.incrementDiceFrom(numDiceCall, diceValCall, aiDice);

    }

    /**
     * Calculate the probability that a certain number of dice that are all
     * the same could occur in a larger set of dice.
     * @param numDice The number of dice that are all the same.
     * @param totalDice The number of dice to choose from.
     * @return The probability of the occurrence.
     */
    private double findProbability(int numDice, int totalDice) {
        double allDiceValues = Math.pow(6, totalDice);
        double invalidProbability = 0;
        while (--numDice >= 0) {
            invalidProbability += nCr(totalDice, numDice) * Math.pow(5, totalDice - numDice) / allDiceValues;
        }
        return 1 - invalidProbability;
    }

    /**
     * Calculate the number of combinations of r items in a set of n.
     * @param n The size of the set.
     * @param r The number of items to choose.
     * @return The number of combinations.
     */
    private double nCr(int n, int r) {
        return factorial(n) / (factorial(r) * factorial(n - r));
    }

    /**
     * Compute a factorial.
     * @param num The number to find the factorial of.
     * @return the factorial.
     */
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
