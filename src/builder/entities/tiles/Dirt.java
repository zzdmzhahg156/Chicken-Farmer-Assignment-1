package builder.entities.tiles;

import builder.GameState;
import builder.entities.resources.Cabbage;
import builder.inventory.items.Bucket;
import builder.inventory.items.Hoe;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.SpriteGroup;
import engine.renderer.Dimensions;

import java.awt.*;

public class Dirt extends Tile {
    private final SpriteGroup field = SpriteGallery.field;
    private final SpriteGroup Tilled = SpriteGallery.tilled;
    private boolean isTilled = false;

    public Dirt(int x, int y) {
        super(x, y, SpriteGallery.field);
        this.isTilled = false;

    }

    public boolean isTilled() {
        return isTilled;
    }

    public void till() {
        this.isTilled = true;
        setArt(Tilled);
    }

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

            if (game.getInventory().getCoins() < Cabbage.Cost) {
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
            game.getInventory().addCoins(-Cabbage.Cost);
        }
    }


}
