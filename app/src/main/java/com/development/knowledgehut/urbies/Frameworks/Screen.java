package com.development.knowledgehut.urbies.Frameworks;


import android.view.MotionEvent;

public abstract class Screen {
    protected final Game game;

    public Screen(Game game){
        this.game = game;
    }

    public abstract void update(float deltaTime);

    public abstract void render(float deltaTime);

    public  abstract void pause();

    public abstract  void resume();

    public abstract  void dispose();

    public abstract void onStandardTouch(MotionEvent event);

    public abstract void doFlingEvent(MotionEvent e1, MotionEvent e2);

    public abstract void input(MotionEvent event);
}
