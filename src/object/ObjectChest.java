package object;

import javax.imageio.ImageIO;
import java.io.File;

public class ObjectChest extends SuperObject{
    public ObjectChest(){
        name = "Chest";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/chest.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }

        collision = true;
    }
}
