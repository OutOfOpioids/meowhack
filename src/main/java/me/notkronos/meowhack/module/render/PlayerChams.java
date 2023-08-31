package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.entity.RenderCrystalEvent;
import me.notkronos.meowhack.event.events.entity.RenderLivingEntityEvent;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import net.minecraft.client.entity.EntityOtherPlayerMP;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static me.notkronos.meowhack.module.render.PlayerChams.Mode.*;
import static me.notkronos.meowhack.util.Wrapper.mc;
import static org.lwjgl.opengl.GL11.*;

public class PlayerChams extends Module {
    public static PlayerChams INSTANCE;

    public PlayerChams() {
        super("PlayerChams", Category.RENDER, "Modifies player rendering", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
        Meowhack.EVENT_BUS.register(this);
    }

    public static Setting<Enum<Mode>> mode = new Setting<>("Mode", LINES);

    public static Setting<Boolean> XQZ = new Setting<>("XQZ", true);
    public static Setting<Boolean> texture = new Setting<>("Texture", false);
    public static Setting<Boolean> shine = new Setting<>("Shine", false);
    public static Setting<Integer> shineStrength = new Setting<>("ShineStrength", 1, 1, 3);

    public static Setting<Integer> lineWidth = new Setting<>("LineWidth", 1, 1, 3);

    public static Setting<Integer> lineRed = new Setting<>("LineRed", 0, 0, 255);
    public static Setting<Integer> lineGreen = new Setting<>("LineGreen", 0, 0, 255);
    public static Setting<Integer> lineBlue = new Setting<>("LineBlue", 0, 0, 255);
    public static Setting<Integer> lineAlpha = new Setting<>("LineAlpha", 0, 0, 255);

    public static Setting<Integer> chamsRed = new Setting<>("chamsRed", 0, 0, 255);
    public static Setting<Integer> chamsGreen = new Setting<>("chamsGreen", 0, 0, 255);
    public static Setting<Integer> chamsBlue = new Setting<>("chamsBlue", 0, 0, 255);
    public static Setting<Integer> chamsAlpha = new Setting<>("chamsAlpha", 127, 1, 255);

    private final ResourceLocation GLINT_TEXTURE = new ResourceLocation("textures/misc/enchanted_item_glint.png");

    @SubscribeEvent
    public void onRenderLivingEntityPre(RenderLivingEntityEvent.RenderLivingEntityPreEvent event) {
        if (isEnabled() && event.getEntityLivingBase() instanceof EntityOtherPlayerMP) {
            event.setCanceled(true);

            if (texture.getValue()) {
                event.getModelBase().render(event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
            }
        }
    }

    @SubscribeEvent
    public void onRenderLivingEntityPost(RenderLivingEntityEvent.RenderLivingEntityPostEvent event) {
        if (isEnabled() && event.getEntityLivingBase() instanceof EntityOtherPlayerMP) {

            glPushMatrix();
            glPushAttrib(GL_ALL_ATTRIB_BITS);

            // remove depth
            if (XQZ.getValue()) {
                glDisable(GL_DEPTH_TEST);
            }

            glDisable(GL_TEXTURE_2D);
            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glEnable(GL_BLEND);
            glDisable(GL_LIGHTING);
            glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
            glEnable(GL_STENCIL_TEST);
            glEnable(GL_POLYGON_OFFSET_LINE);

            if (mode.getValue().equals(BOTH) || mode.getValue().equals(CHAMS)) {

                glColor4f(
                        chamsRed.value / 255.0f,
                        chamsGreen.value / 255.0f,
                        chamsBlue.value / 255.0f,
                        chamsAlpha.value / 255.0f

                );
                event.getModelBase().render(event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
            }

            if (shine.getValue()) {
                glEnable(GL_TEXTURE_2D);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);

                for (int i = 0; i < shineStrength.value; i++) {

                    // bind the enchantment glint texture
                    mc.getRenderManager().renderEngine.bindTexture(GLINT_TEXTURE);

                    // begin the texture matrix
                    GlStateManager.matrixMode(GL_TEXTURE);
                    GlStateManager.loadIdentity();
                    float textureScale = 0.33333334F;
                    GlStateManager.scale(textureScale, textureScale, textureScale);
                    GlStateManager.rotate(30 - (i * 60), 0, 0, 1);
                    GlStateManager.translate(0, (event.getEntityLivingBase().ticksExisted + mc.getRenderPartialTicks()) * (0.001F + (i * 0.003F)) * 4, 0);
                    GlStateManager.matrixMode(GL_MODELVIEW);
                    glTranslatef(0, 0, 0);
                    event.getModelBase().render(event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
                    // load the matrix
                    GlStateManager.matrixMode(5890);
                    GlStateManager.loadIdentity();
                    GlStateManager.matrixMode(5888);
                }
                glDisable(GL_TEXTURE_2D);
                glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            }

            if (mode.getValue().equals(LINES) || mode.getValue().equals(BOTH)) {
                glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
                glEnable(GL_LINE_SMOOTH);
                glHint(GL_LINE_SMOOTH_HINT, GL_NICEST);
                glLineWidth(lineWidth.value + 1);
                glColor4f(
                        lineRed.value / 255.0f,
                        lineGreen.value / 255.0f,
                        lineBlue.value / 255.0f,
                        lineAlpha.value / 255.0f
                );
                event.getModelBase().render(event.getEntityLivingBase(), event.getLimbSwing(), event.getLimbSwingAmount(), event.getAgeInTicks(), event.getNetHeadYaw(), event.getHeadPitch(), event.getScaleFactor());
                glDisable(GL_LINE_SMOOTH);
            }

            if (XQZ.value) {
                glEnable(GL_DEPTH_TEST);
            }
            // reset texture
            glEnable(GL_LIGHTING);
            glDisable(GL_BLEND);
            glEnable(GL_TEXTURE_2D);

            // re-enable depth
            if (XQZ.getValue()) {
                glEnable(GL_DEPTH_TEST);
            }

            glPopAttrib();
            glPopMatrix();
        }
    }

    public enum Mode {
        LINES,
        CHAMS,
        BOTH
    }
}
