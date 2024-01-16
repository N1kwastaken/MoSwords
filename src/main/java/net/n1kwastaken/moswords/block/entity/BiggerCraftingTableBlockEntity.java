package net.n1kwastaken.moswords.block.entity;

import net.fabricmc.fabric.api.screenhandler.v1.ExtendedScreenHandlerFactory;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.text.Text;
import net.minecraft.util.collection.DefaultedList;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import net.n1kwastaken.moswords.item.ModItems;
import org.jetbrains.annotations.Nullable;


public class BiggerCraftingTableBlockEntity extends BlockEntity implements ExtendedScreenHandlerFactory, ImplementedInventory {
    private final DefaultedList<ItemStack> inventory = DefaultedList.ofSize(17, ItemStack.EMPTY);

    private static final int INPUT_SLOT_00 = 0;
    private static final int INPUT_SLOT_01 = 1;
    private static final int INPUT_SLOT_02 = 2;
    private static final int INPUT_SLOT_03 = 3;
    private static final int INPUT_SLOT_04 = 4;
    private static final int INPUT_SLOT_05 = 5;
    private static final int INPUT_SLOT_06 = 6;
    private static final int INPUT_SLOT_07 = 7;
    private static final int INPUT_SLOT_08 = 8;
    private static final int INPUT_SLOT_09 = 9;
    private static final int INPUT_SLOT_10 = 10;
    private static final int INPUT_SLOT_11 = 11;
    private static final int INPUT_SLOT_12 = 12;
    private static final int INPUT_SLOT_13 = 13;
    private static final int INPUT_SLOT_14 = 14;
    private static final int INPUT_SLOT_15 = 15;

    private static final int OUTPUT_SLOT = 16;

    public BiggerCraftingTableBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.BIGGER_CRAFTING_TABLE_BLOCK_ENTITY, pos, state);
    }

    // Other properties and methods...

    @Override
    public void writeScreenOpeningData(ServerPlayerEntity player, PacketByteBuf buf) {
        buf.writeBlockPos(this.pos);
    }

    @Override
    public Text getDisplayName() {
        return Text.of("Bigger Crafting Station");
    }

    @Override
    public DefaultedList<ItemStack> getItems() {
        return inventory;
    }

    @Nullable
    @Override
    public ScreenHandler createMenu(int syncId, PlayerInventory playerInventory, PlayerEntity player) {
        return ;
    }

    public void tick(World world, BlockPos pos, BlockState state) {
        if (world.isClient( )){
            return;
        }

        if(isOutputSlotEmptyOrReceivable()){
            if(this.hasRecipe()){
                markDirty(world, pos, state);

                if(hasCraftingFinished()){
                    this.craftItem();
                }
            }
        }else {
            markDirty(world, pos, state);
        }

    }

    private void craftItem() {
    }

    private boolean hasCraftingFinished() {
        return false;
    }

    private boolean hasRecipe() {
        ItemStack result = new ItemStack(ModItems.RUBY);
        boolean hasInput = getStack(INPUT_SLOT_00).getItem() == ModItems.RAW_RUBY;

        return hasInput && canInsertAmountIntoOutputSlot(result) && canInsertItemIntoOutputSlot(result.getItem());
    }

    private boolean canInsertItemIntoOutputSlot(Item item) {
        return this.getStack(OUTPUT_SLOT).getItem() == item || this.getStack(OUTPUT_SLOT).isEmpty();
    }

    private boolean canInsertAmountIntoOutputSlot(ItemStack result) {
        return this.getStack(OUTPUT_SLOT).getCount() + result.getCount() <= getStack(OUTPUT_SLOT).getMaxCount();
    }

    private boolean isOutputSlotEmptyOrReceivable() {
        return this.getStack(OUTPUT_SLOT).isEmpty() || this.getStack(OUTPUT_SLOT).getCount() < this.getStack(OUTPUT_SLOT).getMaxCount();
    }
}
