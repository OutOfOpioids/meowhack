package me.notkronos.meowhack.manager;

import me.notkronos.meowhack.util.Wrapper;

public class Manager {
    private final String name;

    public Manager(String name) {
        this.name = name;
    }

    /**
     * Runs every update ticks (i.e. 20 times a second)
     */
    public void onUpdate() {

    }

    /**
     * Runs every tick (i.e. 40 times a second)
     */
    public void onTick() {

    }

    /**
     * Runs on the separate module thread (i.e. every cpu tick)
     */
    public void onThread() {

    }

    /**
     * Runs on the game overlay tick (i.e. once every frame)
     */
    public void onRender2D() {

    }

    /**
     * Runs on the global render tick (i.e. once every frame)
     */
    public void onRender3D() {

    }
}
