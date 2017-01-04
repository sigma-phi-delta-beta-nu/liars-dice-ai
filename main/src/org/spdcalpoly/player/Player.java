package org.spdcalpoly.player;

import org.spdcalpoly.game.GameData;
import org.spdcalpoly.game.DialogManager;

public class Player {

    private int diceCount;
    public void setDiceCount(int diceCount) {
        this.diceCount = diceCount;
    }
    public int getDiceCount() {
        return diceCount;
    }
    public void addDie() {
        diceCount += 1;
    }
    public void removeDie() {
        diceCount -= 1;
    }

    public void takeTurn(DialogManager dm, GameData data) {
        String response;
        do {
            dm.println("");
            response = dm.promptString(this.getClass().getSimpleName()
                    + (this.hashCode() % 1000) + "> ");
        } while (!processCommand(response, dm, data));
    }

    public boolean processCommand(String input, DialogManager dm, GameData data) {
        switch (input) {
            case "h":
            case "help":
                outputHelp(dm);
                return false;
            case "c":
            case "call":
                if (dm.promptBoolean("Were you right? ")) {
                    data.getPrevPlayer().removeDie();
                }
                else {
                    diceCount -= 1;
                }
                return true;
            case "e":
            case "exact":
                if (dm.promptBoolean("Were you right? ")) {
                    diceCount += 1;
                }
                else {
                    diceCount -= 1;
                }
                return true;
            case "p":
            case "pass":
                return true;
            case "n":
            case "next":
                data.setNumDiceCall(dm.promptInteger("Enter number of dice called: "));
                data.setDiceValCall(dm.promptInteger("Enter the value of dice called: "));
                return true;
            case "q":
            case "quit":
                diceCount = -1;
                return true;
            default:
                dm.println("Command '" + input + "' is not supported.");
                outputHelp(dm);
                return false;
        }
    }

    private void outputHelp(DialogManager dm) {
        dm.println("Available commands:");
        dm.println("  help  [h]");
        dm.println("  call  [c]");
        dm.println("  exact [e]");
        dm.println("  next  [n]");
        dm.println("  pass  [p]");
        dm.println("  quit  [q]");
    }

}
