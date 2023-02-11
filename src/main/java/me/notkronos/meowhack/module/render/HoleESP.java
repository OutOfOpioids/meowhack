package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.manager.managers.HoleManager;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.render.RenderBuilder;
import me.notkronos.meowhack.util.render.RenderBuilder.Box;
import me.notkronos.meowhack.util.render.RenderUtil;
import net.minecraft.util.math.BlockPos;


import java.awt.*;

import static me.notkronos.meowhack.util.Wrapper.mc;
import static me.notkronos.meowhack.util.Wrapper.meowhack;

public class HoleESP extends Module {
    public static HoleESP INSTANCE;

    public HoleESP() {
        super("HoleESP", Category.RENDER, "Highlights nearby safe holes", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = true;
    }

    //HoleESP Settings

    public static Setting<Float> range = new Setting<>("Range", 5.0f, 0.0f, 20.0f);
    public static Setting<RenderBuilder.Box> main = new Setting<>("Main", Box.FILL);
    public static Setting<Float> mainHeight = new Setting<>("MainHeight", -1.0f, 0.1f, 3.0f);
    public static Setting<Float> mainWidth = new Setting<>("MainWidth", 1.5f, 0.0f, 3.0f);
    public static Setting<Box> outline = new Setting<>("Outline", Box.OUTLINE);
    public static Setting<Float> outlineHeight = new Setting<>("OutlineHeight", 0f, -1.0f, 3.0f);
    public static Setting<Float> outlineWidth = new Setting<>("OutlineWidth", 1.5f, 0.0f, 3.0f);
    public static Setting<Boolean> depth = new Setting<>("Depth", true);
    public static Setting<Boolean> doubles = new Setting<>("Doubles", true);
    public static Setting<Boolean> quads = new Setting<>("Quads", true);
    public static Setting<Boolean> voids = new Setting<>("Void", false);

    // Color Settings

    public static Setting<Integer> red = new Setting<>("Red", 1, 0, 255);
    public static Setting<Integer> green = new Setting<>("Green", 1, 0, 255);
    public static Setting<Integer> blue = new Setting<>("Blue", 1, 0, 255);
    public static Setting<Integer> obsidianAlpha = new Setting<>("ObsidianAlpha", 1, 0, 255);
    public static Setting<Integer> mixedAlpha = new Setting<>("MixedAlpha", 1, 0, 255);
    public static Setting<Integer> bedrockAlpha = new Setting<>("BedrockAlpha", 1, 0, 255);

    //Colors

    Color voidColor = new Color(255, 0, 0, 100);
    Color obsidianColor = new Color(red.getValue(), green.getValue(), blue.getValue(), obsidianAlpha.getValue());
    Color mixedColor = new Color(red.getValue(), green.getValue(), blue.getValue(), mixedAlpha.getValue());
    Color bedrockColor = new Color(red.getValue(), green.getValue(), blue.getValue(), bedrockAlpha.getValue());

    @Override
    public void onRender3D() {

        // get the holes
        meowhack.getHoleManager().getHoles().forEach(hole -> {

            // the position of the hole
            BlockPos holePosition = hole.getHole();

            // check if they are in range
            if (mc.player.getDistance(holePosition.getX(), holePosition.getY(), holePosition.getZ()) < range.getValue()) {

                // void holes
                if (voids.getValue()) {
                    if (hole.getType().equals(HoleManager.Type.VOID)) {
                        RenderUtil.drawBox(new RenderBuilder()
                                .position(hole.getHole())
                                .color(voidColor)
                                .box(Box.FILL)
                                .setup()
                                .line(1.5F)
                                .depth(true)
                                .blend()
                                .texture()
                        );
                    }
                }

                // draw the hole
                switch (hole.getType()) {
                    case OBSIDIAN:
                        drawSmallObsidian(hole, mainHeight, main, mainWidth);
                        drawSmallObsidian(hole, outlineHeight, outline, outlineWidth);
                        break;
                    case MIXED:
                        drawSmallMixed(hole, mainHeight, main, mainWidth);
                        drawSmallMixed(hole, outlineHeight, outline, outlineWidth);
                        break;
                    case BEDROCK:
                        drawSmallBedrock(hole, mainHeight, main, mainWidth);
                        drawSmallBedrock(hole, outlineHeight, outline, outlineWidth);
                        break;
                }

                // draw double holes, scale length and width
                if (doubles.getValue()) {
                    switch (hole.getType()) {
                        case DOUBLE_OBSIDIAN_X:
                            drawMediumObsidianX(hole, mainHeight, main, mainWidth);
                            drawMediumObsidianX(hole, outlineHeight, outline, outlineWidth);
                            break;
                        case DOUBLE_MIXED_X:
                            drawMediumMixedX(hole, mainHeight, main, mainWidth);
                            drawMediumMixedX(hole, outlineHeight, outline, outlineWidth);
                            break;
                        case DOUBLE_BEDROCK_X:
                            drawMediumBedrockX(hole, mainHeight, bedrockColor, main, mainWidth);
                            drawMediumBedrockX(hole, outlineHeight, bedrockColor, outline, outlineWidth);
                            break;
                        case DOUBLE_OBSIDIAN_Z:
                            DrawMediumObsidianZ(hole, mainHeight, main, mainWidth);
                            DrawMediumObsidianZ(hole, outlineHeight, outline, outlineWidth);
                            break;
                        case DOUBLE_MIXED_Z:
                            DrawMediumMixedZ(hole, mainHeight, main, mainWidth);
                            DrawMediumMixedZ(hole, outlineHeight, outline, outlineWidth);
                            break;
                        case DOUBLE_BEDROCK_Z:
                            DrawMediumBedrockZ(hole, mainHeight, main, mainWidth);
                            DrawMediumBedrockZ(hole, outlineHeight, outline, outlineWidth);
                            break;
                    }
                }

                // draw quad holes, scale length and width
                if (quads.getValue()) {
                    switch (hole.getType()) {
                        case QUAD_OBSIDIAN:
                            DrawQuadObsidian(hole, mainHeight, main, mainWidth);
                            DrawQuadObsidian(hole, outlineHeight, outline, outlineWidth);
                            break;
                        case QUAD_BEDROCK:
                            DrawQuadBedrock(hole, mainHeight, main, mainWidth);
                            DrawQuadBedrock(hole, outlineHeight, outline, outlineWidth);
                            break;
                        case QUAD_MIXED:
                            drawQuadMixed(hole, mainHeight, main, mainWidth);
                            drawQuadMixed(hole, outlineHeight, outline, outlineWidth);
                            break;
                    }
                }
            }
        });
    }

    private void drawQuadMixed(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth) {
        drawQuad(hole, mainHeight, main, mainWidth, mixedColor);
    }

    private void drawQuad(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth, Color mixedColor) {
        RenderUtil.drawBox(new RenderBuilder()
                .position(hole.getHole())
                .height(mainHeight.getValue() - 1)
                .length(1)
                .width(1)
                .color(mixedColor)
                .box(main.getValue())
                .setup()
                .line(mainWidth.getValue())
                .cull(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .shade(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .alpha(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .depth(depth.getValue())
                .blend()
                .texture()
        );
    }

    private void DrawQuadBedrock(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth) {
        drawQuad(hole, mainHeight, main, mainWidth, bedrockColor);
    }

    private void DrawQuadObsidian(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth) {
        drawQuad(hole, mainHeight, main, mainWidth, obsidianColor);
    }

    private void DrawMediumBedrockZ(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth) {
        drawMediumZ(hole, mainHeight, main, mainWidth, bedrockColor);
    }

    private void drawMediumZ(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth, Color bedrockColor) {
        RenderUtil.drawBox(new RenderBuilder()
                .position(hole.getHole())
                .height(mainHeight.getValue() - 1)
                .length(0)
                .width(1)
                .color(bedrockColor)
                .box(main.getValue())
                .setup()
                .line(mainWidth.getValue())
                .cull(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .shade(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .alpha(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .depth(depth.getValue())
                .blend()
                .texture()
        );
    }

    private void DrawMediumMixedZ(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth) {
        drawMediumZ(hole, mainHeight, main, mainWidth, mixedColor);
    }

    private void DrawMediumObsidianZ(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth) {
        drawMediumZ(hole, mainHeight, main, mainWidth, obsidianColor);
    }

    private void drawMediumBedrockX(HoleManager.Hole hole, Setting<Float> mainHeight, Color bedrockColor, Setting<Box> main, Setting<Float> mainWidth) {
        RenderUtil.drawBox(new RenderBuilder()
                .position(hole.getHole())
                .height(mainHeight.getValue() - 1)
                .length(1)
                .width(0)
                .color(bedrockColor)
                .box(main.getValue())
                .setup()
                .line(mainWidth.getValue())
                .cull(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .shade(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .alpha(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .depth(depth.getValue())
                .blend()
                .texture()
        );
    }

    private void drawMediumMixedX(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth) {
        drawMediumBedrockX(hole, mainHeight, mixedColor, main, mainWidth);
    }

    private void drawMediumObsidianX(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth) {
        drawMediumBedrockX(hole, mainHeight, obsidianColor, main, mainWidth);
    }

    private void drawSmallBedrock(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth) {
        drawSmall(hole, mainHeight, main, mainWidth, bedrockColor);
    }

    private void drawSmall(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth, Color bedrockColor) {
        RenderUtil.drawBox(new RenderBuilder()
                .position(hole.getHole())
                .height(mainHeight.getValue() - 1)
                .length(0)
                .width(0)
                .color(bedrockColor)
                .box(main.getValue())
                .setup()
                .line(mainWidth.getValue())
                .cull(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .shade(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .alpha(main.getValue().equals(Box.GLOW) || main.getValue().equals(Box.REVERSE))
                .depth(depth.getValue())
                .blend()
                .texture()
        );
    }

    private void drawSmallMixed(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth) {
        drawSmall(hole, mainHeight, main, mainWidth, mixedColor);
    }

    private void drawSmallObsidian(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth) {
        drawSmall(hole, mainHeight, main, mainWidth, obsidianColor);
    }
}