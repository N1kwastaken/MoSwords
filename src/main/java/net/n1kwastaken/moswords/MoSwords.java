package net.n1kwastaken.moswords;

import net.fabricmc.api.ModInitializer;
import net.n1kwastaken.moswords.block.ModBlocks;
import net.n1kwastaken.moswords.block.entity.ModBlockEntities;
import net.n1kwastaken.moswords.enchantment.ModEnchantments;
import net.n1kwastaken.moswords.item.ModItemGroups;
import net.n1kwastaken.moswords.item.ModItems;
import net.n1kwastaken.moswords.recipe.ModRecipeSerializers;
import net.n1kwastaken.moswords.recipe.ModRecipeTypes;
import net.n1kwastaken.moswords.screen.ModScreenHandlerTypes;
import net.n1kwastaken.moswords.world.gen.ModWorldGeneration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class 	MoSwords implements ModInitializer {
	public static final String MOD_ID = "moswords";
    public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ModItemGroups.registerModItemGroups();
		ModItems.registerModItems();
		ModBlocks.registerModBlocks();
		ModEnchantments.registerModEnchantments();
		ModBlockEntities.registerModBlockEntities();
		ModScreenHandlerTypes.registerModScreenHandlerTypes();
		ModRecipeTypes.registerModRecipeTypes();
		ModRecipeSerializers.registerModRecipeSerializers();
		ModWorldGeneration.generateModWorldGen();
	}
}