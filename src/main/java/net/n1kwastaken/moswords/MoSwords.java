package net.n1kwastaken.moswords;

import net.fabricmc.api.ModInitializer;

import net.n1kwastaken.moswords.block.ModBlocks;
import net.n1kwastaken.moswords.block.entity.ModBlockEntities;
import net.n1kwastaken.moswords.enchantment.ModEnchantments;
import net.n1kwastaken.moswords.item.ModItemGroups;
import net.n1kwastaken.moswords.item.ModItems;
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
		ModEnchantments.registerModEnchantments();
		ModBlockEntities.registerBlockEntities();

	}
}