package me.notkronos.meowhack.event.listener;

public interface Listener<T> extends IEventHandler<T> {
    int getPriority();
    Class<T> getTarget();
    Class<?> getType();
}
