import java.awt.*;
import java.awt.event.ActionEvent;
import java.awt.event.ActionListener;
import javax.swing.*;

public class SplashScreen extends JFrame implements ActionListener {

    JButton startButton, highScoreButton, exitButton;
    JLabel titleLabel;

    public SplashScreen() {
        setTitle("Pong Game");
        setSize(500, 400);
        setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        setLocationRelativeTo(null); // Center

        // Layout and panel
        JPanel panel = new JPanel();
        panel.setBackground(Color.BLACK);
        panel.setLayout(new BoxLayout(panel, BoxLayout.Y_AXIS));
        panel.setBorder(BorderFactory.createEmptyBorder(40, 50, 40, 50));

        // Title
        titleLabel = new JLabel("PONG GAME");
        titleLabel.setForeground(Color.WHITE);
        titleLabel.setFont(new Font("Consolas", Font.BOLD, 40));
        titleLabel.setAlignmentX(Component.CENTER_ALIGNMENT);
        panel.add(titleLabel);

        panel.add(Box.createRigidArea(new Dimension(0, 50)));

        // Buttons
        startButton = createButton("Start Game");
        highScoreButton = createButton("View High Score");
        exitButton = createButton("Exit");

        panel.add(startButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(highScoreButton);
        panel.add(Box.createRigidArea(new Dimension(0, 20)));
        panel.add(exitButton);

        add(panel);
        setVisible(true);
    }

    private JButton createButton(String text) {
        JButton button = new JButton(text);
        button.setAlignmentX(Component.CENTER_ALIGNMENT);
        button.setFont(new Font("Arial", Font.PLAIN, 20));
        button.setFocusPainted(false);
        button.setBackground(Color.DARK_GRAY);
        button.setForeground(Color.WHITE);
        button.addActionListener(this);
        return button;
    }

    @Override
    public void actionPerformed(ActionEvent e) {
        if (e.getSource() == startButton) {
            dispose(); // Close splash
            new GameFrame(); // Start game
        } else if (e.getSource() == highScoreButton) {
            int score = ScoreManager.loadHighScore();
            JOptionPane.showMessageDialog(this, "High Score: " + score, "High Score", JOptionPane.INFORMATION_MESSAGE);
        } else if (e.getSource() == exitButton) {
            System.exit(0);
        }
    }

    public static void main(String[] args) {
        new SplashScreen();
    }
}
