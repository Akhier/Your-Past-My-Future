package ypmf;


public class ZombieAi extends CreatureAi {
	private Creature player;
	
	public ZombieAi(Creature creature, Creature player) {
		super(creature);
		this.player = player;
	}

	public void onUpdate(){
		if(MapGen.rng.nextInt(10) < 2) {
			return;
		}
		if (creature.canSee(player.x, player.y, player.z)) {
			hunt(player);
		} else {
			wander();
		}
	}
}
