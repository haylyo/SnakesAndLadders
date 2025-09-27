package core;

public class ComputerPlayer extends Player {

    ComputerPlayer() {
        super("Computer");
    }

    /**
     * Simulated computer player roll.
     * @param die
     * @return integer number rolled 1-6
     * @deprecated Not currently used, all user interaction handled in Main and GameConsoleUI
     */
    @Override
    public int roll(Die die) {
        try {
            Thread.sleep(2500); // simulate thinking
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
            System.exit(0);
        }
        return die.roll();
    }

    /**
     * @return true if computer player
     * @deprecated Currently unused.
     */
    @Override
    public boolean isComputer() {
        return true;
    }
}
