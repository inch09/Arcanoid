import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Arcanoid extends JPanel implements ActionListener, KeyListener {
    Random random;
    int score = 0;
    boolean gameOver;

    int ballVelX = 5;
    int ballVelY = 5;

    int ballX = 390;
    int ballY = 100;

    int brickX;
    int brickY;

    int CoordX = 325;
    int CoordY = 700;
    public int VelocityX = 0;

    public class Platform{
        int Platformwidth;
        int Platformheight;

        Platform(int Platformwidth, int Platformheight){
            this.Platformwidth = Platformwidth;
            this.Platformheight = Platformheight;
        }
    }
    int boardWidth;
    int boardHeight;


    Platform platform;

    Timer gameStart;

    Arcanoid(int boardWidth, int boardHeight){
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(Color.black);
        addKeyListener(this);
        setFocusable(true);

        random = new Random();

        gameStart = new Timer(1,this);
        platform = new Platform(100,10);
        gameStart.start();


    }
    int countbricks = 0;
    public void placeBrick(){
        brickX = random.nextInt(600);
        brickY = random.nextInt(400);
        countbricks = 1;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }
    public void draw(Graphics g){
        // platform
        g.setColor(Color.white);
        g.fillRect(CoordX, CoordY, 150 , 20);
        //brick
        g.setColor(Color.green);
        g.fillRect(brickX,brickY,100,10);
        //ball
        g.setColor(Color.white);
        g.fillOval(ballX, ballY ,20,20);
        if(gameOver){
            g.setColor(Color.red);
            g.fillRect(CoordX, CoordY, 150 , 20);
            g.setColor(Color.red);
            g.fillOval(ballX, ballY ,20,20);
        }
    }
    public void move(){
        if(CoordX >= 650){
            VelocityX = 0;
            CoordX = 649;
        }
        else if(CoordX <= 0){
            VelocityX = 0;
            CoordX = 1;
        }
        CoordX+=VelocityX;
        if(ballX >= 780 || ballX <= 0) {
            ballVelX*=-1;
        }
        if(ballY <= 0) {
            ballVelY*=-1;
        }
        if(ballY >= 780){
            gameOver = true;
            System.out.println("Game Over");
            System.out.println("Score: "+ score);
        }
        if((ballY+20>= CoordY && ballY<=CoordY) && (ballX >= CoordX && ballX<=CoordX+150)){
            ballVelY*=-1;
        }
        if((ballY+20>=brickY && ballY<=brickY) && (ballX >= brickX && ballX<=brickX+100)) {
            ballVelY*=-1;
            score++;
            placeBrick();
        }
        ballX += ballVelX;
        ballY += ballVelY;
    }
    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            VelocityX = -5;
        }
        else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            VelocityX = 5;
        }
        // уменьшение или возрастание скорости мячика
        if(e.getKeyCode()==KeyEvent.VK_UP){
            ballVelY = (ballVelY/Math.abs(ballVelY))*(Math.abs(ballVelY)+1);
            ballVelX = (ballVelX/Math.abs(ballVelX))*(Math.abs(ballVelX)+1);
        }
        else if(e.getKeyCode()==KeyEvent.VK_DOWN && Math.abs(ballVelY)>1 ){
            ballVelY = (ballVelY/Math.abs(ballVelY))*(Math.abs(ballVelY)-1);
            ballVelX = (ballVelX/Math.abs(ballVelX))*(Math.abs(ballVelX)-1);

        }

    }
    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if(countbricks<1) {
            placeBrick();
        }
        repaint();
        if(gameOver){
            gameStart.stop();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }
}

