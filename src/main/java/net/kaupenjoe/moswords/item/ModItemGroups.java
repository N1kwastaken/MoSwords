package net.kaupenjoe.moswords.item;

import net.fabricmc.fabric.api.itemgroup.v1.FabricItemGroup;
import net.kaupenjoe.moswords.MoSwords;
import net.kaupenjoe.moswords.block.ModBlocks;
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
                       //put here the materials to craft swords

                        entries.add(ModItems.RUBY);
                        entries.add(ModItems.RAW_RUBY);
                        entries.add(ModItems.DEVILS_EYE);


                       //here put blocks

                        entries.add(ModBlocks.RUBY_BLOCK);
                        entries.add(ModBlocks.RUBY_DEEPSLATE_ORE);

                       //and here the extras

                       //here put the swords


                    }).build());


    public static void registerItemGroups() {
        MoSwords.LOGGER.info("Registering Item Groups for " + MoSwords.MOD_ID);
    }
}