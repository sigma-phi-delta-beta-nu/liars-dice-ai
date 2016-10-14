package org.spdcalpoly.test;

import org.spdcalpoly.*;

/**
 * Main.java
 */
public class Main {

    public static void main(String[] args) {
        System.out.println("Hello world!");
        (new GameManager("src/org/spdcalpoly/test/integration-tests.txt")).startAI();
    }

}
