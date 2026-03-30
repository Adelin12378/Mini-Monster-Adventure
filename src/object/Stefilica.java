package object;

import javax.imageio.ImageIO;

public class Stefilica extends SuperObject{
    public Stefilica(){
        name = "Stefilica";
        isNPC = true;

        try {
            image = ImageIO.read(getClass().getResourceAsStream("/tiles/insideTheHouse/stefilica.png"));
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
