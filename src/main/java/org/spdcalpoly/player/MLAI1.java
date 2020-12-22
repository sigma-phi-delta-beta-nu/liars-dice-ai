package org.spdcalpoly.player;

import org.neuroph.core.data.DataSet;
import org.neuroph.nnet.MultiLayerPerceptron;
import org.neuroph.nnet.learning.MomentumBackpropagation;
import org.spdcalpoly.game.GameData;

import java.util.ArrayList;
import java.util.Arrays;

/**
 * MLAI1.java
 *
 * The MLAI1 class represents the first AI designed to use machine learning.
 *
 * @author Brandon M. Kelley
 * @since 12/29/2016
 * @version 1.0
 */
public final class MLAI1 extends AI implements Verifiable {

    private static final int INPUTS_COUNT = 4;
    private static final int OUTPUTS_COUNT = 2;

    private static final int INTERMEDIATE_NEURONS = 20;
    private static final double LEARNING_RATE = 0.6;
    private static final double MAX_ERROR = 0.01;

    private static final int UNSAFE_OUTPUT = 4;
    private static final int SAFE_OUTPUT = 5;

    private MultiLayerPerceptron neuralNet;
    private double[] learningBuffer;
    private String[] trainingFiles;
    private boolean verify;

    public MLAI1() {
        super();
        neuralNet = new MultiLayerPerceptron(INPUTS_COUNT, INTERMEDIATE_NEURONS, OUTPUTS_COUNT);
        learningBuffer = null;
        trainingFiles = null;
        verify = false;
        MomentumBackpropagation learningRule = (MomentumBackpropagation) neuralNet.getLearningRule();
        learningRule.setLearningRate(LEARNING_RATE);
        learningRule.setMaxError(MAX_ERROR);
        train();
    }

    public MLAI1(boolean verify) {
        this();
        this.verify = verify;
    }

    @Override
    public String runAI(GameData data) {

        // Retrieve specific game data
        int numDiceCall = data.getNumDiceCall();
        int diceValCall = data.getDiceValCall();
        int totalDice = data.getTotalDice();
        int probabilityNumDice = numDiceCall;

        // Figure out which dice the AI has.
        ArrayList<Integer> aiDice = this.getDice();

        // Remove the dice that the AI already has from the calculation.
        for (int die : aiDice) {
            if (die == diceValCall) {
                probabilityNumDice -= 1;
                totalDice -= 1;
            }
        }

        double inputData[] = new double[] { probabilityNumDice, totalDice, aiDice.size(), data.getPrevPlayer().getDiceCount() };
        learningBuffer = Arrays.copyOf(inputData, INPUTS_COUNT + OUTPUTS_COUNT);

        neuralNet.setInput(inputData);
        neuralNet.calculate();
        double outputData[] = neuralNet.getOutput();

        learningBuffer[UNSAFE_OUTPUT] = outputData[0];
        learningBuffer[SAFE_OUTPUT] = outputData[1];

        System.out.println("Unsafe output: " + outputData[0]);
        System.out.println("Safe output: " + outputData[1]);

        if (learningBuffer[UNSAFE_OUTPUT] < learningBuffer[SAFE_OUTPUT]) {
            return this.incrementDiceFrom(numDiceCall, diceValCall, aiDice);
        }

        else if (learningBuffer[UNSAFE_OUTPUT] > learningBuffer[SAFE_OUTPUT]) {
            return "Call out the previous player";
        }

        return "Call exact";

    }

    @Override
    public void verifyOutput(boolean correct) {
        DataSet updateSet = new DataSet(INPUTS_COUNT, OUTPUTS_COUNT);
        if (learningBuffer == null || !verify) {
            return;
        }
        if (!correct && learningBuffer[UNSAFE_OUTPUT] < learningBuffer[SAFE_OUTPUT]) {
            learningBuffer[UNSAFE_OUTPUT] = 1.0;
            learningBuffer[SAFE_OUTPUT] = 0.0;
        }
        else if (!correct) {
            learningBuffer[UNSAFE_OUTPUT] = 0.0;
            learningBuffer[SAFE_OUTPUT] = 1.0;
        }
        else if (learningBuffer[UNSAFE_OUTPUT] < learningBuffer[SAFE_OUTPUT]) {
            learningBuffer[UNSAFE_OUTPUT] = 0.0;
            learningBuffer[SAFE_OUTPUT] = 1.0;
        }
        else {
            learningBuffer[UNSAFE_OUTPUT] = 1.0;
            learningBuffer[SAFE_OUTPUT] = 0.0;
        }
        updateSet.addRow(learningBuffer);
        neuralNet.learn(updateSet);
        learningBuffer = null;
    }

    @Override
    public boolean verifyOutput() {
        return verify;
    }

    private void train() {
        DataSet trainingSet;
        if (trainingFiles == null) {
            return;
        }
        for (String trainingFile : trainingFiles) {
            trainingSet = DataSet.createFromFile(trainingFile, INPUTS_COUNT, OUTPUTS_COUNT, "\t", false);
            neuralNet.learn(trainingSet);
        }
        trainingFiles = null;
    }

    public void setTrainingFiles(String[] trainingFiles) {
        this.trainingFiles = trainingFiles;
    }

}
