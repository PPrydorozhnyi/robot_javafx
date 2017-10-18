/**
 * Created by drake on 17/09/17.
 */
import javafx.scene.paint.Color;
import javafx.scene.shape.Rectangle;

import java.util.Random;

public class RandomObject {

    private Rectangle rectangle;
    private Random random;
    private int cWidth;
    private int width = 20;
    private boolean detected;

    private int detectedX;
    private int detectedY;

    public RandomObject(int cWidth) {

        rectangle = new Rectangle();
        rectangle.setFill(Color.YELLOW);
        random = new Random();

        rectangle.setWidth(width);
        rectangle.setHeight(width);

        this.cWidth = cWidth;
        detected = false;
    }

    public Rectangle spawn() {

        detected = false;

        rectangle.setX(random.nextInt(cWidth - 2 * width));
        rectangle.setY(random.nextInt(cWidth - 2 * width));

        /*rectangle.setX(300);
        rectangle.setY(200);*/

        return rectangle;
    }

    public boolean isDetected() {
        return detected;
    }

    public void setDetected(boolean detected) {
        this.detected = detected;
    }

    public int getDetectedX() {
        return detectedX;
    }

    public void setDetectedX(int detectedX) {
        this.detectedX = detectedX;
    }

    public int getDetectedY() {
        return detectedY;
    }

    public void setDetectedY(int detectedY) {
        this.detectedY = detectedY;
    }
}
