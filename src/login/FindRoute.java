import javafx.scene.input.MouseEvent;
import static java.lang.Math.*;

public class FindRoute {

    private static FindRoute findRoute;
    private Login login;

    private double Ax;
    private double Ay;
    private double Bx;
    private double By;

    private int pointsCounter;
    private boolean pointConfirmedA;
    private boolean pointConfirmedB;

    private FindRoute() {


    }

    public static FindRoute getInstance() {

        if (findRoute == null)
            findRoute = new FindRoute();

        return findRoute;

    }

    public void setPoints(MouseEvent e) {

        ++pointsCounter;

        if (pointsCounter >= 2) pointsCounter %= 2;

        if (pointsCounter == 1) {
            Ax = e.getSceneX();
            Ay = e.getSceneY();
            pointConfirmedA = true;
        } else {
            Bx = e.getSceneX();
            By = e.getSceneY();
            pointConfirmedB = true;
        }

        System.out.println("Ax: " + Ax + " Ay: " + Ay);
        System.out.println("Bx: " + Bx + " By: " + By);

    }

    public double getAx() {
        return Ax;
    }

    public double getAy() {
        return Ay;
    }

    public double getBx() {
        return Bx;
    }

    public double getBy() {
        return By;
    }


    public int getPointsCounter() {
        return pointsCounter;
    }

    public boolean pointConfirmedA() {
        return pointConfirmedA;
    }

    public boolean pointConfirmedB() {
        return pointConfirmedB;
    }

    public void resetPoints() {
        pointConfirmedA = false;
        pointConfirmedB = false;
    }

    public double countAngle() {

        double angle = 0;

        angle = toDegrees(atan((By - Ay) / (Bx - Ax)));

        if (Bx < Ax) {
            angle += 180;
        }

        return angle;
    }
}
