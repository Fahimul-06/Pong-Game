import java.awt.*;
import java.util.Random;

public class PowerUp extends Rectangle {
    Color color = Color.YELLOW;
    boolean active = false;

    PowerUp(int width, int height) {
        super(new Random().nextInt(width - 30) + 15, new Random().nextInt(height - 30) + 15, 20, 20);
        this.active = true;
    }

    public void draw(Graphics g) {
        if (active) {
            g.setColor(color);
            g.fillRect(x, y, width, height);
        }
    }

    public void deactivate() {
        this.active = false;
    }
}
