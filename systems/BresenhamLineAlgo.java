package systems;

import java.util.ArrayList;
import java.util.Collections;

import components.Coord;

public class BresenhamLineAlgo {
	public static ArrayList<Coord> getLine(int x1, int y1, int x2, int y2) {
		int dx = x2 - x1, dy = y2 - y1;
		boolean isSteep = Math.abs(dy) > Math.abs(dx);

		if(isSteep) {
			int temp = x1;
			x1 = y1;
			y1 = temp;
			temp = x2;
			x2 = y2;
			y2 = temp;
		}

		boolean swapped = false;
		if(x1 > x2) {
			int temp = x1;
			x1 = x2;
			x1 = temp;
			temp = y1;
			y1 = y2;
			y2 = temp;
			swapped = true;
		}

		dx = x2 - x1;
		dy = y2 - y1;

		int error = (int)(dx / 2.0);
		int ystep = y1 < y2 ? 1 : -1;

		int y = y1;
		ArrayList<Coord> points = new ArrayList<Coord>();
		for(int x = x1; x <= x2 + 1; x++) {
			Coord coord = isSteep ? new Coord(y, x) : new Coord(x, y);
			points.add(coord);
			error -= Math.abs(dy);
			if(error < 0) {
				y += ystep;
				error += dx;
			}
		}

		if(swapped) {
			Collections.reverse(points);
		}
		return points;
	}
}
