import java.awt.*;

public class Score extends Rectangle {

    static int GAME_WIDTH;
    static int GAME_HEIGHT;
    int player1;
    int player2;
    int highScore;

    Score(int width, int height) {
        Score.GAME_WIDTH = width;
        Score.GAME_HEIGHT = height;
    }


    public void draw(Graphics g) {
        g.setColor(Color.WHITE);
        g.setFont(new Font("Consolas", Font.BOLD, 50));
        g.drawLine(GAME_WIDTH/2, 0, GAME_WIDTH/2, GAME_HEIGHT);
        g.drawString(String.valueOf(player1), (GAME_WIDTH/2) - 85, 50);
        g.drawString(String.valueOf(player2), (GAME_WIDTH/2) + 40, 50);

        highScore = Math.max(ScoreManager.loadHighScore(), Math.max(player1, player2));
        if (Math.max(player1, player2) > ScoreManager.loadHighScore()) {
            ScoreManager.saveHighScore(Math.max(player1, player2));
        }

        g.setFont(new Font("Arial", Font.PLAIN, 20));
        g.drawString("High Score: " + highScore, (GAME_WIDTH/2) - 70, GAME_HEIGHT - 20);
    }

}
