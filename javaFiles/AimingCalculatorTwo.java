package javaFiles;
public class AimingCalculatorTwo {
    static double dX;
    static double dY;
    static double dZ;

    static double vX;
    static double vY;

    static double nV;

    static double g;

    static double h;
    
    public AimingCalculatorTwo () {

        dX = Constants.TARGET_X - Constants.TESTING.ROBOT_X;
        dY = Constants.TARGET_Y - Constants.TESTING.ROBOT_Y;
        dZ = Constants.TARGET_HEIGHT;

        vX = Constants.TESTING.ROBOT_VELOCITY_X;
        vY = Constants.TESTING.ROBOT_VELOCITY_Y;

        nV = Constants.TESTING.LAUNCH_SPEED;

        g = 9.8;

        h = 0.05;

        double shooterAngle = newtonsMethod(180, 1, 10);
        double robotAngle = f(shooterAngle);
        System.out.println("Shooter Angle : " + shooterAngle);
        System.out.println("Robot Angle   : " + robotAngle);
    }
    public static double j(double x) { // x = robot angle, j(x) is an invert of f(x)
        double top = (dX * vY) - (dY * vX);
        double bottom = nV * (Math.cos(x) * Math.sin(x));
        return Math.acos(top / bottom); // returns shooter angle
    }

    // Example functions
    public static double f(double x) { // x = shooter angle
        double top = ((dX * vY) - (dY * vX)) * Math.sqrt(2);
        double bottom = nV * Math.cos(x);
        double asin = Math.asin(((top / bottom) / 2));
        return asin + (Math.PI / 4); // returns robot angle
        // return function1 result
    }

    public static double g(double x) { // x = shooter angle
        double innerRoot = Math.pow(nV * Math.sin(x),2) - (2 * g * dZ);
        System.out.println(innerRoot);
        double outerRoot = -(nV * Math.sin(x));

        double plusRoot = (outerRoot + Math.sqrt(innerRoot)) / -g;
        double minusRoot = (outerRoot - Math.sqrt(innerRoot)) / -g;

        double ans1 = Math.asin((dY - (plusRoot * vX)) / (plusRoot * nV * Math.cos(x)));
        double ans2 = Math.asin((dY - (minusRoot * vX)) / (minusRoot * nV * Math.cos(x)));
        if (ans1 <= ans2) {
            return ans1;
        } else {
            return ans2;
        }
        //return function2 result
    }

    // Derivative of f(x) - g(x)
    public static double derivative(double x) {
        double dh = (h(x + h) - h(x)) / h;
        return dh;
    }

    // Function h(x) = f(x) - g(x)
    public static double h(double x) {
        double f = f(x);;
        double g = g(x);
        //System.out.println(f);
        System.out.println(g);
        return f(x) - g(x);  // (h(x) = f(x) - g(x))
    }

    // Newton's Method to find the root of h(x)
    public static double newtonsMethod(double initialGuess, double tolerance, int maxIterations) {
        double x = initialGuess;
        for (int i = 0; i < maxIterations; i++) {
            //System.out.println(x);
            double hValue = h(x);
            System.out.println(hValue);
            double derivativeValue = derivative(x);
            //System.out.println(derivativeValue);
            if (Math.abs(derivativeValue) < 1e-10) {
                System.out.println("Derivative too small, method fails.");
                return Double.NaN;
            }
            double nextX = x - hValue / derivativeValue;
            if (Math.abs(nextX - x) < tolerance) {
                return nextX;
            }
            x = nextX;
        }
        System.out.println("Exceeded maximum iterations.");
        return Double.NaN;
    }
}
