package net.sdm.sdmlootforge;

import com.mojang.logging.LogUtils;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.loading.FMLPaths;
import org.slf4j.Logger;

// The value here should match an entry in the META-INF/mods.toml file
@Mod(SDMLootForge.MODID)
public class SDMLootForge {

    public static final String MODID = "sdmlootforge";
    private static final Logger LOGGER = LogUtils.getLogger();

    public SDMLootForge() {
        Config.init(FMLPaths.CONFIGDIR.get().resolve("SDMLoot.toml"));
        Config.onLoaded();
    }
}
