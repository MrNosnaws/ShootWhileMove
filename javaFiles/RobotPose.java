package javaFiles;
public class RobotPose {

    private double robotX;
    private double robotY;
    private double robotAngle;
    private double shooterAngle;

        public RobotPose (
            double robotX,
            double robotY,
            double robotAngle,
            double shooterAngle
        ) {
            this.robotX = robotX;
            this.robotY = robotY;
            this.robotAngle = robotAngle;
            this.shooterAngle = shooterAngle;
        }

        public double getX () {
            return robotX;
        }

        public double getY () {
            return robotY;
        }

        public double getRobotAngle () {
            return robotAngle;
        }

        public double getShooterAngle () {
            return shooterAngle;
        }
}
