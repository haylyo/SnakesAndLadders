package core;

public class Player {

	private String name;
	private int pos;

	Player() {
		pos = 1;
		name = "Player";
	}
	Player(String str) {
		pos = 1;
		name = str;
	}

	/**
	 * Real player roll
	 * @param die
	 * @return integer rolled 1-6
	 * @deprecated currently unused, all UI handled in ui package.
	 */
	public int roll(Die die) {
		// scnr.nextLine();
		return die.roll();
	}

	/**
	 * Returns player's current position (1-100).
	 * @return pos
	 */
	public int getPos() {
		return pos;
	}

	/**
	 * Sets player's current positon (1-100).
	 * @param newPos An integer 1-100.
	 */
	public void setPos(int newPos) {
		this.pos = newPos;
	}

	/**
	 * @return false if real player
	 * @deprecated currently unused
	 */
	public boolean isComputer() {
		return false;
	}

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
