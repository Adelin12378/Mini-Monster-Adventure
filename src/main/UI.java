package main;

import object.ObjectAxe;
import object.ObjectFishingRod;
import object.ObjectKey;
import object.ObjectPickaxe;

import java.awt.*;
import java.awt.image.BufferedImage;

public class UI {

    GamePanel gamePanel;
    Font font;

    public UI(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
        font = new Font("Arial", Font.PLAIN, 20);
    }

    public void draw(Graphics2D g2) {
        drawHotbar(g2);
    }

    public void drawHotbar(Graphics2D g2) {
        int frameWidth = gamePanel.tileSize * 9;
        int frameHeight = gamePanel.tileSize;
        int frameX = (gamePanel.screenWidth / 2) - (frameWidth / 2);
        int frameY = gamePanel.screenHeight - gamePanel.tileSize - 20;

        g2.setColor(new Color(0, 0, 0, 150));
        g2.fillRect(frameX, frameY, frameWidth, frameHeight);

        g2.setColor(Color.white);
        g2.setStroke(new BasicStroke(2));
        for(int i = 0; i < 9; i++) {
            g2.drawRect(frameX + (i * gamePanel.tileSize), frameY, gamePanel.tileSize, gamePanel.tileSize);
        }

        if (gamePanel.player.hasKey > 0) {
            BufferedImage keyImg = new ObjectKey().image;

            g2.drawImage(keyImg, frameX + 10, frameY + 10, gamePanel.tileSize - 20, gamePanel.tileSize - 20, null);

            g2.setFont(font);
            g2.drawString(String.valueOf(gamePanel.player.hasKey), frameX + gamePanel.tileSize - 25, frameY + gamePanel.tileSize - 10);
        }

        if (gamePanel.player.hasPickaxe) {
            BufferedImage pickaxeImg = new ObjectPickaxe().image;

            g2.drawImage(pickaxeImg, frameX + gamePanel.tileSize + 10, frameY + 10, null);

            g2.setFont(font);
            g2.drawString("1", frameX + gamePanel.tileSize - 25, frameY + gamePanel.tileSize - 10);
        }

        if (gamePanel.player.hasAxe) {
            BufferedImage axeImg = new ObjectAxe().image;

            g2.drawImage(axeImg, frameX + (2 * gamePanel.tileSize) + 10, frameY + 10, null);

            g2.setFont(font);
            g2.drawString("1", frameX + gamePanel.tileSize - 25, frameY + gamePanel.tileSize - 10);
        }

        if (gamePanel.player.hasFihingRod) {
            BufferedImage rodImg = new ObjectFishingRod().image;

            g2.drawImage(rodImg, frameX + (3 * gamePanel.tileSize) + 10, frameY + 10, null);

            g2.setFont(font);
            g2.drawString("1", frameX + gamePanel.tileSize - 25, frameY + gamePanel.tileSize - 10);
        }
    }
}
