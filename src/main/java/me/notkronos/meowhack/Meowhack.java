package me.notkronos.meowhack;

import me.notkronos.meowhack.gui.clickgui.ClickGUIScreen;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.manager.managers.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Predicate;

@Mod(modid = Meowhack.MODID, name = Meowhack.NAME, version = Meowhack.VERSION)
public class Meowhack {
    public static final String MODID = "meowhack";
    public static final String NAME = "Meowhack";
    public static final String VERSION = "1.1+6c1471d4";

    @Mod.Instance
    public static Meowhack INSTANCE;

    public Meowhack() {
        INSTANCE = this;
    }

    private ClickGUIScreen clickGUI;

    public static EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;

    private final List<Manager> managers = new ArrayList<>();
    private ModuleManager moduleManager;
    private EventManager eventManager;
    private ThreadManager threadManager;
    private TickManager tickManager;

    public static final Logger LOGGER = LogManager.getLogger("meowhack");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initializing meowhack.");
        Display.setTitle(NAME + " v." + VERSION);

        moduleManager = new ModuleManager("mm");
        managers.add(moduleManager);

        eventManager = new EventManager();
        managers.add(eventManager);

        threadManager = new ThreadManager();
        managers.add(threadManager);

        tickManager = new TickManager();
        managers.add(tickManager);

        clickGUI = new ClickGUIScreen();

        LOGGER.info("Meowhack initialized.");
    }

    public ModuleManager getModuleManager() {
        return moduleManager;
    }

    public EventManager getEventManager() {
        return eventManager;
    }

    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public TickManager getTickManager() { return tickManager; }
    public Manager getManager(Predicate<? super Manager> predicate) {
        return managers.stream()
                .filter(predicate)
                .findFirst()
                .orElse(null);
    }
    public List<Manager> getAllManagers() {
        return managers;
    }
    public static Logger getLogger()
    {
        return LOGGER;
    }
    public ClickGUIScreen getClickGUI() {
        return clickGUI;
    }
}
