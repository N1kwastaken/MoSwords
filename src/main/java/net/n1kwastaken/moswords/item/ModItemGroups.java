package net.n1kwastaken.moswords.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.n1kwastaken.moswords.MoSwords;
import net.n1kwastaken.moswords.block.ModBlocks;
import net.minecraft.item.ItemGroup;
import net.minecraft.item.ItemStack;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.text.Text;
import net.minecraft.util.Identifier;

public class ModItemGroups {
    public static final ItemGroup RUBY_GROUP = Registry.register(Registries.ITEM_GROUP,
            new Identifier(MoSwords.MOD_ID, "ruby"),
            FabricItemGroup.builder().displayName(Text.translatable("itemgroup.ruby"))
                    .icon(() -> new ItemStack(ModItems.DEVILS_EYE)).entries((displayContext, entries) -> {
                        entries.add(ModItems.RUBY);
                        entries.add(ModItems.SAPHIRE);
                        entries.add(ModBlocks.RUBY_BLOCK);
                        entries.add(ModBlocks.SAPHIRE_BLOCK);
                        entries.add(ModItems.RAW_RUBY);
                        entries.add(ModItems.RAW_SAPHIRE);
                        entries.add(ModBlocks.RUBY_DEEPSLATE_ORE);
                        entries.add(ModBlocks.SAPHIRE_ORE);

                        entries.add(ModItems.RUBY_SWORD);
                        entries.add(ModItems.BIG_RUBY_SWORD);
                        entries.add(ModItems.DEVILS_EYE_SWORD);
                        entries.add(ModItems.SUN_SWORD);
                        entries.add(ModItems.MOON_SWORD);




                        entries.add(ModItems.DEVILS_EYE);

                    }).build());


    public static void registerItemGroups() {
        MoSwords.LOGGER.info("Registering Item Groups for " + MoSwords.MOD_ID);
    }
}