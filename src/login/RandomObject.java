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

    public RandomObject(int cWidth) {

        rectangle = new Rectangle();
        rectangle.setFill(Color.YELLOW);
        random = new Random();

        rectangle.setWidth(width);
        rectangle.setHeight(width);

        this.cWidth = cWidth;
    }

    public Rectangle spawn() {

        rectangle.setX(random.nextInt(cWidth - 2 * width));
        rectangle.setY(random.nextInt(cWidth - 2 * width));

        return rectangle;
    }

}
