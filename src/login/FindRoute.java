import javafx.application.Platform;
import javafx.concurrent.Task;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.shape.Shape;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import org.apache.log4j.BasicConfigurator;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.LinkedList;

import static java.lang.Math.*;

class FindRoute {

    private static FindRoute findRoute;
    private Login login;
    private final Logger logger;
    //private LinkedList<Rectangle> traces;

    private double Ax;
    private double Ay;
    private double Bx;
    private double By;

    private int pointsCounter;
    private boolean pointConfirmedA;
    private boolean pointConfirmedB;
    private boolean collision;

    private ArrayList<Line> lines = new ArrayList<>();

    private double collisionAx;
    private double collisionBx;
    private double collisionAy;
    private double collisionBy;

    private boolean going = true;
    private Circle trace;
    private Rotate rotate;
    //private double rectangleWidth;
    private double rectangleHeight;
    private double circleWidth;

    private boolean first = true;
    private int caseM;

    private FindRoute(Login login) {
        this.login = login;
        //rectangleWidth = login.rectangle.getWidth();
        rectangleHeight = login.rectangle.getHeight();
        circleWidth = 2 * login.circle.getRadius();
        logger = LoggerFactory.getLogger(this.getClass());
        //traces = new LinkedList<>();
    }

    static FindRoute getInstance(Login login) {

        if (findRoute == null)
            findRoute = new FindRoute(login);

        return findRoute;

    }

    void setPoints(MouseEvent e, boolean trig) {

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

        Line instance = new Line(Ax, Ay, Bx, By);
        //instance.setStrokeDashOffset(0);
        //instance.stroke
        instance.setStrokeWidth(1);
        instance.setStroke(Color.RED);

        lines.add(instance);

        login.root.getChildren().add(instance);

        resetPoints();
        toFront();

    }

    boolean checkCollisions(Shape shape) {

        for (Line line : lines) {
            collision = intersection(shape, line) || line.intersects(shape.getBoundsInLocal());

            if (collision) {
                //System.out.println(login.circle.getCenterX() + " " + login.circle.getCenterY());
//                System.out.println(lines.size());
//                System.out.println(line.getStartX() + " " + line.getEndX());
//                System.out.println(line.getStartY() + " " + line.getEndY());
                break;
            }
        }

        return !collision;
    }

    boolean intersection(Shape shape1, Shape shape2) {

        boolean collisionDetected = false;

        Shape intersect = Shape.intersect(shape1, shape2);
        if (intersect.getBoundsInLocal().getWidth() != -1) {
            collisionDetected = true;
        }

        return collisionDetected;
    }


    double getAx() {
        return Ax;
    }

    double getAy() {
        return Ay;
    }

    double getBx() {
        return Bx;
    }

    double getBy() {
        return By;
    }


    boolean pointConfirmedA() {
        return pointConfirmedA;
    }

    boolean pointConfirmedB() {
        return pointConfirmedB;
    }


//    public double getCollisionAx() {
//        return collisionAx;
//    }
//
//    public void setCollisionAx(double collisionAx) {
//        this.collisionAx = collisionAx;
//    }
//
//    public double getCollisionBx() {
//        return collisionBx;
//    }
//
//    public void setCollisionBx(double collisionBx) {
//        this.collisionBx = collisionBx;
//    }
//
//    public double getCollisionAy() {
//        return collisionAy;
//    }
//
//    public void setCollisionAy(double collisionAy) {
//        this.collisionAy = collisionAy;
//    }
//
//    public double getCollisionBy() {
//        return collisionBy;
//    }
//
//    public void setCollisionBy(double collisionBy) {
//        this.collisionBy = collisionBy;
//    }

    void resetPoints() {
        pointConfirmedA = false;
        pointConfirmedB = false;
    }

    private double countAngle() {

        double angle;

        angle = toDegrees(atan((By - login.circle.getCenterY()) /
                (Bx - login.circle.getCenterX())));

        if ((Bx < login.circle.getCenterX())/* && (By > login.circle.getCenterY())*/) {
            angle += 180;
        }

        return angle;
    }

    private void strategy(Text actiontarget) {

        if (first) {

            login.angleF = findRoute.countAngle();
            login.rotateRect();
            logger.debug("in first case");
            if (!moving(actiontarget, Direction.DEFAULT)) {
                first = false;
                caseM = 1;
            }

        } else {

            switch (caseM) {
                case 1:
                    forward(actiontarget);
                    break;
                case 2:
                    right(actiontarget);
                    break;
                case 3:
                    back(actiontarget);
                    break;
                case 4:
                    left(actiontarget);
                    break;
                default:
                    assert false : "watta hell in strategy";
            }

        }

    }

    private void forward(Text actiontarget) {
        logger.debug("forward method");
        boolean trigger;

        trigger = moving(actiontarget, Direction.FORWARD);

        if (!trigger) {
            trigger = moving(actiontarget, Direction.RIGHT);
        }

        logger.debug(String.valueOf(trigger));

        if (trigger) {
            first = true;
        }
        else
            caseM = 2;
    }

    private void right(Text actiontarget) {
        logger.debug("right method");
        boolean trigger;
        boolean triggerRight = false;

        trigger = moving(actiontarget, Direction.BACK);
        //logger.debug(String.valueOf(trigger));
        try {
            Thread.sleep(400);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        if (trigger)
            triggerRight = moving(actiontarget, Direction.RIGHT);

        if (!trigger) {
            caseM = 3;
            return;
        }

        if (triggerRight)
            first = true;
    }

    private void back(Text actiontarget) {
        logger.debug("back method");
        boolean trigger;
        boolean triggerBack = false;

        trigger = moving(actiontarget, Direction.LEFT);

        if (!trigger)
            triggerBack = moving(actiontarget, Direction.BACK);

        if (!triggerBack && trigger) {
            return;
        }

        if (trigger)
            first = true;
        else
            caseM = 4;
    }

    private void left(Text actiontarget) {
        logger.debug("left method");
        boolean trigger;
        boolean triggerForward = false;

        trigger = moving(actiontarget, Direction.LEFT);

        if (!trigger)
            triggerForward = moving(actiontarget, Direction.FORWARD);

        if (triggerForward)
            return;
        if (!trigger && !triggerForward)
            first = true;
    }

    boolean moving(Text actiontarget, Direction direction) {

        double dx = 0;
        double dy = 0;
        double prevX = login.beginX;
        double prevY = login.beginY;
        boolean trigger;


        switch (direction) {
            case FORWARD:

                login.angleF = 0;
                dx = circleWidth;
                dy = 0;

                break;
            case BACK:

                login.rotate.setAngle(180);
                System.out.println("login.angleF = " + login.angleF);
                dx = -circleWidth;
                dy = 0;

                break;
            case RIGHT:

                login.angleF = 90;
                dx = 0;
                dy = rectangleHeight;

                break;
            case LEFT:

                login.angleF = 270;
                dx = 0;
                dy = -rectangleHeight;

                break;
            case DEFAULT:

                //logger.debug("Default case");
                dx = circleWidth * cos(toRadians(login.angleF));
                dy = circleWidth * sin(toRadians(login.angleF));

                break;
            default:
                assert false : "moving default";
        }

//            System.out.println("angleF = " + angleF);
//            System.out.println("sin = " + sin(toRadians(angleF)));
//            System.out.println("cos = " + cos(toRadians(angleF)));
//            System.out.println("dx = " + dx);
//            System.out.println("dy = " + dy);

        login.beginX += dx;
        login.beginY += dy;

        login.changeRobotPos(dx, dy);

        trigger = login.canMove(prevX, prevY);
        if (trigger)

            actiontarget.setText("Moving");

        else {
            actiontarget.setText("Can`t move");
        }

        System.out.println(login.circle.getCenterX());
        System.out.println(login.circle.getCenterY());

        return trigger;
    }

    boolean goToPoint(final Text actiontarget) {


        Task<Void> tsk = new Task<Void>() {
            @Override
            protected Void call() throws Exception {
                BasicConfigurator.configure();
                //int counter = 3;
                while (!(login.rectangle.intersects(login.circleB.getBoundsInLocal())
                        || intersection(login.rectangle, login.circleB)
                        || findRoute.intersection(login.circleB, login.circle) ||
                        login.circle.intersects(login.circleB.getBoundsInLocal()))) {

                    try {
                        Thread.sleep(500);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }

                    login.resetRotateRect();
                    strategy(actiontarget);
                    login.rotateRect();

                    Platform.runLater(() -> {
                        trace = new Circle();
                        rotate = new Rotate();
                        rotate.setAngle(login.angleF);
                        rotate.setPivotX(login.rectangle.getX());
                        rotate.setPivotY(login.rectangle.getY() + login.rectangle.getHeight() / 2);

                        trace.setCenterX(login.rectangle.getX());
                        trace.setCenterY(login.rectangle.getY() + login.rectangle.getHeight() / 2);
                        trace.setRadius(login.rWidth / 2);

                        trace.setFill(Color.YELLOW);
                        trace.getTransforms().add(rotate);

                        //traces.add(trace);
                        login.root.getChildren().add(trace);
                        toFront();
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

//    private boolean intersectsWithTrace() {
//        for (Rectangle rect : traces) {
//            collision = intersection(login.rectangle, rect) ||
//                    rect.intersects(login.rectangle.getBoundsInLocal());
//
//            if (collision) {
//                //System.out.println(login.circle.getCenterX() + " " + login.circle.getCenterY());
////                System.out.println(lines.size());
////                System.out.println(line.getStartX() + " " + line.getEndX());
////                System.out.println(line.getStartY() + " " + line.getEndY());
//                break;
//            }
//        }
//
//        return collision;
//    }

    private void toFront() {

        for (Line line : lines)
            line.toFront();

        login.rectangle.toFront();
        login.circle.toFront();
        login.robotLine.toFront();
        login.circleA.toFront();
        login.circleB.toFront();

    }
}
