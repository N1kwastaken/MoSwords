package net.n1kwastaken.moswords.item;

//import net.fabricmc.yarn.constants.MiningLevels;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

import java.util.function.Supplier;

public enum ModToolMaterial implements ToolMaterial {
    RUBY(5, 4000, 5, 6, 40,
            () -> Ingredient.ofItems(ModItems.RUBY)),
    SAPHIRE(4, 3000, 16, 3, 30,
            () -> Ingredient.ofItems(ModItems.SAPHIRE));


    private final int miningLevel;
    private final int ItemDurability;
    private final float miningSpeed;
    private final float attack;
    private final int enchantability;
    private final Supplier<Ingredient> repairIngredient;

    ModToolMaterial(int miningLevel, int itemDurability, float miningSpeed, float attack, int enchantability, Supplier<Ingredient> repairIngredient) {
        this.miningLevel = miningLevel;
        this.ItemDurability = itemDurability;
        this.miningSpeed = miningSpeed;
        this.attack = attack;
        this.enchantability = enchantability;
        this.repairIngredient = repairIngredient;
    }

    

    @Override
    public int getDurability() {
        return this.ItemDurability;
    }

    @Override
    public float getMiningSpeedMultiplier() {
        return this.miningSpeed;
    }

    @Override
    public float getAttackDamage() {
        return this.attack;
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
