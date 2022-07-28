package net.plazmix.minecraft.lookup;

import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.polygon.Cuboid;
import net.plazmix.minecraft.util.geometry.polygon.Intersections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CuboidLookupTest {

    @BeforeAll
    void setup() {
        Intersections.registerIfAbsent(Cuboid.class, new CuboidLookup());
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeFalseWhenWorldsAreDifferent() {
        Cuboid first = new Cuboid(new Point("test", 0, 0, 0), new Point("test", 4, 4, 4));
        Cuboid second = new Cuboid(new Point("other", 0, 0, 0), new Point("other", 4, 4, 4));

        assertFalse(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeTrue() {
        Cuboid first = new Cuboid(new Point("test", 0, 0, 0), new Point("test", 4, 4, 4));
        Cuboid second = new Cuboid(new Point("test", 2, 2, 2), new Point("test", 6, 6, 6));

        assertTrue(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeFalse() {
        Cuboid first = new Cuboid(new Point("test", 0, 0, 0), new Point("test", 4, 4, 4));
        Cuboid second = new Cuboid(new Point("test", 5, 5, 5), new Point("test", 10, 10, 10));

        assertFalse(Intersections.checkSpecific(first, second));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeTrue() {
        Cuboid cuboid = new Cuboid(new Point("test", 0, 0, 0), new Point("test", 4, 4, 4));

        assertTrue(cuboid.contains(new Point("test", 2, 1, 3)));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeFalseWhenWorldsAreDifferent() {
        Cuboid cuboid = new Cuboid(new Point("test", 0, 0, 0), new Point("test", 4, 4, 4));

        assertFalse(cuboid.contains(new Point("other", 2, 1, 3)));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeFalse() {
        Cuboid cuboid = new Cuboid(new Point("test", 0, 0, 0), new Point("test", 4, 4, 4));

        assertFalse(cuboid.contains(new Point("test", 5, 4, 6)));
    }
}
