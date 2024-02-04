package net.n1kwastaken.moswords.mixin;

import net.minecraft.entity.Entity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.passive.VillagerEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.util.Hand;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.At;


import static net.n1kwastaken.moswords.item.ModItems.BLOODY_WOODEN_DAGGER;
import static net.n1kwastaken.moswords.item.ModItems.WOODEN_DAGGER;

@Mixin(VillagerEntity.class)
public class MixinVillagerEntity {
    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci) {
        Entity entity = source.getAttacker();
        if (entity instanceof PlayerEntity) {
            PlayerEntity player = (PlayerEntity) entity;
            ItemStack heldItem = player.getMainHandStack();
            // Verifique se o item segurado é a sua espada
            if (heldItem.getItem() == WOODEN_DAGGER) {
                // Troque a espada pelo item que você quer dar
                player.setStackInHand(Hand.MAIN_HAND, new ItemStack(BLOODY_WOODEN_DAGGER));
            }
        }
    }
}
