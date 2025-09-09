package builder.entities;

import builder.GameState;
import engine.EngineState;

public interface Interactable {
    void interact(EngineState state,
                  GameState game);
}
