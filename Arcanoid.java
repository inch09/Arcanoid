import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyEvent;
import java.awt.event.KeyListener;
import java.util.Random;

public class Arcanoid extends JPanel implements ActionListener, KeyListener {
    Random random;
    int x = 0;
    int y = 1;
    int score = 0;
    int bonus = 10;
    boolean flazhoknabonus = false;
    int coordXbonus = 0;
    int coordYbonus = 0;
    int countLife = 1;
    int platformwidth = 150;
    int platformheight = 20;
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


    int boardWidth;
    int boardHeight;

    int countbricks = 0;
    Platform platform;

    Timer gameStart;
    public class Platform{
        int platformWidth;
        int platformHeight;

        Platform(int Platformwidth, int Platformheight){
            this.platformWidth = Platformwidth;
            this.platformHeight = Platformheight;
        }

        public int getPlatformWidth() {
            return platformWidth;
        }

        public void setPlatformWidth(int platformWidth) {
            this.platformWidth = platformWidth;
        }

        public int getPlatformHeight() {
            return platformHeight;
        }

        public void setPlatformHeight(int platformHeight) {
            this.platformHeight = platformHeight;
        }
    }

    Arcanoid(int boardWidth, int boardHeight) {
        this.boardWidth = boardWidth;
        this.boardHeight = boardHeight;
        setPreferredSize(new Dimension(this.boardWidth, this.boardHeight));
        setBackground(new Color(66, 167, 245));
        addKeyListener(this);
        setFocusable(true);
        JLabel centerLabel = new JLabel("Center");
        centerLabel.setText("213");

        random = new Random();

        gameStart = new Timer(10, this);
        platform = new Platform(100, 10);
        gameStart.start();


    }


    public void placeBrick() {
        brickX = random.nextInt(600);
        brickY = random.nextInt(400);
        countbricks = 1;
    }


    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {
        // platform
        g.setColor(new Color(150, 150, 80));
        g.fillRect(CoordX, CoordY, platformwidth, platformheight);
        //brick
        g.setColor(new Color(245, 144, 66));
        g.fillRect(brickX, brickY, 100, 10);
        //ball
        g.setColor(Color.black);
        g.fillOval(ballX, ballY, 20, 20);
        if (bonus <= 2) {
            if (y == 0) {// +5очков
                coordXbonus = random.nextInt(100, 400);
                coordYbonus = random.nextInt(100, 400);
                y = 1;
            }
            g.setColor(new Color(1, 2, 2));
            g.fillRect(coordXbonus, coordYbonus, 30, 30);
            flazhoknabonus = true;
        }
        if (bonus > 2 && bonus <= 4) { // допжизнь
            if (y == 0) {
                coordXbonus = random.nextInt(100, 400);
                coordYbonus = random.nextInt(100, 400);
                y = 1;
            }
            g.setColor(Color.green);
            g.fillRect(coordXbonus, coordYbonus, 30, 30);
            flazhoknabonus = true;
        }
        if (bonus > 4 && bonus <= 6) { // увеличение платформы
            if (y == 0) {
                coordXbonus = random.nextInt(100, 400);
                coordYbonus = random.nextInt(100, 400);
                y = 1;
            }
            g.setColor(Color.blue);
            g.fillRect(coordXbonus, coordYbonus, 30, 30);
            flazhoknabonus = true;
        }
        if (bonus > 6 && bonus <= 8) { // - жизнь
            if (y == 0) {
                coordXbonus = random.nextInt(100, 400);
                coordYbonus = random.nextInt(100, 400);
                y = 1;
            }
            g.setColor(new Color(245, 87, 66));
            g.fillRect(coordXbonus, coordYbonus, 30, 30);
            flazhoknabonus = true;
        }
        if (gameOver) {
            g.setColor(Color.red);
            g.fillRect(CoordX, CoordY, platformwidth, 20);
            g.setColor(Color.red);

            g.fillOval(ballX, ballY, 20, 20);
        }

    }

    public void move() {
        if (countLife <= 0) {
            gameOver = true;
        }
        if ((ballX >= (coordXbonus - 20) && ballX <= (coordXbonus + 30)) && (ballY >= (coordYbonus - 20) && ballY <= (coordYbonus + 30)) && flazhoknabonus == true) {
            if (bonus <= 2) {
                flazhoknabonus = false;
                bonus = 100;
                score += 5;
            }
            if (bonus >= 3 && bonus <= 4) {
                flazhoknabonus = false;
                bonus = 100;
                countLife++;
            }
            if (bonus >= 5 && bonus <= 6) {
                flazhoknabonus = false;
                bonus = 100;
                platformwidth += 30;
            }
            if (bonus >= 7 && bonus <= 8) {
                flazhoknabonus = false;
                bonus = 100;
                countLife--;

            }


        }
        if (CoordX >= 800 - platformwidth) {
            VelocityX = 0;
            CoordX = 799 - platformwidth;
        } else if (CoordX <= 0) {
            VelocityX = 0;
            CoordX = 1;
        }
        CoordX += VelocityX;
        if (ballX >= 780 || ballX <= 0) {
            ballVelX *= -1;
        }
        if (ballY <= 0) {
            ballVelY *= -1;
        }
        if (ballY >= 780) {
            countLife--;
            ballVelY *= -1;
            if (countLife == 0) {
                gameOver = true;
                System.out.println("Game Over");
                System.out.println("Score: " + score);
            }
        }
        if ((ballY + 20 >= CoordY && ballY <= CoordY + 10) && (ballX + 10 >= CoordX && ballX <= CoordX + platformwidth) && ballVelY > 0) {
            ballVelY *= -1;
        }
        if ((ballY + 20 >= brickY && ballY <= brickY + 10) && (ballX + 10 >= brickX && ballX <= brickX + 100)) {
            ballVelY *= -1;
            score++;
            placeBrick();
            if (flazhoknabonus == false) {
                y = 0;
                bonus();
            }
        }
        ballX += ballVelX;
        ballY += ballVelY;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT) {
            VelocityX = -5;
        } else if (e.getKeyCode() == KeyEvent.VK_RIGHT) {
            VelocityX = 5;
        }
        // уменьшение или возрастание скорости мячика
        if (e.getKeyCode() == KeyEvent.VK_UP) {
            ballVelY = (ballVelY / Math.abs(ballVelY)) * (Math.abs(ballVelY) + 1);
            ballVelX = (ballVelX / Math.abs(ballVelX)) * (Math.abs(ballVelX) + 1);
        } else if (e.getKeyCode() == KeyEvent.VK_DOWN && Math.abs(ballVelY) > 1) {
            ballVelY = (ballVelY / Math.abs(ballVelY)) * (Math.abs(ballVelY) - 1);
            ballVelX = (ballVelX / Math.abs(ballVelX)) * (Math.abs(ballVelX) - 1);

        }

    }

    @Override
    public void actionPerformed(ActionEvent e) {
        move();
        if (countbricks < 1) {
            placeBrick();
        }
        if (flazhoknabonus == true) {
            if (x == 500) {
                bonus = 10;
                flazhoknabonus = false;
            }
            x += 1;
        }
        repaint();

        if (gameOver) {
            gameStart.stop();
        }

    }

    @Override
    public void keyTyped(KeyEvent e) {

    }


    @Override
    public void keyReleased(KeyEvent e) {

    }

    public void bonus() {
        bonus = random.nextInt(1, 12);
    }
}
