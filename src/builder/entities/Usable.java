package builder.entities;

import builder.GameState;
import engine.EngineState;

public interface Usable {
    void use(EngineState state,
             GameState game);
}
