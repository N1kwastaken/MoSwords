package net.n1kwastaken.moswords.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;
import net.n1kwastaken.moswords.MoSwords;
import net.n1kwastaken.moswords.block.ModBlocks;

public class ModItemGroups {
    public static final ItemGroup MOSWORDS_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(MoSwords.MOD_ID, "moswords"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.moswords"))
                    .icon(() -> new ItemStack(ModItems.DEVILS_EYE)).entries((displayContext, entries) -> {

                        //Blocks

                        entries.add(ModBlocks.RUBY_BLOCK);
                        entries.add(ModBlocks.SAPPHIRE_BLOCK);
                        entries.add(ModItems.RAW_RUBY);
                        entries.add(ModItems.RAW_SAPPHIRE);
                        entries.add(ModBlocks.RUBY_DEEPSLATE_ORE);
                        entries.add(ModBlocks.SAPPHIRE_ORE);
                        entries.add(ModBlocks.TITANIUM_ORE);
                        entries.add(ModBlocks.BIGGER_CRAFTING_TABLE);

                        //Items

                        entries.add(ModItems.RUBY);
                        entries.add(ModItems.SAPPHIRE);
                        entries.add(ModItems.TITANIUM_INGOT);
                        entries.add(ModItems.HANDLE);
                        entries.add(ModItems.BAT_WINGS);
                        entries.add(ModItems.BLOOD_BOTTLE);
                        entries.add(ModItems.DEVILS_EYE);

                        //sword
                        entries.add(ModItems.RUBY_SWORD);
                        entries.add(ModItems.BIG_RUBY_SWORD);
                        entries.add(ModItems.DEVILS_EYE_SWORD);
                        entries.add(ModItems.SUN_SWORD);
                        entries.add(ModItems.MOON_SWORD);
                        entries.add(ModItems.SAPPHIRE_SWORD);
                        entries.add(ModItems.THIN_SAPPHIRE_SWORD);
                        entries.add(ModItems.QUADRUPLE_SWORD);
                        entries.add(ModItems.AMETHYST_SWORD);
                        entries.add(ModItems.VAMPIRE_SWORD);
                        entries.add(ModItems.SLIME_SWORD);
                        entries.add(ModItems.EMERALD_SWORD);
                        entries.add(ModItems.EMERALD_STAFF);
                        entries.add(ModItems.WOODEN_DAGGER);
                        entries.add(ModItems.BLOODY_WOODEN_DAGGER);


                    }).build());


    public static void registerModItemGroups() {
        MoSwords.LOGGER.info("Registering ModItemGroups for " + MoSwords.MOD_ID);
    }
}