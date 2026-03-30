package object;

import javax.imageio.ImageIO;
import java.io.File;

public class ObjectKey extends SuperObject{
    public ObjectKey(){
        name = "Key";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/key.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
