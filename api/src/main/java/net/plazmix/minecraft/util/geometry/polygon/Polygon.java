package net.plazmix.minecraft.util.geometry.polygon;

import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Tile;

import java.util.UUID;

public interface Polygon {

    UUID getUniqueId();

    String getWorld();

    boolean contains(Tile tile);

    boolean contains(Point point);
}
