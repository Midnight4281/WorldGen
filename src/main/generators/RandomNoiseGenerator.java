package main.generators;

import main.Display;
import main.tiles.Tiles;

public class RandomNoiseGenerator extends WorldGenerator {
    public RandomNoiseGenerator(Display display) {
        super(display);
    }

    @Override
    public void WorldGenStep() {
        world.SetWorld(progress, Tiles.GetRandomTileIndex());
        AdvanceProgress();
    }
}
