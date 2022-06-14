import javax.swing.*;
import java.awt.*;

public class ShotDisplay extends Sprite{

    ShotDisplay(int x1, int y1){
        initShotDisplay(x1,y1);
    }

    private void initShotDisplay(int x1, int y1) {
        Image img = new ImageIcon(this.getClass().getResource("shot-0.png")).getImage();
        setImage(img);
        setX(x1);
        setY(y1);
    }

}
