package ypmf;

public class GoblinAi extends CreatureAi {
	private Creature player;

	public GoblinAi(Creature creature, Creature player) {
		super(creature);
		this.player = player;
	}

	public void onUpdate(){
		if(creature.canSee(player.x, player.y, player.z)) {
			hunt(player);
		} else {
			wander();
		}
	}
}
