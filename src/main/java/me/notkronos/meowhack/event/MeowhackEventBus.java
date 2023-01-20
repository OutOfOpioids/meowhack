package me.notkronos.meowhack.event;

import me.notkronos.meowhack.event.eventbus.EventBus;
import me.notkronos.meowhack.event.eventbus.ICancellable;
import me.notkronos.meowhack.event.eventbus.ISubscriber;
import me.notkronos.meowhack.event.listener.IListener;
import scala.collection.mutable.Subscriber;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArrayList;

@SuppressWarnings({"rawtypes", "unchecked"})
public class MeowhackEventBus implements EventBus {
    private final Map<Class<?>, List<IListener>> listeners;
    private final Set<ISubscriber> subscribers;
    private final Set<IListener> subscribedListeners;

    public MeowhackEventBus() {
        listeners = new ConcurrentHashMap<>();
        subscribers = Collections.newSetFromMap(new ConcurrentHashMap<>());
        subscribedListeners = Collections.newSetFromMap(new ConcurrentHashMap<>());
    }

    @Override
    public void post(Object object) {
        List<IListener> listening = listeners.get(object.getClass());
        if(listening != null) {
            for(IListener listener : listening) {
                listener.invoke(object);
            }
        }
    }

    @Override
    public void post(Object object, Class<?> type) {
        List<IListener> listening = listeners.get(object.getClass());
        if(listening != null) {
            for(IListener listener : listening) {
                if(listener.getType() == null || listener.getType() == type) {
                    listener.invoke(object);
                }
            }
        }
    }

    @Override
    public boolean postCancellable(ICancellable object) {
        List<IListener> listening = listeners.get(object.getClass());
        if(listening != null) {
            for(IListener listener : listening) {
                listener.invoke(object);
                if(object.isCancelled()) {
                    return true;
                }
            }
        }
        return object.isCancelled();
    }

    @Override
    public boolean postCancellable(ICancellable object, Class<?> type) {
        List<IListener> listening = listeners.get(object.getClass());
        if(listening != null) {
            for(IListener listener : listening) {
                if(listener.getType() == null || listener.getType() == type) {
                    listener.invoke(object);
                    if (object.isCancelled()) {
                        return true;
                    }
                }
            }
        }
        return object.isCancelled();
    }

    @Override
    public void subscribe(Object object) {
        if (object instanceof ISubscriber) {
            ISubscriber subscriber = (ISubscriber) object;
            for (IListener<?> listener : subscriber.getListeners()) {
                register(listener);
            }

            subscribers.add(subscriber);
        }
    }

    @Override
    public void unsubscribe(Object object) {
        if (object instanceof ISubscriber) {
            ISubscriber subscriber = (ISubscriber) object;
            for (IListener<?> listener : subscriber.getListeners()) {
                unregister(listener);
            }
            subscribers.remove(subscriber);
        }
    }

    @Override
    public void register(IListener<?> listener) {
        if (subscribedListeners.add(listener)) {
            addAtPriority(listener, listeners.computeIfAbsent(listener.getTarget(), v -> new CopyOnWriteArrayList<>()));
        }
    }

    @Override
    public void unregister(IListener<?> listener) {
        if (subscribedListeners.remove(listener)) {
            List<IListener> list = listeners.get(listener.getTarget());
            if (list != null) {
                list.remove(listener);
            }
        }
    }

    @Override
    public boolean isSubscribed(Object object) {
        if (object instanceof Subscriber)
        {
            return subscribers.contains(object);
        }
        else if (object instanceof IListener)
        {
            return subscribedListeners.contains(object);
        }
        return false;
    }

    @Override
    public boolean hasSubscribers(Class<?> clazz) {
        List<IListener> listening = listeners.get(clazz);
        return listening != null && !listening.isEmpty();
    }

    @Override
    public boolean hasSubscribers(Class<?> clazz, Class<?> type) {
        List<IListener> listening = listeners.get(clazz);
        return listening != null && listening.stream().anyMatch(listener ->
                listener.getType() == null || listener.getType() == type);
    }

    private void addAtPriority(IListener<?> listener, List<IListener> list)
    {
        int index = 0;
        while (index < list.size()
                && listener.getPriority() < list.get(index).getPriority())
        {
            index++;
        }

        list.add(index, listener);
    }
}
