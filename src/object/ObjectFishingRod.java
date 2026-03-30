package object;

import javax.imageio.ImageIO;

public class ObjectFishingRod extends SuperObject{
    public ObjectFishingRod(){
        name = "FishingRod";

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/objects/fishing-rod.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
