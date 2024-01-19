package net.n1kwastaken.moswords.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.recipe.RecipeEntry;
import net.n1kwastaken.moswords.recipe.BiggerCraftingRecipe;

import java.util.List;

public class BiggerCraftingDisplay extends BasicDisplay {
    public BiggerCraftingDisplay(RecipeEntry<BiggerCraftingRecipe> recipe) {
        super(getInputList(recipe.value()), List.of(EntryIngredients.of(recipe.value().getResult(null))));
    }

    private static List<EntryIngredient> getInputList(BiggerCraftingRecipe recipe) {
        return EntryIngredients.ofIngredients(recipe.getIngredientsWithEmpty());
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return BiggerCraftingCategory.BIGGER_CRAFTING;
    }
}
