package com.development.knowledgehut.urbies.Implementations;

import android.app.Activity;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Point;
import android.os.Build;
import android.os.Bundle;
import android.os.PowerManager;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;

import com.development.knowledgehut.urbies.Frameworks.Audio;
import com.development.knowledgehut.urbies.Frameworks.FileIO;
import com.development.knowledgehut.urbies.Frameworks.Game;
import com.development.knowledgehut.urbies.Frameworks.Graphics;
import com.development.knowledgehut.urbies.Frameworks.Screen;
import com.development.knowledgehut.urbies.Screens.Urbies;


public abstract class AndroidGame extends Activity implements Game {
    AndroidRenderView renderView;
    Graphics graphics;
    Audio audio;
    FileIO fileIO;
    Screen screen;
    PowerManager.WakeLock wakeLock;

    public static int deviceW;
    public static int deviceH;

    public static float GAME_SCALE_X;
    public static int USABLE_AREA_X;
    public static int USABLE_AREA_Y;
    public static int OFFSET_Y;


    @Override
    public void onCreate(Bundle savedInstanceState){
        super.onCreate(savedInstanceState);

        if(Build.VERSION.SDK_INT < 19){
            requestWindowFeature(Window.FEATURE_NO_TITLE);
            getWindow().setFlags(WindowManager.LayoutParams.FLAG_FULLSCREEN, WindowManager.LayoutParams.FLAG_FULLSCREEN);

            super.onCreate(savedInstanceState);
        }
        else if(Build.VERSION.SDK_INT >= 19) {
            super.onCreate(savedInstanceState);
            View decorView = getWindow().getDecorView();
            decorView.setSystemUiVisibility(View.SYSTEM_UI_FLAG_LAYOUT_STABLE |
                    View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_FULLSCREEN |
                    View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_HIDE_NAVIGATION |
                    View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY);
        }


        Point size = new Point();
        getWindowManager().getDefaultDisplay().getSize(size);
        deviceW = size.x;
        deviceH = size.y;

        Bitmap frameBuffer = Bitmap.createBitmap(deviceW, deviceH, Bitmap.Config.RGB_565);

        GAME_SCALE_X = (float)(deviceW )/ (float)(Urbies.WIDTH);
        USABLE_AREA_X = (Math.round(Urbies.WIDTH * GAME_SCALE_X));
        USABLE_AREA_Y = (Math.round(Urbies.HEIGHT * GAME_SCALE_X));
        OFFSET_Y = (deviceH - USABLE_AREA_Y) / 2;

        renderView = new AndroidRenderView(this, frameBuffer);
        graphics = new AndroidGraphics(frameBuffer);
        fileIO = new AndroidFileIO(this);

        audio = new AndroidAudio(this);
        screen = getStartScreen();
        setContentView(renderView);

        PowerManager powerManager = (PowerManager) getSystemService(Context.POWER_SERVICE);
        wakeLock = powerManager.newWakeLock(PowerManager.FULL_WAKE_LOCK, "Game");
    }

    @Override
    public Audio getAudio() {
        return audio;
    }

    @Override
    public FileIO getFileIO() {
        return fileIO;
    }

    public AndroidRenderView getRenderView(){
        return renderView;
    }

    @Override
    public Graphics getGraphics() {
        return graphics;
    }

    @Override
    public void setScreen(Screen screen) {
        if(screen == null) throw new IllegalArgumentException("Screen must not be null");

        this.screen.pause();
        this.screen.dispose();
        screen.resume();
        screen.update(0);
        this.screen = screen;
    }

    @Override
    public Screen getCurrentScreen() {
        return screen;
    }

    @Override
    public Screen getStartScreen() {
        return null;
    }


    @Override
    public void onResume(){
        super.onResume();
        wakeLock.acquire();
        screen.resume();
        renderView.resume();
    }

    @Override
    public void onPause(){
        super.onPause();
        wakeLock.release();
        renderView.pause();
        screen.pause();

        if(isFinishing()) screen.dispose();
    }

}
