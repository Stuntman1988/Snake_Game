import javax.swing.*;
import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import java.awt.event.KeyAdapter;
import java.awt.event.KeyEvent;
import java.util.Random;

public class GamePanel extends JPanel implements ActionListener {

    static final int SCREEN_WIDTH = 600;
    static final int SCREEN_HEIGHT = 600;
    static final int UNIT_SIZE = 25;
    static final int GAME_UNITS = (SCREEN_WIDTH * SCREEN_HEIGHT) / UNIT_SIZE;
    static final int DELAY = 100;
    final int[] snakePosX = new int[GAME_UNITS];
    final int[] snakePosY = new int[GAME_UNITS];
    int bodyParts = 3;
    int applesEaten;
    int appleX;
    int appleY;
    char direction = 'R';
    boolean running = false;
    Timer timer;
    Random random;
    Window parent;
    Color snakeHeadColor;
    Color snakeBodyColor;

    GamePanel(Window parent, Color head, Color body) {
        random = new Random();
        this.setPreferredSize(new Dimension(SCREEN_WIDTH, SCREEN_HEIGHT));
        this.setBackground(Color.black);
        this.setFocusable(true);
        this.addKeyListener(new MyKeyAdapter());
        this.parent = parent;
        this.snakeHeadColor = head;
        this.snakeBodyColor = body;
        startGame();
    }

    public void startGame() {

        newApple();
        running = true;
        timer = new Timer(DELAY, this);
        timer.start();
    }

    public void paintComponent(Graphics g) {
        super.paintComponent(g);
        draw(g);
    }

    public void draw(Graphics g) {

        if (running) {

            //TODO: Optional, make grid lines
//            for (int i = 0; i < SCREEN_HEIGHT / UNIT_SIZE; i++) {
//                g.drawLine(i * UNIT_SIZE, 0, i * UNIT_SIZE, SCREEN_HEIGHT);
//                g.drawLine(0, i * UNIT_SIZE, SCREEN_WIDTH, i * UNIT_SIZE);
//            }

            g.setColor(Color.red);
            g.fillOval(appleX, appleY, UNIT_SIZE, UNIT_SIZE);

            for (int i = 0; i < bodyParts; i++) {
                if (i == 0) {
                    g.setColor(snakeHeadColor);
                    g.fillRect(snakePosX[i], snakePosY[i], UNIT_SIZE, UNIT_SIZE);
                } else {
                    g.setColor(snakeBodyColor);
                    g.fillRect(snakePosX[i], snakePosY[i], UNIT_SIZE, UNIT_SIZE);
                }
            }
            g.setColor(Color.red);
            g.setFont(new Font("Ink Free", Font.BOLD, 20));
            FontMetrics metrics = getFontMetrics(g.getFont());
            g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics.stringWidth("Score: " + applesEaten)) - 10, g.getFont().getSize());
        } else {
            gameOver(g);
        }
    }

    public void newApple() {
        System.out.println("Snake pos: X:" + snakePosX + " Y:" + snakePosY);
        appleX = random.nextInt(SCREEN_WIDTH / UNIT_SIZE) * UNIT_SIZE;
        appleY = random.nextInt(SCREEN_HEIGHT / UNIT_SIZE) * UNIT_SIZE;
        System.out.println("Apple pos: X:" + appleX + " Y:" + appleY);
    }

    public void move() {
        for (int i = bodyParts; i > 0; i--) {
            snakePosX[i] = snakePosX[i - 1];
            snakePosY[i] = snakePosY[i - 1];
        }

        switch (direction) {
            case 'U' -> snakePosY[0] = snakePosY[0] - UNIT_SIZE;
            case 'D' -> snakePosY[0] = snakePosY[0] + UNIT_SIZE;
            case 'L' -> snakePosX[0] = snakePosX[0] - UNIT_SIZE;
            case 'R' -> snakePosX[0] = snakePosX[0] + UNIT_SIZE;
        }
    }

    public void checkApple() {
        if ((snakePosX[0] == appleX) && (snakePosY[0] == appleY)) {
            bodyParts++;
            applesEaten++;
            newApple();
        }
    }

    public void checkCollisions() {
        //This checks if head collides with body
        for (int i = bodyParts; i > 0; i--) {
            if ((snakePosX[0] == snakePosX[i]) && (snakePosY[0] == snakePosY[i])) {
                running = false;
                break;
            }
        }
        //Check if head touches left border
        if (snakePosX[0] < 0) {
            running = false;
        }
        //Check if head touches right border
        if (snakePosX[0] > SCREEN_WIDTH) {
            running = false;
        }
        //Check if head touches top border
        if (snakePosY[0] < 0) {
            running = false;
        }
        //Check if head touches bottom border
        if (snakePosY[0] > SCREEN_HEIGHT) {
            running = false;
        }

        if (!running) {
            timer.stop();
        }
    }

    public void gameOver(Graphics g) {
        //Score
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 40));
        FontMetrics metrics1 = getFontMetrics(g.getFont());
        g.drawString("Score: " + applesEaten, (SCREEN_WIDTH - metrics1.stringWidth("Score: " + applesEaten)) / 2, g.getFont().getSize());

        //Game Over
        g.setColor(Color.red);
        g.setFont(new Font("Ink Free", Font.BOLD, 75));
        FontMetrics metrics2 = getFontMetrics(g.getFont());
        g.drawString("Game Over", (SCREEN_WIDTH - metrics2.stringWidth("Game Over")) / 2, SCREEN_HEIGHT / 2);

        //Try Again button
        JButton tryAgainButton = new JButton("Try Again");
        tryAgainButton.setForeground(Color.BLACK);
        tryAgainButton.setBackground(Color.RED);
        tryAgainButton.addActionListener(e -> {
            parent.dispose();
            new GameFrame();
        });
        tryAgainButton.setBounds((SCREEN_WIDTH - 100) / 2, SCREEN_HEIGHT - 100, 100, 50);
        add(tryAgainButton);
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (running) {
            move();
            checkApple();
            checkCollisions();
        }
        repaint();
    }

    public class MyKeyAdapter extends KeyAdapter {
        @Override
        public void keyPressed(KeyEvent e) {
            switch (e.getKeyCode()) {
                case KeyEvent.VK_LEFT:
                    if (direction != 'R') {
                        direction = 'L';
                        break;
                    }
                case KeyEvent.VK_RIGHT:
                    if (direction != 'L') {
                        direction = 'R';
                        break;
                    }
                case KeyEvent.VK_UP:
                    if (direction != 'D') {
                        direction = 'U';
                        break;
                    }
                case KeyEvent.VK_DOWN:
                    if (direction != 'U') {
                        direction = 'D';
                        break;
                    }
            }
        }
    }
}
