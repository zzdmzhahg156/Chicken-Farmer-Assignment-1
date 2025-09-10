package builder.entities.tiles;

import builder.GameState;
import builder.inventory.items.Hoe;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.Sprite;
import engine.art.sprites.SpriteGroup;
import engine.game.Entity;

public class Grass extends Tile{
private final SpriteGroup grass = SpriteGallery.grass;

    public Grass(int x, int y) {
        super(x, y, SpriteGallery.grass);
    }

    public void use(EngineState state, GameState game){
        if (this.isMarkedForRemoval()) {
            return;
        }
        if (game.getInventory().getHolding() instanceof Hoe) {
            Dirt dirt = new Dirt(this.getX(), this.getY());
            markForRemoval();
            this.placeOn(dirt);
        }
    }
}
