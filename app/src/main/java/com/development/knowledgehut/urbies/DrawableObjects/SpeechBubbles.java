package com.development.knowledgehut.urbies.DrawableObjects;


import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;

import com.development.knowledgehut.urbies.Frameworks.Graphics;
import com.development.knowledgehut.urbies.Implementations.AndroidGame;
import com.development.knowledgehut.urbies.Screens.Assets;

public class SpeechBubbles {
    private Bitmap bitmap;
    private String message;
    private String message2;
    private String message3;
    private int x, y;
    private int offsetY;
    private Paint text;

    public SpeechBubbles(Bitmap bitmap, String message,
                        String message2, String message3, int x, int y,
                        int offSetY){
        this.bitmap = bitmap;
        this.message = message;
        this.message2 = message2;
        this.message3 = message3;
        this.x = x;
        this.y = y;
        this.offsetY = offSetY;

        text = new Paint();
        text.setAntiAlias(true);
        text.setColor(Color.BLACK);
        text.setStyle(Paint.Style.FILL);
        text.setTextAlign(Paint.Align.LEFT);
        text.setTextSize((float)(int)(AndroidGame.deviceW * 0.04));
        text.setTypeface(Assets.typeface1);
    }

    public String getMessage(){
        return message + " " +message2 + " "+message3;
    }

    public void setMessage(String msg1, String msg2, String msg3){
        this.message = msg1;
        this.message2 = msg2;
        this.message3 = msg3;
    }

    public Bitmap getBitmap(){
        return bitmap;
    }

    public void setBitmap(Bitmap bmp){
        this.bitmap = bmp;
    }

    public int getX(){
        return x;
    }

    public void setX(int x){
        this.x = x;
    }

    public int getY(){
        return y;
    }

    public void setY(int y){
        this.y = y;
    }

    public void draw(Graphics graphics){
        graphics.drawBitmap(bitmap, x, y);
        graphics.drawText(message, x + (int)(10 *AndroidGame.GAME_SCALE_X), y +((int)(offsetY *AndroidGame.GAME_SCALE_X)), text);
        graphics.drawText(message2, x + (int)(10 *AndroidGame.GAME_SCALE_X), y +((int)(offsetY *AndroidGame.GAME_SCALE_X))+(int)(text.getTextSize()), text);
        graphics.drawText(message3, x + (int)(10 *AndroidGame.GAME_SCALE_X), y +((int)(offsetY * AndroidGame.GAME_SCALE_X))+(int)(text.getTextSize())+(int)(text.getTextSize()), text);
    }
}
