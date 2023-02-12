package me.notkronos.meowhack.module.render;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.manager.managers.HoleManager;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.render.RenderBuilder;
import me.notkronos.meowhack.util.render.RenderBuilder.Box;
import me.notkronos.meowhack.util.render.RenderUtil;
import net.minecraft.util.math.BlockPos;


import java.awt.*;
import java.util.List;
import java.util.concurrent.CopyOnWriteArrayList;

import static me.notkronos.meowhack.util.Wrapper.mc;

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

    @Override
    public void onRender3D() {
        //Update the colors
        Color obsidianColor = new Color(red.getValue(), green.getValue(), blue.getValue(), obsidianAlpha.getValue());
        Color mixedColor = new Color(red.getValue(), green.getValue(), blue.getValue(), mixedAlpha.getValue());
        Color bedrockColor = new Color(red.getValue(), green.getValue(), blue.getValue(), bedrockAlpha.getValue());

        // get the holes
        List<HoleManager.Hole> holes;
        holes = Meowhack.INSTANCE.getHoleManager().getHoles();
        if(holes.isEmpty()) {
            Meowhack.LOGGER.info("List is empty?!?!");
        } else {
            for (HoleManager.Hole hole :holes) {
                if (hole != null) {
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
                                drawSmall(hole, mainHeight, main, mainWidth, obsidianColor);
                                drawSmall(hole, outlineHeight, outline, outlineWidth, obsidianColor);
                                break;
                            case MIXED:
                                drawSmall(hole, mainHeight, main, mainWidth, mixedColor);
                                drawSmall(hole, outlineHeight, outline, outlineWidth, mixedColor);
                                break;
                            case BEDROCK:
                                drawSmall(hole, mainHeight, main, mainWidth, bedrockColor);
                                drawSmall(hole, outlineHeight, outline, outlineWidth, bedrockColor);
                                break;
                        }

                        // draw double holes, scale length and width
                        if (doubles.getValue()) {
                            switch (hole.getType()) {
                                case DOUBLE_OBSIDIAN_X:
                                    drawMediumX(hole, mainHeight, main, mainWidth, obsidianColor);
                                    drawMediumX(hole, outlineHeight, outline, outlineWidth, obsidianColor);
                                    break;
                                case DOUBLE_MIXED_X:
                                    drawMediumX(hole, mainHeight, main, mainWidth, mixedColor);
                                    drawMediumX(hole, outlineHeight, outline, outlineWidth, mixedColor);
                                    break;
                                case DOUBLE_BEDROCK_X:
                                    drawMediumX(hole, mainHeight, main, mainWidth, bedrockColor);
                                    drawMediumX(hole, outlineHeight, outline, outlineWidth, bedrockColor);
                                    break;
                                case DOUBLE_OBSIDIAN_Z:
                                    drawMediumZ(hole, mainHeight, main, mainWidth, obsidianColor);
                                    drawMediumZ(hole, outlineHeight, outline, outlineWidth, obsidianColor);
                                    break;
                                case DOUBLE_MIXED_Z:
                                    drawMediumZ(hole, mainHeight, main, mainWidth, mixedColor);
                                    drawMediumZ(hole, outlineHeight, outline, outlineWidth, mixedColor);
                                    break;
                                case DOUBLE_BEDROCK_Z:
                                    drawMediumZ(hole, mainHeight, main, mainWidth, bedrockColor);
                                    drawMediumZ(hole, outlineHeight, outline, outlineWidth, bedrockColor);
                                    break;
                            }
                        }

                        // draw quad holes, scale length and width
                        if (quads.getValue()) {
                            switch (hole.getType()) {
                                case QUAD_OBSIDIAN:
                                    drawQuad(hole, mainHeight, main, mainWidth, obsidianColor);
                                    drawQuad(hole, outlineHeight, outline, outlineWidth, obsidianColor);
                                    break;
                                case QUAD_BEDROCK:
                                    drawQuad(hole, mainHeight, main, mainWidth, bedrockColor);
                                    drawQuad(hole, outlineHeight, outline, outlineWidth, bedrockColor);
                                    break;
                                case QUAD_MIXED:
                                    drawQuad(hole, mainHeight, main, mainWidth, mixedColor);
                                    drawQuad(hole, outlineHeight, outline, outlineWidth, mixedColor);
                                    break;
                            }
                        }
                    }
                } else {
                    Meowhack.LOGGER.info("Hole is null?!?!?!?");
                }
            }
        }
    }

    private void drawQuad(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth, Color color) {
        RenderUtil.drawBox(new RenderBuilder()
                .position(hole.getHole())
                .height(mainHeight.getValue() - 1)
                .length(1)
                .width(1)
                .color(color)
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

    private void drawMediumZ(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth, Color color) {
        RenderUtil.drawBox(new RenderBuilder()
                .position(hole.getHole())
                .height(mainHeight.getValue() - 1)
                .length(0)
                .width(1)
                .color(color)
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

    private void drawMediumX(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth, Color color) {
        RenderUtil.drawBox(new RenderBuilder()
                .position(hole.getHole())
                .height(mainHeight.getValue() - 1)
                .length(1)
                .width(0)
                .color(color)
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

    private void drawSmall(HoleManager.Hole hole, Setting<Float> mainHeight, Setting<Box> main, Setting<Float> mainWidth, Color color) {
        RenderUtil.drawBox(new RenderBuilder()
                .position(hole.getHole())
                .height(mainHeight.getValue() - 1)
                .length(0)
                .width(0)
                .color(color)
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
}