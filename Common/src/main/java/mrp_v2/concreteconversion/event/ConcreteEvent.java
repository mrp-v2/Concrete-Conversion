package mrp_v2.concreteconversion.event;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

public final class ConcreteEvent<T extends IConcreteEvent> {

    private final List<T> listeners = new ArrayList<>();
    private final Function<List<T>, T> invoker;

    public ConcreteEvent(Function<List<T>, T> invoker) {
        this.invoker = invoker;
    }

    public void register(T listener) {
        this.listeners.add((listener));
    }

    public T post() {
        return this.invoker.apply(this.listeners);
    }
}
