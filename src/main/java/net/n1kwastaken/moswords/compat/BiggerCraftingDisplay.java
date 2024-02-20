package net.n1kwastaken.moswords.compat;

import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.SimpleGridMenuDisplay;
import me.shedaniel.rei.api.common.display.basic.BasicDisplay;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.InputIngredient;
import me.shedaniel.rei.api.common.entry.type.VanillaEntryTypes;
import me.shedaniel.rei.api.common.transfer.info.MenuInfo;
import me.shedaniel.rei.api.common.transfer.info.MenuSerializationContext;
import me.shedaniel.rei.api.common.transfer.info.simple.SimpleGridMenuInfo;
import me.shedaniel.rei.api.common.util.CollectionUtils;
import me.shedaniel.rei.api.common.util.EntryIngredients;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.recipe.RecipeEntry;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.util.collection.DefaultedList;
import net.n1kwastaken.moswords.recipe.BiggerCraftingRecipe;
import net.n1kwastaken.moswords.recipe.BiggerShapedRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

public class BiggerCraftingDisplay extends BasicDisplay implements SimpleGridMenuDisplay {
    protected RecipeEntry<BiggerCraftingRecipe> recipe;

    public BiggerCraftingDisplay(RecipeEntry<BiggerCraftingRecipe> recipe) {
        super(
                getInputList(recipe.value()),
                List.of(EntryIngredients.of(recipe.value().getResult(null))),
                Optional.of(recipe.id())
        );
        this.recipe = recipe;
    }

    private static List<EntryIngredient> getInputList(BiggerCraftingRecipe recipe) {
        return EntryIngredients.ofIngredients(recipe.getIngredients());
    }

    public static int getSlotWithSize(BiggerCraftingDisplay display, int index, int craftingGridWidth) {
        return getSlotWithSize(display.getInputWidth(craftingGridWidth, BiggerShapedRecipe.HEIGHT), index, craftingGridWidth);
    }

    public static int getSlotWithSize(int recipeWidth, int index, int craftingGridWidth) {
        int x = index % recipeWidth;
        int y = (index - x) / recipeWidth;
        return craftingGridWidth * y + x;
    }

    @Override
    public CategoryIdentifier<BiggerCraftingDisplay> getCategoryIdentifier() {
        return BiggerCraftingCategory.BIGGER_CRAFTING;
    }

    public RecipeEntry<BiggerCraftingRecipe> getRecipe() {
        return this.recipe;
    }

    @Override
    public int getWidth() {
        return this.recipe.value().getWidth();
    }

    @Override
    public int getHeight() {
        return this.recipe.value().getHeight();
    }

    @Override
    public int getInputWidth(int craftingWidth, int craftingHeight) {
        return this.recipe.value().getInputWidth(craftingWidth, craftingHeight);
    }

    @Override
    public int getInputHeight(int craftingWidth, int craftingHeight) {
        return this.recipe.value().getInputHeight(craftingWidth, craftingHeight);
    }

    public boolean isShapeless() {
        return this.recipe.value().isShapeless();
    }

    @Override
    @SuppressWarnings({"removal", "unchecked"})
    public List<InputIngredient<EntryStack<?>>> getInputIngredients(MenuSerializationContext<?, ?, ?> context, MenuInfo<?, ?> info, boolean fill) {
        int craftingWidth = BiggerShapedRecipe.WIDTH;
        int craftingHeight = BiggerShapedRecipe.HEIGHT;

        if (info instanceof SimpleGridMenuInfo && fill) {
            craftingWidth = ((SimpleGridMenuInfo<ScreenHandler, ?>) info).getCraftingWidth(context.getMenu());
            craftingHeight = ((SimpleGridMenuInfo<ScreenHandler, ?>) info).getCraftingHeight(context.getMenu());
        }

        return this.getInputIngredients(craftingWidth, craftingHeight);
    }

    @Override
    public List<InputIngredient<EntryStack<?>>> getInputIngredients(@Nullable ScreenHandler menu, @Nullable PlayerEntity player) {
        return this.getInputIngredients(BiggerShapedRecipe.WIDTH, BiggerShapedRecipe.HEIGHT);
    }

    public List<InputIngredient<EntryStack<?>>> getInputIngredients() {
        return this.getInputIngredients(BiggerShapedRecipe.WIDTH, BiggerShapedRecipe.HEIGHT);
    }

    public List<InputIngredient<EntryStack<?>>> getInputIngredients(int craftingWidth, int craftingHeight) {
        int inputWidth = this.getInputWidth(craftingWidth, craftingHeight);
        int inputHeight = this.getInputHeight(craftingWidth, craftingHeight);

        List<EntryIngredient> inputEntries = this.getInputEntries();

        return getInputIngredients(craftingWidth, craftingHeight, inputWidth, inputHeight, inputEntries);
    }

    public static List<InputIngredient<EntryStack<?>>> getInputIngredients(int craftingWidth, int craftingHeight, int inputWidth, int inputHeight, List<EntryIngredient> inputEntries) {
        List<InputIngredient<EntryStack<?>>> inputIngredients = new ArrayList<>(craftingWidth * craftingHeight);
        for (int i = 0; i < craftingWidth * craftingHeight; i++) {
            inputIngredients.add(InputIngredient.empty(i));
        }

        for (int i = 0; i < inputEntries.size(); i++) {
            EntryIngredient stacks = inputEntries.get(i);
            if (stacks.isEmpty()) {
                continue;
            }
            int indexInIngredients = getSlotWithSize(inputWidth, i, craftingWidth);
            int slotX = i % inputWidth;
            int slotY = i / inputWidth;

            InputIngredient<EntryStack<?>> ingredient = InputIngredient.of(indexInIngredients, BiggerShapedRecipe.WIDTH * slotY + slotX, stacks);
            int slotIndex = craftingWidth * slotY + slotX;
            inputIngredients.set(slotIndex, ingredient);
        }

        return inputIngredients;
    }

    @SuppressWarnings("removal")
    public <T extends ScreenHandler> List<List<ItemStack>> getOrganisedInputEntries(SimpleGridMenuInfo<T, BiggerCraftingDisplay> menuInfo, T container) {
        return CollectionUtils.map(getOrganisedInputEntries(menuInfo.getCraftingWidth(container), menuInfo.getCraftingHeight(container)), ingredient ->
                CollectionUtils.<EntryStack<?>, ItemStack>filterAndMap(ingredient, stack -> stack.getType() == VanillaEntryTypes.ITEM,
                        EntryStack::castValue));
    }

    public List<EntryIngredient> getOrganisedInputEntries(int craftingWidth, int craftingHeight) {
        List<EntryIngredient> organisedInputEntries = DefaultedList.ofSize(craftingWidth * craftingHeight, EntryIngredient.empty());
        for (int i = 0; i < this.getInputEntries().size(); i++) {
            organisedInputEntries.set(getSlotWithSize(this, i, craftingWidth), this.getInputEntries().get(i));
        }
        return organisedInputEntries;
    }
}
