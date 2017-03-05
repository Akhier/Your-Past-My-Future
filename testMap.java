import components.Level;
import systems.MapGen;

public class testMap {
	public static void main(String[] args) {
		Level testMap = MapGen.makeMap(50, 20, 222);
		for(int y = 0; y < testMap.height(); y++) {
			for(int x = 0; x < testMap.width(); x++) {
				char tile = testMap.tileWalkable(x, y) ? '.' : '#';
				System.out.print(tile);
			}
			System.out.println();
		}
	}
}
