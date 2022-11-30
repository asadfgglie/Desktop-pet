package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.Exception.WrongDirectionException;

/**
 * This enum include the speed vector's direction, speed value, and so on.
 * */
public enum SpeedVector {
    Left(), Right(), Top(), Down();

    private double speedX;
    private double speedY;

    /**
     * @param speedX
     * <p>must match its direction for correct speed value.</p>
     * Left means speed <= 0, Right means >= 0
     * @return itself
     * @throws WrongDirectionException
     * When speed value doesn't match its direction.
     */
    public SpeedVector setSpeedX(double speedX) throws WrongDirectionException {
        if(name().equals(Left.name()) || name().equals(Right.name())){
            if(speedX > 0 && this == Right) {
                this.speedX = speedX;
                return this;
            }
            else if(speedX < 0 && this == Left) {
                this.speedX = speedX;
                return this;
            }
            else if(speedX == 0){
                this.speedX = 0;
                return this;
            }
            else throw new WrongDirectionException("The speed direction must mach 'Left' or 'Right' speedVector direction.");
        }
        else {
            throw new WrongDirectionException("Only 'Left' or 'Right' speedVector can use this method.");
        }
    }

    /**
     * @param speedY
     * <p>must match its direction for correct speed value.</p>
     * Top means speed <= 0, Down means >= 0
     * @return itself
     * @throws WrongDirectionException
     * When speed value doesn't match its direction.
     */
    public SpeedVector setSpeedY(double speedY) throws WrongDirectionException {
        if(name().equals(Top.name()) || name().equals(Down.name())){
            this.speedY = speedY;
            return this;
        }
        else {
            throw new WrongDirectionException("Only 'Top' or 'Down' speedVector can use this method.");
        }
    }

    /**
     * get this speed vector's speed.
     * @return this speed vector of x
     * @throws WrongDirectionException
     * When this speedVector isn't Right or Left
     */
    public double getSpeedX () throws WrongDirectionException {
        if(name().equals(Left.name()) || name().equals(Right.name())){
            return speedX;
        }
        else {
            throw new WrongDirectionException("Only 'Left' or 'Right' speedVector can use this method.");
        }
    }

    /**
     * get this speed vector's speed.
     * @return this speed vector of x
     * @throws WrongDirectionException
     * When this speedVector isn't Right or Left
     */
    public double getSpeedY () throws WrongDirectionException {
        if(name().equals(Top.name()) || name().equals(Down.name())){
            return speedY;
        }
        else {
            throw new WrongDirectionException("Only 'Top' or 'Down' speedVector can use this method.");
        }
    }
}
