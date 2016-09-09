package org.spdcalpoly;

/**
 * Main.java
 *
 * The Main class is used to start the AI. The rest is handled by the
 * GameManager.
 *
 * @author Brandon M. Kelley
 * @since 09/08/2016
 * @version 1.0
 */
public class Main {

    /**
     * This is the entry point of the executable. Not much to see here.
     * @param args Command line arguments, not used for this program.
     */
    public static void main(String[] args) {

        // Start the AI
        (new GameManager()).startAI();

    }

}
