package net.n1kwastaken.moswords.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsage;
import net.minecraft.nbt.NbtElement;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.util.TypedActionResult;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.n1kwastaken.moswords.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class RefillablePotionItem extends Item {
    public static final String DRINKS_REMAINING_KEY = "drinks_remaining";

    public RefillablePotionItem(Settings settings) {
        super(settings);
    }

    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);

        if (user instanceof PlayerEntity player && player.isCreative()) {
            return stack;
        }

        if (!stack.hasNbt() || !stack.getNbt().contains(DRINKS_REMAINING_KEY, NbtElement.BYTE_TYPE)) {
            stack.getOrCreateNbt().putByte(DRINKS_REMAINING_KEY, (byte) 3);
        }

        if (stack.getNbt().getByte(DRINKS_REMAINING_KEY) <= 1) {
            return new ItemStack(ModItems.REFILLABLE_POTION_BOTTLE);
        }

        stack.getNbt().putByte(DRINKS_REMAINING_KEY, (byte) (stack.getNbt().getByte(DRINKS_REMAINING_KEY) - 1));
        return stack;
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.moswords.refillable_potion.tooltip.line_1").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("item.moswords.refillable_potion.tooltip.line_2").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("item.moswords.refillable_potion.tooltip.line_3").formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.translatable("tooltip.moswords.press_shift").formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }

    @Override
    public UseAction getUseAction(ItemStack stack) {
        return UseAction.DRINK;
    }

    @Override
    public int getMaxUseTime(ItemStack stack) {
        return 32;
    }

    @Override
    public TypedActionResult<ItemStack> use(World world, PlayerEntity user, Hand hand) {
        return ItemUsage.consumeHeldItem(world, user, hand);
    }
}

