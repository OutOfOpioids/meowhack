package me.notkronos.meowhack.mixin.mixins.entity;

import me.notkronos.meowhack.module.render.SwingSpeed;
import net.minecraft.entity.Entity;
import net.minecraft.entity.EntityLivingBase;
import net.minecraft.entity.player.EntityPlayer;
import net.minecraft.world.World;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

import static me.notkronos.meowhack.util.Wrapper.mc;

@Mixin(EntityLivingBase.class)
public abstract class MixinEntityLivingBase extends Entity {
    public MixinEntityLivingBase(World world)
    {
        super(world);
    }
    @Shadow protected abstract boolean isPlayer();

    private final SwingSpeed SWING_SPEED = SwingSpeed.INSTANCE;

    @Inject(method = "getArmSwingAnimationEnd", at = @At(value = "HEAD"), cancellable = true)
    protected void onGetArmSwingAnimationEnd(CallbackInfoReturnable<Integer> ci){
        if(SWING_SPEED.isEnabled()) {
            int modifier = SwingSpeed.speed.value;
            if (!this.isPlayer()) return;

            UUID playerUUID = this.getUniqueID();
            EntityPlayer player = world.getPlayerEntityByUUID(playerUUID);

            if (SwingSpeed.mode.getValue() == SwingSpeed.Mode.SELF) {
                if(player == mc.player) {
                    ci.setReturnValue(modifier);
                }
            } else if (SwingSpeed.mode.getValue() == SwingSpeed.Mode.OTHERS) {
                if(!(player == mc.player)) {
                    ci.setReturnValue(modifier);
                }
            } else if (SwingSpeed.mode.getValue() == SwingSpeed.Mode.BOTH) {
                ci.setReturnValue(modifier);
            }
        }
    }
}
