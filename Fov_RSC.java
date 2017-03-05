/**
 * A class to check the field of view originating from a tile in a 2d tile map using recursive shadow casting as described on roguebasin
 * @author Akhier Dragonheart
 * @version 1.0
 */
public class Fov_RSC {
	private int width, height, sourceX, sourceY, range;
	private boolean[][] visibleMap, seeThrough;

	/**
	 * Initializes the values for the Fov based on your maps width and height
	 * @param mapwidth		An int with the width of your map in tiles 
	 * @param mapheight		An int with the height of your map in tiles
	 */
	public Fov_RSC(int mapwidth, int mapheight) {
		width = mapwidth;
		height = mapheight;
		visibleMap = new boolean[width][height];   // A 2d bool array for if a tile is visible
		seeThrough = new boolean[width][height];   // A 2d bool array for if a tile is transparent
		sourceX = 0;
		sourceY = 0;
		range = 0;
	}

	/**
	 * Method to calculate the field of view and return a 2d boolean array of visible tiles
	 * @param seethrough is a 2d array from the map of which tiles are see through
	 * @param sourcex is the x of the tile you are viewing from as an int
	 * @param sourcey is the y of the tile you are viewing from as an int
	 * @param sightrange is the range you want to check out to as an int
	 * @return 2d boolean array of visible tiles
	 */
	public boolean[][] Calculate_Sight(boolean[][] seethrough, int sourcex, int sourcey, int sightrange) {
		sourceX = sourcex;
		sourceY = sourcey;
		range = sightrange;
		seeThrough = seethrough;
		visibleMap = new boolean[width][height];
		visibleMap[sourcex][sourcey] = true;
		for(int octant = 1; octant < 9; octant++) {
			scan(1, octant, 1.0, 0.0);
		}
		return visibleMap;
	}

	/**
	 * Recursively goes through the octants checking if tiles are visible and setting visibleMap values to match
	 * @param depth is an int representing the current search depth of the scan
	 * @param octant is which octant of the map is being current checked as an int from 1 to 8
	 * @param startslope is the starting slope as a double
	 * @param endslope is the planned end slope as a double
	 */
	private void scan(int depth, int octant, double startslope, double endslope) {
		int x = 0, y = 0;
		switch(octant) {
		case 1:   // NW
			x = sourceX - (int)(startslope * depth);
			y = sourceY - depth;
			if(checkBounds(x, y)) {
				while(getSlope(x, y, sourceX, sourceY) >= endslope) {
					if(isVisible(x, y)) {
						if(seeThrough[x][y]) {
							if(testTile(x - 1, y, false)) {
								startslope = getSlope(x - .5, y - .5, sourceX, sourceY);
							}
						} else {
							if(testTile(x - 1, y, true)) {
								scan(depth + 1, octant, startslope, getSlope(x - .5, y + .5, sourceX, sourceY));
							}
						}
						visibleMap[x][y] = true;
					}
					x++;
				}
				x--;
			}
			break;
		case 2:   // NE
			x = sourceX + (int)(startslope * depth);
			y = sourceY - depth;
			if(checkBounds(x, y)) {
				while(getSlope(x, y, sourceX, sourceY) <= endslope) {
					if(isVisible(x, y)) {
						if(seeThrough[x][y]) {
							if(testTile(x + 1, y, false)) {
								startslope = -getSlope(x + .5, y - .5, sourceX, sourceY);
							}
						} else {
							if(testTile(x + 1, y, true)) {
								scan(depth + 1, octant, startslope, getSlope(x + .5, y + .5, sourceX, sourceY));
							}
						}
						visibleMap[x][y] = true;
					}
					x--;
				}
				x++;
			}
			break;
		case 3:   // EN
			x = sourceX + depth;
			y = sourceY - (int)(startslope * depth);
			if(checkBounds(x, y)) {
				while(getInvertedSlope(x, y, sourceX, sourceY) <= endslope) {
					if(isVisible(x, y)) {
						if(seeThrough[x][y]) {
							if(testTile(x, y - 1, false)) {
								startslope = -getInvertedSlope(x + .5, y - .5, sourceX, sourceY);
							}
						} else {
							if(testTile(x, y - 1, true)) {
								scan(depth + 1, octant, startslope, getInvertedSlope(x - .5, y - .5, sourceX, sourceY));
							}
						}
						visibleMap[x][y] = true;
					}
					y++;
				}
				y--;
			}
			break;
		case 4:   // ES
			x = sourceX + depth;
			y = sourceY + (int)(startslope * depth);
			if(checkBounds(x, y)) {
				while(getInvertedSlope(x, y, sourceX, sourceY) >= endslope) {
					if(isVisible(x, y)) {
						if(seeThrough[x][y]) {
							if(testTile(x, y + 1, false)) {
								startslope = getInvertedSlope(x + .5, y + .5, sourceX, sourceY);
							}
						} else {
							if(testTile(x, y + 1, true)) {
								scan(depth + 1, octant, startslope, getInvertedSlope(x - .5, y + .5, sourceX, sourceY));
							}
						}
						visibleMap[x][y] = true;
					}
					y--;
				}
				y++;
			}
			break;
		case 5:   // SE
			x = sourceX + (int)(startslope * depth);
			y = sourceY + depth;
			if(checkBounds(x, y)) {
				while(getSlope(x, y, sourceX, sourceY) >= endslope) {
					if(isVisible(x, y)) {
						if(seeThrough[x][y]) {
							if(testTile(x + 1, y, false)) {
								startslope = getSlope(x + .5, y + .5, sourceX, sourceY);
							}
						} else {
							if(testTile(x + 1, y, true)) {
								scan(depth + 1, octant, startslope, getSlope(x + .5, y - .5, sourceX, sourceY));
							}
						}
						visibleMap[x][y] = true;
					}
					x--;
				}
				x++;
			}
			break;
		case 6:   // SW
			x = sourceX - (int)(startslope * depth);
			y = sourceY + depth;
			if(checkBounds(x, y)) {
				while(getSlope(x, y, sourceX, sourceY) <= endslope) {
					if(isVisible(x, y)) {
						if(seeThrough[x][y]) {
							if(testTile(x - 1, y, false)) {
								startslope = -getSlope(x - .5, y + .5, sourceX, sourceY);
							}
						} else {
							if(testTile(x - 1, y, true)) {
								scan(depth + 1, octant, startslope, getSlope(x - .5, y - .5, sourceX, sourceY));
							}
						}
						visibleMap[x][y] = true;
					}
					x++;
				}
				x--;
			}
			break;
		case 7:   // WS
			x = sourceX - depth;
			y = sourceY + (int)(startslope * depth);
			if(checkBounds(x, y)) {
				while(getInvertedSlope(x, y, sourceX, sourceY) <= endslope) {
					if(isVisible(x, y)) {
						if(seeThrough[x][y]) {
							if(testTile(x, y + 1, false)) {
								startslope = -getInvertedSlope(x - .5, y + .5, sourceX, sourceY);
							}
						} else {
							if(testTile(x, y + 1, true)) {
								scan(depth + 1, octant, startslope, getInvertedSlope(x + .5, y + .5, sourceX, sourceY));
							}
						}
						visibleMap[x][y] = true;
					}
					y--;
				}
				y++;
			}
			break;
		case 8:   // WN
			x = sourceX - depth;
			y = sourceY - (int)(startslope * depth);
			if(checkBounds(x, y)) {
				while(getInvertedSlope(x, y, sourceX, sourceY) >= endslope) {
					if(isVisible(x, y)) {
						if(seeThrough[x][y]) {
							if(testTile(x, y - 1, false)) {
								startslope = getInvertedSlope(x - .5, y - .5, sourceX, sourceY);
							}
						} else {
							if(testTile(x, y - 1, true)) {
								scan(depth + 1, octant, startslope, getInvertedSlope(x + .5, y - .5, sourceX, sourceY));
							}
						}
						visibleMap[x][y] = true;
					}
					y++;
				}
				y--;
			}
			break;
		}
		if(x < 0) {
			x = 0;
		}
		if(x >= width) {
			x = width - 1;
		}
		if(y < 0) {
			y = 0;
		}
		if(y >= height) {
			y = height - 1;
		}
		if(isVisible(x, y) && seeThrough[x][y]) {
			scan(depth + 1, octant, startslope, endslope);
		}
	}

	/**
	 * Checks if the given x,y coords are within the map bounds
	 * @param x
	 * @param y
	 * @return boolean of whether the tile is within the map
	 */
	private boolean checkBounds(int x, int y) {
		if(x < 0 || y < 0 || x >= width || y >= height) {
			return false;
		} else {
			return true;
		}
	}

	/**
	 * Gets the slope between two points as defined by their x,y coords
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return double representing the slope between the points
	 */
	private double getSlope(double x1, double y1, double x2, double y2) {
		return (double)(x1 - x2) / (double)(y1 - y2);
	}

	/**
	 * Gets the inverted slope between two points as defined by their x,y coords
	 * @param x1
	 * @param y1
	 * @param x2
	 * @param y2
	 * @return double representing the inverted slope between the points
	 */
	private double getInvertedSlope(double x1, double y1, double x2, double y2) {
		return (double)(y1 - y2) / (double)(x1 - x2);
	}

	/**
	 * Checks if a tile would be visible from the source tile
	 * @param x
	 * @param y
	 * @return boolean for if tile would be visible
	 */
	private boolean isVisible(int x, int y) {
		if(checkBounds(sourceX, sourceY) && checkBounds(x, y)) {
			return Math.hypot(sourceX - x, sourceY - y) <= range;
		} else {
			return false;
		}
	}

	/**
	 * Test if a tile is either not visible and thus return false or if the tiles seethrough state is the same as being tested for
	 * @param x
	 * @param y
	 * @param state is a boolean value you want to test the tile seethrough value against
	 * @return boolean of if it was visible and if so whether it matches the seethrough state given
	 */
	private boolean testTile(int x, int y, boolean state) {
		if(!isVisible(x, y)) {
			return false;
		} else {
			return seeThrough[x][y] == state;
		}
	}
}
