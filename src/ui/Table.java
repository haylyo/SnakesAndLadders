package ui;

import java.util.Map;

public class Table {

	private final int[][] array;

private static final Map<Integer, Integer> snakes = Map.of( // head, tail
        97, 19,
        74, 36
);

private static final Map<Integer, Integer> ladders = Map.of( // base, head
        3, 25,
        27, 57
);

	public Table() {
		array = new int[][]{
				{100, 99, 98, 97, 96, 95, 94, 93, 92, 91},
				{81, 82, 83, 84, 85, 86, 87, 88, 89, 90},
				{80, 79, 78, 77, 76, 75, 74, 73, 72, 71},
				{61, 62, 63, 64, 65, 66, 67, 68, 69, 70},
				{60, 59, 58, 57, 56, 55, 54, 53, 52, 51},
				{41, 42, 43, 44, 45, 46, 47, 48, 49, 50},
				{40, 39, 38, 37, 36, 35, 34, 33, 32, 31},
				{21, 22, 23, 24, 25, 26, 27, 28, 29, 30},
				{20, 19, 18, 17, 16, 15, 14, 13, 12, 11},
				{1, 2, 3, 4, 5, 6, 7, 8, 9, 10}
		};
	}

	/**
	 * Checks if space is tail end of a snake.
	 * @param num The space number to be checked.
	 * @return boolean true if the space is the tail end of a snake.
	 */
	public static boolean isSnakeTail(int num) {
		return snakes.containsValue(num);
	}

	/**
	 * Checks if space is head of ladder.
	 * @param num The space number to be checked.
	 * @return boolean true if the space is the head of a ladder.
	 */
	public static boolean isLadderHead(int num) {
		return ladders.containsValue(num);
	}

	/**
	 * Displays the board in the console. Colors players blue, snakes red, and ladders green.
	 * @param p1 player 1 position
	 * @param p2 player 2 position
	 */
	public void displayTable(int p1, int p2) {
		int snakeHeads = 1;
		int snakeTails = snakes.values().size();
		int ladderBases = ladders.keySet().size();
		int ladderHeads = ladders.values().size();
		for (int r = 0; r < 10; r++) {
			for (int c = 0; c < 10; c++) {
				int pos = array[r][c];
				System.out.print("|");
				if ((p1 == p2) && pos == p1) {
					System.out.print(ColorConsole.colorText("1&2", ColorConsole.BLUE));
				}
				else if (pos == p1) {
					System.out.print(ColorConsole.colorText(" P1", ColorConsole.BLUE));
				}
				else if (pos == p2) {
					System.out.print(ColorConsole.colorText(" P2", ColorConsole.BLUE));
				}
				else if (snakes.containsKey((Integer) pos)) { // Snake Head
					System.out.print(ColorConsole.colorText(String.format(" S%d", snakeHeads), ColorConsole.RED));
					snakeHeads++;
				}
				else if (snakes.containsValue((Integer) pos)) { // Snake Tail
					System.out.print(ColorConsole.colorText(String.format(" T%d", snakeTails), ColorConsole.RED));
					snakeTails--;
				}
				else if (ladders.containsKey((Integer) pos)) { // Ladder Base
					System.out.print(ColorConsole.colorText(String.format(" L%d", ladderBases), ColorConsole.GREEN));
					ladderBases--;
				}
				else if (ladders.containsValue((Integer) pos)) { // Ladder Head
					System.out.print(ColorConsole.colorText(String.format(" H%d", ladderHeads), ColorConsole.GREEN));
					ladderHeads--;
				}
				else {
					System.out.printf("%3d", pos); // Normal number
				}
			}
			System.out.println("|"); // end of row
		}
	}

	/**
	 * Checks whether a spot is a ladder base.
	 * @param pos integer position to be checked
	 * @return boolean true if ladder base false if not
	 */
	public static boolean isLadder(int pos) {
		return ladders.containsKey(pos);
	}

	/**
	 * Checks whether a spot is a snake head.
	 * @param pos integer position to be checked
	 * @return boolean true if snake head false if not
	 */
	public static boolean isSnake(int pos) {
		return snakes.containsKey(pos);
	}

	/**
	 * Input snake head value to get the associated snake tail.
	 * @param head The integer position of the snake head
	 * @return The integer position of the associated snake tail.
	 */
	public int snakeTail(int head) {
		return snakes.get(head);
	}

	/**
	 * Input ladder base to get associated ladder head.
	 * @param base The integer position of the ladder base
	 * @return The integer position of the associated ladder head.
	 */
	public int ladderHead(int base) {
		return ladders.get(base);
	}

	/**
	 * Offers the ability to display colored text in the console.
	 * BLUE, RED, and GREEN available.
	 */
	public class ColorConsole {
		public static final String RESET = "\u001B[0m";
		public static final String BLUE = "\u001B[34m";
		public static final String RED = "\u001B[31m";
		public static final String GREEN = "\u001B[32m";

		/**
		 * Colors text in the console.
		 * @param text the string to be colored
		 * @param color the desired color (RED BLUE GREEN)
		 * @return a string with the text input colored + code for following text to be normal.
		 */
		public static String colorText(String text, String color) {
			return color + text + RESET;
		}
	}
}