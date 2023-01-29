package me.notkronos.meowhack.module.client;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.manager.managers.TickManager;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.ColorUtil;
import me.notkronos.meowhack.util.MathUtil;
import me.notkronos.meowhack.util.Timer;
import me.notkronos.meowhack.util.render.FontUtil;
import me.notkronos.meowhack.util.string.FormatUtil;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.ScaledResolution;
import net.minecraft.client.network.NetworkPlayerInfo;
import net.minecraft.client.renderer.GlStateManager;
import net.minecraft.client.resources.I18n;
import net.minecraft.item.ItemStack;
import net.minecraft.potion.Potion;
import net.minecraft.potion.PotionEffect;
import net.minecraft.util.EnumFacing;
import net.minecraft.util.math.MathHelper;
import net.minecraft.util.text.TextFormatting;

import java.text.DecimalFormat;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;
import java.util.Comparator;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

import static me.notkronos.meowhack.util.EntityUtil.getTextRadarPlayers;
import static me.notkronos.meowhack.util.Wrapper.mc;

public class HUD extends Module {
    public static HUD INSTANCE;
    private static final Timer timer = new Timer();
    private static Map<String, Boolean> players = new HashMap<>();

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
    public static Setting<Boolean> potionEffects = new Setting<>("PotionEffects", true);
    public static Setting<Boolean> speed = new Setting<>("Speed", true);
    public static Setting<Boolean> time = new Setting<>("Time", true);
    public static Setting<Boolean> tps = new Setting<>("TPS", true);
    public static Setting<Boolean> ping = new Setting<>("Ping", true);
    public static Setting<Boolean> fps = new Setting<>("FPS", true);
    public static Setting<Boolean> arrayList = new Setting<>("ArrayList", true);
    public static Setting<Boolean> armor = new Setting<>("Armor", true);
    private static final float ELEMENT_SIZE = FontUtil.getFontHeight() + 1;

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
        int primaryColor;
        if(Colors.INSTANCE.getHudPrimaryColor()) {
            primaryColor = ColorUtil.decimalToHex(colors[0], colors[1], colors[2]);
        } else {
            primaryColor = 0xaaaaaa;
        }
        ScaledResolution resolution = new ScaledResolution(mc);
        int width = resolution.getScaledWidth();
        int height = resolution.getScaledHeight();
        float topLeft = 2;
        float topRight = 2;
        float bottomLeft = height - ELEMENT_SIZE;
        float bottomRight = height - ELEMENT_SIZE;

        if (watermark.getValue()) {
            String watermarkString = Meowhack.NAME +
                    " v." +
                    Meowhack.VERSION;

            FontUtil.drawStringWithShadow(watermarkString, 2, topLeft, primaryColor);
            topLeft += ELEMENT_SIZE;
        }

        if (textRadar.getValue()) {
            if (!players.isEmpty()) {
                for (Map.Entry<String, Boolean> player : players.entrySet()) {
                    String text = player.getKey();
                    FontUtil.drawStringWithShadow(text, 2.0f, topLeft, primaryColor);
                    topLeft += ELEMENT_SIZE;
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
            bottomLeft -= ELEMENT_SIZE;
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
            bottomLeft -= ELEMENT_SIZE;
        }
        if(potionEffects.getValue()) {
            for(PotionEffect potEff : mc.player.getActivePotionEffects()) {
                String potName = I18n.format(potEff.getEffectName());
                if(!potName.equals("FullBright") && !potName.equals("SpeedMine")) {
                    StringBuilder potionString = new StringBuilder(potName);
                    int amplifier = potEff.getAmplifier() + 1;
                    potionString.append(" ")
                            .append(amplifier)
                            .append(" ")
                            .append(ChatFormatting.WHITE)
                            .append(Potion.getPotionDurationString(potEff, 1F));
                    float textWidth = FontUtil.getStringWidth(potionString.toString());
                    FontUtil.drawStringWithShadow(potionString.toString(), width - 1 - textWidth, bottomRight, primaryColor);
                    bottomRight -= ELEMENT_SIZE;
                }
            }
        }
        if(speed.getValue()) {
            double x = mc.player.posX - mc.player.prevPosX;
            double z = mc.player.posZ - mc.player.prevPosZ;
            float speed = MathUtil.roundFloat(((MathHelper.sqrt(x * x + z * z) / 1000) / (0.05F / 3600) * (50 / Meowhack.INSTANCE.getTickManager().getTickLength())), 1);
            StringBuilder speedString = new StringBuilder()
                    .append("Speed ")
                    .append(TextFormatting.WHITE)
                    .append(speed)
                    .append("km/h");
            float textWidth = FontUtil.getStringWidth(speedString.toString());
            FontUtil.drawStringWithShadow(speedString.toString(), width - 1 - textWidth, bottomRight, primaryColor);
            bottomRight -= ELEMENT_SIZE;
        }
        if(time.getValue()) {
            DateTimeFormatter dateTimeFormatter = DateTimeFormatter.ofPattern("hh:mm a");
            LocalTime localTime = LocalTime.now();
            StringBuilder timeString = new StringBuilder()
                    .append("Time ")
                    .append(TextFormatting.WHITE)
                    .append(localTime.format(dateTimeFormatter));

            float textWidth = FontUtil.getStringWidth(timeString.toString());
            FontUtil.drawStringWithShadow(timeString.toString(), width - 1 - textWidth, bottomRight, primaryColor);
            bottomRight -= ELEMENT_SIZE;
        }
        if (tps.getValue()) {

            // tps values
            float current = Meowhack.INSTANCE.getTickManager().getTPS(TickManager.TPS.CURRENT);
            float average = Meowhack.INSTANCE.getTickManager().getTPS(TickManager.TPS.AVERAGE);

            // build display string
            StringBuilder tpsString = new StringBuilder("TPS ")
                    .append(TextFormatting.WHITE)
                    .append(current)
                    .append(" ")
                    .append(TextFormatting.GRAY)
                    .append("[")
                    .append(TextFormatting.WHITE)
                    .append(average)
                    .append(TextFormatting.GRAY)
                    .append("]");

            // width of the element
            float textWidth = FontUtil.getStringWidth(tpsString.toString());

            // draw to HUD
            FontUtil.drawStringWithShadow(tpsString.toString(), width - 1 - textWidth, bottomRight, primaryColor);

            bottomRight -= ELEMENT_SIZE;
        }
        if(ping.getValue()) {
            int responseTime = 0;
            if(mc.getConnection() != null) {
                NetworkPlayerInfo networkPlayerInfo = mc.getConnection().getPlayerInfo(mc.player.getUniqueID());
                responseTime = networkPlayerInfo.getResponseTime();
            }
            StringBuilder pingString = new StringBuilder()
                    .append("Ping ")
                    .append(TextFormatting.WHITE)
                    .append(responseTime == 0 ? "null" : responseTime)
                    .append(responseTime == 0 ? "" : "ms");
            float textWidth = FontUtil.getStringWidth(pingString.toString());
            FontUtil.drawStringWithShadow(pingString.toString(), width - 1 - textWidth, bottomRight, primaryColor);

            bottomRight -= ELEMENT_SIZE;
        }
        if(fps.getValue()) {
            StringBuilder fpsString = new StringBuilder()
                    .append("FPS ")
                    .append(TextFormatting.WHITE)
                    .append(Minecraft.getDebugFPS());
            float textWidth = FontUtil.getStringWidth(fpsString.toString());
            FontUtil.drawStringWithShadow(fpsString.toString(), width - 1 - textWidth, bottomRight, primaryColor);
            bottomRight -= ELEMENT_SIZE;
        }
        if(arrayList.getValue()) {
            List<Module> sorted = Meowhack.INSTANCE.getModuleManager().getAllModules();

            sorted = sorted.stream().sorted(Comparator.comparing(Module::getName)).collect(Collectors.toList());
            for (Module module : sorted) {
                if (!module.isDrawn() || !module.isEnabled()) {
                    continue;
                }
                float textWidth = (float) (FontUtil.getStringWidth(module.getName()));
                FontUtil.drawStringWithShadow(module.getName(), width - 1 - textWidth, topRight, primaryColor);

                topRight += (double) ELEMENT_SIZE;
            }
        }
        if(armor.getValue()) {
            GlStateManager.enableTexture2D();
            final int i = width / 2;
            int iteration = 0;
            final int y = height - 55 - ((mc.player.isInWater() && mc.playerController.gameIsSurvivalOrAdventure()) ? 10 : 0);
            for (final ItemStack is : mc.player.inventory.armorInventory) {
                ++iteration;
                if (is.isEmpty()) {
                    continue;
                }
                final int x = i - 90 + (9 - iteration) * 20 + 2;
                GlStateManager.enableDepth();
                mc.getRenderItem().zLevel = 200.0f;
                mc.getRenderItem().renderItemAndEffectIntoGUI(is, x, y);
                mc.getRenderItem().renderItemOverlayIntoGUI(mc.fontRenderer, is, x, y, "");
                mc.getRenderItem().zLevel = 0.0f;
                GlStateManager.enableTexture2D();
                GlStateManager.disableLighting();
                GlStateManager.disableDepth();
                final String s = (is.getCount() > 1) ? (is.getCount() + "") : "";
                FontUtil.drawStringWithShadow(s, (float) (x + 19 - 2 - FontUtil.getStringWidth(s)), (float) (y + 9), 16777215);
                int dmg = 0;
                final int itemDurability = is.getMaxDamage() - is.getItemDamage();
                final float green = (is.getMaxDamage() - (float) is.getItemDamage()) / is.getMaxDamage();
                final float red = 1.0f - green;
                dmg = 100 - (int) (red * 100.0f);
                FontUtil.drawStringWithShadow(dmg + "", (float) (x + 8 - FontUtil.getStringWidth(dmg + "") / 2), (float) (y - 11), ColorUtil.toRGBA((int) (red * 255.0f), (int) (green * 255.0f), 0));
            }
            GlStateManager.enableDepth();
            GlStateManager.disableLighting();
        }
    }
}
