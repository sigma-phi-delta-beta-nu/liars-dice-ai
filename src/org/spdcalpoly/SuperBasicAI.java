package org.spdcalpoly;

/**
 * SuperBasicAI.java
 */
public class SuperBasicAI implements AI {

    private Game game;

    @Override
    public void setGame(Game game) {
        this.game = game;
    }

    @Override
    public String runAI() {
        return "Do something";
    }
}
