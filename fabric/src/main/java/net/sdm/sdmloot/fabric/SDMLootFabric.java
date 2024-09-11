package net.sdm.sdmloot.fabric;

import net.fabricmc.loader.api.FabricLoader;
import net.sdm.sdmloot.Config;
import net.sdm.sdmloot.SDMLoot;
import net.fabricmc.api.ModInitializer;

public final class SDMLootFabric implements ModInitializer {
    @Override
    public void onInitialize() {
        // This code runs as soon as Minecraft is in a mod-load-ready state.
        // However, some things (like resources) may still be uninitialized.
        // Proceed with mild caution.
        Config.init(FabricLoader.getInstance().getConfigDir().resolve("SDMLoot.toml"));
        // Run our common setup.
        SDMLoot.init();
    }
}
