import javax.imageio.ImageIO;
import java.awt.event.KeyEvent;
import java.awt.image.BufferedImage;
import java.io.IOException;

public class Player extends Sprite {
    private BufferedImage playerB;
    {
        try {
            playerB = ImageIO.read(this.getClass().getResource("player.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    private boolean freeze = false;
    private int tick = 0;
    private int velocity;

    public Player(){
        initPlayer();
    }

    private void initPlayer(){
        velocity=200;
        this.up=false;
        this.left=false;
        this.right=false;
        this.down=false;
        setImage(playerB);
        int startX = Constants.boardW/2;
        int startY = Constants.boardH/2;
        this.setX(startX);
        this.setY(startY);
    }

    public void act(){
        if(tick>0){
            tick--;
        }

        if(tick==0) {
            freeze = false;
            try {
                playerB = ImageIO.read(this.getClass().getResource("player.png"));
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        if(!freeze){
            if(up){
                y+=-4;
            }
            if(down){
                y+=4;
            }
            if(left){
                x+=-4;
            }
            if(right){
                x+=4;
            }
        }
        if(x < 2){
            x = 2;
        }
        else if(x > 896){
            x = 896;
        }
        if(y < 200){
            y = 200;
        }
        else if(y > 500){
            y = 500;
        }
    }

    public void keyPressed(KeyEvent e){
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W){
            this.up = true;
        }
        else if(key == KeyEvent.VK_A){
            this.left = true;
        }
        else if(key == KeyEvent.VK_S){
            this.down = true;
        }
        else if(key == KeyEvent.VK_D){
            this.right = true;
        }
    }

    public void keyReleased(KeyEvent e){
        int key = e.getKeyCode();

        if(key == KeyEvent.VK_W){
            this.up = false;
        }
        else if(key == KeyEvent.VK_A){
            this.left = false;
        }
        else if(key == KeyEvent.VK_S){
            this.down = false;
        }
        else if(key == KeyEvent.VK_D){
            this.right = false;
        }
    }

    public void setFreeze() {
        try {
            playerB = ImageIO.read(this.getClass().getResource("player-frozen.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
        freeze = true;
        tick = 100;
    }

    @Override
    public BufferedImage getImage(){
        return playerB;
    }
}
