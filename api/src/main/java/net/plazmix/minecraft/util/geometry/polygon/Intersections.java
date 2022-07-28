package net.plazmix.minecraft.util.geometry.polygon;

import com.google.common.collect.Maps;
import com.google.common.collect.Sets;
import lombok.experimental.UtilityClass;

import java.util.Collection;
import java.util.Collections;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@UtilityClass
public class Intersections {

    private static final Map<Class<? extends Polygon>, IntersectionLookup>
            REGISTERED_POLYGON_INTERSECTION_LOGIC = Maps.newHashMap();

    private static final Collection<Polygon> REGISTERED_POLYGONS = Sets.newConcurrentHashSet();

    public static void registerIfAbsent(Class<? extends Polygon> clazz, IntersectionLookup lookup) {
        REGISTERED_POLYGON_INTERSECTION_LOGIC.putIfAbsent(clazz, lookup);
    }

    public static void registerPolygon(Polygon polygon) {
        REGISTERED_POLYGONS.add(polygon);
    }

    public static Collection<Polygon> getRegisteredPolygons() {
        return Collections.unmodifiableCollection(REGISTERED_POLYGONS);
    }

    public static <T extends Polygon> Optional<IntersectionLookup<T>> getLookup(T polygon) {
        return Optional.ofNullable(REGISTERED_POLYGON_INTERSECTION_LOGIC.get(polygon.getClass()));
    }

    public static <T extends Polygon> Collection<Polygon> scanAllIntersectedWith(T polygon) {
        Collection<Polygon> intersected = Sets.newHashSet();
        Collection<Polygon> registered = Intersections.getRegisteredPolygons().stream()
                .filter(rg -> rg.getWorld().equals(polygon.getWorld()) && !rg.getUniqueId().equals(polygon.getUniqueId()))
                .collect(Collectors.toSet());
        for (Polygon another : registered)
            checkSpecific(polygon, another);
        return intersected;
    }

    public static boolean checkAll(Polygon polygon) {
        return getLookup(polygon)
                .map(lookup -> !scanAllIntersectedWith(polygon).isEmpty())
                .orElse(false);
    }

    public static boolean checkSpecific(Polygon polygon, Polygon another) {
        return getLookup(polygon)
                .map(lookup -> lookup.isIntersected(polygon, another) || lookup.isIntersected(another, polygon))
                .orElse(false);
    }
}
