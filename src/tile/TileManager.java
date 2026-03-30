package tile;

import main.*;

import java.awt.*;
import java.io.BufferedReader;
import java.io.FileReader;
import java.io.InputStream;
import java.io.InputStreamReader;

public class TileManager {

    GamePanel gamePanel;
    public Tile[] tiles;
    public int mapTileNum[][];

    public TileManager(GamePanel gamePanel) {
        this.gamePanel = gamePanel;

        tiles = new Tile[20];
        mapTileNum = new int[gamePanel.maxWorldCol][gamePanel.maxWorldRow];

        getTileImage();
        loadMap("/maps/map01.txt");
    }

    public void getTileImage() {

        try {
            tiles[0] = new Tile();
            tiles[0].setImage("/tiles/grass.png");

            tiles[1] = new Tile();
            tiles[1].setImage("/tiles/wall.png");
            tiles[1].makeSolid();

            tiles[2] = new Tile();
            tiles[2].setImage("/tiles/water.png");
            tiles[2].makeSolid();

            tiles[3] = new Tile();
            tiles[3].setImage("/tiles/bushes.png");

            tiles[4] = new Tile();
            tiles[4].setImage("/tiles/grass-flowers.png");

            tiles[5] = new Tile();
            tiles[5].setImage("/tiles/house.png");

            tiles[6] = new Tile();
            tiles[6].setImage("/tiles/rock.png");
            tiles[6].makeSolid();

            tiles[7] = new Tile();
            tiles[7].setImage("/tiles/tree.png");
            tiles[7].makeSolid();

            tiles[8] = new Tile();
            tiles[8].setImage("/tiles/tree-apples.png");
            tiles[8].makeSolid();

            tiles[9] = new Tile();
            tiles[9].setImage("/tiles/dirt.png");

            tiles[10] = new Tile();
            tiles[10].setImage("/tiles/insideTheHouse/walls.png");
            tiles[10].makeSolid();

            tiles[11] = new Tile();
            tiles[11].setImage("/tiles/insideTheHouse/carpet.png");

            tiles[12] = new Tile();
            tiles[12].setImage("/tiles/insideTheHouse/stairs.png");

            tiles[13]  = new Tile();
            tiles[13].setImage("/tiles/insideTheHouse/doors.png");


        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void loadMap(String file) {
        try {
            InputStream is = getClass().getResourceAsStream(file);
            BufferedReader br = new BufferedReader(new InputStreamReader(is));

            int row = 0;

            while (row < gamePanel.maxWorldRow) {
                String line = br.readLine();

                if (line == null) break;

                String[] numbers = line.split(" ");

                for (int col = 0; col < gamePanel.maxWorldCol; col++) {
                    int num = Integer.parseInt(numbers[col]);
                    mapTileNum[col][row] = num;
                }

                row++;
            }

            br.close();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void drawTiles(Graphics2D grph2d) {
        int worldCol = 0;
        int worldRow = 0;

        while (worldRow < gamePanel.maxWorldRow) {
            while (worldCol < gamePanel.maxWorldCol) {
                int tileNum = mapTileNum[worldCol][worldRow];

                int worldX = worldCol * gamePanel.tileSize;
                int worldY = worldRow * gamePanel.tileSize;

                int screenX = worldX - gamePanel.player.worldX + gamePanel.player.screenX;
                int screenY = worldY - gamePanel.player.worldY + gamePanel.player.screenY;

                if (gamePanel.player.worldX < gamePanel.player.screenX) {
                    screenX = worldX;
                }

                if (gamePanel.player.worldY < gamePanel.player.screenY) {
                    screenY = worldY;
                }

                int rightOffset = gamePanel.screenWidth - gamePanel.player.screenX;
                if (rightOffset > gamePanel.worldWidth - gamePanel.player.worldX) {
                    screenX = gamePanel.screenWidth - (gamePanel.worldWidth - worldX);
                }

                int bottomOffset = gamePanel.screenHeight - gamePanel.player.screenY;
                if (bottomOffset > gamePanel.worldHeight - gamePanel.player.worldY) {
                    screenY = gamePanel.screenHeight - (gamePanel.worldHeight - worldY);
                }

                if (worldX + gamePanel.tileSize > gamePanel.player.worldX - gamePanel.player.screenX &&
                    worldX - gamePanel.tileSize < gamePanel.player.worldX + gamePanel.player.screenX &&
                    worldY + gamePanel.tileSize > gamePanel.player.worldY - gamePanel.player.screenY &&
                    worldY - gamePanel.tileSize < gamePanel.player.worldY + gamePanel.player.screenY) {

                    grph2d.drawImage(tiles[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
                } else if (gamePanel.player.worldX < gamePanel.player.screenX ||
                        gamePanel.player.worldY < gamePanel.player.screenY ||
                        rightOffset > gamePanel.worldWidth - gamePanel.player.worldX ||
                        bottomOffset > gamePanel.worldHeight - gamePanel.player.worldY) {

                    grph2d.drawImage(tiles[tileNum].image, screenX, screenY, gamePanel.tileSize, gamePanel.tileSize, null);
                }

                worldCol++;
            }
            worldCol = 0;
            worldRow++;
        }
    }
}
