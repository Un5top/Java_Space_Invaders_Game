import javax.swing.*;
import java.awt.*;

public class Powerup extends Sprite{
    private int type;
    private int tick = 0;

    Powerup(int x1, int y1, int a1){
        initPowerup(x1, y1, a1);
    }

    private void initPowerup(int x1, int y1, int a1) {
        Image img = new ImageIcon(this.getClass().getResource("power" + a1 + ".png")).getImage();
        setImage(img);
        setX(x1);
        setY(y1);
        type = a1;
    }

    public void act(){
        tick++;
        if(tick==100){
            die();
        }
    }

    public int getTick(){
        return tick;
    }

    public int getType() {
        return type;
    }
}
