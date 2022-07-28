package net.plazmix.minecraft.util.geometry.polygon;

import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Tile;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.UUID;

public class Circle implements Polygon {

    private final UUID uniqueId = UUID.randomUUID();
    private final Tile center;
    private final double radius;

    public Circle(String world, double x, double z, double radius) {
        this(new Tile(world, x, z), radius);
    }

    public Circle(Tile center, double radius) {
        this.center = center;
        this.radius = radius;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getWorld() {
        return center.getWorld();
    }

    public Tile getCenter() {
        return center;
    }

    public double getRadius() {
        return radius;
    }

    @Override
    public boolean contains(Tile tile) {
        if (!getWorld().equals(tile.getWorld()))
            return false;

        return Math.pow((tile.getBlockX() - center.getBlockX()), 2)
                + Math.pow((tile.getBlockZ() - center.getBlockZ()), 2) <= Math.pow(radius, 2);
    }

    @Override
    public boolean contains(Point point) {
        return contains(point.toTile());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || !(obj instanceof Circle))
            return false;

        Circle circle = (Circle) obj;
        return this.uniqueId.equals(circle.uniqueId);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uniqueId).append(center).append(radius).toHashCode();
    }

    @Override
    public String toString() {
        return "Circle{" +
                "uniqueId=" + uniqueId +
                ", center=" + center +
                ", radius=" + radius +
                '}';
    }
}
