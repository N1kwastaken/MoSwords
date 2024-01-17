package net.n1kwastaken.moswords.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.recipe.RecipeEntry;
import net.n1kwastaken.moswords.recipe.BiggerCraftingRecipe;

import java.util.Collections;
import java.util.List;

public class BiggerCraftingDisplay extends BasicDisplay {
    public BiggerCraftingDisplay(List<EntryIngredient> inputs, List<EntryIngredient> outputs) {
        super(inputs, outputs);
    }

    public BiggerCraftingDisplay(RecipeEntry<BiggerCraftingRecipe> recipe) {
        super(getInputList(recipe.value()), List.of(EntryIngredient.of(EntryStacks.of(recipe.value().getResult(null)))));
    }

    private static List<EntryIngredient> getInputList(BiggerCraftingRecipe recipe) {
        if (recipe == null) return Collections.emptyList();
        return EntryIngredients.ofIngredients(recipe.getIngredients());
    }

    @Override
    public CategoryIdentifier<?> getCategoryIdentifier() {
        return BiggerCraftingCategory.BIGGER_CRAFTING;
    }
}
