package net.n1kwastaken.moswords.recipe;

import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;

public interface BiggerCraftingRecipe extends CraftingRecipe {
    @Override
    public RecipeType<?> getType();
}
