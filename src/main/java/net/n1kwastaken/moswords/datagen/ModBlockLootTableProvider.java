package net.n1kwastaken.moswords.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricBlockLootTableProvider;
import net.minecraft.block.Block;
import net.minecraft.data.server.loottable.BlockLootTableGenerator;
import net.minecraft.enchantment.Enchantments;
import net.minecraft.item.Item;
import net.minecraft.loot.LootTable;
import net.minecraft.loot.entry.ItemEntry;
import net.minecraft.loot.function.ApplyBonusLootFunction;
import net.minecraft.loot.function.SetCountLootFunction;
import net.minecraft.loot.provider.number.UniformLootNumberProvider;
import net.n1kwastaken.moswords.block.ModBlocks;
import net.n1kwastaken.moswords.item.ModItems;

public class ModBlockLootTableProvider extends FabricBlockLootTableProvider {
    public ModBlockLootTableProvider(FabricDataOutput dataOutput) {
        super(dataOutput);
    }

    @Override
    public void generate() {
        addDrop(ModBlocks.RUBY_BLOCK);
        addDrop(ModBlocks.SAPPHIRE_BLOCK);

        addDrop(ModBlocks.SAPPHIRE_ORE, copperLikeOreDrops(ModBlocks.SAPPHIRE_ORE, ModItems.SAPPHIRE));
        addDrop(ModBlocks.RUBY_DEEPSLATE_ORE, oneDropOnlyCopperLikeOre(ModBlocks.RUBY_DEEPSLATE_ORE, ModItems.RAW_RUBY));
        addDrop(ModBlocks.BIGGER_CRAFTING_TABLE);
    }

    public LootTable.Builder copperLikeOreDrops(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop,
                this.applyExplosionDecay(drop, ItemEntry.builder(item)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(2.0f, 5.0f)))
                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));
    }

    public LootTable.Builder oneDropOnlyCopperLikeOre(Block drop, Item item) {
        return BlockLootTableGenerator.dropsWithSilkTouch(drop,
                this.applyExplosionDecay(drop, ItemEntry.builder(item)
                        .apply(SetCountLootFunction.builder(UniformLootNumberProvider.create(1.0f, 1.0f)))
                        .apply(ApplyBonusLootFunction.oreDrops(Enchantments.FORTUNE))));

    }
}
