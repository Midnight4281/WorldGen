package main.tiles;

import java.util.Comparator;
import java.util.List;
import java.util.Optional;

public class Tiles {
    public static final String blankTile = "blank";
    public static final List<Tile> tiles = List.of(
            new Tile("grass", "plains", 0.5f, 0.3f, 0.5f),
            new Tile("sand", "beach", 0.3f, 0.6f, 0.6f),
            new Tile("water", "ocean", 0.2f, 0.8f, 0.4f),
            new Tile("forest", "forest", 0.7f, 0.4f, 0.45f)
    );

    public static String GetTileName(int tile) {
        tile = Math.min(tile, tiles.size() - 1);
        if (tile < 0) return blankTile;
        return tiles.get(tile).GetPath();
    }

    public static int GetTileCount() {
        return tiles.size();
    }

    public static int GetRandomTileIndex() {
        return (int) (Math.random() * GetTileCount());
    }

    public static String getRandomTile() {
        return GetTileName((int) (Math.random() * GetTileCount()));
    }

    private static Tile GetTileClosestToStats(TileStats stats) {
        Optional<Tile> closestTile = tiles.stream()
                .min(Comparator.comparingDouble(tile -> tile.CompareDistance(stats)));
        return closestTile.orElse(null);
    }

    public static int GetTileIndexClosestToStats(TileStats stats) {
        return GetTileIndex(GetTileClosestToStats(stats));
    }

    public static int GetTileIndex(Tile tile) {
        return tiles.indexOf(tile);
    }
}
