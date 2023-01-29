package me.notkronos.meowhack.module.client;

import me.notkronos.meowhack.event.events.client.SettingUpdateEvent;
import me.notkronos.meowhack.event.events.render.RenderFontEvent;
import me.notkronos.meowhack.util.render.FontRenderer;
import me.notkronos.meowhack.module.Category;
import me.notkronos.meowhack.module.Module;
import me.notkronos.meowhack.setting.Setting;
import me.notkronos.meowhack.util.FileSystemUtil;
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

    private final String fontName = "Lato";
    private FontRenderer font;
    private final FontType style = FontType.PLAIN;
    private int fontType;
    public static Setting<Boolean> antiAlias = new Setting<>("AntiAlias", true);

    @SubscribeEvent
    public void onSettingUpdate(SettingUpdateEvent event) {

            String font = getFont();

            // reload font with new style
            loadFont(font, style.getType());
    }

    @SubscribeEvent
    public void onFontRender(RenderFontEvent event) {
        event.setCanceled(true);
    }

    public void loadFont(String in, int type) {
        font = new FontRenderer(loadFont(in, 40, type));
        fontType = type;
    }

    private Font loadFont(String in, int size, int type) {
        fontType = type;
        try {

            // font stream
            InputStream fontStream = Files.newInputStream(FileSystemUtil.FONTS.resolve(in).toFile().toPath());

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
    public Object getFontRenderer() {
        return font != null ? font : new FontRenderer(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
    }

    /**
     * Gets the current font
     * @return The current font
     */
    public String getFont() {
        return font.getName();
    }


    public int getFontType() {
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
    private int type;

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

