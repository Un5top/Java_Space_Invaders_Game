import javax.imageio.ImageIO;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.awt.geom.AffineTransform;
import java.awt.image.AffineTransformOp;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.ArrayList;
import java.util.Random;
import java.util.Scanner;
import java.util.List;


public class Board extends JPanel implements ActionListener{
    // Objects
    private BufferedImage shotB;
    {
        try {
            shotB = ImageIO.read(this.getClass().getResource("shot.png"));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
    private int tick = 0;
    private int powerupCount = 0;
    private int shotLimit = 5;
    private Random rand = new Random();
    private int index=0;
    private Color darkBlue = new Color(0,102,255);
    private double degree=90;
    private double rotation=0;
    private int degreeVis=90;
    private boolean space=false;
    private ShotDisplay shotDisplay;
    private boolean restart=false;
    private int cooldown = 0;
    private List<Enemy> enemies;
    private List<Powerup> powerups;
    private List<Barricade> barricades;
    private Player player;
    private List<Shot> shots;
    private int level = 0;
    private ImageIcon t = new ImageIcon(this.getClass().getResource("test1.png"));
    private ImageIcon y = new ImageIcon(this.getClass().getResource("invaders.png"));
    private ImageIcon g = new ImageIcon(this.getClass().getResource("gameover.png"));
    private ImageIcon w = new ImageIcon(this.getClass().getResource("youwin.png"));
    private ImageIcon l1 = new ImageIcon(this.getClass().getResource("level1.png"));
    private ImageIcon l2 = new ImageIcon(this.getClass().getResource("level2.png"));
    private ImageIcon l3 = new ImageIcon(this.getClass().getResource("level3.png"));
    private Image background = t.getImage();
    private Image title = y.getImage();
    private Image youWin = w.getImage();
    private Image gameOver = g.getImage();
    private Image level1 = l1.getImage();
    private Image level2 = l2.getImage();
    private Image level3 = l3.getImage();
    private int xValue = Constants.boardW/2;
    private int yValue = Constants.boardH/2;
    private int highScoreValue=0;
    private JButton play = new JButton("Play");
    private JButton exit = new JButton("Exit");
    private JLabel highScore = new JLabel();
    private JLabel score = new JLabel();
    private Timer timer;

    public Board(){
        this.setLayout(null);
        this.setFocusable(true);
        intro();
    }

    private void intro() {
        this.setLayout(null);
        this.setFocusable(true);
        try{
            String s="0";
            File HS = new File(System.getProperty("user.home") + "/" + "highscore.txt");
            if(!HS.createNewFile()){
                Scanner sc = new Scanner(HS);
                if(sc.hasNext()){
                    s = sc.next();
                }
            }
            highScore.setText("High Score: " + s);
            highScore.setFont(new Font("Serif", Font.BOLD, 32));
            highScore.setForeground(darkBlue);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Dimension size = play.getPreferredSize();
        size.setSize(size.getWidth()*2,size.getHeight()*2);
        play.setBounds(xValue - size.width/2 - size.width + 40,yValue - size.height/2, size.width, size.height);
        exit.setBounds(xValue - size.width/2 + size.width - 40,yValue - size.height/2, size.width, size.height);
        size = highScore.getPreferredSize();
        highScore.setBounds(xValue-size.width/2, yValue + 50, size.width + 140, size.height);
        play.addActionListener(this);
        exit.addActionListener(this);
        this.add(highScore);
        this.add(play);
        this.add(exit);
    }

    private void gameOver() {
        this.setLayout(null);
        this.setFocusable(true);
        try{
            String s="0";
            File HS = new File(System.getProperty("user.home") + "/" + "highscore.txt");
            if(!HS.createNewFile()){
                Scanner sc = new Scanner(HS);
                if(sc.hasNext()){
                    s = sc.next();
                }
            }
            highScore.setText("High Score: " + s);
            highScore.setFont(new Font("Serif", Font.BOLD, 32));
            highScore.setForeground(darkBlue);
            score.setText("Score: " + highScoreValue);
            score.setFont(new Font("Serif", Font.BOLD, 32));
            score.setForeground(darkBlue);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Dimension size = play.getPreferredSize();
        size.setSize(size.getWidth()*2,size.getHeight()*2);
        play.setBounds(xValue - size.width/2 - size.width + 40,yValue - size.height/2, size.width, size.height);
        exit.setBounds(xValue - size.width/2 + size.width - 40,yValue - size.height/2, size.width, size.height);
        size = highScore.getPreferredSize();
        highScore.setBounds(xValue-size.width/2, yValue + 50, size.width + 140, size.height);
        size = score.getPreferredSize();
        score.setBounds(xValue-size.width/2, yValue + 86, size.width + 140, size.height);
        play.addActionListener(this);
        exit.addActionListener(this);
        this.add(score);
        this.add(highScore);
        this.add(play);
        this.add(exit);
    }

    private void win() {
        this.removeAll();
        this.revalidate();
        try{
            String s="0";
            File HS = new File(System.getProperty("user.home") + "/" + "highscore.txt");
            if(!HS.createNewFile()){
                Scanner sc = new Scanner(HS);
                if(sc.hasNext()){
                    s = sc.next();
                }
            }
            if(Integer.parseInt(s)<highScoreValue){
                FileWriter fileWriter = new FileWriter(HS);
                PrintWriter printWriter = new PrintWriter(fileWriter);
                printWriter.print(highScoreValue);
                printWriter.close();
                highScore.setText("High Score: " + highScoreValue);
            }
            else if(Integer.parseInt(s)>=highScoreValue){
                highScore.setText("High Score: " + s);
                score.setText("Score: " + highScoreValue);
                score.setFont(new Font("Serif", Font.BOLD, 32));
                score.setForeground(darkBlue);
            }
            highScore.setFont(new Font("Serif", Font.BOLD, 32));
            highScore.setForeground(darkBlue);
        }
        catch (IOException e) {
            e.printStackTrace();
        }
        Dimension size = play.getPreferredSize();
        size.setSize(size.getWidth()*2,size.getHeight()*2);
        play.setBounds(xValue - size.width/2 - size.width + 40,yValue - size.height/2, size.width, size.height);
        exit.setBounds(xValue - size.width/2 + size.width - 40,yValue - size.height/2, size.width, size.height);
        size = highScore.getPreferredSize();
        highScore.setBounds(xValue-size.width/2, yValue + 50, size.width + 140, size.height);
        size = score.getPreferredSize();
        score.setBounds(xValue-size.width/2, yValue + 86, size.width + 140, size.height);
        play.addActionListener(this);
        exit.addActionListener(this);
        this.add(score);
        this.add(highScore);
        this.add(play);
        this.add(exit);
    }

    private void level1(){
        powerups = new ArrayList<>();
        enemies = new ArrayList<>();
        barricades = new ArrayList<>();
        shots = new ArrayList<>();
        player = new Player();
        shots.add(new Shot(0,0,degree,rotation));
        shots.get(0).setVisible(false);
        shotDisplay = new ShotDisplay(800,560);
        int gap = 0;
        for(int i=0;i<3;i++){
            for(int j=0;j<12;j++){
                if(i==2){
                    if(j % 4 == 0 && j!=0){
                        gap += 10;
                    }
                    barricades.add(new Barricade(20 + (32*j) + gap,20 + (32*i)));
                }
                else{
                    if(j % 4 == 0 && j!=0){
                        gap += 10;
                    }
                    enemies.add(new Enemy(20 + (32*j) + gap,20 + (32*i)));
                }
            }
            gap=0;
        }
    }


    private void level2(){
        this.removeAll();
        this.revalidate();
        enemies = new ArrayList<>();
        barricades = new ArrayList<>();
        shots = new ArrayList<>();
        player = new Player();
        shots.add(new Shot(0,0,degree,rotation));
        shots.get(0).setVisible(false);
        shotDisplay = new ShotDisplay(800,560);
        int gap = 0;
        for(int i=0;i<4;i++){
            for(int j=0;j<12;j++){
                if(i==3){
                    if(j % 4 == 0 && j!=0){
                        gap += 10;
                    }
                    barricades.add(new Barricade(20 + (Constants.barricadeW*j) + gap,20 + (Constants.barricadeH*i)));
                }
                else{
                    if(j % 4 == 0 && j!=0){
                        gap += 10;
                    }
                    enemies.add(new Enemy(20 + (Constants.enemyW*j) + gap,20 + (Constants.enemyH*i)));
                }
            }
            gap=0;
        }
    }

    private void level3(){
        this.removeAll();
        this.revalidate();
        enemies = new ArrayList<>();
        barricades = new ArrayList<>();
        shots = new ArrayList<>();
        player = new Player();
        shots.add(new Shot(0,0,degree,rotation));
        shots.get(0).setVisible(false);
        shotDisplay = new ShotDisplay(800,560);
        int gap = 0;
        for(int i=0;i<5;i++){
            for(int j=0;j<12;j++){
                if(i==3 || i==4){
                    if(j % 4 == 0 && j!=0){
                        gap += 10;
                    }
                    barricades.add(new Barricade(20 + (Constants.barricadeW*j) + gap,20 + (Constants.barricadeH*i)));
                }
                else{
                    if(j % 4 == 0 && j!=0){
                        gap += 10;
                    }
                    enemies.add(new Enemy(20 + (Constants.enemyW*j) + gap,20 + (Constants.enemyH*i)));
                }
            }
            gap=0;
        }
    }

    public void paintComponent(Graphics g){
        super.paintComponent(g);
        doDrawing(g);
    }

    private void doDrawing(Graphics g){
        if(level == 0){
            //Intro
            g.drawImage(background,0,0,this);
            if(!restart){
                g.drawImage(title,110,120,this);
            }
            else{
                g.drawImage(gameOver,85,120,this);
            }
        }
        else if(level==4){
            g.drawImage(background,0,0,this);
            g.drawImage(youWin,125,120,this);
        }
        else if(level == 1 || level==2 || level==3){
            //Level 1
            g.drawImage(background,0,0,this);
            drawPowerup(g);
            drawPlayer(g);
            drawEnemy(g);
            drawBarricade(g);
            drawShot(g);
            drawShotDisplay(g);
            g.setColor(darkBlue);
            g.setFont(new Font("Serif",Font.BOLD,16));
            g.drawString("Degree: " + String.valueOf(degreeVis),800,535);
            g.drawString("Highscore: " + String.valueOf(highScoreValue),800,555);
            g.setColor(Color.gray);
            if(level==1){
                g.drawImage(level1,780,10,this);
            }
            else if(level==2){
                g.drawImage(level2,780,10,this);
            }
            else if(level==3){
                g.drawImage(level3,780,10,this);
            }
        }
        Toolkit.getDefaultToolkit().sync();
    }

    private void setLevel(int l){
        level = l;
    }

    private void drawPlayer(Graphics g){
        double locationX = player.getImage().getWidth(this)/2;
        double locationY = player.getImage().getHeight(this)/2;
        AffineTransform tx = AffineTransform.getRotateInstance(rotation,locationX,locationY);
        AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
        Graphics2D g2d = (Graphics2D) g;
        g2d.drawImage(op.filter(player.getImage(),null),player.getX(),player.getY(),null);
    }

    private void drawEnemy(Graphics g){
        for(Enemy enemy : enemies){
            if(enemy.isVisible()){
                g.drawImage(enemy.getImage(),enemy.getX(),enemy.getY(),this);
            }
        }
    }

    private void drawBarricade(Graphics g){
        for(Barricade barricade : barricades){
            if(barricade.isVisible()){
                g.drawImage(barricade.getImage(),barricade.getX(),barricade.getY(),this);
            }
        }
    }

    private void drawShot(Graphics g){
        for(Shot shot : shots){
            if(shot.isVisible()){
                double locationX = shot.getImage().getWidth(this)/2;
                double locationY = shot.getImage().getHeight(this)/2;
                AffineTransform tx = AffineTransform.getRotateInstance(shot.getRotation(),locationX,locationY);
                AffineTransformOp op = new AffineTransformOp(tx,AffineTransformOp.TYPE_BILINEAR);
                Graphics2D g2d = (Graphics2D) g;
                g2d.drawImage(op.filter(shotB,null),shot.getX()-4,shot.getY()-2,null);
            }
        }
    }

    private void drawPowerup(Graphics g){
        for(Powerup powerup : powerups){
            if(powerup.isVisible()){
                g.drawImage(powerup.getImage(),powerup.getX(),powerup.getY(),this);
            }
        }
    }

    private void drawShotDisplay(Graphics g){
        g.drawImage(shotDisplay.getImage(),shotDisplay.getX(),shotDisplay.getY(),this);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if(e.getSource() == play){
            highScoreValue=0;
            this.removeAll();
            this.revalidate();
            setLevel(1);
            initBoard();
            level1();
        }
        else if(e.getSource() == exit){
            System.exit(0);
        }
    }

    private void initBoard() {
        if(level == 1){
            addKeyListener(new kAdapter());
            timer = new Timer(17,new GameCycle());
            if(!restart){
                timer.start();
            }
            setLevel(1);
            level1();
        }
    }

    private class kAdapter extends KeyAdapter{

        @Override
        public void keyReleased(KeyEvent e){
            player.keyReleased(e);
            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                space = false;
            }
        }

        @Override
        public void keyPressed(KeyEvent e){
            player.keyPressed(e);

            if(e.getKeyCode() == KeyEvent.VK_SPACE){
                space = true;
            }
        }
    }


    private void doGameCycle() {
        if(level==1 || level==2 || level==3){
            update();
            repaint();
        }
    }

    private void update() {
        if(shotLimit==15 && tick>0){
            tick--;
        }
        if(tick==0){
            shotLimit=5;
        }
        Point p = new Point(player.getX()+16, player.getY()+16);
        SwingUtilities.convertPointToScreen(p,this);
        degree = Math.toDegrees(Math.atan2(MouseInfo.getPointerInfo().getLocation().x - p.getX(),MouseInfo.getPointerInfo().getLocation().y - p.getY()));
        for(Barricade barricade : barricades){
            if(player.getX() >= barricade.getX() && player.getX() <= barricade.getX()+20 && player.getY() >= barricade.getY() && player.getY() <= barricade.getY()+20 && player.isVisible() && barricade.isVisible()){
                //Game Over Screen
                restart = true;
                setLevel(0);
                gameOver();
            }
        }
        for(Enemy enemy: enemies){
            if(player.getX() >= enemy.getX() && player.getX() <= enemy.getX()+32 && player.getY() >= enemy.getY() && player.getY() <= enemy.getY()+32 && player.isVisible() && enemy.isVisible()){
                restart = true;
                setLevel(0);
                gameOver();
            }
        }
        for(Shot shot: shots){
            for(Enemy enemy: enemies){
                if(shot.getX() >= enemy.getX() && shot.getX() <= enemy.getX()+32 && shot.getY() >= enemy.getY() && shot.getY() <= enemy.getY()+32 && shot.isVisible() && enemy.isVisible()){
                    enemy.die();
                    shot.die();
                    if(rand.nextInt(6)!=1 && powerupCount !=3){
                        powerups.add(new Powerup(rand.nextInt(600) + 100,rand.nextInt(201)+200,2));
                        powerupCount++;
                    }
                    else if(powerupCount != 3){
                        powerups.add(new Powerup(rand.nextInt(600) + 100,rand.nextInt(201)+200,1));
                        powerupCount++;
                    }
                    if(shotLimit==15){
                        highScoreValue = highScoreValue + 300;
                    }
                    else{
                        highScoreValue = highScoreValue + 200;
                    }
                }
            }
        }
        for(Shot shot: shots){
            for(Barricade barricade: barricades){
                if(shot.getX() >= barricade.getX() && shot.getX() <= barricade.getX()+32 && shot.getY() >= barricade.getY() && shot.getY() <= barricade.getY()+32 && shot.isVisible() && barricade.isVisible()){
                    if(barricade.getDamage()==0){
                        barricade.updateDamage();
                        barricade.updateImage();
                        shot.die();
                    }
                    else{
                        barricade.die();
                        shot.die();
                    }
                }
            }
        }
        for(Powerup powerup : powerups){
            if(player.getX() >= powerup.getX() && player.getX() <= powerup.getX()+32 && player.getY() >= powerup.getY() && player.getY() <= powerup.getY()+32 && player.isVisible() && powerup.isVisible()){
                if(powerup.getType()==2){
                    //bad powerup
                    highScoreValue = highScoreValue - 100;
                    player.setFreeze();
                    powerup.die();
                    shotLimit=5;
                }
                else{
                    //good powerup
                    if(shotLimit==5){
                        shotLimit = 15;
                        tick=200;
                    }
                    powerup.die();
                }
            }
        }

        if(degree < 0 && degree > -90){
            rotation = Math.toRadians(-90);
            degreeVis = 180;
        }
        else if(degree < 90 && degree >= 0){
            rotation = Math.toRadians(90);
            degreeVis = 0;
        }
        else{
            rotation = Math.toRadians(180-degree);
            if(degree < 0){
                degreeVis = 270 + (int)degree;
            }
            else{
                degreeVis = (int)degree -90;
            }
        }
        player.act();
        if(enemies.get(enemies.size()-1).getX() > 896 || enemies.get(0).getX() < 2){
            for(Enemy enemy: enemies){
                enemy.changeDirection();
            }
        }
        int deadEnemies=0;
        for(Enemy enemy : enemies){
            if(enemy.getY()==Constants.gameOverY){
                //Game Over Screen
                restart = true;
                setLevel(0);
                gameOver();
            }
            if(!enemy.isVisible()){
                deadEnemies++;
            }
            if(deadEnemies==getDeadEnemyRequirement()){
                if(level == 1){
                    highScoreValue = highScoreValue + 750;
                    setLevel(2);
                    level2();
                }
                else if(level == 2){
                    highScoreValue = highScoreValue + 750;
                    setLevel(3);
                    level3();
                }
                else if(level == 3){
                    highScoreValue = highScoreValue + 800;
                    restart=true;
                    setLevel(4);
                    win();
                }
            }
            enemy.act();
        }
        if(barricades.get(barricades.size()-1).getX() > 896 || barricades.get(0).getX() < 2){
            for(Barricade barricade : barricades){
                barricade.changeDirection();
            }
        }
        for(Barricade barricade : barricades){
            barricade.act();
        }
        int shotCount = 0;
        for(Shot shot : shots){
            if(shot.isVisible()){
                shotCount++;
            }
        }
        for(int i=0;i<6;i++){
            if (shotLimit==15 && shotCount==i){
                shotDisplay.setImage(new ImageIcon(this.getClass().getResource("shot-" + i + ".png")).getImage());
            }
            else if(shotLimit==5 && shotCount ==i){
                shotDisplay.setImage(new ImageIcon(this.getClass().getResource("shot-" + i + ".png")).getImage());
            }
        }
        powerupCount=0;
        for(Powerup powerup : powerups){
            if(powerup.isVisible()){
                powerupCount++;
            }
        }
        for(Powerup powerup : powerups){
            powerup.act();
        }

        for (Shot shot : shots){
            shot.act();
            if(shot.getY() < 0 || shot.getX() < 0 || shot.getX() > Constants.boardW){
                if(shot.isVisible()){
                    if(shotLimit!=15){
                        highScoreValue = highScoreValue - 50;
                    }
                    shot.die();
                }
            }
        }
        if(cooldown > 0){
            cooldown--;
        }
        if(space){
            int x = player.getX();
            int y = player.getY();

            if(cooldown==0 && shotCount !=shotLimit){
                if(shotLimit==15){
                    shots.add(new Shot(x,y,degree,rotation));
                    shots.add(new Shot(x-32,y,degree,rotation));
                    shots.add(new Shot(x+32,y,degree,rotation));
                }
                else{
                    shots.add(new Shot(x,y,degree,rotation));
                }
                cooldown = 10;
            }
        }
    }

    public int getDeadEnemyRequirement(){
        if(level == 1){
            return 24;
        }
        else if (level == 2){
            return 36;
        }
        else if (level==3){
            return 36;
        }
        else{
            return 999;
        }
    }

    private class GameCycle implements ActionListener {
        @Override
        public void actionPerformed(ActionEvent e){
            doGameCycle();
        }
    }
}
