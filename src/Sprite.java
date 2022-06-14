import java.awt.*;

public abstract class Sprite {

    protected boolean up,down,right,left;
    private boolean visible;
    private boolean dying;
    private Image image;

    protected int x,y,dx,dy;


    public Sprite(){
        visible = true;
    }

    public void die(){
        visible = false;
    }

    public boolean isVisible(){
        return visible;
    }

    protected void setVisible(boolean visible1){
        this.visible = visible1;
    }

    public void setImage(Image img){
        this.image = img;
    }

    public Image getImage(){
        return image;
    }

    public void setX(int x1){
        this.x = x1;
    }

    public void setY(int y1){
        this.y = y1;
    }

    public int getX(){
        return this.x;
    }

    public int getY(){
        return this.y;
    }

    public void setDying(boolean dying1) {
        this.dying = dying1;
    }

    public boolean isDying() {
        return this.dying;
    }
}
