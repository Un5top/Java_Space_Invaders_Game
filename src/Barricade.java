import javax.swing.*;
import java.awt.*;

public class Barricade extends Sprite{

    private int damage = 0;
    private int tick = 1;

    public Barricade(int x1, int y1){
        initBarricade(x1,y1);
    }

    private void initBarricade(int x1, int y1) {
        Image img = new ImageIcon(this.getClass().getResource("barricade-full.png")).getImage();
        setImage(img);
        setX(x1);
        setY(y1);
        dy = 5;
        dx = 2;
        damage = 0;
    }

    public void changeDirection(){
        dx = -dx;
    }

    public void act(){
        if(tick ==5 || tick ==10 || tick ==15){
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

    public void updateDamage(){
        damage=1;
    }

    public int getDamage(){
        return damage;
    }

    public void updateImage() {
        Image img = new ImageIcon(this.getClass().getResource("barricade-damaged.png")).getImage();
        setImage(img);
    }
}
