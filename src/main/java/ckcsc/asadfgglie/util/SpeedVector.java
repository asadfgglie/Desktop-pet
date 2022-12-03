package ckcsc.asadfgglie.util;

/**
 * This enum include the speed vector's direction, speed value, and so on.
 * */
public class SpeedVector {
    private double speedX;
    private double speedY;

    public SpeedVector(double x, double y){
        speedX = x;
        speedY = y;
    }

    @Override
    public String toString () {
        return this.getClass().getSimpleName() + "@" + this.hashCode() + "(" + speedX + ", " + speedY + ")";
    }

    public static SpeedVector add(SpeedVector a, SpeedVector b){
        return new SpeedVector(a.speedX + b.speedX, a.speedY + b.speedY);
    }

    public static SpeedVector minus(SpeedVector a, SpeedVector b){
        return new SpeedVector(a.speedX - b.speedX, a.speedY - b.speedY);
    }

    public void setSpeedX (double speedX) {
        this.speedX = speedX;
    }

    public void setSpeedY (double speedY) {
        this.speedY = speedY;
    }

    @Override
    public boolean equals (Object obj) {
        return obj instanceof SpeedVector && (((SpeedVector) obj).speedX == speedX && ((SpeedVector) obj).speedY == speedY);
    }

    /**
     * get this speed vector's speed.
     * @return this speed vector of x
     */
    public double getSpeedX () {
        return speedX;
    }

    /**
     * get this speed vector's speed.
     * @return this speed vector of Y
     */
    public double getSpeedY () {
        return speedY;
    }
}
