package main.tiles;

public class Tile {
    private TileStats stats;
    private String path;
    private String name;
    public Tile(String path, String name, float elevation, float moisture, float temperature) {
        this.path = path;
        this.name = name;
        this.stats = new TileStats(elevation, moisture, temperature);
    }
    public TileStats GetStats() {
        return stats;
    }
    public String GetPath() {
        return path;
    }
    public String GetName() {
        return name;
    }
    public float CompareDistance(TileStats other) {
        return stats.CompareDistance(other);
    }
}
