package main.tiles;

public class TileStats {
    public float elevation;
    public float moisture;
    public float temperature;
    public TileStats(float elevation, float moisture, float temperature) {
        this.elevation = elevation;
        this.moisture = moisture;
        this.temperature = temperature;
    }
    public float CompareDistance(TileStats other) {
        double elevationDiff = other.elevation >= 0 ?Math.pow(this.elevation - other.elevation, 2) : 0;
        double moistureDiff = other.moisture >= 0 ? Math.pow(this.moisture - other.moisture, 2) : 0;
        double temperatureDiff = other.temperature >= 0 ? Math.pow(this.temperature - other.temperature, 2) : 0;
        return (float) Math.sqrt(elevationDiff + moistureDiff + temperatureDiff);
    }
}
