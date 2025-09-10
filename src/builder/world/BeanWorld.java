package builder.world;

import builder.GameState;
import builder.Tickable;
import builder.entities.tiles.Tile;
import builder.ui.RenderableGroup;
import engine.EngineState;
import engine.renderer.Dimensions;
import engine.renderer.Renderable;

import java.util.ArrayList;
import java.util.List;

public class BeanWorld implements RenderableGroup, Tickable, World {
    private final List<Tile> tiles;

    public BeanWorld() {
        this.tiles = new ArrayList<Tile>();
    }

    @Override
    public List<Tile> tilesAtPosition(int x, int y, Dimensions dimensions) {
        List <Tile> result = new ArrayList<>();
        int tile_x = dimensions.pixelToTile(x);
        int tile_y = dimensions.pixelToTile(y);

        for (Tile tile : tiles) {
            int tileGridX = dimensions.pixelToTile(tile.getX());
            int tileGridY = dimensions.pixelToTile(tile.getY());
            if (tileGridX == tile_x && tileGridY == tile_y) {
                result.add(tile);
            }
        }
        return result;

    }

    @Override
    public void tick(EngineState state, GameState game) {
        for (Tile tile: tiles) {
            tile.tick(state);
        }
    }

    @Override
    public List<Renderable> render() {
        List<Renderable> renderables = new ArrayList<>();
        for (Tile tile : tiles) {
            renderables.addAll(tile.render());
        }
        return renderables;
    }

    @Override
    public List<Tile> allTiles() {
        return new ArrayList<>(tiles);
    }

    @Override
    public void place(Tile tile) {
        if (tile == null) {
            return;
        }
        tiles.add(tile);
    }
}
