package me.notkronos.meowhack.module.combat;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.chat.ChatUtil;
import me.notkronos.meowhack.util.chat.MessageType;
import net.minecraft.init.Items;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class AutoHeadBurrow extends Module {
    public static AutoHeadBurrow INSTANCE;

    public AutoHeadBurrow() {
        super("AutoHeadBurrow", Category.COMBAT, "Automatically burrows your head in a block", new String[]{"AutoHead"});
        INSTANCE = this;
        this.enabled = false;
        this.drawn = true;
    }

    public static Setting<Enum<Type>> type = new Setting<>("Type", Type.DISTANCE);

    @Override
    public void onEnable() {
        if(!hasHeadItem())  {
            this.setEnabled(false);
            ChatUtil.commandFeedback("No Skulls in hotbar found!", MessageType.ERROR);
            return;
        }
    }

    private boolean hasHeadItem() {
        for (int i = 0; i < 9; i++) {
            if (mc.player.inventory.getStackInSlot(i).getItem() == Items.SKULL) {
                return true;
            }
        }
        return false;
    }
}

enum Type {
    SMART,
    DISTANCE
}