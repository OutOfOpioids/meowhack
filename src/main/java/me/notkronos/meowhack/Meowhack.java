package me.notkronos.meowhack;

import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.manager.managers.*;
import me.notkronos.meowhack.module.Module;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import org.apache.logging.log4j.Logger;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.core.Core;
import org.lwjgl.opengl.Display;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Predicate;

@Mod(modid = Meowhack.MODID, name = Meowhack.NAME, version = Meowhack.VERSION)
public class Meowhack {
    public static final String MODID = "meowhack";
    public static final String NAME = "Meowhack";
    public static final String VERSION = "0.2-beta";

    @Mod.Instance
    public static Meowhack INSTANCE;

    public Meowhack() {
        INSTANCE = this;
    }

    public static EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;

    private final List<Manager> managers = new ArrayList<>();
    private CoreModuleManager coreModuleManager;
    private ModuleManager moduleManager;
    private EventManager eventManager;
    private ThreadManager threadManager;
    private FontManager fontManager;

    public static final Logger LOGGER = LogManager.getLogger("meowhack");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
        fontManager = new FontManager();
        managers.add(fontManager);
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initializing meowhack.");
        Display.setTitle(NAME + " v." + VERSION);

        coreModuleManager = new CoreModuleManager("cmm");
        managers.add(coreModuleManager);

        moduleManager = new ModuleManager("mm");
        managers.add(moduleManager);

        eventManager = new EventManager();
        managers.add(eventManager);

        threadManager = new ThreadManager();
        managers.add(threadManager);

        LOGGER.info("Meowhack initialized.");
    }

    public CoreModuleManager getCoreModuleManager() { return coreModuleManager; }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public FontManager getFontManager() { return fontManager; }

    public Manager getManager(Predicate<? super Manager> predicate) {
        return managers.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }

    public List<Manager> getAllManagers() {
        return managers;
    }

    public List<Module> getNullSafeFeatures() {
        return Arrays.asList();
    }

    public static Logger getLogger()
    {
        return LOGGER;
    }
}
