package me.notkronos.meowhack.module.client;

import me.notkronos.meowhack.event.events.client.SettingUpdateEvent;
import me.notkronos.meowhack.event.events.render.RenderFontEvent;
import me.notkronos.meowhack.util.render.FontRenderer;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import net.minecraftforge.fml.common.eventhandler.SubscribeEvent;
import java.awt.*;
import java.io.InputStream;
import java.nio.file.Files;

public class CustomFont extends Module {
    public static CustomFont INSTANCE;

    public CustomFont() {
        super("CustomFont", Category.CLIENT, "Used to select the client font", new String[]{});
        INSTANCE = this;
        INSTANCE.enabled = false;
        INSTANCE.drawn = false;
    }



    public static Setting<Integer> size = new Setting<>("Size", 35, 0, 255);
    public static Setting<Boolean> antiAlias = new Setting<>("AntiAlias", true);

    private static FontRenderer font;
    private final String fontName = "Lato-Regular";
    private final FontType style = FontType.PLAIN;
    private static int fontType;

    @SubscribeEvent
    public void onSettingUpdate(SettingUpdateEvent event) {
            // reload font with new style
            loadFont(fontName, style.getType());
    }

    @SubscribeEvent
    public void onFontRender(RenderFontEvent event) {
        event.setCanceled(true);
    }

    public static void loadFont(String in, int type) {
        font = new FontRenderer(loadFont(in, size.getValue(), type));
        fontType = type;
    }

    private static Font loadFont(String in, int size, int type) {
        fontType = type;
        try {

            // font stream
            InputStream fontStream = null; //Files.newInputStream(FileSystemUtil.FONTS.resolve(in).toFile().toPath());

            // if the client font exists
            // creates and derives the font
            Font clientFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
            clientFont = clientFont.deriveFont(type, size);

            // close stream
            fontStream.close();
            return clientFont;
        } catch (Exception exception) {
            exception.printStackTrace();
            return new Font(Font.SANS_SERIF, type, size);
        }
    }
    public static FontRenderer getFontRenderer() {
        return font != null ? font : new FontRenderer(new Font(Font.SANS_SERIF, Font.PLAIN, size.getValue()));
    }

    public static String getFont() {
        if (font == null) {
            font = new FontRenderer(new Font(Font.SANS_SERIF, Font.PLAIN, size.getValue()));
        }
        return font.getName();
    }


    public static int getFontType() {
        return fontType;
    }

public enum FontType {

    /**
     * Plain font
     */
    PLAIN(Font.PLAIN),

    /**
     * Bold font
     */
    BOLD(Font.PLAIN),

    /**
     * Italicised font
     */
    ITALICS(Font.PLAIN);

    // font type identifier
    private final int type;

    FontType(int type) {
        this.type = type;
    }

    /**
     * Gets the font type identifier
     * @return The font type identifier
     */
    public int getType() {
        return type;
    }
}
}

