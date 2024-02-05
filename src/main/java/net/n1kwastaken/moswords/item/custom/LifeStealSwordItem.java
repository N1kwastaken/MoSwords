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
        AttackEntityCallback.EVENT.register((player, world, hand, entity, hitResult) -> {
            if (player.getStackInHand(hand).getItem() instanceof LifeStealSwordItem) {
                if (entity instanceof LivingEntity) {
                    healthMap.put((LivingEntity) entity, ((LivingEntity) entity).getHealth());
                }
            }
            return ActionResult.PASS;
        });
    }

    @Override
    public boolean postHit(ItemStack stack, LivingEntity target, LivingEntity attacker) {
        if (!(attacker instanceof PlayerEntity)) {
            return super.postHit(stack, target, attacker);
        }

        PlayerEntity player = (PlayerEntity) attacker;
        float previousHealth = healthMap.getOrDefault(target, target.getHealth());
        float dealtDamage = previousHealth - target.getHealth();
        player.heal(dealtDamage);

        return super.postHit(stack, target, attacker);
    }
}



