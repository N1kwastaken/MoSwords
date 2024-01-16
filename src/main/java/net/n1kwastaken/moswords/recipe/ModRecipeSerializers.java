package net.n1kwastaken.moswords.recipe;

import net.minecraft.recipe.Recipe;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.n1kwastaken.moswords.MoSwords;

public class ModRecipeSerializers {
    public static final RecipeSerializer<BiggerShapedRecipe> BIGGER_SHAPED = register("bigger_crafting_shaped", new BiggerShapedRecipe.Serializer());

    public static <S extends RecipeSerializer<T>, T extends Recipe<?>> S register(String id, S serializer) {
        return Registry.register(Registries.RECIPE_SERIALIZER, new Identifier(MoSwords.MOD_ID, id), serializer);
    }

    public static void registerModRecipeSerializers() {
        MoSwords.LOGGER.info("Registering ModRecipeSerializers for " + MoSwords.MOD_ID);
    }
}
