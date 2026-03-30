package main;

import entity.Entity;
import object.SuperObject;

public class CollisinCheck {
    GamePanel gamePanel;

    public CollisinCheck (GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void checkTile (Entity entity) {
        int entityLeftWorldX = entity.worldX + entity.collisionBox.x;
        int entityRightWorldX = entity.worldX + entity.collisionBox.x + entity.collisionBox.width;
        int entityTopWorldY = entity.worldY + entity.collisionBox.y;
        int entityBottomWorldY = entity.worldY + entity.collisionBox.y + entity.collisionBox.height;

        int entityLeftCol = entityLeftWorldX / gamePanel.tileSize;
        int entityRightCol = entityRightWorldX / gamePanel.tileSize;
        int entityTopRow = entityTopWorldY / gamePanel.tileSize;
        int entityBottomRow = entityBottomWorldY / gamePanel.tileSize;

        int tile1, tile2;

        switch (entity.direction) {
            case "up" :
                entityTopRow = (entityTopWorldY - entity.speed) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tile2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                if (gamePanel.tileManager.tiles[tile1].collision || gamePanel.tileManager.tiles[tile2].collision) {
                    entity.collisionOn =  true;
                }
                break;
            case "down":
                entityBottomRow = (entityBottomWorldY + entity.speed) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                tile2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tile1].collision || gamePanel.tileManager.tiles[tile2].collision) {
                    entity.collisionOn =  true;
                }
                break;
            case "left":
                entityLeftCol = (entityLeftWorldX - entity.speed) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityTopRow];
                tile2 = gamePanel.tileManager.mapTileNum[entityLeftCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tile1].collision || gamePanel.tileManager.tiles[tile2].collision) {
                    entity.collisionOn =  true;
                }
                break;
            case "right":
                entityRightCol = (entityRightWorldX + entity.speed) / gamePanel.tileSize;
                tile1 = gamePanel.tileManager.mapTileNum[entityRightCol][entityTopRow];
                tile2 = gamePanel.tileManager.mapTileNum[entityRightCol][entityBottomRow];
                if (gamePanel.tileManager.tiles[tile1].collision || gamePanel.tileManager.tiles[tile2].collision) {
                    entity.collisionOn =  true;
                }
                break;
        }
    }

    public int checkObject(Entity entity, boolean player) {
        int index = 999;

        for (int i = 0; i < gamePanel.obj.length; i++) {
            if (gamePanel.obj[i] != null) {
                entity.collisionBox.x = entity.worldX + entity.collisionBox.x;
                entity.collisionBox.y = entity.worldY + entity.collisionBox.y;

                gamePanel.obj[i].collisionBox.x = gamePanel.obj[i].worldX + gamePanel.obj[i].collisionBox.x;
                gamePanel.obj[i].collisionBox.y = gamePanel.obj[i].worldY + gamePanel.obj[i].collisionBox.y;

                switch (entity.direction) {
                    case "up" :
                        entity.collisionBox.y -= entity.speed;
                        if (entity.collisionBox.intersects(gamePanel.obj[i].collisionBox)) {
                            if (gamePanel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                            System.out.println("te doare capu");
                        }
                        break;
                    case "down":
                        entity.collisionBox.y += entity.speed;
                        if (entity.collisionBox.intersects(gamePanel.obj[i].collisionBox)) {
                            if (gamePanel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                            System.out.println("te doare picioarele");
                        }
                        break;
                    case "left":
                        entity.collisionBox.x -= entity.speed;
                        if (entity.collisionBox.intersects(gamePanel.obj[i].collisionBox)) {
                            if (gamePanel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                            System.out.println("te doare la stanga");
                        }
                        break;
                    case "right":
                        entity.collisionBox.x += entity.speed;
                        if (entity.collisionBox.intersects(gamePanel.obj[i].collisionBox)) {
                            if (gamePanel.obj[i].collision) {
                                entity.collisionOn = true;
                            }
                            if (player) {
                                index = i;
                            }
                            System.out.println("te doare la dreapta");
                        }
                        break;
                }

                entity.collisionBox.x = entity.collisionBoxDefaultX;
                entity.collisionBox.y = entity.collisionBoxDefaultY;

                gamePanel.obj[i].collisionBox.x = gamePanel.obj[i].collisionBoxDefaultX;
                gamePanel.obj[i].collisionBox.y = gamePanel.obj[i].collisionBoxDefaultY;
            }
        }
        return index;
    }
}
