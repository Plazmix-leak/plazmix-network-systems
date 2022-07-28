package net.plazmix;

import com.google.common.base.Preconditions;
import lombok.Getter;
import net.plazmix.minecraft.Minecraft;
import net.plazmix.network.Network;

import java.util.logging.Logger;

public final class PlazmixAPI {

    @Getter
    private static PlazmixAPI instance;
    @Getter
    private static Logger logger;
    @Getter
    private static Network network;
    @Getter
    private static Minecraft minecraft;

    public static void init() {
        Preconditions.checkState(instance == null, "PlazmixAPI NETWORK SYSTEM IS ALREADY INITIALIZED!");
        instance = new PlazmixAPI();
    }
}
