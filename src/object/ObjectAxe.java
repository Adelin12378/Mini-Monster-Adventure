package object;

import javax.imageio.ImageIO;

public class ObjectAxe extends SuperObject{
    public ObjectAxe(){
        name = "Axe";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/axe.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
