package net.plazmix.minecraft.lookup;

import net.plazmix.minecraft.util.geometry.Point;
import net.plazmix.minecraft.util.geometry.polygon.Circle;
import net.plazmix.minecraft.util.geometry.polygon.Cylinder;
import net.plazmix.minecraft.util.geometry.polygon.Intersections;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.TestInstance;

import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

@TestInstance(TestInstance.Lifecycle.PER_CLASS)
public class CylinderLookupTest {

    @BeforeAll
    void setup() {
        Intersections.registerIfAbsent(Cylinder.class, new CylinderLookup());
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeFalseWhenWorldsAreDifferent() {
        Circle f = new Circle("test", 0, 0, 3);
        Circle s = new Circle("other", 1, 1, 2);

        Cylinder first = new Cylinder(f, 0, 10);
        Cylinder second = new Cylinder(s, 5, 15);

        assertFalse(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeTrueWhenContainsCenter() {
        Circle f = new Circle("test", 0, 0, 3);
        Circle s = new Circle("test", 1, 1, 2);

        Cylinder first = new Cylinder(f, 0, 10);
        Cylinder second = new Cylinder(s, 5, 15);

        assertTrue(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeFalseWhenNoYIntersection() {
        Circle f = new Circle("test", 0, 0, 3);
        Circle s = new Circle("test", 1, 1, 2);

        Cylinder first = new Cylinder(f, 0, 10);
        Cylinder second = new Cylinder(s, 11, 20);

        assertFalse(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeTrueWhenCenterIsOutsideCircle() {
        Circle f = new Circle("test", 0, 0, 3);
        Circle s = new Circle("test", 3, 3, 2);

        Cylinder first = new Cylinder(f, 0, 10);
        Cylinder second = new Cylinder(s, 5, 15);

        assertTrue(Intersections.checkSpecific(first, second));
    }

    @Test
    void isIntersected_shouldDetectCorrectly_shouldBeFalse() {
        Circle f = new Circle("test", 0, 0, 3);
        Circle s = new Circle("test", 100, 100, 2);

        Cylinder first = new Cylinder(f, 0, 10);
        Cylinder second = new Cylinder(s, 5, 15);

        assertFalse(Intersections.checkSpecific(first, second));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeTrue() {
        Circle c = new Circle("test", 0, 0, 3);
        Cylinder cylinder = new Cylinder(c, 0, 10);

        assertTrue(cylinder.contains(new Point("test", 1, 5, 1)));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeFalseWhenWorldsAreDifferent() {
        Circle c = new Circle("test", 0, 0, 3);
        Cylinder cylinder = new Cylinder(c, 0, 10);

        assertFalse(cylinder.contains(new Point("other", 1, 5, 1)));
    }

    @Test
    void contains_shouldDetectCorrectly_shouldBeFalse() {
        Circle c = new Circle("test", 0, 0, 3);
        Cylinder cylinder = new Cylinder(c, 0, 10);

        assertFalse(cylinder.contains(new Point("test", 2, 11, 2)));
    }
}
