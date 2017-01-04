package org.spdcalpoly.game;

import org.spdcalpoly.player.Player;

/**
 * game.java
 *
 * The game class is used to manage the rotating turns of each player in a game
 * of Liar's Dice. It also initializes the game and controls its termination.
 *
 * @author Alex Boyd
 * @since 09/08/2016
 * @version 1.0
 */
public class Game {


    // The input file to use when reading commands.
    private String inputFile;

    /**
     * Mutator for the input filename.
     * @param inputFile the input filename to use for game dialog.
     */
    public void setInputFile(String inputFile) {
        this.inputFile = inputFile;
    }


    // The output file to use when responding to commands.
    private String outputFile;

    /**
     * Mutator for the output filename.
     * @param outputFile the output filename to use for game dialog.
     */
    public void setOutputFile(String outputFile) {
        this.outputFile = outputFile;
    }

    // The dialog manager used to handle all game dialog.
    private DialogManager dm;

    // The data object used to store and update all game data.
    private GameData data;

    // The dice count used at the beginning of a game.
    private int startingDiceCount;

    /**
     * Empty constructor, initializes everything to null.
     */
    public Game () {
        inputFile = null;
        outputFile = null;
        dm = null;
        data = null;
    }

    /**
     * Start a new game.
     */
    public void startGame() {
        Player currentPlayer;
        for (Player p : data.getPlayers()) {
            p.setDiceCount(startingDiceCount);
        }
        do {
            currentPlayer = data.getNextPlayer();
            currentPlayer.takeTurn(dm, data);
        } while (data.getTotalDice() > 0 && currentPlayer.getDiceCount() != -1);
    }

    /**
     * Add a player to the data object list.
     * @param p the player to add.
     */
    public void addPlayer(Player p) {
        data.getPlayers().add(p);
    }

    /**
     * Add a player to a specific index in the data object list.
     * @param p the player to add.
     * @param index the position in the list to add the player to.
     */
    public void addPlayer(Player p, int index) {
        data.getPlayers().add(index, p);
    }

    /**
     * Setup a game with a friendly message and some starting information.
     */
    public void setupGame() {
        dm = new DialogManager(inputFile, outputFile);
        outputIntroduction();
        int numPlayers = dm.promptInteger("How many players are there? ");
        startingDiceCount = dm.promptInteger("How many dice does each player get to start? ");
        data = new GameData(numPlayers);
    }

    /**
     * Print a friendly introduction message.
     */
    private void outputIntroduction() {
        dm.println("**************************************************");
        dm.println("          Welcome to the Liar's Dice AI!          ");
        dm.println("**************************************************");
        dm.println("");
        dm.println("The AI is controlled by entering commands into the");
        dm.println("AI console. For a complete list of commands, enter");
        dm.println("the \"help\" command.");
        dm.println("");
    }

}