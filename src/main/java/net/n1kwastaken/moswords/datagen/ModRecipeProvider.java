package net.n1kwastaken.moswords.datagen;

import net.fabricmc.fabric.api.datagen.v1.FabricDataOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricRecipeProvider;
import net.minecraft.block.Blocks;
import net.minecraft.data.server.recipe.RecipeExporter;
import net.minecraft.data.server.recipe.ShapedRecipeJsonBuilder;
import net.minecraft.item.ItemConvertible;
import net.minecraft.item.Items;
import net.minecraft.recipe.book.RecipeCategory;
import net.minecraft.registry.tag.ItemTags;
import net.minecraft.util.Identifier;
import net.n1kwastaken.moswords.block.ModBlocks;
import net.n1kwastaken.moswords.datagen.server.recipe.BiggerShapedRecipeJsonBuilder;
import net.n1kwastaken.moswords.item.ModItems;

import java.util.List;

public class ModRecipeProvider extends FabricRecipeProvider {

    private static final List<ItemConvertible> RUBY_SMELTABLES = List.of(ModItems.RAW_RUBY,
            ModBlocks.RUBY_DEEPSLATE_ORE);
    private static final List<ItemConvertible> SAPPHIRE_SMELTABLES = List.of(ModItems.RAW_SAPPHIRE,
            ModBlocks.SAPPHIRE_ORE);

    public ModRecipeProvider(FabricDataOutput output) {
        super(output);
    }



    @Override
    public void generate(RecipeExporter exporter) {
        offerSmelting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                5f, 120, "ruby");
        offerBlasting(exporter, RUBY_SMELTABLES, RecipeCategory.MISC, ModItems.RUBY,
                5f, 80, "ruby");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.RUBY, RecipeCategory.MISC,
                ModBlocks.RUBY_BLOCK);

        offerSmelting(exporter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE,
                3f, 100, "sapphire");
        offerBlasting(exporter, SAPPHIRE_SMELTABLES, RecipeCategory.MISC, ModItems.SAPPHIRE,
                3f, 60, "sapphire");
        offerReversibleCompactingRecipes(exporter, RecipeCategory.BUILDING_BLOCKS, ModItems.SAPPHIRE, RecipeCategory.MISC,
                ModBlocks.SAPPHIRE_BLOCK);

        BiggerShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.HANDLE)
                 .pattern("  II")
                 .pattern(" STI")
                 .pattern("FTS ")
                 .pattern("FF  ")
                 .inputWithCriterion('T', ModItems.TITANIUM_INGOT)
                 .inputWithCriterion('F', Items.FLINT)
                 .inputWithCriterion('S', Items.STONE)
                 .inputWithCriterion('I', Items.IRON_INGOT)
                 .offerTo(exporter, new Identifier(getRecipeName(ModItems.HANDLE)));

        BiggerShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.QUADRUPLE_SWORD)
                .pattern("E  R")
                .pattern(" IH ")
                .pattern(" HI ")
                .pattern("S  A")
                .inputWithCriterion('R', ModItems.RUBY_SWORD)
                .inputWithCriterion('E', ModItems.EMERALD_SWORD)
                .inputWithCriterion('S', ModItems.SAPPHIRE_SWORD)
                .inputWithCriterion('A', ModItems.AMETHYST_SWORD)
                .inputWithCriterion('I', ModItems.TITANIUM_INGOT)
                .inputWithCriterion('H', ModItems.HANDLE)
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.QUADRUPLE_SWORD)));

        BiggerShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.BIG_RUBY_SWORD)
                .pattern("   I")
                .pattern(" IH ")
                .pattern(" RI ")
                .pattern("S   ")
                .inputWithCriterion('R', ModItems.RUBY_SWORD)
                .inputWithCriterion('S', Items.STICK)
                .inputWithCriterion('I', ModItems.RUBY)
                .inputWithCriterion('H', ModItems.HANDLE)
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.BIG_RUBY_SWORD)));

        BiggerShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.EMERALD_STAFF)
                .pattern("   E")
                .pattern(" HS ")
                .pattern(" SH ")
                .pattern("E   ")
                .inputWithCriterion('E', Items.EMERALD)
                .inputWithCriterion('S', Items.STICK)
                .inputWithCriterion('H', ModItems.HANDLE)
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.EMERALD_STAFF)));

        BiggerShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.THIN_SAPPHIRE_SWORD)
                .pattern("   S")
                .pattern(" WS ")
                .pattern(" SH ")
                .pattern("M   ")
                .inputWithCriterion('M', Items.STICK)
                .inputWithCriterion('S', ModItems.SAPPHIRE)
                .inputWithCriterion('W', ModItems.SAPPHIRE_SWORD)
                .inputWithCriterion('H', ModItems.HANDLE)
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.THIN_SAPPHIRE_SWORD)));

        // normal crafting

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.VAMPIRE_SWORD, 1)
                 .pattern(" A ")
                 .pattern("WBW")
                 .pattern(" S ")
                 .input('A', ModItems.AMETHYST_SWORD)
                 .input('B', ModItems.BLOOD_BOTTLE)
                 .input('W', ModItems.BAT_WINGS)
                 .input('S', Items.STICK)
                 .criterion(hasItem( ModItems.AMETHYST_SWORD), conditionsFromItem( ModItems.AMETHYST_SWORD))
                 .criterion(hasItem( ModItems.BLOOD_BOTTLE), conditionsFromItem( ModItems.BLOOD_BOTTLE))
                 .criterion(hasItem( ModItems.BAT_WINGS), conditionsFromItem( ModItems.BAT_WINGS))
                 .criterion(hasItem( Items.STICK), conditionsFromItem( Items.STICK))
                 .offerTo(exporter, new Identifier(getRecipeName(ModItems.VAMPIRE_SWORD)));


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

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.SLIME_SWORD, 1)
                .pattern("B")
                .pattern("B")
                .pattern("S")
                .input('S', Items.SLIME_BALL)
                .input('B', Items.SLIME_BLOCK)
                .criterion(hasItem(Items.SLIME_BALL), conditionsFromItem(Items.SLIME_BALL))
                .criterion(hasItem(Items.SLIME_BLOCK), conditionsFromItem(Items.SLIME_BLOCK))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.SLIME_SWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.EMERALD_SWORD, 1)
                .pattern("E")
                .pattern("E")
                .pattern("S")
                .input('S', Items.STICK)
                .input('E', Items.EMERALD)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.EMERALD), conditionsFromItem(Items.EMERALD))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.EMERALD_SWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.AMETHYST_SWORD, 1)
                .pattern("A")
                .pattern("A")
                .pattern("S")
                .input('S', Items.STICK)
                .input('A', Items.AMETHYST_SHARD)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.AMETHYST_SWORD)));

        ShapedRecipeJsonBuilder.create(RecipeCategory.MISC, ModItems.WOODEN_DAGGER, 1)
                .pattern("  F")
                .pattern(" T ")
                .pattern("S  ")
                .input('S', Items.STICK)
                .input('F', Items.FLINT)
                .input('T', ModItems.TITANIUM_INGOT)
                .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                .criterion(hasItem(Items.FLINT), conditionsFromItem(Items.FLINT))
                .criterion(hasItem(ModItems.TITANIUM_INGOT), conditionsFromItem(ModItems. TITANIUM_INGOT))
                .offerTo(exporter, new Identifier(getRecipeName(ModItems.WOODEN_DAGGER)));

//        BiggerShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.BIGGER_CRAFTING_TABLE,1 )
//              .pattern("BPPB")
//              .pattern("ECCS")
//              .pattern("ACCR")
//              .pattern("BPPB")
//              .inputWithCriterion('B', Items.BLUE_DYE)
//              .inputWithCriterion('P', ItemTags.PLANKS)
//              .inputWithCriterion('E', Items.EMERALD)
//              .inputWithCriterion('A', Items.AMETHYST_SHARD)
//              .inputWithCriterion('S', ModItems.SAPPHIRE)
//              .inputWithCriterion('R', ModItems.RUBY)
//              .inputWithCriterion('C', Blocks.CRAFTING_TABLE)
//              .offerTo(exporter);

        ShapedRecipeJsonBuilder.create(RecipeCategory.COMBAT, ModItems.SAPPHIRE_SWORD, 1 )
                        .pattern("S")
                        .pattern("S")
                        .pattern("E")
                        .input('S', ModItems.SAPPHIRE)
                        .input('E', Items.STICK)
                        .criterion(hasItem(ModItems.SAPPHIRE), conditionsFromItem(ModItems.SAPPHIRE))
                        .criterion(hasItem(Items.STICK), conditionsFromItem(Items.STICK))
                        .offerTo(exporter, new Identifier(getRecipeName(ModItems.SAPPHIRE_SWORD)));



        ShapedRecipeJsonBuilder.create(RecipeCategory.DECORATIONS, ModBlocks.BIGGER_CRAFTING_TABLE,1 )
        .pattern("EBS")
        .pattern("PCP")
        .pattern("ABR")
        .input('B', Items.BLUE_DYE)
        .input('P', ItemTags.PLANKS)
        .input('E', Items.EMERALD)
        .input('A', Items.AMETHYST_SHARD)
        .input('S', ModItems.SAPPHIRE)
        .input('R', ModItems.RUBY)
        .input('C', Blocks.CRAFTING_TABLE)
        .criterion(hasItem(ModItems.SAPPHIRE), conditionsFromItem(ModItems.SAPPHIRE))
        .criterion(hasItem(ModItems.RUBY), conditionsFromItem(ModItems.RUBY))
        .criterion(hasItem(Items.EMERALD), conditionsFromItem(Items.EMERALD))
        .criterion(hasItem(Items.AMETHYST_SHARD), conditionsFromItem(Items.AMETHYST_SHARD))
        .criterion(hasItem(Items.BLUE_DYE), conditionsFromItem(Items.BLUE_DYE))
        .criterion(hasItem(Blocks.CRAFTING_TABLE), conditionsFromItem(Blocks.CRAFTING_TABLE))
        .criterion("has_planks", conditionsFromTag(ItemTags.PLANKS))
        .offerTo(exporter, new Identifier(getRecipeName(ModBlocks.BIGGER_CRAFTING_TABLE)));

    }
}