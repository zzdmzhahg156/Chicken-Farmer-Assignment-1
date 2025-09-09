package builder.entities.tiles;

import builder.ui.SpriteGallery;
import engine.art.sprites.SpriteGroup;

public class OreVein extends Tile{
    private static final SpriteGroup ore = SpriteGallery.field;

    public OreVein(int x, int y) {
        super(x, y, SpriteGallery.field);
    }
}
