package net.n1kwastaken.moswords.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentTarget;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.n1kwastaken.moswords.MoSwords;

public class ModEnchantments {
    public static Enchantment FREEZING = register("freezing",
            new Freezing(Enchantment.Rarity.COMMON,
                    EnchantmentTarget.WEAPON, EquipmentSlot.MAINHAND));

    private static Enchantment register(String name, Enchantment enchantment) {
        return Registry.register(Registries.ENCHANTMENT, new Identifier(MoSwords.MOD_ID, name), enchantment);
    }

    public static void registerModEnchantments(){
        System.out.println("Registering Enchantments for " + MoSwords.MOD_ID);
    }

}
