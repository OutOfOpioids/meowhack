package me.notkronos.meowhack.mixin.mixins.render;

import me.notkronos.meowhack.module.render.PopParticles;
import net.minecraft.client.particle.ParticleSimpleAnimated;
import net.minecraft.client.particle.ParticleTotem;
import net.minecraft.world.World;
import net.minecraftforge.fml.relauncher.Side;
import net.minecraftforge.fml.relauncher.SideOnly;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static me.notkronos.meowhack.module.render.PopParticles.ParticleType.TRANS;

@SideOnly(Side.CLIENT)
@Mixin(ParticleTotem.class)
public abstract class MixinParticleTotem extends ParticleSimpleAnimated {

    public MixinParticleTotem(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn) {
        super(worldIn, xCoordIn, yCoordIn, zCoordIn, 176, 8, -0.05f);
        this.motionX = xSpeedIn;
        this.motionY = ySpeedIn;
        this.motionZ = zSpeedIn;
    }

    @Inject(method = "<init>", at = @At("RETURN"))
    public void onParticleTotem(World worldIn, double xCoordIn, double yCoordIn, double zCoordIn, double xSpeedIn, double ySpeedIn, double zSpeedIn, CallbackInfo info) {
        if(PopParticles.INSTANCE.isEnabled()) {
            if(PopParticles.mode.value.equals(TRANS)) {
                int rand = (int) (1 + Math.random() * 3);
                switch (rand) {
                    case 1:
                        this.setRBGColorF(91 / 255f, 206 / 255f, 250 / 255f);
                        break;
                    case 2:
                        this.setRBGColorF(255 / 255f, 255 / 255f, 255 / 255f);
                        break;
                    case 3:
                        this.setRBGColorF(245 / 255f, 169 / 255f, 184 / 255f);
                        break;
                    default:
                        break;
                }
            } else {
                this.setRBGColorF(PopParticles.red.value / 255f, PopParticles.green.value / 255f, PopParticles.blue.value / 255f);
            }
            this.setBaseAirFriction(0.6F);
            this.motionX *= PopParticles.duration.value;
            this.motionY *= PopParticles.duration.value;
            this.motionZ *= PopParticles.duration.value;
        } else {
            this.particleScale *= 0.75F;
            this.particleMaxAge = 60 + this.rand.nextInt(12);

            if (this.rand.nextInt(4) == 0)
            {
                this.setRBGColorF(0.6F + this.rand.nextFloat() * 0.2F, 0.6F + this.rand.nextFloat() * 0.3F, this.rand.nextFloat() * 0.2F);
            }
            else
            {
                this.setRBGColorF(0.1F + this.rand.nextFloat() * 0.2F, 0.4F + this.rand.nextFloat() * 0.3F, this.rand.nextFloat() * 0.2F);
            }

            this.setBaseAirFriction(0.6F);
        }
    }
}
