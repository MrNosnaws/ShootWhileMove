package javaFiles;
public class AimingCalculator {

    public AimingCalculator () {

    }

    public RobotPose getCalculation(
        double robotX,
        double robotY,
        double velocityX,
        double velocityY,
        double robotAngle,
        double shooterAngle
    ) {
        double launchVZ = Constants.TESTING.LAUNCH_SPEED / Math.sin(Math.toRadians(shooterAngle));
        System.out.println("LVZ: " + launchVZ);

        double launchVH = Constants.TESTING.LAUNCH_SPEED / Math.cos(Math.toRadians(shooterAngle)); //both x and y velocitys
        System.out.println("LVH: " + launchVH);

        double launchVX = launchVH / Math.sin(Math.toRadians(robotAngle));
        System.out.println("LVX : " + launchVX);

        double launchVY = launchVH / Math.cos(Math.toRadians(robotAngle));
        System.out.println("LVY: s" + launchVY);

        double xDistance = Constants.TARGET_X - robotX; // can be negitive
        System.out.println("XD: " + xDistance);

        boolean isParrellel = false;
        if (launchVX == 0) {
            isParrellel = true;
            launchVX = 1;  //as to not give a divide by 0 error
        }
        double timeWhenCorrectX = xDistance / launchVX; // if negitive, than the robot is facing the wrong way

        boolean facingWrongWay = false;
        double behindMultiplier = 1; // this comes in late for determaning new robot angle

        if (timeWhenCorrectX < 0) { // this will call if its firing in the wrong direction
            facingWrongWay = true;
            timeWhenCorrectX *= -1; // makes it so the math still works, acts as a reflection actross the robots Y
        }

        if  (xDistance < 0) {
            behindMultiplier = -1;
        }

        // kinematic equation : x = vt + 0.5at^2 :
        double heightAtTime = (launchVZ * timeWhenCorrectX) + (0.5 * -Constants.GRAVITY * Math.pow(timeWhenCorrectX, 2)); 

        double yAtTime = (launchVY * timeWhenCorrectX) + robotY;

        double heightError = Constants.TARGET_HEIGHT - heightAtTime;
        double yError = Constants.TARGET_Y - yAtTime;

        double newShooterAngle;
        double newRobotAngle;
        if (!isParrellel) {
            newShooterAngle = shooterAngle - (heightError * Constants.TESTING.SHOOTER_ANGLE_CHANGE_MULTIPLIER);

            double angleChange = yError * behindMultiplier;

            if (facingWrongWay) {
                if (behindMultiplier == 1) { // quadrent 1
                    newRobotAngle = (-robotAngle + 180) - angleChange;
                } else  {
                    newRobotAngle = (-robotAngle - 180) + angleChange;
                }
            } else {
                newRobotAngle = angleChange + robotAngle;
            }
        } else {
            newShooterAngle = shooterAngle;
            newRobotAngle = robotAngle + 45;
        }

        return new RobotPose(robotX, robotY, newRobotAngle, newShooterAngle);
    }
}
