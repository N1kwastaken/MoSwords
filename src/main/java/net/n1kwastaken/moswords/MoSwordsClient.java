package net.n1kwastaken.moswords;

import net.fabricmc.api.ClientModInitializer;
import net.minecraft.client.gui.screen.ingame.HandledScreens;
import net.n1kwastaken.moswords.screen.BiggerCraftingScreen;
import net.n1kwastaken.moswords.screen.ModScreenHandlerTypes;

public class MoSwordsClient implements ClientModInitializer {
    @Override
    public void onInitializeClient() {
        HandledScreens.register(ModScreenHandlerTypes.BIGGER_CRAFTING, BiggerCraftingScreen::new);
    }
}
