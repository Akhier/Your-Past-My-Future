package components;
public class Coord {
	public int X, Y;
	public Coord(int x, int y) {
		X = x;
		Y = y;
	}

	@Override
	public String toString() {
		return "X: " + X + " Y: " + Y;
	}
}
