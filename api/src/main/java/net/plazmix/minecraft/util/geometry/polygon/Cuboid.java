package net.plazmix.minecraft.util.geometry.polygon;

import com.google.common.base.Preconditions;
import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Tile;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.UUID;

public class Cuboid implements Polygon {

    private final UUID uniqueId = UUID.randomUUID();
    private final Point min, max, maxXMinYMinZ, maxXMinYMaxZ, minXMinYMaxZ, minXMaxYMaxZ, minXMaxYMinZ, maxXMaxYMinZ;

    public Cuboid(String world, int xMin, int yMin, int zMin, int xMax, int yMax, int zMax) {
        this(new Point(world, xMin, yMin, zMin), new Point(world, xMax, yMax, zMax));
    }

    public Cuboid(Point min, Point max) {
        Preconditions.checkArgument(min.getWorld().equals(max.getWorld()), "Different worlds of points!");
        String world = min.getWorld();

        int xMin = Math.min(min.getBlockX(), max.getBlockX()), yMin = Math.min(min.getBlockY(), max.getBlockY()),
                zMin = Math.min(min.getBlockZ(), max.getBlockZ()), xMax = Math.max(min.getBlockX(), max.getBlockX()),
                yMax = Math.max(min.getBlockY(), max.getBlockY()), zMax = Math.max(min.getBlockZ(), max.getBlockZ());

        this.min = new Point(world, xMin, yMin, zMin);
        this.max = new Point(world, xMax, yMax, zMax);

        this.maxXMinYMinZ = new Point(world, xMax, yMin, zMin);
        this.maxXMinYMaxZ = new Point(world, xMax, yMin, zMax);
        this.minXMinYMaxZ = new Point(world, xMin, yMin, zMax);
        this.minXMaxYMaxZ = new Point(world, xMin, yMax, zMax);
        this.minXMaxYMinZ = new Point(world, xMin, yMax, zMin);
        this.maxXMaxYMinZ = new Point(world, xMax, yMax, zMin);
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getWorld() {
        return min.getWorld();
    }

    public Point getMinimumPoint() {
        return min;
    }

    public Point getMaxXMinYMinZ() {
        return maxXMinYMinZ;
    }

    public Point getMaxXMinYMaxZ() {
        return maxXMinYMaxZ;
    }

    public Point getMinXMinYMaxZ() {
        return minXMinYMaxZ;
    }

    public Point getMinXMaxYMaxZ() {
        return minXMaxYMaxZ;
    }

    public Point getMinXMaxYMinZ() {
        return minXMaxYMinZ;
    }

    public Point getMaxXMaxYMinZ() {
        return maxXMaxYMinZ;
    }

    public Point getMaximumPoint() {
        return max;
    }

    public Point[] getVertices() {
        return new Point[]{getMinimumPoint(), getMaxXMinYMinZ(), getMaxXMinYMaxZ(), getMinXMinYMaxZ(),
                getMinXMaxYMaxZ(), getMinXMaxYMinZ(), getMaxXMaxYMinZ(), getMaximumPoint()};
    }

    @Override
    public boolean contains(Tile tile) {
        return toPlot().contains(tile);
    }

    @Override
    public boolean contains(Point point) {
        return contains(point.toTile()) && min.getBlockY() <= point.getBlockY() && max.getBlockY() >= point.getBlockY();
    }

    public long getVolume() {
        return getWidth() * getHeight() * getDepth();
    }

    public long getWidth() {
        return max.getBlockX() - min.getBlockX() + 1;
    }

    public long getHeight() {
        return max.getBlockY() - min.getBlockY() + 1;
    }

    public long getDepth() {
        return max.getBlockZ() - min.getBlockZ() + 1;
    }

    public Plot toPlot() {
        return new Plot(getMinimumPoint().toTile(), getMaximumPoint().toTile());
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || !(obj instanceof Cuboid))
            return false;

        Cuboid cuboid = (Cuboid) obj;
        return this.uniqueId.equals(cuboid.uniqueId);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uniqueId).append(min).append(max).toHashCode();
    }

    @Override
    public String toString() {
        return "Cuboid{" +
                "uniqueId=" + uniqueId +
                ", min=" + min +
                ", max=" + max +
                ", maxXMinYMinZ=" + maxXMinYMinZ +
                ", maxXMinYMaxZ=" + maxXMinYMaxZ +
                ", minXMinYMaxZ=" + minXMinYMaxZ +
                ", minXMaxYMaxZ=" + minXMaxYMaxZ +
                ", minXMaxYMinZ=" + minXMaxYMinZ +
                ", maxXMaxYMinZ=" + maxXMaxYMinZ +
                '}';
    }
}
