package builder.world;

import builder.entities.tiles.Tile;
import engine.renderer.Dimensions;

import java.util.List;

public interface World {
    List<Tile> tilesAtPosition(int x, int y, Dimensions dimensions);
    List<Tile> allTiles();
    void place(Tile tile);

}
