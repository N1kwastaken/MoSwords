package net.n1kwastaken.moswords.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.UseAction;
import net.minecraft.world.World;
import net.n1kwastaken.moswords.item.ModItems;
import org.jetbrains.annotations.Nullable;
import java.util.List;
public class RefillablePotion extends Item {
    private int drinkCount;
    public RefillablePotion(Settings settings) {
        super(settings);
        this.drinkCount = 0;
    }
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);

        if (drinkCount < 4) {
            drinkCount++;
            return stack;
        } else {
            this.drinkCount = 0;
            return new ItemStack(ModItems.REFILLABLE_POTION_BOTTLE);
        }
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.moswords.refillable_potion").formatted(Formatting.DARK_GRAY));
        } else {
            tooltip.add(Text.translatable("tooltip.moswords.press_shift.tooltip").formatted(Formatting.GRAY));
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
}

