package org.spdcalpoly;

import net.sf.javaml.core.Dataset;

/**
 * AI.java
 *
 * The AI interface is used to define the basic functionality of an AI. Its
 * methods are called by the Game class when using the AI.
 *
 * @author Alex Boyd
 * @since 09/10/2016
 * @version 1.0
 */
public interface AI {

    /**
     * Read in training data from past collections.
     * @return A dataset of the training data.
     */
    Dataset generateData();

    /**
     * Stochastically update the data from the current game.
     */
    void updateData();

    /**
     * Run the current model and make a prediction.
     * @return The prediction string.
     */
    String runModel();

}
