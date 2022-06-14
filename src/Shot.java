import javax.swing.*;
import java.awt.*;

public class Shot extends Sprite{
    private double rotation;
    private double degree;
    private int velocity;
    private int tick=1;

    public Shot(){
        Image img = new ImageIcon("shot.png").getImage();
        setImage(img);
        velocity=8;
    }

    public Shot(int x1, int y1, double degree1, double rotation1){
        initShot(x1,y1, degree1, rotation1);
    }

    private void initShot(int x1, int y1, double degree1, double rotation1) {
        rotation=rotation1;
        degree=degree1;
        velocity=8;
        if(degree < 0 && degree > -90){
            degree=Math.toRadians(-90);
            dy=0;
            dx=-8;
        }
        else if(degree < 90 && degree > 0){
            degree = Math.toRadians(90);
            dy=0;
            dx=8;
        }
        else{
            dx = (int)(velocity * Math.sin(Math.toRadians(degree)));
            dy = (int)(velocity * Math.cos(Math.toRadians(degree)));
        }
        Image img = new ImageIcon(this.getClass().getResource("shot.png")).getImage();
        setImage(img);
        setX(x1 + 8);
        setY(y1 + 2);
    }

    public double getRotation(){
        return rotation;
    }

    public double getDegree(){
        return degree;
    }

    public void act(){
        y+=dy;
        x+=dx;
    }
}
