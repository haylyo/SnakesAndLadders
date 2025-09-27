package ui;

import java.util.Scanner;

public class Main {

    public static void main(String[] args) throws InterruptedException {
        Scanner scnr = new Scanner(System.in);
        boolean again = false;
        do {
            GameConsoleUI ui = new GameConsoleUI(scnr);
            ui.startGame();
            System.out.print("Play again? (y/n) - ");
            try {
                String ans = scnr.nextLine().trim();
                if (ans.equalsIgnoreCase("y")) {
                    again = true;
                }
                else if (ans.equalsIgnoreCase("n")) {
                    again = false;
                }
                else {
                    throw new Exception();
                }
            }
            catch (Exception e) {
                System.out.println("Invalid input. Exiting program...");
                again = false;
            }
        } while (again); // Repeat as long as player indicates they want to continue playing.
    }
}
