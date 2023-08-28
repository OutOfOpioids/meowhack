package me.notkronos.meowhack.font;

import java.awt.*;
import java.io.IOException;

public class FontInit
{
    public void initFonts() {
        try {
            final GraphicsEnvironment ge = GraphicsEnvironment.getLocalGraphicsEnvironment();
            ge.registerFont(Font.createFont(0, FontInit.class.getResourceAsStream("/fonts/Comfortaa-Regular.ttf")));
            ge.registerFont(Font.createFont(0, FontInit.class.getResourceAsStream("/fonts/GOTHIC.TTF")));
            ge.registerFont(Font.createFont(0, FontInit.class.getResourceAsStream("/fonts/MODERN SPACE.ttf")));
        }
        catch (IOException | FontFormatException e) {
            e.printStackTrace();
        }
    }
}