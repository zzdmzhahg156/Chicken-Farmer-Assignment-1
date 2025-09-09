package builder.entities.tiles;

import builder.ui.SpriteGallery;
import engine.art.sprites.SpriteGroup;

public class Water extends Tile{
    private final SpriteGroup water = SpriteGallery.water;

    public Water(int x, int y) {
        super(x, y, SpriteGallery.water);
    }

    @Override
    public boolean canWalkThrough() {
        return false;
    }
}
