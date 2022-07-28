package net.plazmix.minecraft.lookup;

import com.google.common.collect.Sets;
import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Tile;
import net.plazmix.minecraft.util.geometry.polygon.*;

import java.util.Collection;

public class CylinderLookup implements IntersectionLookup<Cylinder> {

    @Override
    public Collection<Point> scanPolygon(Cylinder cylinder) {
        return Sets.newHashSet();
    }

    @Override
    public boolean isIntersected(Cylinder cylinder, Polygon another) {
        if (!cylinder.getWorld().equals(another.getWorld()))
            return false;

        if (another instanceof Plot) {
            Plot other = (Plot) another;
            if (other.contains(cylinder.getCenter()))
                return true;

            boolean intersects = false;
            for (Tile tile : other.getVertices()) {
                int distance = (int) Math.sqrt((tile.getBlockX() - cylinder.getCenter().getBlockX())
                        * (tile.getBlockX() - cylinder.getCenter().getBlockX())
                        + (tile.getBlockZ() - cylinder.getCenter().getBlockZ())
                        * (tile.getBlockZ() - cylinder.getCenter().getBlockZ()));
                if (distance <= cylinder.getRadius()) {
                    intersects = true;
                    break;
                }
            }
            return intersects;
        } else if (another instanceof Cuboid) {
            Cuboid cuboid = (Cuboid) another;
            Plot other = cuboid.toPlot();
            if (other.contains(cylinder.getCenter()))
                return true;

            boolean intersects = false;
            for (Tile tile : other.getVertices()) {
                int distance = (int) Math.sqrt((tile.getBlockX() - cylinder.getCenter().getBlockX())
                        * (tile.getBlockX() - cylinder.getCenter().getBlockX())
                        + (tile.getBlockZ() - cylinder.getCenter().getBlockZ())
                        * (tile.getBlockZ() - cylinder.getCenter().getBlockZ()));
                if (distance <= cylinder.getRadius()) {
                    intersects = true;
                    break;
                }
            }
            return intersects && cylinder.getMinimumY() <= cuboid.getMaximumPoint().getBlockY()
                    && cylinder.getMaximumY() >= cuboid.getMinimumPoint().getBlockY();
        } else if (another instanceof Cube) {
            Cube cube = (Cube) another;
            Plot other = cube.toPlot();
            if (other.contains(cylinder.getCenter()))
                return true;

            boolean intersects = false;
            for (Tile tile : other.getVertices()) {
                int distance = (int) Math.sqrt((tile.getBlockX() - cylinder.getCenter().getBlockX())
                        * (tile.getBlockX() - cylinder.getCenter().getBlockX())
                        + (tile.getBlockZ() - cylinder.getCenter().getBlockZ())
                        * (tile.getBlockZ() - cylinder.getCenter().getBlockZ()));
                if (distance <= cylinder.getRadius()) {
                    intersects = true;
                    break;
                }
            }
            return intersects && cylinder.getMinimumY() <= cube.getMaximumPoint().getBlockY()
                    && cylinder.getMaximumY() >= cube.getMinimumPoint().getBlockY();
        } else if (another instanceof Circle) {
            Circle other = (Circle) another;
            if (cylinder.contains(other.getCenter()) || other.contains(cylinder.getCenter()))
                return true;

            double length = Math.sqrt((other.getCenter().getBlockZ() - cylinder.getCenter().getBlockZ()) * (other.getCenter().getBlockZ() - cylinder.getCenter().getBlockZ()) + (other.getCenter().getBlockX() - cylinder.getCenter().getBlockX()) * (other.getCenter().getBlockX() - cylinder.getCenter().getBlockX()));

            return length - other.getRadius() - cylinder.getRadius() <= 0;
        } else if (another instanceof Cylinder) {
            Cylinder other = (Cylinder) another;
            boolean containsCircled = cylinder.contains(other.getCenter()) || other.contains(cylinder.getCenter());
            boolean isInsideYBounds =
                    (cylinder.getMinimumY() >= other.getMinimumY() && cylinder.getMinimumY() <= other.getMaximumY())
                    ||
                    (cylinder.getMaximumY() <= other.getMaximumY() && cylinder.getMaximumY() >= other.getMinimumY());

            if (!isInsideYBounds)
                return false;

            if (containsCircled)
                return true;

            double length = Math.sqrt((other.getCenter().getBlockZ() - cylinder.getCenter().getBlockZ()) * (other.getCenter().getBlockZ() - cylinder.getCenter().getBlockZ()) + (other.getCenter().getBlockX() - cylinder.getCenter().getBlockX()) * (other.getCenter().getBlockX() - cylinder.getCenter().getBlockX()));

            return length - other.getRadius() - cylinder.getRadius() <= 0;
        }

        return false;
    }
}
