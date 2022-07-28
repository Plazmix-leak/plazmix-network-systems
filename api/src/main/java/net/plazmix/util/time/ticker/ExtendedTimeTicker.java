package net.plazmix.util.time.ticker;

public abstract class ExtendedTimeTicker extends TimeTickerDecorator {

    public ExtendedTimeTicker(TimeTicker timeTicker) {
        super(timeTicker);
    }

    public abstract void onTick();

    @Override
    public void setCurrentTicks(long ticks) {
        onTick();
        super.setCurrentTicks(ticks);
    }
}
