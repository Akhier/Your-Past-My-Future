package ypmf;

import java.util.List;
import asciiPanel.AsciiPanel;

public class StuffFactory {
	private World world;

	public StuffFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(List<String> messages, FieldOfView fov){
		Creature player = new Creature(world, '@', AsciiPanel.brightWhite, "player", 100, 20, 5);
		world.addAtStartingStairs(player);
		new PlayerAi(player, messages, fov);
		return player;
	}
	
	public Creature newFungus(int depth){
		Creature fungus = new Creature(world, 'f', AsciiPanel.green, "fungus", 10, 0, 0);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}
	
	public Creature newBat(int depth){
		Creature bat = new Creature(world, 'b', AsciiPanel.brightYellow, "bat", 15, 5, 0);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}
	
	public Creature newZombie(int depth, Creature player){
		Creature zombie = new Creature(world, 'z', AsciiPanel.white, "zombie", 50, 10, 10);
		world.addAtEmptyLocation(zombie, depth);
		new ZombieAi(zombie, player);
		return zombie;
	}

	public Creature newGoblin(int depth, Creature player){
		Creature goblin = new Creature(world, 'g', AsciiPanel.brightGreen, "goblin", 66, 15, 5);
		new GoblinAi(goblin, player);
		world.addAtEmptyLocation(goblin, depth);
		return goblin;
	}
}
