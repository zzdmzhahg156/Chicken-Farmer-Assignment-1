package builder.world;

import builder.entities.tiles.Tile;
import builder.entities.tiles.TileFactory;
import engine.renderer.Dimensions;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.List;

public class WorldBuilder {
    public WorldBuilder() {
        super();
    }

    public static List<Tile> fromString(Dimensions dimensions,
                                        String text)
            throws WorldLoadException {
        List <Tile> tiles = new ArrayList<>();
        String[] rows = text.split("\n", -1);

        if (rows.length > 0 && rows[rows.length - 1].isEmpty()) {
            rows = java.util.Arrays.copyOf(rows, rows.length - 1);
        }

        int expectedRows = dimensions.windowSize()/dimensions.tileSize();
        if (expectedRows != rows.length) {
            throw new WorldLoadException("Expected rows not equal provided", expectedRows, rows.length);
        }

        for (int row = 0; row < rows.length; row++) {
            String line = rows[row];

            if (line.length() != expectedRows) {
                throw new WorldLoadException(
                        String.format("Expected line length of %d but got %d", expectedRows, line.length()),
                        row
                );
            }

            for (int col = 0; col < line.length(); col++) {
                char symbol = line.charAt(col);

                int corX = col*dimensions.tileSize() + dimensions.tileSize()/2;
                int corY = row*dimensions.tileSize() + dimensions.tileSize()/2;

                Tile tile = TileFactory.fromSymbol(corX, corY, symbol);
                tiles.add(tile);

            }
        }

        return tiles;
    }

    public static BeanWorld fromFile(Dimensions dimensions,
                                     String filepath)
            throws IOException,
            WorldLoadException{
        String stringContent = new String(Files.readAllBytes(Paths.get(filepath)));
        List<Tile> tiles = fromString(dimensions, stringContent);

        return fromTiles(tiles);

    }

    public static BeanWorld empty() {
        return new BeanWorld();
    }

    public static BeanWorld fromTiles(List<Tile>tiles) {
        BeanWorld world = new BeanWorld();

        for (Tile tile : tiles) {
            world.place(tile);
        }

        return world;
    }


}
