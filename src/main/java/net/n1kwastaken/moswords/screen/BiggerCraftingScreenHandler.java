package net.n1kwastaken.moswords.screen;

import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.CraftingInventory;
import net.minecraft.inventory.CraftingResultInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.inventory.RecipeInputInventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.packet.s2c.play.ScreenHandlerSlotUpdateS2CPacket;
import net.minecraft.recipe.*;
import net.minecraft.recipe.book.RecipeBookCategory;
import net.minecraft.screen.AbstractRecipeScreenHandler;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.slot.CraftingResultSlot;
import net.minecraft.screen.slot.Slot;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.world.World;
import net.n1kwastaken.moswords.block.ModBlocks;
import net.n1kwastaken.moswords.recipe.BiggerCraftingRecipe;
import net.n1kwastaken.moswords.recipe.ModRecipeTypes;

import java.util.Optional;

public class BiggerCraftingScreenHandler extends AbstractRecipeScreenHandler<RecipeInputInventory> {
    public static final int RESULT_ID = 0;
    private static final int INPUT_START = 1;
    private static final int INPUT_END = 17;
    private static final int INVENTORY_START = 17;
    private static final int INVENTORY_END = 44;
    private static final int HOTBAR_START = 44;
    private static final int HOTBAR_END = 53;
    private final RecipeInputInventory input = new CraftingInventory(this, 4, 4);
    private final CraftingResultInventory result = new CraftingResultInventory();
    private final ScreenHandlerContext context;
    private final PlayerEntity player;

    public BiggerCraftingScreenHandler(int syncId, PlayerInventory playerInventory) {
        this(syncId, playerInventory, ScreenHandlerContext.EMPTY);
    }

    public BiggerCraftingScreenHandler(int syncId, PlayerInventory playerInventory, ScreenHandlerContext context) {
        super(ModScreenHandlerTypes.BIGGER_CRAFTING, syncId);
        int j;
        int i;
        this.context = context;
        this.player = playerInventory.player;
        this.addSlot(new CraftingResultSlot(playerInventory.player, this.input, this.result, 0, 124, 35));
        // INPUT SLOTS
        for (i = 0; i < 4; ++i) {
            for (j = 0; j < 4; ++j) {
                this.addSlot(new Slot(this.input, j + i * 4, 30 + j * 18, 17 + i * 18));
            }
        }
        // PLAYER INVENTORY
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, /*84 + 9*/ 93 + i * 18));
            }
        }
        // PLAYER HOTBAR
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, /*142 + 9*/ 151));
        }
    }

    protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (world.isClient) {
            return;
        }
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
        ItemStack resultStack = ItemStack.EMPTY;
        Optional<RecipeEntry<BiggerCraftingRecipe>> firstBiggerCraftingResult = world.getServer().getRecipeManager().getFirstMatch(ModRecipeTypes.BIGGER_CRAFTING, craftingInventory, world);
        Optional<RecipeEntry<CraftingRecipe>> firstCraftingResult = world.getServer().getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);
        if (firstBiggerCraftingResult.isPresent() || firstCraftingResult.isPresent()) {
            RecipeEntry<? extends CraftingRecipe> recipeEntry = firstBiggerCraftingResult.isPresent() ? firstBiggerCraftingResult.get() : firstCraftingResult.get();
            CraftingRecipe craftingRecipe = recipeEntry.value();
            if (resultInventory.shouldCraftRecipe(world, serverPlayerEntity, recipeEntry)) {
                ItemStack recipeOutput = craftingRecipe.craft(craftingInventory, world.getRegistryManager());
                if (recipeOutput.isItemEnabled(world.getEnabledFeatures())) {
                    resultStack = recipeOutput;
                }
            }
        }
        resultInventory.setStack(0, resultStack);
        handler.setPreviousTrackedSlot(0, resultStack);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), 0, resultStack));
    }

    @Override
    public void onContentChanged(Inventory inventory) {
        this.context.run((world, pos) -> updateResult(this, world, this.player, this.input, this.result));
    }

    @Override
    public void populateRecipeFinder(RecipeMatcher finder) {
        this.input.provideRecipeInputs(finder);
    }

    @Override
    public void clearCraftingSlots() {
        this.input.clear();
        this.result.clear();
    }

    @Override
    public boolean matches(RecipeEntry<? extends Recipe<RecipeInputInventory>> recipe) {
        return recipe.value().matches(this.input, this.player.getWorld());
    }

    @Override
    public void onClosed(PlayerEntity player) {
        super.onClosed(player);
        this.context.run((world, pos) -> this.dropInventory(player, this.input));
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return canUse(this.context, player, ModBlocks.BIGGER_CRAFTING_TABLE);
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int inventorySlotIndex) {
        ItemStack itemStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(inventorySlotIndex);
        if (slot.hasStack()) {
            ItemStack itemStack2 = slot.getStack();
            itemStack = itemStack2.copy();
            if (inventorySlotIndex == 0) {
                this.context.run((world, pos) -> itemStack2.getItem().onCraftByPlayer(itemStack2, world, player));
                if (!this.insertItem(itemStack2, INVENTORY_START, HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(itemStack2, itemStack);
            } else if (inventorySlotIndex >= INVENTORY_START && inventorySlotIndex < HOTBAR_END ?
                    !this.insertItem(itemStack2, INPUT_START, INPUT_END, false) &&
                            (inventorySlotIndex < INVENTORY_END ?
                                    !this.insertItem(itemStack2, HOTBAR_START, HOTBAR_END, false) :
                                    !this.insertItem(itemStack2, INVENTORY_START, INVENTORY_END, false)) :
                    !this.insertItem(itemStack2, INVENTORY_START, HOTBAR_END, false)) {
                return ItemStack.EMPTY;
            }
            if (itemStack2.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (itemStack2.getCount() == itemStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, itemStack2);
            if (inventorySlotIndex == 0) {
                player.dropItem(itemStack2, false);
            }
        }
        return itemStack;
    }

    @Override
    public boolean canInsertIntoSlot(ItemStack stack, Slot slot) {
        return slot.inventory != this.result && super.canInsertIntoSlot(stack, slot);
    }

    @Override
    public int getCraftingResultSlotIndex() {
        return RESULT_ID;
    }

    @Override
    public int getCraftingWidth() {
        return this.input.getWidth();
    }

    @Override
    public int getCraftingHeight() {
        return this.input.getHeight();
    }

    @Override
    public int getCraftingSlotCount() {
        return INPUT_END;
    }

    @Override
    public RecipeBookCategory getCategory() {
        return RecipeBookCategory.CRAFTING;
    }

    @Override
    public boolean canInsertIntoSlot(int index) {
        return index != this.getCraftingResultSlotIndex();
    }
}