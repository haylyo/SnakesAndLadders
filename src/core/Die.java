package core;
import java.util.Random;
public class Die {

	private Random rand;

	Die() {
		rand = new Random();
	}

	/**
	 * Returns a random integer 1-6 representing a die roll.
	 * @return random integer 1-6
	 */
	public int roll() {
		return rand.nextInt(6) + 1;
	}

}
