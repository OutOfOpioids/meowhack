package me.notkronos.meowhack.mixin.mixins.entity;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.entity.DeathEvent;
import me.notkronos.meowhack.module.render.SwingSpeed;
import net.minecraft.client.Minecraft;
import net.minecraft.client.entity.EntityPlayerSP;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.init.MobEffects;
import net.minecraft.item.ItemStack;
import net.minecraft.network.datasync.DataParameter;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.Objects;
import java.util.UUID;

import static me.notkronos.meowhack.util.Wrapper.mc;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {
    public MixinEntityLivingBase(World world) {
        super(world);
    }

    @Shadow
    @Final
    private static DataParameter<Float> HEALTH;

    @Shadow
    protected abstract boolean isPlayer();

    @Shadow
    public boolean isSwingInProgress;
    @Shadow
    public int swingProgressInt;

    @Shadow
    public abstract ItemStack getActiveItemStack();

    private final SwingSpeed SWING_SPEED = SwingSpeed.INSTANCE;
    int modifier = SwingSpeed.speed.value;

    @Inject(method = "getArmSwingAnimationEnd", at = @At("HEAD"), cancellable = true)
    public void getArmSwingAnimationEndHook(CallbackInfoReturnable<Integer> ci) {
        int swingSpeed = SWING_SPEED.isEnabled() ? modifier : 8;
        EntityPlayerSP clientPlayer = Minecraft.getMinecraft().player;
        if (clientPlayer != null) {
            if (SWING_SPEED.isEnabled()) {

                if (!this.isPlayer()) return;

                UUID playerUUID = this.getUniqueID();
                EntityPlayer player = world.getPlayerEntityByUUID(playerUUID);

                if (SwingSpeed.mode.getValue() == SwingSpeed.Mode.SELF) {
                    if (player == mc.player) {
                        if (player.isPotionActive(MobEffects.HASTE)) {
                            ci.setReturnValue(swingSpeed - (1 + (Objects.requireNonNull(player.getActivePotionEffect(MobEffects.HASTE))).getAmplifier()));
                        } else {
                            ci.setReturnValue((player.isPotionActive(MobEffects.MINING_FATIGUE) ? swingSpeed + (1 + Objects.requireNonNull(player.getActivePotionEffect(MobEffects.MINING_FATIGUE)).getAmplifier()) * 2 : swingSpeed));
                        }
                    }
                } else if (SwingSpeed.mode.getValue() == SwingSpeed.Mode.OTHERS) {
                    if (!(player == mc.player)) {
                        if (player.isPotionActive(MobEffects.HASTE)) {
                            ci.setReturnValue(swingSpeed - (1 + (Objects.requireNonNull(player.getActivePotionEffect(MobEffects.HASTE))).getAmplifier()));
                        } else {
                            ci.setReturnValue((player.isPotionActive(MobEffects.MINING_FATIGUE) ? swingSpeed + (1 + Objects.requireNonNull(player.getActivePotionEffect(MobEffects.MINING_FATIGUE)).getAmplifier()) * 2 : swingSpeed));
                        }
                    }
                } else if (SwingSpeed.mode.getValue() == SwingSpeed.Mode.BOTH) {
                    if (player.isPotionActive(MobEffects.HASTE)) {
                        ci.setReturnValue(swingSpeed - (1 + (Objects.requireNonNull(player.getActivePotionEffect(MobEffects.HASTE))).getAmplifier()));
                    } else {
                        ci.setReturnValue((player.isPotionActive(MobEffects.MINING_FATIGUE) ? swingSpeed + (1 + Objects.requireNonNull(player.getActivePotionEffect(MobEffects.MINING_FATIGUE)).getAmplifier()) * 2 : swingSpeed));
                    }
                }
            }
        }
    }

    @Inject(method = "notifyDataManagerChange", at = @At("RETURN"))
    public void notifyDataManagerChange(DataParameter<?> key, CallbackInfo ci) {
        if (key.equals(HEALTH)
                && this.dataManager.get(HEALTH) <= 0.0
                && this.world != null
                && this.world.isRemote)
        {
            Meowhack.EVENT_BUS.post(new DeathEvent(EntityLivingBase.class.cast(this)));
        }
    }
}