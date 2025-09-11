package builder.entities;

import builder.GameState;
import engine.EngineState;

/**
 * A game component that has an interaction behaviour with the player.
 * The interaction event (calling of interact(engine.EngineState, builder.GameState))
 * is triggered when the player occupies the same grid square as this component.
 * The event continues to be fired each tick for as long as the player occupies the space.
 * Note that for left-click behaviour, Usable should be used instead.
 */
public interface Interactable {
    /**
     Handles interaction behaviour with the player.
     Parameters:
     state - The state of the engine, including the mouse, keyboard information and dimension.
     Useful for processing keyboard presses or mouse movement.
     game - The state of the game, including the player and world.
     Can be used to query or update the game state.
     */
    void interact(EngineState state,
                  GameState game);
}
