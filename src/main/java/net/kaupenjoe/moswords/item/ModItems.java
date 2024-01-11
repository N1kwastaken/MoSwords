package net.kaupenjoe.moswords.item;

import net.fabricmc.fabric.api.item.v1.FabricItemSettings;
import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroupEntries;
import net.kaupenjoe.moswords.MoSwords;
import net.minecraft.item.Item;
import net.minecraft.item.SwordItem;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;

public class ModItems {
    public static final Item RUBY = registerItem("ruby", new Item(new FabricItemSettings()));
    public static final Item RAW_RUBY = registerItem("raw_ruby", new Item(new FabricItemSettings()));
    public static final Item DEVILS_EYE = registerItem("devils_eye", new Item(new FabricItemSettings()));
    public static final Item SAPHIRE = registerItem("saphire", new Item(new FabricItemSettings()));
    public static final Item RAW_SAPHIRE = registerItem("raw_saphire", new Item(new FabricItemSettings()));

    public static final Item RUBY_SWORD = registerItem("ruby_sword",
            new SwordItem(ModToolMaterial.RUBY, 20, 4, new FabricItemSettings()));
    public static final Item DEVILS_EYE_SWORD = registerItem("devils_eye_sword",
            new SwordItem(ModToolMaterial.RUBY, 50, 0.5f, new FabricItemSettings()));
    public static final Item MOON_SWORD = registerItem("devils_eye_sword",
            new SwordItem(ModToolMaterial.SAPHIRE, 15, 6, new FabricItemSettings()));

    public static final Item SUN_SWORD = registerItem("sun_sword",
            new SwordItem(ModToolMaterial.RUBY, 30, 0.3f, new FabricItemSettings()));

    private static void addItemsToIngredientItemGroup(FabricItemGroupEntries entries) {
        entries.add(RUBY);
        entries.add(RAW_RUBY);
        entries.add(DEVILS_EYE);
        entries.add(RAW_SAPHIRE);
        entries.add(SAPHIRE);
        entries.add(RUBY_SWORD);
        entries.add(DEVILS_EYE);
        entries.add(DEVILS_EYE_SWORD);
        entries.add(SUN_SWORD);
        entries.add(MOON_SWORD);

    }

    private static Item registerItem(String name, Item item) {
        return Registry.register(Registries.ITEM, new Identifier(MoSwords.MOD_ID, name), item);
    }

    public static void registerModItems() {
        MoSwords.LOGGER.info("Registering Mod Items for " + MoSwords.MOD_ID);

      //ItemGroupEvents.modifyEntriesEvent(ItemGroups.INGREDIENTS).register(ModItems::addItemsToIngredientItemGroup);
    }
}