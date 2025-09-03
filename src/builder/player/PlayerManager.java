package builder.player;

import builder.GameState;
import builder.Tickable;
import builder.ui.RenderableGroup;
import engine.EngineState;
import engine.renderer.Renderable;

import java.util.List;

public class PlayerManager implements Tickable, RenderableGroup {
    public PlayerManager(int x, int y){}

    public void tick(EngineState state, GameState game){

    }

    public Player getPlayer(){

        return null;
    }

    @Override
    public List<Renderable> render() {
        return List.of();
    }
}
