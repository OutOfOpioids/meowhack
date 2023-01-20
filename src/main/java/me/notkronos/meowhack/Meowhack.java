package me.notkronos.meowhack;

import net.minecraftforge.fml.common.Mod;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.lwjgl.opengl.Display;

@Mod(modid = Meowhack.MODID, name = Meowhack.NAME, version = Meowhack.VERSION)
public class Meowhack {
    public static final String MODID = "meowhack";
    public static final String NAME = "Meowhack";
    public static final String VERSION = "0.1-beta";

    private static final Logger LOGGER = LogManager.getLogger("meowhack");

    public static void preInit() {
        // ...
    }

    public static void init() {
        LOGGER.info("Initializing meowhack.");
        Display.setTitle(NAME + " v." + VERSION);
        LOGGER.info("Meowhack initialized.");
    }

    public static Logger getLogger()
    {
        return LOGGER;
    }
}
