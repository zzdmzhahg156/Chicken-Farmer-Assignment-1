package builder.entities.tiles;

import builder.entities.resources.Ore;
import builder.ui.SpriteGallery;
import engine.art.sprites.SpriteGroup;

/**
 * An ore vein tile has a Ore instance stacked on top.
 * An ore vein is rendered the same as a field but will always have an Ore above it.
 * An ore vein tile is rendered as SpriteGallery.field.
 */
public class OreVein extends Tile {
    private static final SpriteGroup field = SpriteGallery.field;
    private final Ore ore;

    /**
     *
     Construct a new ore vein at the given x, y position.
     Parameters:
     x - The x-axis (horizontal) coordinate.
     y - The y-axis (vertical) coordinate.
     Requires:
     x >= 0, x is less than the window width, y >= 0, y is less than the window height
     */
    public OreVein(int x, int y) {
        super(x, y, SpriteGallery.field);
        this.ore = new Ore(x, y);
        this.placeOn(ore);
    }

    /**
     * \
     Returns the instance of Ore stacked on this ore vein.
     Returns:
     The current instance on top of this tile.
     */
    public Ore getOre() {
        return ore;
    }
}
