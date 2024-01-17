package net.n1kwastaken.moswords.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.n1kwastaken.moswords.block.ModBlocks;
import net.n1kwastaken.moswords.recipe.BiggerCraftingRecipe;
import net.n1kwastaken.moswords.recipe.ModRecipeTypes;
import net.n1kwastaken.moswords.screen.BiggerCraftingScreen;

public class MoSwordsREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
//        REIClientPlugin.super.registerCategories(registry);
        registry.add(new BiggerCraftingCategory());
        registry.addWorkstations(BiggerCraftingCategory.BIGGER_CRAFTING, EntryStacks.of(ModBlocks.BIGGER_CRAFTING_TABLE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(BiggerCraftingRecipe.class, ModRecipeTypes.BIGGER_CRAFTING,
                BiggerCraftingDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerClickArea(screen -> new Rectangle(75, 30, 20, 30), BiggerCraftingScreen.class,
                BiggerCraftingCategory.BIGGER_CRAFTING);
    }
}
