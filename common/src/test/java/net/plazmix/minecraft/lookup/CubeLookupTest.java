package net.plazmix.minecraft.lookup;

import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.polygon.Cube;
import net.plazmix.minecraft.util.geometry.polygon.Intersections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CubeLookupTest {

    @BeforeAll
    void setup() {
        Intersections.registerIfAbsent(Cube.class, new CubeLookup());
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeFalseWhenWorldsAreDifferent() {
        Cube first = new Cube(new Point("test", 0, 0, 0), 4);
        Cube second = new Cube(new Point("other", 0, 0, 0), 4);

        assertFalse(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeTrue() {
        Cube first = new Cube(new Point("test", 0, 0, 0), 4);
        Cube second = new Cube(new Point("test", 2, 2, 2), 4);

        assertTrue(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeFalse() {
        Cube first = new Cube(new Point("test", 0, 0, 0), 4);
        Cube second = new Cube(new Point("test", 20, 20, 20), 4);

        assertFalse(Intersections.checkSpecific(first, second));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeTrue() {
        Cube cube = new Cube(new Point("test", 0, 0, 0), 4);

        assertTrue(cube.contains(new Point("test", 2, 1, 3)));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeFalseWhenWorldsAreDifferent() {
        Cube cube = new Cube(new Point("test", 0, 0, 0), 4);

        assertFalse(cube.contains(new Point("other", 2, 1, 3)));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeFalse() {
        Cube cube = new Cube(new Point("test", 0, 0, 0), 4);

        assertFalse(cube.contains(new Point("test", 5, 4, 6)));
    }
}
