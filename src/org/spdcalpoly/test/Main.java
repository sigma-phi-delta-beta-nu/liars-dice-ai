package org.spdcalpoly.test;

import org.spdcalpoly.*;

/**
 * Main.java
 */
public class Main {

    public static void main(String[] args) {
        probabilityAITests();
        //integrationTests();
    }

    private static void probabilityAITests() {
        (new GameManager("src/org/spdcalpoly/test/probability-test-1.txt")).startAI();
    }

    private static void integrationTests() {
        (new GameManager("src/org/spdcalpoly/test/integration-tests.txt")).startAI();
    }

}
