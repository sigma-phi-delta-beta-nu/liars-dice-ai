import org.spdcalpoly.game.Game;
import org.spdcalpoly.player.ProbabilityAI;

public class ProbabilityAITests {

    public static void main(String[] args) {
        new Game() {{
            setInputFile("res/probabilityai-test-1.txt");
            setupGame();
            addPlayer(new ProbabilityAI());
            startGame();
        }};
    }

}