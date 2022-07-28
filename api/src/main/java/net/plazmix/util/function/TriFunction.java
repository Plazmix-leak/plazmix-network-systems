package net.plazmix.util.function;

public interface TriFunction<T, U, O, R> {

    R apply(T t, U u, O o);
}
