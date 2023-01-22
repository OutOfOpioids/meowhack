package me.notkronos.meowhack.manager.managers;

import com.mojang.realmsclient.gui.ChatFormatting;
import me.notkronos.meowhack.Meowhack;
import me.notkronos.meowhack.manager.Manager;
import me.notkronos.meowhack.util.file.FileSystemUtil;
import me.notkronos.meowhack.util.render.FontRenderer;

import java.awt.*;
import java.io.FileInputStream;
import java.io.InputStream;

public class FontManager extends Manager {

    // client font
    private FontRenderer font;
    private int fontType;

    public FontManager() {
        super("FontManager");
    }

    /**
     * Loads a given font
     * @param in The given font
     * @param type Font type (bold, italicized, etc.)
     */
    public void loadFont(String in, int type) {
        font = new FontRenderer(loadFont(in, 40, type));
        fontType = type;
    }

    /**
     * Attempts to load a given font
     * @param in The given font
     * @param size The size of the font
     * @return The loaded font
     */
    private Font loadFont(String in, int size, int type) {
        fontType = type;
        try {

            // font stream
            InputStream fontStream = new FileInputStream(FileSystemUtil.FONTS.resolve(in).toFile());

            // if the client font exists
            if (fontStream != null) {

                // creates and derives the font
                Font clientFont = Font.createFont(Font.TRUETYPE_FONT, fontStream);
                clientFont = clientFont.deriveFont(type, size);

                // close stream
                fontStream.close();
                return clientFont;
            }

            // default
            return new Font(Font.SANS_SERIF, type, size);

        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return new Font(Font.SANS_SERIF, type, size);
    }


    /**
     * Gets the current font
     * @return The current font
     */
    public FontRenderer getFontRenderer() {
        return font != null ? font : new FontRenderer(new Font(Font.SANS_SERIF, Font.PLAIN, 40));
    }

    /**
     * Gets the current font
     * @return The current font
     */
    public String getFont() {
        return font.getName();
    }

    /**
     * Gets the current font type
     * @return The current font type
     */
    public int getFontType() {
        return fontType;
    }
}
