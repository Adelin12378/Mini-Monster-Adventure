package main;

import entity.*;
import object.SuperObject;
import tile.*;

import javax.swing.*;
import java.awt.*;

public class GamePanel extends JPanel implements Runnable {

    final int originalTileSize = 32;
    final int scale = 2;
    public final int tileSize = originalTileSize * scale;
    public final int maxScreenCol = 16;
    public final int maxScreenRow = 16;
    public final int screenWidth = tileSize * maxScreenCol;
    public final int screenHeight = tileSize * maxScreenRow;
    public int maxWorldCol = 48;
    public int maxWorldRow = 31;
    public final int worldWidth = tileSize * maxWorldCol;
    public final int worldHeight = tileSize * maxWorldRow;
    int FPS = 60;

    public TileManager tileManager = new TileManager(this);
    public KeyActions keyActions = new KeyActions();
    Thread gameThread;
    public CollisinCheck colcheck = new CollisinCheck(this);
    public AssetSetter assetSetter = new AssetSetter(this);
    public Player player = new Player(this, keyActions);
    public SuperObject[] obj = new SuperObject[10];
    public String currentMap;
    public UI ui = new UI(this);
    public boolean[][] rockRemoved = new boolean[48][31];

    public GamePanel() {
        this.setPreferredSize(new Dimension(screenWidth, screenHeight));
        this.setBackground(Color.black);
        this.setDoubleBuffered(true);
        this.addKeyListener(keyActions);
        this.setFocusable(true);
    }

    public void setUpGame() {
        assetSetter.setObjects();
    }

    public void startGameThread() {
        gameThread = new Thread(this);
        gameThread.start();
    }

    @Override
    public void run() {
        double drawIntervals = (double) 1000000000 / FPS;
        double drawTimer = System.nanoTime() + drawIntervals;

        while (gameThread != null) {
            long currentTime = System.nanoTime();
            update();
            repaint();
            try {
                double deltaTime = drawTimer - System.nanoTime();
                deltaTime /= 1000000;

                if (deltaTime < 0) {
                    deltaTime = 0;
                }

                Thread.sleep((long)deltaTime);

                drawTimer += drawIntervals;
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        }
    }

    public void update() {
        player.update();
    }

    @Override
    protected void paintComponent(Graphics g) {
        super.paintComponent(g);

        Graphics2D grph2d = (Graphics2D) g;

        tileManager.drawTiles(grph2d);

        for (int i = 0; i < obj.length; i++) {
            if (obj[i] != null) {
                obj[i].draw(grph2d, this);
            }
        }

        player.draw(grph2d);

        ui.draw(grph2d);

        grph2d.dispose();
    }
}
