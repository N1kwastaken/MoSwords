package net.n1kwastaken.moswords.mixin;

import net.minecraft.block.CraftingTableBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.screen.CraftingScreenHandler;
import net.minecraft.screen.ScreenHandlerContext;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(CraftingScreenHandler.class)
public abstract class CraftingScreenHandlerMixin {

    @Shadow
    @Final
    private ScreenHandlerContext context;

    @Inject(method = "canUse", at = @At("HEAD"), cancellable = true)
    private void canUse(PlayerEntity player, CallbackInfoReturnable<Boolean> cir) {
        if (context.get((world, pos) -> world.getBlockState(pos).getBlock() instanceof CraftingTableBlock, true)) {
            cir.setReturnValue(true);
        }
    }
}
