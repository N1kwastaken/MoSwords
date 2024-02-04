package net.n1kwastaken.moswords.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.item.Item;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.n1kwastaken.moswords.MoSwords;

import javax.swing.*;

public class ModItems {
    public static final Item RUBY = registerItem("ruby", new Item(new FabricItemSettings()));
    public static final Item RAW_RUBY = registerItem("raw_ruby", new Item(new FabricItemSettings()));
    public static final Item DEVILS_EYE = registerItem("devils_eye", new Item(new FabricItemSettings()));
    public static final Item SAPPHIRE = registerItem("sapphire", new Item(new FabricItemSettings()));
    public static final Item RAW_SAPPHIRE = registerItem("raw_sapphire", new Item(new FabricItemSettings()));
    public static final Item TITANIUM_INGOT = registerItem("titanium_ingot", new Item(new FabricItemSettings()));
    public static final Item HANDLE = registerItem("handle", new Item(new FabricItemSettings()));
    public static final Item BAT_WINGS = registerItem("bat_wings", new Item(new FabricItemSettings()));


    public static final Item RUBY_SWORD = registerItem("ruby_sword",
            new SwordItem(ModToolMaterials.RUBY, 2, -2.7f, new FabricItemSettings()));
    public static final Item AMETHYST_SWORD = registerItem("amethyst_sword",
            new SwordItem(ModToolMaterials.RUBY, -2, -1.7f, new FabricItemSettings()));
    public static final Item SAPPHIRE_SWORD = registerItem("sapphire_sword",
            new SwordItem(ModToolMaterials.SAPPHIRE, 3, -1, new FabricItemSettings()));
    public static final Item EMERALD_SWORD = registerItem("emerald_sword",
            new SwordItem(ModToolMaterials.EMERALD, 0, -1.5f, new FabricItemSettings()));
    public static final Item SLIME_SWORD = registerItem("slime_sword",
            new SwordItem(ModToolMaterials.SLIME, -1, 1, new FabricItemSettings()));
    public static final Item EMERALD_STAFF = registerItem("emerald_staff",
            new SwordItem(ModToolMaterials.EMERALD, 9, -3f, new FabricItemSettings()));
    public static final Item ACOUSTIC_GUITAR = registerItem("acoustic_guitar",
            new SwordItem(ModToolMaterials.EMERALD, -1, -3f, new FabricItemSettings()));


    public static final Item DEVILS_EYE_SWORD = registerItem("devils_eye_sword",
            new SwordItem(ModToolMaterials.RUBY, 34, -2.5f, new FabricItemSettings()));
    public static final Item VAMPIRE_SWORD = registerItem("vampire_sword",
            new SwordItem(ModToolMaterials.AMETHYST, 14, 0, new FabricItemSettings()));

    public static final Item MOON_SWORD = registerItem("moon_sword",
            new SwordItem(ModToolMaterials.SAPPHIRE, 15, 2, new FabricItemSettings()));

    public static final Item SUN_SWORD = registerItem("sun_sword",
            new SwordItem(ModToolMaterials.RUBY, 30, -3, new FabricItemSettings()));
    public static final Item BIG_RUBY_SWORD = registerItem("big_ruby_sword",
            new SwordItem(ModToolMaterials.RUBY, 5, -3.5f, new FabricItemSettings()));

    public static final Item THIN_SAPPHIRE_SWORD = registerItem("thin_sapphire_sword",
            new SwordItem(ModToolMaterials.AMETHYST, 0, 1, new FabricItemSettings()));

    public static final Item QUADRUPLE_SWORD = registerItem("quadruple_sword",
            new SwordItem(ModToolMaterials.EMARS, 28, -3.2f, new FabricItemSettings()));


    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(RUBY);
        entries.add(RAW_RUBY);
        entries.add(DEVILS_EYE);
        entries.add(RAW_SAPPHIRE);
        entries.add(SAPPHIRE);
        entries.add(RUBY_SWORD);
        entries.add(DEVILS_EYE_SWORD);
        entries.add(SUN_SWORD);
        entries.add(MOON_SWORD);
        entries.add(BIG_RUBY_SWORD);
        entries.add(SAPPHIRE_SWORD);
        entries.add(THIN_SAPPHIRE_SWORD);
        entries.add(QUADRUPLE_SWORD);
        entries.add(AMETHYST_SWORD);
        entries.add(VAMPIRE_SWORD);
        entries.add(SLIME_SWORD);
        entries.add(EMERALD_SWORD);
        entries.add(ACOUSTIC_GUITAR);
        entries.add(TITANIUM_INGOT);
        entries.add(BAT_WINGS);

    }

    private static Item registerItem(String id, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MoSwords.MOD_ID, id), item);
    }

    public static void registerModItems() {
        MoSwords.LOGGER.info("Registering ModItems for " + MoSwords.MOD_ID);

        ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}