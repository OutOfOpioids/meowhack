package me.notkronos.meowhack.util.minecraft;

import me.notkronos.meowhack.util.Wrapper;
import net.minecraft.util.math.BlockPos;

public class PlayerUtil implements Wrapper {
    public static BlockPos getPosition() {
        return new BlockPos(mc.player.posX, mc.player.posY, mc.player.posZ);
    }
}