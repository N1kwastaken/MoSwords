package net.n1kwastaken.moswords.block;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.object.builder.v1.block.FabricBlockSettings;
import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.ExperienceDroppingBlock;
import net.minecraft.item.BlockItem;
import net.minecraft.item.Item;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.minecraft.util.math.intprovider.UniformIntProvider;
import net.n1kwastaken.moswords.MoSwords;
import net.n1kwastaken.moswords.block.custom.BiggerCraftingTableBlock;

public class ModBlocks {
    public static final Block RUBY_BLOCK = registerBlock("ruby_block", new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
    public static final Block SAPPHIRE_BLOCK = registerBlock("sapphire_block", new Block(FabricBlockSettings.copyOf(Blocks.IRON_BLOCK)));
    public static final Block BIGGER_CRAFTING_TABLE = registerBlock("bigger_crafting_table", new BiggerCraftingTableBlock(FabricBlockSettings.copyOf(Blocks.CRAFTING_TABLE)));

    public static final Block RUBY_DEEPSLATE_ORE = registerBlock("ruby_deepslate_ore", new ExperienceDroppingBlock(UniformIntProvider.create(10, 30), FabricBlockSettings.copyOf(Blocks.DEEPSLATE).strength(5f)));

    public static final Block SAPPHIRE_ORE = registerBlock("sapphire_ore", new ExperienceDroppingBlock(UniformIntProvider.create(7, 20), FabricBlockSettings.copyOf(Blocks.STONE).strength(3f)));

    private static Block registerBlock(String id, Block block) {
        registerBlockItem(id, block);
        return Registry.register(Registries.BLOCK, new Identifier(MoSwords.MOD_ID, id), block);
    }

    private static Item registerBlockItem(String id, Block block) {
        return Registry.register(Registries.ITEM, new Identifier(MoSwords.MOD_ID, id), new BlockItem(block, new FabricItemSettings()));
    }

    public static void registerModBlocks() {
        MoSwords.LOGGER.info("Registering ModBlocks for " + MoSwords.MOD_ID);
    }
}
