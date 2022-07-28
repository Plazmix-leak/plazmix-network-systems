package net.plazmix.util;

import lombok.Getter;

import java.util.Optional;

@Getter
public final class Result<E> {

    private final Status status;
    private final Optional<String> description;
    private final Optional<E> entity;

    public Result(Status status) {
        this.status = status;
        this.description = Optional.empty();
        this.entity = Optional.empty();
    }

    public Result(Status status, E entity) {
        this.status = status;
        this.description = Optional.empty();
        this.entity = Optional.ofNullable(entity);
    }

    public Result(Status status, String description) {
        this.status = status;
        this.description = Optional.ofNullable(description);
        this.entity = Optional.empty();
    }

    public Result(Status status, String description, E entity) {
        this.status = status;
        this.description = Optional.ofNullable(description);
        this.entity = Optional.ofNullable(entity);
    }

    public Result<E> onSuccess(Runnable runnable) {
        if (status == Status.SUCCESS)
            runnable.run();
        return this;
    }

    public Result<E> onFailure(Runnable runnable) {
        if (status == Status.FAILURE)
            runnable.run();
        return this;
    }

    public enum Status {SUCCESS, FAILURE}
}
