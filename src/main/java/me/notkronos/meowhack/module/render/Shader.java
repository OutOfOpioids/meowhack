package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.event.events.render.RenderItemInFirstPersonEvent;
import me.notkronos.meowhack.event.events.render.RenderWorldEvent;
import me.notkronos.meowhack.mixin.mixins.render.entity.IEntityRenderer;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.shader.ItemShader;
import me.notkronos.meowhack.util.shader.shaders.FramebufferWrapper;
import me.notkronos.meowhack.util.shader.GlShader;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import org.lwjgl.opengl.Display;

import java.awt.*;

import static me.notkronos.meowhack.util.Wrapper.mc;

public class Shader extends Module {
    public static Shader INSTANCE;

    //Entity type Settings
    public static Setting<Boolean> self = new Setting<>("Self", true);
    public static Setting<Boolean> hands = new Setting<>("Hands", true);
    public static Setting<Boolean> players = new Setting<>("Players", true);
    public static Setting<Boolean> crystals = new Setting<>("Crystals", true);
    public static Setting<Boolean> exp = new Setting<>("Exp", true);
    public static Setting<Boolean> pearls = new Setting<>("Pearls", true);
    public static Setting<Boolean> items = new Setting<>("Items", true);

    //Color Settings
    public static Setting<Integer> red = new Setting<>("Red", 10, 1, 255);
    public static Setting<Integer> green = new Setting<>("Green", 1, 1, 255);
    public static Setting<Integer> blue = new Setting<>("Blue", 10, 1, 255);
    public static Setting<Integer> alpha = new Setting<>("Alpha", 110, 1, 255);
    public static Setting<Boolean> global = new Setting<>("UseGlobalColor", false);

    //Shader Settings
    public static Setting<Float> radius = new Setting<>("Radius", 3.0f, 0.1f, 6.0f);
    public static Setting<Boolean> filled = new Setting<>("Filled", true);
    public static Setting<Float> blend = new Setting<>("FilledBlend", 0.25f, 0.1f, 1.0f);
    public static Setting<Boolean> handRainbow = new Setting<>("HandRainbow", false);

    //Shader Stuff

    protected final FramebufferWrapper wrapper = new FramebufferWrapper();
    protected boolean forceRender = false;

    public Shader() {
        super("Shader", Category.RENDER, "Alternative Chams module", new String[]{"ShaderChams"});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = false;
        Meowhack.EVENT_BUS.register(this);
    }

    @SubscribeEvent
    public void onRenderItemInFirstPersonEvent(RenderItemInFirstPersonEvent.RenderItemInFirstPersonPreEvent event)
    {
        if(INSTANCE.isEnabled()) {
            if (!forceRender && self.getValue()) {
                event.setCanceled(true);
            }
        }
    }

    @SubscribeEvent
    public void onRenderWorldEvent(RenderWorldEvent event) {
        if(INSTANCE.isEnabled()) {
            if (Display.isActive() || Display.isVisible()) {
                GlStateManager.pushMatrix();
                GlStateManager.pushAttrib();
                GlStateManager.enableBlend();
                GlStateManager.tryBlendFuncSeparate(GlStateManager.SourceFactor.SRC_ALPHA, GlStateManager.DestFactor.ONE_MINUS_SRC_ALPHA, GlStateManager.SourceFactor.ONE, GlStateManager.DestFactor.ZERO);
                GlStateManager.enableDepth();
                GlStateManager.depthMask(true);
                GlStateManager.enableAlpha();
                ItemShader shader = new ItemShader();
                shader.mix = blend.getValue();
                shader.alpha = new Color(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue()).getAlpha();
                shader.startDraw(mc.getRenderPartialTicks());
                forceRender = true;
                ((IEntityRenderer) mc.entityRenderer).invokeRenderHand(mc.getRenderPartialTicks(), 2);
                forceRender = false;
                shader.stopDraw(new Color(red.getValue(), green.getValue(), blue.getValue(), alpha.getValue()), radius.getValue(), 1.0f);
                GlStateManager.disableBlend();
                GlStateManager.disableAlpha();
                GlStateManager.disableDepth();
                GlStateManager.popAttrib();
                GlStateManager.popMatrix();
            }
        }
    }
}
