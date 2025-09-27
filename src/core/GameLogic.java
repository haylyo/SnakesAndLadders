package core;

import ui.Table;

public class GameLogic {

	private boolean hasWinner;

	private Player p1;

	private Player p2;

	private Player currentPlayer;

	private Die die;

	private Table table;

	public GameLogic(int gameType) {
		if (gameType == 1) { // [1] Computer Player
			hasWinner = false;
			p1 = new Player("Player 1");
			p2 = new ComputerPlayer();
			currentPlayer = p1;
			die = new Die();
			table = new Table();
		}
		else { // [2] Another Player
			hasWinner = false;
			p1 = new Player("Player 1");
			p2 = new Player("Player 2");
			currentPlayer = p1;
			die = new Die();
			table = new Table();
		}
	}

	/**
	 * Displays game board as 10x10 of numbers separated by |.
	 * Will include identifiers in places where P1 and P2 are located,
	 * or "1&2" if both player occupy the same spot.
	 */
	public void displayBoard() {
		table.displayTable(p1.getPos(), p2.getPos());
	}

	/**
	 * Completes the next turn by simulating a die roll and updating the appropriate
	 * player's position and the overall game status.
	 * @return int representing the die number that was rolled (1-6).
	 */
	public int playTurn() {
		// int roll = currentPlayer.roll(die); // Currently handled in UI, may change later
		int roll = die.roll();
		int newPos = currentPlayer.getPos() + roll;
		if (newPos >= 100) { // Player wins
			currentPlayer.setPos(100);
			hasWinner = true;
			return roll;
		}
		if (table.isSnake(newPos)) { // Landed on snake head (move down to snake tail)
			newPos = table.snakeTail(newPos);
		}
		if (table.isLadder(newPos)) { // Landed on ladder base (move up to ladder head)
			newPos = table.ladderHead(newPos);
		}
		currentPlayer.setPos(newPos);
		currentPlayer = (currentPlayer == p1) ? p2 : p1; // other player's turn
		return roll; // for calling method to display what was rolled
	}

	/**
	 * Produces a string identifying the player whos turn it is.
	 * @return String Player1 or Player2
	 */
	public String whosTurn() {
		return currentPlayer.getName();
	}

	/**
	 * Returns true if the current game has a winner.
	 * @return boolean true if someone has won, false if not.
	 */
	public boolean winner() {
		return hasWinner;
	}

}
