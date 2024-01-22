package me.notkronos.meowhack.util;

import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

class ColorUtilTest {
    @Test
    void addAlphaTest() {
        Assertions.assertEquals(ColorUtil.addAlpha(0xffffff, 0xff), Long.decode("0xffffffff").intValue());
        Assertions.assertEquals(ColorUtil.addAlpha(0xffffff, 0x00), Long.decode("0x00ffffff").intValue());
        Assertions.assertEquals(ColorUtil.addAlpha(0xffffff, 255), Long.decode("0xffffffff").intValue());
    }
}