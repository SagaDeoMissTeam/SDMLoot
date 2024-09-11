package net.sdm.sdmloot.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.fabricmc.loader.api.ModContainer;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.EntityType;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.fml.config.ModConfig;
import net.sdm.sdmloot.Config;
import net.sdm.sdmloot.SDMLoot;
import net.fabricmc.api.ModInitializer;

import java.util.List;
import java.util.stream.Collectors;

public final class SDMLootFabric implements ModInitializer {
    @Override
    public void onInitialize() {

//        ModContainer container = FabricLoader.getInstance().getModContainer("sdmloot").orElse(null);
//        if(container != null) {
//
//            ModConfig config = new ModConfig(ModConfig.Type.SERVER, SPEC, container, "SDMLoot.toml");
//            SPEC.setConfig(config.getConfigData());
//
//            Config.blacklist = TYPE_LIST.get();
//            Config.entityList = getEntityList();
//        }
        SDMLoot.init();
    }



//    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();
//
//    private static final ForgeConfigSpec.BooleanValue TYPE_LIST =
//            BUILDER.comment("Type list.")
//                    .define("blacklist", true);
//
//    private static final ForgeConfigSpec.ConfigValue<List<? extends String>> ENTITY_STRING =
//            BUILDER.comment("The list of entities that the mod will work on, depending on the type of list")
//                    .defineList("list", List.of("minecraft:slime"), Config::validateItemName);
//
//    public static final ForgeConfigSpec SPEC = BUILDER.build();
//
//    private static List<EntityType<?>> getEntityList(){
//        return ENTITY_STRING.get().stream().map(s -> Registry.ENTITY_TYPE.get(new ResourceLocation(s))).collect(Collectors.toList());
//    }
}
