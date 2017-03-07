package ypmf;

import java.awt.Color;
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

	private int maxFood;
	public int maxFood() { return maxFood; }
	
	private int food;
	public int food() { return food; }
	
	private int xp;
	public int xp() { return xp; }
	public void modifyXp(int amount) { 
		xp += amount;
		notify("You %s %d xp.", amount < 0 ? "lose" : "gain", amount);
		while (xp > (int)(Math.pow(level, 1.75) * 25)) {
			level++;
			doAction("advance to level %d", level);
			ai.onGainLevel();
			modifyHp(level * 2, "Died from having a negative level?");
		}
	}
	
	private int level;
	public int level() { return level; }
	
	private int regenHpCooldown;
	private int regenHpPer1000;
	public void modifyRegenHpPer1000(int amount) { regenHpPer1000 += amount; }
	
	private List<Effect> effects;
	public List<Effect> effects(){ return effects; }
	
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
	
	public Creature(World world, char glyph, Color color, String name, int maxHp, int attack, int defense){
		this.world = world;
		this.glyph = glyph;
		this.color = color;
		this.maxHp = maxHp;
		this.hp = maxHp;
		this.attackValue = attack;
		this.defenseValue = defense;
		this.visionRadius = 9;
		this.name = name;
		this.maxFood = 1000;
		this.food = maxFood / 3 * 2;
		this.level = 1;
		this.regenHpPer1000 = 10;
		this.effects = new ArrayList<Effect>();
		this.maxMana = 5;
		this.mana = maxMana;
		this.regenManaPer1000 = 20;
	}
	
	public void moveBy(int mx, int my, int mz){
		if (mx==0 && my==0 && mz==0) {
			return;
		}
		int gx = x + mx, gy = y + my, gz = z + mz;
		if (mz == -1){
			if (world.tile(x, y, z) == Tile.STAIRS_UP) {
				doAction("walk up the stairs to level %d", z+mz+1);
				for(int lx = 0; lx < world.width(); lx++) {
					for(int ly = 0; ly < world.height(); ly++) {
						boolean done = false;
						if(world.tile(lx, ly, gz) == Tile.STAIRS_DOWN) {
							gx = lx;
							gy = ly;
							done = true;
							break;
						}
						if(done) { break; }
					}
				}
			} else {
				doAction("try to go up but are stopped by the cave ceiling");
				return;
			}
		} else if (mz == 1){
			if (world.tile(x, y, z) == Tile.STAIRS_DOWN) {
				doAction("walk down the stairs to level %d", z+mz+1);
				for(int lx = 0; lx < world.width(); lx++) {
					for(int ly = 0; ly < world.height(); ly++) {
						boolean done = false;
						if(world.tile(lx, ly, gz) == Tile.STAIRS_UP) {
							gx = lx;
							gy = ly;
							done = true;
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
		modifyFood(-1);
		
		if (other == null) {
			ai.onEnter(gx, gy, gz, tile);
		} else {
			meleeAttack(other);
		}
	}

	public void meleeAttack(Creature other){
		commonAttack(other, attackValue(), "attack the %s for %d damage", other.name);
	}

	private void commonAttack(Creature other, int attack, String action, Object ... params) {
		modifyFood(-2);
		int amount = Math.max(0, attack - other.defenseValue());
		amount = (int)(Math.random() * amount) + 1;
		Object[] params2 = new Object[params.length+1];
		for (int i = 0; i < params.length; i++){
			params2[i] = params[i];
		}
		params2[params2.length - 1] = amount;
		doAction(action, params2);
		other.modifyHp(-amount, "Killed by a " + name);
		if (other.hp < 1) {
			gainXp(other);
		}
	}
	
	public void gainXp(Creature other){
		int amount = other.maxHp + other.attackValue() + other.defenseValue() - level;
		
		if (amount > 0) {
			modifyXp(amount);
		}
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
	
	public void dig(int wx, int wy, int wz) {
		modifyFood(-10);
		world.dig(wx, wy, wz);
		doAction("dig");
	}
	
	public void update(){
		modifyFood(-1);
		regenerateHealth();
		regenerateMana();
		updateEffects();
		ai.onUpdate();
	}
	
	private void updateEffects(){
		List<Effect> done = new ArrayList<Effect>();
		for (Effect effect : effects){
			effect.update(this);
			if (effect.isDone()) {
				effect.end(this);
				done.add(effect);
			}
		}
		effects.removeAll(done);
	}
	
	private void regenerateHealth(){
		regenHpCooldown -= regenHpPer1000;
		if (regenHpCooldown < 0){
			if (hp < maxHp){
				modifyHp(1, "Died from regenerating health?");
				modifyFood(-1);
			}
			regenHpCooldown += 1000;
		}
	}

	private void regenerateMana(){
		regenManaCooldown -= regenManaPer1000;
		if (regenManaCooldown < 0){
			if (mana < maxMana) {
				modifyMana(1);
				modifyFood(-1);
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
	
	public void modifyFood(int amount) { 
		food += amount;
		if (food > maxFood) {
			maxFood = (maxFood + food) / 2;
			food = maxFood;
			notify("You can't belive your stomach can hold that much!");
			modifyHp(-1, "Killed by overeating.");
		} else if (food < 1 && isPlayer()) {
			modifyHp(-1000, "Starved to death.");
		}
	}
	
	public boolean isPlayer(){
		return glyph == '@';
	}
	
	private void addEffect(Effect effect){
		if (effect == null) {
			return;
		}
		effect.start(this);
		effects.add(effect);
	}
	
	public String details() {
		return String.format("  level:%d  attack:%d  defense:%d  hp:%d", level, attackValue(), defenseValue(), hp);
	}
	
	public void summon(Creature other) {
		world.add(other);
	}
	
	private int detectCreatures;
	public void modifyDetectCreatures(int amount) { detectCreatures += amount; }
	
	public void castSpell(Spell spell, int x2, int y2) {
		Creature other = creature(x2, y2, z);
		if (spell.manaCost() > mana){
			doAction("point and mumble but nothing happens");
			return;
		} else if (other == null) {
			doAction("point and mumble at nothing");
			return;
		}
		other.addEffect(spell.effect());
		modifyMana(-spell.manaCost());
	}
	
}
