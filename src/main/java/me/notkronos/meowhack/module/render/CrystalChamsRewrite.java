package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.entity.RenderCrystalEvent;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.util.ResourceLocation;
import net.minecraft.util.math.MathHelper;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;

import static me.notkronos.meowhack.module.render.CrystalChamsRewrite.Mode.*;
import static me.notkronos.meowhack.util.Wrapper.mc;
import static org.lwjgl.opengl.GL11.*;

public class CrystalChamsRewrite extends Module {
    public static CrystalChamsRewrite INSTANCE;

    public CrystalChamsRewrite() {
        super("CrystalChamsRewrite", Category.RENDER, "Modifies crystal rendering", new String[]{"EndCrystalChamsRewrite"});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
        Meowhack.EVENT_BUS.register(this);
    }

    public static Setting<Enum<Mode>> mode = new Setting<>("Mode", LINES);
    public static Setting<Boolean> noAnimation = new Setting<>("NoAnimation", false);
    public static Setting<Float> rotationSpeed = new Setting<>("RotationSpeed", 1.0f, 0.1f, 5.0f);
    public static Setting<Float> scale = new Setting<>("Scale", 1.0f, 0.1f, 2.0f);

    public static Setting<Boolean> XQZ = new Setting<>("XQZ", true);
    public static Setting<Boolean> texture = new Setting<>("Texture", false);
    public static Setting<Boolean> shine = new Setting<>("Shine", false);

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
    public void onRenderCrystalEvent(RenderCrystalEvent.RenderCrystalPreEvent event) {
        if (isEnabled()) {
            event.setCanceled(true);

            //GlStateManager.enableBlendProfile(GlStateManager.Profile.TRANSPARENT_MODEL);
            glPushMatrix();
            glScaled(scale.value, scale.value, scale.value);

            float ageInTicks;
            if (noAnimation.getValue()) {
                ageInTicks = 0.15f;
            } else {
                ageInTicks = event.getAgeInTicks();
            }

            if (texture.getValue()) {
                event.getModelBase().render(event.getEntity(), 0, event.getLimbSwingAmount() * rotationSpeed.value, ageInTicks, 0, 0, event.getScaleFactor());
            }

            glScaled(1 / scale.value, 1 / scale.value, 1 / scale.value);
            glPopMatrix();

        }
    }

    @SubscribeEvent
    public void onRenderCrystalPost(RenderCrystalEvent.RenderCrystalPostEvent event) {
        if (isEnabled()) {
            float f = (float) event.getEntityEnderCrystal().innerRotation + event.getPartialTicks();
            float rotationMoved;
            rotationMoved = MathHelper.sin(f * 0.2F) / 2.0f + 0.5F;
            rotationMoved = rotationMoved * rotationMoved + rotationMoved;

            glPushMatrix();
            glPushAttrib(GL_ALL_ATTRIB_BITS);

            // remove depth
            if (XQZ.getValue()) {
                glDisable(GL_DEPTH_TEST);
            }

            glTranslated(event.getX(), event.getY(), event.getZ());
            glScaled(scale.getValue(), scale.getValue(), scale.getValue());

            glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
            glEnable(GL_BLEND);
            glDisable(GL_TEXTURE_2D);
            glDisable(GL_LIGHTING);
            glEnable(GL_STENCIL_TEST);
            glEnable(GL_POLYGON_OFFSET_LINE);

            if (mode.getValue().equals(BOTH) || mode.getValue().equals(CHAMS)) {
                glPolygonMode(GL_FRONT_AND_BACK, GL_FILL);
                glColor4f(
                        chamsRed.value / 255.0f,
                        chamsGreen.value / 255.0f,
                        chamsBlue.value / 255.0f,
                        chamsAlpha.value / 255.0f

                );
                event.getModelNoBase().render(event.getEntityEnderCrystal(), 0, f * 3 * rotationSpeed.value, noAnimation.value ? 0.15f : rotationMoved * 0.2f, 0, 0, 0.0625F);
            }

            if (shine.getValue()) {
                glEnable(GL_TEXTURE_2D);
                GlStateManager.blendFunc(GlStateManager.SourceFactor.SRC_COLOR, GlStateManager.DestFactor.ONE);

                // render twice (one glint isn't bright enough) <--- whoever thought that is true is retarded
                for (int i = 0; i < 1; i++) { // too lazy to just remove the loop LOL!

                    // bind the enchantment glint texture
                    mc.getRenderManager().renderEngine.bindTexture(GLINT_TEXTURE);

                    // begin the texture matrix
                    GlStateManager.matrixMode(GL_TEXTURE);
                    GlStateManager.loadIdentity();
                    float textureScale = 0.33333334F;
                    GlStateManager.scale(textureScale, textureScale, textureScale);
                    GlStateManager.rotate(30, 0, 0, 1);
                    GlStateManager.translate(0, (event.getEntityEnderCrystal().ticksExisted + mc.getRenderPartialTicks()) * (0.001F + (i * 0.003F)) * 4, 0);
                    GlStateManager.matrixMode(GL_MODELVIEW);
                    glTranslatef(0, 0, 0);
                    event.getModelNoBase().render(event.getEntityEnderCrystal(), 0, f * 3 * rotationSpeed.value, noAnimation.value ? 0.15f : rotationMoved * 0.2f, 0, 0, 0.0625F);
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
                event.getModelNoBase().render(event.getEntityEnderCrystal(), 0, f * 3 * rotationSpeed.value, noAnimation.value ? 0.15f : rotationMoved * 0.2f, 0, 0, 0.0625F);
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

            // reset scale
            glScaled(1 / scale.getValue(), 1 / scale.getValue(), 1 / scale.getValue());

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
