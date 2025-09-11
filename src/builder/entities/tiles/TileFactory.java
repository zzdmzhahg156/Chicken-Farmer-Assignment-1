package builder.entities.tiles;

/**
 *
 A tile factory uses the fromSymbol(int, int, char) method
 to construct new tile instances from a set encoding.
 */
public class TileFactory {

    /**
     *
     Construct a new tile factory.
     */
    public TileFactory() {
        super();
    }

    /**
     * Construct a new tile based on the symbol encoded at the given position.
     * The following table enumerates the tile encodings.
     * Character Tile
     * d Dirt
     * t Dirt that has been tilled
     * w Water
     * g Grass
     * o OreVein
     * Any characters not listed above should throw an IllegalArgumentException.
     * Parameters:
     * x - The x-axis (horizontal) coordinate.
     * y - The y-axis (vertical) coordinate.
     * symbol - A symbol to identify the tile type.
     * Returns:
     * A new tile at the given x,y coordinate of the type specified by the symbol.
     * Throws:
     * IllegalArgumentException - If symbol does not correspond to a tile.
     * Requires:
     * x >= 0, y >= 0
     */
    public static Tile fromSymbol(int x, int y, char symbol) {
        switch (symbol) {
            case 'd' :
                return new Dirt(x, y);
            case 't' :
                Dirt tilledirt = new Dirt(x, y);
                tilledirt.till();
                return tilledirt;
            case 'w':
                return new Water(x, y);
            case 'g':
                return new Grass(x, y);
            case 'o':
                return new OreVein(x, y);
            default:
                throw new IllegalArgumentException("Invalid tile symbol: '" + symbol + "'");

        }

    }
}
