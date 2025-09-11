package builder.world;

import builder.entities.tiles.Tile;
import builder.entities.tiles.TileFactory;
import engine.renderer.Dimensions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

/**
 *
 Load an instance of a world from a string representation.
 Each line of the file, separated by new line characters,
 corresponds to a row of tiles in the world.
 Each character represents a tile according to TileFactory.fromSymbol(int, int, char).
 */
public class WorldBuilder {

    /**
     * Construct a new world builder.
     */
    public WorldBuilder() {
        super();
    }

    /**
     * Read the encoded world text and construct the corresponding list of tiles.
     * Each line in the text corresponds to a horizontal row of tiles. Each character correspond to a tile. A character in the file at line 3, character 10, will correspond to a tile at y=2 and x=9.
     * The character in the encoding indicates the type of tile to construct based on TileFactory.fromSymbol(int, int, char).
     * The number of lines and length of those lines must correspond to the dimensions provided. For example, if the window size is 800 and the tile size is 25 then we expect (800/25 =) 32 tiles so there must be 32 lines of text and each line must have 32 characters. Otherwise, a WorldLoadException is thrown.
     * Parameters:
     * dimensions - The dimensions of the world. The tile encoding must correspond to these dimensions.
     * text - The text encoding of a world.
     * Returns:
     * A list of tiles loaded from the given string.
     * Throws:
     * WorldLoadException - If the number of lines doesn't match the required amount according to the dimensions.
     * WorldLoadException - If the length of any line doesn't match the required amount according to the dimensions.
     * WorldLoadException - If any character doesn't correspond to a tile according to TileFactory.fromSymbol(int, int, char).
     */
    public static List<Tile> fromString(Dimensions dimensions,
                                        String text)
            throws WorldLoadException {
        List<Tile> tiles = new ArrayList<>();
        String[] rows = text.split("\n", -1);

        if (rows.length > 0 && rows[rows.length - 1].isEmpty()) {
            rows = java.util.Arrays.copyOf(rows, rows.length - 1);
        }

        int expectedRows = dimensions.windowSize() / dimensions.tileSize();
        if (expectedRows != rows.length) {
            throw new WorldLoadException("Expected rows not equal provided",
                    expectedRows, rows.length);
        }

        for (int row = 0; row < rows.length; row++) {
            String line = rows[row];

            if (line.length() != expectedRows) {
                throw new WorldLoadException(
                        String.format("Expected line length of %d but got %d",
                                expectedRows, line.length()),
                        row
                );
            }

            for (int col = 0; col < line.length(); col++) {
                char symbol = line.charAt(col);

                int corX = col * dimensions.tileSize() + dimensions.tileSize() / 2;
                int corY = row * dimensions.tileSize() + dimensions.tileSize() / 2;

                Tile tile = TileFactory.fromSymbol(corX, corY, symbol);
                tiles.add(tile);

            }
        }

        return tiles;
    }

    /**
     Read the provided file and attempt to create
     a new world based on the tile encoding in the file.
     See fromString(Dimensions, String) for a description of how the tile encoding is read.

     Parameters:
     dimensions - The dimensions of the world.
     The tile encoding must correspond to these dimensions.
     filepath - The path to a file containing a tile encoding.
     Returns:
     A new world containing all tiles in the specified file.
     Throws:
     IOException - If the file path doesn't exist or otherwise can't be read.
     WorldLoadException - If the tile encoding is invalid
     (according to fromString(Dimensions, String)).
     */
    public static BeanWorld fromFile(Dimensions dimensions,
                                     String filepath)
            throws IOException,
            WorldLoadException {
        String stringContent = new String(Files.readAllBytes(Paths.get(filepath)));
        List<Tile> tiles = fromString(dimensions, stringContent);

        return fromTiles(tiles);

    }

    /**
     * Construct a new empty world, i.e. with no tiles.
     */
    public static BeanWorld empty() {
        return new BeanWorld();
    }

    /**
     * Construct a new world containing all the tiles in the parameter.
     */
    public static BeanWorld fromTiles(List<Tile> tiles) {
        BeanWorld world = new BeanWorld();

        for (Tile tile : tiles) {
            world.place(tile);
        }

        return world;
    }


}
