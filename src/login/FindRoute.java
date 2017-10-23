import javafx.scene.input.MouseEvent;
import javafx.scene.shape.Line;

import java.util.ArrayList;

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

    ArrayList<Line> lines = new ArrayList<>();
    Line instance;

    private FindRoute(Login login) {
        this.login = login;
    }

    public static FindRoute getInstance(Login login) {

        if (findRoute == null)
            findRoute = new FindRoute(login);

        return findRoute;

    }

    public void setPoints(MouseEvent e, boolean trig) {

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

            if (trig) {
                createLine();
                System.out.println("wtf");
            }

        }

        System.out.println("Ax: " + Ax + " Ay: " + Ay);
        System.out.println("Bx: " + Bx + " By: " + By);


    }

    private void createLine() {

        instance = new Line(Ax, Ay, Bx, By);

        lines.add(instance);

        if (login == null)
            System.out.println("kurwa");
        if (login.root == null)
            System.out.println("how");
        login.root.getChildren().add(instance);

        resetPoints();
        login.toFront();

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
