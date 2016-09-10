package org.spdcalpoly.data;

import java.io.File;
import java.io.FileNotFoundException;
import java.io.PrintStream;

/**
 * GenerateData.java
 *
 * The GenerateData class is used to create data sets for training AI models.
 *
 * @author Brandon Kelley
 * @since 09/10/2016
 * @version 1.0
 */
public class GenerateData {

    public static void main(String args[]) {

        generateCSV("C:\\Users\\bkell\\Desktop\\code\\projects\\java\\liars-dice-ai\\src\\org\\spdcalpoly\\data\\basic-data.csv");

    }

    private static void generateCSV(String path) {

        PrintStream writer = null;

        try {
            writer = new PrintStream(new File(path));
        }
        catch (FileNotFoundException e) {
            e.printStackTrace();
            System.exit(1);
        }

        int minPlayers = 2;
        int maxPlayers = 15;
        int minDice = 1;
        int maxDice = 10;

        writer.println("AI Dice Count, Total Dice Count, Active Player Count, Starting Dice, Starting Players");

        for (int startingPlayers = minPlayers; startingPlayers <= maxPlayers; startingPlayers++) {
            for (int startingDice = minDice; startingDice <= maxDice; startingDice++) {
                for (int activePlayers = minPlayers; activePlayers <= startingPlayers; activePlayers++) {
                    for (int aiDice = minDice; aiDice <= startingDice; aiDice++) {
                        for (int totalDice = activePlayers + aiDice - 1; totalDice <= activePlayers * startingDice; totalDice++) {
                            writer.println(aiDice + "," + totalDice + ","
                                    + activePlayers + "," + startingDice + ","
                                    + startingPlayers);
                        }
                    }
                }
            }
        }

    }

}
