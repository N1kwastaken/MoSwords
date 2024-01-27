package net.n1kwastaken.moswords.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.n1kwastaken.moswords.MoSwords;

public class ModRecipeTypes {
    public static final RecipeType<BiggerCraftingRecipe> BIGGER_CRAFTING = register("bigger_crafting");

    private static <T extends Recipe<?>> RecipeType<T> register(String id) {
        return Registry.register(Registries.RECIPE_TYPE, new Identifier(MoSwords.MOD_ID, id), new RecipeType<T>() {

            @Override
            public String toString() {
                return id;
            }
        });
    }

    public static void registerModRecipeTypes() {
        MoSwords.LOGGER.info("Registering ModRecipeTypes for " + MoSwords.MOD_ID);
    }
}
