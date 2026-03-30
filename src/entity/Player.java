package entity;
import main.*;
import object.*;

import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.File;
import java.io.InputStream;

public class Player extends Entity {

    GamePanel gamePanel;
    KeyActions keyActions;

    public final int screenX, screenY;
    public boolean openedChest = false;
    public boolean keyPickedUp = false;
    public boolean hasPickaxe = false;
    public boolean hasAxe = false;
    public boolean hasFihingRod = false;
    public int hasKey = 0;


    public Player(GamePanel gamePanel, KeyActions keyActions) {
        this.gamePanel = gamePanel;
        this.keyActions = keyActions;
        screenX = gamePanel.screenWidth / 2 - gamePanel.tileSize / 2;
        screenY = gamePanel.screenHeight / 2 - gamePanel.tileSize / 2;
        collisionBox = new Rectangle(8, 16, 48, 48);
        collisionBoxDefaultX = collisionBox.x;
        collisionBoxDefaultY = collisionBox.y;
        setDefaults();
        getPlayerImage();
    }

    public void setDefaults() {
        worldX = gamePanel.tileSize * 8;
        worldY = gamePanel.tileSize * 8;
        speed = 5;
        direction = "down";
        gamePanel.currentMap = "overworld";
    }

    public void getPlayerImage() {
        try {
            front1 = ImageIO.read(getClass().getResourceAsStream("/player/playerFront1.png"));
            front2 = ImageIO.read(getClass().getResourceAsStream("/player/playerFront2.png"));
            back1 = ImageIO.read(getClass().getResourceAsStream("/player/playerBack1.png"));
            back2 = ImageIO.read(getClass().getResourceAsStream("/player/playerBack2.png"));
            left1 = ImageIO.read(getClass().getResourceAsStream("/player/playerLeft1.png"));
            left2 = ImageIO.read(getClass().getResourceAsStream("/player/playerLeft2.png"));
            right1 = ImageIO.read(getClass().getResourceAsStream("/player/playerRight1.png"));
            right2 = ImageIO.read(getClass().getResourceAsStream("/player/playerRight2.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void update() {

        if (keyActions.upP == true || keyActions.downP == true || keyActions.leftP == true || keyActions.rightP == true) {
            if (keyActions.upP) {
                direction = "up";
            }
            if (keyActions.downP) {
                direction = "down";
            }
            if (keyActions.leftP) {
                direction = "left";
            }
            if (keyActions.rightP) {
                direction = "right";
            }

            collisionOn = false;
            gamePanel.colcheck.checkTile(this);

            int objIndex = gamePanel.colcheck.checkObject(this, true);
            pickUpObject(objIndex);

            if (!collisionOn) {
                switch (direction) {
                    case "up" :
                        worldY -= speed;
                        break;
                    case "down":
                        worldY += speed;
                        break;
                    case "left":
                        worldX -= speed;
                        break;
                    case "right":
                        worldX += speed;
                        break;
                }
            }

            spriteCounter++;
            if (spriteCounter > 15) {
                if (spriteNumber == 1) {
                    spriteNumber = 2;
                } else if (spriteNumber == 2) {
                    spriteNumber = 1;
                }
                spriteCounter = 0;
            }

            if (gamePanel.keyActions.actionEP) {
                checkFishing();
            }
        }

        if (keyActions.actionEP) {
            checkFishing();
            checkMinig();
            checkCuttingTree();
            checkNPCInteraction();
        }

        int playerCenterX = worldX + (gamePanel.tileSize / 2);
        int playerCenterY = worldY + (gamePanel.tileSize / 2);

        int col = playerCenterX / gamePanel.tileSize;
        int row = playerCenterY / gamePanel.tileSize;

        if (col >= 0 && col < gamePanel.maxWorldCol && row >= 0 && row < gamePanel.maxWorldRow) {
            if (gamePanel.tileManager.mapTileNum[col][row] == 5) {
                gamePanel.maxWorldCol = 16;
                gamePanel.maxWorldRow = 16;
                gamePanel.currentMap = "house downstairs";
                System.out.println("intrat");
                teleportToHouse();
            }
        }

        if (col >= 0 && col < gamePanel.maxWorldCol && row >= 0 && row < gamePanel.maxWorldRow) {
            if (gamePanel.tileManager.mapTileNum[col][row] == 13) {
                gamePanel.maxWorldCol = 48;
                gamePanel.maxWorldRow = 31;
                System.out.println("iesit");
                teleportOutside();
            }
        }

        if (col >= 0 && col < gamePanel.maxWorldCol && row >= 0 && row < gamePanel.maxWorldRow) {
            if (gamePanel.tileManager.mapTileNum[col][row] == 12)
                if (isUpstairs) {
                    gamePanel.maxWorldCol = 16;
                    gamePanel.maxWorldRow = 16;
                    System.out.println("jos");
                    teleportToHouse();
                    isUpstairs = false;
                } else {
                    gamePanel.maxWorldCol = 16;
                    gamePanel.maxWorldRow = 16;
                    System.out.println("sus");
                    teleportUpstairs();
                    isUpstairs = true;
                }
        }
    }

    public void pickUpObject(int index) {
        if (index != 999) {
            if (gamePanel.obj[index] instanceof ObjectKey) {
                gamePanel.obj[index] = null;
                hasKey++;
                keyPickedUp = true;
                JOptionPane.showMessageDialog(null, "You just picked up a key, now you have " + hasKey + " keys! \n Maybe there is a chest on which you could use it.", "Picked up key", JOptionPane.INFORMATION_MESSAGE);
            }

            if (gamePanel.obj[index] instanceof ObjectChest && hasKey > 0) {
                int savedX = gamePanel.obj[index].worldX;
                int savedY = gamePanel.obj[index].worldY;

                gamePanel.obj[index] = new ObjectFishingRod();
                gamePanel.obj[index].worldX = savedX;
                gamePanel.obj[index].worldY = savedY;

                hasKey--;
                openedChest = true;
                JOptionPane.showMessageDialog(null, "You've just opened this chest! \n Wonder what's in it.", "Chest Opened", JOptionPane.INFORMATION_MESSAGE);
            }

            if (gamePanel.obj[index] instanceof ObjectFishingRod) {
                gamePanel.obj[index] = null;
                hasFihingRod = true;
                JOptionPane.showMessageDialog(null, "You found a fishing rod, this is amazing! \n Maybe you could try to fish in the pond nearby. \n I doubt you'll have any luck since there hasn't been any fish in that pond in a long time.", "Fishing rod obtained", JOptionPane.INFORMATION_MESSAGE);
            }
        }
    }

    boolean isUpstairs = false;

    public void teleportToHouse() {
        gamePanel.currentMap = "house downstairs";
        gamePanel.tileManager.loadMap("/maps/inside-of-the-house.txt");

        gamePanel.assetSetter.setObjects();

        if (isUpstairs) {
            this.worldX = gamePanel.tileSize * 7;
            this.worldY = gamePanel.tileSize * 2;
        } else {
            this.worldX = gamePanel.tileSize * 7;
            this.worldY = gamePanel.tileSize * 14;
        }
    }

    public void teleportUpstairs() {
        gamePanel.currentMap = "house upstairs";
        gamePanel.tileManager.loadMap("/maps/upstairs.txt");

        gamePanel.assetSetter.setObjects();

        this.worldX = gamePanel.tileSize * 7;
        this.worldY = gamePanel.tileSize * 2;

        isUpstairs = true;
    }

    public void teleportOutside() {
        gamePanel.currentMap = "overworld";
        gamePanel.tileManager.loadMap("/maps/map01.txt");

        gamePanel.assetSetter.setObjects();

        this.worldX = gamePanel.tileSize * 5;
        this.worldY = gamePanel.tileSize * 8;
    }

    public void draw(Graphics2D grph2d) {
        BufferedImage image = null;

        switch (direction) {
            case "up":
                if (spriteNumber == 1) {
                    image = back1;
                } else if (spriteNumber == 2) {
                    image = back2;
                }
                break;
            case "down":
                if (spriteNumber == 1) {
                    image = front1;
                } else if (spriteNumber == 2) {
                    image = front2;
                }
                break;
            case "left":
                if (spriteNumber == 1) {
                    image = left1;
                } else if (spriteNumber == 2) {
                    image = left2;
                }
                break;
            case "right":
                if (spriteNumber == 1) {
                    image = right1;
                } else if (spriteNumber == 2) {
                    image = right2;
                }
                break;
        }

        int tempScreenX = screenX;
        int tempScreenY = screenY;

        if (screenX > worldX) {
            tempScreenX = worldX;
        } else if (screenX > gamePanel.worldWidth - worldX) {
            tempScreenX = gamePanel.screenWidth - (gamePanel.worldWidth - worldX);
        }

        if (screenY > worldY) {
            tempScreenY = worldY;
        } else if (screenY > gamePanel.worldHeight - worldY) {
            tempScreenY = gamePanel.screenHeight - (gamePanel.worldHeight - worldY);
        }

        grph2d.drawImage(image, tempScreenX, tempScreenY, gamePanel.tileSize, gamePanel.tileSize, null);
    }

    public void checkFishing() {
        int col = (worldX + gamePanel.tileSize / 2) / gamePanel.tileSize;
        int row = (worldY + gamePanel.tileSize / 2) / gamePanel.tileSize;

        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        for (int[] dir : directions) {
            int checkCol = col + dir[0];
            int checkRow = row + dir[1];

            if (checkCol >= 0 && checkCol < gamePanel.maxWorldCol &&
                checkRow >= 0 && checkRow < gamePanel.maxWorldRow) {

                if (gamePanel.tileManager.mapTileNum[checkCol][checkRow] == 2) {

                    if (!hasFihingRod) {
                        JOptionPane.showMessageDialog(null, "You need a fishing rod to fish!", "Fishing Failed", JOptionPane.INFORMATION_MESSAGE);
                        gamePanel.keyActions.actionEP = false;
                        return;
                    }

                    if (checkCol == 20 && checkRow == 11) {
                        if (!hasAxe) {
                            hasAxe = true;
                            JOptionPane.showMessageDialog(null, "You fished up an Axe!", "Fishing Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else if (checkCol == 25 && checkRow == 11) {
                        if (!hasPickaxe) {
                            hasPickaxe = true;
                            JOptionPane.showMessageDialog(null, "You fished up an Pickaxe!", "Fishing Success", JOptionPane.INFORMATION_MESSAGE);
                        }
                    }
                    else {
                        JOptionPane.showMessageDialog(null, "The pond hasn't had a fish in many years!", "Fishing Failed", JOptionPane.INFORMATION_MESSAGE);

                    }

                    gamePanel.keyActions.actionEP = false;
                    return;
                }
            }
        }
        gamePanel.keyActions.actionEP = false;
    }

    public void checkMinig () {
        int col = (worldX + gamePanel.tileSize / 2) / gamePanel.tileSize;
        int row = (worldY + gamePanel.tileSize / 2) / gamePanel.tileSize;

        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        for (int[] dir : directions) {
            int checkCol = col + dir[0];
            int checkRow = row + dir[1];

            if (checkCol >= 0 && checkCol < gamePanel.maxWorldCol &&
                checkRow >= 0 && checkRow < gamePanel.maxWorldRow) {

                if (gamePanel.tileManager.mapTileNum[checkCol][checkRow] == 6) {
                    if (!hasPickaxe) {
                        JOptionPane.showMessageDialog(null, "You need a pickaxe to mine!", "Mining Failed", JOptionPane.INFORMATION_MESSAGE);
                        gamePanel.keyActions.actionEP = false;
                        return;
                    }

                    gamePanel.tileManager.mapTileNum[checkCol][checkRow] = 0;

                    gamePanel.keyActions.actionEP = false;
                    return;
                }
            }
        }
        gamePanel.keyActions.actionEP = false;
    }

    public void checkCuttingTree () {
        int col = (worldX + gamePanel.tileSize / 2) / gamePanel.tileSize;
        int row = (worldY + gamePanel.tileSize / 2) / gamePanel.tileSize;

        int[][] directions = {{0, -1}, {0, 1}, {-1, 0}, {1, 0}};

        for (int[] dir : directions) {
            int checkCol = col + dir[0];
            int checkRow = row + dir[1];

            if (checkCol >= 0 && checkCol < gamePanel.maxWorldCol &&
                    checkRow >= 0 && checkRow < gamePanel.maxWorldRow) {

                if (gamePanel.tileManager.mapTileNum[checkCol][checkRow] == 7 ||  gamePanel.tileManager.mapTileNum[checkCol][checkRow] == 8) {
                    if (!hasAxe) {
                        JOptionPane.showMessageDialog(null, "You need an axe to chop this tree!", "Chopping Failed", JOptionPane.INFORMATION_MESSAGE);
                        gamePanel.keyActions.actionEP = false;
                        return;
                    }

                    gamePanel.tileManager.mapTileNum[checkCol][checkRow] = 0;

                    gamePanel.keyActions.actionEP = false;
                    return;
                }
            }
        }
        gamePanel.keyActions.actionEP = false;
    }

    public void checkNPCInteraction () {
        int objIndex = gamePanel.colcheck.checkObject(this, true);

        if (objIndex != 999) {
            if (gamePanel.obj[objIndex] instanceof SuperObject && gamePanel.obj[objIndex].isNPC) {
                JOptionPane.showMessageDialog(null, "Hello there! I am Stefilica Samburica.", "Conversation", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "I am a doctor in Mini Monsters biology.", "Conversation", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "I see you are a starter trainer.", "Conversation", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Let me \"show\" you the ropes.", "Conversation", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "A starter trainer is supposed to catch three Mini monsters an train them so he could compete in Mini Monster tournaments where you fight other trainers for prizes, or just for fun ;p.", "Conversation", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Unfortunately now tournaments aren't kept anymore ever since the cruel Gargamel became the leader of our town and canceled them because he thought he was the best Mini Monster trainer and no one could surpass him.", "Conversation", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Please young trainer, defeat him so we can end his age of tyranny and get back to having fun in this sacred sport.", "Conversation", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "You can find wild Mini Monsters around this town still, or even fossils which you can bring back to life, or so I've heard.", "Conversation", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Young trainer, you must make an unbeatable team and defeat Gargamel.", "Conversation", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "Here let me help you in this journey.", "Conversation", JOptionPane.INFORMATION_MESSAGE);
                JOptionPane.showMessageDialog(null, "I will give you your first Mini Monster, choose one of these three.", "Conversation", JOptionPane.INFORMATION_MESSAGE);

                gamePanel.keyActions.actionEP = false;
            }
        }
    }

}
