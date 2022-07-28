package net.plazmix.minecraft.util.geometry.polygon;

import net.plazmix.minecraft.util.geometry.Point;

import java.util.Collection;

public interface IntersectionLookup<T extends Polygon> {

    Collection<Point> scanPolygon(T polygon);

    boolean isIntersected(T polygon, Polygon another);
}
