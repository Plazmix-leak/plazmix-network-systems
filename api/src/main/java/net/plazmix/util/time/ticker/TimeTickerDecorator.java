package net.plazmix.util.time.ticker;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

public class TimeTickerDecorator implements TimeTicker {

    private final TimeTicker timeTicker;

    public TimeTickerDecorator(TimeTicker timeTicker) {
        this.timeTicker = timeTicker;
    }

    @Override
    public UUID getUniqueId() {
        return timeTicker.getUniqueId();
    }

    @Override
    public TimeUnit getTimeUnit() {
        return timeTicker.getTimeUnit();
    }

    @Override
    public long getStartTicks() {
        return timeTicker.getStartTicks();
    }

    @Override
    public long getEndTicks() {
        return timeTicker.getEndTicks();
    }

    @Override
    public long getCurrentTicks() {
        return timeTicker.getCurrentTicks();
    }

    @Override
    public void setCurrentTicks(long ticks) {
        timeTicker.setCurrentTicks(ticks);
    }

    @Override
    public boolean isPaused() {
        return timeTicker.isPaused();
    }

    @Override
    public void setPaused(boolean paused) {
        timeTicker.setPaused(paused);
    }

    @Override
    public void resetTicks() {
        timeTicker.resetTicks();
    }

    @Override
    public void addTicks(int ticks) {
        timeTicker.addTicks(ticks);
    }

    @Override
    public void subtractTime(int ticks) {
        timeTicker.subtractTime(ticks);
    }
}
