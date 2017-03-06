package ypmf.screens;

import ypmf.Creature;
import ypmf.Item;

public class EquipScreen extends InventoryBasedScreen {
	public EquipScreen(Creature player) {
		super(player);
	}

	@Override
	protected String getVerb() {
		return "wear or wield";
	}

	@Override
	protected boolean isAcceptable(Item item) {
		return item.attackValue() > 0 || item.defenseValue() > 0;
	}

	@Override
	protected Screen use(Item item) {
		player.equip(item);
		return null;
	}
}
