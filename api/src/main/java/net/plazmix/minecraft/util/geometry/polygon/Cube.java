package net.plazmix.minecraft.util.geometry.polygon;

import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Tile;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.UUID;

public class Cube implements Polygon {

    private final UUID uniqueId = UUID.randomUUID();
    private final Cuboid cuboid;
    private final Point center;
    private final double length;

    public Cube(Point center, double length) {
        this.cuboid = new Cuboid(
                new Point(center.getWorld(), center.getX() - length, center.getY() - length, center.getZ() - length),
                new Point(center.getWorld(), center.getX() + length, center.getY() + length, center.getZ() + length));
        this.center = center;
        this.length = length;
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    public Point getCenter() {
        return center;
    }

    public double getLength() {
        return length;
    }

    @Override
    public String getWorld() {
        return cuboid.getWorld();
    }

    public Point getMinimumPoint() {
        return cuboid.getMinimumPoint();
    }

    public Point getMaxXMinYMinZ() {
        return cuboid.getMaxXMinYMinZ();
    }

    public Point getMaxXMinYMaxZ() {
        return cuboid.getMaxXMinYMinZ();
    }

    public Point getMinXMinYMaxZ() {
        return cuboid.getMinXMinYMaxZ();
    }

    public Point getMinXMaxYMaxZ() {
        return cuboid.getMinXMaxYMaxZ();
    }

    public Point getMinXMaxYMinZ() {
        return cuboid.getMinXMaxYMinZ();
    }

    public Point getMaxXMaxYMinZ() {
        return cuboid.getMaxXMaxYMinZ();
    }

    public Point getMaximumPoint() {
        return cuboid.getMaximumPoint();
    }

    public Point[] getVertices() {
        return cuboid.getVertices();
    }

    @Override
    public boolean contains(Tile tile) {
        return cuboid.contains(tile);
    }

    @Override
    public boolean contains(Point point) {
        return cuboid.contains(point);
    }

    public long getVolume() {
        return cuboid.getVolume();
    }

    public long getWidth() {
        return cuboid.getWidth();
    }

    public long getHeight() {
        return cuboid.getHeight();
    }

    public long getDepth() {
        return cuboid.getDepth();
    }

    public Plot toPlot() {
        return cuboid.toPlot();
    }

    public Cuboid toCuboid() {
        return cuboid;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || !(obj instanceof Cube))
            return false;

        Cube cube = (Cube) obj;
        return this.uniqueId.equals(cube.uniqueId);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uniqueId).append(center).append(length).append(cuboid).toHashCode();
    }

    @Override
    public String toString() {
        return "Cube{" +
                "uniqueId=" + uniqueId +
                ", cuboid=" + cuboid +
                ", center=" + center +
                ", length=" + length +
                '}';
    }
}
