package builder.entities.tiles;

import builder.GameState;
import builder.entities.resources.Cabbage;
import builder.inventory.items.Bucket;
import builder.inventory.items.Hoe;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.SpriteGroup;
import engine.renderer.Dimensions;

/**
 * A dirt tile may be used for farming. A dirt tile has two states: tilled and untilled.
 * The tile should begin untilled and may become tilled by using a hoe on it.
 * When untilled, dirt is rendered as SpriteGallery.field, when tilled,
 * dirt is rendered as SpriteGallery.tilled.
 * A bucket can be used on dirt to plant a cabbage on it.
 */
public class Dirt extends Tile {
    private final SpriteGroup field = SpriteGallery.field;
    private final SpriteGroup Tilled = SpriteGallery.tilled;
    private boolean isTilled = false;

    /**
     * Construct a new untilled dirt tile at the given x, y position.
     *
     * @param x The x-axis (horizontal) coordinate.
     * @param y The y-axis (vertical) coordinate.
     */
    public Dirt(int x, int y) {
        super(x, y, SpriteGallery.field);
        this.isTilled = false;

    }

    /**
     * Whether the dirt is tilled or not.
     *
     * @return true if the dirt is tilled, false otherwise.
     */
    public boolean isTilled() {
        return isTilled;
    }

    /**
     * Till the dirt, changing its rendering to its tilled state.
     */
    public void till() {
        this.isTilled = true;
        setArt(Tilled);
    }

    /**
     * When a hoe is used on a dirt tile, it should become tilled.
     * When a bucket is used on a dirt tile and conditions are met,
     * a cabbage should be planted upon it.
     *
     * @param state The state of the engine provides information about
     *              which tick this interaction occurred during.
     * @param game The game state that can be queried or updated as needed.
     */
    @Override
    public void use(EngineState state,
                    GameState game) {
        super.use(state,game);

        if (game.getInventory().getHolding() instanceof Hoe) {
            till();
        } else if (game.getInventory().getHolding() instanceof Bucket) {

            if (!(isTilled)) {
                return;
            }

            if (game.getInventory().getCoins() < Cabbage.COST) {
                return;
            }

            int corX = game.getPlayer().getX();
            int corY = game.getPlayer().getY();
            Dimensions dimension = state.getDimensions();
            int checkCabbage = (this.getStackedEntities().size());

            if (checkCabbage > 0) {
                return;
            }

            Cabbage cabbage = new Cabbage(this.getX(),this.getY());
            this.placeOn(cabbage);
            game.getInventory().addCoins(-Cabbage.COST);
        }
    }
}
