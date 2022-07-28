package net.plazmix;

import com.google.common.collect.Maps;
import net.plazmix.minecraft.game.GameWorld;
import net.plazmix.minecraft.platform.paper.PaperSpigotClassMapper;
import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Position;
import net.plazmix.game.MinecraftGameWorld;
import net.plazmix.util.Result;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;

import java.util.Map;
import java.util.UUID;

public class MinecraftPaperSpigotClassMapper implements PaperSpigotClassMapper {

    private final Map<UUID, GameWorld> worlds = Maps.newHashMap();

    @Override
    public Location fromPoint(Point point) {
        return new Location(Bukkit.getWorld(point.getWorld()), point.getX(), point.getY(), point.getZ());
    }

    @Override
    public Location fromPosition(Position position) {
        return new Location(Bukkit.getWorld(position.getWorld()), position.getX(), position.getY(), position.getZ(), position.getYaw(), position.getPitch());
    }

    @Override
    public Position toPosition(Location location) {
        return new Position(location.getWorld().getName(), location.getX(), location.getY(), location.getZ(), location.getPitch(), location.getYaw());
    }

    @Override
    public Point toPoint(Location location) {
        return new Point(location.getWorld().getName(), location.getX(), location.getY(), location.getZ());
    }

    @Override
    public World fromGameWorld(GameWorld gameWorld) {
        return Bukkit.getWorld(gameWorld.getName());
    }

    @Override
    public GameWorld toGameWorld(World world) {
        return worlds.computeIfAbsent(world.getUID(), f -> new MinecraftGameWorld(world.getName()));
    }

    @Override
    public Result<Void> unloadWorld(World world) {
        Result<Void> result = new Result<>(worlds.remove(world.getUID()) != null ? Result.Status.SUCCESS : Result.Status.FAILURE);
        Bukkit.unloadWorld(world, false);
        return result;
    }
}
