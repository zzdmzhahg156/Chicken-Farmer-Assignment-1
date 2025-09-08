package builder.player;

import builder.GameState;
import builder.Tickable;
import builder.ui.RenderableGroup;
import engine.EngineState;
import engine.game.Direction;
import engine.input.KeyState;
import engine.renderer.Renderable;

import java.util.List;

public class PlayerManager implements Tickable, RenderableGroup {
    private final ChickenFarmer chickenFarmer;
    public PlayerManager(int x, int y){
        this.chickenFarmer = new ChickenFarmer(x, y);
    }

    @Override
    public void tick(EngineState state, GameState game){
        KeyState keys = state.getKeys();
        this.chickenFarmer.tick(state);

        if (keys.isDown('w')) {
            chickenFarmer.move(Direction.NORTH, 1);
        } else if (keys.isDown('s')) {
            chickenFarmer.move(Direction.SOUTH, 1);
        } else if (keys.isDown('a')) {
            chickenFarmer.move(Direction.WEST, 1);
        } else if (keys.isDown('d')) {
            chickenFarmer.move(Direction.EAST, 1);
        } else {
            chickenFarmer.move(Direction.SOUTH, 0);
        }

    }

    public Player getPlayer(){
        return chickenFarmer;
    }

    @Override
    public List<Renderable> render() {
        return List.of(chickenFarmer);
    }
}
