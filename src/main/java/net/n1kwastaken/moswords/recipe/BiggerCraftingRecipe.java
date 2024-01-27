package net.n1kwastaken.moswords.recipe;

import net.minecraft.recipe.CraftingRecipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.recipe.book.CraftingRecipeCategory;

public interface BiggerCraftingRecipe extends CraftingRecipe {
    int WIDTH = 4;
    int HEIGHT = 4;
    int SIZE = WIDTH * HEIGHT;

    @Override
    default RecipeType<?> getType() {
        return ModRecipeTypes.BIGGER_CRAFTING;
    }

    CraftingRecipeCategory getCategory();

    boolean isShapeless();

    int getWidth();

    int getHeight();

    int getInputWidth(int craftingWidth, int craftingHeight);

    int getInputHeight(int craftingWidth, int craftingHeight);
}
