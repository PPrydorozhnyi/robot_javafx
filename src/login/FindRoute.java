import javafx.scene.input.MouseEvent;

public class FindRoute {

    private static FindRoute findRoute;

    private double Ax;
    private double Ay;
    private double Bx;
    private double By;

    private int pointsCounter;

    private FindRoute() {



    }

    public static FindRoute getInstance() {

        if (findRoute == null)
            findRoute = new FindRoute();

        return findRoute;

    }

    public void setPoints(MouseEvent e) {
        ++pointsCounter;

        if (pointsCounter >=2)  pointsCounter %= 2 ;

        if ( pointsCounter == 1) {
            Ax = e.getSceneX();
            Ay = e.getSceneY();
        } else {
            Bx = e.getSceneX();
            By = e.getSceneY();
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
}
