package main;

import main.generators.PerlinNoiseGenerator;
import main.generators.RandomNoiseGenerator;
import main.generators.WorldGenerator;

public class Main {
    Display display;
    WorldGenerator worldGenerator;
    public void main(String[] args) {
        display = new Display(this, 1000, 1000, 40, 40);
        //worldGenerator = new PerlinNoiseGenerator(display,0.1f, true, true, true);
        worldGenerator = new RandomNoiseGenerator(display);
        while (true) {
            if(worldGenerator.ShouldContinue()) {
                worldGenerator.WorldGenStep();
            }
            try {
                Thread.sleep(1);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }
    public void ResetWorld() {
        worldGenerator.Restart();
    }
}
