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

import net.n1kwastaken.moswords.item.ModItems;

@Mixin(VillagerEntity.class)
public class VillagerEntityMixin {
    @Inject(method = "onDeath", at = @At("HEAD"))
    public void onDeath(DamageSource source, CallbackInfo ci) {
        Entity entity = source.getAttacker();
        if (entity instanceof PlayerEntity player) {
            ItemStack heldItem = player.getMainHandStack();
            if (heldItem.getItem() == ModItems.WOODEN_DAGGER) {
                // Crie o novo item com a mesma durabilidade da espada original
                ItemStack newItem = new ItemStack(ModItems.BLOODY_WOODEN_DAGGER);
                newItem.setDamage(heldItem.getDamage());
                // Troque a espada pelo novo item
                player.setStackInHand(Hand.MAIN_HAND, newItem);
            }
        }
    }
}
