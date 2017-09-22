import javafx.animation.Animation;
import javafx.animation.KeyFrame;
import javafx.animation.KeyValue;
import javafx.animation.Timeline;
import javafx.application.Application;
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

import static javafx.geometry.HPos.RIGHT;

public class Login extends Application {
    
    private Stage primaryS;
    
    private int cWidth = 500;
    
    private TextField beginCoordX;
    private TextField beginCoordY;
    private TextField stepLength;
    private TextField robWidth;
    private TextField angleField;
    private TextField angle1Field;
    
    private int beginX = 200;
    private int beginY = 200;
    private int stLength = 0;
    private int rWidth = 40;
    private int angleF = 0;
    
    private Rectangle rectangle = new Rectangle();
    private Circle circle = new Circle();
    private final Line[] lines = new Line[5];
    private Rotate rotate = new Rotate();
    private RandomObject spawnObject = new RandomObject(cWidth);
    private Rectangle spawnRectangle;
    private Line searchingLine = new Line();


    @Override
    public void start(Stage primaryStage) {
        
        primaryS = primaryStage;
        
        primaryStage.setTitle("lab 1");
        primaryStage.setHeight(600);
        primaryStage.setWidth(900);
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

        setRect();
        circle.setFill(Color.BROWN);
        rectangle.getTransforms().add(rotate);
        createLines();
        lines[4].getTransforms().add(rotate);

        
        drawScene(grid);
        
    }
    
    private void drawScene(GridPane grid) {
        
        Group root = new Group();


        if (spawnRectangle != null) {
            root.getChildren().add(spawnRectangle);
            root.getChildren().add(searchingLine);
        }

        root.getChildren().add(rectangle);
        root.getChildren().add(circle);
        for (Line line : lines) 
            root.getChildren().add(line);

        HBox hBox = new HBox(10, root, grid);
        
        primaryS.setScene(new Scene(hBox, 800, 600));
        primaryS.show();
        
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
                    checkSearchColissions();

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
                checkSearchColissions();
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
            actiontarget2.setFill(Color.FIREBRICK);

            stLength = Integer.valueOf(stepLength.getText());

            actiontarget2.setText("Processing");


            resetRotateRect();
            moving(actiontarget2);
            rotateRect();
            if (spawnRectangle != null)
                checkSearchColissions();

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

            spawnRectangle = spawnObject.spawn();
            drawScene(grid);

            actiontarget3.setText("Searching");

            search();

            //resetRotateRect();
            //rotate360();

            //rotateRect();

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
    }

    private void setAngleF() {

        if (angleF < 0)
            angleF += 360 + Integer.valueOf(angle1Field.getText());
        else
            angleF += Integer.valueOf(angle1Field.getText());

        if (angleF >= 360)
            angleF = angleF - (int) (angleF / 360) * 360;

    }

    private void moving(Text actiontarget) {

        int dx;
        int dy;
        int prevX = beginX;
        int prevY = beginY;

        while (stLength-- > 0) {

            //System.out.println(stLength);

            dx = (int)(rectangle.getWidth() * Math.cos(Math.toRadians(angleF)));
            dy = (int)(rectangle.getWidth() * Math.sin(Math.toRadians(angleF)));

            System.out.println("angleF = " + angleF);
            System.out.println("sin = " + Math.sin(Math.toRadians(angleF)));
            System.out.println("cos = " + Math.cos(Math.toRadians(angleF)));
            System.out.println("dx = " + dx);
            System.out.println("dy = " + dy);

            beginX += dx;
            beginY += dy;

            changeRobotPos(dx, dy);

            if (canMove(prevX , prevY))

                actiontarget.setText("Mooving");

            else {
                actiontarget.setText("Can`t move");
                break;
            }
        }

    }
    
    private void setLinePos() {

        lines[4].setStartX(rectangle.getX()/* + rectangle.getWidth() / 2*/);
        lines[4].setStartY(rectangle.getY() + rectangle.getHeight() / 2);
        lines[4].setEndX(rectangle.getX() + rectangle.getWidth() / 2 + rectangle.getWidth() + 40);
        lines[4].setEndY(rectangle.getY() + rectangle.getHeight() / 2);
        //checkRotateCollisions();
                    
    }

    private void rotateRect() {

        rotate.setPivotX(rectangle.getX());
        rotate.setPivotY(rectangle.getY() + rectangle.getHeight() / 2);
        rotate.setAngle(angleF);

        //System.out.println("check rotate");

    }

    private void setRotatePivot() {
        rotate.setPivotX(rectangle.getX());
        rotate.setPivotY(rectangle.getY() + rectangle.getHeight() / 2);
    }

    private void resetRotateRect() {
        setRotatePivot();
        rotate.setAngle(0);
    }
    
    private void changeRobotPos(int dX, int dY) {
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
    
    private boolean canMove(int prevX, int prevY) {
        int dX;
        int dY;
        boolean trig = true;
                for (int i = 0; i < 4; ++i)
                        while (lines[i].intersects(circle.getBoundsInParent())
                                || circle.getCenterX() > cWidth || circle.getCenterX() < 0
                                || circle.getCenterY() > cWidth || circle.getCenterY() <0) {
                            //dX = -rWidth;
                            //System.out.println(dX);
                            dX = prevX - beginX;
                            dY = prevY - beginY;

                            beginX = prevX;
                            beginY = prevY;
                            
                            changeRobotPos(dX, dY);
                            trig = false;
                        }
                        
                        
                return trig;
    }

    public boolean search() {
        boolean found = false;
        int angle = Integer.valueOf(angle1Field.getText());

        createSearchingLine();

        //for (int searchingAngle = 0; searchingAngle < 360; searchingAngle += Integer.valueOf(angle1Field.getText())) {

            setAngleF();
            //setRotatePivot();
            //rotateAnimation();
            rotate(angle);

            /*if (searchingLine.intersects(spawnRectangle.getLayoutBounds()) || rectangle.intersects(spawnRectangle.getLayoutBounds()) ) {


                System.out.println("yep");
                System.out.println(lines[4].getEndX());
                System.out.println(lines[4].getEndY());
                //System.out.println(lines[4].getRotate());
                System.out.println(rectangle.getX());
                System.out.println(rectangle.getY());
            }*/

            /*try {
                Thread.sleep(2000);
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }*/


        return found;

    }

    private void createSearchingLine() {
        searchingLine.setVisible(true);

        searchingLine.setStartX(rectangle.getX()/* + rectangle.getWidth() / 2*/);
        searchingLine.setStartY(rectangle.getY() + rectangle.getHeight() / 2);
        searchingLine.setEndX(rectangle.getX() + rectangle.getWidth() / 2 + rectangle.getWidth() + 40);
        searchingLine.setEndY(rectangle.getY() + rectangle.getHeight() / 2);


    }

    private void rotate( int angle){

        double length = rectangle.getWidth() / 2 + rectangle.getWidth() + 40;

        //System.out.println(length * Math.cos(Math.toRadians(angle)));

        searchingLine.setEndX(searchingLine.getStartX() + length * Math.cos(Math.toRadians(angleF)) );

        searchingLine.setEndY(searchingLine.getStartY() + length * Math.sin(Math.toRadians(angleF)) );



    }

    private void checkSearchColissions() {

        if (rectangle.intersects(spawnRectangle.getLayoutBounds()) || lines[4].intersects(spawnRectangle.getLayoutBounds()))
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

    private void rotateAnimation() {

        final Timeline rotationAnimation = new Timeline();

        rotationAnimation.setCycleCount(1);

        rotationAnimation.getKeyFrames()
                .add(
                        new KeyFrame(
                                Duration.millis(500),
                                new KeyValue(
                                        rotate.angleProperty(),
                                        angleF
                                )
                        )
                );

        rotationAnimation.play();
    }


    
    /*private void checkRotateCollisions() {
        lines[4].getTransforms().add(new Rotate(angleF,rectangle.getX() + rectangle.getWidth() / 2,rectangle.getY() + rectangle.getHeight() / 2));
                *//*for (int i = 0; i < 4; ++i) {
                    if (lines[i].intersects(lines[4].getBoundsInParent())) {
                        
                        System.out.println("check");
                        
                        switch (i) {
                            case 0:    
                            lines[4].setEndX(0);
                            System.out.println("check0");
                            lines[4].setEndY(0);
                                break;
                        
                            case 1:
                                lines[4].setEndX(0);
                            //System.out.println("check0");
                            lines[4].setEndY(0);
                                System.out.println("check1");
                                break;
                                
                            case 2:
                                lines[4].setEndX(0);
                            //System.out.println("check0");
                            lines[4].setEndY(0);
                                System.out.println("check2");
                                break;
                            case 3:
                                lines[4].setEndX(0);
                            //System.out.println("check0");
                            lines[4].setEndY(0);
                                System.out.println("check3");
                                System.out.println(lines[4].getEndY());
                                break;
                        
                        }
                    }
                }*//*
                
    }*/
    

    public static void main(String[] args) {

        launch(args);
    }

}
