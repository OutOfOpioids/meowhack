package me.notkronos.meowhack.event.listener;

public interface IListener<T> extends IEventHandler<T> {
    int getPriority();
    Class<T> getTarget();
    Class<?> getType();
}
