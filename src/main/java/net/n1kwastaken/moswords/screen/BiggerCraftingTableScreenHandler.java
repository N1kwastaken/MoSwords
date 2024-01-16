package net.n1kwastaken.moswords.screen;

import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.player.PlayerInventory;
import net.minecraft.inventory.Inventory;
import net.minecraft.item.ItemStack;
import net.minecraft.network.PacketByteBuf;
import net.minecraft.screen.ArrayPropertyDelegate;
import net.minecraft.screen.PropertyDelegate;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.n1kwastaken.moswords.block.entity.BiggerCraftingTableBlockEntity;
import org.jetbrains.annotations.Nullable;

public class BiggerCraftingTableScreenHandler extends ScreenHandler {
    private final PropertyDelegate propertyDelegate;
    private final Inventory inventory;
    public final BiggerCraftingTableBlockEntity blockEntity;


    public BiggerCraftingTableScreenHandler(int syncId, PlayerInventory inventory, PacketByteBuf buf) {
        this(syncId, inventory, inventory.player.getWorld().getBlockEntity(buf.readBlockPos()),
                new ArrayPropertyDelegate(2));
    }
    protected BiggerCraftingTableScreenHandler(@Nullable ScreenHandlerType<?> type, int syncId) {
        super(type, syncId);
    }

    public BiggerCraftingTableScreenHandler(int syncId, PlayerInventory inventory,
                                            BlockEntity blockEntity, ArrayPropertyDelegate arrayPropertyDelegate) {
        super(, syncId);
        checkSize(((Inventory)blockEntity), 17);
        this.inventory = ((Inventory)blockEntity);
        inventory.onOpen(inventory.player);
        this.propertyDelegate = arrayPropertyDelegate;
        this.blockEntity = ((BiggerCraftingTableBlockEntity) blockEntity);

        this.addSlot(new Slot(Inventory,))
    }

    @Override
    public ItemStack quickMove(PlayerEntity player, int slot) {
        return null;
    }

    @Override
    public boolean canUse(PlayerEntity player) {
        return false;
    }
}
