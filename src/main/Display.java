package main;

import javax.swing.*;
import javax.swing.border.Border;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.MouseAdapter;
import java.awt.event.MouseEvent;
import java.net.URL;
import java.util.ArrayList;
import java.util.List;

public class Display {
    private Main main;

    private int tileSize;

    private int toolBarWidth = 200;

    private int worldX, worldY;

    public JFrame window;

    public JPanel worldPanel;
    public JPanel toolPanel;

    public JButton resetButton;

    private List<Integer> world = new ArrayList<>();

    Border blackline = BorderFactory.createLineBorder(Color.black);
    public Display(Main main, int pixelX, int pixelY, int worldX, int worldY) {
        this.worldX = worldX;
        this.worldY = worldY;
        //calculate size of tiles
        tileSize = Math.min((pixelX - toolBarWidth) / worldX , pixelY / worldY);
        System.out.println("tileSize: " + tileSize);

        this.main = main;
        //initialize the screen
        window = new JFrame("World Generator");
        window.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        window.setSize((worldX * tileSize + toolBarWidth), (tileSize * worldY));
        window.setLayout(new BorderLayout());

        worldPanel = new JPanel();
        worldPanel.setBorder(blackline);
        worldPanel.setPreferredSize(new Dimension((int) (tileSize * worldX), (int) (tileSize * worldY)));
        worldPanel.setLayout(new GridLayout(worldY, worldX));

        toolPanel = new JPanel();
        toolPanel.setBorder(blackline);
        toolPanel.setPreferredSize(new Dimension(toolBarWidth, (int) (tileSize * worldY)));

        resetButton = new JButton("Reset");
        resetButton.addMouseListener(new MouseAdapter() {
            @Override
            public void mousePressed(MouseEvent e) {
                super.mousePressed(e);
                ResetWorld();
            }
        });
        toolPanel.add(resetButton);

        window.add(worldPanel, BorderLayout.EAST);
        window.add(toolPanel, BorderLayout.WEST);

        FixWorldPanel();

        window.revalidate();
        window.repaint();

        window.setVisible(true);

        System.out.println((worldX * tileSize + toolBarWidth) + ", " + (tileSize * worldY));
    }
    public void FixWorldPanel() {
        worldPanel.removeAll();
        for(int i = 0; i < worldX * worldY; i++){
            int num = i;
            JLabel label = new JLabel();
            label.setBorder(BorderFactory.createLineBorder(Color.black));
            label.setName("tile_" + num);
            label.setSize(tileSize, tileSize);

            worldPanel.add(label);

            SetWorld(i, -1, false);
        }
    }
    public void ResetWorld() {
        for(int i = 0; i < worldX * worldY; i++){
            SetWorld(i, -1, true);
        }
        main.ResetWorld();
    }
    public int GetWorldSize() {
        return world.size();
    }
    public int GetWorldX() {
        return worldX;
    }
    public int GetWorldY() {
        return worldY;
    }
    public int GetWorld(int index) {
        return world.get(index);
    }
    public int GetWorld(int x, int y) {
        return GetWorld(GetIndexXY(x, y));
    }
    public int GetCentralTile() {
        return worldX * worldY / 2;
    }
    public boolean GetIfInWorld(int x, int y) {
        return x >= 0 && y >= 0 && x < worldX && y < worldY;
    }
    public boolean GetIfInWorld(int index) {
        return index >= 0 && index < world.size();
    }
    public int GetXAtIndex(int index) {
        return index % worldX;
    }
    public int GetYAtIndex(int index) {
        return (index / worldX);
    }
    public int GetIndexXY(int x, int y) {
        return x + y * worldX;
    }
    public void SetWorld(int x, int y, int value) {
        SetWorld(GetIndexXY(x, y), value);
    }
    public void SetWorld(int index, int value) {
        SetWorld(index, value, true);
    }
    public void SetWorld(int index, int value, boolean drawTile) {
        if(index < 0 || index >= worldX * worldY){
            return;
        }
        while(index >= world.size()) {
            world.add(0);
        }
        world.set(index, value);
        if(drawTile) {
            SetTile(index, value);
        }
    }
    public void SetTile(int index, int value) {
        Component component = Utils.findComponentByName(worldPanel, "tile_" + index);
        if(component instanceof JLabel){
            JLabel label = (JLabel) component;
            label.setIcon(new ScalableIcon(Utils.loadTile(value)));
        }
    }
}
