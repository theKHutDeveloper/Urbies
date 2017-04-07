package com.development.knowledgehut.urbies.Objects;


import com.development.knowledgehut.urbies.DrawableObjects.BitmapAnimation;
import com.development.knowledgehut.urbies.Screens.Urbies;

public class Obstacles {
    private BitmapAnimation bmp;
    private Urbies.UrbieStatus status;
    private Urbies.VisibilityStatus visibilityStatus;
    private int numberUntilDestroyed;
    private int destroyCounter;

    public Obstacles(BitmapAnimation bmp, Urbies.UrbieStatus status, Urbies.VisibilityStatus visibilityStatus){
        this.bmp = bmp;
        this.status = status;
        this.visibilityStatus = visibilityStatus;

        switch(status){
            case NONE:
            case HIDDEN:
            case CAGED:
            case POISONED:
                numberUntilDestroyed = 0; break;
            case GLASS:
            case SINGLE_TILE: numberUntilDestroyed = 1; break;
            case WOODEN:
            case DOUBLE_TILE: numberUntilDestroyed = 2; break;
            case CEMENT: numberUntilDestroyed = 4; break;
        }
        destroyCounter = numberUntilDestroyed;
    }

    public BitmapAnimation getObstacle(){
        return bmp;
    }


    public Urbies.UrbieStatus getStatus(){
        return status;
    }

    public int getLocation(){
        return bmp.getLocation();
    }


    public void clearStatus(){
        if(status != Urbies.UrbieStatus.NONE){
            status = Urbies.UrbieStatus.NONE;
        }
    }

    public boolean isVisible(){
        if(visibilityStatus == Urbies.VisibilityStatus.VISIBLE) return true;
        else return false;
    }

    @SuppressWarnings("unused")
    public int getNumberUntilDestroyed(){
        return numberUntilDestroyed;
    }

    public void deductDestroyCounter(){
        if(destroyCounter > 0){
            destroyCounter--;
        }
    }

    public int getDestroyCounter(){
        return destroyCounter;
    }
}
