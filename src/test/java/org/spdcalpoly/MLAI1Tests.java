package org.spdcalpoly;

import org.junit.jupiter.api.Test;
import org.spdcalpoly.game.Game;
import org.spdcalpoly.player.MLAI1;

public class MLAI1Tests {

    @Test
    void startsGameWithTrainingData() {
        MLAI1 ai = new MLAI1();
        ai.setTrainingFiles(new String[] { "src/test/resources/training-data/mlai1-training-1.tsv" });
        new Game() {{
            setInputFile("src/test/resources/mlai1-test-1.txt");
            setupGame();
            addPlayer(ai);
            startGame();
        }};
    }

}
