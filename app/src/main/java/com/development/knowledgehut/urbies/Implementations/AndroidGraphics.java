package com.development.knowledgehut.urbies.Implementations;

import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

import com.development.knowledgehut.urbies.Frameworks.Graphics;
import static com.development.knowledgehut.urbies.Implementations.AndroidGame.GAME_SCALE_X;


public class AndroidGraphics implements Graphics {
    private Bitmap frameBuffer;
    private Canvas canvas;


    AndroidGraphics(Bitmap frameBuffer){
        this.frameBuffer = frameBuffer;
        this.canvas = new Canvas(frameBuffer);
    }

    @Override
    public Typeface setTypeface(Context context, String file) {
        return Typeface.createFromAsset(context.getAssets(), file);
    }

    @Override
    public Bitmap scaledBitmap(Context context, Drawable drawable, BitmapFormat bitmapFormat) {
        Bitmap.Config config;
        Bitmap bitmap;

        if(bitmapFormat == BitmapFormat.RGB565) config = Bitmap.Config.RGB_565;
        else if(bitmapFormat == BitmapFormat.ARGB4444) config = Bitmap.Config.ARGB_4444;
        else config = Bitmap.Config.ARGB_8888;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;

        Bitmap temp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), config);

        bitmap = Bitmap.createScaledBitmap(temp, (Math.round(temp.getWidth() * GAME_SCALE_X)),
                (Math.round(temp.getHeight() * GAME_SCALE_X)), true);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    @Override
    public Bitmap animatedBitmap(Context context, Drawable drawable, int frames, BitmapFormat format) {
        Bitmap.Config config;
        Bitmap bitmap;

        if(format == BitmapFormat.RGB565) config = Bitmap.Config.RGB_565;
        else if(format == BitmapFormat.ARGB4444) config = Bitmap.Config.ARGB_4444;
        else config = Bitmap.Config.ARGB_8888;

        BitmapFactory.Options options = new BitmapFactory.Options();
        options.inPreferredConfig = config;

        Bitmap temp = Bitmap.createBitmap(drawable.getIntrinsicWidth(), drawable.getIntrinsicHeight(), config);

        int width = (int)(temp.getWidth()*GAME_SCALE_X);
        width = width / frames;
        width = width * frames;
        bitmap = Bitmap.createScaledBitmap(temp, width,
                (int)(temp.getHeight()*GAME_SCALE_X), true);

        Canvas canvas = new Canvas(bitmap);
        drawable.setBounds(0, 0, canvas.getWidth(), canvas.getHeight());
        drawable.draw(canvas);

        return bitmap;
    }

    public void drawText(String text, int x, int y, Paint paint){
        canvas.drawText(text, x, y, paint);
    }

    @Override
    public void clear(int colour) {
        canvas.drawRGB((colour & 0xff0000) >> 16, (colour & 0xff00) >> 8,(colour & 0xff));
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint){
        canvas.drawBitmap(bitmap, matrix, paint);
    }

    @Override
    public void drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint) {
        canvas.drawBitmap(bitmap, src,dst, paint);
    }

    @Override
    public void drawBitmap(Bitmap bitmap, int x, int y) {
        canvas.drawBitmap(bitmap, x, y, null);
    }

    @Override
    public void drawPath(Path path, Paint paint) {
        canvas.drawPath(path, paint);
    }

    @Override
    public int getWidth() {
        return frameBuffer.getWidth();
    }
}
