package me.notkronos.meowhack.event.eventbus;

import me.notkronos.meowhack.event.listener.IListener;

import java.util.Collection;

public interface ISubscriber {
    Collection<IListener<?>> getListeners();
}
