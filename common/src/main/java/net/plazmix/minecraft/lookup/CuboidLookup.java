package net.plazmix.minecraft.lookup;

import com.google.common.collect.Sets;
import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Tile;
import net.plazmix.minecraft.util.geometry.polygon.*;

import java.util.Collection;

public class CuboidLookup implements IntersectionLookup<Cuboid> {

    @Override
    public Collection<Point> scanPolygon(Cuboid cuboid) {
        return Sets.newHashSet();
    }

    @Override
    public boolean isIntersected(Cuboid cuboid, Polygon another) {
        if (!cuboid.getWorld().equals(another.getWorld()))
            return false;

        if (another instanceof Plot) {
            Plot other = (Plot) another;
            return other.getMinimum().getBlockX() <= cuboid.getMaximumPoint().getBlockX() && other.getMaximum().getBlockX() >= cuboid.getMinimumPoint().getBlockX()
                    && other.getMaximum().getBlockZ() <= cuboid.getMaximumPoint().getBlockZ() && other.getMaximum().getBlockZ() >= cuboid.getMinimumPoint().getBlockZ();
        } else if (another instanceof Cuboid) {
            Cuboid other = (Cuboid) another;
            return other.getMinimumPoint().getBlockX() <= cuboid.getMaximumPoint().getBlockX() && other.getMaximumPoint().getBlockX() >= cuboid.getMinimumPoint().getBlockX()
                    && other.getMaximumPoint().getBlockZ() <= cuboid.getMaximumPoint().getBlockZ() && other.getMaximumPoint().getBlockZ() >= cuboid.getMinimumPoint().getBlockZ()
                    && other.getMinimumPoint().getBlockY() <= cuboid.getMaximumPoint().getBlockY()
                    && other.getMaximumPoint().getBlockY() >= cuboid.getMinimumPoint().getBlockY();
        } else if (another instanceof Cube) {
            Cube other = (Cube) another;
            return other.getMinimumPoint().getBlockX() <= cuboid.getMaximumPoint().getBlockX() && other.getMaximumPoint().getBlockX() >= cuboid.getMinimumPoint().getBlockX()
                    && other.getMaximumPoint().getBlockZ() <= cuboid.getMaximumPoint().getBlockZ() && other.getMaximumPoint().getBlockZ() >= cuboid.getMinimumPoint().getBlockZ()
                    && other.getMinimumPoint().getBlockY() <= cuboid.getMaximumPoint().getBlockY()
                    && other.getMaximumPoint().getBlockY() >= cuboid.getMinimumPoint().getBlockY();
        } else if (another instanceof Circle) {
            Circle other = (Circle) another;
            if (cuboid.contains(other.getCenter()))
                return true;

            boolean intersects = false;
            Plot plot = cuboid.toPlot();
            for (Tile tile : plot.getVertices()) {
                int distance = (int) Math.sqrt((tile.getBlockX() - other.getCenter().getBlockX())
                        * (tile.getBlockX() - other.getCenter().getBlockX())
                        + (tile.getBlockZ() - other.getCenter().getBlockZ())
                        * (tile.getBlockZ() - other.getCenter().getBlockZ()));
                if (distance <= other.getRadius()) {
                    intersects = true;
                    break;
                }
            }
            return intersects;
        } else if (another instanceof Cylinder) {
            Cylinder other = (Cylinder) another;
            if (cuboid.contains(other.getCenter()))
                return true;

            boolean intersects = false;
            Plot plot = cuboid.toPlot();
            for (Tile tile : plot.getVertices()) {
                int distance = (int) Math.sqrt((tile.getBlockX() - other.getCenter().getBlockX())
                        * (tile.getBlockX() - other.getCenter().getBlockX())
                        + (tile.getBlockZ() - other.getCenter().getBlockZ())
                        * (tile.getBlockZ() - other.getCenter().getBlockZ()));
                if (distance <= other.getRadius()) {
                    intersects = true;
                    break;
                }
            }
            return intersects && other.getMinimumY() <= cuboid.getMaximumPoint().getBlockY()
                    && other.getMaximumY() >= cuboid.getMinimumPoint().getBlockY();
        }

        return false;
    }
}
