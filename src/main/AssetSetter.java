package main;

import object.*;

public class AssetSetter {

    GamePanel gamePanel;

    public AssetSetter(GamePanel gamePanel) {
        this.gamePanel = gamePanel;
    }

    public void setObjects() {

        for (int i = 0; i < gamePanel.obj.length; i++) {
            gamePanel.obj[i] = null;
        }

        if (gamePanel.currentMap.equals("house upstairs")) {
            gamePanel.obj[2] = new Stefilica();
            gamePanel.obj[2].worldX = 4 * gamePanel.tileSize;
            gamePanel.obj[2].worldY = 4 * gamePanel.tileSize;

            if (!gamePanel.player.keyPickedUp) {

                gamePanel.obj[0] = new ObjectKey();
                gamePanel.obj[0].worldX = 4 * gamePanel.tileSize;
                gamePanel.obj[0].worldY = 8 * gamePanel.tileSize;
            }
        }

        if (gamePanel.currentMap.equals("house downstairs")) {
            if (!gamePanel.player.openedChest) {
                gamePanel.obj[1] = new ObjectChest();
                gamePanel.obj[1].worldX = gamePanel.tileSize;
                gamePanel.obj[1].worldY = 10 * gamePanel.tileSize;
            } else if (!gamePanel.player.hasFihingRod) {
                gamePanel.obj[1] = new ObjectFishingRod();
                gamePanel.obj[1].worldX = 10 * gamePanel.tileSize;
                gamePanel.obj[1].worldY = 10 * gamePanel.tileSize;
            }
        }
    }
}
