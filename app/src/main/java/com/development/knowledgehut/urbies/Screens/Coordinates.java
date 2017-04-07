package com.development.knowledgehut.urbies.Screens;

import android.graphics.Point;

import static com.development.knowledgehut.urbies.Implementations.AndroidGame.GAME_SCALE_X;


public class Coordinates {
    public Point calcPosition(int x, int y){
        Point position = new Point();

        position.x = (Math.round(GAME_SCALE_X * x));
        position.y = (Math.round(GAME_SCALE_X * y));

        return position;
    }

    public int getX(int x){
        return (Math.round(GAME_SCALE_X * x));
    }

    public int getY(int y){
        return (Math.round(GAME_SCALE_X * y));
    }
}
