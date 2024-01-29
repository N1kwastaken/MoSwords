package net.n1kwastaken.moswords.mixin;

import net.fabricmc.api.EnvType;
import net.fabricmc.api.Environment;
import net.minecraft.client.gui.screen.recipebook.RecipeBookWidget;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.gen.Invoker;

@Environment(EnvType.CLIENT)
@Mixin(RecipeBookWidget.class)
public interface RecipeBookWidgetInvoker {

    @Invoker("refreshResults")
    void invokeRefreshResults(boolean resetCurrentPage);
}
