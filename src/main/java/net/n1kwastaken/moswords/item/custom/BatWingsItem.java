package net.n1kwastaken.moswords.item.custom;

import net.minecraft.client.gui.screen.Screen;
import net.minecraft.client.item.TooltipContext;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.text.Text;
import net.minecraft.util.Formatting;
import net.minecraft.world.World;
import org.jetbrains.annotations.Nullable;

import java.util.List;

public class BatWingsItem extends Item {
    public BatWingsItem(Settings settings) {
        super(settings);
    }

    @Override
    public void appendTooltip(ItemStack stack, @Nullable World world, List<Text> tooltip, TooltipContext context) {
        if (Screen.hasShiftDown()) {
            tooltip.add(Text.translatable("item.moswords.bat_wings.tooltip.line_1").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("item.moswords.bat_wings.tooltip.line_2").formatted(Formatting.GRAY));
            tooltip.add(Text.translatable("item.moswords.bat_wings.tooltip.line_3").formatted(Formatting.GRAY));
        } else {
            tooltip.add(Text.translatable("tooltip.moswords.press_shift").formatted(Formatting.GRAY));
        }
        super.appendTooltip(stack, world, tooltip, context);
    }
}
