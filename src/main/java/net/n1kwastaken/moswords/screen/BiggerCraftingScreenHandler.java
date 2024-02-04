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
import net.minecraft.util.collection.DefaultedList;
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
    public boolean onlyBiggerRecipes = false;
    private final RecipeInputInventory input = new CraftingInventory(this, BiggerCraftingRecipe.WIDTH, BiggerCraftingRecipe.HEIGHT);
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
        this.addSlot(new CraftingResultSlot(this.player, this.input, this.result, 0, 133, 44) {
            @Override
            public void onTakeItem(PlayerEntity player, ItemStack stack) {
                this.onCrafted(stack);
                DefaultedList<ItemStack> remainingStacks;
                if (player.getWorld().getRecipeManager().getFirstMatch(ModRecipeTypes.BIGGER_CRAFTING, BiggerCraftingScreenHandler.this.input, player.getWorld()).isPresent()) {
                    remainingStacks = player.getWorld().getRecipeManager().getRemainingStacks(ModRecipeTypes.BIGGER_CRAFTING, BiggerCraftingScreenHandler.this.input, player.getWorld());
                } else {
                    remainingStacks = player.getWorld().getRecipeManager().getRemainingStacks(RecipeType.CRAFTING, BiggerCraftingScreenHandler.this.input, player.getWorld());
                }
                for (int i = 0; i < remainingStacks.size(); ++i) {
                    ItemStack stackInSlot = BiggerCraftingScreenHandler.this.input.getStack(i);
                    ItemStack remainingStack = remainingStacks.get(i);
                    if (!stackInSlot.isEmpty()) {
                        BiggerCraftingScreenHandler.this.input.removeStack(i, 1);
                        stackInSlot = BiggerCraftingScreenHandler.this.input.getStack(i);
                    }
                    if (remainingStack.isEmpty()) continue;
                    if (stackInSlot.isEmpty()) {
                        BiggerCraftingScreenHandler.this.input.setStack(i, remainingStack);
                        continue;
                    }
                    if (ItemStack.canCombine(stackInSlot, remainingStack)) {
                        remainingStack.increment(stackInSlot.getCount());
                        BiggerCraftingScreenHandler.this.input.setStack(i, remainingStack);
                        continue;
                    }
                    if (playerInventory.insertStack(remainingStack)) continue;
                    BiggerCraftingScreenHandler.this.player.dropItem(remainingStack, false);
                }
            }
        });
        // INPUT SLOTS
        for (i = 0; i < BiggerCraftingRecipe.HEIGHT; ++i) {
            for (j = 0; j < BiggerCraftingRecipe.WIDTH; ++j) {
                this.addSlot(new Slot(this.input, j + i * BiggerCraftingRecipe.WIDTH, 21 + j * 18, 17 + i * 18));
            }
        }
        // PLAYER INVENTORY
        for (i = 0; i < 3; ++i) {
            for (j = 0; j < 9; ++j) {
                this.addSlot(new Slot(playerInventory, j + i * 9 + 9, 8 + j * 18, 102 + i * 18));
            }
        }
        // PLAYER HOTBAR
        for (i = 0; i < 9; ++i) {
            this.addSlot(new Slot(playerInventory, i, 8 + i * 18, 160));
        }
    }

    protected static void updateResult(ScreenHandler handler, World world, PlayerEntity player, RecipeInputInventory craftingInventory, CraftingResultInventory resultInventory) {
        if (world.isClient) {
            return;
        }
        ServerPlayerEntity serverPlayerEntity = (ServerPlayerEntity) player;
        ItemStack resultStack = ItemStack.EMPTY;
        Optional<RecipeEntry<BiggerCraftingRecipe>> firstBiggerCraftingResult = world.getRecipeManager().getFirstMatch(ModRecipeTypes.BIGGER_CRAFTING, craftingInventory, world);
        Optional<RecipeEntry<CraftingRecipe>> firstCraftingResult = world.getRecipeManager().getFirstMatch(RecipeType.CRAFTING, craftingInventory, world);


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
        resultInventory.setStack(RESULT_ID, resultStack);
        handler.setPreviousTrackedSlot(RESULT_ID, resultStack);
        serverPlayerEntity.networkHandler.sendPacket(new ScreenHandlerSlotUpdateS2CPacket(handler.syncId, handler.nextRevision(), RESULT_ID, resultStack));
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
        ItemStack newStack = ItemStack.EMPTY;
        Slot slot = this.slots.get(inventorySlotIndex);
        if (slot.hasStack()) {
            ItemStack stackInSlot = slot.getStack();
            newStack = stackInSlot.copy();
            if (inventorySlotIndex == RESULT_ID) { // TAKE FROM OUTPUT
                this.context.run((world, pos) -> stackInSlot.getItem().onCraftByPlayer(stackInSlot, world, player));
                if (!this.insertItem(stackInSlot, INVENTORY_START, HOTBAR_END, true)) {
                    return ItemStack.EMPTY;
                }
                slot.onQuickTransfer(stackInSlot, newStack);
            } else if (inventorySlotIndex >= INVENTORY_START && inventorySlotIndex < HOTBAR_END ? // FROM PLAYER INVENTORY
                    !this.insertItem(stackInSlot, INPUT_START, INPUT_END, false) && // FROM PLAYER INVENTORY; INSERT TO INPUT SLOTS
                            (inventorySlotIndex < INVENTORY_END ? // IS FROM MAIN PLAYER INVENTORY
                                    !this.insertItem(stackInSlot, HOTBAR_START, HOTBAR_END, false) : // INSERT TO HOTBAR
                                    !this.insertItem(stackInSlot, INVENTORY_START, INVENTORY_END, false)) : // FROM HOTBAR; INSERT TO MAIN PLAYER INVENTORY
                    !this.insertItem(stackInSlot, INVENTORY_START, HOTBAR_END, false)) { // FROM INPUT SLOT; INSERT TO PLAYER INVENTORY
                return ItemStack.EMPTY;
            }
            if (stackInSlot.isEmpty()) {
                slot.setStack(ItemStack.EMPTY);
            } else {
                slot.markDirty();
            }
            if (stackInSlot.getCount() == newStack.getCount()) {
                return ItemStack.EMPTY;
            }
            slot.onTakeItem(player, stackInSlot);
            if (inventorySlotIndex == RESULT_ID) {
                player.dropItem(stackInSlot, false);
            }
        }
        return newStack;
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