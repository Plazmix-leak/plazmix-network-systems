package net.plazmix.minecraft.game.logic.paper.event;

import lombok.Getter;
import net.plazmix.minecraft.game.GameSession;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.block.*;
import org.bukkit.event.inventory.*;
import org.bukkit.event.player.*;
import org.bukkit.inventory.Inventory;
import org.bukkit.inventory.InventoryView;
import org.bukkit.inventory.ItemStack;

@Getter
public class GameEvent<T extends GameSession> {

    private final T gameSession;

    protected GameEvent(T gameSession) {
        this.gameSession = gameSession;
    }

    @Getter
    public static class PlayerBlock<T extends GameSession> extends GameEvent<T> {

        public static final EventLogicMapper<GameSession, BlockBreakEvent> BREAK = EventLogicMapper.create(BlockBreakEvent.class, (gameSession, event) -> new PlayerBlock(gameSession, event.getPlayer(), event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockPlaceEvent> PLACE = EventLogicMapper.create(BlockPlaceEvent.class, (gameSession, event) -> new PlayerBlock(gameSession, event.getPlayer(), event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockDamageEvent> DAMAGE = EventLogicMapper.create(BlockDamageEvent.class, (gameSession, event) -> new PlayerBlock(gameSession, event.getPlayer(), event.getBlock()));
        public static final EventLogicMapper<GameSession, PlayerInteractEvent> INTERACT_LEFT = EventLogicMapper.create(PlayerInteractEvent.class, (gameSession, event) -> {
            if (event.getAction() == Action.LEFT_CLICK_BLOCK)
                return new PlayerBlock(gameSession, event.getPlayer(), event.getClickedBlock());
            return null;
        });
        public static final EventLogicMapper<GameSession, PlayerInteractEvent> INTERACT_RIGHT = EventLogicMapper.create(PlayerInteractEvent.class, (gameSession, event) -> {
            if (event.getAction() == Action.RIGHT_CLICK_BLOCK)
                return new PlayerBlock(gameSession, event.getPlayer(), event.getClickedBlock());
            return null;
        });

        private final Player player;
        private final org.bukkit.block.Block block;

        public PlayerBlock(T gameSession, Player player, org.bukkit.block.Block block) {
            super(gameSession);
            this.player = player;
            this.block = block;
        }
    }

    @Getter
    public static class PlayerInventory<T extends GameSession> extends GameEvent<T> {

        public static final EventLogicMapper<GameSession, InventoryCloseEvent> CLOSE = EventLogicMapper.create(InventoryCloseEvent.class, (gameSession, event) -> new PlayerInventory(gameSession, (Player) event.getPlayer(), event.getInventory(), event.getView()));
        public static final EventLogicMapper<GameSession, InventoryOpenEvent> OPEN = EventLogicMapper.create(InventoryOpenEvent.class, (gameSession, event) -> new PlayerInventory(gameSession, (Player) event.getPlayer(), event.getInventory(), event.getView()));

        private final Player player;
        private final Inventory inventory;
        private final InventoryView view;

        public PlayerInventory(T gameSession, Player player, Inventory inventory, InventoryView view) {
            super(gameSession);
            this.player = player;
            this.inventory = inventory;
            this.view = view;
        }
    }

    @Getter
    public static class PlayerInventoryAction<T extends GameSession> extends GameEvent<T> {

        public static final EventLogicMapper<GameSession, InventoryClickEvent> CLICK = EventLogicMapper.create(InventoryClickEvent.class, (gameSession, event) -> new PlayerInventoryAction(gameSession, (Player) event.getWhoClicked(), event.getInventory(), event.getView(), event.getClick(), event.getSlotType(), event.getAction(), event.getSlot(), event.getRawSlot(), event.getCurrentItem(), event.getCursor(), event.getHotbarButton()));
        public static final EventLogicMapper<GameSession, InventoryCreativeEvent> CREATIVE = EventLogicMapper.create(InventoryCreativeEvent.class, (gameSession, event) -> new PlayerInventoryAction(gameSession, (Player) event.getWhoClicked(), event.getInventory(), event.getView(), event.getClick(), event.getSlotType(), event.getAction(), event.getSlot(), event.getRawSlot(), event.getCurrentItem(), event.getCursor(), event.getHotbarButton()));

        private final Player player;
        private final Inventory inventory;
        private final InventoryView view;

        private final ClickType click;
        private final InventoryType.SlotType slotType;
        private final InventoryAction action;
        private final int slot;
        private final int rawSlot;
        private final ItemStack currentItem;
        private final ItemStack cursorItem;
        private final int hotbarKey;

        public PlayerInventoryAction(T gameSession, Player player, Inventory inventory, InventoryView view, ClickType click, InventoryType.SlotType slotType, InventoryAction action, int slot, int rawSlot, ItemStack currentItem, ItemStack cursorItem, int hotbarKey) {
            super(gameSession);
            this.player = player;
            this.inventory = inventory;
            this.view = view;
            this.click = click;
            this.slotType = slotType;
            this.action = action;
            this.slot = slot;
            this.rawSlot = rawSlot;
            this.currentItem = currentItem;
            this.cursorItem = cursorItem;
            this.hotbarKey = hotbarKey;
        }
    }

    @Getter
    public static class PlayerItem<T extends GameSession> extends GameEvent<T> {

        public static final EventLogicMapper<GameSession, PlayerItemConsumeEvent> CONSUME = EventLogicMapper.create(PlayerItemConsumeEvent.class, (gameSession, event) -> new PlayerItem(gameSession, event.getPlayer(), event.getItem()));
        public static final EventLogicMapper<GameSession, PlayerItemBreakEvent> BREAK = EventLogicMapper.create(PlayerItemBreakEvent.class, (gameSession, event) -> new PlayerItem(gameSession, event.getPlayer(), event.getBrokenItem()));
        public static final EventLogicMapper<GameSession, PlayerItemDamageEvent> DAMAGE = EventLogicMapper.create(PlayerItemDamageEvent.class, (gameSession, event) -> new PlayerItem(gameSession, event.getPlayer(), event.getItem()));
        public static final EventLogicMapper<GameSession, PlayerItemHeldEvent> HELD = EventLogicMapper.create(PlayerItemHeldEvent.class, (gameSession, event) -> new PlayerItem(gameSession, event.getPlayer(), event.getPlayer().getItemInHand()));
        public static final EventLogicMapper<GameSession, PlayerInteractEvent> INTERACT_LEFT = EventLogicMapper.create(PlayerInteractEvent.class, (gameSession, event) -> {
            if (event.getAction() == Action.LEFT_CLICK_AIR)
                return new PlayerItem(gameSession, event.getPlayer(), event.getItem());
            return null;
        });
        public static final EventLogicMapper<GameSession, PlayerInteractEvent> INTERACT_RIGHT = EventLogicMapper.create(PlayerInteractEvent.class, (gameSession, event) -> {
            if (event.getAction() == Action.RIGHT_CLICK_AIR)
                return new PlayerItem(gameSession, event.getPlayer(), event.getItem());
            return null;
        });

        private final Player player;
        private final ItemStack item;

        public PlayerItem(T gameSession, Player player, ItemStack item) {
            super(gameSession);
            this.player = player;
            this.item = item;
        }
    }

    @Getter
    public static class PlayerLocation<T extends GameSession> extends GameEvent<T> {

        public static final EventLogicMapper<GameSession, PlayerTeleportEvent> TELEPORT = EventLogicMapper.create(PlayerTeleportEvent.class, (gameSession, event) -> new PlayerLocation(gameSession, event.getPlayer(), event.getFrom().getWorld(), event.getTo().getWorld(), event.getFrom(), event.getTo()));
        public static final EventLogicMapper<GameSession, PlayerMoveEvent> MOVE = EventLogicMapper.create(PlayerMoveEvent.class, (gameSession, event) -> new PlayerLocation(gameSession, event.getPlayer(), event.getFrom().getWorld(), event.getTo().getWorld(), event.getFrom(), event.getTo()));
        public static final EventLogicMapper<GameSession, PlayerChangedWorldEvent> WORLD_CHANGE = EventLogicMapper.create(PlayerChangedWorldEvent.class, (gameSession, event) -> new PlayerLocation(gameSession, event.getPlayer(), event.getFrom(), event.getPlayer().getWorld(), event.getFrom().getSpawnLocation(), event.getPlayer().getLocation()));

        private final Player player;
        private final World fromWorld, toWorld;
        private final Location fromLocation, toLocation;

        public PlayerLocation(T gameSession, Player player, World fromWorld, World toWorld, Location fromLocation, Location toLocation) {
            super(gameSession);
            this.player = player;
            this.fromWorld = fromWorld;
            this.toWorld = toWorld;
            this.fromLocation = fromLocation;
            this.toLocation = toLocation;
        }
    }

    @Getter
    public static class PlayerEntity<T extends GameSession> extends GameEvent<T> {

        public static final EventLogicMapper<GameSession, PlayerDropItemEvent> DROP = EventLogicMapper.create(PlayerDropItemEvent.class, (gameSession, event) -> new PlayerEntity(gameSession, event.getPlayer(), event.getItemDrop()));
        public static final EventLogicMapper<GameSession, PlayerPickupItemEvent> PICKUP = EventLogicMapper.create(PlayerPickupItemEvent.class, (gameSession, event) -> new PlayerEntity(gameSession, event.getPlayer(), event.getItem()));
        public static final EventLogicMapper<GameSession, PlayerInteractAtEntityEvent> INTERACT = EventLogicMapper.create(PlayerInteractAtEntityEvent.class, (gameSession, event) -> new PlayerEntity(gameSession, event.getPlayer(), event.getRightClicked()));

        private final Player player;
        private final Entity entity;

        public PlayerEntity(T gameSession, Player player, Entity entity) {
            super(gameSession);
            this.player = player;
            this.entity = entity;
        }
    }

    @Getter
    public static class Block<T extends GameSession> extends GameEvent<T> {

        public static final EventLogicMapper<GameSession, BlockBurnEvent> BURN = EventLogicMapper.create(BlockBurnEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockCanBuildEvent> CAN_BUILD = EventLogicMapper.create(BlockCanBuildEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockDispenseEvent> DISPENSE = EventLogicMapper.create(BlockDispenseEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockExpEvent> EXP = EventLogicMapper.create(BlockExpEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockExplodeEvent> EXPLODE = EventLogicMapper.create(BlockExplodeEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockFadeEvent> FADE = EventLogicMapper.create(BlockFadeEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockFormEvent> FORM = EventLogicMapper.create(BlockFormEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockFromToEvent> FROM_TO = EventLogicMapper.create(BlockFromToEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockGrowEvent> GROW = EventLogicMapper.create(BlockGrowEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockIgniteEvent> IGNITE = EventLogicMapper.create(BlockIgniteEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockPhysicsEvent> PHYSICS = EventLogicMapper.create(BlockPhysicsEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockPistonExtendEvent> PISTON_EXTEND = EventLogicMapper.create(BlockPistonExtendEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockPistonRetractEvent> PISTON_RETRACT = EventLogicMapper.create(BlockPistonRetractEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockRedstoneEvent> REDSTONE = EventLogicMapper.create(BlockRedstoneEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, BlockSpreadEvent> SPREAD = EventLogicMapper.create(BlockSpreadEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));
        public static final EventLogicMapper<GameSession, LeavesDecayEvent> LEAVES_DECAY = EventLogicMapper.create(LeavesDecayEvent.class, (gameSession, event) -> new Block(gameSession, event.getBlock()));

        private final org.bukkit.block.Block block;

        public Block(T gameSession, org.bukkit.block.Block block) {
            super(gameSession);
            this.block = block;
        }
    }
}
