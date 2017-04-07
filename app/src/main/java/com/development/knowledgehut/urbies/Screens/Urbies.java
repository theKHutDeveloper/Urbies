package com.development.knowledgehut.urbies.Screens;


import com.development.knowledgehut.urbies.Implementations.AndroidGame;
import com.development.knowledgehut.urbies.Frameworks.Screen;

public class Urbies extends AndroidGame {
    public static final int WIDTH = 320, HEIGHT = 480;
    public static boolean tutorialLevel = false;
    public static int level;
    public static final float DELTA_TIME = 30;

    public enum UrbieType{
        BABY, PAC, PIGTAILS, NERD, PUNK, LADY, ROCKER,
        GIRL_NERD, MAGICIAN, WHITE_CHOCOLATE,
        STRIPE_HORIZONTAL, STRIPE_VERTICAL, MAGIC_BOMB, NONE
    }

    public enum UrbieStatus{
        NONE, GLASS, CAGED, POISONED, HIDDEN, CEMENT, WOODEN, SINGLE_TILE, DOUBLE_TILE
    }

    public enum VisibilityStatus{
        VISIBLE, INVISIBLE
    }

    public enum MatchShape{
        NONE, LINE, LINE_OF_FOUR_VERTICAL, LINE_OF_FOUR_HORIZONTAL,
        LINE_OF_FIVE_OR_MORE, L_OR_T_SHAPE
    }

    public Screen getStartScreen(){
        return new LoadingScreen(this);
    }
}
