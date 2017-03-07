package ypmf.screens;

import ypmf.Creature;
import ypmf.Tile;

public class LookScreen extends TargetBasedScreen {
	public LookScreen(Creature player, String caption, int sx, int sy) {
		super(player, caption, sx, sy);
	}

	public void enterWorldCoordinate(int x, int y, int screenX, int screenY) {
		Creature creature = player.creature(x, y, player.z);
		if(creature != null) {
			caption = creature.glyph() + " " + creature.name() + creature.details();
			return;
		}

		Tile tile = player.tile(x, y, player.z);
		caption = tile.glyph() + " " + tile.details();
	}
}
