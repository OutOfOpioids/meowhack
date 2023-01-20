package me.notkronos.meowhack.event.eventbus;

import me.notkronos.meowhack.event.listener.IListener;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class SubscriberImpl implements ISubscriber {
    protected final List<IListener<?>> listeners = new ArrayList<>();

    @Override
    public Collection<IListener<?>> getListeners()
    {
        return listeners;
    }

}