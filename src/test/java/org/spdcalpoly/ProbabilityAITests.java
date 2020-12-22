package org.spdcalpoly;

import org.junit.jupiter.api.Test;
import org.spdcalpoly.game.Game;
import org.spdcalpoly.player.ProbabilityAI;

public class ProbabilityAITests {

    @Test
    void startsGame() {
        new Game() {{
            setInputFile("src/test/resources/probabilityai-test-1.txt");
            setupGame();
            addPlayer(new ProbabilityAI());
            startGame();
        }};
    }

}