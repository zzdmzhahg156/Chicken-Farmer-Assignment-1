package builder;

import builder.inventory.Inventory;

/**
 * An interface to the game state information, including world, player, and inventory data.
 *
 * @stage1
 * @hint The state will eventually need to include the World and Player. In stage 1, you will only
 *     need it to include the Player and in stage 2, you only need World.
 */
public interface GameState {
    /**
     * Returns the current state of the inventory.
     *
     * <p>The returned inventory is mutable, that is calling mutator methods such as {@link
     * Inventory#addCoins(int)} will modify the inventory.
     *
     * @return The inventory of the player.
     */
    Inventory getInventory();
}
