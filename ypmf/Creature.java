package ypmf;

import java.awt.Color;
import java.io.IOException;
import java.io.PrintWriter;
import java.util.ArrayList;
import java.util.List;


public class Creature {
	private World world;
	public int x, y, z;
	
	private char glyph;
	public char glyph() { return glyph; }
	
	private Color color;
	public Color color() { return color; }

	private CreatureAi ai;
	public void setCreatureAi(CreatureAi ai) { this.ai = ai; }
	
	private int maxHp;
	public int maxHp() { return maxHp; }
	public void modifyMaxHp(int amount) { maxHp += amount; }
	
	private int hp;
	public int hp() { return hp; }
	
	private int attackValue;
	public void modifyAttackValue(int value) { attackValue += value; }
	public int attackValue() { 
		return attackValue;
	}

	private int defenseValue;
	public void modifyDefenseValue(int value) { defenseValue += value; }
	public int defenseValue() { 
		return defenseValue;
	}

	private int visionRadius;
	public void modifyVisionRadius(int value) { visionRadius += value; }
	public int visionRadius() { return visionRadius; }

	private String name;
	public String name() { return name; }

	private int regenHpCooldown;
	private int regenHpPer1000;
	public void modifyRegenHpPer1000(int amount) { regenHpPer1000 += amount; }
	
	private int maxMana;
	public int maxMana() { return maxMana; }
	public void modifyMaxMana(int amount) { maxMana += amount; }
	
	private int mana;
	public int mana() { return mana; }
	public void modifyMana(int amount) { mana = Math.max(0, Math.min(mana+amount, maxMana)); }
	
	private int regenManaCooldown;
	private int regenManaPer1000;
	public void modifyRegenManaPer1000(int amount) { regenManaPer1000 += amount; }
	
	private String causeOfDeath;
	public String causeOfDeath() { return causeOfDeath; }

	private int playerClass;
	public int pClass() { return playerClass; }

	public int previousClass;
	
	public Creature(World world, char glyph, Color color, String name, int maxHp, int attack, int defense, int mana){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
		this.visionRadius = 9;
		this.name = name;
		this.regenHpPer1000 = 100;
		this.maxMana = mana;
		this.mana = maxMana;
		this.regenManaPer1000 = 100;
	}
	
	public Creature(World world, char glyph, Color color, String name, int maxHp, int attack, int defense, int mana, int playerclass){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
		this.visionRadius = 9;
		this.name = name;
		this.regenHpPer1000 = 30;
		this.maxMana = mana;
		this.mana = maxMana;
		this.regenManaPer1000 = 20;
		this.playerClass = playerclass;
	}
	
	public void moveBy(int mx, int my, int mz){
		if (mx==0 && my==0 && mz==0) {
			return;
		}
		int gx = x + mx, gy = y + my, gz = z + mz;
		if (mz == 1){
			if (world.tile(x, y, z) == Tile.STAIRS_DOWN) {
				doAction("walk down the stairs to level %d", z+mz+1);
				for(int lx = 0; lx < world.width(); lx++) {
					for(int ly = 0; ly < world.height(); ly++) {
						boolean done = false;
						if(world.tile(lx, ly, gz) == Tile.STAIRS_UP) {
							try(PrintWriter writer = new PrintWriter("prevclass", "UTF-8")){
							    writer.println(pClass() + z * 3);
							} catch (IOException e) { }
							gx = lx;
							gy = ly;
							done = true;
							switch(pClass()) {
							case(0):
								maxMana += 2;
								maxHp += 15;
								break;
							case(1):
								maxMana += 4;
								maxHp += 10;
								break;
							case(2):
								maxMana += 6;
								maxHp += 5;
								break;
							}
							mana = maxMana;
							hp = maxHp;
							break;
						}
						if(done) { break; }
					}
				}
			} else {
				doAction("try to go down but are stopped by the cave floor");
				return;
			}
		}

		Tile tile = world.tile(gx, gy, gz);
		Creature other = world.creature(gx, gy, gz);
		
		if (other == null) {
			ai.onEnter(gx, gy, gz, tile);
		} else {
			meleeAttack(other);
		}
	}

	public void meleeAttack(Creature other){
		commonAttack(other, attackValue(), "attack the %s for %d damage", other.name);
	}

	public void commonAttack(Creature other, int attack, String action, Object ... params) {
		int amount = Math.max(1, attack - other.defenseValue());
		amount = MapGen.rng.nextInt(amount) + 1;
		Object[] params2 = new Object[params.length+1];
		for (int i = 0; i < params.length; i++){
			params2[i] = params[i];
		}
		params2[params2.length - 1] = amount;
		doAction(action, params2);
		other.modifyHp(-amount, "Killed by a " + name);
	}
	
	public void modifyHp(int amount, String causeOfDeath) { 
		hp += amount;
		this.causeOfDeath = causeOfDeath;
		if (hp > maxHp) {
			hp = maxHp;
		} else if (hp < 1) {
			doAction("die");
			world.remove(this);
		}
	}
	
	public void update(){
		regenerateHealth();
		regenerateMana();
		ai.onUpdate();
	}
	
	private void regenerateHealth(){
		regenHpCooldown -= regenHpPer1000;
		if (regenHpCooldown < 0){
			if (hp < maxHp){
				modifyHp(1, "Died from regenerating health?");
			}
			regenHpCooldown += 1000;
		}
	}

	private void regenerateMana(){
		regenManaCooldown -= regenManaPer1000;
		if (regenManaCooldown < 0){
			if (mana < maxMana) {
				modifyMana(1);
			}
			regenManaCooldown += 1000;
		}
	}
	
	public boolean canEnter(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz).isGround() && world.creature(wx, wy, wz) == null;
	}

	public void notify(String message, Object ... params){
		ai.onNotify(String.format(message, params));
	}
	
	public void doAction(String message, Object ... params){
		for (Creature other : getCreaturesWhoSeeMe()){
			if (other == this){
				other.notify("You " + message + ".", params);
			} else {
				other.notify(String.format("The %s %s.", name, makeSecondPerson(message)), params);
			}
		}
	}
	
	private List<Creature> getCreaturesWhoSeeMe(){
		List<Creature> others = new ArrayList<Creature>();
		int r = 9;
		for (int ox = -r; ox < r+1; ox++){
			for (int oy = -r; oy < r+1; oy++){
				if (ox*ox + oy*oy > r*r) {
					continue;
				}
				Creature other = world.creature(x+ox, y+oy, z);
				if (other == null) {
					continue;
				}
				others.add(other);
			}
		}
		return others;
	}
	
	private String makeSecondPerson(String text){
		String[] words = text.split(" ");
		words[0] = words[0] + "s";
		StringBuilder builder = new StringBuilder();
		for (String word : words){
			builder.append(" ");
			builder.append(word);
		}
		return builder.toString().trim();
	}
	
	public boolean canSee(int wx, int wy, int wz){
		return (detectCreatures > 0 && world.creature(wx, wy, wz) != null || ai.canSee(wx, wy, wz));
	}

	public Tile realTile(int wx, int wy, int wz) {
		return world.tile(wx, wy, wz);
	}
	
	public Tile tile(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz)) {
			return world.tile(wx, wy, wz);
		} else {
			return ai.rememberedTile(wx, wy, wz);
		}
	}

	public Creature creature(int wx, int wy, int wz) {
		if (canSee(wx, wy, wz)) {
			return world.creature(wx, wy, wz);
		} else {
			return null;
		}
	}
	
	public boolean isPlayer(){
		return glyph == '@';
	}
	
	public String details() {
		return String.format("  attack:%d  defense:%d  hp:%d", attackValue(), defenseValue(), hp);
	}
	
	public void summon(Creature other) {
		world.add(other);
	}
	
	private int detectCreatures;
	public void modifyDetectCreatures(int amount) { detectCreatures += amount; }
}
