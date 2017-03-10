package ypmf.screens;

import ypmf.Creature;

public class SparkScreen extends MagicTargetingScreen {
	public SparkScreen(Creature player, int sx, int sy) {
		super(player, sx, sy);
	}

	public void selectWorldCoordinate(int x, int y, int screenX, int screenY) {
		Creature creature = player.creature(x, y, player.z);
		if(creature != null && player.canSee(x, y, player.z)) {
			player.commonAttack(creature, player.attackValue(), "send a small spark at %s and deal %d damage", creature.name());
			player.modifyMana(-1);
			return;
		}
	}
}
