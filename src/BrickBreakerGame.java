import java.awt.*;
import java.awt.event.*;
import javax.swing.*;

public class BrickBreakerGame extends JPanel implements KeyListener {
    int paddleX = 180;
    int paddleWidth = 80;
    int paddleHeight = 10;

    int ballX = 200, ballY = 250, ballDX = 2, ballDY = -2;
    int ballSize = 20;
    boolean gameRunning = true;
    int score = 0;

    int[][] bricks = new int[5][7];
    int brickWidth = 50, brickHeight = 20;

    public BrickBreakerGame() {
        addKeyListener(this);
        setFocusable(true);
        Timer timer = new Timer(10, e -> updateGame());
        timer.start();
        initializeBricks();
    }

    private void initializeBricks() {
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                bricks[i][j] = 1;
            }
        }
    }

    public void updateGame() {
        if (!gameRunning) return;

        ballX += ballDX;
        ballY += ballDY;

        // Wall collision
        if (ballX <= 0 || ballX >= getWidth() - ballSize) ballDX *= -1;
        if (ballY <= 0) ballDY *= -1;

        // Paddle collision
        Rectangle paddle = new Rectangle(paddleX, getHeight() - 30, paddleWidth, paddleHeight);
        Rectangle ball = new Rectangle(ballX, ballY, ballSize, ballSize);
        if (ball.intersects(paddle)) {
            ballDY *= -1;
            ballY = getHeight() - 30 - ballSize; // prevent sticking
        }

        // Brick collision
        A: for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                if (bricks[i][j] == 1) {
                    int brickX = j * brickWidth + 20;
                    int brickY = i * brickHeight + 50;
                    Rectangle brickRect = new Rectangle(brickX, brickY, brickWidth, brickHeight);

                    if (ball.intersects(brickRect)) {
                        bricks[i][j] = 0;
                        score += 10;
                        if(score == 350){
                        gameRunning = false;
                        JOptionPane.showMessageDialog(this, "Congradulations, you won! Score: ");
                        }


                        // Determine direction of bounce based on side of impact
                        int ballCenterX = ballX + ballSize / 2;
                        int ballCenterY = ballY + ballSize / 2;

                        boolean hitFromLeftOrRight = 
                            ballCenterX < brickX || ballCenterX > brickX + brickWidth;
                        boolean hitFromTopOrBottom = 
                            ballCenterY < brickY || ballCenterY > brickY + brickHeight;

                        if (hitFromLeftOrRight) ballDX *= -1;
                        else if (hitFromTopOrBottom) ballDY *= -1;
                        else ballDY *= -1; // fallback

                        break A; // break out of both loops
                    }
                }
            }
        }

        // Lose condition
        if (ballY >= getHeight()) {
            gameRunning = false;
            System.out.println("Game Over! Final Score: " + score);
            JOptionPane.showMessageDialog(this, "Game Over! Score: " + score);        }

        repaint();
    }

    @Override
    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        g.setColor(Color.BLACK);
        g.fillRect(0, 0, getWidth(), getHeight());

        // Paddle
        g.setColor(Color.WHITE);
        g.fillRect(paddleX, getHeight() - 30, paddleWidth, paddleHeight);

        // Ball
        g.setColor(Color.BLUE);
        g.fillOval(ballX, ballY, ballSize, ballSize);

        // Bricks
        for (int i = 0; i < bricks.length; i++) {
            for (int j = 0; j < bricks[i].length; j++) {
                if (bricks[i][j] == 1) {
                    int brickX = j * brickWidth + 20;
                    int brickY = i * brickHeight + 50;
                    g.setColor(Color.RED);
                    g.fillRect(brickX, brickY, brickWidth, brickHeight);
                    g.setColor(Color.BLACK);
                    g.drawRect(brickX, brickY, brickWidth, brickHeight);
                }
            }
        }

        g.setColor(Color.WHITE);
        g.drawString("Score: " + score, 20, 20);
    }

    @Override
    public void keyPressed(KeyEvent e) {
        if (e.getKeyCode() == KeyEvent.VK_LEFT && paddleX > 0) paddleX -= 20;
        if (e.getKeyCode() == KeyEvent.VK_RIGHT && paddleX < getWidth() - paddleWidth) paddleX += 20;
        repaint();
    }

    @Override public void keyReleased(KeyEvent e) {}
    @Override public void keyTyped(KeyEvent e) {}

    public static void main(String[] args) {
        JFrame frame = new JFrame("Brick Breaker");
        BrickBreakerGame game = new BrickBreakerGame();
        frame.add(game);
        frame.setSize(500, 400);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.setVisible(true);
    }
}
