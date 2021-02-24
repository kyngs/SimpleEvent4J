package xyz.kyngs.simpleevent4j;

@FunctionalInterface
public interface EventListener<T> {

    void onEventFired(T event);

}
