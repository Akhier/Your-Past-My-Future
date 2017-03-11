package ypmf;

import java.util.List;
import asciiPanel.AsciiPanel;

public class StuffFactory {
	private World world;

	public StuffFactory(World world){
		this.world = world;
	}
	
	public Creature newPlayer(List<String> messages, FieldOfView fov, int playerclass){
		Creature player;
		if(playerclass == 0) {
			player = new Creature(world, '@', AsciiPanel.brightYellow, "player(Pikeman)", 120, 30, 5, 6, 0);
			player.modifyRegenHpPer1000(100);
		} else if (playerclass == 1) {
			player = new Creature(world, '@', AsciiPanel.brightCyan, "player(Assasin)", 80, 25, 5, 12, 1);
			player.modifyRegenHpPer1000(50);
			player.modifyRegenManaPer1000(50);
		} else if (playerclass == 2) {
			player = new Creature(world, '@', AsciiPanel.brightRed, "player(Fire Magus)", 100, 20, 5, 18, 2);
			player.modifyRegenManaPer1000(100);
		} else {
			player = new Creature(world, '#', AsciiPanel.magenta, "ERROR", 0, 0, 0, 0);
		}
		world.addAtStartingStairs(player);
		new PlayerAi(player, messages, fov);
		return player;
	}
	
	public Creature newFungus(int depth){
		Creature fungus = new Creature(world, 'f', AsciiPanel.brightGreen, "fungus", 10, 0, 0, 0);
		world.addAtEmptyLocation(fungus, depth);
		new FungusAi(fungus, this);
		return fungus;
	}
	
	public Creature newBat(int depth){
		Creature bat = new Creature(world, 'b', AsciiPanel.brightYellow, "bat", 15, 5, 0, 0);
		world.addAtEmptyLocation(bat, depth);
		new BatAi(bat);
		return bat;
	}
	
	public Creature newZombie(int depth, Creature player){
		Creature zombie = new Creature(world, 'z', AsciiPanel.white, "zombie", 50, 10, 10, 10);
		world.addAtEmptyLocation(zombie, depth);
		new ZombieAi(zombie, player);
		return zombie;
	}

	public Creature newGoblin(int depth, Creature player){
		Creature goblin = new Creature(world, 'g', AsciiPanel.brightGreen, "goblin", 66, 15, 5, 5);
		new GoblinAi(goblin, player);
		world.addAtEmptyLocation(goblin, depth);
		return goblin;
	}
}
