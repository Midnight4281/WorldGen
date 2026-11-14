package main;

import main.tiles.Tiles;

import javax.imageio.ImageIO;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.IOException;
import java.io.InputStream;

public class Utils {
    public static Component findComponentByName(Container container, String targetName) {
        for (Component component : container.getComponents()) {
            if (targetName.equals(component.getName())) {
                return component;
            }
            if (component instanceof Container) {
                Component found = findComponentByName((Container) component, targetName);
                if (found != null) {
                    return found;
                }
            }
        }
        return null; // Not found
    }
    public static String getIcon(int i) {
        return "/tiles/" + Tiles.GetTileName(i) + ".png";
    }
    public static BufferedImage loadTile(int value) {
        try (InputStream is = Utils.class.getResourceAsStream("/main/resources/" + getIcon(value))) {
            if (is == null) {
                throw new IllegalArgumentException("Image not found in resources!");
            }
            return ImageIO.read(is);
        } catch (IOException e) {
            throw new RuntimeException(e);
        }
    }
}
