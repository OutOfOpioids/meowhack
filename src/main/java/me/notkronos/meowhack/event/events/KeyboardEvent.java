package me.notkronos.meowhack.event.events;

public class KeyboardEvent {
    private final int key;

    public KeyboardEvent(int key) {
        this.key = key;
    }

    public int getKey() {
        return key;
    }
}
