package me.notkronos.meowhack.module.misc;

import club.minnced.discord.rpc.DiscordEventHandlers;
import club.minnced.discord.rpc.DiscordRPC;
import club.minnced.discord.rpc.DiscordRichPresence;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.rpc.IMAGE;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class RPC extends Module {
    public static RPC INSTANCE;

    public static Setting<Enum<IMAGE>> image = new Setting<>("image", IMAGE.OG);

    public RPC() {
        super("RPC", Category.MISC, "Displays discord RPC", new String[]{"DiscordRichPresence"});
        INSTANCE = this;
        INSTANCE.drawn = true;
        INSTANCE.enabled = false;
    }
    // discord stuff
    private static final DiscordRPC discordPresence = DiscordRPC.INSTANCE;
    private static final DiscordRichPresence richPresence = new DiscordRichPresence();
    private static final DiscordEventHandlers presenceHandlers = new DiscordEventHandlers();

    // discord presence thread
    private static Thread presenceThread;

    @Override
    public void onEnable() {
        super.onEnable();
        startPresence();

    }
    @Override
    public void onDisable() {
        super.onDisable();
        interruptPresence();
    }
    public static void startPresence() {

        discordPresence.Discord_Initialize("975671314440392704", presenceHandlers, true, "");
        richPresence.startTimestamp = System.currentTimeMillis() / 1000L;
        discordPresence.Discord_UpdatePresence(richPresence);

        presenceThread = new Thread(() -> {
            while (!Thread.currentThread().isInterrupted()) {
                try {
                    richPresence.largeImageKey = getLargeImageKey();
                    richPresence.largeImageText = Meowhack.NAME + " " + Meowhack.VERSION;
                    richPresence.smallImageKey = "nya";
                    richPresence.smallImageText = "Nya :3";
                    richPresence.details = mc.isIntegratedServerRunning() ? "SinglePlayer" : (mc.getCurrentServerData() != null ? mc.getCurrentServerData().serverIP.toLowerCase() : "Menus");
                    richPresence.state = "Owning spawn!";
                    discordPresence.Discord_UpdatePresence(richPresence);

                    // update every 3 seconds
                    Thread.sleep(3000);
                } catch (Exception ignored) {

                }
            }
        });

        presenceThread.start();
    }
    public static void interruptPresence() {
        if (presenceThread != null && !presenceThread.isInterrupted()) {
            presenceThread.interrupt();
        }

        // shutdown
        discordPresence.Discord_Shutdown();
        discordPresence.Discord_ClearPresence();
    }

    public static String getLargeImageKey() {
        if(image.getValue() == IMAGE.OG) {
            return "meowhack";
        }
        else if(image.getValue() == IMAGE.CATGIRL) {
            return "kitsureii";
        }
        return "";
    }
}
