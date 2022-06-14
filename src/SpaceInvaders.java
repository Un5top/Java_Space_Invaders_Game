import javax.swing.*;
import java.awt.*;


public class SpaceInvaders extends JFrame {

    public SpaceInvaders(){
        this.setTitle("Knockoff Space Invaders");
        this.setSize(Constants.boardW,Constants.boardH);
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.add(new Board());
        this.setLocationRelativeTo(null);
        this.setVisible(true);
    }

}
