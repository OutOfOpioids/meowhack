package me.notkronos.meowhack.event.listener;

public interface ICancellable {
    void cancel(boolean cancelled);
    boolean isCancelled();
}
