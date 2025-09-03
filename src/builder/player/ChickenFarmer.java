package builder.player;

import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.SpriteGroup;
import engine.game.Direction;
import engine.game.Entity;

public class ChickenFarmer extends Entity implements Player {
    private final SpriteGroup art = SpriteGallery.chickenFarmer;
    public ChickenFarmer(int x, int y){
        super(x,y);
    }

    @Override
    public int getDamage(){
        return 0;
    }


    public void move(Direction direction, int amount){

    }

    @Override
    public void tick (EngineState state) {

    }




}
