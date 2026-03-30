package entity;

import java.awt.*;
import java.awt.image.*;

public class Entity {

    public int worldX, worldY;
    public int speed;

    public BufferedImage front1, front2, back1, back2, left1, left2, right1, right2;
    public String direction;

    public int spriteCounter = 0;
    public int spriteNumber = 1;

    public Rectangle collisionBox;
    public int collisionBoxDefaultX,  collisionBoxDefaultY;
    public boolean collisionOn = false;
}
