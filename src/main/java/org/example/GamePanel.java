package org.example;
import javax.swing.*;
import java.awt.*;
import java.awt.event.*;
import java.util.Random;

public class GamePanel extends JPanel  implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 30;
    static final int GAME_UNITS = (SCREEN_WIDTH*SCREEN_HEIGHT)/UNIT_SIZE;
    static final int DELAY = 60;
    final int[] x = new int[GAME_UNITS];
    final int[] y = new int[GAME_UNITS];
    int  bodyParts = 1;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    private final MusicPlayer musicPlayer;

    GamePanel(){
        random = new Random();
        musicPlayer = new MusicPlayer("src/main/resources/Clown_chosic.wav");
        this.setPreferredSize(new Dimension(SCREEN_WIDTH,SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        startGame();
    }

    public void startGame(){
        newApple();
        running = true;
        timer = new Timer(DELAY,this); //for how fast game is running change delay
        timer.start();
        musicPlayer.setLooping(true);
        musicPlayer.play();
    }

    public void paintComponent(Graphics graphics){
        super.paintComponent(graphics);
        draw(graphics);
    }

    public void draw(Graphics graphics){
        if(running){
            graphics.setColor(Color.red);
            graphics.fillOval(appleX,appleY,UNIT_SIZE, UNIT_SIZE);

            for(int bodyPart = 0; bodyPart< bodyParts;bodyPart++) {
                if (bodyPart == 0) {
                    graphics.setColor(Color.green);
                    graphics.fillRect(x[bodyPart], y[bodyPart], UNIT_SIZE, UNIT_SIZE);
                }
                else {
                    graphics.setColor(new Color(random.nextInt(255), random.nextInt(255), random.nextInt(255)));
                    graphics.fillRect(x[bodyPart], y[bodyPart], UNIT_SIZE, UNIT_SIZE);
                }
            }
            graphics.setColor(Color.white);
            graphics.setFont( new Font("Times New Roman",Font.ITALIC, 30));
            FontMetrics metrics = getFontMetrics(graphics.getFont());
            graphics.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: "+applesEaten))/2, graphics.getFont().getSize());
        }
        else{
            gameOver(graphics);
        }
    }

    public void newApple(){
        appleX = random.nextInt(SCREEN_WIDTH/UNIT_SIZE)*UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT/UNIT_SIZE)*UNIT_SIZE;
    }

    public void move() {
        for (int bodyPart = bodyParts; bodyPart > 0; bodyPart--) {
            x[bodyPart] = x[bodyPart - 1];
            y[bodyPart] = y[bodyPart - 1];
        }
        switch (direction) {
            case 'U':
                y[0] = y[0] - UNIT_SIZE;
                break;
            case 'D':
                y[0] = y[0] + UNIT_SIZE;
                break;
            case 'R':
                x[0] = x[0] + UNIT_SIZE;
                break;
            case 'L':
                x[0] = x[0] - UNIT_SIZE;
                break;
        }
    }

    public void checkApple(){
        if((x[0] == appleX) && (y[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions(){
        for(int bodyPart = bodyParts;bodyPart>0;bodyPart--) {
            if ((x[0] == x[bodyPart]) && (y[0] == y[bodyPart])) {
                running = false;
                break;
            }
        }
        if(x[0] < 0) {
            running = false;
        }
        if(x[0] >= SCREEN_WIDTH) {
            running = false;
        }
        if(y[0] < 0) {
            running = false;
        }
        if(y[0] > SCREEN_HEIGHT) {
            running = false;
        }
        if(!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics graphics){
        graphics.setColor(Color.white);
        graphics.setFont( new Font("Times New Roman",Font.ITALIC, 30));
        FontMetrics metrics1 = getFontMetrics(graphics.getFont());
        graphics.drawString("Score: "+applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: "+applesEaten))/2, graphics.getFont().getSize());

        graphics.setColor(Color.red);
        graphics.setFont( new Font("Times New Roman",Font.ITALIC, 60));
        FontMetrics metrics2 = getFontMetrics(graphics.getFont());
        graphics.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over"))/2, SCREEN_HEIGHT/2);

        musicPlayer.stop();
    }

    @Override
    public void actionPerformed(ActionEvent event){
        if(running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent keyEvent){
            switch(keyEvent.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if(direction != 'R') {
                        direction = 'L';
                    }
                    break;
                case KeyEvent.VK_RIGHT:
                    if(direction != 'L') {
                        direction = 'R';
                    }
                    break;
                case KeyEvent.VK_UP:
                    if(direction != 'D') {
                        direction = 'U';
                    }
                    break;
                case KeyEvent.VK_DOWN:
                    if(direction != 'U') {
                        direction = 'D';
                    }
                    break;
            }
        }
    }
}
