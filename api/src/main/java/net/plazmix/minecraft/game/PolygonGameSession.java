package net.plazmix.minecraft.game;

import net.plazmix.minecraft.util.geometry.polygon.Polygon;

public interface PolygonGameSession extends GameSession {

    Polygon getPolygon();
}
