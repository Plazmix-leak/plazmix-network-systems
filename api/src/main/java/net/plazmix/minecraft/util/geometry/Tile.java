package net.plazmix.minecraft.util.geometry;

import org.apache.commons.lang.builder.HashCodeBuilder;

public final class Tile {

    private final String world;
    private final double x, z;

    public Tile(String world, double x, double z) {
        this.world = world;
        this.x = x;
        this.z = z;
    }

    public static Tile fromString(String string) {
        String[] data = string.split(":");
        String[] coords = data[1].split(";");
        return new Tile(data[0], Double.parseDouble(coords[0]), Double.parseDouble(coords[1]));
    }

    public static String toString(Tile position) {
        return position.toString();
    }

    public String getWorld() {
        return world;
    }

    public double getX() {
        return x;
    }

    public double getZ() {
        return z;
    }

    public int getBlockX() {
        return (int) Math.floor(x);
    }

    public int getBlockZ() {
        return (int) Math.floor(z);
    }

    public Tile add(UnaryDirection direction, double add) {
        double x = 0, z = 0;

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
            default:
                break;
        }

        return add(x, z);
    }

    public Tile add(double x, double z) {
        return new Tile(world, this.x + x, this.z + z);
    }

    @Override
    public String toString() {
        return world + ":" + x + ";" + z;
    }

    @Override
    public boolean equals(Object obj) {
        if (obj == this)
            return true;

        if (obj == null || !(obj instanceof Tile))
            return false;

        Tile point = (Tile) obj;
        return this.world.equals(point.world) && this.x == point.x && this.z == point.z;
    }

    @Override
    public int hashCode() {
        return new HashCodeBuilder().append(world).append(x).append(z).toHashCode();
    }
}