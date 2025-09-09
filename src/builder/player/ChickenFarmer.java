package builder.player;

import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.Sprite;
import engine.art.sprites.SpriteGroup;
import engine.game.Direction;
import engine.game.Entity;
import engine.timing.Animation;
import engine.timing.AnimationDuration;

public class ChickenFarmer extends Entity implements Player {
    private final SpriteGroup art = SpriteGallery.chickenFarmer;
    private final Animation leftWalking =
            new Animation(
                    AnimationDuration.SLOW,
                    new Sprite[] {
                            art.getSprite("left"), art.getSprite("left1"), art.getSprite("left2")
                    });
    private final Animation rightWalking =
            new Animation(
                    AnimationDuration.SLOW,
                    new Sprite[] {
                            art.getSprite("right"), art.getSprite("right1"), art.getSprite("right2")
                    });
    private final Animation upWalking =
            new Animation(
                    AnimationDuration.SLOW,
                    new Sprite[] {art.getSprite("up"), art.getSprite("up1"), art.getSprite("up2")});
    private final Animation downWalking =
            new Animation(
                    AnimationDuration.SLOW,
                    new Sprite[] {
                            art.getSprite("down"), art.getSprite("down1"), art.getSprite("down2")});
    private final Animation downFace =
            new Animation(
                    AnimationDuration.SLOW,
                    new Sprite[] {
                            art.getSprite("down")
                    });


    private Direction travelling = Direction.SOUTH;



    public ChickenFarmer(int x, int y){
        super(x,y);
    }

    @Override
    public int getDamage(){
        return 2;
    }


    public void move(Direction direction, int amount){

        switch (direction) {
            case NORTH -> setY(getY() - amount);
            case SOUTH -> setY(getY() + amount);
            case EAST -> setX(getX() + amount);
            case WEST -> setX(getX() - amount);
        }

        switch (direction) {
            case EAST -> setSprite(rightWalking);
            case WEST -> setSprite(leftWalking);
            case NORTH -> setSprite(upWalking);
            case SOUTH -> setSprite(downWalking);
        }
    }

    @Override
    public void tick (EngineState state) {
        setSprite(downFace);
        leftWalking.tick(state);
        rightWalking.tick(state);
        downWalking.tick(state);
        upWalking.tick(state);
    }




}
