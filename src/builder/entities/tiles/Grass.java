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
private Dirt dirt;

    public Grass(int x, int y) {
        super(x, y, SpriteGallery.grass);
        this.dirt = new Dirt(x,y);
    }

    public void use(EngineState state, GameState game){
        super.use(state,game);

        if (game.getInventory().getHolding() instanceof Hoe) {
            if (!isMarkedForRemoval()){
                markForRemoval();
                game.getWorld().place(dirt);
            }
        }
    }
}
