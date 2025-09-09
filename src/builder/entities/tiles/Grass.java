package builder.entities.tiles;

import builder.GameState;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.Sprite;
import engine.art.sprites.SpriteGroup;

public class Grass extends Tile{
private final SpriteGroup grass = SpriteGallery.grass;

    public Grass(int x, int y) {
        super(x, y, SpriteGallery.grass);
    }

    public void use(EngineState state, GameState game){

    }


}
