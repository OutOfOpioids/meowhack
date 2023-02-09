package me.notkronos.meowhack.event.events.render;

import net.minecraftforge.fml.common.eventhandler.Event;

public class AspectRatioEvent extends Event {
    private float aspectRatio;

    public AspectRatioEvent(float aspectRatio)
    {
        this.aspectRatio = aspectRatio;
    }

    public float getAspectRatio() {
        return aspectRatio;
    }

    public void setAspectRatio(float aspectRatio) {
        this.aspectRatio = aspectRatio;
    }
}