package com.development.knowledgehut.urbies.Screens;

import android.content.SharedPreferences;
import android.graphics.Point;
import android.view.MotionEvent;

import com.development.knowledgehut.urbies.Frameworks.Game;
import com.development.knowledgehut.urbies.Frameworks.Graphics;
import com.development.knowledgehut.urbies.Frameworks.Screen;
import static com.development.knowledgehut.urbies.Implementations.AndroidGame.OFFSET_Y;


public class MenuScreen extends Screen {

    private Point playPos;
    private Point tutorialPos;

    MenuScreen(Game game){
        super(game);

        Coordinates coordinates = new Coordinates();

        playPos = new Point(coordinates.getX(110),
                coordinates.getY(260)+OFFSET_Y);

        tutorialPos = new Point(coordinates.getX(230),
                coordinates.getY(410)+OFFSET_Y);

        SharedPreferences preferences = game.getFileIO().getPreferences();
        int access_level = preferences.getInt(Assets.ACCESS_LEVEL, 100);
        boolean musicOn = preferences.getBoolean(Assets.MUSIC_PREF, true);
        boolean soundOn = preferences.getBoolean(Assets.SOUND_PREF, true);
    }

    @Override
    public void update(float deltaTime) {
    }

    @Override
    public void render(float deltaTime) {

        Graphics graphics = game.getGraphics();
        graphics.clear(0);

        graphics.drawBitmap(Assets.title,0, OFFSET_Y);
        graphics.drawBitmap(Assets.play, playPos.x, playPos.y);
        graphics.drawBitmap(Assets.tutorial, tutorialPos.x, tutorialPos.y);

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
    public void onStandardTouch(MotionEvent event){
        Coordinates coordinates = new Coordinates();

        int event_action = event.getAction();
        int x = (int)event.getX();
        int y = (int)event.getY();

        switch(event_action){
            case MotionEvent.ACTION_DOWN:
                if (x > playPos.x &&  x <= playPos.x + (coordinates.getX(Assets.play.getWidth())) &&
                        y > playPos.y && y <= playPos.y + (coordinates.getY(Assets.play.getHeight()))) {
                    game.setScreen(new LevelScreen(game));

                }

                else if(x > tutorialPos.x && x <= tutorialPos.x + (coordinates.getX(Assets.tutorial.getWidth())) &&
                        y > tutorialPos.y && y <= tutorialPos.y + (coordinates.getY(Assets.tutorial.getHeight()))) {
                    Urbies.tutorialLevel = true;
                    Urbies.level = -1;
                    game.setScreen(new PlayScreen(game));

                }
            break;
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
