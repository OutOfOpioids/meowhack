package me.notkronos.meowhack.module.misc;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.chat.ChatUtil;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class Spammer extends Module {
    private final String[] hazelSpam = {
            "holy shit i love C10",
            "im omw to get estrogen",
            "dan heng is my beautiful little princess",
            "cinnamorolling the opps",
            "i gotta go grief sun knights brb",
            "does anyone here like newjeans",
            "hey guys my name is hazel",
            "any hazel fans online rn",
            "donfeur LITERALLY loves me",
            "hazel hazel bo bazel banana nana fo fazel fe fi fo fazel HAZEL!!!!!!! :3"
    };
    public static String[] spam;

    public static Spammer INSTANCE;
    private long time = 0;
    public boolean isFileSet = false;
    private boolean isWarningSent = false;

    public Spammer() {
        super("Spammer", Category.MISC, "Spams the chat with messages inside of a specified file", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = true;
        INSTANCE.drawn = true;
    }

    public static Setting<Integer> seconds = new Setting<>("Delay", 30, 1, 60);
    public static Setting<Enum<modeEnum>> mode = new Setting<>("Mode", modeEnum.file);

    @Override
    public void onTick() {
        if (isEnabled()) {
            int time = seconds.getValue(); // set time in seconds
            long tick = mc.world.getTotalWorldTime();
            if (tick - this.time >= 20L * time) {
                if(mode.getValue() == modeEnum.file) {
                    if(!isFileSet && !isWarningSent) {
                        ChatUtil.sendMessageClientSide(ChatFormatting.RED + "SpammerFile is not set. Please set it using " + Meowhack.PREFIX + "spammerfile <name>");
                        isWarningSent = true;
                    } else if(spam != null) {
                        mc.player.sendChatMessage(spam[(int) (Math.random() * spam.length)]);
                        this.time = tick;
                    }
                } else {
                    mc.player.sendChatMessage(hazelSpam[(int) (Math.random() * hazelSpam.length)]);
                    this.time = tick;
                }
            }
        }
    }

    public static String[] getSpam(String[] spam) {
        return spam;
    }
}

enum modeEnum {
    file, hazelSpammer
}