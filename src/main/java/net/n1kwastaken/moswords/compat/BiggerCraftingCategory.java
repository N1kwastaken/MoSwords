package net.n1kwastaken.moswords.compat;

import com.google.common.collect.Lists;
import me.shedaniel.math.Point;
import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.gui.Renderer;
import me.shedaniel.rei.api.client.gui.widgets.Slot;
import me.shedaniel.rei.api.client.gui.widgets.Widget;
import me.shedaniel.rei.api.client.gui.widgets.Widgets;
import me.shedaniel.rei.api.client.registry.display.DisplayCategory;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.DisplayMerger;
import me.shedaniel.rei.api.common.entry.EntryIngredient;
import me.shedaniel.rei.api.common.entry.EntryStack;
import me.shedaniel.rei.api.common.entry.InputIngredient;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.text.Text;
import net.n1kwastaken.moswords.MoSwords;
import net.n1kwastaken.moswords.block.ModBlocks;
import net.n1kwastaken.moswords.recipe.BiggerCraftingRecipe;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Iterator;
import java.util.List;

public class BiggerCraftingCategory implements DisplayCategory<BiggerCraftingDisplay> {
    public static final CategoryIdentifier<BiggerCraftingDisplay> BIGGER_CRAFTING =
            CategoryIdentifier.of(MoSwords.MOD_ID, "bigger_crafting");

    @Override
    public CategoryIdentifier<BiggerCraftingDisplay> getCategoryIdentifier() {
        return BIGGER_CRAFTING;
    }

    @Override
    public Text getTitle() {
        return Text.literal("Bigger Crafting Table");
    }

    @Override
    public Renderer getIcon() {
        return EntryStacks.of(ModBlocks.BIGGER_CRAFTING_TABLE.asItem().getDefaultStack());
    }

    @Override
    public List<Widget> setupDisplay(BiggerCraftingDisplay display, Rectangle bounds) {
        final Point startPoint = new Point(bounds.getCenterX() - 58, bounds.getCenterY() - 36);

        List<Widget> widgets = new ArrayList<>();

        widgets.add(Widgets.createRecipeBase(bounds));
        widgets.add(Widgets.createArrow(new Point(startPoint.x + 69, startPoint.y + 27)));
        widgets.add(Widgets.createResultSlotBackground(new Point(startPoint.x + 104, startPoint.y + 28)));
        List<InputIngredient<EntryStack<?>>> input = display.getInputIngredients(BiggerCraftingRecipe.WIDTH, BiggerCraftingRecipe.HEIGHT);
        List<Slot> slots = Lists.newArrayList();
        for (int y = 0; y < BiggerCraftingRecipe.HEIGHT; y++) {
            for (int x = 0; x < BiggerCraftingRecipe.WIDTH; x++) {
                slots.add(Widgets.createSlot(new Point(startPoint.x - 8 + x * 18, startPoint.y + 1 + y * 18)).markInput());
            }
        }
        for (InputIngredient<EntryStack<?>> ingredient : input) {
            slots.get(ingredient.getIndex()).entries(ingredient.get());
        }
        widgets.addAll(slots);
        widgets.add(Widgets.createSlot(new Point(startPoint.x + 104, startPoint.y + 28)).entries(display.getOutputEntries().get(0)).disableBackground().markOutput());
        if (display.isShapeless()) {
            widgets.add(Widgets.createShapelessIcon(bounds));
        }

        return widgets;
    }

    @Override
    public int getDisplayHeight() {
        return 84;
    }

    @Override
    public int getDisplayWidth(BiggerCraftingDisplay display) {
        return 168;
    }

    @Override
    @Nullable
    public DisplayMerger<BiggerCraftingDisplay> getDisplayMerger() {
        return new DisplayMerger<>() {
            @Override
            public boolean canMerge(BiggerCraftingDisplay first, BiggerCraftingDisplay second) {
                if (!first.getCategoryIdentifier().equals(second.getCategoryIdentifier())) return false;
                if (!equals(first.getOrganisedInputEntries(BiggerCraftingRecipe.WIDTH, BiggerCraftingRecipe.HEIGHT),
                        second.getOrganisedInputEntries(BiggerCraftingRecipe.WIDTH, BiggerCraftingRecipe.HEIGHT))) return false;
                if (!equals(first.getOutputEntries(), second.getOutputEntries())) return false;
                if (first.isShapeless() != second.isShapeless()) return false;
                if (first.getWidth() != second.getWidth()) return false;
                if (first.getHeight() != second.getHeight()) return false;
                return true;
            }

            @Override
            public int hashOf(BiggerCraftingDisplay display) {
                return display.getCategoryIdentifier().hashCode() * 31 * 31 * 31 +
                        display.getOrganisedInputEntries(BiggerCraftingRecipe.WIDTH, BiggerCraftingRecipe.HEIGHT).hashCode() * 31 * 31 +
                        display.getOutputEntries().hashCode();
            }

            private boolean equals(List<EntryIngredient> firstEntries, List<EntryIngredient> secondEntries) {
                if (firstEntries.size() != secondEntries.size()) return false;
                Iterator<EntryIngredient> firstEntriesIterator = firstEntries.iterator();
                Iterator<EntryIngredient> secondEntriesIterator = secondEntries.iterator();
                while (firstEntriesIterator.hasNext() && secondEntriesIterator.hasNext()) {
                    if (!firstEntriesIterator.next().equals(secondEntriesIterator.next())) return false;
                }
                return true;
            }
        };
    }
}
