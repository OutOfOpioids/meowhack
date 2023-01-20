package me.notkronos.meowhack.event.listener;

import me.notkronos.meowhack.event.eventbus.EventBus;
import me.notkronos.meowhack.event.listener.IListener;

import java.util.EventListener;

public abstract class ListenerImpl<T> implements IListener<T> {
    Class<T> target;
    Class<?> type;
    int priority;

    public ListenerImpl(Class<T> target) {
        this(target, EventBus.DEFAULT_PRIORITY, null);
    }

    public ListenerImpl(Class<T> target, Class<?> type) {
        this(target, EventBus.DEFAULT_PRIORITY, type);
    }

    public ListenerImpl(Class<T> target, int priority) {
        this(target, priority, null);
    }

    public ListenerImpl(Class<T> target, int priority, Class<?> type) {
        this.priority = priority;
        this.target = target;
        this.type = type;
    }


    @Override
    public int getPriority() {
        return priority;
    }

    @Override
    public Class<T> getTarget() {
        return target;
    }

    @Override
    public Class<?> getType() {
        return type;
    }
}
