package net.plazmix.minecraft;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.anyString;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class MinecraftMock {

    private final Minecraft minecraft = mock(AbstractMinecraft.class);

    public MinecraftMock() {
        when(minecraft.newGameBuilder(any())).thenCallRealMethod();
        when(minecraft.newGameStateBuilder()).thenCallRealMethod();
        when(minecraft.newTeam(anyString())).thenCallRealMethod();
    }

    public Minecraft getMinecraft() {
        return minecraft;
    }
}
