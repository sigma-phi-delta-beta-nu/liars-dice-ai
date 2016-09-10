package org.spdcalpoly;

import net.sf.javaml.core.Dataset;

/**
 * Created by Alex on 9/10/2016.
 */
public interface AI {

    // Read in training data from past collections
    Dataset generateData();

    // Stochastically update the data from the current game
    void updateData();

    // Run the current model and make a prediction
    String runModel();

}
