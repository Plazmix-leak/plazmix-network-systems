package net.plazmix.minecraft.lookup;

import com.google.common.collect.Sets;
import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Tile;
import net.plazmix.minecraft.util.geometry.polygon.*;

import java.util.Collection;

public class CircleLookup implements IntersectionLookup<Circle> {

    @Override
    public Collection<Point> scanPolygon(Circle circle) {
        return Sets.newHashSet();
    }

    @Override
    public boolean isIntersected(Circle circle, Polygon another) {
        if (!circle.getWorld().equals(another.getWorld()))
            return false;

        if (another instanceof Plot) {
            Plot other = (Plot) another;
            if (other.contains(circle.getCenter()))
                return true;

            boolean intersects = false;
            for (Tile tile : other.getVertices()) {
                int distance = (int) Math.sqrt((tile.getBlockX() - circle.getCenter().getBlockX())
                        * (tile.getBlockX() - circle.getCenter().getBlockX())
                        + (tile.getBlockZ() - circle.getCenter().getBlockZ())
                        * (tile.getBlockZ() - circle.getCenter().getBlockZ()));
                if (distance <= circle.getRadius()) {
                    intersects = true;
                    break;
                }
            }
            return intersects;
        } else if (another instanceof Cuboid) {
            Plot other = ((Cuboid) another).toPlot();
            if (other.contains(circle.getCenter()))
                return true;

            boolean intersects = false;
            for (Tile tile : other.getVertices()) {
                int distance = (int) Math.sqrt((tile.getBlockX() - circle.getCenter().getBlockX())
                        * (tile.getBlockX() - circle.getCenter().getBlockX())
                        + (tile.getBlockZ() - circle.getCenter().getBlockZ())
                        * (tile.getBlockZ() - circle.getCenter().getBlockZ()));
                if (distance <= circle.getRadius()) {
                    intersects = true;
                    break;
                }
            }
            return intersects;
        } else if (another instanceof Cube) {
            Plot other = ((Cube) another).toPlot();
            if (other.contains(circle.getCenter()))
                return true;

            boolean intersects = false;
            for (Tile tile : other.getVertices()) {
                int distance = (int) Math.sqrt((tile.getBlockX() - circle.getCenter().getBlockX())
                        * (tile.getBlockX() - circle.getCenter().getBlockX())
                        + (tile.getBlockZ() - circle.getCenter().getBlockZ())
                        * (tile.getBlockZ() - circle.getCenter().getBlockZ()));
                if (distance <= circle.getRadius()) {
                    intersects = true;
                    break;
                }
            }
            return intersects;
        } else if (another instanceof Circle) {
            Circle other = (Circle) another;
            if (circle.contains(other.getCenter()) || other.contains(circle.getCenter()))
                return true;

            double length = Math.sqrt((other.getCenter().getBlockZ() - circle.getCenter().getBlockZ()) * (other.getCenter().getBlockZ() - circle.getCenter().getBlockZ()) + (other.getCenter().getBlockX() - circle.getCenter().getBlockX()) * (other.getCenter().getBlockX() - circle.getCenter().getBlockX()));

            return length - other.getRadius() - circle.getRadius() <= 0;
        } else if (another instanceof Cylinder) {
            Circle other = ((Cylinder) another).toCircle();
            if (circle.contains(other.getCenter()) || other.contains(circle.getCenter()))
                return true;

            double length = Math.sqrt((other.getCenter().getBlockZ() - circle.getCenter().getBlockZ()) * (other.getCenter().getBlockZ() - circle.getCenter().getBlockZ()) + (other.getCenter().getBlockX() - circle.getCenter().getBlockX()) * (other.getCenter().getBlockX() - circle.getCenter().getBlockX()));

            return length - other.getRadius() - circle.getRadius() <= 0;
        }

        return false;
    }
}
