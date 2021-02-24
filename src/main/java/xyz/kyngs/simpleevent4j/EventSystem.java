package xyz.kyngs.simpleevent4j;

import java.util.HashMap;
import java.util.HashSet;
import java.util.Map;
import java.util.Set;
import java.util.concurrent.Executor;
import java.util.concurrent.Executors;

@SuppressWarnings("rawtypes")
public class EventSystem {

    private final Map<Class<?>, Set<EventListener>> map;
    private Executor executor;

    public EventSystem() {
        map = new HashMap<>();
    }

    public EventSystem(int threadCount) {
        this();
        executor = Executors.newFixedThreadPool(threadCount, runnable -> {
            Thread thread = new Thread(runnable);
            thread.setDaemon(true);
            return thread;
        });
    }

    public <T> void registerListener(Class<T> clazz, EventListener<T> listener) {
        map.computeIfAbsent(clazz, k -> new HashSet<>()).add(listener);
    }

    @SuppressWarnings("unchecked")
    public <T> void fireEvent(T instance) {
        Runnable run = () -> {
            for (EventListener listener : map.get(instance.getClass())) {
                listener.onEventFired(instance);
            }
        };
        if (executor == null) {
            run.run();
        } else {
            executor.execute(run);
        }
    }

}
