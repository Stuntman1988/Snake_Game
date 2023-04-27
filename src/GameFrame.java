import javax.swing.*;
import java.awt.*;
import java.awt.event.WindowAdapter;
import java.awt.event.WindowEvent;

public class GameFrame extends JFrame {

    Color snakeHeadColor;
    Color snakeBodyColor;
    Dimension size = new Dimension(60,50);
    JDialog dialog = new JDialog();
    JPanel buttonPanel = new JPanel();
    JButton greenButton = new JButton();
    JButton magentaButton = new JButton();
    JButton blueButton = new JButton();
    JButton orangeButton = new JButton();
    Label text = new Label("Choose your color:");
    Label welcomeText = new Label("Welcome to Snake!");

    GameFrame() {
        dialog.setTitle("Snake");
        dialog.setModal(true);
        dialog.setLayout(new BorderLayout());

        text.setAlignment(Label.CENTER);
        text.setFont(new Font("Verdana", Font.PLAIN, 15));
        text.setBackground(Color.GRAY);

        welcomeText.setAlignment(Label.CENTER);
        welcomeText.setFont(new Font("Verdana", Font.BOLD, 20));
        welcomeText.setBackground(Color.GRAY);

        buttonPanel.setBackground(Color.GRAY);


        greenButton.setBackground(Color.GREEN);
        greenButton.setPreferredSize(size);
        greenButton.addActionListener(e -> {
            snakeHeadColor = Color.green;
            snakeBodyColor = new Color(45,180,0);
            dialog.dispose();
        });

        magentaButton.setBackground(Color.MAGENTA);
        magentaButton.setPreferredSize(size);
        magentaButton.addActionListener(e -> {
            snakeHeadColor = Color.magenta;
            snakeBodyColor = new Color(199, 0, 199);
            dialog.dispose();
        });

        blueButton.setBackground(Color.BLUE);
        blueButton.setPreferredSize(size);
        blueButton.addActionListener(e -> {
            snakeHeadColor = Color.blue;
            snakeBodyColor = new Color(0, 0, 199);
            dialog.dispose();
        });

        orangeButton.setBackground(Color.ORANGE);
        orangeButton.setPreferredSize(size);
        orangeButton.addActionListener(e -> {
            snakeHeadColor = Color.orange;
            snakeBodyColor = new Color(196, 154, 0);
            dialog.dispose();
        });

        buttonPanel.add(greenButton);
        buttonPanel.add(magentaButton);
        buttonPanel.add(blueButton);
        buttonPanel.add(orangeButton);

        dialog.addWindowListener(new WindowAdapter() {
            @Override
            public void windowClosing(WindowEvent e) {
                System.exit(0);
            }
        });

        dialog.add(welcomeText, BorderLayout.NORTH);
        dialog.add(text, BorderLayout.CENTER);
        dialog.add(buttonPanel, BorderLayout.SOUTH);
        dialog.pack();
        dialog.setLocationRelativeTo(null);
        dialog.setVisible(true);

        this.add(new GamePanel(this, snakeHeadColor, snakeBodyColor));
        this.setTitle("Snake");
        this.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        this.setResizable(false);
        this.pack();
        this.setVisible(true);
        this.setLocationRelativeTo(null);
    }
}
