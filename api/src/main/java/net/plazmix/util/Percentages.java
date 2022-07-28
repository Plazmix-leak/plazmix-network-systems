package net.plazmix.util;

import lombok.experimental.UtilityClass;

import java.util.Collection;

@UtilityClass
public class Percentages {

    public static <T> PercentageSet<T> newHashPercentageSet() {
        return new HashPercentageSet<>();
    }

    public static <T> PercentageSet<T> newHashPercentageSet(Collection<T> collection) {
        PercentageSet<T> set = new HashPercentageSet<>();
        set.addAll(collection);
        return set;
    }
}