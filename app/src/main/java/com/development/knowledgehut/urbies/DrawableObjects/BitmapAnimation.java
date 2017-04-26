package com.development.knowledgehut.urbies.DrawableObjects;


import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Path;
import android.graphics.PathMeasure;
import android.graphics.Point;
import android.graphics.Rect;
import android.graphics.RectF;
import android.support.annotation.NonNull;

import com.development.knowledgehut.urbies.Frameworks.Graphics;
import com.development.knowledgehut.urbies.Implementations.AndroidGame;

import java.util.ArrayList;

public class BitmapAnimation implements Comparable<BitmapAnimation> {
    private Bitmap bitmap; 				//the bitmaps containing the animation for the object
    private Rect sourceRect;			//the animation bounding box area
    private int frame_num;				//the number of frames in animation
    private int duration;
    private int current_frame;			//the current frame
    private int fps;
    private long frame_ticker;			//time of the last frame update
    private int frame_period;			//the milliseconds between each frame
    private int sprite_width;			//the width of the sprite
    private int sprite_height;			//the height of the sprite
    private int location;               //the location assigned to the object
    private Point position;             //the position of the object
    private boolean forwardAnimation;	//the direction of the animation if looped
    private boolean loopAnimation;		//are the animation frames to be looped?
    private boolean animationFinished;	//has the animation finished?
    private boolean animate_complete;
    private ArrayList<Point> spritePath = new ArrayList<>();
    private int pathCounter = 0;
    private float scale = 1;
    private PathMeasure pathMeasure = new PathMeasure();
    private float speed, distance = 0f;
    private Path p;
    private float[] pos=new float[2];
    private float tan[]=new float[2];


    public BitmapAnimation(Bitmap bitmap, Point position, int fps, int frameCount,int frameDuration,
                           boolean looped, int location, boolean forwardAnimation){

        this.bitmap = bitmap;
        this.position = position;
        this.fps = fps;

        frame_num = frameCount;
        duration = frameDuration;

        this.location = location;
        frame_period = duration / this.fps;
        sprite_width = bitmap.getWidth() / frameCount;
        sprite_height = bitmap.getHeight();
        sourceRect = new Rect(0,0, sprite_width, sprite_height);
        frame_ticker = 1;
        loopAnimation = looped;
        this.forwardAnimation = forwardAnimation;

        if(!forwardAnimation){
            current_frame = frame_num -1;
        } else current_frame = 0;

        animationFinished = false;
        animate_complete = false;
    }


    @SuppressWarnings("unused")
    public int getDuration(){
        return duration;
    }



    public void changeBitmapProperties(Bitmap bitmap, int fps, int frameCount, int frameDuration, boolean looped, boolean forwardAnimation){
        this.bitmap = bitmap;
        this.fps = fps;
        this.frame_num = frameCount;
        this.duration = frameDuration;
        this.loopAnimation = looped;
        this.forwardAnimation = forwardAnimation;
        sprite_width = bitmap.getWidth() / frameCount;
        sprite_height = bitmap.getHeight();
        frame_period = duration / this.fps;
        sourceRect = new Rect(0,0, sprite_width, sprite_height);
        if(!forwardAnimation){
            current_frame = frame_num-1;
        } else current_frame = 0;
    }

    public void setFrameDuration(int duration){
        this.duration = duration;
        frame_period = duration / this.fps;
    }

    public void setFPS(int fps){
        this.fps = fps;
        frame_period = duration / this.fps;
    }

    public int getFrameCount(){
        return frame_num;
    }

    public void setFrameCount(int frames){
        frame_num = frames;
    }

    public boolean getLooped(){
        return loopAnimation;
    }
    public int getFps(){
        return fps;
    }


    @SuppressWarnings("unused")
    public boolean getAnimationFinished(){
        return animate_complete;
    }

    @SuppressWarnings("unused")
    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setBitmap(Bitmap bitmap){
        this.bitmap = bitmap;
    }



    private void update(long gameTime){
        if (gameTime > frame_ticker + frame_period) {
            frame_ticker = gameTime;

            if(loopAnimation){ //the sprite animation is to be looped
                if(forwardAnimation){
                    current_frame++; //forward frame
                } else {
                    current_frame--; //reverse frame
                }

                if(current_frame >= (frame_num - 1)){ 	//last frame reached
                    forwardAnimation = false;			//set animation to reverse
                    current_frame = frame_num - 1;		//reverse frame
                }

                if(current_frame == 0){					//beginning of frame reached
                    forwardAnimation = true;			//set animation to forward
                }
            } else { //the sprite animation is not looped
                if(forwardAnimation) {
                    current_frame++;    //increase frame
                    if (current_frame >= frame_num) {    //maximum frame reached
                        animationFinished = true;
                    }
                } else{
                    if(current_frame > 0) {
                        current_frame--;
                    }
                    if(current_frame == 0){
                        animationFinished = true;
                    }

                }
            }
        }
        //define the bounding area of the current frame
        this.sourceRect.left = current_frame * sprite_width;
        this.sourceRect.right = this.sourceRect.left + sprite_width;
    }

    public void draw(Graphics graphics) {

        update(System.currentTimeMillis());

        Rect destRect = new Rect (position.x, position.y, position.x + sprite_width, position.y + sprite_height);

        graphics.drawBitmap(bitmap, sourceRect, destRect, null);

    }

    public void drawAnimation(Graphics graphics){

        if(scale < 1f){
            update(System.currentTimeMillis());
            graphics.drawBitmap(bitmap,scale(),null);
            animate_complete = false;
        }
        else{animate_complete=true;}
    }

    public boolean isSelected(int eventX, int eventY){
        return eventX >= position.x  && eventX <=(position.x + get_Width()) &&
                eventY >= position.y && eventY <= (position.y + get_Height());
    }

    public int getLocation(){
        return location;
    }

    public void setLocation(int location){
        this.location = location;
    }


    @SuppressWarnings("unused")
    public void setScale(float scale){
        this.scale = scale;
    }

    public float getScale(){
        return this.scale;
    }

    private Matrix scale(){
        Matrix matrix = new Matrix();

        matrix.postScale(scale, scale, bitmap.getWidth()/2, bitmap.getHeight()/2);
        matrix.postTranslate(this.position.x, this.position.y);

        if(scale < 1f){
            scale = scale + 0.05f;
        }
        return matrix;
    }

    public boolean animFinished(){
        return animationFinished;
    }

    public void resetAnimation(){
        animationFinished = false;
        current_frame = 0;
    }

    public Point getPosition(){
        return position;
    }

    public int getX(){
        return position.x;
    }

    public int getY(){
        return position.y;
    }

    public int getHeight(){
        return sprite_height;
    }

    public int getWidth(){
        return sprite_width;
    }

    public void setX(int x){
        this.position.x = x;
    }


    public void setY(int y){
        this.position.y = y;
    }

    private int get_Width(){
        return sprite_width;
    }


    private int get_Height(){
        return sprite_height;
    }

    public void findLine( int x0, int y0, int x1, int y1) {
        int dx = Math.abs(x1 - x0);
        int dy = Math.abs(y1 - y0);
        int sx = (x0 < x1) ? 1 : -1;
        int sy = (y0 < y1) ? 1 : -1;
        int err = dx-dy;
        int e2;

        while (true){
            spritePath.add(new Point(x0,y0));
            if (x0 == x1 && y0 == y1)
                break;

            e2 = 2 * err;
            if (e2 > -dy){
                err = err - dy;
                x0 = x0 + sx;
            }

            if (e2 < dx){
                err = err + dx;
                y0 = y0 + sy;
            }
        }
        pathCounter = 0;
    }


    public void clearPath(){
        spritePath.clear();
    }

    public int getLineIndex(){
        if(!spritePath.isEmpty()){
            return spritePath.size();
        } else return 0;
    }

    public boolean updatePath(float deltaTime){
        boolean isDestined;

        pathCounter+= (sprite_height * (deltaTime * 15));

        if(pathCounter >= getLineIndex()){
            this.position.x = spritePath.get(getLineIndex() - 1).x;
            this.position.y = spritePath.get(getLineIndex() - 1).y;
            isDestined = true;
        }
        else{
            this.position.x = spritePath.get(pathCounter).x;
            this.position.y = spritePath.get(pathCounter).y;
            isDestined = false;
        }
        return isDestined;
    }

    public void setUpBounceOutMiddle(int startX, int startY){
        RectF rectF = new RectF(
                startX,
                startY,
                (int)(50 * AndroidGame.GAME_SCALE_X) + startX,
                (int)(400 * AndroidGame.GAME_SCALE_X)
        );

        p = new Path();
        p.arcTo(rectF, -90, 140, false);

        RectF rectF1 = new RectF(
                ((int)(160 * AndroidGame.GAME_SCALE_X)),
                (int)(355 * AndroidGame.GAME_SCALE_X),
                (270 * AndroidGame.GAME_SCALE_X),
                400 * AndroidGame.GAME_SCALE_X
        );
        p.arcTo(rectF1, -180, 200, false);

        RectF rectF2 = new RectF(
                (270 * AndroidGame.GAME_SCALE_X),
                (355 * AndroidGame.GAME_SCALE_X),
                (380 * AndroidGame.GAME_SCALE_X),
                400 * AndroidGame.GAME_SCALE_X
        );
        p.arcTo(rectF2, -180, 180, false);

        pathMeasure = new PathMeasure();
        pathMeasure.setPath(p, false);
        speed = pathMeasure.getLength() / 40;
    }

    public void setUpBounceOutLeft(int startX, int startY){
        RectF rectF = new RectF(
                startX,
                startY,
                ((int)(118 * AndroidGame.GAME_SCALE_X) + startX),
                (int)(400 * AndroidGame.GAME_SCALE_X)
        );
        p = new Path();
        p.arcTo(rectF,240, -110, false);
        p.lineTo(startX + (int)(25 * AndroidGame.GAME_SCALE_X), (int)(390 * AndroidGame.GAME_SCALE_X));
        RectF rectF1 = new RectF(
                ((int)(90 * AndroidGame.GAME_SCALE_X)),
                (int)(355 * AndroidGame.GAME_SCALE_X),
                startX + (int)(25 * AndroidGame.GAME_SCALE_X),
                (int)(424 * AndroidGame.GAME_SCALE_X));
        p.arcTo(rectF1,0,-180, false);
        p.lineTo((int)(90 * AndroidGame.GAME_SCALE_X), (int)(410 * AndroidGame.GAME_SCALE_X));
        RectF rectF2 = new RectF(
                ((int)(-sprite_width * AndroidGame.GAME_SCALE_X)),
                (int)(355 * AndroidGame.GAME_SCALE_X),
                ((int)(90 * AndroidGame.GAME_SCALE_X)),
                (int)(424 * AndroidGame.GAME_SCALE_X));
        p.arcTo(rectF2,0,-180, false);
        pathMeasure = new PathMeasure();
        pathMeasure.setPath(p, false);
        speed = pathMeasure.getLength() / 40;
    }

    public void setUpBounceOutRight(int startX, int startY){
        RectF rectF = new RectF(
                startX,
                startY,
                ((int)(118 * AndroidGame.GAME_SCALE_X) + startX),
                (int)(400 * AndroidGame.GAME_SCALE_X)
        );
        p = new Path();
        p.arcTo(rectF,270, 90, false);
        p.lineTo(((int)(118 * AndroidGame.GAME_SCALE_X) + startX), (int)(390 * AndroidGame.GAME_SCALE_X));
        RectF rectF1 = new RectF(
                ((int)(118 * AndroidGame.GAME_SCALE_X) + startX),
                (int)(355 * AndroidGame.GAME_SCALE_X),
                ((int)(175 * AndroidGame.GAME_SCALE_X) + startX),
                (int)(424 * AndroidGame.GAME_SCALE_X));
        p.arcTo(rectF1,180,180, false);
        RectF rectF2 = new RectF(
                ((int)(175 * AndroidGame.GAME_SCALE_X) + startX),
                (int)(355 * AndroidGame.GAME_SCALE_X),
                ((int)(320 * AndroidGame.GAME_SCALE_X) + startX),
                (int)(424 * AndroidGame.GAME_SCALE_X));
        p.arcTo(rectF2,180,180, false);

        pathMeasure = new PathMeasure();
        pathMeasure.setPath(p, false);
        speed = pathMeasure.getLength() / 40;
    }

    public boolean bounceOut(float deltaTime){
        boolean finished;

        if (distance < pathMeasure.getLength()){
            pathMeasure.getPosTan(distance, pos, tan);
            distance += speed * (deltaTime * 30);
            this.position.x = (int)(pos[0]);
            this.position.y = (int)(pos[1]);
            finished = false;
        }
        else {
            this.position.x = (int)(pos[0]);
            this.position.y = (int)(pos[1]);
            finished = true;
        }

        return finished;
    }

    @SuppressWarnings("unused")
    public void resetBounce(){
        pathMeasure = new PathMeasure();
        p = new Path();
        distance = 0;
        speed = 0;
    }

    public void resetCounter(){
        this.pathCounter = 0;
    }

    @SuppressWarnings("unused")
    public int getPathCounter(){
        return pathCounter;
    }

    @SuppressWarnings("unused")
    public ArrayList<Point> getSpritePath(){
        for(int i = 0; i < spritePath.size(); i++){
            System.out.println(spritePath.get(i));
        }
        return spritePath;
    }

    public void setSpritePath(ArrayList<Point>path){
        this.spritePath = path;
    }

    @Override
    public int compareTo(@NonNull BitmapAnimation o) {
        return this.location - o.location;
    }

}
