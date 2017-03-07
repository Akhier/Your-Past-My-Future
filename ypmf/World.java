package ypmf;

import java.awt.Color;
import java.util.ArrayList;
import java.util.List;

public class World {
	private Tile[][][] tiles;
	
	private int width;
	public int width() { return width; }
	
	private int height;
	public int height() { return height; }

	private int depth;
	public int depth() { return depth; }
	
	private List<Creature> creatures;
	
	public World(Tile[][][] tiles){
		this.tiles = tiles;
		this.width = tiles.length;
		this.height = tiles[0].length;
		this.depth = tiles[0][0].length;
		this.creatures = new ArrayList<Creature>();
	}

	public Creature creature(int x, int y, int z){
		for (Creature c : creatures){
			if (c.x == x && c.y == y && c.z == z) {
				return c;
			}
		}
		return null;
	}
	
	public Tile tile(int x, int y, int z){
		if (x < 0 || x >= width || y < 0 || y >= height || z < 0 || z >= depth) {
			return Tile.BOUNDS;
		} else {
			return tiles[x][y][z];
		}
	}
	
	public char glyph(int x, int y, int z){
		Creature creature = creature(x, y, z);
		if (creature != null) {
			return creature.glyph();
		}
		return tile(x, y, z).glyph();
	}
	
	public Color color(int x, int y, int z){
		Creature creature = creature(x, y, z);
		if (creature != null) {
			return creature.color();
		}
		return tile(x, y, z).color();
	}

	public void dig(int x, int y, int z) {
		if (tile(x, y, z).isDiggable()) {
			tiles[x][y][z] = Tile.FLOOR;
		}
	}
	
	public void addAtEmptyLocation(Creature creature, int z){
		int x;
		int y;
		do {
			x = (int)(Math.random() * width);
			y = (int)(Math.random() * height);
		} while (!tile(x,y,z).isGround() || creature(x,y,z) != null);
		creature.x = x;
		creature.y = y;
		creature.z = z;
		creatures.add(creature);
	}

	public void addAtStartingStairs(Creature creature) {
		int x = 0, y = 0;
		for(int lx = 0; lx < width; lx++) {
			for(int ly = 0; ly < height; ly++) {
				boolean done = false;
				if(tile(lx, ly, 0) == Tile.STAIRS_UP) {
					x = lx;
					y = ly;
					done = true;
					break;
				}
				if(done) { break; }
			}
		}
		creature.x = x;
		creature.y = y;
		creature.z = 0;
		creatures.add(creature);
	}
	
	public void update(){
		List<Creature> toUpdate = new ArrayList<Creature>(creatures);
		for (Creature creature : toUpdate){
			creature.update();
		}
	}

	public void remove(Creature other) {
		creatures.remove(other);
	}
	
	public void add(Creature pet) {
		creatures.add(pet);
	}
}
