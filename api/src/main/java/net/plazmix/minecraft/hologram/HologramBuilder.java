package net.plazmix.minecraft.hologram;

import net.plazmix.util.function.Builder;

public interface HologramBuilder extends Builder<Hologram> {

    HologramBuilder addLine(HologramLine line);

    HologramBuilder setLine(int index, HologramLine line);
}
