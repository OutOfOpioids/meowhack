package me.notkronos.meowhack;

import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.manager.managers.*;
import me.notkronos.meowhack.util.file.FileSystemUtil;
import net.minecraftforge.common.MinecraftForge;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.common.event.FMLInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPostInitializationEvent;
import net.minecraftforge.fml.common.event.FMLPreInitializationEvent;
import net.minecraftforge.fml.common.event.FMLServerStoppingEvent;
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
    public static final String VERSION = "1.4";
    public static boolean SETUP = false;
    public static String PREFIX = "++";
    public long initTime = 0;

    @Mod.Instance
    public static Meowhack INSTANCE;

    public Meowhack() {
        INSTANCE = this;
    }

    public static EventBus EVENT_BUS = MinecraftForge.EVENT_BUS;

    private final List<Manager> managers = new ArrayList<>();

    private ConfigManager configManager;
    private CommandManager commandManager;
    private EventManager eventManager;
    private FriendManager friendManager;
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

        long startTime = System.currentTimeMillis();

        commandManager = new CommandManager();
        managers.add(commandManager);

        eventManager = new EventManager();
        managers.add(eventManager);

        friendManager = new FriendManager();
        managers.add(friendManager);

        holeManager = new HoleManager();
        managers.add(holeManager);

        moduleManager = new ModuleManager();
        managers.add(moduleManager);

        tickManager = new TickManager();
        managers.add(tickManager);

        threadManager = new ThreadManager();
        managers.add(threadManager);

        initTime = System.currentTimeMillis() - startTime;
        LOGGER.info("Meowhack initialized in" + initTime + ".");

    }
    @Mod.EventHandler
    public void postInit(FMLPostInitializationEvent event) {

        //This has to be done after Module Manager was initialized and I don't want to mess up the alphabetic order
        FileSystemUtil.createFileSystem();
        configManager = new ConfigManager();
        managers.add(configManager);

        //Load configs
        getConfigManager().loadModules();
        getConfigManager().loadFriends();
        getConfigManager().loadFont();

        //Add a shutdown hook to save the config
        Runtime.getRuntime().addShutdownHook(new Thread(() -> {
            getConfigManager().saveModules();
            getConfigManager().saveFriends();
            getConfigManager().saveFont();
        }));

        SETUP = true;
    }

    @Mod.EventHandler
    public void onShutdown(FMLServerStoppingEvent event) {
        getConfigManager().saveModules();
        getConfigManager().saveFriends();
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
    public FriendManager getFriendManager() {
        return friendManager;
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
}
