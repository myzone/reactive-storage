package com.myzone.reactive.observable;

import com.myzone.annotations.NotNull;
import com.myzone.reactive.events.ChangeEvent;

import java.util.concurrent.CopyOnWriteArrayList;

/**
 * @author myzone
 * @date 30.12.13
 */
public abstract class AbstractObservable<T, E extends ChangeEvent<T>> implements Observable<T, E> {

    private final @NotNull CopyOnWriteArrayList<ChangeListener<T, ? super E>> listeners;

    protected AbstractObservable() {
        this.listeners = new CopyOnWriteArrayList<>();
    }

    public @Override void addListener(@NotNull ChangeListener<T, ? super E> changeListener) {
        synchronized (listeners) {
            listeners.add(changeListener);
        }
    }

    public @Override void removeListener(@NotNull ChangeListener<T, ? super E> changeListener) {
        synchronized (listeners) {
            listeners.remove(changeListener);
        }
    }

    protected void fireEvent(@NotNull E changeEvent) {
        synchronized (listeners) {
            listeners.forEach(listener -> {
                listener.onChange(this, changeEvent);
            });
        }
    }

}