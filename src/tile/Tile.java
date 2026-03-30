package tile;

import javax.imageio.ImageIO;
import java.awt.image.*;
import java.io.File;

public class Tile {

    public BufferedImage image;
    public boolean collision = false;

    public void setImage(String path) {
        try {
            image = ImageIO.read(getClass().getResourceAsStream(path));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    public void makeSolid() {
        collision = true;
    }

}
