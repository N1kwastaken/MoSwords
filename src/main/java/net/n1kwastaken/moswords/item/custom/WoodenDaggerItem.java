package net.n1kwastaken.moswords.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.entity.LivingEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.util.Hand;
import net.minecraft.world.World;
import net.n1kwastaken.moswords.item.ModItems;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class WoodenDaggerItem extends SwordItem {
    public WoodenDaggerItem(ToolMaterial toolMaterial, int attackDamage, float attackSpeed, Settings settings) {
        super(toolMaterial, attackDamage, attackSpeed, settings);
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!target.isAlive() /* not sure if this is the same as .isDead() */) {
            ItemStack newStack = new ItemStack(ModItems.BLOODY_WOODEN_DAGGER, stack.getCount());
            if (stack.hasNbt()) {
                newStack.setNbt(stack.getNbt());
            }
            attacker.setStackInHand(Hand.MAIN_HAND, newStack);
            return super.postHit(newStack, target, attacker);
        }
        return super.postHit(stack, target, attacker);
    }
    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("tooltip.moswords.wooden_dagger").formatted(Formatting.DARK_GRAY));
        } else {
            tooltip.add(Text.translatable("tooltip.moswords.press_shift").formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
