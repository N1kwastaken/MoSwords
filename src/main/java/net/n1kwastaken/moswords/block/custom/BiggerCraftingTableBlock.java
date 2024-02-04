package net.n1kwastaken.moswords.block.custom;

import com.mojang.serialization.MapCodec;
import net.minecraft.block.AbstractBlock;
import net.minecraft.block.BlockState;
import net.minecraft.block.CraftingTableBlock;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.screen.NamedScreenHandlerFactory;
import net.minecraft.screen.ScreenHandlerContext;
import net.minecraft.screen.SimpleNamedScreenHandlerFactory;
import net.minecraft.text.Text;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.BlockView;
import net.minecraft.world.World;
import net.n1kwastaken.moswords.screen.BiggerCraftingScreenHandler;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BiggerCraftingTableBlock extends CraftingTableBlock {
    public static final MapCodec<BiggerCraftingTableBlock> CODEC = createCodec(BiggerCraftingTableBlock::new);
    private static final Text TITLE = Text.literal("Bigger Crafting");
    public BiggerCraftingTableBlock(AbstractBlock.Settings settings) {
        super(settings);
    }
    public MapCodec<? extends BiggerCraftingTableBlock> getCodec() {
        return CODEC;
    }
    @Override
    public ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player, Hand hand, BlockHitResult hit) {
        if (world.isClient) {
            return ActionResult.SUCCESS;
        }
        player.openHandledScreen(state.createScreenHandlerFactory(world, pos));
        return ActionResult.CONSUME;
    }
    @Override
    public NamedScreenHandlerFactory createScreenHandlerFactory(BlockState state, World world, BlockPos pos) {
        return new SimpleNamedScreenHandlerFactory((syncId, inventory, player) -> new BiggerCraftingScreenHandler(syncId, inventory, ScreenHandlerContext.create(world, pos)), TITLE);

    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable BlockView world, List<Text> tooltip, TooltipContext options) {
        if (Screen.hasShiftDown()) {
        tooltip.add(Text.translatable("tooltip.moswords.bigger_crafting_table.tooltip").formatted(Formatting.DARK_GRAY));
        } else{
        tooltip.add(Text.translatable("tooltip.moswords.press_shift.tooltip").formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, world, tooltip, options);
    }
}