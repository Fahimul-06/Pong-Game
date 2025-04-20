import java.io.*;

public class ScoreManager {
    private static final String FILE = "highscore.dat";

    public static int loadHighScore() {
        try (BufferedReader reader = new BufferedReader(new FileReader(FILE))) {
            return Integer.parseInt(reader.readLine());
        } catch (Exception e) {
            return 0; // Default score
        }
    }

    public static void saveHighScore(int score) {
        try (BufferedWriter writer = new BufferedWriter(new FileWriter(FILE))) {
            writer.write(String.valueOf(score));
        } catch (IOException e) {
            e.printStackTrace();
        }
    }
}
