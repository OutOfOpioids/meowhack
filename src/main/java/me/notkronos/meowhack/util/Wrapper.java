package me.notkronos.meowhack.util;

import me.notkronos.meowhack.Meowhack;
import net.minecraft.client.Minecraft;

public interface Wrapper {
    Minecraft mc = Minecraft.getMinecraft();
    Meowhack meowhack = Meowhack.INSTANCE;
    default boolean nullCheck() {
        return mc.player != null && mc.world != null;
    }
    default Meowhack getMeowhack() {
        return Meowhack.INSTANCE;
    }
}

