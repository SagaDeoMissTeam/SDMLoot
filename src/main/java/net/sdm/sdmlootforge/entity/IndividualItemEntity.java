package net.sdm.sdmlootforge.entity;

import net.minecraft.nbt.CompoundTag;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;

import java.util.UUID;

public class IndividualItemEntity extends ItemEntity {
    private UUID itemOwner;


    public IndividualItemEntity(EntityType<? extends ItemEntity> p_31991_, Level p_31992_) {
        super(p_31991_, p_31992_);
    }

    public IndividualItemEntity(Level level, double x, double y, double z, ItemStack itemStack) {
        this(level, x, y, z, itemStack, level.random.nextDouble() * 0.2 - 0.1, 0.2, level.random.nextDouble() * 0.2 - 0.1);
    }

    public IndividualItemEntity(Level level, double x, double y, double z, ItemStack itemStack, double i, double j, double k) {
        super(level, x, y, z, itemStack, i, j, k);
        this.setPos(x, y, z);
        this.setDeltaMovement(i, j, k);
        this.setItem(itemStack);
    }

    @Override
    public boolean hurt(DamageSource p_32013_, float p_32014_) {
        return false;
    }

    @Override
    public void addAdditionalSaveData(CompoundTag compoundTag) {
        super.addAdditionalSaveData(compoundTag);
        if (this.itemOwner != null) {
            compoundTag.putUUID("ItemOwnerUUID", this.itemOwner);
        }
    }

    @Override
    public void readAdditionalSaveData(CompoundTag compoundTag) {
        super.readAdditionalSaveData(compoundTag);
        if (compoundTag.hasUUID("ItemOwnerUUID")) {
            this.itemOwner = compoundTag.getUUID("ItemOwnerUUID");
        }
    }

    @Override
    public void playerTouch(Player player) {
        if (player.getUUID().equals(this.itemOwner)) {
            this.getItem().removeTagKey("ItemOwnerUUID");
            super.playerTouch(player);
        }
    }

    @Override
    public boolean broadcastToPlayer(ServerPlayer serverPlayer) {
        return serverPlayer.getUUID().equals(this.itemOwner) && super.broadcastToPlayer(serverPlayer);
    }


    public void setItemOwner(UUID itemOwner) {
        this.itemOwner = itemOwner;
        CompoundTag compoundTag = new CompoundTag();
        compoundTag.putUUID("ItemOwnerUUID", itemOwner);
        this.getItem().setTag(compoundTag);
    }

    public UUID getItemOwner() {
        return itemOwner;
    }
}
