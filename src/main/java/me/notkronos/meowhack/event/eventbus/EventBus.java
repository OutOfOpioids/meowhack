package me.notkronos.meowhack.event.eventbus;

import me.notkronos.meowhack.event.listener.ICancellable;
import me.notkronos.meowhack.event.listener.Listener;

public interface EventBus {
    int DEFAULT_PRIORITY = 10;

    void post(Object object);
    void post(Object object, Class<?> type);
    boolean postCancellable(ICancellable object);
    boolean postCancellable(ICancellable object, Class<?> type);
    void subscribe(Object object);
    void unsubscribe(Object object);
    void register(Listener<?> listener);
    void unregister(Listener<?> listener);
    boolean isSubscribed(Object object);
    boolean hasSubscribers(Class<?> clazz);
    boolean hasSubscribers(Class<?> clazz, Class<?> type);
}
