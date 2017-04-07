package com.development.knowledgehut.urbies.Screens;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.MotionEvent;

import com.development.knowledgehut.urbies.Frameworks.Game;
import com.development.knowledgehut.urbies.Frameworks.Graphics;
import com.development.knowledgehut.urbies.Frameworks.Screen;
import com.development.knowledgehut.urbies.DrawableObjects.Images;

import java.util.ArrayList;

import static com.development.knowledgehut.urbies.Implementations.AndroidGame.OFFSET_Y;


public class LevelScreen extends Screen {

    private Images holder;
    private ArrayList<Images> buttons = new ArrayList<>();
    private int access_level;

    LevelScreen(Game game){
        super(game);

        Coordinates coordinates = new Coordinates();

        SharedPreferences preferences = game.getFileIO().getPreferences();
        access_level = preferences.getInt(Assets.ACCESS_LEVEL, 1);


        holder = new Images(Assets.holder, new Point(coordinates.getX(5), coordinates.getY(5) + OFFSET_Y));
        Point pos1 = new Point(coordinates.getX(70), coordinates.getY(130)+ OFFSET_Y);

        for(int i = 0; i < 12; i++){
            if(access_level < (i + 1)){
                buttons.add(new Images(Assets.padlock, pos1));
            }
            else{
                buttons.add(new Images(Assets.button, pos1));
            }
            if(i == 3 || i == 7){
                int change = pos1.y;
                pos1 = new Point(coordinates.getX(50), change + buttons.get(i).getHeight() + coordinates.getY(10));
            }
            else{
                int changeX = pos1.x;
                int changeY = pos1.y;
                pos1 = new Point(changeX + buttons.get(i).getWidth() + coordinates.getX(10), changeY);
            }
        }

    }

    @Override
    public void update(float deltaTime) {

    }

    @Override
    public void render(float deltaTime) {
        Graphics graphics = game.getGraphics();
        graphics.clear(0);

        graphics.drawBitmap(Assets.background2,0, OFFSET_Y);
        holder.draw(graphics);

        for(int i = 0; i < buttons.size(); i++){
            buttons.get(i).draw(graphics);
        }
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {

    }

    @Override
    public void onStandardTouch(MotionEvent event) {
        int x = (int)event.getX();
        int y = (int)event.getY();
        if(event.getAction() == MotionEvent.ACTION_DOWN){
            for(int b = 0; b < buttons.size(); b++){
                if(buttons.get(b).isSelected(x, y)){
                    if(access_level >= (b+1)){
                        Urbies.level = b+1;
                        Urbies.tutorialLevel = false;
                        game.setScreen(new PlayScreen(game));
                        return;
                    }
                }
            }
        }
    }

    @Override
    public void doFlingEvent(MotionEvent e1, MotionEvent e2) {

    }

    @Override
    public void input(MotionEvent event) {
        onStandardTouch(event);
    }
}
