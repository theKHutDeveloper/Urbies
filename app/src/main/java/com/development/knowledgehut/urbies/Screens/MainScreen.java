package com.development.knowledgehut.urbies.Screens;


import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.development.knowledgehut.urbies.Behaviours.GameMethods;
import com.development.knowledgehut.urbies.DrawableObjects.BitmapAnimation;
import com.development.knowledgehut.urbies.DrawableObjects.Images;
import com.development.knowledgehut.urbies.DrawableObjects.SpecialFX;
import com.development.knowledgehut.urbies.DrawableObjects.UrbieAnimation;
import com.development.knowledgehut.urbies.Frameworks.Game;
import com.development.knowledgehut.urbies.Frameworks.Graphics;
import com.development.knowledgehut.urbies.Frameworks.Screen;
import com.development.knowledgehut.urbies.Implementations.AndroidGame;
import com.development.knowledgehut.urbies.Objects.BaseTiles;
import com.development.knowledgehut.urbies.Objects.LevelManager;
import com.development.knowledgehut.urbies.Objects.MatchedDetails;
import com.development.knowledgehut.urbies.Objects.ObjectPathCreator;
import com.development.knowledgehut.urbies.Objects.Obstacles;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.development.knowledgehut.urbies.Implementations.AndroidGame.OFFSET_Y;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.GLASS;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.NONE;
import static java.util.Collections.reverseOrder;

public class MainScreen extends Screen {

    private enum MatchState {
        AUTO, READY, SWAP, RESET_SWAP, USER_MATCH, SHUFFLE, SPECIAL_URBS
    }

    private enum Procedure {
        CHECK, MATCH, REPLACE_OBJECTS
    }

    private GameMethods gameMethods = new GameMethods();
    private LevelManager levelManager;
    private ArrayList<Point> tileLocations;
    private List<UrbieAnimation> Urbs = new ArrayList<>();
    private List<BitmapAnimation> matchedUrbs = new ArrayList<>();
    private ArrayList<Integer> userMatchOne = new ArrayList<>();
    private ArrayList<Integer> urbMatchOne = new ArrayList<>();
    private ArrayList<Integer> userMatchTwo = new ArrayList<>();
    private ArrayList<Integer> objectsToMoveDown = new ArrayList<>();
    private ArrayList<Integer> urbsToMoveDown = new ArrayList<>();
    private ArrayList<Point> coordinatesToMoveTo = new ArrayList<>();
    private ArrayList<Integer> validTiles;
    private ArrayList<Urbies.UrbieType> urbTypesInLevel = new ArrayList<>();
    private ArrayList<Images> urbGUI = new ArrayList<>();
    private ArrayList<Integer> specialUrbs = new ArrayList<>();
    private ArrayList<Urbies.UrbieType> specialUrbTypes = new ArrayList<>();
    private ArrayList<Point> futureCoordinates = new ArrayList<>();
    private ArrayList<Integer> futurePositions = new ArrayList<>();
    private ArrayList<BitmapAnimation> effects = new ArrayList<>();
    private ArrayList<Integer> matchesOffScreen = new ArrayList<>();
    private ArrayList<Integer> entrance = new ArrayList<>();
    private ArrayList<Integer> possibleMatches = new ArrayList<>();
    private ArrayList<Integer> urbPossibleMatches = new ArrayList<>();

    private Images pause, help, board;
    private MatchState matchState;
    private Procedure pState;

    private Paint text;
    private Images victory, failed;
    private int one = -1, two = -1;
    private int urbOne, urbTwo;
    private Urbies.UrbieType urbOneType, urbTwoType;
    private int initialise = 0;
    private int tileH;
    private int tileWidth, tileHeight;
    private long startBounceOutTime, readyToMoveTimer, moveToNextScreenTimer;
    private boolean canBounce = false;
    private boolean showPossibleMove = false;
    private boolean noValidSwap = false;
    private ArrayList<SpecialFX> specialFX = new ArrayList<>();
    private boolean allowSpecialUserInput = false;
    private int userClicksUrb, specialUrbUserObject;
    private Urbies.UrbieType userSwapType;
    private int setSpecialPhase = 0;
    private long startRotateTimer;
    private int urbCounter = 0;
    private boolean startRotateAvailableUrbs = false;
    private Bitmap bmpSelected = null;

    private ArrayList<Obstacles> obstacleTiles = new ArrayList<>();
    private SharedPreferences preferences;

    public MainScreen(Game game) {
        super(game);

        preferences = game.getFileIO().getPreferences();
        levelManager = new LevelManager(Urbies.level);
        LevelManager.MapData levelTileMap = levelManager.getLevelTileMap();
        BaseTiles baseTiles = new BaseTiles(
                levelTileMap.getMapLevel(),
                levelTileMap.getWidth(), levelTileMap.getHeight(),
                Assets.tile);

        tileWidth = levelTileMap.getWidth();
        tileHeight = levelTileMap.getHeight();
        tileLocations = baseTiles.getTileLocations();
        validTiles = baseTiles.getValidTileLocations();
        tileH = baseTiles.getIndividualTileWidth();

        randomUrbsPlayingInLevel(levelManager.getUrbsInLevel());
        Urbs = createUrbies(Urbs, validTiles, validTiles.size(), 10, 5);
        if (levelManager.isGlass()) {
            ArrayList<Integer> locations = new ArrayList<>(levelManager.obstacleTileLocation());
            for (int i = 0; i < locations.size(); i++) {
                int pos = gameMethods.findBitmapByMapLocation(Urbs, tileLocations, locations.get(i));

                obstacleTiles.add(new Obstacles(
                        new BitmapAnimation(Assets.glassTile, new Point(Urbs.get(pos).getX(), Urbs.get(pos).getY()), 20, 1, 2000, true, locations.get(i), true),
                        GLASS, Urbies.VisibilityStatus.VISIBLE)
                );
                Urbs.get(pos).setStatus(GLASS);
            }
        } else if (levelManager.isWood()) {
            ArrayList<Integer> locations = new ArrayList<>(levelManager.obstacleTileLocation());
            for (int i = 0; i < locations.size(); i++) {
                int pos = gameMethods.findBitmapByMapLocation(Urbs, tileLocations, locations.get(i));
                obstacleTiles.add(new Obstacles(
                        new BitmapAnimation(Assets.wood_100, new Point(Urbs.get(pos).getX(), Urbs.get(pos).getY()), 20, 1, 2000, true, locations.get(i), true),
                        Urbies.UrbieStatus.WOODEN, Urbies.VisibilityStatus.INVISIBLE)
                );
                Urbs.get(pos).setStatus(Urbies.UrbieStatus.WOODEN);
                Urbs.get(pos).setVisible(Urbies.VisibilityStatus.INVISIBLE);
            }
        } else if (levelManager.isCement()) {
            ArrayList<Integer> locations = new ArrayList<>(levelManager.obstacleTileLocation());
            for (int i = 0; i < locations.size(); i++) {
                int pos = gameMethods.findBitmapByMapLocation(Urbs, tileLocations, locations.get(i));
                obstacleTiles.add(new Obstacles(
                        new BitmapAnimation(Assets.cement_100, new Point(Urbs.get(pos).getX(), Urbs.get(pos).getY()), 20, 1, 2000, true, locations.get(i), true),
                        Urbies.UrbieStatus.CEMENT, Urbies.VisibilityStatus.INVISIBLE)
                );
                Urbs.get(pos).setStatus(Urbies.UrbieStatus.CEMENT);
                Urbs.get(pos).setVisible(Urbies.VisibilityStatus.INVISIBLE);
            }
        }
        matchState = MatchState.AUTO;
        pState = Procedure.CHECK;
        levelManager.startTimer();

        loadGameText();
        loadGui();
    }


    @Override
    public void update(float deltaTime) {
        switch (matchState) {

            case AUTO:
                switch (pState) {
                    case CHECK:
                        ArrayList<MatchedDetails> details;
                        details = gameMethods.findAutomaticMatches(Urbs, tileWidth, levelManager.getLevelTileMap().getMapLevel(), obstacleTiles);
                        ArrayList<ArrayList<Integer>> matchList = new ArrayList<>();

                        //with the remaining matches remove the intersecting urb for matches with 4 or more elements
                        if (!details.isEmpty()) {
                            for (int i = 0; i < details.size(); i++) {
                                if (!details.get(i).getReturnedMatches().isEmpty() && details.get(i).getMatchShape() != Urbies.MatchShape.LINE) {
                                    int element = gameMethods.findBitmapByMapLocation(Urbs, tileLocations, details.get(i).getIntersecting_element());
                                    levelManager.addUrbCounter(Urbs.get(element).getType(), 1);
                                    specialUrbs.add(element);
                                    specialUrbTypes.add(details.get(i).getUrbieType());
                                    int index = details.get(i).getReturnedMatches().indexOf(details.get(i).getIntersecting_element());
                                    if (index > -1) {
                                        details.get(i).getReturnedMatches().remove(index);
                                    }
                                }
                            }
                        }

                        int specialRules = -1;
                        if (!details.isEmpty()) {
                            for (int i = 0; i < details.size(); i++) {
                                //if 3 of the same special urbs match apply special rule (all urbs out)
                                if (!details.get(i).getReturnedMatches().isEmpty()) {
                                    int pos = gameMethods.findObjectByPosition(details.get(i).getReturnedMatches().get(0), Urbs);
                                    if (Urbs.get(pos).getType() == Urbies.UrbieType.WHITE_CHOCOLATE ||
                                            Urbs.get(pos).getType() == Urbies.UrbieType.STRIPE_HORIZONTAL ||
                                            Urbs.get(pos).getType() == Urbies.UrbieType.STRIPE_VERTICAL ||
                                            Urbs.get(pos).getType() == Urbies.UrbieType.MAGICIAN) {
                                        specialRules = i;
                                    }
                                }
                            }
                        }


                        if (specialRules > -1) {
                            //special rule pops out all urbs.
                            details.clear();
                            specialUrbs.clear();
                            Collections.addAll(matchList, validTiles);
                            levelManager.addBigPopToScore(validTiles.size());
                        } else {
                            for (int i = 0; i < details.size(); i++) {
                                levelManager.addMatchesToScore(details.get(i).getMatchShape(), details.get(i).getReturnedMatches().size());
                            }
                        }

                        if (!details.isEmpty()) {
                            for (int i = 0; i < details.size(); i++) {
                                matchList.add(details.get(i).getReturnedMatches());
                            }
                        }

                        if (!matchList.isEmpty()) {
                            for (int i = 0; i < matchList.size(); i++) {
                                userMatchOne.addAll(matchList.get(i));
                            }

                            gameMethods.uniqueArrayIntegerList(userMatchOne);

                            matchFoundations();

                        } else {
                            initialise = 0;
                            matchState = MatchState.READY;
                        }

                        break;

                    case MATCH:
                        matchRoutine();
                        break;

                    case REPLACE_OBJECTS:
                        replaceEvents();
                        pState = Procedure.CHECK;
                        break;
                }

            break;




            case SHUFFLE:
                if (initialise == 1) {
                    ArrayList<Integer> newLocations = getArrayLocations();
                    moveUrbsToShuffledLocations(newLocations, Urbs, tileLocations);
                    initialise = 2;
                }

                if (initialise == 3) {
                    for (int i = 0; i < Urbs.size(); i++) {
                        Urbs.get(i).clearPath();
                    }
                    initialise = 0;
                    matchState = MatchState.AUTO;
                }

            break;


            case READY:

                if (levelManager.success()) {
                    if (initialise == 0) {
                        moveToNextScreenTimer = System.currentTimeMillis();
                        initialise = 1;
                    } else if (initialise == 1) {
                        if (System.currentTimeMillis() > moveToNextScreenTimer + 2000) {
                            int access_lvl = preferences.getInt(Assets.ACCESS_LEVEL, 1);
                            if (access_lvl < Urbies.level + 1) {
                                access_lvl++;
                                preferences.edit().putInt(Assets.ACCESS_LEVEL, access_lvl).apply();
                            }

                            game.setScreen(new LevelScreen(game));
                            return;
                        }
                    }

                } else if (levelManager.failing()) {
                    if (initialise == 0) {
                        moveToNextScreenTimer = System.currentTimeMillis();
                        initialise = 1;
                    } else if (initialise == 1) {
                        if (System.currentTimeMillis() > moveToNextScreenTimer + 2000) {
                            game.setScreen((new LevelScreen(game)));
                            return;
                        }
                    }
                } else {
                    if (initialise == 0) {
                        possibleMatches = gameMethods.findRandomPotentialMatch(
                                Urbs,
                                levelManager.getLevelTileMap().getMapLevel(),
                                tileWidth, obstacleTiles
                        );

                        for (int i = 0; i < Urbs.size(); i++) {
                            if (Urbs.get(i).getType() == Urbies.UrbieType.MAGICIAN ||
                                    Urbs.get(i).getType() == Urbies.UrbieType.STRIPE_HORIZONTAL ||
                                    Urbs.get(i).getType() == Urbies.UrbieType.STRIPE_VERTICAL ||
                                    Urbs.get(i).getType() == Urbies.UrbieType.WHITE_CHOCOLATE) {
                                urbPossibleMatches.add(i);
                            }
                        }

                        if (possibleMatches.isEmpty() && urbPossibleMatches.isEmpty()) {
                            matchState = MatchState.SHUFFLE;
                        } else {
                            readyToMoveTimer = System.currentTimeMillis();
                        }

                        initialise = 1;
                    }


                    if (System.currentTimeMillis() > (readyToMoveTimer + 6000)) {
                        if (!possibleMatches.isEmpty()) {
                            visualisePotentialMatch(possibleMatches);
                        } else if (!urbPossibleMatches.isEmpty()) {
                            visualiseSpecialUrb(urbPossibleMatches.get(0));
                        }
                        showPossibleMove = true;
                    }

                    if (System.currentTimeMillis() > (readyToMoveTimer + 8000)) {
                        if (!possibleMatches.isEmpty()) {
                            gameMethods.resetVisualisePotentialMatch(Urbs, possibleMatches);
                            showPossibleMove = false;
                        } else if (!urbPossibleMatches.isEmpty()) {
                            resetSpecialUrb(urbPossibleMatches.get(0));
                        }
                        readyToMoveTimer = System.currentTimeMillis();
                        showPossibleMove = false;
                    }
                }
                break;


            case SWAP:

                if (initialise == 0) {
                    Urbs.get(urbOne).findLine(
                            Urbs.get(urbOne).getX(), Urbs.get(urbOne).getY(), Urbs.get(urbTwo).getX(), Urbs.get(urbTwo).getY());
                    Urbs.get(urbTwo).findLine(
                            Urbs.get(urbTwo).getX(), Urbs.get(urbTwo).getY(), Urbs.get(urbOne).getX(), Urbs.get(urbOne).getY());
                    initialise = 1;
                }

                if (initialise == 2) {
                    if (gameMethods.validSwap(Urbs, urbOne, urbTwo)) {
                        int temp = Urbs.get(urbOne).getLocation();
                        Urbs.get(urbOne).setLocation(Urbs.get(urbTwo).getLocation());
                        Urbs.get(urbTwo).setLocation(temp);
                        Collections.swap(Urbs, urbOne, urbTwo);

                        //identify whether swap of a special urb takes place BEFORE doing anything else
                        boolean result = false;
                        boolean isOne = false, isTwo = false;
                        if (hasSpecialObjectBeenSwapped(Urbs, urbOne)) {
                            urbOneType = Urbs.get(urbOne).getType();
                            isOne = true;
                        }

                        if (hasSpecialObjectBeenSwapped(Urbs, urbTwo)) {
                            urbTwoType = Urbs.get(urbTwo).getType();
                            isTwo = true;
                        }

                        if (isOne || isTwo) result = true;

                        if (result) {
                            initialise = 0;
                            levelManager.deductMoveCounter();
                            matchState = MatchState.SPECIAL_URBS;
                            pState = Procedure.CHECK;
                        }

                        if (!result) {
                            MatchedDetails details;

                            details = gameMethods.combineMatches(gameMethods.findUserMatchesByRow(Urbs, one, tileWidth, levelManager.getLevelTileMap().getMapLevel(), obstacleTiles),
                                    gameMethods.findUserMatchesByColumn(Urbs, one, tileWidth, levelManager.getLevelTileMap().getMapLevel(), obstacleTiles),
                                    tileWidth, obstacleTiles, one, two);


                            //store the urb that will represent a special urb
                            if (!details.getReturnedMatches().isEmpty() && details.getMatchShape() != Urbies.MatchShape.LINE) {
                                int element = gameMethods.findBitmapByMapLocation(Urbs, tileLocations, details.getIntersecting_element());
                                levelManager.addUrbCounter(Urbs.get(element).getType(), 1);
                                specialUrbs.add(element);
                                specialUrbTypes.add(details.getUrbieType());
                                int index = details.getReturnedMatches().indexOf(details.getIntersecting_element());
                                if (index > -1) {
                                    details.getReturnedMatches().remove(index);
                                }
                            }

                            if (!details.getReturnedMatches().isEmpty()) {
                                levelManager.addMatchesToScore(details.getMatchShape(), details.getReturnedMatches().size());
                            }

                            ArrayList<Integer> matchOne = details.getReturnedMatches();

                            MatchedDetails details2;
                            details2 = gameMethods.combineMatches(
                                    gameMethods.findUserMatchesByRow(Urbs, two, tileWidth,
                                            levelManager.getLevelTileMap().getMapLevel(), obstacleTiles),
                                    gameMethods.findUserMatchesByColumn(Urbs, two, tileWidth,
                                            levelManager.getLevelTileMap().getMapLevel(), obstacleTiles),
                                    tileWidth, obstacleTiles, two, one);


                            if (!details2.getReturnedMatches().isEmpty() && details2.getMatchShape() != Urbies.MatchShape.LINE) {
                                int element = gameMethods.findBitmapByMapLocation(Urbs, tileLocations, details2.getIntersecting_element());
                                levelManager.addUrbCounter(Urbs.get(element).getType(), 1);
                                specialUrbs.add(element);
                                specialUrbTypes.add(details2.getUrbieType());
                                int index = details2.getReturnedMatches().indexOf(details2.getIntersecting_element());
                                if (index > -1) {
                                    details2.getReturnedMatches().remove(index);
                                }
                            }

                            if (!details2.getReturnedMatches().isEmpty()) {
                                levelManager.addMatchesToScore(details2.getMatchShape(), details2.getReturnedMatches().size());
                            }
                            ArrayList<Integer> matchTwo = details2.getReturnedMatches();

                            if (matchOne.isEmpty() && matchTwo.isEmpty()) {
                                initialise = 0;
                                Urbs.get(urbOne).clearPath();
                                Urbs.get(urbTwo).clearPath();
                                Urbs.get(urbOne).resetCounter();
                                Urbs.get(urbTwo).resetCounter();

                                matchState = MatchState.RESET_SWAP;
                            } else {
                                Urbs.get(urbOne).clearPath();
                                Urbs.get(urbTwo).clearPath();
                                levelManager.deductMoveCounter();
                                clearUserSelection();
                                initialise = 0;
                                storeMatchList(matchOne, matchTwo);
                                pState = Procedure.CHECK;
                                matchState = MatchState.USER_MATCH;
                            }
                        }

                    } else {
                        Urbs.get(urbOne).clearPath();
                        Urbs.get(urbTwo).clearPath();
                        initialise = 0;
                        noValidSwap = true;
                        matchState = MatchState.RESET_SWAP;
                    }
                }
                break;

            case RESET_SWAP:
                if (initialise == 0) {

                    Urbs.get(urbOne).findLine(
                            Urbs.get(urbOne).getX(),
                            Urbs.get(urbOne).getY(),
                            Urbs.get(urbTwo).getX(),
                            Urbs.get(urbTwo).getY()
                    );
                    Urbs.get(urbTwo).findLine(
                            Urbs.get(urbTwo).getX(),
                            Urbs.get(urbTwo).getY(),
                            Urbs.get(urbOne).getX(),
                            Urbs.get(urbOne).getY()
                    );
                    initialise = 1;
                }
                if (initialise == 2) {

                    Urbs.get(urbOne).clearPath();
                    Urbs.get(urbTwo).clearPath();

                    if (!noValidSwap) {
                        Collections.swap(Urbs, urbOne, urbTwo);
                        int temp = Urbs.get(urbOne).getLocation();
                        Urbs.get(urbOne).setLocation(Urbs.get(urbTwo).getLocation());
                        Urbs.get(urbTwo).setLocation(temp);
                    }

                    clearUserSelection();
                    initialise = 0;
                    matchState = MatchState.READY;
                }

            break;

            case USER_MATCH:
                switch (pState) {
                    case CHECK:
                        //merge the two lists or put the list into userMatchOne
                        if (!userMatchOne.isEmpty() && !userMatchTwo.isEmpty()) {
                            userMatchOne.addAll(userMatchTwo);
                            userMatchTwo.clear();
                            Collections.sort(userMatchOne, reverseOrder());
                        } else if (userMatchOne.isEmpty() && !userMatchTwo.isEmpty()) {
                            userMatchOne.addAll(userMatchTwo);
                            userMatchTwo.clear();
                            Collections.sort(userMatchOne, reverseOrder());
                        }

                        matchFoundations();

                    break;

                    case MATCH:
                        matchRoutine();

                    break;


                    case REPLACE_OBJECTS:
                        replaceEvents();
                        matchState = MatchState.AUTO;
                        pState = Procedure.CHECK;

                    break;
                }
            break;
        }
    }

    @Override
    public void render(float deltaTime) {

        Graphics graphics = game.getGraphics();
        graphics.clear(0);

        graphics.drawBitmap(Assets.background, 0, OFFSET_Y);
        pause.draw(graphics);
        help.draw(graphics);
        board.draw(graphics);

        for (int i = 0; i < urbGUI.size(); i++) {
            urbGUI.get(i).draw(graphics);
        }

        graphics.drawText(Integer.toString(levelManager.getUrbCounter(Urbies.UrbieType.PAC)), 0, (int) (470 * AndroidGame.GAME_SCALE_X), text);
        graphics.drawText(Integer.toString(levelManager.getUrbCounter(Urbies.UrbieType.BABY)), (int) (45 * AndroidGame.GAME_SCALE_X), (int) (470 * AndroidGame.GAME_SCALE_X), text);
        graphics.drawText(Integer.toString(levelManager.getUrbCounter(Urbies.UrbieType.LADY)), (int) (85 * AndroidGame.GAME_SCALE_X), (int) (470 * AndroidGame.GAME_SCALE_X), text);
        graphics.drawText(Integer.toString(levelManager.getUrbCounter(Urbies.UrbieType.NERD)), (int) (125 * AndroidGame.GAME_SCALE_X), (int) (470 * AndroidGame.GAME_SCALE_X), text);
        graphics.drawText(Integer.toString(levelManager.getUrbCounter(Urbies.UrbieType.PUNK)), (int) (165 * AndroidGame.GAME_SCALE_X), (int) (470 * AndroidGame.GAME_SCALE_X), text);
        graphics.drawText(Integer.toString(levelManager.getUrbCounter(Urbies.UrbieType.PIGTAILS)), (int) (205 * AndroidGame.GAME_SCALE_X), (int) (470 * AndroidGame.GAME_SCALE_X), text);
        graphics.drawText(Integer.toString(levelManager.getUrbCounter(Urbies.UrbieType.GIRL_NERD)), (int) (245 * AndroidGame.GAME_SCALE_X), (int) (470 * AndroidGame.GAME_SCALE_X), text);
        graphics.drawText(Integer.toString(levelManager.getUrbCounter(Urbies.UrbieType.ROCKER)), (int) (285 * AndroidGame.GAME_SCALE_X), (int) (470 * AndroidGame.GAME_SCALE_X), text);
        graphics.drawText(Integer.toString(levelManager.getCementCounter()), (int) (85 * AndroidGame.GAME_SCALE_X), (int) (70 * AndroidGame.GAME_SCALE_X), text);


        for (int i = 0; i < validTiles.size(); i++) {
            graphics.drawBitmap(Assets.tile, tileLocations.get(validTiles.get(i)).x, tileLocations.get(validTiles.get(i)).y);
        }

        graphics.drawText(Integer.toString(levelManager.getScore()), 500, (int) (50 * AndroidGame.GAME_SCALE_X), text);

        if (levelManager.isMoveLevel()) {
            graphics.drawText(Integer.toString(levelManager.getMoves()), 800, (int) (50 * AndroidGame.GAME_SCALE_X), text);
        }

        if (levelManager.isTimedLevel()) {
            graphics.drawText(Integer.toString(levelManager.getTimeRemaining()), 800, (int) (50 * AndroidGame.GAME_SCALE_X), text);
        }

        if (urbOne > 0) {
            graphics.drawBitmap(Assets.selector, tileLocations.get(one).x, tileLocations.get(one).y);
        }

        if (urbTwo > 0) {
            graphics.drawBitmap(Assets.selector, tileLocations.get(two).x, tileLocations.get(two).y);
        }


        for (int i = 0; i < Urbs.size(); i++) {
            if (Urbs.get(i).getStatus() != NONE) {
                Urbs.get(i).draw(graphics);
            }
        }

        for (int i = 0; i < Urbs.size(); i++) {
            if (Urbs.get(i).getStatus() == NONE) {
                Urbs.get(i).draw(graphics);
            }
        }


        for (int i = 0; i < matchedUrbs.size(); i++) {
            if (!effects.isEmpty()) {
                effects.get(i).draw(graphics);
            }
            matchedUrbs.get(i).draw(graphics);
            if (canBounce) {
                matchedUrbs.get(i).bounceOut(deltaTime);
            }
        }

        if (!specialFX.isEmpty()) {
            for (int i = 0; i < specialFX.size(); i++) {
                specialFX.get(i).draw(graphics);
            }
        }

        if (matchState == MatchState.READY && initialise == 1 && levelManager.success()) {
            victory.draw(graphics);
        } else if (matchState == MatchState.READY && initialise == 1 && levelManager.failing()) {
            failed.draw(graphics);
        }

        if (matchState == MatchState.AUTO) {
            if (pState == Procedure.MATCH && initialise == 2) {

                if (!specialUrbs.isEmpty()) {
                    for (int s = 0; s < specialUrbs.size(); s++) {
                        if (Urbs.get(specialUrbs.get(s)).getScale() < 1f) {
                            Urbs.get(specialUrbs.get(s)).drawAnimation(graphics);
                        } else Urbs.get(specialUrbs.get(s)).draw(graphics);
                    }
                }

                int counter = 0;
                if (!urbsToMoveDown.isEmpty()) {
                    if (!urbMatchOne.isEmpty()) {
                        if (System.currentTimeMillis() > startBounceOutTime + 500) {
                            for (int i = 0; i < urbsToMoveDown.size(); i++) {
                                if (Urbs.get(urbsToMoveDown.get(i)).updatePath(deltaTime)) {
                                    counter++;
                                }
                            }
                        }
                    } else {
                        if (System.currentTimeMillis() > startBounceOutTime + 1200) {
                            for (int i = 0; i < urbsToMoveDown.size(); i++) {
                                if (Urbs.get(urbsToMoveDown.get(i)).updatePath(deltaTime)) {
                                    counter++;
                                }
                            }
                        }
                    }
                }


                //if there are no urbs to move down and replacements are equal to the size of the urb matches (this could be zero)
                //if there are no matchedUrbs OR if there are matchedUrbs and more than 2 seconds have passed increase variable.
                if (urbsToMoveDown.isEmpty()) {
                    if (!matchesOffScreen.isEmpty()) {
                        if (System.currentTimeMillis() > startBounceOutTime + 2000) {
                            initialise = 3;
                        }
                    } else {
                        initialise = 3;
                    }
                }
                //There are urbs to move down and replacements are equal to the size of the urb matches
                else if (counter == urbsToMoveDown.size()) {
                    initialise = 3;
                }
            }

            if (pState == Procedure.MATCH && initialise == 5) {
                int replacements = 0;

                if (!urbMatchOne.isEmpty()) {
                    if (System.currentTimeMillis() > startBounceOutTime + 600) {
                        for (int i = 0; i < urbMatchOne.size(); i++) {
                            if (Urbs.get(urbMatchOne.get(i)).updatePath(deltaTime)) {
                                replacements++;
                            }
                        }
                    }
                }

                if (replacements == urbMatchOne.size()) {
                    initialise = 6;
                }
            }
        }

        if (matchState == MatchState.SWAP) {
            if (initialise == 1) {
                if (Urbs.get(urbOne).updatePath(deltaTime) && Urbs.get(urbTwo).updatePath(deltaTime)) {
                    initialise = 2;
                }
            }
        }

        if (matchState == MatchState.RESET_SWAP) {
            if (initialise == 1) {
                if (Urbs.get(urbOne).updatePath(deltaTime) && Urbs.get(urbTwo).updatePath(deltaTime)) {
                    initialise = 2;
                }
            }
        }

        if (matchState == MatchState.SHUFFLE && initialise == 2) {
            int counter = 0;
            int notNoneCounter = 0;

            for (int i = 0; i < Urbs.size(); i++) {
                if (Urbs.get(i).getStatus() != NONE || (Urbs.get(i).getStatus() == NONE && Urbs.get(i).getActive() && Urbs.get(i).getLineIndex() == 0)) {
                    notNoneCounter++;
                }
            }

            for (int i = 0; i < Urbs.size(); i++) {
                if (Urbs.get(i).getStatus() == NONE && Urbs.get(i).getActive() && Urbs.get(i).getLineIndex() > 0) {
                    if (Urbs.get(i).updatePath(deltaTime)) {
                        counter++;
                    }
                } else if (Urbs.get(i).getStatus() == NONE && !Urbs.get(i).getActive()) {
                    counter++;
                }
            }

            if (counter == Urbs.size() - notNoneCounter) {
                initialise = 3;
            }
        }

        if (matchState == MatchState.USER_MATCH) {
            if (pState == Procedure.MATCH && initialise == 2) {

                if (!specialUrbs.isEmpty()) {
                    for (int s = 0; s < specialUrbs.size(); s++) {
                        if (Urbs.get(specialUrbs.get(s)).getScale() < 1f) {
                            Urbs.get(specialUrbs.get(s)).drawAnimation(graphics);
                        } else Urbs.get(specialUrbs.get(s)).draw(graphics);
                    }
                }

                int counter = 0;
                if (!urbsToMoveDown.isEmpty()) {
                    if (!urbMatchOne.isEmpty()) {
                        if (System.currentTimeMillis() > startBounceOutTime + 500) {
                            for (int i = 0; i < urbsToMoveDown.size(); i++) {
                                if (Urbs.get(urbsToMoveDown.get(i)).updatePath(deltaTime)) {
                                    counter++;
                                }
                            }
                        }
                    } else {
                        if (System.currentTimeMillis() > startBounceOutTime + 1200) {
                            for (int i = 0; i < urbsToMoveDown.size(); i++) {
                                if (Urbs.get(urbsToMoveDown.get(i)).updatePath(deltaTime)) {
                                    counter++;
                                }
                            }
                        }
                    }
                }


                //if there are no urbs to move down and replacements are equal to the size of the urb matches (this could be zero)
                //if there are no matchedUrbs OR if there are matchedUrbs and more than 2 seconds have passed increase variable.
                if (urbsToMoveDown.isEmpty()) { //&& replacements == urbMatchOne.size()) {
                    if (!matchesOffScreen.isEmpty()) {
                        if (System.currentTimeMillis() > startBounceOutTime + 2000) {
                            initialise = 3;
                        }
                    } else {
                        initialise = 3;
                    }
                }
                //There are urbs to move down and replacements are equal to the size of the urb matches
                else if (counter == urbsToMoveDown.size()) {// && replacements == urbMatchOne.size()) {
                    initialise = 3;
                }
            }

            if (pState == Procedure.MATCH && initialise == 5) {
                int replacements = 0;
                if (!urbMatchOne.isEmpty()) {
                    if (System.currentTimeMillis() > startBounceOutTime + 600) {
                        for (int i = 0; i < urbMatchOne.size(); i++) {
                            if (Urbs.get(urbMatchOne.get(i)).updatePath(deltaTime)) {
                                replacements++;
                            }
                        }
                    }
                }

                if (replacements == urbMatchOne.size()) {
                    initialise = 6;
                }
            }
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

    }

    @Override
    public void doFlingEvent(MotionEvent e1, MotionEvent e2) {

        int x1 = (int) e1.getX();
        int y1 = (int) e1.getY();

        int moveValue = 0;

        switch (gameMethods.getSlope(e1.getX(), e1.getY(), e2.getX(), e2.getY())) {
            case 1:
                moveValue = -tileWidth;

                break;
            case 2:
                moveValue = -1;
                break;
            case 3:
                moveValue = tileWidth;
                break;
            case 4:
                moveValue = 1;
                break;
        }

        if (!possibleMatches.isEmpty()) {
            gameMethods.resetVisualisePotentialMatch(Urbs, possibleMatches);
            showPossibleMove = false;
            readyToMoveTimer = System.currentTimeMillis();
        }

        for (int k = 0; k < Urbs.size(); k++) {
            if (Urbs.get(k).isSelected(x1, y1)) {
                one = Urbs.get(k).getLocation();
                urbOne = k;
                if ((one + moveValue) >= 0 && (one + moveValue) < levelManager.getLevelTileMap().getMapLevel().size() &&
                        levelManager.getLevelTileMap().getMapLevel().get(one + moveValue) == 1 &&
                        ((((one + moveValue) / tileWidth == one / tileWidth) || ((one + moveValue) % tileWidth == one % tileWidth)))) {
                    two = one + moveValue;
                    urbTwo = gameMethods.findSpecifiedLocation(Urbs, two);

                    initialise = 0;
                    urbPossibleMatches.clear();

                    if (Urbs.get(urbOne).getStatus() == NONE && Urbs.get(urbTwo).getStatus() == NONE) {
                        matchState = MatchState.SWAP;
                    } else {
                        one = -1;
                        urbOne = -1;
                        two = -1;
                        urbTwo = -1;
                        matchState = MatchState.READY;
                    }


                    break;
                }
            }
        }
    }

    @Override
    public void input(MotionEvent event) {

    }

    private void randomUrbsPlayingInLevel(int urbsInLevel) {
        ArrayList<Urbies.UrbieType> urbCount = new ArrayList<>();
        int rnd;

        Collections.addAll(urbCount,
                Urbies.UrbieType.BABY,
                Urbies.UrbieType.LADY,
                Urbies.UrbieType.NERD,
                Urbies.UrbieType.PAC,
                Urbies.UrbieType.GIRL_NERD,
                Urbies.UrbieType.PUNK,
                Urbies.UrbieType.ROCKER,
                Urbies.UrbieType.PIGTAILS
        );

        for (int i = 0; i < urbsInLevel; i++) {
            rnd = new Random().nextInt(urbCount.size());
            urbTypesInLevel.add(urbCount.get(rnd));
            urbCount.remove(rnd);
        }

        ArrayList<Urbies.UrbieType> neededUrbs = new ArrayList<>(levelManager.requiredUrbs());

        if (!neededUrbs.isEmpty()) {
            if (neededUrbs.size() == urbsInLevel) {
                urbTypesInLevel.clear();
                urbTypesInLevel = neededUrbs;
            } else if (neededUrbs.size() < urbsInLevel) {
                for (int i = 0; i < neededUrbs.size(); i++) {
                    if (!urbTypesInLevel.contains(neededUrbs.get(i))) {
                        urbTypesInLevel.set(i, neededUrbs.get(i));
                    }
                }
            }
        }
    }


    private List<UrbieAnimation> createUrbies(List<UrbieAnimation> objects, ArrayList<Integer> valid, int size, int fps, int frames) {

        for (int i = 0; i < size; i++) {
            int rnd = new Random().nextInt(urbTypesInLevel.size());
            int duration = new Random().nextInt(12000) + 3001;

            switch (urbTypesInLevel.get(rnd)) {
                case ROCKER:
                    objects.add(new UrbieAnimation(Assets.rocker, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.ROCKER, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case PAC:
                    objects.add(new UrbieAnimation(Assets.pac, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.PAC, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case PIGTAILS:
                    objects.add(new UrbieAnimation(Assets.pigtails, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.PIGTAILS, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case PUNK:
                    objects.add(new UrbieAnimation(Assets.punk, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.PUNK, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case NERD:
                    objects.add(new UrbieAnimation(Assets.nerd, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.NERD, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case GIRL_NERD:
                    objects.add(new UrbieAnimation(Assets.nerd_girl, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.GIRL_NERD, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case BABY:
                    objects.add(new UrbieAnimation(Assets.baby, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.BABY, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case LADY:
                    objects.add(new UrbieAnimation(Assets.lady, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.LADY, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
            }
        }
        return objects;
    }

    private void loadGameText() {
        text = new Paint();
        text.setAntiAlias(true);
        text.setColor(Color.WHITE);
        text.setStyle(Paint.Style.FILL);
        text.setTextAlign(Paint.Align.LEFT);
        text.setTextSize((float) (int) (AndroidGame.deviceW * 0.05));
        text.setTypeface(Assets.typeface1);
    }

    private void loadGui() {
        Coordinates coordinates = new Coordinates();
        pause = new Images(Assets.pause, new Point(coordinates.getX(270), coordinates.getY(30) + OFFSET_Y));
        help = new Images(Assets.help, new Point(coordinates.getX(6), coordinates.getY(30) + OFFSET_Y));
        board = new Images(Assets.board, new Point(coordinates.getX(0), coordinates.getY(458) + OFFSET_Y));
        victory = new Images(Assets.victory, new Point(coordinates.getX(10), coordinates.getY(200) + OFFSET_Y));
        failed = new Images(Assets.failed, new Point(coordinates.getX(10), coordinates.getY(200) + OFFSET_Y));

        urbGUI.add(new Images(Assets.small_pac, new Point(coordinates.getX(0), coordinates.getY(460) + OFFSET_Y)));//0, (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_baby, new Point(coordinates.getX(40), coordinates.getY(460) + OFFSET_Y)));//(int) (40 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_lady, new Point(coordinates.getX(80), coordinates.getY(460) + OFFSET_Y)));//(int) (80 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_nerd, new Point(coordinates.getX(120), coordinates.getY(460) + OFFSET_Y)));//(int) (120 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_punk, new Point(coordinates.getX(160), coordinates.getY(460) + OFFSET_Y)));//(int) (160 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_pigtails, new Point(coordinates.getX(200), coordinates.getY(460) + OFFSET_Y)));//(int) (200 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_nerdgirl, new Point(coordinates.getX(240), coordinates.getY(460) + OFFSET_Y)));//(int) (240 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_rocker, new Point(coordinates.getX(280), coordinates.getY(460) + OFFSET_Y)));//(int) (280 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
    }


    /********************************************************

     *********************************************************/
    private void matchFoundations() {

        objectsToMoveDown();

        if (!objectsToMoveDown.isEmpty()) {
            for (int i = 0; i < objectsToMoveDown.size(); i++) {
                urbsToMoveDown.add(gameMethods.findBitmapByMapLocation(Urbs, tileLocations, objectsToMoveDown.get(i)));
            }
        }

        for (int i = 0; i < userMatchOne.size(); i++) {
            urbMatchOne.add(gameMethods.findObjectByPosition(userMatchOne.get(i), Urbs));
        }

        for (int i = 0; i < urbMatchOne.size(); i++) {
            levelManager.addUrbCounter(Urbs.get(urbMatchOne.get(i)).getType(), 1);
        }

        setMatchedUrbs();

        placeObjectsOffScreen();

        initialise = 0;
        pState = Procedure.MATCH;
    }


    /********************************************************

     *********************************************************/
    private void matchRoutine() {

        if (initialise == 0) {

            if (!matchedUrbs.isEmpty()) {
                for (int i = 0; i < matchedUrbs.size(); i++) {
                    int rnd = i % 3;
                    switch (rnd) {
                        case 0:
                            matchedUrbs.get(i).setUpBounceOutLeft(matchedUrbs.get(i).getX(), matchedUrbs.get(i).getY());
                            break;
                        case 1:
                            matchedUrbs.get(i).setUpBounceOutMiddle(matchedUrbs.get(i).getX(), matchedUrbs.get(i).getY());
                            break;
                        case 2:
                            matchedUrbs.get(i).setUpBounceOutRight(matchedUrbs.get(i).getX(), matchedUrbs.get(i).getY());
                            break;
                    }
                    effects.add(new BitmapAnimation(Assets.muzzle,
                            new Point(matchedUrbs.get(i).getX(), matchedUrbs.get(i).getY()),
                            30,
                            4,
                            1200,
                            false,
                            -1, true));
                }
                canBounce = true;
                startBounceOutTime = System.currentTimeMillis();
            }

            initialise = 1;
        }

        else if (initialise == 1) {

            if (!specialUrbs.isEmpty()) {

                for (int k = 0; k < specialUrbs.size(); k++) {
                    changeToEffectAnimation(specialUrbs.get(k), Urbs, specialUrbTypes.get(k));
                }
            }

            initialise = 2;
        }

        else if (initialise == 2) {

            if (!specialUrbs.isEmpty()) {
                for (int k = 0; k < specialUrbs.size(); k++) {
                    if (Urbs.get(specialUrbs.get(k)).animFinished()) {
                        changeToSpecialUrb(specialUrbs.get(k), Urbs, specialUrbTypes.get(k));
                    }
                }
            }
        }

        else if (initialise == 3) {

            if (!urbsToMoveDown.isEmpty()) {
                for (int i = 0; i < urbsToMoveDown.size(); i++) {
                    Urbs.get(urbsToMoveDown.get(i)).clearPath();
                    Urbs.get(urbsToMoveDown.get(i)).resetCounter();
                }
            }
            initialise = 4;
        }

        else if (initialise == 4) {

            ArrayList<ObjectPathCreator> creators = gameMethods.replaceObjects(Urbs, userMatchOne, obstacleTiles, tileWidth, tileLocations, levelManager.getLevelTileMap().getMapLevel(), matchesOffScreen);

            if (userMatchOne.isEmpty()) {
                urbMatchOne.clear();

                initialise = 0;
                pState = Procedure.REPLACE_OBJECTS;
            } else {
                System.out.println("UserMatchOne = " + userMatchOne);


                for (int i = 0; i < creators.size(); i++) {
                    futurePositions.add(creators.get(i).getElement());
                    futureCoordinates.add(creators.get(i).getPosition());
                    Urbs.get(creators.get(i).getElement()).setSpritePath(creators.get(i).getPath());
                    Urbs.get(creators.get(i).getElement()).setLocation(urbMatchOne.get(i));
                    System.out.println("creators element = " + creators.get(i).getElement());
                    System.out.println("creators position = " + creators.get(i).getPosition());
                    System.out.println("path counter = " + Urbs.get(creators.get(i).getElement()).getPathCounter());

                }
                initialise = 5;
            }
        }

        else if (initialise == 6) {
            if(!urbMatchOne.isEmpty()) {
                for (int i = 0; i < urbMatchOne.size(); i++) {
                    Urbs.get(urbMatchOne.get(i)).clearPath();
                    Urbs.get(urbMatchOne.get(i)).resetCounter();
                }
            }

            initialise = 0;
            pState = Procedure.REPLACE_OBJECTS;
        }
    }


    /********************************************************

     *********************************************************/
    private void replaceEvents() {
        if (initialise == 0) {
            //drop down elements have their locations changed according to their position in the arrayList
            for (int i = 0; i < coordinatesToMoveTo.size(); i++) {
                for (int j = 0; j < tileLocations.size(); j++) {
                    if (tileLocations.get(j) == coordinatesToMoveTo.get(i)) {
                        Urbs.get(urbsToMoveDown.get(i)).setLocation(j);
                    }
                }
            }
            initialise = 1;
        }

        if (initialise == 1) {
            sortUrbs(Urbs);
            initialise = 2;
        }

        if (initialise == 2) {
            canBounce = false;
            urbMatchOne.clear();
            userMatchOne.clear();
            matchedUrbs.clear();
            futureCoordinates.clear();
            futurePositions.clear();
            urbsToMoveDown.clear();
            objectsToMoveDown.clear();
            coordinatesToMoveTo.clear();
            specialUrbs.clear();
            specialUrbTypes.clear();
            effects.clear();
            initialise = 0;
        }
    }


    /********************************************************

     *********************************************************/
    private void objectsToMoveDown() {

        ArrayList<ObjectPathCreator> creators = gameMethods.separateTheMadness(Urbs, userMatchOne, obstacleTiles, tileWidth, tileLocations, levelManager.getLevelTileMap().getMapLevel(), matchesOffScreen, entrance);

        for (int i = 0; i < creators.size(); i++) {
            objectsToMoveDown.add(creators.get(i).getElement());
            coordinatesToMoveTo.add(creators.get(i).getPath().get(creators.get(i).getPath().size() - 1));
            Urbs.get(creators.get(i).getElement()).setSpritePath(creators.get(i).getPath());
        }

        System.out.println("objectsToMoveDown = " + objectsToMoveDown);
    }

    private void changeToEffectAnimation(int num, List<UrbieAnimation> objects, Urbies.UrbieType type) {
        switch (type) {
            case MAGICIAN:
                objects.get(num).changeBitmapProperties(Assets.magician_fade_in_out, 30, 5, 500, false, false);
                objects.get(num).setType(Urbies.UrbieType.MAGICIAN);
                break;
            case WHITE_CHOCOLATE:
                objects.get(num).changeBitmapProperties(Assets.white_chocolate_fade, 30, 5, 500, false, false);
                objects.get(num).setType(Urbies.UrbieType.WHITE_CHOCOLATE);
                break;
            case STRIPE_HORIZONTAL:
                objects.get(num).changeBitmapProperties(Assets.horizontal_fade, 30, 5, 500, false, false);
                objects.get(num).setType(Urbies.UrbieType.STRIPE_HORIZONTAL);
                break;
            case STRIPE_VERTICAL:
                objects.get(num).changeBitmapProperties(Assets.vertical_fade, 30, 5, 500, false, false);
                objects.get(num).setType(Urbies.UrbieType.STRIPE_HORIZONTAL);
                break;
        }
    }


    /********************************************************

     *********************************************************/
    private void changeToSpecialUrb(int num, List<UrbieAnimation> objects, Urbies.UrbieType type) {
        int duration = new Random().nextInt(1200) + 3001;
        objects.get(num).resetAnimation();
        switch (type) {
            case MAGICIAN:
                objects.get(num).changeBitmapProperties(Assets.magician, 30, 5, new Random().nextInt(12000) + 3001, true, true);
                objects.get(num).setType(Urbies.UrbieType.MAGICIAN);
                break;
            case WHITE_CHOCOLATE:
                objects.get(num).changeBitmapProperties(Assets.chameleon, 30, 5, duration, true, true);
                objects.get(num).setType(Urbies.UrbieType.WHITE_CHOCOLATE);
                break;
            case STRIPE_HORIZONTAL:
                objects.get(num).changeBitmapProperties(Assets.stripe_h, 30, 5, duration, true, true);
                objects.get(num).setType(Urbies.UrbieType.STRIPE_HORIZONTAL);
                break;
            case STRIPE_VERTICAL:
                objects.get(num).changeBitmapProperties(Assets.stripe_v, 30, 5, duration, true, true);
                objects.get(num).setType(Urbies.UrbieType.STRIPE_VERTICAL);
                break;
        }
    }


    /********************************************************

     *********************************************************/
    private void changeToRandomBitmap(int num, List<UrbieAnimation> objects) {
        int rnd = new Random().nextInt(urbTypesInLevel.size());
        Bitmap temp = null;

        switch (urbTypesInLevel.get(rnd)) {
            case ROCKER:
                temp = Assets.rocker;
                objects.get(num).setType(Urbies.UrbieType.ROCKER);
                break;
            case BABY:
                temp = Assets.baby;
                objects.get(num).setType(Urbies.UrbieType.BABY);
                break;
            case PIGTAILS:
                temp = Assets.pigtails;
                objects.get(num).setType(Urbies.UrbieType.PIGTAILS);
                break;
            case PAC:
                temp = Assets.pac;
                objects.get(num).setType(Urbies.UrbieType.PAC);
                break;
            case PUNK:
                temp = Assets.punk;
                objects.get(num).setType(Urbies.UrbieType.PUNK);
                break;
            case NERD:
                temp = Assets.nerd;
                objects.get(num).setType(Urbies.UrbieType.NERD);
                break;
            case GIRL_NERD:
                temp = Assets.nerd_girl;
                objects.get(num).setType(Urbies.UrbieType.GIRL_NERD);
                break;
            case LADY:
                temp = Assets.lady;
                objects.get(num).setType(Urbies.UrbieType.LADY);
                break;
        }

        objects.get(num).changeBitmapProperties(temp, 10, 5, new Random().nextInt(12000) + 3001, true, true);
    }


    /*******************************************************
     sort objects based on their position in the tile map
     *******************************************************/
    private void sortUrbs(List<UrbieAnimation> urbieAnimations) {

        int temp = -1;
        for (int i = 0; i < urbieAnimations.size(); i++) {
            int realLoc = gameMethods.findMapLocationOfBitmap(tileLocations, urbieAnimations, i);
            if (urbieAnimations.get(i).getLocation() != realLoc) {
                if (realLoc > -1) {
                    urbieAnimations.get(i).setLocation(realLoc);
                } else {
                    //urb is not on the tilemap
                    urbieAnimations.get(i).setLocation(temp);
                    temp--;
                }
            }
        }

        int j;
        boolean flag = true;   // set flag to true to begin first pass

        while (flag) {
            flag = false;    //set flag to false awaiting a possible swap
            for (j = 0; j < urbieAnimations.size() - 1; j++) {
                if (urbieAnimations.get(j).getLocation() > urbieAnimations.get(j + 1).getLocation())   // change to > for ascending sort
                {

                    Collections.swap(urbieAnimations, j, j + 1);

                    flag = true;              //shows a swap occurred
                }
            }
        }

        /*System.out.println("Sort Urbs Method");
        for (int i = 0; i < urbieAnimations.size(); i++) {
            System.out.print(i + " = " + urbieAnimations.get(i).getLocation());
            System.out.println(" ");
        }*/
    }


    /********************************************************
     Copies the matched urbs into another Arraylist, that will
     be used to show the urbs bouncing out of the tile map
     *********************************************************/
    private void setMatchedUrbs() {
        for (int i = 0; i < urbMatchOne.size(); i++) {
            matchedUrbs.add(new UrbieAnimation(Urbs.get(urbMatchOne.get(i)).getBitmap(),
                    new Point(Urbs.get(urbMatchOne.get(i)).getX(),
                            Urbs.get(urbMatchOne.get(i)).getY()),
                    Urbs.get(urbMatchOne.get(i)).getFps(),
                    Urbs.get(urbMatchOne.get(i)).getFrameCount(),
                    Urbs.get(urbMatchOne.get(i)).getDuration(),
                    Urbs.get(urbMatchOne.get(i)).getLooped(),
                    Urbs.get(urbMatchOne.get(i)).getType(),
                    Urbs.get(urbMatchOne.get(i)).getLocation(), true,
                    Urbs.get(urbMatchOne.get(i)).getStatus(),
                    Urbs.get(urbMatchOne.get(i)).getVisible(),
                    Urbs.get(urbMatchOne.get(i)).getActive()));
        }
    }


    /********************************************************

     *********************************************************/
    private void placeObjectsOffScreen() {

        System.out.println("matchesOffScreen = " + matchesOffScreen);
        System.out.println("urbMatchOne = " + urbMatchOne);

        if (!matchesOffScreen.isEmpty()) {
            for (int a = urbMatchOne.size() - 1; a >= 0; a--) {
                if (matchesOffScreen.contains(Urbs.get(urbMatchOne.get(a)).getLocation())) {
                    Urbs.get(urbMatchOne.get(a)).setY(-300);
                    Urbs.get(urbMatchOne.get(a)).setActive(false);
                    urbMatchOne.remove(a);
                }
            }

            for (int i = userMatchOne.size() - 1; i >= 0; i--) {
                if (matchesOffScreen.contains(userMatchOne.get(i))) {
                    userMatchOne.remove(i);
                }
            }
        }

        int yLoc = 0;
        for (int i = 0; i < urbMatchOne.size(); i++) {

            changeToRandomBitmap(urbMatchOne.get(i), Urbs);
            Urbs.get(urbMatchOne.get(i)).setY((int) ((0 - Urbs.get(urbMatchOne.get(i)).getHeight() + yLoc) * AndroidGame.GAME_SCALE_X));
            Urbs.get(urbMatchOne.get(i)).setX(-200);
            Urbs.get(urbMatchOne.get(i)).setLocation(-200);
        }
    }


    /*******************************************************************
     Identify valid shuffle locations, under the following conditions:
     Tile Location is valid
     Urb at position is at status NONE
     Urb location is actually within the tile map
     *******************************************************************/
    private ArrayList<Integer> getArrayLocations() {
        ArrayList<Integer> mapLevel = levelManager.getLevelTileMap().getMapLevel();
        ArrayList<Integer> shuffledLocations = new ArrayList<>();
        ArrayList<ArrayList<Integer>> blockedRows;
        //ArrayList<Integer>separateShuffleList = new ArrayList<>();

        //add valid locations to ArrayList
        for (int i = 0; i < mapLevel.size(); i++) {
            if (mapLevel.get(i) == 1) {
                shuffledLocations.add(i);
            }
        }

        //remove locations where urb is held by an obstacle
        for (int i = shuffledLocations.size() - 1; i >= 0; i--) {
            int urbLoc = gameMethods.findObjectByPosition(shuffledLocations.get(i), Urbs);
            if (urbLoc > -1) {
                if (Urbs.get(urbLoc).getStatus() != NONE || Urbs.get(urbLoc).getY() < 0) {
                    shuffledLocations.remove(i);
                }
            }
        }

        //if location is completely blocked by a location e.g. whole row or whole column
        //find and remove from shuffle array and put into another array
        blockedRows = gameMethods.getListOfBlockedRows(tileHeight, tileWidth, obstacleTiles, mapLevel);
        //so far this just removes the urbs that are underneath blocked row from the array list
        if (!blockedRows.isEmpty()) {
            for (int a = 0; a < blockedRows.size(); a++) {
                for (int i = shuffledLocations.size() - 1; i >= 0; i--) {
                    if (shuffledLocations.get(i) > blockedRows.get(a).get(0)) {
                        shuffledLocations.remove(i);
                    }
                }
            }
        }

        if (!shuffledLocations.isEmpty()) Collections.shuffle(shuffledLocations);
        return shuffledLocations;
    }


    private void moveUrbsToShuffledLocations(ArrayList<Integer> newLocations, List<UrbieAnimation> objects, ArrayList<Point> positions) {
        ArrayList<ArrayList<Integer>> blockedRows;
        ArrayList<Integer> mapLevel = levelManager.getLevelTileMap().getMapLevel();
        int counter = 0;

        blockedRows = gameMethods.getListOfBlockedRows(tileHeight, tileWidth, obstacleTiles, mapLevel);
        //so far this just removes the urbs that are underneath blocked row from the array list


        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getStatus() == NONE && objects.get(i).getActive()) {
                if (!blockedRows.isEmpty()) {
                    for (int a = 0; a < blockedRows.size(); a++) {
                        if (objects.get(i).getLocation() < blockedRows.get(a).get(0)) {
                            objects.get(i).findLine(
                                    objects.get(i).getX(),
                                    objects.get(i).getY(),
                                    positions.get(newLocations.get(counter)).x,
                                    positions.get(newLocations.get(counter)).y
                            );
                            objects.get(i).setLocation(newLocations.get(counter));
                            counter++;
                        }
                    }
                } else {
                    objects.get(i).findLine(
                            objects.get(i).getX(),
                            objects.get(i).getY(),
                            positions.get(newLocations.get(counter)).x,
                            positions.get(newLocations.get(counter)).y
                    );
                    objects.get(i).setLocation(newLocations.get(counter));
                    counter++;
                }
            }
        }
        sortUrbs(objects);
    }


    private void visualisePotentialMatch(ArrayList<Integer> possibleMatches) {
        if (!possibleMatches.isEmpty()) {
            for (int loop = 0; loop < possibleMatches.size(); loop++) {
                switch (Urbs.get(possibleMatches.get(loop)).getType()) {
                    case BABY:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.babyBounce);
                        break;
                    case NERD:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.nerdBounce);
                        break;
                    case PIGTAILS:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.pigtailsBounce);
                        break;
                    case PAC:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.pacBounce);
                        break;
                    case LADY:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.ladyBounce);
                        break;
                    case PUNK:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.punkBounce);
                        break;
                    case ROCKER:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.rockerBounce);
                        break;
                    case GIRL_NERD:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.nerdGirlBounce);
                        break;
                    case WHITE_CHOCOLATE:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.chameleonBounce);
                        break;
                    case MAGICIAN:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.magicianBounce);
                        break;
                    case STRIPE_HORIZONTAL:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.stripeBounce_h);
                        break;
                    case STRIPE_VERTICAL:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.stripeBounce_v);
                        break;
                }
                Urbs.get(possibleMatches.get(loop)).setFrameDuration(0);
            }

            for (int loop = 0; loop < possibleMatches.size(); loop++) {
                Urbs.get(possibleMatches.get(loop)).setFrameDuration(1000);
                Urbs.get(possibleMatches.get(loop)).setFPS(30);
            }
        }
    }


    private void visualiseSpecialUrb(int position) {
        switch (Urbs.get(position).getType()) {
            case MAGICIAN:
                Urbs.get(position).setBitmap(Assets.magicianBounce);
                break;
            case WHITE_CHOCOLATE:
                Urbs.get(position).setBitmap(Assets.chameleonBounce);
                break;
            case STRIPE_HORIZONTAL:
                Urbs.get(position).setBitmap(Assets.stripeBounce_h);
                break;
            case STRIPE_VERTICAL:
                Urbs.get(position).setBitmap(Assets.stripeBounce_v);
                break;
        }
        Urbs.get(position).setFrameCount(5);
        Urbs.get(position).setFPS(30);
    }


    private void resetSpecialUrb(int position) {
        switch (Urbs.get(position).getType()) {
            case MAGICIAN:
                Urbs.get(position).setBitmap(Assets.magician);
                break;
            case WHITE_CHOCOLATE:
                Urbs.get(position).setBitmap(Assets.chameleon);
                break;
            case STRIPE_HORIZONTAL:
                Urbs.get(position).setBitmap(Assets.stripe_h);
                break;
            case STRIPE_VERTICAL:
                Urbs.get(position).setBitmap(Assets.stripe_v);
                break;
        }
        Urbs.get(position).setFrameCount(5);
        Urbs.get(position).setFPS(30);
    }


    private boolean hasSpecialObjectBeenSwapped(List<UrbieAnimation> objects, int element) {
        boolean result = false;

        switch (objects.get(element).getType()) {
            case MAGICIAN:
            case STRIPE_HORIZONTAL:
            case STRIPE_VERTICAL:
            case WHITE_CHOCOLATE:
                result = true;
                break;
        }
        return result;
    }


    private void clearUserSelection() {
        urbOne = -1;
        one = -1;
        urbTwo = -1;
        two = -1;
        noValidSwap = false;
    }


    private void storeMatchList(ArrayList<Integer> matchOne, ArrayList<Integer> matchTwo) {
        userMatchOne.addAll(matchOne);
        userMatchTwo.addAll(matchTwo);
    }
}

