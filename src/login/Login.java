import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
import javafx.concurrent.Task;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.paint.Color;
import javafx.scene.shape.Circle;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;
import javafx.util.Duration;

import static java.lang.Math.*;
import static javafx.geometry.HPos.RIGHT;

public class Login extends Application {

    private Stage primaryS;

    Group root;

    private int cWidth = 800;

    private TextField beginCoordX;
    private TextField beginCoordY;
    private TextField stepLength;
    private TextField robWidth;
    private TextField angleField;
    private TextField angle1Field;

    double beginX = 200;
    double beginY = 200;
    private int stLength = 0;
    int rWidth = 20;
    double angleF = 0;

    Rectangle rectangle = new Rectangle();
    private Rectangle background = new Rectangle(cWidth, cWidth);
    Circle circle = new Circle();
    private final Line[] lines = new Line[5];
    Rotate rotate = new Rotate();
    private RandomObject spawnObject = new RandomObject(cWidth);
    private Rectangle spawnRectangle;
    private Line searchingLine = new Line();
    Line robotLine;

    private FindRoute findRoute;
    private Button searchBtn;
    private Button obstacleBtn;
    Circle circleA = new Circle();
    Circle circleB = new Circle();

    @Override
    public void start(Stage primaryStage) {

        primaryS = primaryStage;

        primaryStage.setTitle("lab");
        primaryStage.setHeight(850);
        primaryStage.setWidth(1200);
        primaryStage.setResizable(false);
        GridPane grid = new GridPane();
        grid.setAlignment(Pos.TOP_RIGHT);
        grid.setHgap(10);
        grid.setVgap(10);
        grid.setPadding(new Insets(25, 25, 25, 25));

        Text scenetitle = new Text("Welcome");
        scenetitle.setFont(Font.font("Tahoma", FontWeight.NORMAL, 20));
        grid.add(scenetitle, 0, 0, 2, 1);

        makeLabels(grid);
        makeTextFields(grid);
        makeButtons(grid);

        setBackground();
        setRect();
        circle.setFill(Color.BROWN);
        rectangle.getTransforms().add(rotate);
        createLines();
        lines[4].getTransforms().add(rotate);


        drawScene(grid);

    }

    private void drawScene(GridPane grid) {

        root = new Group();

        root.getChildren().add(background);

        if (spawnRectangle != null) {
            //System.out.println("wtf");
            root.getChildren().add(spawnRectangle);
            //root.getChildren().add(searchingLine);
        }

        root.getChildren().add(searchingLine);
        searchingLine.setVisible(false);

        root.getChildren().add(rectangle);
        root.getChildren().add(circle);
        root.getChildren().add(circleA);
        root.getChildren().add(circleB);
        for (Line line : lines)
            root.getChildren().add(line);

        HBox hBox = new HBox(10, root, grid);

        primaryS.setScene(new Scene(hBox, 800, 600));
        primaryS.show();

    }

    private void setBackground() {

        background.setFill(Color.WHITE);

    }

    private void resetBackground() {
        background.setOnMouseClicked(null);
    }

    private void setRect() {

        rectangle.setX(beginX);
        rectangle.setY(beginY);
        rectangle.setWidth(2 * rWidth);
        rectangle.setHeight(rWidth);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);

        circle.setCenterX(rectangle.getX());
        circle.setCenterY(rectangle.getY() + rectangle.getHeight() / 2);
        circle.setRadius(rWidth / 2);


    }

    private void createLines() {

        lines[0] = new Line();
        lines[0].setStartX(0);
        lines[0].setStartY(0);
        lines[0].setEndX(0);
        lines[0].setEndY(cWidth);

        lines[1] = new Line();
        lines[1].setStartX(0);
        lines[1].setStartY(cWidth);
        lines[1].setEndX(cWidth);
        lines[1].setEndY(cWidth);

        lines[2] = new Line();
        lines[2].setStartX(cWidth);
        lines[2].setStartY(cWidth);
        lines[2].setEndX(cWidth);
        lines[2].setEndY(0);

        lines[3] = new Line();
        lines[3].setStartX(cWidth);
        lines[3].setStartY(0);
        lines[3].setEndX(0);
        lines[3].setEndY(0);

        lines[4] = new Line();
        robotLine = lines[4];
        lines[4].setStartX(rectangle.getX()/* + rectangle.getWidth() / 2*/);
        lines[4].setStartY(rectangle.getY() + rectangle.getHeight() / 2);
        lines[4].setEndX(rectangle.getX() + rectangle.getWidth() / 2 + rectangle.getWidth() + 40);
        lines[4].setEndY(rectangle.getY() + rectangle.getHeight() / 2);
        lines[4].setStroke(Color.CORAL);
    }

    private void makeLabels(GridPane grid) {
        Label userName = new Label("X0:");
        grid.add(userName, 0, 1);

        Label pw = new Label("Y0:");
        grid.add(pw, 0, 2);

        Label sl = new Label("Step Length:");
        grid.add(sl, 0, 3);

        Label rWidth = new Label("Robot`s width:");
        grid.add(rWidth, 0, 4);

        Label angl = new Label("Angle:");
        grid.add(angl, 0, 5);

        Label angl1 = new Label("Angle:");
        grid.add(angl1, 0, 8);

    }

    private void makeTextFields(GridPane grid) {

        beginCoordX = new TextField();
        grid.add(beginCoordX, 1, 1);

        beginCoordY = new TextField();
        grid.add(beginCoordY, 1, 2);

        stepLength = new TextField();
        grid.add(stepLength, 1, 3);

        robWidth = new TextField();
        grid.add(robWidth, 1, 4);

        angleField = new TextField();
        grid.add(angleField, 1, 5);

        angle1Field = new TextField();
        grid.add(angle1Field, 1, 8);

    }

    private void makeButtons(GridPane grid) {

        Button btn = new Button("Accept");
        HBox hbBtn = new HBox(10);
        hbBtn.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn.getChildren().add(btn);
        grid.add(hbBtn, 1, 6);

        final Text actiontarget = new Text();
        grid.add(actiontarget, 0, 7);
        GridPane.setColumnSpan(actiontarget, 2);
        GridPane.setHalignment(actiontarget, RIGHT);
        actiontarget.setId("actiontarget");

        btn.setOnAction(e -> {
            actiontarget.setFill(Color.FIREBRICK);
            actiontarget.setText("Processing");
            if (checkValidData()) {
                actiontarget.setText("Processing");
                //CHANGED
                setRect();
                setLinePos();
                rotateRect();
                if (spawnRectangle != null)
                    checkSearchCollisions();

            } else
                actiontarget.setText("Try again");

        });

        Button btn1 = new Button("Change angle");
        HBox hbBtn1 = new HBox(10);
        hbBtn1.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn1.getChildren().add(btn1);
        grid.add(hbBtn1, 1, 9);

        final Text actiontarget1 = new Text();
        grid.add(actiontarget1, 0, 10);
        GridPane.setColumnSpan(actiontarget1, 2);
        GridPane.setHalignment(actiontarget1, RIGHT);
        actiontarget1.setId("actiontarget1");

        btn1.setOnAction(e -> {
            actiontarget1.setFill(Color.FIREBRICK);
            actiontarget1.setText("Processing");
            //CHANGED
            //angleF = Integer.valueOf(angle1Field.getText());

            setAngleF();

            System.out.println(angleF);

            setLinePos();
            //drawScene(grid);
            rotateRect();
            if (spawnRectangle != null)
                checkSearchCollisions();
        });

        Button btn2 = new Button("Rotate");
        HBox hbBtn2 = new HBox(10);
        hbBtn2.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn2.getChildren().add(btn2);
        grid.add(hbBtn2, 1, 11);

        final Text actiontarget2 = new Text();
        grid.add(actiontarget2, 0, 12);
        GridPane.setColumnSpan(actiontarget2, 2);
        GridPane.setHalignment(actiontarget2, RIGHT);
        actiontarget2.setId("actiontarget2");

        //TODO : fix rotation logic
        btn2.setOnAction(e -> {

            if (findRoute == null)
                findRoute = FindRoute.getInstance(this);

            actiontarget2.setFill(Color.FIREBRICK);

            stLength = Integer.valueOf(stepLength.getText());

            actiontarget2.setText("Processing");


            resetRotateRect();
            findRoute.moving(actiontarget2, Direction.DEFAULT);
            rotateRect();
            if (spawnRectangle != null)
                checkSearchCollisions();

        });

        Button btn3 = new Button("Search");
        HBox hbBtn3 = new HBox(10);
        hbBtn3.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn3.getChildren().add(btn3);
        grid.add(hbBtn3, 1, 13);

        final Text actiontarget3 = new Text();
        grid.add(actiontarget3, 0, 14);
        GridPane.setColumnSpan(actiontarget3, 2);
        GridPane.setHalignment(actiontarget3, RIGHT);
        actiontarget3.setId("actiontarget3");


        btn3.setOnAction(e -> {
            actiontarget3.setFill(Color.FIREBRICK);

            if (spawnRectangle == null) {
                spawnRectangle = spawnObject.spawn();
                drawScene(grid);
            }

            actiontarget3.setText("Searching");

            if (search())
                actiontarget3.setText(String.format("x: %.2f, y: %.2f\n", searchingLine.getEndX(), searchingLine.getEndY()));
            else
                actiontarget3.setText("Not found");


        });

        Button btn4 = new Button("+-360");
        HBox hbBtn4 = new HBox(10);
        hbBtn4.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn4.getChildren().add(btn4);
        grid.add(hbBtn4, 0, 11);

        btn4.setOnAction(e -> {

            setRotatePivot();
            rotate360();

            //rotateRect();

        });

        Button btn5 = new Button("Spawn object");
        HBox hbBtn5 = new HBox(10);
        hbBtn5.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn5.getChildren().add(btn5);
        grid.add(hbBtn5, 0, 13);

        btn5.setOnAction(e -> {

            spawnRectangle = spawnObject.spawn();
            drawScene(grid);

        });

        Button btn6 = new Button("Change width");
        HBox hbBtn6 = new HBox(10);
        hbBtn6.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn6.getChildren().add(btn6);
        grid.add(hbBtn6, 0, 9);

        btn6.setOnAction(e -> {

            rWidth = Integer.valueOf(robWidth.getText());
            setRect();
            setLinePos();

        });

        Button btn7 = new Button("Choose points");
        searchBtn = btn7;
        HBox hbBtn7 = new HBox(10);
        hbBtn7.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn7.getChildren().add(btn7);
        grid.add(hbBtn7, 1, 15);

        final Text actiontarget7 = new Text();
        grid.add(actiontarget7, 1, 16);
        GridPane.setColumnSpan(actiontarget, 2);
        GridPane.setHalignment(actiontarget, RIGHT);
        actiontarget7.setId("actiontarget7");
        actiontarget7.setFill(Color.FIREBRICK);

        btn7.setOnAction(e -> {

            if (findRoute == null)
                findRoute = FindRoute.getInstance(this);

            background.setOnMouseClicked(event -> {
                if (findRoute != null) {
                    findRoute.setPoints(event, false);
                    searchBtn.fire();
                }
            });

            if (!findRoute.pointConfirmedA()) {
                actiontarget7.setText("Click on point A");
            } else if (!findRoute.pointConfirmedB()) {
                actiontarget7.setText("Click on point B");
            } else {
                actiontarget7.setText("Routing\n" +
                        "Ax: " + findRoute.getAx() + " Ay: " + findRoute.getAy()
                        + "\nBx: " + findRoute.getBx() + " By: " + findRoute.getBy());
                //goToPoint(actiontarget7);
                setCircles();
                findRoute.resetPoints();

                resetBackground();
            }

        });

        Button btn8 = new Button("Reset search line");
        HBox hbBtn8 = new HBox(10);
        hbBtn8.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn8.getChildren().add(btn8);
        grid.add(hbBtn8, 0, 15);

        btn8.setOnAction(e -> {

            spawnRectangle = null;
            drawScene(grid);

        });

        Button btn9 = new Button("Go");
        HBox hbBtn9 = new HBox(10);
        hbBtn9.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn9.getChildren().add(btn9);
        grid.add(hbBtn9, 0, 16);

        btn9.setOnAction(e -> {

            Task<Void> tsk = new Task<Void>() {

                @Override
                protected Void call() throws Exception {
                    setRectPos(findRoute.getAx(), findRoute.getAy());
                    while (!findRoute.goToPoint(actiontarget7)) ;
                    actiontarget7.setText("Completed!");
                    return null;
                }
            };

            Thread tmp = new Thread(tsk);
            tmp.setDaemon(true);
            tmp.start();
        });

        Button btn10 = new Button("Make obstacle");
        obstacleBtn = btn10;
        HBox hbBtn10 = new HBox(10);
        hbBtn10.setAlignment(Pos.BOTTOM_RIGHT);
        hbBtn10.getChildren().add(btn10);
        grid.add(hbBtn10, 0, 17);

        btn10.setOnAction(e -> {

            if (findRoute == null)
                findRoute = FindRoute.getInstance(this);

            makeObstacle();

        });

    }

    private void setAngleF() {

        if (angleF < 0)
            angleF += 360 + Integer.valueOf(angle1Field.getText());
        else
            angleF += Integer.valueOf(angle1Field.getText());

        if (angleF >= 360)
            angleF = angleF - (int) (angleF / 360) * 360;

    }


    private void setLinePos() {

        lines[4].setStartX(rectangle.getX()/* + rectangle.getWidth() / 2*/);
        lines[4].setStartY(rectangle.getY() + rectangle.getHeight() / 2);
        lines[4].setEndX(rectangle.getX() + rectangle.getWidth() / 2 + rectangle.getWidth() + 40);
        lines[4].setEndY(rectangle.getY() + rectangle.getHeight() / 2);
        //checkRotateCollisions();

    }

    void rotateRect() {

        rotate.setPivotX(rectangle.getX());
        rotate.setPivotY(rectangle.getY() + rectangle.getHeight() / 2);
        rotate.setAngle(angleF);

        //System.out.println("check rotate");

    }

    private void setRotatePivot() {
        rotate.setPivotX(rectangle.getX());
        rotate.setPivotY(rectangle.getY() + rectangle.getHeight() / 2);
    }

    void resetRotateRect() {
        setRotatePivot();
        rotate.setAngle(0);
    }

    void changeRobotPos(double dX, double dY) {
        setRect();
        lines[4].setStartX(rectangle.getX());
        lines[4].setStartY(rectangle.getY() + rectangle.getHeight() / 2);
        lines[4].setEndX(lines[4].getEndX() + dX);
        lines[4].setEndY(lines[4].getEndY() + dY);
        //checkRotateCollisions();
    }

    private boolean checkValidData() {

        if (beginCoordX.getText() == null || beginCoordY.getText() == null
                || stepLength.getText() == null
                || robWidth.getText() == null || angleField.getText() == null)
            return false;

        beginX = Integer.valueOf(beginCoordX.getText());
        beginY = Integer.valueOf(beginCoordY.getText());
        stLength = Integer.valueOf(stepLength.getText());
        rWidth = Integer.valueOf(robWidth.getText());
        angleF = Integer.valueOf(angleField.getText());

        if (angleF >= 360)
            angleF = angleF - (int) (angleF / 360) * 360;

        //System.out.println(angleF);

        return true;
    }

    boolean canMove(double prevX, double prevY) {
        double dX;
        double dY;
        boolean trig = true;
        for (int i = 0; i < 4; ++i) {
            if (findRoute.intersection(lines[i], rectangle) ||
                    lines[i].intersects(rectangle.getBoundsInLocal())
                    || findRoute.intersection(lines[i], circle) ||
                    lines[i].intersects(circle.getBoundsInLocal())) {
                //dX = -rWidth;
                //System.out.println(dX);
                trig = false;
            }
        }

        if (findRoute != null) {
            //System.out.println(trig + " " + findRoute.checkCollisions());
            trig = trig &&
                    (findRoute.checkCollisions(circle) || findRoute.checkCollisions(rectangle));
        }

        if (!trig) {
            dX = prevX - beginX;
            dY = prevY - beginY;

            beginX = prevX;
            beginY = prevY;

            changeRobotPos(dX, dY);
        }


        return trig;
    }

    private boolean search() {
        //boolean found = false;
        //int angle = Integer.valueOf(angle1Field.getText());
        createSearchingLine();

        System.out.println("in search");
        int delta = 1;

        for (int angle = (int) angleF + 1; angle <= 360 + (int) angleF; angle += delta) {

            rotate(angle, rWidth);

            for (int length = rWidth + 1;
                 !searchingLine.intersects(lines[0].getLayoutBounds()) &&
                         !searchingLine.intersects(lines[1].getLayoutBounds()) &&
                         !searchingLine.intersects(lines[2].getLayoutBounds()) &&
                         !searchingLine.intersects(lines[3].getLayoutBounds());
                 length++) {

                rotate(angle, length);

                if (spawnRectangle.contains(searchingLine.getEndX(), searchingLine.getEndY())) {
                    return true;
                }

//                    if (findRoute != null)
//                    for (Line line: findRoute.lines) {
//                        if (findRoute.intersection(line, searchingLine) ) {
//                            findRoute.setCollisionBx(searchingLine.getEndX());
//                            findRoute.setCollisionBy(searchingLine.getEndY());
//                            System.out.println(findRoute.lines.size());
//                            System.out.println("break in search");
//                            return true;
//                        }
//                    }
            }
            //System.out.println(counter);

        }


        return false;

    }


    // counting length between two points
//    private double countLength(double Ax, double Ay, double Bx, double By) {
//
//        double length = 0;
//
//        length = sqrt ( pow((Bx - Ax), 2) + pow((By - Ay), 2) );
//
//        return length;
//
//    }


    private void createSearchingLine() {
        searchingLine.setVisible(true);

        searchingLine.setStartX(rectangle.getX()/* + rectangle.getWidth() / 2*/);
        searchingLine.setStartY(rectangle.getY() + rectangle.getHeight() / 2);
        searchingLine.setEndX(rectangle.getX() + rectangle.getWidth() / 2 + rectangle.getWidth() + 40);
        searchingLine.setEndY(rectangle.getY() + rectangle.getHeight() / 2);


    }

    private void rotate(int angle, double length) {

        searchingLine.setEndX(searchingLine.getStartX() + length * cos(toRadians(angle)));

        searchingLine.setEndY(searchingLine.getStartY() + length * sin(toRadians(angle)));

    }

    private void checkSearchCollisions() {

        if (circle.intersects(spawnRectangle.getLayoutBounds()) || searchingLine.intersects(spawnRectangle.getLayoutBounds()))
            System.out.println("done");

    }

    private void rotate360() {

        final Timeline rotationAnimation = new Timeline();

        rotationAnimation.setCycleCount(2);
        rotationAnimation.setAutoReverse(true);

        rotationAnimation.getKeyFrames()
                .add(
                        new KeyFrame(
                                Duration.seconds(5),
                                new KeyValue(
                                        rotate.angleProperty(),
                                        angleF + 360
                                )
                        )
                );

        rotationAnimation.play();
    }

    private void setRectPos(double beginX, double beginY) {
        this.beginX = beginX;
        this.beginY = beginY - rWidth / 2;

        resetRotateRect();

        setRect();

        lines[4].setStartX(rectangle.getX()/* + rectangle.getWidth() / 2*/);
        lines[4].setStartY(rectangle.getY() + rectangle.getHeight() / 2);
        lines[4].setEndX(rectangle.getX() + rectangle.getWidth() / 2 + rectangle.getWidth() + 40);
        lines[4].setEndY(rectangle.getY() + rectangle.getHeight() / 2);

        rotateRect();

    }

    // set points A and B like circles
    private void setCircles() {
        circleA.setCenterX(findRoute.getAx());
        circleA.setCenterY(findRoute.getAy());
        circleA.setRadius(5);
        circleA.setFill(Color.BLUE);

        circleB.setCenterX(findRoute.getBx());
        circleB.setCenterY(findRoute.getBy());

        circleB.setRadius(5);
        circleB.setFill(Color.RED);
    }


    private void makeObstacle() {

        background.setOnMouseClicked(event -> {
            if (findRoute != null) {
                findRoute.setPoints(event, true);
                obstacleBtn.fire();
            }
        });

    }


    public static void main(String[] args) {

        launch(args);
    }

}
