package net.n1kwastaken.moswords.compat;

import me.shedaniel.math.Rectangle;
import me.shedaniel.rei.api.client.plugins.REIClientPlugin;
import me.shedaniel.rei.api.client.registry.category.CategoryRegistry;
import me.shedaniel.rei.api.client.registry.display.DisplayRegistry;
import me.shedaniel.rei.api.client.registry.screen.ScreenRegistry;
import me.shedaniel.rei.api.client.registry.transfer.TransferHandlerRegistry;
import me.shedaniel.rei.api.client.registry.transfer.simple.SimpleTransferHandler;
import me.shedaniel.rei.api.common.category.CategoryIdentifier;
import me.shedaniel.rei.api.common.display.Display;
import me.shedaniel.rei.api.common.transfer.info.stack.SlotAccessor;
import me.shedaniel.rei.api.common.util.EntryStacks;
import net.minecraft.client.network.ClientPlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.screen.ScreenHandler;
import net.n1kwastaken.moswords.block.ModBlocks;
import net.n1kwastaken.moswords.recipe.BiggerCraftingRecipe;
import net.n1kwastaken.moswords.recipe.ModRecipeTypes;
import net.n1kwastaken.moswords.screen.BiggerCraftingScreen;
import net.n1kwastaken.moswords.screen.BiggerCraftingScreenHandler;

import java.util.Arrays;
import java.util.stream.Collectors;
import java.util.stream.IntStream;

public class MoSwordsREIClientPlugin implements REIClientPlugin {

    @Override
    public void registerCategories(CategoryRegistry registry) {
        registry.add(new BiggerCraftingCategory());
        registry.addWorkstations(BiggerCraftingCategory.BIGGER_CRAFTING, EntryStacks.of(ModBlocks.BIGGER_CRAFTING_TABLE));
    }

    @Override
    public void registerDisplays(DisplayRegistry registry) {
        registry.registerRecipeFiller(BiggerCraftingRecipe.class, ModRecipeTypes.BIGGER_CRAFTING,
                BiggerCraftingDisplay::new);
    }

    @Override
    public void registerScreens(ScreenRegistry registry) {
        registry.registerContainerClickArea(screen -> new Rectangle(97, 41, 28, 23), BiggerCraftingScreen.class, BiggerCraftingCategory.BIGGER_CRAFTING); // arrow in bigger crafting table
    }

    @Override
    @SuppressWarnings("UnstableApiUsage")
    public void registerTransferHandlers(TransferHandlerRegistry registry) {
        registry.register(SimpleTransferHandler.create(BiggerCraftingScreenHandler.class, BiggerCraftingCategory.BIGGER_CRAFTING,
                new SimpleTransferHandler.IntRange(1, BiggerCraftingRecipe.SIZE + 1)));
        registry.register(createTransferHandlerWithInputSlots(BiggerCraftingScreenHandler.class, CategoryIdentifier.of("minecraft", "plugins/crafting"),
                new int[] {
                        1, 2, 3,
                        5, 6, 7,
                        9, 10, 11
                }));
    }

    @SuppressWarnings("UnstableApiUsage")
    private static SimpleTransferHandler createTransferHandlerWithInputSlots(
            Class<? extends ScreenHandler> containerClass, CategoryIdentifier<? extends Display> categoryIdentifier, int[] inputSlots) {
        return new SimpleTransferHandler() {
            @Override
            public ApplicabilityResult checkApplicable(Context context) {
                if (!containerClass.isInstance(context.getMenu())
                        || !categoryIdentifier.equals(context.getDisplay().getCategoryIdentifier())
                        || context.getContainerScreen() == null) {
                    return ApplicabilityResult.createNotApplicable();
                } else {
                    return ApplicabilityResult.createApplicable();
                }
            }

            @Override
            public Iterable<SlotAccessor> getInputSlots(Context context) {
                return Arrays.stream(inputSlots)
                        .mapToObj(id -> SlotAccessor.fromSlot(context.getMenu().getSlot(id)))
                        .toList();
            }

            @Override
            public Iterable<SlotAccessor> getInventorySlots(Context context) {
                ClientPlayerEntity player = context.getMinecraft().player;
                PlayerInventory inventory = player.getInventory();
                return IntStream.range(0, inventory.main.size())
                        .mapToObj(index -> SlotAccessor.fromPlayerInventory(player, index))
                        .collect(Collectors.toList());
            }
        };
    }
}
