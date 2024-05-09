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
        double launchVZ = Constants.TESTING.LAUNCH_SPEED * Math.sin(Math.toRadians(shooterAngle));

        double launchVH = Constants.TESTING.LAUNCH_SPEED * Math.cos(Math.toRadians(shooterAngle));

        double launchVX = launchVH * Math.sin(Math.toRadians(robotAngle));
        double launchVY = launchVH * Math.cos(Math.toRadians(robotAngle));

        double xDistance = Constants.TARGET_X - robotX;

        double timeWhenCorrectX = xDistance / launchVX;

        boolean facingWrongWay = false;
        double behindMultiplier = 1;
        if (timeWhenCorrectX < 0) { // this will call if its firing in the wrong direction
            facingWrongWay = true;
            timeWhenCorrectX *= -1;
        }

        if  (xDistance < 0) {
            behindMultiplier = -1;
        }

        // kinematic equation : x = vt + 0.5at^2 :
        double heightAtTime = (launchVZ * timeWhenCorrectX) + (0.5 * -Constants.GRAVITY * Math.pow(timeWhenCorrectX, 2)); 

        double yAtTime = (launchVY * timeWhenCorrectX) + robotY;

        double heightError = Constants.TARGET_HEIGHT - heightAtTime;
        double yError = Constants.TARGET_Y - yAtTime;

        double newShooterAngle = shooterAngle - (heightError * Constants.TESTING.SHOOTER_ANGLE_CHANGE_MULTIPLIER);

        double angleChange = yError * behindMultiplier;

        double newRobotAngle;
        if (facingWrongWay) {
            newRobotAngle = 180 + (angleChange + robotAngle);
        } else {
            newRobotAngle = angleChange + robotAngle;
        }

        return new RobotPose(robotX, robotY, newRobotAngle, newShooterAngle);
    }
}
