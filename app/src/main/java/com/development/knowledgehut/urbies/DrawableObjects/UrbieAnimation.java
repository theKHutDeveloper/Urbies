package com.development.knowledgehut.urbies.DrawableObjects;


import android.graphics.Bitmap;
import android.graphics.Point;

import com.development.knowledgehut.urbies.Screens.Urbies;

public class UrbieAnimation extends BitmapAnimation{
    private Urbies.UrbieStatus status;
    private Urbies.UrbieType type;
    private Urbies.VisibilityStatus visible;
    private boolean active;

    public UrbieAnimation(Bitmap bitmap,
                          Point position,
                          int fps, int frameCount,
                          int frameDuration,
                          boolean looped, Urbies.UrbieType type,
                          int location,
                          boolean forwardAnimation, Urbies.UrbieStatus status,
                          Urbies.VisibilityStatus visible,
                          boolean active
    ) {
        super(bitmap, position, fps, frameCount, frameDuration, looped, location, forwardAnimation);
        this.status = status;
        this.type = type;
        this.visible = visible;
        this.active = active;
    }

    public void setStatus(Urbies.UrbieStatus status){
        this.status = status;
    }

    public Urbies.VisibilityStatus getVisible(){
        return visible;
    }

    public void setVisible(Urbies.VisibilityStatus visible){
        this.visible = visible;
    }

    public Urbies.UrbieStatus getStatus(){
        return status;
    }

    public Urbies.UrbieType getType(){
        return type;
    }
    public void setType(Urbies.UrbieType type){
        this.type = type;
    }

    public void setActive(boolean active){
        this.active = active;
    }

    public boolean getActive(){
        return active;
    }

    public void print(){
        System.out.println("location = "+ getLocation());
        System.out.println("type = "+type);
        System.out.println("visible = "+visible);
        System.out.println("active = "+active);
        System.out.println("status = "+status);
    }
}
