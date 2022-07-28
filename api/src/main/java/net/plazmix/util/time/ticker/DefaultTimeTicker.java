package net.plazmix.util.time.ticker;

import lombok.Data;

import java.util.UUID;
import java.util.concurrent.TimeUnit;

@Data
public class DefaultTimeTicker implements TimeTicker {

    private final UUID uniqueId;
    private final TimeUnit timeUnit;
    private final long startTicks, endTicks;

    private long currentTicks;
    private boolean paused;
}
