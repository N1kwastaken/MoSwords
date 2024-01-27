package net.n1kwastaken.moswords.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.DataResult;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeMatcher;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.world.World;

public class BiggerShapelessRecipe implements BiggerCraftingRecipe {
    final String group;
    final CraftingRecipeCategory category;
    final ItemStack result;
    final DefaultedList<Ingredient> ingredients;

    public BiggerShapelessRecipe(String group, CraftingRecipeCategory category, ItemStack result, DefaultedList<Ingredient> ingredients) {
        this.group = group;
        this.category = category;
        this.result = result;
        this.ingredients = ingredients;
    }

    @Override
    public RecipeSerializer<?> getSerializer() {
        return ModRecipeSerializers.BIGGER_SHAPELESS;
    }

    @Override
    public String getGroup() {
        return this.group;
    }

    @Override
    public CraftingRecipeCategory getCategory() {
        return this.category;
    }

    @Override
    public boolean isShapeless() {
        return true;
    }

    @Override
    public int getWidth() {
        return 4;
    }

    @Override
    public int getHeight() {
        return 4;
    }

    @Override
    public int getInputWidth(int craftingWidth, int craftingHeight) {
        return Math.min(this.ingredients.size(), craftingWidth);
    }

    @Override
    public int getInputHeight(int craftingWidth, int craftingHeight) {
        return (int) Math.ceil(this.ingredients.size() / (double) getInputWidth(craftingWidth, craftingHeight));
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return this.result;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.ingredients;
    }

    @Override
    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        RecipeMatcher recipeMatcher = new RecipeMatcher();
        int nonEmptyStacks = 0;
        for (int slotIndex = 0; slotIndex < recipeInputInventory.size(); ++slotIndex) {
            ItemStack itemStack = recipeInputInventory.getStack(slotIndex);
            if (itemStack.isEmpty()) continue;
            ++nonEmptyStacks;
            recipeMatcher.addInput(itemStack, 1);
        }
        return nonEmptyStacks == this.ingredients.size() && recipeMatcher.match(this, null);
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager) {
        return this.result.copy();
    }

    @Override
    public boolean fits(int width, int height) {
        return width * height >= this.ingredients.size();
    }

    public static class Serializer
            implements RecipeSerializer<BiggerShapelessRecipe> {
        private static final Codec<BiggerShapelessRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                Codec.STRING.optionalFieldOf("group", "").forGetter(recipe -> recipe.group),
                CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(recipe -> recipe.category),
                ItemStack.RECIPE_RESULT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                Ingredient.DISALLOW_EMPTY_CODEC.listOf().fieldOf("ingredients").flatXmap(ingredientsWithEmpty -> {
                    Ingredient[] ingredients = ingredientsWithEmpty.stream().filter(ingredient -> !ingredient.isEmpty()).toArray(Ingredient[]::new);
                    if (ingredients.length == 0) {
                        return DataResult.error(() -> "No ingredients for shapeless recipe");
                    }
                    if (ingredients.length > BiggerCraftingRecipe.SIZE) {
                        return DataResult.error(() -> "Too many ingredients for shapeless recipe");
                    }
                    return DataResult.success(DefaultedList.copyOf(Ingredient.EMPTY, ingredients));
                }, DataResult::success).forGetter(recipe -> recipe.ingredients)).apply(instance, BiggerShapelessRecipe::new));

        @Override
        public Codec<BiggerShapelessRecipe> codec() {
            return CODEC;
        }

        @Override
        public BiggerShapelessRecipe read(PacketByteBuf packetByteBuf) {
            String group = packetByteBuf.readString();
            CraftingRecipeCategory category = packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
            int ingredientsLength = packetByteBuf.readVarInt();
            DefaultedList<Ingredient> ingredients = DefaultedList.ofSize(ingredientsLength, Ingredient.EMPTY);
            ingredients.replaceAll(ignored -> Ingredient.fromPacket(packetByteBuf));
            ItemStack itemStack = packetByteBuf.readItemStack();
            return new BiggerShapelessRecipe(group, category, itemStack, ingredients);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, BiggerShapelessRecipe biggerShapelessRecipe) {
            packetByteBuf.writeString(biggerShapelessRecipe.group);
            packetByteBuf.writeEnumConstant(biggerShapelessRecipe.category);
            packetByteBuf.writeVarInt(biggerShapelessRecipe.ingredients.size());
            for (Ingredient ingredient : biggerShapelessRecipe.ingredients) {
                ingredient.write(packetByteBuf);
            }
            packetByteBuf.writeItemStack(biggerShapelessRecipe.result);
        }
    }
}