package ypmf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.Collections;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import asciiPanel.AsciiPanel;

public class StuffFactory {
	private World world;
	private Map<String, Color> potionColors;
	private List<String> potionAppearances;

	public StuffFactory(World world){
		this.world = world;
		setUpPotionAppearances();
	}
	
	private void setUpPotionAppearances(){
		potionColors = new HashMap<String, Color>();
		potionColors.put("red potion", AsciiPanel.brightRed);
		potionColors.put("yellow potion", AsciiPanel.brightYellow);
		potionColors.put("green potion", AsciiPanel.brightGreen);
		potionColors.put("cyan potion", AsciiPanel.brightCyan);
		potionColors.put("blue potion", AsciiPanel.brightBlue);
		potionColors.put("magenta potion", AsciiPanel.brightMagenta);
		potionColors.put("dark potion", AsciiPanel.brightBlack);
		potionColors.put("grey potion", AsciiPanel.white);
		potionColors.put("light potion", AsciiPanel.brightWhite);
		potionAppearances = new ArrayList<String>(potionColors.keySet());
		Collections.shuffle(potionAppearances);
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
