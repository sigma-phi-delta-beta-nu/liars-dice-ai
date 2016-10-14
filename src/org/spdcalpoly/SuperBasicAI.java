package org.spdcalpoly;

/**
 * SuperBasicAI.java
 */
public class SuperBasicAI implements AI {


    @Override
    public void updateData() {
        // Do nothing
    }

    @Override
    public String runAI(int numDice, int diceVal) {
        return (numDice == diceVal) ? "Equals" : "Bullshit";
    }

}
