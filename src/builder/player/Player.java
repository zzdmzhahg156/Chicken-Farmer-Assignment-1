package builder.player;

/**
 *
 An interface to query the player entity in the game.
 */
public interface Player {

    /**
     *
     Returns the horizontal (x-axis) coordinate of the player entity.
     Returns:
     The horizontal (x-axis) coordinate.
     Ensures:
     \result >= 0, \result is less than the window width.
     */
    int getX(); // Returns the horizontal (x-axis) coordinate of the player entity.

    /**
     *
     Returns the horizontal (x-axis) coordinate of the player entity.
     Returns:
     The horizontal (x-axis) coordinate.
     Ensures:
     \result >= 0, \result is less than the window width.
     */
    int getY(); // Returns the vertical (y-axis) coordinate of the player entity.

    /**
     Returns the amount of damage dealt by a player hit.
     Returns:
     The amount of damage a player deals.
     */
    int getDamage(); // Returns the amount of damage dealt by a player hit.

}
