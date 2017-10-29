import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;

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
    private boolean collision;

    ArrayList<Line> lines = new ArrayList<>();
    Line instance;

    private double collisionAx;
    private double collisionBx;
    private double collisionAy;
    private double collisionBy;

    private Shape intersect;
    boolean going = true;
    private Rectangle trace;
    private Rotate rotate;
    private double rectangleWidth;


    private FindRoute(Login login) {
        this.login = login;
        rectangleWidth = login.rectangle.getWidth();

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
        //instance.setStrokeDashOffset(0);
        //instance.stroke
        instance.setStrokeWidth(1);
        instance.setStroke(Color.RED);

        lines.add(instance);

        login.root.getChildren().add(instance);

        resetPoints();
        login.toFront();

    }

    boolean checkCollisions(Shape shape) {

        for (Line line : lines) {
            collision = intersection(shape, line);

            if (collision) {
                //System.out.println(login.circle.getCenterX() + " " + login.circle.getCenterY());
                System.out.println(lines.size());
                System.out.println(line.getStartX() + " " + line.getEndX());
                System.out.println(line.getStartY() + " " + line.getEndY());
                break;
            }
        }

        return !collision;
    }

    public boolean intersection(Shape shape1, Shape shape2) {

        boolean collisionDetected = false;

        intersect = Shape.intersect(shape1, shape2);
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            collisionDetected = true;
        }

        return collisionDetected;
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


    public double getCollisionAx() {
        return collisionAx;
    }

    public void setCollisionAx(double collisionAx) {
        this.collisionAx = collisionAx;
    }

    public double getCollisionBx() {
        return collisionBx;
    }

    public void setCollisionBx(double collisionBx) {
        this.collisionBx = collisionBx;
    }

    public double getCollisionAy() {
        return collisionAy;
    }

    public void setCollisionAy(double collisionAy) {
        this.collisionAy = collisionAy;
    }

    public double getCollisionBy() {
        return collisionBy;
    }

    public void setCollisionBy(double collisionBy) {
        this.collisionBy = collisionBy;
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

    private void strategy() {

    }

    void moving(Text actiontarget) {

        double dx;
        double dy;
        double prevX = login.beginX;
        double prevY = login.beginY;


        dx = rectangleWidth * cos(toRadians(login.angleF));
        dy = rectangleWidth * sin(toRadians(login.angleF));

//            System.out.println("angleF = " + angleF);
//            System.out.println("sin = " + sin(toRadians(angleF)));
//            System.out.println("cos = " + cos(toRadians(angleF)));
//            System.out.println("dx = " + dx);
//            System.out.println("dy = " + dy);

        login.beginX += dx;
        login.beginY += dy;

        login.changeRobotPos(dx, dy);

        if (login.canMove(prevX, prevY))

            actiontarget.setText("Moving");

        else {
            actiontarget.setText("Can`t move");
        }


    }

    // N-S-W-E 4 directions
    private void move4(int forward, int back, int right, int left) {

    }

    boolean goToPoint(final Text actiontarget) {
        //setCircles();
        //findRoute.resetPoints();


        login.angleF = findRoute.countAngle();
        login.rotateRect();
        /*if (!checkObstacleOnRoute(actiontarget)) {
            return false;
        }*/

        Task<Void> tsk = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                //int counter = 3;
                while (!login.rectangle.intersects(login.circleB.getBoundsInParent())) {

                    try {
                        Thread.sleep(1000);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    login.resetRotateRect();
                    moving(actiontarget);
                    login.rotateRect();

                    Platform.runLater(new Runnable() {
                        @Override
                        public void run() {
                            trace = new Rectangle();
                            rotate = new Rotate();
                            rotate.setAngle(login.angleF);
                            rotate.setPivotX(login.rectangle.getX());
                            rotate.setPivotY(login.rectangle.getY() + login.rectangle.getHeight() / 2);

                            trace.setX(login.beginX);
                            trace.setY(login.beginY);
                            trace.setWidth(2 * login.rWidth);
                            trace.setHeight(login.rWidth);
                            trace.setArcWidth(20);
                            trace.setArcHeight(20);

                            trace.setFill(Color.YELLOW);
                            trace.getTransforms().add(rotate);

                            login.root.getChildren().add(trace);
                            login.toFront();
                        }
                    });
                    //btn2.fire();
                    //System.out.println(counter);
                }
                going = false;
                return null;
            }
        };

        Thread temp = new Thread(tsk);
        temp.setDaemon(true);
        temp.start();

        while (going) ;

        return login.circle.intersects(login.circleB.getBoundsInLocal());
    }
}
