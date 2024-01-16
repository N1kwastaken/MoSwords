package net.n1kwastaken.moswords.screen;

import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.resource.featuretoggle.FeatureFlags;
import net.minecraft.screen.ScreenHandler;
import net.minecraft.screen.ScreenHandlerType;
import net.minecraft.util.Identifier;
import net.n1kwastaken.moswords.MoSwords;

public class ModScreenHandlerTypes {
    public static final ScreenHandlerType<BiggerCraftingScreenHandler> BIGGER_CRAFTING = register("bigger_crafting", BiggerCraftingScreenHandler::new);

    private static <T extends ScreenHandler> ScreenHandlerType<T> register(String id, ScreenHandlerType.Factory<T> factory) {
        return Registry.register(Registries.SCREEN_HANDLER, new Identifier(MoSwords.MOD_ID, id), new ScreenHandlerType<>(factory, FeatureFlags.VANILLA_FEATURES));
    }

    public static void registerModScreenHandlerTypes() {
        MoSwords.LOGGER.info("Registering ModScreenHandlerTypes for " + MoSwords.MOD_ID);
    }
}
