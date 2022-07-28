package net.plazmix.game;

import com.google.common.base.Preconditions;
import lombok.Data;
import lombok.RequiredArgsConstructor;
import net.plazmix.PlazmixAPI;
import net.plazmix.minecraft.game.AbstractGameRegistry;
import net.plazmix.minecraft.game.Game;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.logic.GameState;
import net.plazmix.minecraft.game.logic.GameStateController;
import net.plazmix.minecraft.game.logic.paper.PaperGameLogic;
import net.plazmix.minecraft.game.logic.paper.event.EventLogic;
import net.plazmix.minecraft.game.logic.paper.event.GameEvent;
import net.plazmix.minecraft.game.mode.PolygonGame;
import net.plazmix.minecraft.game.mode.ServerGame;
import net.plazmix.minecraft.game.mode.WorldGame;
import net.plazmix.minecraft.game.player.MinecraftGamePlayer;
import net.plazmix.minecraft.game.session.MinecraftPolygonGameSession;
import net.plazmix.minecraft.game.session.MinecraftWorldGameSession;
import net.plazmix.minecraft.platform.paper.PaperSpigotClassMapper;
import net.plazmix.minecraft.platform.paper.PaperSpigotPlatform;
import net.plazmix.network.module.LocalizationModule;
import net.plazmix.network.user.User;
import net.plazmix.util.Result;
import org.bukkit.ChatColor;
import org.bukkit.block.Block;
import org.bukkit.entity.Player;
import org.bukkit.event.*;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.InventoryClickEvent;
import org.bukkit.event.inventory.InventoryCloseEvent;
import org.bukkit.event.inventory.InventoryCreativeEvent;
import org.bukkit.event.inventory.InventoryOpenEvent;
import org.bukkit.event.player.*;

import java.util.Locale;
import java.util.concurrent.atomic.AtomicReference;
import java.util.function.BiFunction;

@RequiredArgsConstructor
public class PaperGameRegistry extends AbstractGameRegistry implements Listener {

    private final AtomicReference<ServerGame> activeServerGame = new AtomicReference<>();
    private final LocalizationModule localization;

    @Override
    public Result<Void> registerGame(Game game) {
        Result<Void> result = super.registerGame(game);
        if (game instanceof ServerGame) {
            Preconditions.checkState(activeServerGame.get() == null, "You cannot register more than one ServerGame instance!");
            result.onSuccess(() -> activeServerGame.set((ServerGame) game));
        }
        return result;
    }

    @EventHandler(priority = EventPriority.LOW)
    private void on(PlayerLoginEvent event) {
        ServerGame mainGame = activeServerGame.get();
        if (mainGame != null) {
            // This logic is moved to addPlayer but this double check is recommended for performance and stability
            GameStateController stateController = mainGame.getStateController();
            GameState currentState = stateController.getCurrentState();
            MinecraftPaperGameLogic gameLogic = (MinecraftPaperGameLogic) currentState.getLogic();

            Locale locale = PlazmixAPI.getNetwork().getUserById(event.getPlayer().getUniqueId())
                    .map(User::getLocale)
                    .orElse(Locale.forLanguageTag(event.getPlayer().spigot().getLocale()));

            if (!gameLogic.isPlayerAddAllowed())
                event.disallow(PlayerLoginEvent.Result.KICK_OTHER, localization.getLocalizedText(locale, LocalizationModule.SystemTextKey.GAMESTATE_JOIN_DISALLOWED)
                        .getFirstLine().orElse(ChatColor.RED + "This game is not ready for joining!"));
            int playerLimit = mainGame.getCache().getOrDefault(GameSession.GAME_SESSION_PLAYER_LIMIT, () -> -1);
            if (playerLimit > 0 && playerLimit <= mainGame.getPlayers().size())
                event.disallow(PlayerLoginEvent.Result.KICK_FULL, localization.getLocalizedText(locale, LocalizationModule.SystemTextKey.GAME_IS_FULL)
                        .getFirstLine().orElse(ChatColor.RED + "This game is full!"));
        }
    }

    @EventHandler(priority = EventPriority.LOW)
    private void on(PlayerJoinEvent event) {
        ServerGame mainGame = activeServerGame.get();
        if (mainGame != null) {
            Player player = event.getPlayer();
            User user = PlazmixAPI.getNetwork().getUserById(player.getUniqueId()).get();
            MinecraftGamePlayer gamePlayer = new MinecraftGamePlayer(user);
            Result<Void> result = mainGame.addPlayer(gamePlayer);
            result.onFailure(() -> {
                if (result.getDescription().isPresent()) {
                    String description = result.getDescription().get();
                    player.sendMessage(localization.getUserText(user, description).getFirstLine()
                            .orElse(ChatColor.RED + description));
                    return;
                }
                player.sendMessage(ChatColor.RED + "You should not see this message. Something went wrong during adding you to the game! Please inform server administrators about this issue (Error Case №1).");
            });
        }
    }

    @EventHandler
    private void on(BlockBreakEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockPlaceEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockDamageEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockBurnEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockCanBuildEvent event) {
        handleBlock(event, null);
    }

    @EventHandler
    private void on(BlockDispenseEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockExpEvent event) {
        handleBlock(event, null);
    }

    @EventHandler
    private void on(BlockExplodeEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockFadeEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockFormEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockFromToEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockGrowEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockIgniteEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockPhysicsEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockPistonExtendEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockPistonRetractEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(BlockRedstoneEvent event) {
        handleBlock(event, null);
    }

    @EventHandler
    private void on(BlockSpreadEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(LeavesDecayEvent event) {
        handleBlock(event, event);
    }

    @EventHandler
    private void on(PlayerInteractEvent event) {
        handlePlayer(event, event);
        if (event.getClickedBlock() != null)
            handleBlock(event.getClickedBlock(), event, event);
    }

    @EventHandler
    private void on(InventoryOpenEvent event) {
        if (event.getPlayer() instanceof Player)
            handlePlayer((Player) event.getPlayer(), event, event);
    }

    @EventHandler
    private void on(InventoryCloseEvent event) {
        if (event.getPlayer() instanceof Player)
            handlePlayer((Player) event.getPlayer(), event, null);
    }

    @EventHandler
    private void on(InventoryClickEvent event) {
        if (event.getWhoClicked() instanceof Player)
            handlePlayer((Player) event.getWhoClicked(), event, event);
    }

    @EventHandler
    private void on(InventoryCreativeEvent event) {
        if (event.getWhoClicked() instanceof Player)
            handlePlayer((Player) event.getWhoClicked(), event, event);
    }

    @EventHandler
    private void on(PlayerItemConsumeEvent event) {
        handlePlayer(event, event);
    }

    @EventHandler
    private void on(PlayerItemBreakEvent event) {
        handlePlayer(event, null);
    }

    @EventHandler
    private void on(PlayerItemDamageEvent event) {
        handlePlayer(event, event);
    }

    @EventHandler
    private void on(PlayerItemHeldEvent event) {
        handlePlayer(event, event);
    }

    @EventHandler
    private void on(PlayerTeleportEvent event) {
        handlePlayer(event, event);
    }

    @EventHandler
    private void on(PlayerMoveEvent event) {
        handlePlayer(event, event);
    }

    @EventHandler
    private void on(PlayerChangedWorldEvent event) {
        handlePlayer(event, null);
    }

    @EventHandler
    private void on(PlayerDropItemEvent event) {
        handlePlayer(event, event);
    }

    @EventHandler
    private void on(PlayerPickupItemEvent event) {
        handlePlayer(event, event);
    }

    @EventHandler
    private void on(PlayerInteractAtEntityEvent event) {
        handlePlayer(event, event);
    }

    private void handleBlock(BlockEvent event, Cancellable cancellable) {
        handleBlock(event.getBlock(), event, cancellable);
    }

    private void handleBlock(Block block, Event event, Cancellable cancellable) {
        ServerGame mainGame = activeServerGame.get();
        if (mainGame != null) {
            handleGameEvent(mainGame, event, cancellable);
        }
        registeredGames.values().forEach(game -> {
            if (game.equals(mainGame))
                return;

            if (game instanceof WorldGame) {
                WorldGame<MinecraftWorldGameSession> worldGame = (WorldGame<MinecraftWorldGameSession>) game;
                worldGame.getGames().forEach(session -> {
                    if (!session.getWorld().getName().equals(block.getWorld().getName()))
                        return;

                    handleGameEvent(session, event, cancellable);
                });
            } else if (game instanceof PolygonGame) {
                PolygonGame<MinecraftPolygonGameSession> polygonGame = (PolygonGame<MinecraftPolygonGameSession>) game;
                PaperSpigotClassMapper classMapper = PlazmixAPI.getMinecraft().getPlatform(PaperSpigotPlatform.class).get().getClassMapper();
                polygonGame.getGames().forEach(session -> {
                    if (!session.getPolygon().contains(classMapper.toPoint(block.getLocation())))
                        return;

                    handleGameEvent(session, event, cancellable);
                });
            }
        });
    }

    private void handlePlayer(PlayerEvent event, Cancellable cancellable) {
        handlePlayer(event.getPlayer(), event, cancellable);
    }

    private void handlePlayer(Player player, Event event, Cancellable cancellable) {
        ServerGame mainGame = activeServerGame.get();
        if (mainGame != null) {
            handleGameEvent(mainGame, event, cancellable);
        }
        registeredGames.values().forEach(game -> {
            if (game.equals(mainGame))
                return;

            if (game instanceof WorldGame) {
                WorldGame<MinecraftWorldGameSession> worldGame = (WorldGame<MinecraftWorldGameSession>) game;
                worldGame.getGames().forEach(session -> session.getPlayerById(player.getUniqueId())
                        .ifPresent(gamePlayer -> handleGameEvent(session, event, cancellable)));
            } else if (game instanceof PolygonGame) {
                PolygonGame<MinecraftPolygonGameSession> polygonGame = (PolygonGame<MinecraftPolygonGameSession>) game;
                polygonGame.getGames().forEach(session -> session.getPlayerById(player.getUniqueId())
                        .ifPresent(gamePlayer -> handleGameEvent(session, event, cancellable)));
            }
        });
    }

    private <T extends Event> void handleGameEvent(GameSession session, T event, Cancellable cancellable) {
        PaperGameLogic gameLogic = (PaperGameLogic) session.getStateController().getCurrentState().getLogic();
        EventLogic<T, GameEvent> eventLogic = gameLogic.getBlockLogic(event.getClass());
        BiFunction<GameSession, T, GameEvent> gameEventMapper = (BiFunction<GameSession, T, GameEvent>) eventLogic.getMapper().getMapper();
        GameEvent gameEvent = gameEventMapper.apply(session, event);
        if (gameEvent == null)
            return;

        if (cancellable != null) {
            cancellable.setCancelled(gameLogic.isCancelled(event.getClass()));
            cancellable.setCancelled(eventLogic.getPredicate().test(gameEvent));
        }
    }
}
