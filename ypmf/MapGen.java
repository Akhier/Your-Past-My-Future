package ypmf;

import java.util.Random;

public class MapGen {
	public static Random rng;
	private static boolean[][][] walkable;
	private static int Seed;

	public static World makeLevels(int width, int height, int depth, int seed) {
		if(seed == -1) {
			Seed = (int)(Math.random() * 10000);
		} else {
			Seed = seed;
		}
		rng = new Random(Seed);
		walkable = new boolean[width][height][depth];
		for(int z = 0; z < depth; z++) {
			int maxRooms = 1000;
			int rW = randInt(5, 11);
			int rH = randInt(5, 11);
			int rX = 1;
			int rY = (int)((height / 2) - (rH / 2));
			addRoom(rX, rY, rW, rH, z);
			int curRooms = 1;
			int fail = 0, totalfail = 0;
			while(curRooms < maxRooms) {
				int oldW = rW, oldH = rH, oldX = rX, oldY = rY, shiftX = randInt(1, 5), shiftY = randInt(1, 5);
				rW = randInt(5, 11);
				rH = randInt(5, 11);
				int dir = randInt(1, 17);   // 1=SW 2=W 3=NW 4=S 5=N 6-7=SE 8-9=NE 10-12=E
				switch(dir) {
				case 1:
				case 2:
				case 3:   //sw
					rX -= (shiftX + rW);
					rY += (shiftY + oldH);
					break;
				case 4:
				case 5:
				case 6:
				case 7:   //w
					rX -= (shiftX + rW);
					break;
				case 8:
				case 9:
				case 10:   //nw
					rX -= (shiftX + rW);
					rY -= (shiftY + rH);
					break;
				case 11:
				case 12:   //s
					rY += (shiftY + oldH);
					break;
				case 13:
				case 14:   //n
					rY -= (shiftY + rH);
					break;
				case 15:   //se
					rX += (shiftX + oldW);
					rY += (shiftY + oldH);
					break;
				case 16:   //ne
					rX += (shiftX + oldW);
					rY -= (shiftY + rH);
					break;
				default:   //e
					rX += (shiftX + oldW);
					break;
				}
				if(rX < 0 || rY < 0 || rX + rW > width || rY + rH > height) {
					rX = oldX;
					rY = oldY;
					rW = oldW;
					rH = oldH;
					fail++;
					totalfail++;
				} else {
					if(addRoom(rX, rY, rW, rH, z)) {
						int hX1 = oldX + oldW / 2;
						int hY1 = oldY + oldH / 2;
						int hX2 = rX + rW / 2;
						int hY2 = rY + rH / 2;
						addHallway(hX1, hY1, hX2, hY2, z);
						curRooms++;
						fail = 0;
						totalfail = 0;
					} else {
						rX = oldX;
						rY = oldY;
						rW = oldW;
						rH = oldH;
						fail++;
						totalfail++;
					}
					if(fail > 10) {
						for(int x = width - 1; x > 0; x--) {
							if(walkable[x][(int)(height / 2)][z]) {
								rX = x;
								rY = (int)(height / 2);
								rW = 1;
								rH = 1;
								fail = 0;
								break;
							}
						}
					}
					if(totalfail > 30) {
						break;
					}
				}
			}
		}

		Tile[][][] tiles = new Tile[width][height][depth];
		for(int z = 0; z < depth; z++) {
			for(int y = 0; y < height; y++) {
				for(int x = 0; x < width; x++) {
					tiles[x][y][z] = walkable[x][y][z] ? Tile.FLOOR : Tile.WALL;
				}
				if(y == (int)(height / 2)) {
					for(int x = 0; x < width; x++) {
						if(walkable[x][y][z]) {
							tiles[x + 1][y][z] = Tile.STAIRS_UP;
							break;
						}
					}
					for(int x = width - 1; x >= 0; x--) {
						if(walkable[x][y][z]) {
							tiles[x - 1][y][z] = Tile.STAIRS_DOWN;
							break;
						}
					}
				}
			}
		}
		World output = new World(tiles, Seed);
		return output;
	}

	private static int randInt(int min, int max) {
		return rng.nextInt(max - min + 1) + min;
	}
	
	private static boolean addRoom(int X, int Y, int width, int height, int depth) {
		for(int x = 0; x < width - 1; x++) {
			for(int y = 0; y < height - 1; y++) {
				if(walkable[X + x][Y + y][depth]) {
					return false;
				}
			}
		}
		for(int x = 1; x < width - 1; x++) {
			for(int y = 1; y < height -1; y++) {
				walkable[X + x][Y + y][depth] = true;
			}
		}
		return true;
	}

	private static void addHallway(int x1, int y1, int x2, int y2, int depth) {
		if(x1 < x2) {
			for(int x = x1; x <= x2; x++) {
				walkable[x][y1][depth] = true;
			}
		} else {
			for(int x = x1; x >= x2; x--) {
				walkable[x][y1][depth] = true;
			}
		}
		if(y1 < y2) {
			for(int y = y1; y <= y2; y++) {
				walkable[x2][y][depth] = true;
			}
		} else {
			for(int y = y1; y >= y2; y--) {
				walkable[x2][y][depth] = true;
			}
		}
	}
}
