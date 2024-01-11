package net.kaupenjoe.moswords.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.kaupenjoe.moswords.block.ModBlocks;
import net.kaupenjoe.moswords.item.ModItems;
import net.minecraft.data.server.recipe.RecipeJsonProvider;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.util.Identifier;

import java.util.List;
import java.util.function.Consumer;

public class ModRecipeProvider extends FabricRecipeProvider {

    private static final List<ItemConvertible>RUBY_SMELTABLES = List.of(ModItems.RAW_RUBY,
            ModBlocks.RUBY_DEEPSLATE_ORE);
    private static final List<ItemConvertible>SAPHIRE_SMELTABLES = List.of(ModItems.RAW_SAPHIRE,
            ModBlocks.SAPHIRE_ORE);

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }

    @Override
    public void generate(Consumer<RecipeJsonProvider> exporter) {
        offerSmelting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                5f, 120, "ruby");
        offerBlasting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                5f, 80, "ruby");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY, RecipeCategory.MISC,
                ModBlocks.RUBY_BLOCK);

        offerSmelting(exporter, SAPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                3f, 100, "saphire");
        offerBlasting(exporter, SAPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                3f, 60, "saphire");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.SAPHIRE, RecipeCategory.MISC,
                ModBlocks.SAPHIRE_BLOCK);


        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RAW_RUBY, 1)
                .pattern("SSS")
                .pattern("SRS")
                .pattern("SSS")
                .input('S', Items.STONE)
                .input('R', ModItems.RUBY)
                .criterion(hasItem(Items.STONE), conditionsFromItem(Items.STONE))
                .criterion(hasItem(ModItems.RUBY), conditionsFromItem(ModItems.RUBY))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.RAW_RUBY)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.RUBY_SWORD, 1)
                .pattern("R")
                .pattern("R")
                .pattern("S")
                .input('S', Items.STICK)
                .input('R', ModItems.RUBY)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(ModItems.RUBY), conditionsFromItem(ModItems.RUBY))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.RUBY_SWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.DEVILS_EYE_SWORD, 1)
                .pattern("R")
                .pattern("E")
                .pattern("S")
                .input('S', Items.STICK)
                .input('R', ModItems.RUBY_SWORD)
                .input('E', ModItems.DEVILS_EYE)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(ModItems.RUBY), conditionsFromItem(ModItems.RUBY))
                .criterion(hasItem(ModItems.DEVILS_EYE), conditionsFromItem(ModItems.DEVILS_EYE))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.DEVILS_EYE_SWORD)));
    }
}