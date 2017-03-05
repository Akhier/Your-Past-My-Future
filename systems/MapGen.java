package systems;

import java.util.ArrayList;
import java.util.Random;
import components.Coord;
import components.Map;

public class MapGen {
	private static Random rng;
	private static boolean[][] transparent, walkable;

	public static Map makeMap(int width, int height, int seed) {
		rng = new Random(seed);
		transparent = new boolean[width][height];
		walkable = new boolean[width][height];



		Map output = new Map(width, height);
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				output.setTile(x, y, transparent[x][y], walkable[x][y]);
			}
		}
		return null;
	}
	
	private boolean addRoom(int X, int Y, int width, int height) {
		for(int x = 0; x < width; x++) {
			for(int y = 0; y < height; y++) {
				if(!walkable[X + x][Y + y]) {
					return false;
				}
			}
		}
		for(int x = 1; x < width - 1; x++) {
			for(int y = 1; y < height -1; y++) {
				transparent[X + x][Y + y] = true;
				walkable[X + x][Y + y] = true;
			}
		}
		return true;
	}

	private void addHallway(int x1, int y1, int x2, int y2) {
		boolean wide = rng.nextBoolean();
		boolean up = y1 > y2 ? true : false;
		boolean left = x1 > x2 ? true : false;
		ArrayList<Coord> line = BresenhamLineAlgo.getLine(x1, y1, x2, y2);
		for(Coord coord : line) {
			transparent[coord.X][coord.Y] = true;
			walkable[coord.X][coord.Y] = true;
			if(wide) {
				if(Math.abs(x1 - x2) > Math.abs(y1 - y2)) {
					if(up) {
						coord.Y--;
					} else {
						coord.Y++;
					}
				} else {
					if(left) {
						coord.X--;
					} else {
						coord.X++;
					}
				}
				transparent[coord.X][coord.Y] = true;
				walkable[coord.X][coord.Y] = true;
			}
		}
	}
}
