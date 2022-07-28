package net.plazmix.util.function;

import java.util.Objects;

public interface TriConsumer<T, U, O> {

    void accept(T t, U u, O o);

    default TriConsumer<T, U, O> andThen(TriConsumer<? super T, ? super U, ? super O> after) {
        Objects.requireNonNull(after);
        return (T t, U u, O o) -> {
            accept(t, u, o);
            after.accept(t, u, o);
        };
    }
}
