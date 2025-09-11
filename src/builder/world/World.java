package builder.world;

import builder.entities.tiles.Tile;
import engine.renderer.Dimensions;

import java.util.List;

/**
 *
 An interface to query and modify the state of the world.
 A world consists of a grid of tiles.
 The tiles at a pixel x and y position can be queried via tilesAtPosition(int, int, Dimensions).
 New tiles can be placed on the world (at the position contained within the tile instance)
 using place(Tile).
 */
public interface World {
    /**
     Return all tiles at the grid position of the x and y position.
     A tile is at a position if it's x and y position occupy
     the same tile index as the given x and y position (according to Dimensions.pixelToTile(int)).

     The order of the tiles is unspecified, any ordering is suitable.

     Parameters:
     x - The x-axis (horizontal) coordinate in pixels.
     y - The y-axis (vertical) coordinate in pixels.
     dimensions - The dimensions of the world.
     Returns:
     A list of all tiles occupying the given x, y position.
     */
    List<Tile> tilesAtPosition(int x, int y, Dimensions dimensions);

    /**
     * Return all tiles in the world.
     * Modifying the returned list must not modify the state of the world
     * (although modifying the tiles within the list will).
     * The order of the tiles is unspecified, any ordering is suitable.
     * Returns:
     * All tiles in the world.
     */
    List<Tile> allTiles();

    /**
     *
     Place a new tile into the world.
     The tile will be placed at the position specified by
     its Entity.getX() and Entity.getY() position.

     Parameters:
     tile - The tile to place into the world.
     Ensures:
     Any calls to tilesAtPosition(int, int, engine.renderer.Dimensions)
     will reflect the existence of this new tile in the world.
     */
    void place(Tile tile);

}
