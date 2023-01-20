package me.notkronos.meowhack.event.eventbus;

import me.notkronos.meowhack.event.listener.IListener;

public interface EventBus {
    int DEFAULT_PRIORITY = 10;

    void post(Object object);
    void post(Object object, Class<?> type);
    boolean postCancellable(ICancellable object);
    boolean postCancellable(ICancellable object, Class<?> type);
    void subscribe(Object object);
    void unsubscribe(Object object);
    void register(IListener<?> listener);
    void unregister(IListener<?> listener);
    boolean isSubscribed(Object object);
    boolean hasSubscribers(Class<?> clazz);
    boolean hasSubscribers(Class<?> clazz, Class<?> type);
}
