package builder.world;

import builder.GameState;
import builder.Tickable;
import builder.entities.tiles.Tile;
import builder.ui.RenderableGroup;
import engine.EngineState;
import engine.renderer.Dimensions;
import engine.renderer.Renderable;

import java.util.List;

public class BeanWorld implements RenderableGroup, Tickable, World {
    @Override
    public void tick(EngineState state, GameState game) {

    }

    @Override
    public List<Renderable> render() {
        return List.of();
    }

    @Override
    public List<Tile> tilesAtPosition(int x, int y, Dimensions dimensions) {
        return List.of();
    }

    @Override
    public List<Tile> allTiles() {
        return List.of();
    }

    @Override
    public void place(Tile tile) {

    }
}
