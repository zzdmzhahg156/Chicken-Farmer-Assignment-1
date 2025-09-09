package builder.entities.tiles;

import builder.GameState;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.SpriteGroup;

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

    }


}
