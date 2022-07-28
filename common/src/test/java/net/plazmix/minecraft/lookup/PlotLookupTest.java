package net.plazmix.minecraft.lookup;

import net.plazmix.minecraft.util.geometry.Tile;
import net.plazmix.minecraft.util.geometry.polygon.Intersections;
import net.plazmix.minecraft.util.geometry.polygon.Plot;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class PlotLookupTest {

    @BeforeAll
    void setup() {
        Intersections.registerIfAbsent(Plot.class, new PlotLookup());
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeFalseWhenWorldsAreDifferent() {
        Plot first = new Plot("test", -2, -2, 2, 2);
        Plot second = new Plot("other", 0, 0, 4, 4);

        assertFalse(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeTrue() {
        Plot first = new Plot("test", -2, -2, 2, 2);
        Plot second = new Plot("test", 0, 0, 4, 4);

        assertTrue(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeFalse() {
        Plot first = new Plot("test", -2, -2, 2, 2);
        Plot second = new Plot("test", 3, 3, 6, 6);

        assertFalse(Intersections.checkSpecific(first, second));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeTrue() {
        Plot plot = new Plot("test", -2, -2, 2, 2);

        assertTrue(plot.contains(new Tile("test", 0, 0)));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeFalseWhenWorldsAreDifferent() {
        Plot plot = new Plot("test", -2, -2, 2, 2);

        assertFalse(plot.contains(new Tile("other", 0, 0)));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeFalse() {
        Plot plot = new Plot("test", -2, -2, 2, 2);

        assertFalse(plot.contains(new Tile("test", 5, 0)));
    }
}
