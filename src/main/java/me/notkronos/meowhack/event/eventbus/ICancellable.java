package me.notkronos.meowhack.event.eventbus;

public interface ICancellable {
    void cancel(boolean cancelled);
    boolean isCancelled();
}
