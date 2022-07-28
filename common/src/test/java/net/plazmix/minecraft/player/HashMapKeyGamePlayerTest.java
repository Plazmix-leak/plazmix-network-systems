package net.plazmix.minecraft.player;

import com.google.common.collect.Maps;
import net.plazmix.minecraft.game.player.GamePlayer;
import org.junit.jupiter.api.Test;

import java.nio.charset.StandardCharsets;
import java.util.Map;
import java.util.UUID;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;

public class HashMapKeyGamePlayerTest {

    private final Map<GamePlayer, Boolean> map = Maps.newHashMap();
    private final UUID uuid = UUID.nameUUIDFromBytes("test".getBytes(StandardCharsets.UTF_8));

    @Test
    void put_shouldPut_contains() {
        GamePlayer original = mock(GamePlayer.class);
        when(original.getName()).thenReturn("test");
        when(original.getUniqueId()).thenReturn(uuid);

        GamePlayer player1 = new KeyGamePlayer(original);
        GamePlayer player2 = new KeyGamePlayer(original);

        map.put(player1, true);

        assertEquals(player1, player2);
        assertTrue(map.getOrDefault(player2, false));
    }
}
