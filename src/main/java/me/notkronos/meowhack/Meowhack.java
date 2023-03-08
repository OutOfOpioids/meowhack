package me.notkronos.meowhack;

import me.notkronos.meowhack.gui.clickgui.ClickGUIScreen;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.manager.managers.*;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.eventhandler.EventBus;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.lwjgl.opengl.Display;
import java.util.ArrayList;
import java.util.List;

@Mod(modid = Meowhack.MODID, name = Meowhack.NAME, version = Meowhack.VERSION)
public class Meowhack {
    public static final String MODID = "meowhack";
    public static final String NAME = "Meowhack";
    public static final String VERSION = "1.4+91df0c9f";
    public static boolean SETUP = false;
    public static String PREFIX = "++";

    @Mod.Instance
    public static Meowhack INSTANCE;

    public Meowhack() {
        INSTANCE = this;
    }

    private ClickGUIScreen clickGUI;

    public static EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;

    private final List<Manager> managers = new ArrayList<>();

    private ConfigManager configManager;
    private CommandManager commandManager;
    private EventManager eventManager;
    private HoleManager holeManager;
    private ModuleManager moduleManager;
    private TickManager tickManager;
    private ThreadManager threadManager;

    public static final Logger LOGGER = LogManager.getLogger("meowhack");

    @Mod.EventHandler
    public void preInit(FMLPreInitializationEvent event) {
    }

    @Mod.EventHandler
    public void init(FMLInitializationEvent event) {
        LOGGER.info("Initializing meowhack.");
        Display.setTitle(NAME + " v." + VERSION);

        commandManager = new CommandManager();
        managers.add(commandManager);

        eventManager = new EventManager();
        managers.add(eventManager);

        holeManager = new HoleManager();
        managers.add(holeManager);

        moduleManager = new ModuleManager();
        managers.add(moduleManager);

        tickManager = new TickManager();
        managers.add(tickManager);

        threadManager = new ThreadManager();
        managers.add(threadManager);

        clickGUI = new ClickGUIScreen();

        LOGGER.info("Meowhack initialized.");
    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        //this has to be done after Module Manager was initialized and I don't want to mess up the alphabetic order lol
        configManager = new ConfigManager();
        managers.add(configManager);

        SETUP = true;
    }

    public CommandManager getCommandManager() {
        return commandManager;
    }
    public ConfigManager getConfigManager() {
        return configManager;
    }
    public EventManager getEventManager() {
        return eventManager;
    }
    public HoleManager getHoleManager() {
        return holeManager;
    }
    public ModuleManager getModuleManager() {
        return moduleManager;
    }
    public TickManager getTickManager() {
        return tickManager;
    }
    public ThreadManager getThreadManager() {
        return threadManager;
    }

    public List<Manager> getAllManagers() {
        return managers;
    }
    public ClickGUIScreen getClickGUI() {
        return clickGUI;
    }
}
