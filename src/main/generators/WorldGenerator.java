package main.generators;

import main.Display;

public abstract class WorldGenerator {
    protected Display world;
    public int progress;

    public WorldGenerator(Display display) {
        this.world = display;
    }
    public abstract void WorldGenStep();
    public boolean ShouldContinue() {
        return progress < world.GetWorldSize();
    }
    public void AdvanceProgress() {
        progress++;
    }
    public void Restart() {
        progress = 0;
    }
}