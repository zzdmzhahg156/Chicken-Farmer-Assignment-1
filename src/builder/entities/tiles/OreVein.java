package builder.entities.tiles;

import builder.entities.resources.Ore;
import builder.ui.SpriteGallery;
import engine.art.sprites.SpriteGroup;

public class OreVein extends Tile{
    private static final SpriteGroup field = SpriteGallery.field;
    private final Ore ore;

    public OreVein(int x, int y) {
        super(x, y, SpriteGallery.field);
        this.ore = new Ore(x,y);
        this.placeOn(ore);
    }

    public Ore getOre() {
        return ore;
    }
}
