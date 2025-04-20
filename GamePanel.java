import java.awt.*;
import java.awt.event.*;
import java.util.*;
import javax.swing.*;

public class GamePanel extends JPanel implements Runnable {

    static final int GAME_WIDTH = 1000;
    static final int GAME_HEIGHT = (int)(GAME_WIDTH * (0.5555));
    static final Dimension SCREEN_SIZE = new Dimension(GAME_WIDTH, GAME_HEIGHT);
    static final int PADDLE_WIDTH = 25;
    static final int PADDLE_HEIGHT = 100;
    static final int BALL_DIAMETER = 20;

    Thread gameThread;
    Image image;
    Graphics graphics;
    Random random;
    Paddle paddle1;
    Paddle paddle2;
    Ball ball;
    Score score;

    GamePanel() {
        newPaddles();
        newBall();
        score = new Score(GAME_WIDTH, GAME_HEIGHT);
        this.setFocusable(true);
        this.addKeyListener(new AL());
        this.setPreferredSize(SCREEN_SIZE);

        gameThread = new Thread(this);
        gameThread.start();
    }

    public void newBall() {
        random = new Random();
        ball = new Ball(GAME_WIDTH/2 - BALL_DIAMETER/2, random.nextInt(GAME_HEIGHT - BALL_DIAMETER), BALL_DIAMETER, BALL_DIAMETER);
    }

    public void newPaddles() {
        paddle1 = new Paddle(0, (GAME_HEIGHT/2) - (PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 1);
        paddle2 = new Paddle(GAME_WIDTH - PADDLE_WIDTH, (GAME_HEIGHT/2) - (PADDLE_HEIGHT/2), PADDLE_WIDTH, PADDLE_HEIGHT, 2);
    }

    public void paint(Graphics g) {
        image = createImage(getWidth(), getHeight());
        graphics = image.getGraphics();
        draw(graphics);
        g.drawImage(image, 0, 0, this);
    }

    public void draw(Graphics g) {
        paddle1.draw(g);
        paddle2.draw(g);
        ball.draw(g);
        score.draw(g);
        if (powerUp != null) powerUp.draw(g);
    }

    public void move() {
        paddle1.move();
        paddle2.move();
        ball.move();
    }

    public void checkCollision() {
        // Ball and paddle collision
        if(ball.intersects(paddle1)) {
            ball.xVelocity = Math.abs(ball.xVelocity);
            ball.setXDirection(ball.xVelocity);
        }
        if(ball.intersects(paddle2)) {
            ball.xVelocity = -Math.abs(ball.xVelocity);
            ball.setXDirection(ball.xVelocity);
        }

        // Bounce top and bottom
        if(ball.y <= 0 || ball.y >= GAME_HEIGHT - BALL_DIAMETER) {
            ball.setYDirection(-ball.yVelocity);
        }

        // Scoring
        if(ball.x <= 0) {
            score.player2++;
            newPaddles();
            newBall();
        }
        if(ball.x >= GAME_WIDTH - BALL_DIAMETER) {
            score.player1++;
            newPaddles();
            newBall();
        }

        // Prevent paddles from going out of bounds
        paddle1.checkBounds(GAME_HEIGHT);
        paddle2.checkBounds(GAME_HEIGHT);
        // Power-up generation every 10 seconds
        if (System.currentTimeMillis() - powerUpSpawnTime > 10_000 && powerUp == null) {
            powerUp = new PowerUp(GAME_WIDTH, GAME_HEIGHT);
            powerUpSpawnTime = System.currentTimeMillis();
        }

        // Ball collects power-up
        if (powerUp != null && ball.intersects(powerUp) && powerUp.active) {
            powerUp.deactivate();
            ball.xVelocity *= 2;
            ball.yVelocity *= 2;
            powerUpEffectActive = true;
            powerUpEffectEndTime = System.currentTimeMillis() + 5_000; // 5 sec effect
        }

        // Reset ball speed after effect
        if (powerUpEffectActive && System.currentTimeMillis() > powerUpEffectEndTime) {
            ball.xVelocity /= 2;
            ball.yVelocity /= 2;
            powerUpEffectActive = false;
            powerUp = null;
        }

    }


    boolean isPaused = false;

    public void run() {
        // Game loop
        long lastTime = System.nanoTime();
        double amountOfTicks = 60.0;
        double ns = 1_000_000_000 / amountOfTicks;
        double delta = 0;

        while(true) {
            long now = System.nanoTime();
            delta += (now - lastTime)/ns;
            lastTime = now;
            if (!isPaused && delta >= 1) {
            move();
            checkCollision();
            repaint();
            delta--;
            }
        }
    }

    PowerUp powerUp;
    long powerUpSpawnTime = System.currentTimeMillis();
    boolean powerUpEffectActive = false;
    long powerUpEffectEndTime;
    

    public class AL extends KeyAdapter {
        public void keyPressed(KeyEvent e) {
            if (e.getKeyCode() == KeyEvent.VK_P) {
               isPaused = !isPaused;
            }

            paddle1.keyPressed(e);
            paddle2.keyPressed(e);
        }

        public void keyReleased(KeyEvent e) {
            paddle1.keyReleased(e);
            paddle2.keyReleased(e);
        }
    }
}
