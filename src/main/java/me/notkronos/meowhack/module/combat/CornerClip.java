package me.notkronos.meowhack.module.combat;

import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.util.chat.ChatUtil;
import me.notkronos.meowhack.util.chat.MessageType;
import net.minecraft.network.play.client.CPacketPlayer;
import net.minecraft.util.math.MathHelper;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class CornerClip extends Module {
    Integer timeout = 0;
    public static CornerClip INSTANCE;
    public CornerClip() {
        super("CornerClip", Category.COMBAT, "Russian exploit", new String[]{"CornerClip"});
        INSTANCE = this;
        this.enabled = false;
        this.drawn = true;
    }

    @Override
    public void onEnable() {
        timeout = 0;
    }

    @Override
    public void onTick() {
        if(timeout <= 10) {
            push();
            timeout++;
        } else {
            this.setEnabled(false);
            ChatUtil.commandFeedback("CornerClip timed out.", MessageType.INFO);
            timeout = 0;
        }
    }

    private void push() {
        if (mc.world.getCollisionBoxes(mc.player, mc.player.getEntityBoundingBox().grow(0.01, 0, 0.01)).size() < 2) {
            mc.player.setPosition(roundToClosest(mc.player.posX, Math.floor(mc.player.posX) + 0.301, Math.floor(mc.player.posX) + 0.699), mc.player.posY, roundToClosest(mc.player.posZ, Math.floor(mc.player.posZ) + 0.301, Math.floor(mc.player.posZ) + 0.699));
        } else if (mc.player.ticksExisted % 5 == 0) {
            mc.player.setPosition(mc.player.posX + MathHelper.clamp(roundToClosest(mc.player.posX, Math.floor(mc.player.posX) + 0.241, Math.floor(mc.player.posX) + 0.759) - mc.player.posX, -0.03, 0.03), mc.player.posY, mc.player.posZ + MathHelper.clamp(roundToClosest(mc.player.posZ, Math.floor(mc.player.posZ) + 0.241, Math.floor(mc.player.posZ) + 0.759) - mc.player.posZ, -0.03, 0.03));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(mc.player.posX, mc.player.posY, mc.player.posZ, true));
            mc.player.connection.sendPacket(new CPacketPlayer.Position(roundToClosest(mc.player.posX, Math.floor(mc.player.posX) + 0.23, Math.floor(mc.player.posX) + 0.77), mc.player.posY, roundToClosest(mc.player.posZ, Math.floor(mc.player.posZ) + 0.23, Math.floor(mc.player.posZ) + 0.77), true));
        }
    }

    private double roundToClosest(double in, double min, double max) {
        double d1 = in - min;
        double d2 = max - in;
        if (d1 < d2) {
            return min;
        } else {
            return max;
        }
    }

    public int getTimeout() {
        return timeout;
    }
}
