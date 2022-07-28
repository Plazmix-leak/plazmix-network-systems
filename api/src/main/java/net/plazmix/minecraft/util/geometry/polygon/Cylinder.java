package net.plazmix.minecraft.util.geometry.polygon;

import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Tile;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.UUID;

public class Cylinder implements Polygon {

    private final UUID uniqueId = UUID.randomUUID();
    private final Circle circle;
    private final double yMin, yMax;

    public Cylinder(Tile center, double radius, double yMin, double yMax) {
        this(new Circle(center, radius), yMin, yMax);
    }

    public Cylinder(Circle circle, double yMin, double yMax) {
        this.circle = circle;
        this.yMin = Math.min(yMin, yMax);
        this.yMax = Math.max(yMin, yMax);
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getWorld() {
        return circle.getWorld();
    }

    public Tile getCenter() {
        return circle.getCenter();
    }

    public double getRadius() {
        return circle.getRadius();
    }

    @Override
    public boolean contains(Tile tile) {
        return circle.contains(tile);
    }

    @Override
    public boolean contains(Point point) {
        return circle.contains(point.toTile()) && point.getBlockY() >= this.getMinimumY()
                && point.getBlockY() <= this.getMaximumY();
    }

    public double getMinimumY() {
        return yMin;
    }

    public double getMaximumY() {
        return yMax;
    }

    public Circle toCircle() {
        return new Circle(circle.getCenter(), circle.getRadius());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || !(obj instanceof Cylinder))
            return false;

        Cylinder cylinder = (Cylinder) obj;
        return this.uniqueId.equals(cylinder.uniqueId);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uniqueId).append(circle).append(yMin).append(yMax).toHashCode();
    }

    @Override
    public String toString() {
        return "Cylinder{" +
                "uniqueId=" + uniqueId +
                ", circle=" + circle +
                ", yMin=" + yMin +
                ", yMax=" + yMax +
                '}';
    }
}
