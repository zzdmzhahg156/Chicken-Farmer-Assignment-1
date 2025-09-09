package builder.entities.tiles;

public class TileFactory {
    public TileFactory(){
        super();
    }

    public static Tile fromSymbol(int x, int y, char symbol) {
        switch (symbol) {
            case 'd' :
                return new Dirt(x,y);
            case 't' :
                Dirt tilledirt = new Dirt(x,y);
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
