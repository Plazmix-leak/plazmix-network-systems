package net.plazmix.minecraft.platform.paper;

import net.plazmix.minecraft.game.GameWorld;
import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.Position;
import net.plazmix.util.Result;
import org.bukkit.Location;
import org.bukkit.World;

public interface PaperSpigotClassMapper {

    Location fromPoint(Point point);

    Location fromPosition(Position position);

    Position toPosition(Location location);

    Point toPoint(Location location);

    World fromGameWorld(GameWorld gameWorld);

    GameWorld toGameWorld(World world);

    Result<Void> unloadWorld(World world);
}
