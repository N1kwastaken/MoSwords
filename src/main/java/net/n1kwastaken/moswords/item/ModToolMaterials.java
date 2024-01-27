package net.n1kwastaken.moswords.item;

import net.minecraft.item.Items;
import net.minecraft.item.ToolMaterial;
import net.minecraft.recipe.Ingredient;

public class ModToolMaterials {
    public static final ToolMaterial RUBY = new ModToolMaterial(5, 4000, -1, 6, 40, () -> Ingredient.ofItems(ModItems.RUBY));
    public static final ToolMaterial SAPPHIRE = new ModToolMaterial(4, 3000, 10, 2, 30, () -> Ingredient.ofItems(ModItems.SAPPHIRE));
    public static final ToolMaterial AMETHYST = new ModToolMaterial(3, 500, 5, 4, 35, () -> Ingredient.ofItems(Items.AMETHYST_SHARD));
    public static final ToolMaterial EMARS = new ModToolMaterial(5, 6000, 1, 10, 40, () -> Ingredient.ofItems(ModItems.SAPPHIRE));
    public static final ToolMaterial EMERALD = new ModToolMaterial(4, 4500, 2, 5, 45, () -> Ingredient.ofItems(Items.EMERALD));
    public static final ToolMaterial SLIME = new ModToolMaterial(2, 250, 10, 3, 25, () -> Ingredient.ofItems(Items.SLIME_BALL));
    public static final ToolMaterial TITANIUM = new ModToolMaterial(3, 10000, 1, 5, 30, () -> Ingredient.ofItems(ModItems.TITANIUM_INGOT));
}
