package builder.player;

import builder.inventory.items.Item;
import builder.ui.SpriteGallery;
import engine.EngineState;
import engine.art.sprites.Sprite;
import engine.art.sprites.SpriteGroup;
import engine.game.Direction;
import engine.game.Entity;
import engine.timing.Animation;
import engine.timing.AnimationDuration;

/**
 * An instance of the player entity.
 * The chicken farmer is rendered to the screen and moved by the PlayerManager.
 */

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

    /**
     * Constructs a chicken farmer instance at the given coordinates.
     * Parameters:
     * x - The x-axis (horizontal) coordinate.
     * y - The y-axis (vertical) coordinate.
     * Requires:
     * x >= 0, x is less than the window width, y >= 0, y is less than the window height
     */
    public ChickenFarmer(int x, int y) {
        super(x, y);
    }

    @Override
    public int getDamage() {
        return 2;
    }

    /**
     * Move the player by the given amount in the given direction.
     * Update the player's x or y position according to the following table.
     * Direction x y
     * NORTH-amount
     * SOUTH amount
     * EAST amount
     * WEST-amount
     * The player's sprite should also be updated based on the move.
     * If the player moves north, the sprite should be set to 'up'. If the player moves south,
     * the sprite should be set to 'down'. If the player moves either east or west,
     * the sprite should be set to the appropriate animation step of that direction,
     *  see the hint in the class comment.
     * Note: Moving to a negative position is unspecified and won't be tested.
     * Parameters:
     * direction - The direction to move in.
     * amount - How many pixels to move the player.
     * Requires:
     * amount > 0
     */
    public void move(Direction direction, int amount) {
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
    public void tick(EngineState state) {
        setSprite(downFace);
        leftWalking.tick(state);
        rightWalking.tick(state);
        downWalking.tick(state);
        upWalking.tick(state);
    }

    /**
     * Animate the player using an item.
     * If the given item is null (the player is not holding an item) nothing should happen.
     * If the item's Item.useAnimation() is not empty (i.e. Optional.isPresent() is true)
     * then the player should store that animation and set its sprite to show the animation.
     * Parameters:
     * item - The item that the player is currently holding.
     */
    public void use(Item item) {
        if (item == null) {
            return;
        }

        if (item.useAnimation().isPresent()) {
            Animation currentAnimation = item.useAnimation().get();
            setSprite(currentAnimation);
        }
    }




}
