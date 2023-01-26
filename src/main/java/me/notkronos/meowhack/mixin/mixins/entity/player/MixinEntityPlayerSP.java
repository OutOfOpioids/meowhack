package me.notkronos.meowhack.mixin.mixins.entity.player;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.movement.MotionEvent;
import me.notkronos.meowhack.util.Wrapper;
import me.notkronos.meowhack.event.events.entity.LivingUpdateEvent;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.AbstractClientPlayer;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.MoverType;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(EntityPlayerSP.class)
public abstract class MixinEntityPlayerSP extends AbstractClientPlayer implements Wrapper {
    public MixinEntityPlayerSP() {
        super(Minecraft.getMinecraft().world, Minecraft.getMinecraft().player.getGameProfile());
    }

    // locks the update function
    private boolean updateLock;

    // mc
    @Shadow
    protected Minecraft mc;

    @Shadow
    private boolean prevOnGround;

    @Shadow
    private float lastReportedYaw;

    @Shadow
    private float lastReportedPitch;

    @Shadow
    private int positionUpdateTicks;

    @Shadow
    private double lastReportedPosX;

    @Shadow
    private double lastReportedPosY;

    @Shadow
    private double lastReportedPosZ;

    @Shadow
    private boolean autoJumpEnabled;

    @Shadow
    private boolean serverSprintState;

    @Shadow
    private boolean serverSneakState;

    @Shadow
    protected abstract boolean isCurrentViewEntity();

    @Shadow
    public abstract void onUpdate();

    @Shadow
    protected abstract void onUpdateWalkingPlayer();

    @Redirect(method = "move", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/AbstractClientPlayer;move(Lnet/minecraft/entity/MoverType;DDD)V"))
    public void move(AbstractClientPlayer player, MoverType type, double x, double y, double z) {
        MotionEvent motionEvent = new MotionEvent(type, x, y, z);
        Meowhack.EVENT_BUS.post(motionEvent);

        if (motionEvent.isCanceled()) {
            super.move(motionEvent.getType(), motionEvent.getX(), motionEvent.getY(), motionEvent.getZ());
        } else {
            super.move(type, x, y, z);
        }
    }

    @Redirect(method = "onLivingUpdate", at = @At(value = "INVOKE", target = "Lnet/minecraft/client/entity/EntityPlayerSP;setSprinting(Z)V", ordinal = 2))
    public void onLivingUpdate(EntityPlayerSP entityPlayerSP, boolean sprintUpdate) {
        LivingUpdateEvent livingUpdateEvent = new LivingUpdateEvent(entityPlayerSP, sprintUpdate);
        Meowhack.EVENT_BUS.post(livingUpdateEvent);

        if (livingUpdateEvent.isCanceled()) {
            livingUpdateEvent.getEntityPlayerSP().setSprinting(true);
        } else {
            entityPlayerSP.setSprinting(sprintUpdate);
        }
    }
}