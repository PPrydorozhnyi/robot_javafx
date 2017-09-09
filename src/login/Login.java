
package login;

import javafx.application.Application;
import javafx.event.ActionEvent;
import javafx.event.EventHandler;
import static javafx.geometry.HPos.RIGHT;
import javafx.geometry.Insets;
import javafx.geometry.Pos;
import javafx.scene.Group;
import javafx.scene.Scene;
import javafx.scene.control.Button;
import javafx.scene.control.Label;
import javafx.scene.control.TextField;
import javafx.scene.layout.GridPane;
import javafx.scene.layout.HBox;
import javafx.scene.text.Font;
import javafx.scene.text.FontWeight;
import javafx.scene.text.Text;
import javafx.scene.canvas.Canvas;
import javafx.scene.canvas.GraphicsContext;
import javafx.scene.input.MouseEvent;
import javafx.scene.paint.Color;
import javafx.scene.shape.ArcType;
import javafx.scene.shape.Line;
import javafx.scene.shape.Rectangle;
import javafx.scene.transform.Affine;
import javafx.scene.transform.Rotate;
import javafx.stage.Stage;

public class Login extends Application {
    
    private Stage primaryS;
    //private Canvas canvas;
    //private GraphicsContext gc;
    
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
    private final Line[] lines = new Line[5];
    private Rotate rotate;
    
    //double x = 50;
    //double y = 100;
    //double width = 10;
    //double height = 20;
    //int angle = 0;
   
    

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

        //Scene scene = new Scene(grid, 300, 275);
        //primaryStage.setScene(scene);
        //primaryStage.show();
        
        drawScene(grid);
        
    }
    
    private void drawScene(GridPane grid) {
        
        Group root = new Group();
        //System.out.println("Drawscene");
        
        //canvas = new Canvas(500, 600);
        //gc = canvas.getGraphicsContext2D();
        //drawShapes();
        //r.setVisible(false);

        setRect();
        createLines();
        
        
        
        //root.getChildren().add(canvas);
        root.getChildren().add(rectangle);
        for (Line line : lines) {
            root.getChildren().add(line);
        }
       
        
        HBox hBox = new HBox(10, root, grid);
        
        primaryS.setScene(new Scene(hBox, 800, 600));
        primaryS.show();
        
    }
    
    private void setRect() {
        rectangle.setX(beginX);
        rectangle.setY(beginY);
        rectangle.setWidth(rWidth);
        rectangle.setHeight(rWidth);
        rectangle.setArcWidth(20);
        rectangle.setArcHeight(20);
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
        //lines[2].setVisible(false);
        
        lines[3] = new Line();
        lines[3].setStartX(cWidth);
        lines[3].setStartY(0);
        lines[3].setEndX(0);
        lines[3].setEndY(0);
        
        lines[4] = new Line();
        lines[4].setStartX(rectangle.getX() + rectangle.getWidth() / 2);
        lines[4].setStartY(rectangle.getY() + rectangle.getHeight() / 2);
        lines[4].setEndX(cWidth);
        lines[4].setEndY(rectangle.getY() + rectangle.getHeight() / 2);
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

        btn.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget.setFill(Color.FIREBRICK);
                actiontarget.setText("Processing");
                //reset(Color.WHITE);
                //drawShapes();
                //System.out.println(userTextField.getText());
                
                //rectangle.getTransforms().add(rotate);
                //System.out.println(rotate.getAngle());
                //lines[4].setEndX(20);
                if (checkValidData()) {
                    actiontarget.setText("Processing");
                    setPos();
                } else
                    actiontarget.setText("Try again");
                    
                
                
                /*double rotationCenterX = (x + width) / 2;
                double rotationCenterY = (y + height) / 2;

                angle += 45;
                
                gc.setFill(Color.GREEN);
                
                gc.save();
                gc.transform(new Affine(new Rotate(angle, x, y)));
                gc.fillRect(100, 50, width, height);
                gc.restore();
                
                gc.fillOval(10, 60, 30, 30);*/
            }

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

        btn1.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget1.setFill(Color.FIREBRICK);
                actiontarget1.setText("Processing");
                angleF = Integer.valueOf(angle1Field.getText());
                
                if (angleF < 0)
                    angleF += 360;
                setPos();
            }

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

        btn2.setOnAction(new EventHandler<ActionEvent>() {

            @Override
            public void handle(ActionEvent e) {
                actiontarget2.setFill(Color.FIREBRICK);
                
                stLength = Integer.valueOf(stepLength.getText());
                
                if (canMove()) {
                    
                actiontarget2.setText("Processing");
                    
                int dX = (int)(stLength * Math.cos(angleF));
                int dY = (int)(stLength * (int)Math.sin(angleF));
                
                beginX += dX;
                beginY += dY;
                
                changeRectPos(dX, dY);
                } else {
                    actiontarget2.setText("Can`t move");
                }
                
            }

        });
    }
    
    private void setPos() {
        
        setRect();
        rotate = new Rotate(angleF,rectangle.getX() + rectangle.getWidth() / 2,rectangle.getY() + rectangle.getHeight() / 2);
        rectangle.getTransforms().add(rotate);
        lines[4].setStartX(rectangle.getX() + rectangle.getWidth() / 2);
        lines[4].setStartY(rectangle.getY() + rectangle.getHeight() / 2);
        lines[4].setEndX(cWidth);
        lines[4].setEndY(rectangle.getY() + rectangle.getHeight() / 2);
        checkRotateCollisions();
                    
    }
    
    private void changeRectPos(int dX, int dY) {
        setRect();
        lines[4].setStartX(rectangle.getX() + rectangle.getWidth() / 2);
        lines[4].setStartY(rectangle.getY() + rectangle.getHeight() / 2);
        lines[4].setEndX(lines[4].getEndX() + dX);
        lines[4].setEndY(lines[4].getEndY() + dY);
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
        
        return true;
        }
    
    private boolean canMove() {
                for (int i = 0; i < 4; ++i)
                    if (lines[i].intersects(lines[4].getBoundsInParent()))
                        return false;
                        
                        
                return true;
    }
    
    private void checkRotateCollisions() {
        lines[4].getTransforms().add(new Rotate(angleF,rectangle.getX() + rectangle.getWidth() / 2,rectangle.getY() + rectangle.getHeight() / 2));
                /*for (int i = 0; i < 4; ++i) {
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
                }*/
                
    }
    

    public static void main(String[] args) {

        launch(args);
    }

}
