package object;

import main.GamePanel;

import java.awt.*;
import java.awt.image.*;

public class SuperObject {

    public BufferedImage image;
    public String name;
    public boolean collision = false;
    public int worldX, worldY;
    public Rectangle collisionBox = new Rectangle(0, 0, 48, 48);
    public int collisionBoxDefaultX = 0,  collisionBoxDefaultY = 0;
    public boolean isNPC = false;
    public void draw(Graphics2D grph2d, GamePanel gamePanel) {

        int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
        int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

        if (gamePanel.player.screenX > gamePanel.player.worldX) {
            screenX = worldX;
        }
        if (gamePanel.player.screenY > gamePanel.player.worldY) {
            screenY = worldY;
        }

        if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

            grph2d.drawImage(image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
        }
    }

}
