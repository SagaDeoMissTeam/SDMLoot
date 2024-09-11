package net.sdm.sdmloot;

import net.minecraft.world.entity.item.ItemEntity;
import net.minecraft.world.item.ItemStack;

import java.util.UUID;

public interface IOwnerable {

    UUID getOwnerUUID();
    void setOwnerUUID(UUID uuid);

    ItemEntity sdm$spawnAtLocationCustom(ItemStack itemStack);
    ItemEntity sdm$spawnAtLocationCustom(ItemStack arg, float f);
}
