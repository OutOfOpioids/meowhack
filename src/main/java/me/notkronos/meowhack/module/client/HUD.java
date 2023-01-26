package me.notkronos.meowhack.module.client;

import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.ColorUtil;
import me.notkronos.meowhack.util.Timer;
import me.notkronos.meowhack.util.render.FontUtil;
import me.notkronos.meowhack.util.string.FormatUtil;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.text.TextFormatting;
import java.text.DecimalFormat;
import java.util.HashMap;
import java.util.Map;
import static me.notkronos.meowhack.util.EntityUtil.getTextRadarPlayers;
import static me.notkronos.meowhack.util.Wrapper.mc;

public class HUD extends Module {
    public static HUD INSTANCE;
    private static final Timer timer = new Timer();
    private static Map<String, Boolean> players = new HashMap<String, Boolean>();

    public HUD() {
        super("HUD", Category.CLIENT, "HUD of the client", new String[]{});
        INSTANCE = this;
        INSTANCE.drawn = false;
        INSTANCE.enabled = true;

    }

    private static final FormatUtil formatUtil = new FormatUtil();
    private static final DecimalFormat decimalFormat = new DecimalFormat("0.0");

    //Settings
    public static Setting<Boolean> watermark = new Setting<>("Watermark", true);
    public static Setting<Boolean> direction = new Setting<>("Direction", true);
    public static Setting<Boolean> coordinates = new Setting<>("Coordinates", true);
    public static Setting<Boolean> netherCoordinates = new Setting<>("NetherCoordinates", true);
    public static Setting<Boolean> textRadar = new Setting<>("TextRadar", true);

    private static final float ELEMENT = FontUtil.getFontHeight() + 1;

    @Override
    public void onUpdate() {
        if(timer.passedMs(500)) {
            players = getTextRadarPlayers();
            timer.reset();
        }
    }

    @Override
    public void onRender2D() {
        int[] colors = ColorUtil.getPrimaryColor();
        int primaryColor = ColorUtil.decimalToHex(colors[0], colors[1], colors[2]);
        ScaledResolution resolution = new ScaledResolution(mc);
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        float topLeft = 2;
        float topRight = 2;
        float bottomLeft = height - ELEMENT;
        float bottomRight = height - ELEMENT;

        StringBuilder watermarkS = null;
        if (watermark.getValue()) {
            watermarkS = new StringBuilder()
                    .append(Meowhack.NAME)
                    .append(" v.")
                    .append(Meowhack.VERSION);

            FontUtil.drawStringWithShadow(watermarkS.toString(), 2, topLeft, primaryColor);
            topLeft += ELEMENT;
        }

        if (textRadar.getValue()) {
            if (!players.isEmpty()) {
                for (Map.Entry<String, Boolean> player : players.entrySet()) {
                    String text = player.getKey();
                    FontUtil.drawStringWithShadow(text, 2.0f, topLeft, primaryColor);
                    topLeft += ELEMENT;
                }
            }
        }
        //This shit looks awful lol
        if(coordinates.getValue()) {
            double X = Double.parseDouble(decimalFormat.format(mc.player.posX));
            double Y = Double.parseDouble(decimalFormat.format(mc.player.posY));
            double Z = Double.parseDouble(decimalFormat.format(mc.player.posZ));
            String defaultCoordsString = "XYZ " +
                    TextFormatting.WHITE +
                    X +
                    TextFormatting.RESET +
                    ", " +
                    TextFormatting.WHITE +
                    Y +
                    TextFormatting.RESET +
                    ", " +
                    TextFormatting.WHITE +
                    Z;
            String coordinates;
            if(!netherCoordinates.getValue()) {

                FontUtil.drawStringWithShadow(defaultCoordsString, 2, bottomLeft, primaryColor);
            }
            else {

                int dimension = mc.player.dimension;

                if(dimension < 0) {
                    coordinates = "XYZ " +
                            TextFormatting.WHITE +
                            X +
                            TextFormatting.RESET +
                            ", " +
                            TextFormatting.WHITE +
                            Y +
                            TextFormatting.RESET +
                            ", " +
                            TextFormatting.WHITE +
                            Z +
                            TextFormatting.RESET +
                            " [" +
                            TextFormatting.WHITE +
                            Double.parseDouble(decimalFormat.format((X * 8))) +
                            TextFormatting.RESET +
                            ", " +
                            TextFormatting.WHITE +
                            Double.parseDouble(decimalFormat.format((Z * 8))) +
                            TextFormatting.RESET +
                            "]";
                }
                else if(dimension == 0) {
                    coordinates = "XYZ " +
                            TextFormatting.WHITE +
                            X +
                            TextFormatting.RESET +
                            ", " +
                            TextFormatting.WHITE +
                            Y +
                            TextFormatting.RESET +
                            ", " +
                            TextFormatting.WHITE +
                            Z +
                            TextFormatting.RESET +
                            " [" +
                            TextFormatting.WHITE +
                            Double.parseDouble(decimalFormat.format((X / 8))) +
                            TextFormatting.RESET +
                            ", " +
                            TextFormatting.WHITE +
                            Double.parseDouble(decimalFormat.format((Z / 8))) +
                            TextFormatting.RESET +
                            "]";
                } else {
                    coordinates = defaultCoordsString;
                }
                FontUtil.drawStringWithShadow(coordinates, 2, bottomLeft, primaryColor);
            }
            bottomLeft -= ELEMENT;
        }
        if(direction.getValue()) {
            EnumFacing direction = mc.player.getHorizontalFacing();
            EnumFacing.AxisDirection axisDirection = direction.getAxisDirection();

            String directionString = TextFormatting.WHITE
                    +formatUtil.capitalise(direction.getName())
                    + TextFormatting.RESET +
                    " [" +
                    TextFormatting.WHITE +
                    (axisDirection.equals(EnumFacing.AxisDirection.POSITIVE) ? "+" : "-") +
                    FormatUtil.formatEnum(direction.getAxis()) +
                    TextFormatting.RESET +
                    "]";
            FontUtil.drawStringWithShadow(directionString, 2, bottomLeft, primaryColor);
            bottomLeft -= ELEMENT;
        }

    }
}
