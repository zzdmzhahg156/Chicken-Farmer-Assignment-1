package builder.entities;

import builder.GameState;
import engine.EngineState;

/**
 *
 A game component that can be used when the player left-clicks while on it.
 When the player left-clicks anywhere on the screen,
 the use(EngineState, GameState) method of any Usable entities
 underneath the player will be called.
 */
public interface Usable {

    /**
     Process the player using this component by left-clicking.
     The state of the player and the player's inventory
     (provided by the game state) may be useful in determining the appropriate effects.
     In particular the Inventory.getHolding()
     method indicates the item currently held by the player.

     Parameters:
     state - The state of the engine provides information about which
     tick this interaction occurred during.
     game - The game state that can be queried or updated as needed.
     */
    void use(EngineState state,
             GameState game);
}
