package net.plazmix.minecraft.util.geometry.polygon;

import com.google.common.base.Preconditions;
import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Tile;
import org.apache.commons.lang.builder.HashCodeBuilder;

import java.util.UUID;

public class Plot implements Polygon {

    private final UUID uniqueId = UUID.randomUUID();
    private final Tile min, max, minXMaxZ, maxXMinZ;

    public Plot(String world, int xMin, int zMin, int xMax, int zMax) {
        this(new Tile(world, xMin, zMin), new Tile(world, xMax, zMax));
    }

    public Plot(Tile min, Tile max) {
        Preconditions.checkArgument(min.getWorld().equals(max.getWorld()), "Different worlds of points!");

        String world = min.getWorld();

        int xMin = Math.min(min.getBlockX(), max.getBlockX()), zMin = Math.min(min.getBlockZ(), max.getBlockZ()),
                xMax = Math.max(min.getBlockX(), max.getBlockX()), zMax = Math.max(min.getBlockZ(), max.getBlockZ());

        this.min = new Tile(world, xMin, zMin);
        this.max = new Tile(world, xMax, zMax);
        this.minXMaxZ = new Tile(world, xMin, zMax);
        this.maxXMinZ = new Tile(world, xMax, zMin);
    }

    @Override
    public UUID getUniqueId() {
        return uniqueId;
    }

    @Override
    public String getWorld() {
        return min.getWorld();
    }

    public Tile getMinimum() {
        return min;
    }

    public Tile getMinXMaxZ() {
        return minXMaxZ;
    }

    public Tile getMaxXMinZ() {
        return maxXMinZ;
    }

    public Tile getMaximum() {
        return max;
    }

    public Tile[] getVertices() {
        return new Tile[]{getMinimum(), getMinXMaxZ(), getMaxXMinZ(), getMaximum()};
    }

    @Override
    public boolean contains(Tile tile) {
        if (!getWorld().equals(tile.getWorld()))
            return false;

        return min.getBlockX() <= tile.getBlockX() && min.getBlockZ() <= tile.getBlockZ()
                && max.getBlockX() >= tile.getBlockX() && max.getBlockZ() >= tile.getBlockZ();
    }

    @Override
    public boolean contains(Point point) {
        return contains(point.toTile());
    }

    public long getSquare() {
        return getWidth() * getDepth();
    }

    public long getWidth() {
        return max.getBlockX() - min.getBlockX() + 1;
    }

    public long getDepth() {
        return max.getBlockZ() - min.getBlockZ() + 1;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || !(obj instanceof Plot))
            return false;

        Plot plot = (Plot) obj;
        return this.uniqueId.equals(plot.uniqueId);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(uniqueId).append(min).append(max).toHashCode();
    }

    @Override
    public String toString() {
        return "Plot{" +
                "uniqueId=" + uniqueId +
                ", min=" + min +
                ", max=" + max +
                ", minXMaxZ=" + minXMaxZ +
                ", maxXMinZ=" + maxXMinZ +
                '}';
    }
}
