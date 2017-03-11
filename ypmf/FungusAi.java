package ypmf;

public class FungusAi extends CreatureAi {
	private StuffFactory factory;
	private int spreadcount;
	
	public FungusAi(Creature creature, StuffFactory factory) {
		super(creature);
		this.factory = factory;
	}

	public void onUpdate(){
		if (spreadcount < 5 && MapGen.rng.nextInt(100) <= 1) {
			spread();
		}
	}
	
	private void spread(){
		int x = creature.x + MapGen.rng.nextInt(11) - 5;
		int y = creature.y + MapGen.rng.nextInt(11) - 5;
		
		if (!creature.canEnter(x, y, creature.z)) {
			return;
		}
		creature.doAction("spawn a child");
		Creature child = factory.newFungus(creature.z);
		child.x = x;
		child.y = y;
		child.z = creature.z;
		spreadcount++;
	}
}
