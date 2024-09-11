package net.sdm.sdmloot.forge;

import net.minecraftforge.fml.loading.FMLPaths;
import net.sdm.sdmloot.Config;
import net.sdm.sdmloot.SDMLoot;
import dev.architectury.platform.forge.EventBuses;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.javafmlmod.FMLJavaModLoadingContext;

@Mod(SDMLoot.MOD_ID)
public final class SDMLootForge {
    public SDMLootForge() {
        // Submit our event bus to let Architectury API register our content on the right time.
        EventBuses.registerModEventBus(SDMLoot.MOD_ID, FMLJavaModLoadingContext.get().getModEventBus());

        Config.init(FMLPaths.CONFIGDIR.get().resolve("SDMLoot.toml"));
        // Run our common setup.
        SDMLoot.init();
    }
}
