package me.notkronos.meowhack.manager.managers;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.network.PacketEvent;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.mixin.mixins.accessor.IMInecraftAccessor;
import me.notkronos.meowhack.mixin.mixins.accessor.ITimerAccessor;
import me.notkronos.meowhack.util.MathUtil;
import net.minecraft.network.play.server.SPacketTimeUpdate;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import static me.notkronos.meowhack.util.Wrapper.mc;

public class TickManager extends Manager {
    private final float[] latestTicks = new float[10];
    private long time = -1;
    private int tick;

    public TickManager() {
        super("TickManager");
        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onPacketReceive(PacketEvent.PacketReceiveEvent event) {

        // packet for server time updates
        if (event.getPacket() instanceof SPacketTimeUpdate) {

            // update our ticks
            if (time != -1) {
                latestTicks[tick % latestTicks.length] = (20 / ((float) (System.currentTimeMillis() - time) / 1000));
                tick++;
            }

            // mark as last response
            time = System.currentTimeMillis();
        }
    }

    public float getTPS(TPS tps) {

        // do not calculate ticks if we are not on a server
        if (mc.isSingleplayer() || tps.equals(TPS.NONE)) {
            return 20;
        }

        else {
            switch (tps) {
                case CURRENT:
                    // use the last ticks calculation
                    return MathUtil.roundFloat(latestTicks[0], 2);
                case AVERAGE:
                default:
                    int tickCount = 0;
                    float tickRate = 0;

                    // calculate the average ticks
                    for (float tick : latestTicks) {
                        if (tick > 0) {
                            tickRate += tick;
                            tickCount++;
                        }
                    }

                    return MathUtil.roundFloat((tickRate / tickCount), 2);
            }
        }
    }
    public void setClientTicks(float ticks) {
        ((ITimerAccessor) ((IMInecraftAccessor) mc).getTimer()).setTickLength((50 / ticks));
    }

    public float getTickLength() {
        return ((ITimerAccessor) ((IMInecraftAccessor) mc).getTimer()).getTickLength();
    }

    public enum TPS {
        CURRENT,

        AVERAGE,
        NONE
    }
}