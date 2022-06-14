import javax.swing.*;
import java.awt.*;

public class Enemy extends Sprite{
    private int tick = 1;

    public Enemy(int x1, int y1){
        initEnemy(x1,y1);
    }

    private void initEnemy(int x1, int y1){
        Image img = new ImageIcon(this.getClass().getResource("enemy.png")).getImage();
        setImage(img);
        setX(x1);
        setY(y1);
        dy=5;
        dx=2;
    }

    public void changeDirection(){
        dx = -dx;
    }

    public void act(){
        if(tick == 5 || tick == 10 || tick == 15){
            x += dx;
        }
        if(tick == 20){
            x += dx;
            y += dy;
            tick = 1;
        }
        else{
            tick++;
        }
    }
}
