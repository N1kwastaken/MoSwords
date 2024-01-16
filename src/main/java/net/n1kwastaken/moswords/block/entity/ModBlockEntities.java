package net.n1kwastaken.moswords.block.entity;

import dev.architectury.core.item.ArchitecturySpawnEggItem;
import net.fabricmc.fabric.api.object.builder.v1.block.entity.FabricBlockEntityTypeBuilder;
import net.minecraft.block.entity.BlockEntityType;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.util.Identifier;
import net.n1kwastaken.moswords.MoSwords;
import net.n1kwastaken.moswords.block.ModBlocks;

public class ModBlockEntities {
    public static final BlockEntityType<BiggerCraftingTableBlockEntity> BIGGER_CRAFTING_TABLE_BLOCK_ENTITY =
            Registry.register(Registries.BLOCK_ENTITY_TYPE, new Identifier(MoSwords.MOD_ID, "bigger_crafting_table_be"),
                    FabricBlockEntityTypeBuilder.create(BiggerCraftingTableBlockEntity::new,
                            ModBlocks.CUSTOM_CRAFTING_TABLE).build());

    public static void registerBlockEntities() {
        MoSwords.LOGGER.info("Registering Block Entities for " + MoSwords.MOD_ID);
    }
}
