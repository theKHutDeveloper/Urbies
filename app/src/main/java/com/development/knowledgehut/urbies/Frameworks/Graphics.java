package com.development.knowledgehut.urbies.Frameworks;


import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.Matrix;
import android.graphics.Paint;
import android.graphics.Path;
import android.graphics.Rect;
import android.graphics.Typeface;
import android.graphics.drawable.Drawable;

public interface Graphics {
    public static enum BitmapFormat {
        ARGB8888, ARGB4444, RGB565
    }


    public Bitmap animatedBitmap(Context context, Drawable drawable, int frames, BitmapFormat format);
    public Bitmap scaledBitmap(Context context, Drawable drawable, BitmapFormat bitmapFormat);
    public Typeface setTypeface(Context context, String file);
    public void clear(int colour);
    public void drawBitmap(Bitmap bitmap, int x, int y);
    public void drawBitmap(Bitmap bitmap, Matrix matrix, Paint paint);
    public void drawBitmap(Bitmap bitmap, Rect src, Rect dst, Paint paint);
    public void drawPath(Path path, Paint paint);
    public void drawText(String text, int x, int y, Paint paint);
    public int getWidth();
}
