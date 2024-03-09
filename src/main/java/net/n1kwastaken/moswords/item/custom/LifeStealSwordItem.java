package net.n1kwastaken.moswords.item.custom;

import net.fabricmc.fabric.api.event.player.AttackEntityCallback;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.SwordItem;
import net.minecraft.item.ToolMaterial;
import net.minecraft.util.ActionResult;

import java.util.WeakHashMap;

public class LifeStealSwordItem extends SwordItem {
    private final WeakHashMap<LivingEntity, Float> healthMap = new WeakHashMap<>();

    public LifeStealSwordItem(ToolMaterial material, int attackDamage, float attackSpeed, Settings settings) {
        super(material, attackDamage, attackSpeed, settings);
        AttackEntityCallback.EVENT.register((player, world, hand, target, hitResult) -> { // TODO
            if (player.getStackInHand(hand).getItem() instanceof LifeStealSwordItem) {
                if (target instanceof LivingEntity livingTarget) {
                    healthMap.put(livingTarget, livingTarget.getHealth());
                }
            }
            return ActionResult.PASS;
        });
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof PlayerEntity playerAttacker)) {
            return super.postHit(stack, target, attacker);
        }

        float previousHealth = healthMap.getOrDefault(target, target.getHealth());
        float dealtDamage = previousHealth - target.getHealth();
        playerAttacker.heal(dealtDamage);

        return super.postHit(stack, target, attacker);
    }
}
