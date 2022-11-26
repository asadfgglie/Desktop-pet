package ckcsc.asadfgglie.pet.action;

import ckcsc.asadfgglie.Exception.WrongDirectionException;

public enum Side {
    Left(), Right(), Top(), Down();

    private double speedX;
    private double speedY;

    public Side setSpeedX(double speedX) throws WrongDirectionException {
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
            else throw new WrongDirectionException("The speed direction must mach 'Left' or 'Right' side direction.");
        }
        else {
            throw new WrongDirectionException("Only 'Left' or 'Right' side can use this method.");
        }
    }

    public Side setSpeedY(double speedY) throws WrongDirectionException {
        if(name().equals(Top.name()) || name().equals(Down.name())){
            this.speedY = speedY;
            return this;
        }
        else {
            throw new WrongDirectionException("Only 'Top' or 'Down' side can use this method.");
        }
    }

    public double getSpeedX () throws WrongDirectionException {
        if(name().equals(Left.name()) || name().equals(Right.name())){
            return speedX;
        }
        else {
            throw new WrongDirectionException("Only 'Left' or 'Right' side can use this method.");
        }
    }

    public double getSpeedY () throws WrongDirectionException {
        if(name().equals(Top.name()) || name().equals(Down.name())){
            return speedY;
        }
        else {
            throw new WrongDirectionException("Only 'Top' or 'Down' side can use this method.");
        }
    }
}
