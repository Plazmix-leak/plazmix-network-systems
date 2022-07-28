package net.plazmix.minecraft.lookup;

import com.google.common.collect.Sets;
import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Tile;
import net.plazmix.minecraft.util.geometry.polygon.*;

import java.util.Collection;

public class PlotLookup implements IntersectionLookup<Plot> {

    @Override
    public Collection<Point> scanPolygon(Plot plot) {
        return Sets.newHashSet();
    }

    @Override
    public boolean isIntersected(Plot plot, Polygon another) {
        if (!plot.getWorld().equals(another.getWorld()))
            return false;

        if (another instanceof Plot) {
            Plot other = (Plot) another;
            return other.getMinimum().getBlockX() <= plot.getMaximum().getBlockX() && other.getMaximum().getBlockX() >= plot.getMinimum().getBlockX()
                    && other.getMaximum().getBlockZ() <= plot.getMaximum().getBlockZ() && other.getMaximum().getBlockZ() >= plot.getMinimum().getBlockZ();
        } else if (another instanceof Cuboid) {
            Cuboid other = (Cuboid) another;
            return other.getMinimumPoint().getBlockX() <= plot.getMaximum().getBlockX() && other.getMaximumPoint().getBlockX() >= plot.getMinimum().getBlockX()
                    && other.getMaximumPoint().getBlockZ() <= plot.getMaximum().getBlockZ() && other.getMaximumPoint().getBlockZ() >= plot.getMinimum().getBlockZ();
        } else if (another instanceof Cube) {
            Cube other = (Cube) another;
            return other.getMinimumPoint().getBlockX() <= plot.getMaximum().getBlockX() && other.getMaximumPoint().getBlockX() >= plot.getMinimum().getBlockX()
                    && other.getMaximumPoint().getBlockZ() <= plot.getMaximum().getBlockZ() && other.getMaximumPoint().getBlockZ() >= plot.getMinimum().getBlockZ();
        } else if (another instanceof Circle) {
            Circle other = (Circle) another;
            if (plot.contains(other.getCenter()))
                return true;

            boolean intersects = false;
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
            if (plot.contains(other.getCenter()))
                return true;

            boolean intersects = false;
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
        }

        return false;
    }
}
