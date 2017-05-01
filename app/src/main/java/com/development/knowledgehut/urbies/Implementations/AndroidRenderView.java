package com.development.knowledgehut.urbies.Implementations;

import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Rect;
import android.view.GestureDetector;
import android.view.MotionEvent;
import android.view.SurfaceHolder;
import android.view.SurfaceView;
import android.view.GestureDetector.SimpleOnGestureListener;


public class AndroidRenderView extends SurfaceView implements Runnable {
    AndroidGame game;
    Bitmap frameBuffer;
    Thread thread = null;
    SurfaceHolder surfaceHolder;
    volatile boolean running = false;
    GestureDetector gestureDetector;

    public AndroidRenderView(AndroidGame game, Bitmap frameBuffer){
        super(game);
        this.game = game;
        this.frameBuffer = frameBuffer;
        this.surfaceHolder = getHolder();
        gestureDetector = new GestureDetector(game, gestureListener);
    }


    public synchronized boolean onTouchEvent(MotionEvent event){
        return gestureDetector.onTouchEvent(event);
    }


    SimpleOnGestureListener gestureListener = new SimpleOnGestureListener(){
        @Override
        public boolean onSingleTapUp(MotionEvent e) {
            //return super.onSingleTapUp(e);
            game.getCurrentScreen().onStandardTouch(e);
            return true;
        }

        @Override
        public boolean onFling(MotionEvent e1, MotionEvent e2, float velocityX, float velocityY){
            game.getCurrentScreen().doFlingEvent(e1, e2);
            return true;
        }

        @Override
        public boolean onDown(MotionEvent e){
            game.getCurrentScreen().input(e);
            return true;
        }

    };


    @Override
    public void run() {
        Rect dstRect = new Rect();
        long startTime = System.nanoTime();
        while(running){
            if(!surfaceHolder.getSurface().isValid()) continue;

            float deltaTime = (System.nanoTime() - startTime) / 1000000000.0f;
            startTime = System.nanoTime();

            game.getCurrentScreen().update(deltaTime);
            game.getCurrentScreen().render(deltaTime);

            Canvas canvas=surfaceHolder.lockCanvas();
            canvas.getClipBounds(dstRect);
            canvas.drawBitmap(frameBuffer, null, dstRect, null);
            surfaceHolder.unlockCanvasAndPost(canvas);
        }
    }

    public void resume(){
        running = true;
        thread = new Thread(this);
        thread.start();
    }

    public void pause(){
        running = false;
        while(true){
            try {
                thread.join();
                return;
            } catch (InterruptedException e) {
                e.printStackTrace();
            }

        }
    }
}
