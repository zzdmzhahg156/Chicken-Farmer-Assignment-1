package builder;

import builder.inventory.Inventory;
import builder.player.Player;
import builder.world.World;

/**
 * An implementation of the game state for the JavaBean game. Stores the world, player, and inventory.
 */
public class JavaBeanGameState implements GameState {
    private final World world;
    private final Inventory inventory;
    private final Player player;

    /**
     * Construct a new instance storing the given world, player, and inventory.
     * Parameters:
     * world - The world of the game.
     * player - The player of the game.
     * inventory - The inventory of the player.
     */
    public JavaBeanGameState(World world, Player player, Inventory inventory) {
        this.world = world;
        this.player = player;
        this.inventory = inventory;
    }

    @Override
    public Inventory getInventory() {
        return inventory;
    }

    @Override
    public Player getPlayer() {
        return player;
    }

    @Override
    public World getWorld() {
        return world;
    }
}
