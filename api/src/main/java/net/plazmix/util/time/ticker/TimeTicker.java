package net.plazmix.util.time.ticker;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public interface TimeTicker {

    static TimeTicker createDefault(TimeUnit timeUnit, long startTicks, long endTicks) {
        return new DefaultTimeTicker(UUID.randomUUID(), timeUnit, startTicks, endTicks);
    }

    static TimeTicker createTimer(TimeUnit timeUnit, long from) {
        return createDefault(timeUnit, from, 0);
    }

    static TimeTicker createInfinite(TimeUnit timeUnit) {
        return createDefault(timeUnit, -1, -1);
    }

    UUID getUniqueId();

    TimeUnit getTimeUnit();

    long getStartTicks();

    long getEndTicks();

    long getCurrentTicks();

    void setCurrentTicks(long ticks);

    boolean isPaused();

    void setPaused(boolean paused);

    default void resetTicks() {
        setCurrentTicks(getStartTicks());
    }

    default void addTicks(int ticks) {
        setCurrentTicks(getCurrentTicks() + ticks);
    }

    default void subtractTime(int ticks) {
        setCurrentTicks(getCurrentTicks() - ticks);
    }
}
