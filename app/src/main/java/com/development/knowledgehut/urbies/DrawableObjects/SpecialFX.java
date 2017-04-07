package com.development.knowledgehut.urbies.DrawableObjects;


import android.graphics.Bitmap;
import android.graphics.Matrix;

import com.development.knowledgehut.urbies.Frameworks.Graphics;

import java.util.ArrayList;

public class SpecialFX {
    private ArrayList<Bitmap> bitmaps;
    private int x1, x2;
    private int y1, y2;
    private double distance;
    private float degrees;
    private int xDiff, yDiff;
    private Bitmap currentBitmap;
    private int frames, frameNumber;
    private long lastFrameTime;
    private long duration;
    private int tileHeight;
    private boolean animationFinished;

    public SpecialFX(ArrayList<Bitmap>bmp, int x1, int y1, int x2, int y2, long duration, int tileHeight){
        this.x1 = x1;
        this.y1 = y1;
        this.x2 = x2;
        this.y2 = y2;
        this.tileHeight = tileHeight;

        this.degrees = (float) Math.toDegrees(Math.atan2(this.y2-this.y1,x2-this.x1)) + 90f;
        this.distance = Math.abs(Math.sqrt(Math.pow(this.x2 - this.x1, 2) + Math.pow(this.y2-this.y1, 2)));

        if(distance == 0){
            distance = tileHeight;
        }

        this.bitmaps = bmp;
        for(int i = 0; i < bitmaps.size(); i++){
            Bitmap temp = Bitmap.createScaledBitmap(bmp.get(i),bmp.get(i).getWidth(), (int)distance, true);
            Matrix matrix = new Matrix();
            matrix.postRotate(degrees);
            temp = Bitmap.createBitmap(temp,0, 0, temp.getWidth(), (int)distance, matrix, false);
            this.bitmaps.set(i, temp);
        }

        this.duration = duration;

        xDiff = x1 - x2;
        yDiff = y1 - y2;

        frames = this.bitmaps.size();
        frameNumber = 0;
        currentBitmap = this.bitmaps.get(0);
        lastFrameTime = System.currentTimeMillis();
        animationFinished = false;

    }

    @SuppressWarnings("unused")
    public double getDistance(){
        return distance;
    }

    @SuppressWarnings("unused")
    public float getDegrees(){
        return degrees;
    }

    public boolean isAnimationFinished(){
        return animationFinished;
    }


    private void update(){
        if(System.currentTimeMillis() > (lastFrameTime + duration)){
            if(frameNumber == frames - 1){
                animationFinished = true;
            }
            else if(frameNumber < frames -1){
                frameNumber++;
                currentBitmap = bitmaps.get(frameNumber);
            }
            lastFrameTime = System.currentTimeMillis();
        }

    }
    public void draw(Graphics graphics){
        update();
        if(!animationFinished) {
            if (y1 > y2) {
                if (x1 > x2) {
                    graphics.drawBitmap(currentBitmap, x1 - xDiff, (y1 - yDiff + tileHeight / 4));
                } else {
                    graphics.drawBitmap(currentBitmap, (x1), (y1 - yDiff + tileHeight / 4));
                }
            } else if (y1 < y2) {
                if (x1 < x2) {
                    graphics.drawBitmap(currentBitmap, x1, y1 + (tileHeight / 2));
                } else {
                    graphics.drawBitmap(currentBitmap, (x1 - xDiff), y1 + tileHeight / 2);
                }
            } else if (y1 == y2) {
                if (x1 < x2) {
                    graphics.drawBitmap(currentBitmap, x1 + (tileHeight / 2), y1);
                } else {
                    graphics.drawBitmap(currentBitmap, x1 - (int) distance + (tileHeight / 2), y1);
                }
            }
        }
    }
}
