package net.sdm.sdmloot.forge;

import com.electronwill.nightconfig.core.file.CommentedFileConfig;
import com.electronwill.nightconfig.core.io.WritingMode;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.loading.FMLPaths;
import net.sdm.sdmloot.Config;
import net.sdm.sdmloot.SDMLoot;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

import java.util.List;
import java.util.stream.Collectors;

@Mod(SDMLoot.MOD_ID)
public final class SDMLootForge {
    public SDMLootForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(SDMLoot.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        final CommentedFileConfig configData = CommentedFileConfig.builder(FMLPaths.CONFIGDIR.get().resolve("SDMLoot.toml"))
                .sync()
                .autosave()
                .writingMode(WritingMode.REPLACE)
                .build();


        configData.load();
        SPEC.setConfig(configData);

        // Run our common setup.
        SDMLoot.init();
    }




    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue TYPE_LIST =
            BUILDER.comment("Type list.")
                    .define("blacklist", true);

    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENTITY_STRING =
            BUILDER.comment("The list of entities that the mod will work on, depending on the type of list")
                    .defineList("list", List.of("minecraft:slime"), Config::validateItemName);

    public static final ForgeConfigSpec SPEC = BUILDER.build();

    private static List<EntityType<?>> getEntityList(){
        return ENTITY_STRING.get().stream().map(s -> Registry.ENTITY_TYPE.get(new ResourceLocation(s))).collect(Collectors.toList());
    }
}
