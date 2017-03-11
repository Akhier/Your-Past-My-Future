package ypmf;

import java.awt.Color;

import asciiPanel.AsciiPanel;

public class FungusAi extends CreatureAi {
	private StuffFactory factory;
	private Creature fungus;
	private int spreadcount;
	
	public FungusAi(Creature creature, StuffFactory factory) {
		super(creature);
		this.factory = factory;
		this.fungus = creature;
	}

	public void onUpdate(){
		if (spreadcount <= 3 && MapGen.rng.nextInt(300) <= 1) {
			spread();
		}
	}
	
	private void spread(){
		int x = creature.x + MapGen.rng.nextInt(9) - 4;
		int y = creature.y + MapGen.rng.nextInt(9) - 4;
		
		if (!creature.canEnter(x, y, creature.z)) {
			return;
		}
		creature.doAction("spawn a child");
		Creature child = factory.newFungus(creature.z);
		child.x = x;
		child.y = y;
		child.z = creature.z;
		spreadcount++;
		if(spreadcount == 1) {
			fungus.setColor(AsciiPanel.yellow);
		} else if(spreadcount == 2) {
			fungus.setColor(new Color(150, 75, 0));   //Brown
		} else if(spreadcount == 3) {
			fungus.setColor(new Color(101, 67, 33));   //Dark Brown
		}
	}
}
