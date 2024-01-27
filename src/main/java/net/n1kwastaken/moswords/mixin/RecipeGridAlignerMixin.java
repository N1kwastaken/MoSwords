package net.n1kwastaken.moswords.mixin;

import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeGridAligner;
import net.n1kwastaken.moswords.recipe.BiggerShapedRecipe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

import java.util.Iterator;

@Mixin(RecipeGridAligner.class)
public interface RecipeGridAlignerMixin {
    @ModifyVariable(method = "alignRecipeToGrid", at = @At("STORE"), index = 7)
    private int changeWidth(int prevWidth, int gridWidth, int gridHeight, int gridOutputSlot, RecipeEntry<?> recipe, Iterator<Integer> inputs, int amount) {
        if (recipe.value() instanceof BiggerShapedRecipe biggerShapedRecipe) {
            return biggerShapedRecipe.getWidth();
        }
        return prevWidth;
    }

    @ModifyVariable(method = "alignRecipeToGrid", at = @At("STORE"), index = 8)
    private int changeHeight(int prevHeight, int gridWidth, int gridHeight, int gridOutputSlot, RecipeEntry<?> recipe, Iterator<Integer> inputs, int amount) {
        if (recipe.value() instanceof BiggerShapedRecipe biggerShapedRecipe) {
            return biggerShapedRecipe.getHeight();
        }
        return prevHeight;
    }
}
