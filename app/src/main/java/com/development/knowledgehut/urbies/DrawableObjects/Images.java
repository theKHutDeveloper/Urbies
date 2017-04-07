package com.development.knowledgehut.urbies.DrawableObjects;

import android.graphics.Bitmap;
import android.graphics.Point;

import com.development.knowledgehut.urbies.Frameworks.Graphics;


public class Images {
    private Bitmap bitmap;
    private Point position;

    public Images(Bitmap bitmap, Point position){
        this.bitmap = bitmap;
        this.position = position;
    }

    public int getX(){
        return position.x;
    }

    public int getY(){
        return position.y;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }

    public void setX(int x){
        this.position.x = x;
    }

    public void setY(int y){
        this.position.y = y;
    }

    public int getWidth(){
        return this.bitmap.getWidth();
    }

    public int getHeight(){
        return this.bitmap.getHeight();
    }

    public boolean isSelected(int eventX, int eventY){
        return eventX >= position.x && eventX <=(position.x + getWidth()) &&
                eventY >= (position.y )  &&
                eventY <= ((position.y ) + getHeight());
    }

    public void draw(Graphics graphics){
        graphics.drawBitmap(bitmap, position.x, position.y);
    }
}
