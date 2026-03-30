package object;

import javax.imageio.ImageIO;

public class ObjectPickaxe extends SuperObject {
    public ObjectPickaxe(){
        name = "Pickaxe";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/pickaxe.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
