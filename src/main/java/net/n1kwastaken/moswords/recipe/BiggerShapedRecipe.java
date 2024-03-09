package net.n1kwastaken.moswords.recipe;

import com.mojang.serialization.Codec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.recipe.Ingredient;
import net.minecraft.recipe.RecipeSerializer;
import net.minecraft.recipe.book.CraftingRecipeCategory;
import net.minecraft.registry.DynamicRegistryManager;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.dynamic.Codecs;
import net.minecraft.world.World;

public class BiggerShapedRecipe implements BiggerCraftingRecipe {
    public static final int WIDTH = 4;
    public static final int HEIGHT = 4;

    protected final RawBiggerShapedRecipe raw;
    protected final ItemStack result;
    protected final String group;
    protected final CraftingRecipeCategory category;
    protected final boolean showNotification;

    public BiggerShapedRecipe(String group, CraftingRecipeCategory category, RawBiggerShapedRecipe raw, ItemStack result, boolean showNotification) {
        this.group = group;
        this.category = category;
        this.raw = raw;
        this.result = result;
        this.showNotification = showNotification;
    }

    public BiggerShapedRecipe(String group, CraftingRecipeCategory category, RawBiggerShapedRecipe raw, ItemStack result) {
        this(group, category, raw, result, true);
    }

    @Override
    public RecipeSerializer<BiggerShapedRecipe> getSerializer() {
        return ModRecipeSerializers.BIGGER_SHAPED;
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
        return false;
    }

    @Override
    public ItemStack getResult(DynamicRegistryManager registryManager) {
        return this.result;
    }

    @Override
    public DefaultedList<Ingredient> getIngredients() {
        return this.raw.ingredients();
    }

    @Override
    public boolean showNotification() {
        return this.showNotification;
    }

    @Override
    public boolean fits(int width, int height) {
        return width >= this.raw.width() && height >= this.raw.height();
    }

    @Override
    public boolean matches(RecipeInputInventory recipeInputInventory, World world) {
        return this.raw.matches(recipeInputInventory);
    }

    @Override
    public ItemStack craft(RecipeInputInventory recipeInputInventory, DynamicRegistryManager dynamicRegistryManager) {
        return this.getResult(dynamicRegistryManager).copy();
    }

    @Override
    public int getWidth() {
        return this.raw.width();
    }

    @Override
    public int getHeight() {
        return this.raw.height();
    }

    @Override
    public int getInputWidth(int craftingWidth, int craftingHeight) {
        return this.getWidth();
    }

    @Override
    public int getInputHeight(int craftingWidth, int craftingHeight) {
        return this.getHeight();
    }

    @Override
    public boolean isEmpty() {
        DefaultedList<Ingredient> ingredients = this.getIngredients();
        return ingredients.isEmpty() || ingredients.stream().filter(ingredient -> !ingredient.isEmpty()).anyMatch(ingredient -> ingredient.getMatchingStacks().length == 0);
    }

    public static class Serializer implements RecipeSerializer<BiggerShapedRecipe> {
        public static final Codec<BiggerShapedRecipe> CODEC = RecordCodecBuilder.create(instance -> instance.group(
                        Codecs.createStrictOptionalFieldCodec(
                                Codec.STRING, "group", "").forGetter(recipe -> recipe.group),
                        CraftingRecipeCategory.CODEC.fieldOf("category").orElse(CraftingRecipeCategory.MISC).forGetter(recipe -> recipe.category),
                        RawBiggerShapedRecipe.CODEC.forGetter(recipe -> recipe.raw),
                        ItemStack.RECIPE_RESULT_CODEC.fieldOf("result").forGetter(recipe -> recipe.result),
                        Codecs.createStrictOptionalFieldCodec(Codec.BOOL, "show_notification", true).forGetter(recipe -> recipe.showNotification))
                .apply(instance, BiggerShapedRecipe::new));

        @Override
        public Codec<BiggerShapedRecipe> codec() {
            return CODEC;
        }

        @Override
        public BiggerShapedRecipe read(PacketByteBuf packetByteBuf) {
            String group = packetByteBuf.readString();
            CraftingRecipeCategory category = packetByteBuf.readEnumConstant(CraftingRecipeCategory.class);
            RawBiggerShapedRecipe raw = RawBiggerShapedRecipe.readFromBuf(packetByteBuf);
            ItemStack result = packetByteBuf.readItemStack();
            boolean showNotification = packetByteBuf.readBoolean();
            return new BiggerShapedRecipe(group, category, raw, result, showNotification);
        }

        @Override
        public void write(PacketByteBuf packetByteBuf, BiggerShapedRecipe biggerShapedRecipe) {
            packetByteBuf.writeString(biggerShapedRecipe.group);
            packetByteBuf.writeEnumConstant(biggerShapedRecipe.category);
            biggerShapedRecipe.raw.writeToBuf(packetByteBuf);
            packetByteBuf.writeItemStack(biggerShapedRecipe.result);
            packetByteBuf.writeBoolean(biggerShapedRecipe.showNotification);
        }
    }
}
