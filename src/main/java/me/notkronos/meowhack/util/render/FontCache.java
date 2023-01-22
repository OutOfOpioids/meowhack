package me.notkronos.meowhack.util.render;

import org.lwjgl.opengl.GL11;

public class FontCache {

    private int displayList;
    private long lastUsage;

    private boolean deleted;

    public FontCache(int displayList, long lastUsage) {
        this.displayList = displayList;
        this.lastUsage = lastUsage;
    }

    public void finalize() {
        if (!deleted) {
            GL11.glDeleteLists(displayList, 1);
        }
    }

    public void setDisplayList(int in) {
        displayList = in;
    }

    public int getDisplayList() {
        return displayList;
    }

    public void setLastUsage(long in) {
        lastUsage = in;
    }

    public long getLastUsage() {
        return lastUsage;
    }

    public void delete() {
        deleted = true;
    }

    public boolean isDeleted() {
        return deleted;
    }
}