package net.n1kwastaken.moswords.item.custom;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.minecraft.entity.effect.StatusEffect;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.item.FoodComponent;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.util.UseAction;
import net.minecraft.entity.LivingEntity;
import net.minecraft.world.World;

public class BloodBottle extends Item {
    public BloodBottle() {
        super(new FabricItemSettings().food(new FoodComponent.Builder().hunger(1).
                saturationModifier(0.1f)
                .statusEffect(new StatusEffectInstance(StatusEffects.POISON, 50*10), 0.8f)
                .statusEffect(new StatusEffectInstance(StatusEffects.NAUSEA, 20*10), 1.0f)
                .build()));
    }
    @Override
    public ItemStack finishUsing(ItemStack stack, World world, LivingEntity user) {
        super.finishUsing(stack, world, user);
        return new ItemStack(Items.GLASS_BOTTLE);

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