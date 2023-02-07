package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.event.events.entity.RenderCrystalEvent;
import me.notkronos.meowhack.event.events.network.PacketEvent;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.ColorUtil;
import net.minecraft.entity.Entity;
import net.minecraft.entity.item.EntityEnderCrystal;
import net.minecraft.network.play.server.SPacketDestroyEntities;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.awt.*;
import java.util.ArrayList;
import java.util.List;

import static me.notkronos.meowhack.util.Wrapper.mc;
import static org.lwjgl.opengl.GL11.*;

public class CrystalChams extends Module {
    public static CrystalChams INSTANCE;

    public CrystalChams() {
        super("CrystalChams", Category.RENDER, "Modifies crystal rendering", new String[]{"EndCrystalChams"});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
    }

    private final List<EntityEnderCrystal> crystalList = new ArrayList<>();

    //General Settings
    public static Setting<Boolean> noAnimation = new Setting<>("NoAnimation", false);
    public static Setting<Boolean> XQZ = new Setting<>("XQZ", true);
    public static Setting<Enum<Mode>> mode = new Setting<>("Mode", Mode.NONE);

    //Line Settings

    public static Setting<Integer> lineWidth = new Setting<>("LineWidth", 1, 1, 3);
    public static Setting<Integer> lineRed = new Setting<>("LineRed", 0, 0, 255);
    public static Setting<Integer> lineGreen = new Setting<>("LineGreen", 0, 0, 255);
    public static Setting<Integer> lineBlue = new Setting<>("LineBlue", 0, 0, 255);
    public static Setting<Integer> lineAlpha = new Setting<>("LineAlpha", 0, 0, 255);

    //Chams settings

    public static Setting<Integer> chamsRed = new Setting<>("chamsRed", 0, 0, 255);
    public static Setting<Integer> chamsGreen = new Setting<>("chamsGreen", 0, 0, 255);
    public static Setting<Integer> chamsBlue = new Setting<>("chamsBlue", 0, 0, 255);
    public static Setting<Integer> chamsAlpha = new Setting<>("chamsAlpha", 0, 0, 255);

    //XQZ settings

    public static Setting<Integer> xqzRed = new Setting<>("XQZRed", 0, 0, 255);
    public static Setting<Integer> xqzGreen = new Setting<>("XQZGreen", 0, 0, 255);
    public static Setting<Integer> xqzBlue = new Setting<>("XQZBlue", 0, 0, 255);
    public static Setting<Integer> xqzAlpha = new Setting<>("XQZAlpha", 0, 0, 255);

    public void onRenderModel(RenderCrystalEvent.RenderCrystalPreEvent event) {
        if(mode.value == Mode.LINE) {
           renderLines();
           render(event);
        }
        else if(mode.value == Mode.CHAMS) {
            renderChams();
            render(event);
        }
        else if(mode.value == Mode.BOTH) {
            renderLines();
            render(event);
            renderChams();
            render(event);
        }
    }

    @Override
    public void onUpdate() {
        for(Entity crystal : mc.world.loadedEntityList) {
            if(!(crystal instanceof EntityEnderCrystal)) continue;
            crystalList.add((EntityEnderCrystal) crystal);
        }
    }

    //remove an EndCrystal from the list if it got destroyed.
    @SubscribeEvent
    public void onReceivePacket(PacketEvent.PacketReceiveEvent event) {
            if(event.getPacket() instanceof SPacketDestroyEntities) {
                SPacketDestroyEntities packet = (SPacketDestroyEntities) event.getPacket();
                for(int id : packet.getEntityIDs()) {
                    Entity crystal = mc.world.getEntityByID(id);
                    if(crystal instanceof EntityEnderCrystal) crystalList.remove(crystal);
                }
            }
    }

    public void renderLines() {
        Color lineColor = getLineColor();
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_LIGHTING);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glPolygonMode(GL_FRONT_AND_BACK, GL_LINE);
        glLineWidth(lineWidth.value + 1);
        glColor4f(lineColor.getRed() / 255.0f,
                lineColor.getGreen() / 255.0f,
                lineColor.getBlue() / 255.0f,
                lineColor.getAlpha() / 255.0f
        );
    }
    public void renderChams() {
        Color chamsColor = new Color(chamsRed.value, chamsGreen.value, chamsBlue.value, chamsAlpha.value);
        Color xqzColor = new Color(xqzRed.value, xqzGreen.value, xqzBlue.value, xqzAlpha.value);
        glPushAttrib(GL_ALL_ATTRIB_BITS);
        glEnable(GL_BLEND);
        glDisable(GL_TEXTURE_2D);
        glDisable(GL_LIGHTING);
        glDisable(GL_ALPHA_TEST);
        glBlendFunc(GL_SRC_ALPHA, GL_ONE_MINUS_SRC_ALPHA);
        glEnable(GL_STENCIL_TEST);
        glEnable(GL_POLYGON_OFFSET_LINE);
        if (XQZ.getValue()) {
            glDepthMask(false);
            glDisable(GL_DEPTH_TEST);
            glColor4f(xqzColor.getRed() / 255.0f,
                    xqzColor.getGreen() / 255.0f,
                    xqzColor.getBlue() / 255.0f,
                    xqzColor.getAlpha() / 255.0f
            );
        } else {
            glColor4f(chamsColor.getRed() / 255.0f,
                    chamsColor.getGreen() / 255.0f,
                    chamsColor.getBlue() / 255.0f,
                    chamsColor.getAlpha() / 255.0f
            );
        }
    }
    public void render(RenderCrystalEvent.RenderCrystalPreEvent event) {
        float ageInTicks;
        if(noAnimation.getValue()) {
            ageInTicks = 0.15f;
        } else {
            ageInTicks = event.getAgeInTicks();
        }

        event.getModelBase().render(event.getEntity(),
                event.getLimbSwing(),
                event.getLimbSwingAmount(),
                ageInTicks,
                event.getNetHeadYaw(),
                event.getHeadPitch(),
                event.getScaleFactor()
        );

        glPopAttrib();
    }

    public Color getLineColor() {
        int rgba = ColorUtil.toRGBA(lineRed.value, lineGreen.value, lineBlue.value, lineAlpha.value);
        return new Color(rgba);
    }

    public enum Mode {
        NONE,
        CHAMS,
        LINE,
        BOTH
    }
}
