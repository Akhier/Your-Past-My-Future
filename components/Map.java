package components;
public class Map {
	private int width, height;
	private boolean[][] transparent, walkable;
	public Map(int width, int height) {
		this.width = width;
		this.height = height;
		transparent = new boolean[width][height];
		walkable = new boolean[width][height];
	}

	public int width() { return width; }

	public int height() { return height; }

	public boolean tileTransparent(int x, int y) { return transparent[x][y]; }

	public boolean tileWalkable(int x, int y) { return walkable[x][y]; }

	public boolean[][] mapTransparency() { return transparent; }
}
