package net.plazmix.minecraft.util.geometry;

import org.apache.commons.lang.builder.HashCodeBuilder;

public final class Point {

    private final Tile tile;
    private final double y;

    public Point(Point point) {
        this(point.getWorld(), point.getX(), point.getBlockY(), point.getZ());
    }

    public Point(Tile tile, double y) {
        this(tile.getWorld(), tile.getX(), y, tile.getZ());
    }

    public Point(String world, double x, double y, double z) {
        this.tile = new Tile(world, x, z);
        this.y = y;
    }

    public static Point fromString(String string) {
        String[] data = string.split(":");
        String[] coords = data[1].split(";");
        return new Point(data[0], Double.parseDouble(coords[0]), Double.parseDouble(coords[1]),
                Double.parseDouble(coords[2]));
    }

    public static String toString(Point position) {
        return position.toString();
    }

    public String getWorld() {
        return tile.getWorld();
    }

    public double getX() {
        return tile.getX();
    }

    public double getY() {
        return y;
    }

    public double getZ() {
        return tile.getZ();
    }

    public int getBlockX() {
        return tile.getBlockX();
    }

    public int getBlockY() {
        return (int) Math.floor(y);
    }

    public int getBlockZ() {
        return tile.getBlockZ();
    }

    public Point add(UnaryDirection direction, double add) {
        double x = 0, y = 0, z = 0;

        switch (direction) {
            case NORTH:
                z -= add;
                break;
            case WEST:
                x -= add;
                break;
            case SOUTH:
                z += add;
                break;
            case EAST:
                x += add;
                break;
            case UP:
                y += add;
                break;
            case DOWN:
                y -= add;
                break;
        }

        return add(x, y, z);
    }

    public Point add(double x, double y, double z) {
        return new Point(tile.add(x, z), this.y + y);
    }

    public Tile toTile() {
        return tile;
    }

    @Override
    public String toString() {
        return tile.getWorld() + ":" + tile.getX() + ";" + y + ";" + tile.getZ();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || !(obj instanceof Point))
            return false;

        Point point = (Point) obj;
        return this.tile.equals(point.toTile()) && this.y == point.y;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(tile).append(y).toHashCode();
    }
}