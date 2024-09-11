package net.sdm.sdmloot.mixin;

import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.Items;
import net.sdm.sdmloot.Config;
import net.sdm.sdmloot.IOwnerable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(WitherBoss.class)
public class WitherBossMixin {

    public LivingEntity entity = (LivingEntity) (Object)this;

    @Inject(method = "dropCustomDeathLoot", at = @At(value = "INVOKE", target = "Lnet/minecraft/world/entity/boss/wither/WitherBoss;spawnAtLocation(Lnet/minecraft/world/level/ItemLike;)Lnet/minecraft/world/entity/item/ItemEntity;"), cancellable = true)
    public void sdm$dropCustomDeathLoot(DamageSource damageSource, int i, boolean bl, CallbackInfo ci){
        if(
                (Config.blacklist && Config.entityList.contains(entity.getType())) ||
                (!Config.blacklist && !Config.entityList.contains(entity.getType()))
        ) return;

        ci.cancel();
        if(damageSource.getEntity() instanceof ServerPlayer player) {
            for (ServerPlayer serverPlayer : player.getLevel().players()) {
                ((IOwnerable) entity).setOwnerUUID(serverPlayer.getUUID());
                ItemEntity itemEntity = ((IOwnerable) entity).sdm$spawnAtLocationCustom(Items.NETHER_STAR.getDefaultInstance());
                if (itemEntity != null) {
                    itemEntity.setExtendedLifetime();
                }
            }
        }

    }
}
