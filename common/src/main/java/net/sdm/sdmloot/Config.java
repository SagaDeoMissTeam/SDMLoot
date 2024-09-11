package net.sdm.sdmloot;


import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;


import java.nio.file.Path;
import java.util.List;

public class Config {

    public static void init(Path file)
    {

    }

    public static boolean blacklist;
    public static List<EntityType<?>> entityList;

    public static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && Registry.ENTITY_TYPE.containsKey(new ResourceLocation(itemName));
    }
}
