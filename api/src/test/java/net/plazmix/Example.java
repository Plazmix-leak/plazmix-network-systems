package net.plazmix;

import com.google.common.collect.Sets;
import net.plazmix.minecraft.Minecraft;
import net.plazmix.minecraft.game.GameSession;
import net.plazmix.minecraft.game.logic.paper.event.EventLogic;
import net.plazmix.minecraft.game.logic.paper.event.EventLogicMapper;
import net.plazmix.minecraft.game.logic.paper.event.GameEvent;
import net.plazmix.minecraft.game.mode.team.ServerTeamGame;
import net.plazmix.minecraft.game.player.GamePlayer;
import net.plazmix.minecraft.game.player.paper.DefaultSpectator;
import net.plazmix.minecraft.game.team.Team;
import net.plazmix.minecraft.game.team.TeamGameSession;
import net.plazmix.minecraft.game.team.TeamMember;
import net.plazmix.minecraft.platform.paper.PaperSpigotPlatform;
import net.plazmix.minecraft.platform.paper.event.PlayerDamageEvent;
import net.plazmix.minecraft.util.paper.time.ticker.PlayerCollectionTimeTicker;
import net.plazmix.network.Network;
import net.plazmix.util.time.ticker.TimeTicker;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.Material;
import org.bukkit.World;
import org.bukkit.block.Block;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.Event;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.EntityDamageEvent;

import java.util.Arrays;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.function.Predicate;

public class Example {


    public static final String GAME_WORLD = "GAME_WORLD";
    public static final String GAME_RADIUS = "GAME_RADIUS";

    public static final String TEAM_SPAWNPOINT = "TEAM_SPAWNPOINT";

    public static final String TEAM_BED_BLOCK_1 = "TEAM_BED_BLOCK_1";
    public static final String TEAM_BED_BLOCK_2 = "TEAM_BED_BLOCK_2";

    public static final String IS_BROKEN = "IS_BROKEN";

    public static final String BEDS_BROKEN = "BEDS_BROKEN";

    public static final String KILLS = "KILLS";
    public static final String DEATHS = "DEATHS";
    public static final String FINAL_KILLS = "FINAL_KILLS";

    public static final String IS_DEAD = "IS_DEAD";
    public static final String WINNER = "WINNER";
    public static final String DEAD_TEAMS = "DEAD_TEAMS";
    public static final String DEAD_PLAYERS = "DEAD_PLAYERS";

    public static final String PLACED_BLOCKS = "PLACED_BLOCKS";

    public static final String WAITING_TIME_TICKER = "WAITING_TIME_TICKER";

    private static void addPlayerBlockEvent(EventLogicMapper<? extends GameSession, ? extends Event> mapper, Predicate<GameEvent.PlayerBlock> predicate) {
        Map<Class<? extends Event>, EventLogic<? extends Event, GameEvent.PlayerBlock>> map = null;
        map.put(mapper.getEventClass(), EventLogic.of(mapper, predicate));
    }

    void example() {
        Minecraft minecraft = PlazmixAPI.getMinecraft();
        Network network = PlazmixAPI.getNetwork();
        network.getUserByName("_MasterCapeXD_")
                .ifPresent(user -> network.redirect(user, network.getServerByName("bws-1").get())
                        .onSuccess(() -> {
                        })
                        .onFailure(() -> {
                        }));

        ServerTeamGame bedWarsGame = minecraft.newGameBuilder(ServerTeamGame.class)
                .withName("bedwars")
                .addState(minecraft.newGameStateBuilder()
                        .withName("autosetup")
                        .withLogic(minecraft.getPlatform(PaperSpigotPlatform.class).get().getGameLogicBuilder(TeamGameSession.class)
                                .disallowPlayerAdd()
                                .cancel(GameEvent.Block.LEAVES_DECAY)
                                .cancel(GameEvent.Block.BURN)
                                .cancel(GameEvent.Block.FADE)
                                .cancel(GameEvent.Block.FORM)
                                .cancel(GameEvent.Block.IGNITE)
                                .cancel(GameEvent.Block.SPREAD)
                                .cancel(GameEvent.Block.EXPLODE)
                                .cancel(GameEvent.Block.FROM_TO)
                                .cancel(GameEvent.Block.GROW)
                                .cancel(GameEvent.Block.PHYSICS)
                                .onStart(session -> {
                                    World world = session.getCache().get(GAME_WORLD, World.class);
                                    int radius = session.getCache().getInt(GAME_RADIUS);

                                    // Scan world by radius to setup the game session
                                    Team[] teams = new Team[4];

                                    // Register teams & switch to the next game state
                                    Arrays.stream(teams)
                                            .peek(team -> {
                                                // Setup team spawnpoints & beds
                                                Location location = null;
                                                team.getCache().set(TEAM_SPAWNPOINT, location);

                                                Block block1 = null, block2 = null;
                                                team.getCache().set(TEAM_BED_BLOCK_1, block1);
                                                team.getCache().set(TEAM_BED_BLOCK_2, block2);
                                            })
                                            .forEach(session::registerTeam);
                                    session.getStateController().nextState();
                                })
                                .build())
                        .build())
                .addState(minecraft.newGameStateBuilder()
                        .withName("waiting")
                        .withLogic(minecraft.getPlatform(PaperSpigotPlatform.class).get().getGameLogicBuilder()
                                .cancel(GameEvent.Block.LEAVES_DECAY)
                                .cancel(GameEvent.Block.BURN)
                                .cancel(GameEvent.Block.FADE)
                                .cancel(GameEvent.Block.FORM)
                                .cancel(GameEvent.Block.IGNITE)
                                .cancel(GameEvent.Block.SPREAD)
                                .cancel(GameEvent.Block.FROM_TO)
                                .cancel(GameEvent.Block.GROW)

                                .cancel(GameEvent.PlayerBlock.INTERACT_LEFT)
                                .cancel(GameEvent.PlayerBlock.INTERACT_RIGHT)

                                .cancel(GameEvent.PlayerEntity.DROP)
                                .cancel(GameEvent.PlayerEntity.PICKUP)
                                .cancel(GameEvent.PlayerEntity.INTERACT)

                                .cancel(GameEvent.PlayerInventoryAction.CLICK)

                                .cancel(GameEvent.PlayerItem.CONSUME)
                                .cancel(GameEvent.PlayerItem.INTERACT_LEFT)
                                .cancel(GameEvent.PlayerItem.INTERACT_RIGHT)

                                .allowPlayerAdd()

                                .onStart(session -> {
                                    session.getCache().set(WAITING_TIME_TICKER, session.getStateController().getCurrentState()
                                            .addTimeTicker(new PlayerCollectionTimeTicker(
                                                    TimeTicker.createTimer(TimeUnit.SECONDS, 60),
                                                    (ticker, player) -> {
                                                        if (ticker.getCurrentTicks() == 0 && !ticker.isPaused()) {
                                                            session.getStateController().nextState();
                                                            ticker.setPaused(true);
                                                            session.getStateController().getCurrentState().removeTimeTicker(ticker);
                                                        }
                                                    })).getUniqueId());
                                })

                                .postPlayerAdd((gamePlayer, session) -> {
                                    PlayerCollectionTimeTicker timeTicker = session.getStateController().getCurrentState().getTimeTicker(session.getCache().get(WAITING_TIME_TICKER), PlayerCollectionTimeTicker.class);
                                    timeTicker.getPlayers().add(Bukkit.getPlayer(gamePlayer.getUniqueId()));
                                    if (timeTicker.getPlayers().size() >= 2 && timeTicker.isPaused()) {
                                        timeTicker.setPaused(false);
                                    } else
                                        timeTicker.setPaused(true);
                                })
                                .postPlayerRemove((gamePlayer, session) -> {
                                    PlayerCollectionTimeTicker timeTicker = session.getStateController().getCurrentState().getTimeTicker(session.getCache().get(WAITING_TIME_TICKER), PlayerCollectionTimeTicker.class);
                                    timeTicker.getPlayers().remove(Bukkit.getPlayer(gamePlayer.getUniqueId()));
                                    if (timeTicker.getPlayers().size() < 2) {
                                        timeTicker.setPaused(true);
                                    }
                                })

                                .build())
                        .build())
                .addState(minecraft.newGameStateBuilder()
                        .withName("ingame")
                        .withLogic(minecraft.getPlatform(PaperSpigotPlatform.class).get().getGameLogicBuilder(TeamGameSession.class)
                                .onStart(session -> {
                                    // Throw players to teams and teleport them to team spawns
                                    session.getRegisteredTeams()
                                            .forEach(team -> team.getMembers()
                                                    .forEach(member -> Bukkit.getPlayer(member.getUniqueId())
                                                            .teleport(team.getCache().get(TEAM_SPAWNPOINT, Location.class))));
                                })
                                .handleGamePlayerEventDirectly(PlayerDamageEvent.class, (session, event) -> {
                                    GamePlayer gamePlayer = session.getPlayerById(event.getPlayer().getUniqueId()).get();
                                    if (event.getCause() == EntityDamageEvent.DamageCause.ENTITY_ATTACK) {
                                        EntityDamageByEntityEvent damageByEntityEvent = (EntityDamageByEntityEvent) event.getEvent();
                                        Entity entity = damageByEntityEvent.getDamager();
                                        if (entity instanceof Projectile) {
                                            Projectile projectile = (Projectile) entity;
                                            if (projectile.getShooter() instanceof Entity) {
                                                entity = (Entity) projectile.getShooter();
                                            }
                                        }

                                        if (entity instanceof Player) {
                                            Player damager = (Player) entity;
                                            Optional<GamePlayer> gameDamagerOptional = session.getPlayerById(damager.getUniqueId());
                                            if (gameDamagerOptional.isPresent()) {
                                                GamePlayer gameDamager = gameDamagerOptional.get();

                                                event.setCancelled(session.toTeamMember(gamePlayer).getTeam().equals(session.toTeamMember(gameDamager).getTeam()));
                                                if (!event.isCancelled()) {

                                                }
                                            } else
                                                event.setCancelled(true);
                                        }
                                    } else if (event.getCause() == EntityDamageEvent.DamageCause.VOID) {
                                        event.setCancelled(true);
                                        DefaultSpectator.of(gamePlayer).setSpectating(true);
                                        session.toTeamMember(gamePlayer).getTeam().ifPresent(
                                                team -> event.getPlayer().teleport(team.getCache().get(TEAM_SPAWNPOINT, Location.class)));
                                    } else {
                                        if (event.getDamage() >= event.getPlayer().getHealth()) {
                                            // Handle death
                                            event.setCancelled(true);
                                            DefaultSpectator.of(gamePlayer).setSpectating(true);
                                        }
                                    }
                                })
                                .addPlayerBlockEvent(GameEvent.PlayerBlock.PLACE, event -> {
                                    Set<Location> placedBlockLocations = event.getGameSession().getCache().compute(PLACED_BLOCKS, Sets::newHashSet);
                                    placedBlockLocations.add(event.getBlock().getLocation());
                                    event.getGameSession().getCache().set(PLACED_BLOCKS, placedBlockLocations);
                                    return false;
                                })
                                .addPlayerBlockEvent(GameEvent.PlayerBlock.BREAK, event -> {
                                    if (event.getBlock().getType() == Material.BED) {
                                        Optional<GamePlayer> gamePlayerOptional = event.getGameSession().getPlayerById(event.getPlayer().getUniqueId());
                                        if (gamePlayerOptional.isPresent()) {
                                            // Determine which team bed is broken
                                            event.getGameSession().getRegisteredTeams()
                                                    .stream()
                                                    .filter(team ->
                                                            team.getCache().get(TEAM_BED_BLOCK_1, Location.class).equals(event.getBlock().getLocation())
                                                                    ||
                                                                    team.getCache().get(TEAM_BED_BLOCK_2, Location.class).equals(event.getBlock().getLocation()))
                                                    .findFirst()
                                                    .ifPresent(brokenBedTeam -> {
                                                        GamePlayer gamePlayer = gamePlayerOptional.get();
                                                        TeamMember teamMember = event.getGameSession().toTeamMember(gamePlayer);

                                                        // Send message about bed break

                                                        teamMember.getCache().increment(BEDS_BROKEN);
                                                        teamMember.getTeam().ifPresent(team -> team.getCache().increment(BEDS_BROKEN));
                                                        brokenBedTeam.getCache().set(IS_BROKEN, true);
                                                    });

                                            return false;
                                        }
                                        return false;
                                    } else
                                        return !event.getGameSession().getCache().getList(PLACED_BLOCKS, Location.class)
                                                .contains(event.getBlock().getLocation());
                                })
                                .build())
                        .build())
                .build();

        bedWarsGame.run(session -> {
            session.getCache().set(GAME_WORLD, Bukkit.getWorld("BedWars"));
            session.getCache().set(GAME_RADIUS, 250);
        });

        PlazmixAPI.getMinecraft().getGameRegistry().registerGame(bedWarsGame);
    }
}
