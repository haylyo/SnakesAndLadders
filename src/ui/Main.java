package ui;

import java.util.Scanner;

public class Main {

    /**
     * Offers (in the console) choice between Console-based or GUI game experience.
     * If console is selected, also handles offering another game.
     * @param args
     * @throws InterruptedException From Thread.sleep() in inner code for computer player.
     */
    public static void main(String[] args) throws InterruptedException {
        Scanner scnr = new Scanner(System.in);
        boolean again = false;
        do {
            System.out.print("Choose interface (1 = Console, 2 = GUI): ");
            String choice = scnr.nextLine().trim();
            if (choice.equals("1")) {
                GameConsoleUI ui = new GameConsoleUI(scnr);
                ui.startGame();
            }
            else if (choice.equals("2")){
                GameGUI.main(args);
                return;
            }
            else {
                System.out.println("Invalid choice. Defaulting to console.");
                GameConsoleUI ui = new GameConsoleUI(scnr);
                ui.startGame();
            }
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
