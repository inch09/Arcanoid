import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Arcanoid extends JPanel implements ActionListener, KeyListener {
    Random random;
    int vor = 0;
    int score = 0;
    boolean gameOver;

    int ballVelX = 5;
    int ballVelY = 5;

    int ballX = 100;
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
        setBackground(Color.pink);
        addKeyListener(this);
        setFocusable(true);

        random = new Random();

        gameStart = new Timer(10,this);
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
        g.setColor(Color.black);
        g.fillRect(CoordX, CoordY, 150 , 20);
        //brick
        g.setColor(Color.yellow);
        g.fillRect(brickX,brickY,100,10);
        //ball
        g.setColor(Color.black);
        g.fillOval(ballX, ballY ,20,20);
        if(gameOver){
            g.setColor(Color.red);
            g.fillRect(CoordX, CoordY, 150 , 20);
            g.setColor(Color.red);
            g.fillOval(ballX, ballY ,20,20);
        }
        if(vor==1){
            g.setColor(Color.black);
            g.fillRect(300, 0, 200 , 10);
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
        if((ballY<=10) &&  (ballX+10>=300 && ballX<=490)&& (vor==1)){
            vor = 0;
            score+=3;
        }
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
        if((ballY+20>= CoordY && ballY<=CoordY+10 ) && (ballX +10 >= CoordX && ballX<=CoordX+150) && ballVelY>0){
            ballVelY*=-1;
        }
        if((ballY+20>=brickY && ballY<=brickY+10 ) && (ballX + 10 >= brickX && ballX<=brickX+100)) {
            ballVelY*=-1;
            score++;
            placeBrick();
            if(vor != 1) {
                vor = vorota();
            }
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
    public int vorota(){
        Random randomnumb = new Random();
        int numb = randomnumb.nextInt(3);
        return numb;
    }
}