import javafx.scene.input.MouseEvent;

/**
 * @author P.Pridorozhny
 */
public class FindPosition {

    private Login login;
    private double x = 10;
    private double y = 10;
    private boolean pointConfirmed;

    FindPosition(Login login) {
        this.login = login;
    }

    String detectPosition() {

        StringBuilder result = new StringBuilder();

        double deltaX = login.searchingLine.getEndX() - login.circle.getCenterX();
        double deltaY = login.searchingLine.getEndY() - login.circle.getCenterY();
        double deltaLength = Math.sqrt(Math.pow(deltaX, 2) + Math.pow(deltaY, 2));
        double angle = login.searchAngle - login.angleF;

        if (angle >= 355 && angle <= 360 || angle >= 0 && angle <= 5) {
            result.append("Объект находится прямо ");
        } else if (angle > 5 && angle < 85) {
            result.append("Объект находится впереди справа ");
        } else if (angle >= 85 && angle <= 95) {
            result.append("Объект находится справа ");
        } else if (angle > 95 && angle < 175) {
            result.append("Объект находится сзади справа ");
        } else if (angle >= 175 && angle <= 185) {
            result.append("Объект находится сзади ");
        } else if (angle > 185 && angle < 265) {
            result.append("Объект находится сзади слева ");
        } else if (angle >= 265 && angle <= 275) {
            result.append("Объект находится слева ");
        } else if (angle > 275 && angle < 355) {
            result.append("Объект находится впереди слева ");
        } else {
            System.out.println();
            System.out.println("Something going wrong in findPosition.decectPosition angle");
        }

        if (deltaLength < 50) {
            result.append("впритык");
        } else if (deltaLength > 50 && deltaLength < 100) {
            result.append("очень близко");
        } else if (deltaLength > 100 && deltaLength < 200) {
            result.append("близко");
        } else if (deltaLength > 200 && deltaLength < 450) {
            result.append("на среднем расстоянии");
        } else if (deltaLength > 450 && deltaLength < 700) {
            result.append("далеко");
        } else if (deltaLength > 800) {
            result.append("очень далеко");
        }

        return result.toString();
    }

    void setPoint(MouseEvent e) {

        x = e.getSceneX();
        y = e.getSceneY();

        pointConfirmed = true;
    }

    void resetPoint() {
        pointConfirmed = false;
    }

    public double getX() {
        return x;
    }

    public double getY() {
        return y;
    }

    public boolean isPointConfirmed() {
        return pointConfirmed;
    }
}
