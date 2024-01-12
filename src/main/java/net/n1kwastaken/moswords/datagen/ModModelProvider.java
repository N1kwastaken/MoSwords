package net.n1kwastaken.moswords.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricModelProvider;
import net.n1kwastaken.moswords.block.ModBlocks;
import net.n1kwastaken.moswords.item.ModItems;
import net.minecraft.data.client.BlockStateModelGenerator;
import net.minecraft.data.client.ItemModelGenerator;
import net.minecraft.data.client.Models;

public class ModModelProvider extends FabricModelProvider {
    public ModModelProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generateBlockStateModels(BlockStateModelGenerator blockStateModelGenerator) {
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SAPHIRE_BLOCK);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.RUBY_DEEPSLATE_ORE);
        blockStateModelGenerator.registerSimpleCubeAll(ModBlocks.SAPHIRE_ORE);
    }

    @Override
    public void generateItemModels(ItemModelGenerator itemModelGenerator) {
        itemModelGenerator.register(ModItems.RUBY, Models.GENERATED);
        itemModelGenerator.register(ModItems.SAPHIRE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RAW_RUBY, Models.GENERATED);
        itemModelGenerator.register(ModItems.BIG_RUBY_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.RAW_SAPHIRE, Models.GENERATED);
        itemModelGenerator.register(ModItems.DEVILS_EYE, Models.GENERATED);
        itemModelGenerator.register(ModItems.RUBY_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.DEVILS_EYE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.MOON_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SUN_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.SAPHIRE_SWORD, Models.HANDHELD);
        itemModelGenerator.register(ModItems.THIN_SAPHIRE_SWORD, Models.HANDHELD);
    }
}

