package net.n1kwastaken.moswords.mixin;

import net.minecraft.enchantment.SweepingEnchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;
import net.minecraft.enchantment.Enchantment;


@Mixin(Enchantment.class)
public class MixinEnchantment {
    @Inject(method = "getMaxLevel", at = @At("HEAD"), cancellable = true)
    private void changeMaxLevel(CallbackInfoReturnable<Integer> cir) {
        if ((Object)this instanceof SweepingEnchantment) {
            cir.setReturnValue(10);
        }
    }
}