package net.n1kwastaken.moswords.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import net.minecraft.client.gui.screen.recipebook.RecipeResultCollection;
import net.minecraft.client.recipebook.ClientRecipeBook;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.n1kwastaken.moswords.recipe.BiggerCraftingRecipe;
import net.n1kwastaken.moswords.screen.BiggerCraftingScreenHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Environment(EnvType.CLIENT)
@Mixin(RecipeBookWidget.class)
public abstract class RecipeBookWidgetMixin {
    @Shadow protected AbstractRecipeScreenHandler<?> craftingScreenHandler;
    @Shadow @Final private RecipeMatcher recipeFinder;
    @Shadow private ClientRecipeBook recipeBook;

    @Inject(method = "refreshResults", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/gui/screen/recipebook/RecipeBookResults;setResults(Ljava/util/List;Z)V"))
    private void onRefreshResults(boolean resetCurrentPage, CallbackInfo ci, @Local(name = "list2") List<RecipeResultCollection> recipes) {
        if (this.craftingScreenHandler instanceof BiggerCraftingScreenHandler biggerCraftingScreenHandler &&
                biggerCraftingScreenHandler.onlyBiggerRecipes) {
            recipes.replaceAll(resultCollection -> {
                List<RecipeEntry<?>> newRecipes = new ArrayList<>(resultCollection.getAllRecipes());
                newRecipes.removeIf(recipeEntry -> !(recipeEntry.value() instanceof BiggerCraftingRecipe));
                RecipeResultCollection newResultCollection = new RecipeResultCollection(resultCollection.getRegistryManager(), newRecipes);
                newResultCollection.computeCraftables(this.recipeFinder, this.craftingScreenHandler.getCraftingWidth(), this.craftingScreenHandler.getCraftingHeight(), this.recipeBook);
                return newResultCollection;
            });
            recipes.removeIf(resultCollection -> resultCollection.getAllRecipes().isEmpty());
        }
    }
}
