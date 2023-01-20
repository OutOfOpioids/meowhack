package me.notkronos.meowhack.event.listener;

public interface IEventHandler<T> {
    void invoke(T event);
}
