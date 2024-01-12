package net.n1kwastaken.moswords.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.n1kwastaken.moswords.MoSwords;
import net.minecraft.item.*;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

import java.io.StringWriter;

public class ModItems {
    public static final Item RUBY = registerItem("ruby", new Item(new FabricItemSettings()));
    public static final Item RAW_RUBY = registerItem("raw_ruby", new Item(new FabricItemSettings()));
    public static final Item DEVILS_EYE = registerItem("devils_eye", new Item(new FabricItemSettings()));
    public static final Item SAPHIRE = registerItem("saphire", new Item(new FabricItemSettings()));
    public static final Item RAW_SAPHIRE = registerItem("raw_saphire", new Item(new FabricItemSettings()));

    public static final Item RUBY_SWORD = registerItem("ruby_sword",
            new SwordItem(ModToolMaterial.RUBY, 15, 1, new FabricItemSettings()));

    public static final Item SAPHIRE_SWORD = registerItem("saphire_sword",
            new SwordItem(ModToolMaterial.SAPHIRE, 13, 4, new FabricItemSettings()));

    public static final Item DEVILS_EYE_SWORD = registerItem("devils_eye_sword",
            new SwordItem(ModToolMaterial.RUBY, 50, -2.5f, new FabricItemSettings()));
    public static final Item MOON_SWORD = registerItem("moon_sword",
            new SwordItem(ModToolMaterial.SAPHIRE, 15, 2, new FabricItemSettings()));

    public static final Item SUN_SWORD = registerItem("sun_sword",
            new SwordItem(ModToolMaterial.RUBY, 30, -3, new FabricItemSettings()));
    public static final Item BIG_RUBY_SWORD = registerItem("big_ruby_sword",
            new SwordItem(ModToolMaterial.RUBY, 30, -3.5f, new FabricItemSettings()));

    public  static final Item THIN_SAPHIRE_SWORD = registerItem("thin_saphire_sword",
            new SwordItem(ModToolMaterial.SAPHIRE, 12, 6, new FabricItemSettings()));


    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(RUBY);
        entries.add(RAW_RUBY);
        entries.add(DEVILS_EYE);
        entries.add(RAW_SAPHIRE);
        entries.add(SAPHIRE);
        entries.add(RUBY_SWORD);
        entries.add(DEVILS_EYE_SWORD);
        entries.add(SUN_SWORD);
        entries.add(MOON_SWORD);
        entries.add(BIG_RUBY_SWORD);
        entries.add(SAPHIRE_SWORD);
        entries.add(THIN_SAPHIRE_SWORD);

    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MoSwords.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MoSwords.LOGGER.info("Registering Mod Items for " + MoSwords.MOD_ID);

      ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}