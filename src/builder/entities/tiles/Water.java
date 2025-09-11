package builder.entities.tiles;

import builder.ui.SpriteGallery;
import engine.art.sprites.SpriteGroup;

/**
 *
 A water tile is a tile that cannot be walked over. A water tile is rendered as SpriteGallery.water.
 */
public class Water extends Tile {
    private final SpriteGroup water = SpriteGallery.water;

    /**
     *
     Construct a new water tile at the given x, y position.
     Parameters:
     x - The x-axis (horizontal) coordinate.
     y - The y-axis (vertical) coordinate.
     Requires:
     x >= 0, x is less than the window width, y >= 0, y is less than the window height
     */
    public Water(int x, int y) {
        super(x, y, SpriteGallery.water);
    }

    @Override
    public boolean canWalkThrough() {
        return false;
    }
}
