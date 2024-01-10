package net.kaupenjoe.moswords;

import net.fabricmc.api.ModInitializer;

import net.kaupenjoe.moswords.block.ModBlocks;
import net.kaupenjoe.moswords.item.ModItemGroups;
import net.kaupenjoe.moswords.item.ModItems;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class MoSwords implements ModInitializer {
	public static final String MOD_ID = "moswords";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerItemGroups();

		ModItems.registerModItems();
		ModBlocks.registerModBlocks();

	}
}