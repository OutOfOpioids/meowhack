package me.notkronos.meowhack.util;

import org.lwjgl.input.Keyboard;

public class Bind {
    int key;

    public Bind(int key) {
        this.key = key;
    }
    public int getKey() {
        return key;
    }

    public void setKey(int key) {
        this.key = key;
    }

    public static Bind none() {
        return new Bind(-1);
    }

    public static Bind fromKey(int key) {
        return new Bind(key);
    }

    public String toString() {
        if(key == -1) {
            return "NONE";
        } else {
            return Keyboard.getKeyName(key);
        }
    }
}
