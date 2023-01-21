package me.notkronos.meowhack.event.eventbus;

import me.notkronos.meowhack.event.listener.Listener;

import java.util.Collection;

public interface Subscriber {
    Collection<Listener<?>> getListeners();
}
