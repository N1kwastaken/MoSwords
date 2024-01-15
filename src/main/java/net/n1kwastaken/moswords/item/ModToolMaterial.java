package net.n1kwastaken.moswords.item;

//import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public enum ModToolMaterial implements ToolMaterial {
    RUBY(5, 4000, -1, 6, 40,
            () -> Ingredient.ofItems(ModItems.RUBY)),
    SAPPHIRE(4, 3000, 10, 2, 30,
            () -> Ingredient.ofItems(ModItems.SAPPHIRE)),
    AMETHYST(3, 500, 5, 4, 35,
            () -> Ingredient.ofItems(Items.AMETHYST_SHARD)),

    EMARS(5, 6000, 1, 10, 40,
            () -> Ingredient.ofItems(ModItems.SAPPHIRE)),

    EMERALD(4, 4500, 2, 5, 45,
            () -> Ingredient.ofItems(Items.EMERALD)),

    SLIME(2, 250, 10, 3, 25,
            () -> Ingredient.ofItems(Items.SLIME_BALL));


    private final int miningLevel;
    private final int itemDurability;
    private final float miningSpeed;
    private final float attackDamage;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attack, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.itemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attackDamage = attack;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }



    @Override
    public int getDurability() {
        return this.itemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attackDamage;
    }

    @Override
    public int getMiningLevel() {
        return this.miningLevel;
    }

    @Override
    public int getEnchantability() {
        return this.enchantability;
    }

    @Override
    public Ingredient getRepairIngredient() {
        return this.repairIngredient.get();
    }
}