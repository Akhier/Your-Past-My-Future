package components;

public class Creature {
	public int curHp;
	private int maxHp, baseDefense, baseStrength, baseAgility, visionRange;
	public Creature(int hp, int def, int str, int agi, int vision) {
		maxHp = hp;
		curHp = hp;
		baseDefense = def;
		baseStrength = str;
		baseAgility = agi;
		visionRange = vision;
	}

	public int defense() { return baseDefense; }

	public int strength() { return baseStrength; }

	public int agility() { return baseAgility; }

	public int vision() { return visionRange; }

	public int maxHp() { return maxHp; }
}
