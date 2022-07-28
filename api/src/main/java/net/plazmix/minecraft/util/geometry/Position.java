package net.plazmix.minecraft.util.geometry;

import org.apache.commons.lang.builder.HashCodeBuilder;

public final class Position {

    private final Point point;
    private final Direction direction;

    public Position(String world, double x, double y, double z, float pitch, float yaw) {
        this(new Point(world, x, y, z), new Direction(pitch, yaw));
    }

    public Position(Point point, Direction direction) {
        this.point = point;
        this.direction = direction;
    }

    public static Position fromString(String string) {
        String[] data = string.split(":");
        String[] coords = data[1].split(";");
        String[] direction = data[2].split(";");
        return new Position(data[0], Double.parseDouble(coords[0]), Double.parseDouble(coords[1]),
                Double.parseDouble(coords[2]), Float.parseFloat(direction[0]), Float.parseFloat(direction[1]));
    }

    public String getWorld() {
        return point.getWorld();
    }

    public Position switchWorld(String world) {
        return new Position(world, point.getX(), point.getY(), point.getZ(), direction.getPitch(), direction.getYaw());
    }

    public double getX() {
        return point.getX();
    }

    public Position setX(double x) {
        return new Position(point.getWorld(), x, point.getY(), point.getZ(), direction.getPitch(), direction.getYaw());
    }

    public double getY() {
        return point.getY();
    }

    public Position setY(double y) {
        return new Position(point.getWorld(), point.getX(), y, point.getZ(), direction.getPitch(), direction.getYaw());
    }

    public double getZ() {
        return point.getZ();
    }

    public Position setZ(double z) {
        return new Position(point.getWorld(), point.getX(), point.getY(), z, direction.getPitch(), direction.getYaw());
    }

    public int getBlockX() {
        return point.getBlockX();
    }

    public int getBlockY() {
        return point.getBlockY();
    }

    public int getBlockZ() {
        return point.getBlockZ();
    }

    public Position add(UnaryDirection direction, double add) {
        return new Position(point.add(direction, add), this.direction);
    }

    public Position add(double x, double y, double z) {
        return new Position(point.add(x, y, z), direction);
    }

    public float getPitch() {
        return direction.getPitch();
    }

    public Position setPitch(float pitch) {
        return new Position(point.getWorld(), point.getX(), point.getY(), point.getZ(), pitch, direction.getYaw());
    }

    public float getYaw() {
        return direction.getYaw();
    }

    public Position setYaw(float yaw) {
        return new Position(point.getWorld(), point.getX(), point.getY(), point.getZ(), direction.getPitch(), yaw);
    }

    public Point toPoint() {
        return point;
    }

    public Direction toDirection() {
        return direction;
    }

    @Override
    public String toString() {
        return point.toString() + ":" + direction.toString();
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || !(obj instanceof Position))
            return false;

        Position position = (Position) obj;
        return this.point.equals(position.point) && this.direction.equals(position.direction);
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(point).append(direction).toHashCode();
    }
}