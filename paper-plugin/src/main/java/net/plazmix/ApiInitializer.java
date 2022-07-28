package net.plazmix;

import net.plazmix.exception.ApiInitializationException;

import java.lang.reflect.Field;

public class ApiInitializer {

    public ApiInitializer() {
        PlazmixAPI.init();
    }

    public void initialize(String field, Object object) {
        Field f = null;
        try {
            f = PlazmixAPI.class.getDeclaredField(field);
            f.setAccessible(true);
            f.set(PlazmixAPI.getInstance(), object);
        } catch (Exception e) {
            if (f != null)
                throw new ApiInitializationException(e, f.getName());
            else
                throw new ApiInitializationException(e);
        }
    }
}
