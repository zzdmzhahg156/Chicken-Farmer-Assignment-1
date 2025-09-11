package builder.entities.tiles;

import builder.GameState;
import builder.inventory.items.Hoe;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.SpriteGroup;

/**
 A grass tile is a basic tile. A grass tile can be walked through.
 A grass tile is rendered as SpriteGallery.grass. (Stage 3)
 A hoe can be used on the grass tile to turn it into a Dirt tile.
 */
public class Grass extends Tile {
    private final SpriteGroup grass = SpriteGallery.grass;
    private Dirt dirt;

    /**
     * Construct a new grass tile at the given x, y position.
     * Parameters:
     * x - The x-axis (horizontal) coordinate.
     * y - The y-axis (vertical) coordinate.
     * Requires:
     * x >= 0, x is less than the window width, y >= 0, y is less than the window height
     */
    public Grass(int x, int y) {
        super(x, y, SpriteGallery.grass);
        this.dirt = new Dirt(x, y);
    }

    /**
     *When a hoe is used on a grass tile, it should be marked for removal and replaced with a dirt tile at the same location.
     * If the tile is already marked for removal (according to Entity.markForRemoval()) then it should not be replaced.
     * Specified by:
     * use in interface Usable
     * Overrides:
     * use in class Tile
     * Parameters:
     * state - The state of the engine provides information about which tick this interaction occurred during.
     * game - The game state that can be queried or updated as needed.
     */
    public void use(EngineState state, GameState game) {
        super.use(state, game);

        if (game.getInventory().getHolding() instanceof Hoe) {
            if (!isMarkedForRemoval()) {
                markForRemoval();
                game.getWorld().place(dirt);
            }
        }
    }
}
