package net.plazmix.testplugin;

import com.grinderwolf.swm.api.world.properties.SlimePropertyMap;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.WorldGameSession;
import net.plazmix.minecraft.game.logic.paper.event.GameEvent;
import net.plazmix.minecraft.game.mode.WorldGame;
import net.plazmix.minecraft.game.util.paper.GameWorldGenerator;
import net.plazmix.minecraft.platform.paper.PaperSpigotPlatform;
import net.plazmix.minecraft.platform.paper.event.PlayerDamageEvent;
import net.plazmix.network.module.GameMapModule;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.player.AsyncPlayerChatEvent;
import org.bukkit.inventory.ItemStack;
import org.bukkit.plugin.java.JavaPlugin;

import static net.plazmix.PlazmixAPI.getMinecraft;
import static net.plazmix.PlazmixAPI.getNetwork;

public class TestGamePlugin extends JavaPlugin implements Listener {

    private final PaperSpigotPlatform platform = getMinecraft().getPlatform(PaperSpigotPlatform.class).get();
    private final GameMapModule gameMapModule = getNetwork().getModule(GameMapModule.class).get();
    private WorldGame<GameSession> worldGame;

    @Override
    @SuppressWarnings("unchecked")
    public void onEnable() {
        this.worldGame = getMinecraft().newGameBuilder(WorldGame.class)
                .withName("TestGame")
                .addState(getMinecraft().newGameStateBuilder()
                        .withName("game")
                        .withLogic(platform.getGameLogicBuilder(WorldGameSession.class)
                                .onStart(session -> Bukkit.broadcastMessage("New TestGame session is starting: " + session.getSessionId()))
                                .onShutdown(session -> {
                                    Bukkit.broadcastMessage("TestGame session is finished!");
                                })
                                .allowPlayerAdd()
                                .postPlayerAdd((gamePlayer, session) -> {
                                    Player player = Bukkit.getPlayer(gamePlayer.getUniqueId());
                                    player.teleport(platform.getClassMapper().fromGameWorld(session.getWorld()).getSpawnLocation());
                                    player.getInventory().addItem(new ItemStack(Material.TNT));
                                    player.getInventory().addItem(new ItemStack(Material.FLINT_AND_STEEL));
                                })
                                .postPlayerRemove((gamePlayer, session) -> {
                                    Player player = Bukkit.getPlayer(gamePlayer.getUniqueId());
                                    player.getInventory().clear();
                                })
                                .cancel(GameEvent.Block.EXPLODE)
                                .cancel(GameEvent.PlayerBlock.PLACE)
                                .handleGamePlayerEventDirectly(PlayerDamageEvent.class, (session, event) -> {
                                    event.getPlayer().sendMessage("Hit!");
                                })
                                .addPlayerBlockEvent(GameEvent.PlayerBlock.BREAK, event -> {
                                    if (event.getBlock().getType() == Material.STONE)
                                        event.getPlayer().getInventory().addItem(new ItemStack(Material.COBBLESTONE));
                                    else if (event.getBlock().getType() == Material.WOOD)
                                        event.getPlayer().getInventory().addItem(new ItemStack(Material.WOOD));
                                    else if (event.getBlock().getType() == Material.IRON_BLOCK) {
                                        event.getPlayer().sendMessage("You won!");
                                        event.getGameSession().getStateController().nextState();
                                    }
                                    return true;
                                })
                                .build())
                        .build())
                .addState(getMinecraft().newGameStateBuilder()
                        .withName("end")
                        .withLogic(platform.getGameLogicBuilder(WorldGameSession.class)
                                .cancelAll()
                                .onStart(session -> {
                                    session.getPlayers().forEach(player -> {
                                        Bukkit.getPlayer(player.getUniqueId()).kickPlayer("Ending game!");
                                    });
                                    session.setActive(false);
                                    GameWorldGenerator.unloadWorld(session.getWorld());
                                })
                                .build())
                        .build())
                .build();
        this.getServer().getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    private void on(AsyncPlayerChatEvent event) {
        String message = event.getMessage().toLowerCase();
        if (message.startsWith("хочу играть ")) {
            String next = message.replace("хочу играть ", "");
            String worldName = null;
            if (next.startsWith("на карте ")) {
                worldName = next.replace("на карте ", "").split(" ")[0];
            }
            SlimePropertyMap propertyMap = gameMapModule.slime().getPropertyMap(worldName);
            String mapName = "Тестовая игра";
            GameWorldGenerator.generate(this, propertyMap, worldName);
            GameWorldGenerator.runLater(this, worldGame, worldName, mapName, 10L,
                    session -> session.addPlayer(getMinecraft().getGameRegistry()
                            .asGamePlayer(getNetwork().getUserById(event.getPlayer().getUniqueId())
                                    .get())));
        }
    }
}
