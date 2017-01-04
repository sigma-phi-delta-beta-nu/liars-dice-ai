import org.spdcalpoly.game.Game;
import org.spdcalpoly.player.MLAI1;

/**
 *
 */
public class MLAI1Tests {

    public static void main(String[] args) {
        MLAI1 ai = new MLAI1();
        ai.setTrainingFiles(new String[] { "res/training-data/mlai1-training-1.tsv" });
        new Game() {{
            setInputFile("res/mlai1-test-1.txt");
            setupGame();
            addPlayer(ai);
            startGame();
        }};
    }

}
