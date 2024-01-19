package net.n1kwastaken.moswords.recipe;

import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeType;

import java.util.List;

public interface BiggerCraftingRecipe extends CraftingRecipe {
    @Override
    default public RecipeType<?> getType() {
        return ModRecipeTypes.BIGGER_CRAFTING;
    }

    public List<Ingredient> getIngredientsWithEmpty();
}
