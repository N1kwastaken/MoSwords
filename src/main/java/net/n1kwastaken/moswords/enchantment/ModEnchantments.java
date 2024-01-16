package net.n1kwastaken.moswords.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.n1kwastaken.moswords.MoSwords;

public class ModEnchantments {
    public static Enchantment SLOWING = register("slowing", new SlowingEnchantment(Enchantment.Rarity.COMMON, EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    private static Enchantment register(String id, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(MoSwords.MOD_ID, id), enchantment);
    }

    public static void registerModEnchantments() {
        MoSwords.LOGGER.info("Registering ModEnchantments for " + MoSwords.MOD_ID);
    }
}
