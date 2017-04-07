package com.development.knowledgehut.urbies.Screens;


import android.view.MotionEvent;

import com.development.knowledgehut.urbies.Frameworks.Game;
import com.development.knowledgehut.urbies.Frameworks.Graphics;
import com.development.knowledgehut.urbies.Frameworks.Screen;

import static com.development.knowledgehut.urbies.Implementations.AndroidGame.OFFSET_Y;

class StatusScreen extends Screen {
    long startTime;

    StatusScreen(Game game){
        super(game);
        startTime = System.currentTimeMillis();
    }
    @Override
    public void update(float deltaTime) {
        if(Urbies.level < 0){
            if(System.currentTimeMillis() > (startTime + 1000)){
                game.setScreen(new PlayScreen(game));
            }
        }
    }

    @Override
    public void render(float deltaTime) {
        Graphics graphics = game.getGraphics();
        graphics.clear(0);
        graphics.drawBitmap(Assets.background, 0, OFFSET_Y);
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

    }

    @Override
    public void doFlingEvent(MotionEvent e1, MotionEvent e2) {

    }

    @Override
    public void input(MotionEvent event) {

    }
}
