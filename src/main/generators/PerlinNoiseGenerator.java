package main.generators;

import main.Display;
import main.tiles.TileStats;
import main.tiles.Tiles;
import main.noise.PerlinNoise;

public class PerlinNoiseGenerator extends WorldGenerator {
    private PerlinNoise elevationMap, moistureMap, temperatureMap;

    private boolean usesElevation, usesMoisture, usesTemperature;

    public long seed;
    public float scale = 0.05f;

    public PerlinNoiseGenerator(Display display, float scale, boolean usesElevation, boolean usesMoisture, boolean usesTemperature) {
        super(display);
        this.scale = scale;
        this.usesElevation = usesElevation;
        this.usesMoisture = usesMoisture;
        this.usesTemperature = usesTemperature;
        ResetRandom();
    }

    @Override
    public void WorldGenStep() {
        int index = progress;
        int x = world.GetXAtIndex(index);
        int y = world.GetYAtIndex(index);

        float normalizedElevation = (float) ((elevationMap.noise(x * scale, y * scale) + 1) / 2);
        float normalizedMoisture = (float) ((moistureMap.noise(x * scale, y * scale) + 1) / 2);
        float normalizedTemperature = (float) ((elevationMap.noise(x * scale, y * scale) + 1) / 2);

        TileStats stats = new TileStats(
                usesElevation ? normalizedElevation : -1,
                usesMoisture ? normalizedMoisture : -1,
                usesTemperature ? normalizedTemperature : -1
        );
        //System.out.println(stats.elevation + ", " + stats.moisture + ", " + stats.temperature);
        world.SetWorld(index, Tiles.GetTileIndexClosestToStats(stats));
        AdvanceProgress();
    }

    @Override
    public void Restart() {
        super.Restart();
        ResetRandom();
    }

    private void ResetRandom() {
        seed = System.currentTimeMillis();
        elevationMap = new PerlinNoise(seed);
        moistureMap = new PerlinNoise(seed + 1);
        temperatureMap = new PerlinNoise(seed + 2);
    }
}
