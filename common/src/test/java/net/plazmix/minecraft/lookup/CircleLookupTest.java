package net.plazmix.minecraft.lookup;

import net.plazmix.minecraft.util.geometry.Tile;
import net.plazmix.minecraft.util.geometry.polygon.Circle;
import net.plazmix.minecraft.util.geometry.polygon.Intersections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CircleLookupTest {

    @BeforeAll
    void setup() {
        Intersections.registerIfAbsent(Circle.class, new CircleLookup());
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeFalseWhenWorldsAreDifferent() {
        Circle first = new Circle("test", 0, 0, 3);
        Circle second = new Circle("other", 1, 1, 2);

        assertFalse(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeTrueWhenContainsCenter() {
        Circle first = new Circle("test", 0, 0, 3);
        Circle second = new Circle("test", 1, 1, 2);

        assertTrue(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeTrueWhenCenterIsOutsideCircle() {
        Circle first = new Circle("test", 0, 0, 3);
        Circle second = new Circle("test", 3, 3, 2);

        assertTrue(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeFalse() {
        Circle first = new Circle("test", 0, 0, 3);
        Circle second = new Circle("test", 100, 100, 2);

        assertFalse(Intersections.checkSpecific(first, second));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeTrue() {
        Circle circle = new Circle("test", 0, 0, 3);

        assertTrue(circle.contains(new Tile("test", 1, 1)));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeFalseWhenWorldsAreDifferent() {
        Circle circle = new Circle("test", 0, 0, 3);

        assertFalse(circle.contains(new Tile("other", 1, 1)));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeFalse() {
        Circle circle = new Circle("test", 0, 0, 3);

        assertFalse(circle.contains(new Tile("test", 3, 3)));
    }
}
