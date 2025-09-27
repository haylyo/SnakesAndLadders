package ui;

import core.GameLogic;
import java.util.Scanner;

public class GameConsoleUI {

 private GameLogic game;
 private Scanner scnr;
 private int gameType;

 public GameConsoleUI(Scanner scnr) {
	 this.scnr = scnr;
 }

 /**
  * Begins the game and displays the board, then calls
  * promptRoll(), playTurn(), and displayBoard() until a winner is determined.
  * {@link #promptRoll()}
  */
 public void startGame() throws InterruptedException {
		System.out.println("Would you like to play against:\n[1] A computer\n[2] Another player");
		try {
			gameType = Integer.parseInt(scnr.nextLine());
			if (gameType != 1 && gameType != 2) {
				throw new IllegalArgumentException();
			}
		}
		catch (Exception e) {
			System.out.println("Invalid response. Exiting...");
			System.exit(0);
		}
		game = new GameLogic(gameType);
		System.out.println("Begin Game.");
		game.displayBoard();
		while (!game.winner()) {
			promptRoll();
			System.out.println(game.playTurn());
			game.displayBoard();
		}
		System.out.println(game.whosTurn() + " has won!");
	}

	/**
	 * Called by startGame(), prompts player to take their turn.
	 */
	public void promptRoll() throws InterruptedException {
		System.out.print(game.whosTurn() + " - your turn. Press the <<Enter>> key to Roll the dice.");
		if (!game.whosTurn().equals("Computer")) { // Real player, wait for enter key
			scnr.nextLine();
		}
		else { // Simulate deciding (Computer Player)
			try {
				Thread.sleep(2500);
				System.out.println();
			}
			catch (InterruptedException e) {
				System.out.println(e.getMessage());
				System.exit(0);
			}
		}
	}
}
