package net.sdm.sdmloot.mixin;


import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootParams;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.parameters.LootContextParamSets;
import net.sdm.sdmloot.Config;
import net.sdm.sdmloot.IOwnerable;
import net.sdm.sdmloot.entity.IndividualItemEntity;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.LocalCapture;

import java.util.UUID;

@Mixin(LivingEntity.class)
public abstract class LivingEntityMixin implements IOwnerable {

    @Shadow public abstract void remove(Entity.RemovalReason arg);

    @Shadow public abstract boolean isUsingItem();

    public LivingEntity entity = (LivingEntity)(Object)this;
    public UUID ownerID = null;

    @Inject(method = "dropFromLootTable", at = @At(value = "INVOKE",
            target = "Lnet/minecraft/world/level/storage/loot/LootTable;getRandomItems(Lnet/minecraft/world/level/storage/loot/LootParams;JLjava/util/function/Consumer;)V"),
            locals = LocalCapture.CAPTURE_FAILHARD,
            cancellable = true)
    public void sdm$dropFromLootTable(DamageSource damageSource, boolean bl, CallbackInfo ci, ResourceLocation resourceLocation, LootTable lootTable, LootParams.Builder builder, LootParams lootParams){
        if(damageSource.getEntity() instanceof ServerPlayer player){
            if(
                    (Config.blacklist && Config.entityList.contains(entity.getType())) ||
                            (!Config.blacklist && !Config.entityList.contains(entity.getType()))
            ) return;

            ci.cancel();
            for (ServerPlayer serverPlayer : player.serverLevel().players()) {
                ownerID = serverPlayer.getUUID();
                lootTable.getRandomItems(builder.create(LootContextParamSets.ENTITY), this::sdm$spawnAtLocationCustom);
            }
        }
    }

    @Unique
    @Override
    public ItemEntity sdm$spawnAtLocationCustom(ItemStack itemStack) {
        return sdm$spawnAtLocationCustom(itemStack, 0.0f);
    }

    @Override
    public void setOwnerUUID(UUID uuid) {
        this.ownerID = uuid;
    }

    @Unique
    public ItemEntity sdm$spawnAtLocationCustom(ItemStack arg, float f) {
        if (arg.isEmpty()) {
            return null;
        } else if (entity.level().isClientSide) {
            return null;
        } else {
            IndividualItemEntity itementity = new IndividualItemEntity(entity.level(), entity.getX(), entity.getY() + (double)f, entity.getZ(), arg);
            itementity.setItemOwner(ownerID);
            itementity.setDefaultPickUpDelay();
            entity.level().addFreshEntity(itementity);
            return itementity;
        }
    }

    @Override
    public UUID getOwnerUUID() {
        return ownerID;
    }
}
