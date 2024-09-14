package net.sdm.sdmlootforge;


import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.registries.ForgeRegistries;

import java.nio.file.Path;
import java.util.List;
import java.util.stream.Collectors;

public class Config {

    public static void init(Path file)
    {
        final CommentedFileConfig configData = CommentedFileConfig.builder(file)
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();

        configData.load();
        SPEC.setConfig(configData);
    }

    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue TYPE_LIST =
            BUILDER.comment("Type list.")
                    .define("blacklist", true);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENTITY_STRING =
            BUILDER.comment("The list of entities that the mod will work on, depending on the type of list")
                    .defineList("list", List.of("minecraft:slime"), Config::validateItemName);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    public static boolean blacklist;
    public static List<EntityType<?>> entityList;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    private static List<EntityType<?>> getEntityList(){
        return ENTITY_STRING.get().stream().map(s -> ForgeRegistries.ENTITY_TYPES.getValue(new ResourceLocation(s))).collect(Collectors.toList());
    }

    public static void onLoaded(){
        blacklist = TYPE_LIST.get();
        entityList = getEntityList();
    }
}
