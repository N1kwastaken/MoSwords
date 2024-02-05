package net.n1kwastaken.moswords.item.custom;

import net.minecraft.entity.Entity;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.effect.StatusEffectInstance;
import net.minecraft.entity.effect.StatusEffects;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ArmorItem;
import net.minecraft.item.ArmorMaterial;
import net.minecraft.item.ItemStack;
import net.minecraft.world.World;

public class MercuryBoots extends ArmorItem {
    public MercuryBoots(ArmorMaterial material, Type type, Settings settings) {
        super(material, type, settings);
    }

    @Override
    public void inventoryTick(ItemStack stack, World world, Entity entity, int slot, boolean selected) {
        if (entity instanceof PlayerEntity player) {
            if (player.getEquippedStack(EquipmentSlot.FEET).equals(stack)) {
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.FIRE_RESISTANCE, 20*10, 0)); // Resistência ao fogo por 5 minutos
                player.addStatusEffect(new StatusEffectInstance(StatusEffects.SPEED, 20*10, 0)); // Aumento de velocidade por 5 minutos
            }
        }
    }
}