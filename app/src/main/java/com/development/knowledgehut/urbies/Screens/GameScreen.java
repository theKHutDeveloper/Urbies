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
import java.util.Arrays;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.development.knowledgehut.urbies.Implementations.AndroidGame.OFFSET_Y;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.GLASS;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.NONE;
import static java.util.Collections.reverseOrder;

class GameScreen extends Screen {

    private enum MatchState {
        AUTO, READY, SWAP, RESET_SWAP, USER_MATCH,
        SHUFFLE, SPECIAL_URBS
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
    private ArrayList<Point> futureCoordinates = new ArrayList<>();
    private ArrayList<Integer> futurePositions = new ArrayList<>();
    private ArrayList<BitmapAnimation> effects = new ArrayList<>();
    private ArrayList<Integer> possibleMatches = new ArrayList<>();
    private ArrayList<Urbies.UrbieType> urbTypesInLevel = new ArrayList<>();
    private ArrayList<Integer> specialUrbs = new ArrayList<>();
    private ArrayList<Urbies.UrbieType> specialUrbTypes = new ArrayList<>();
    private ArrayList<Integer> urbPossibleMatches = new ArrayList<>();
    private ArrayList<Integer> specials = new ArrayList<>();
    private ArrayList<Images> magicianSelects = new ArrayList<>();
    private ArrayList<ArrayList<Integer>> specSpecials = new ArrayList<>(2);
    private ArrayList<Integer> colourBombUrbs = new ArrayList<>();
    private ArrayList<Integer> matchesOffScreen = new ArrayList<>();
    private ArrayList<Integer> notInPlay = new ArrayList<>();
    private ArrayList<Integer> updateMoveDownElements = new ArrayList<>();
    private ArrayList<Images> urbGUI = new ArrayList<>();
    private ArrayList<Integer> entrance = new ArrayList<>();
    private Images pause, help, board;
    private BitmapAnimation colourBombExplosion;
    private MatchState matchState;
    private Procedure pState;

    private Paint text;
    private Images victory, failed;
    private int magicBomb = -1;
    private int one = -1, two = -1;
    private int urbOne, urbTwo;
    private Urbies.UrbieType urbOneType, urbTwoType;
    private int initialise = 0;
    private boolean noValidSwap = false;
    private boolean showPossibleMove = false;
    private long startBounceOutTime, readyToMoveTimer, moveToNextScreenTimer;
    private boolean canBounce = false;
    private ArrayList<SpecialFX> specialFX = new ArrayList<>();
    private boolean allowSpecialUserInput = false;
    private int userClicksUrb, specialUrbUserObject;
    private Urbies.UrbieType userSwapType;
    private int tileH;
    private int setSpecialPhase = 0;
    private long startRotateTimer;
    private int urbCounter = 0;
    private boolean startRotateAvailableUrbs = false;
    private Bitmap bmpSelected = null;

    private int tileWidth, tileHeight;

    private ArrayList<Obstacles> obstacleTiles = new ArrayList<>();
    private SharedPreferences preferences;

    GameScreen(Game game) {
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

        if (Urbies.level == 11) {
            Urbs = fakeUrbies(Urbs, validTiles, validTiles.size(), 10, 5);
            ArrayList<Integer> locations = new ArrayList<>(levelManager.obstacleTileLocation());
            for (int i = 0; i < locations.size(); i++) {
                int pos = gameMethods.findBitmapByMapLocation(Urbs, tileLocations, locations.get(i));
                obstacleTiles.add(new Obstacles(
                        new BitmapAnimation(Assets.cement_100, new Point(Urbs.get(pos).getX(), Urbs.get(pos).getY()), 20, 1, 2000, true, locations.get(i), true),
                        Urbies.UrbieStatus.CEMENT, Urbies.VisibilityStatus.INVISIBLE)
                );
                Urbs.get(pos).setStatus(Urbies.UrbieStatus.CEMENT);
                Urbs.get(pos).setVisible(Urbies.VisibilityStatus.INVISIBLE);
                matchState = MatchState.AUTO;
                pState = Procedure.CHECK;
                levelManager.startTimer();
            }
        } else {
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
        }

        tileH = baseTiles.getIndividualTileWidth();
        loadGameText();
        loadGui();
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


    private List<UrbieAnimation> fakeUrbies(List<UrbieAnimation> objects, ArrayList<Integer> valid, int size, int fps, int frames) {
        ArrayList<Integer> values = new ArrayList<>();

        if (Urbies.level == 11) {
            Collections.addAll(values,
                    1, 2, 5, 4, 1,
                    1, 5, 1, 2, 6,
                    5, 1, 5, 1, 2,
                    2, 5, 3, 4, 1,
                    1, 4, 2, 6, 1,
                    2, 3, 6, 5, 6);
            urbTypesInLevel.add(Urbies.UrbieType.BABY);
            urbTypesInLevel.add(Urbies.UrbieType.PAC);
            urbTypesInLevel.add(Urbies.UrbieType.NERD);
            urbTypesInLevel.add(Urbies.UrbieType.PIGTAILS);
            urbTypesInLevel.add(Urbies.UrbieType.ROCKER);
        }

        for (int i = 0; i < size; i++) {

            int duration = new Random().nextInt(12000) + 3001;

            switch (values.get(i)) {
                case 1:
                    objects.add(new UrbieAnimation(Assets.baby, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.BABY, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case 2:
                    objects.add(new UrbieAnimation(Assets.pac, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.PAC, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case 3:
                    objects.add(new UrbieAnimation(Assets.nerd, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.NERD, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case 4:
                    objects.add(new UrbieAnimation(Assets.pigtails, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.PIGTAILS, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case 5:
                    objects.add(new UrbieAnimation(Assets.rocker, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.ROCKER, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case 6:
                    objects.add(new UrbieAnimation(Assets.lady, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.LADY, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case 7:
                    objects.add(new UrbieAnimation(Assets.punk, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.PUNK, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case 8:
                    objects.add(new UrbieAnimation(Assets.nerd_girl, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.GIRL_NERD, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
                case 80:
                    objects.add(new UrbieAnimation(Assets.whiteChocolate, new Point(tileLocations.get(valid.get(i)).x, tileLocations.get(valid.get(i)).y),
                            fps, frames, duration, true, Urbies.UrbieType.WHITE_CHOCOLATE, valid.get(i), true, NONE, Urbies.VisibilityStatus.VISIBLE, true));
                    break;
            }
        }

        return objects;
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
                                    int pos = findObjectByPosition(details.get(i).getReturnedMatches().get(0), Urbs);
                                    if (Urbs.get(pos).getType() == Urbies.UrbieType.WHITE_CHOCOLATE ||
                                            Urbs.get(pos).getType() == Urbies.UrbieType.STRIPE_HORIZONTAL ||
                                            Urbs.get(pos).getType() == Urbies.UrbieType.STRIPE_VERTICAL ||
                                            Urbs.get(pos).getType() == Urbies.UrbieType.GOBSTOPPER) {
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

                            sameAsOne();

                        } else {
                            initialise = 0;
                            matchState = MatchState.READY;
                        }

                        break;

                    case MATCH:
                        sameMatch();
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
                            if (Urbs.get(i).getType() == Urbies.UrbieType.GOBSTOPPER ||
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

                        sameAsOne();

                        break;

                    case MATCH:

                        sameMatch();

                        break;


                    case REPLACE_OBJECTS:

                        replaceEvents();
                        matchState = MatchState.AUTO;
                        pState = Procedure.CHECK;
                        break;
                }
                break;

            case SPECIAL_URBS:
                switch (pState) {
                    case CHECK:

                        userMatchOne.clear();

                        if (urbOneType != null && urbTwoType != null) { //both urbs are special objects
                            setSpecialUrbPattern(urbOneType, urbOne, one, urbTwoType, urbTwo, two);
                        } else {
                            int urbTemp = -1, temp = -1;
                            Urbies.UrbieType type = Urbies.UrbieType.NONE;
                            int urbSp = -1;

                            if (urbOneType != null) {
                                urbTemp = urbTwo;
                                type = urbOneType;
                                temp = one;
                                urbSp = urbOne;
                            } else if (urbTwoType != null) {
                                urbTemp = urbOne;
                                type = urbTwoType;
                                temp = two;
                                urbSp = urbTwo;
                            }

                            switch (type) {

                                case GOBSTOPPER:
                                    if (setSpecialPhase == 0) {
                                        specialUrbUserObject = urbSp;
                                        userClicksUrb = urbTemp;

                                        if (Urbs.get(userClicksUrb).getType() != Urbies.UrbieType.STRIPE_HORIZONTAL ||
                                                Urbs.get(userClicksUrb).getType() != Urbies.UrbieType.STRIPE_VERTICAL ||
                                                Urbs.get(userClicksUrb).getType() != Urbies.UrbieType.WHITE_CHOCOLATE ||
                                                Urbs.get(userClicksUrb).getType() != Urbies.UrbieType.GOBSTOPPER) {

                                            specials = gameMethods.findMatchingObjectTypes(Urbs, userClicksUrb);
                                            for (int i = 0; i < specials.size(); i++) {
                                                magicianSelects.add(
                                                        new Images(Assets.bright_selector, new Point(Urbs.get(specials.get(i)).getX(), Urbs.get(specials.get(i)).getY()))
                                                );
                                            }
                                            startRotateAvailableUrbs = true;
                                            startRotateTimer = System.currentTimeMillis();
                                            urbCounter = 0;
                                        }
                                        setSpecialPhase = 1;
                                    }

                                    allowSpecialUserInput = true;
                                    break;
                                case WHITE_CHOCOLATE:
                                    if (Urbs.get(urbTemp).getType() != Urbies.UrbieType.STRIPE_HORIZONTAL ||
                                            Urbs.get(urbTemp).getType() != Urbies.UrbieType.STRIPE_VERTICAL ||
                                            Urbs.get(urbTemp).getType() != Urbies.UrbieType.WHITE_CHOCOLATE ||
                                            Urbs.get(urbTemp).getType() != Urbies.UrbieType.GOBSTOPPER) {

                                        specials = gameMethods.findMatchingObjectTypes(Urbs, urbTemp);
                                        for (int i = 0; i < specials.size(); i++) {
                                            specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                                                    Assets.lightning0, Assets.lightning1)),
                                                    Urbs.get(urbSp).getX(),
                                                    Urbs.get(urbSp).getY(),
                                                    Urbs.get(specials.get(i)).getX(),
                                                    Urbs.get(specials.get(i)).getY(),
                                                    80, tileH));
                                        }
                                        Urbs.get(urbSp).changeBitmapProperties(Assets.white_chocolate_fade, 30, 5, 800, false, true);
                                        levelManager.addSpecialUrbBonusToScore(Urbs.get(urbSp).getType(), specials.size() - 1);
                                        levelManager.addUrbCounter(Urbs.get(specials.get(0)).getType(), specials.size());
                                    }
                                    break;

                                case STRIPE_HORIZONTAL:
                                    specials = gameMethods.findObjectsInRow(Urbs, temp, levelManager.getLevelTileMap().getMapLevel(),
                                            tileLocations, tileWidth);

                                    levelManager.addSpecialUrbBonusToScore(Urbs.get(urbSp).getType(), specials.size() - 1);
                                    for (int i = 0; i < specials.size(); i++) {
                                        levelManager.addUrbCounter(Urbs.get(specials.get(i)).getType(), 1);
                                    }

                                    specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                                            Assets.lightning0, Assets.lightning1)),
                                            Urbs.get(specials.get(0)).getX(),
                                            Urbs.get(specials.get(0)).getY(),
                                            Urbs.get(specials.get(specials.size() - 1)).getX(),
                                            Urbs.get(specials.get(specials.size() - 1)).getY(),
                                            80, tileH)
                                    );
                                    Urbs.get(urbSp).changeBitmapProperties(Assets.horizontal_fade, 30, 5, 800, false, true);
                                    break;

                                case STRIPE_VERTICAL:
                                    specials = gameMethods.findObjectsInColumn(Urbs, temp, levelManager.getLevelTileMap().getMapLevel(),
                                            tileLocations, tileWidth);

                                    levelManager.addSpecialUrbBonusToScore(Urbs.get(urbSp).getType(), specials.size() - 1);
                                    for (int i = 0; i < specials.size(); i++) {
                                        levelManager.addUrbCounter(Urbs.get(specials.get(i)).getType(), 1);
                                    }
                                    specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                                            Assets.lightning0, Assets.lightning1)),
                                            Urbs.get(specials.get(0)).getX(),
                                            Urbs.get(specials.get(0)).getY(),
                                            Urbs.get(specials.get(specials.size() - 1)).getX(),
                                            Urbs.get(specials.get(specials.size() - 1)).getY(),
                                            80, tileH)
                                    );
                                    Urbs.get(urbSp).changeBitmapProperties(Assets.vertical_fade, 30, 5, 800, false, true);
                                    break;
                            }

                            if (type == Urbies.UrbieType.GOBSTOPPER) {
                                if (startRotateAvailableUrbs) {
                                    if (System.currentTimeMillis() > startRotateTimer + 300) {

                                        if (urbCounter < urbTypesInLevel.size()) {
                                            urbCounter++;
                                            if (urbCounter < urbTypesInLevel.size())
                                                if (Urbs.get(userClicksUrb).getType() == urbTypesInLevel.get(urbCounter)) {
                                                    urbCounter++;
                                                }

                                        }
                                        if (urbCounter >= urbTypesInLevel.size()) {
                                            urbCounter = 0;
                                            if (Urbs.get(userClicksUrb).getType() == urbTypesInLevel.get(urbCounter)) {
                                                urbCounter++;
                                            }
                                        }

                                        switch (urbTypesInLevel.get(urbCounter)) {
                                            case PAC:
                                                Urbs.get(userClicksUrb).setBitmap(Assets.pac);
                                                bmpSelected = Assets.pac;
                                                break;
                                            case PIGTAILS:
                                                Urbs.get(userClicksUrb).setBitmap(Assets.pigtails);
                                                bmpSelected = Assets.pigtails;
                                                break;
                                            case PUNK:
                                                Urbs.get(userClicksUrb).setBitmap(Assets.punk);
                                                bmpSelected = Assets.punk;
                                                break;
                                            case ROCKER:
                                                Urbs.get(userClicksUrb).setBitmap(Assets.rocker);
                                                bmpSelected = Assets.rocker;
                                                break;
                                            case NERD:
                                                Urbs.get(userClicksUrb).setBitmap(Assets.nerd);
                                                bmpSelected = Assets.nerd;
                                                break;
                                            case GIRL_NERD:
                                                Urbs.get(userClicksUrb).setBitmap(Assets.nerd_girl);
                                                bmpSelected = Assets.nerd_girl;
                                                break;
                                            case BABY:
                                                Urbs.get(userClicksUrb).setBitmap(Assets.baby);
                                                bmpSelected = Assets.baby;
                                                break;
                                            case LADY:
                                                Urbs.get(userClicksUrb).setBitmap(Assets.lady);
                                                bmpSelected = Assets.lady;
                                                break;
                                        }
                                        startRotateTimer = System.currentTimeMillis();
                                    }
                                }

                            } else if (type == Urbies.UrbieType.WHITE_CHOCOLATE || type == Urbies.UrbieType.STRIPE_HORIZONTAL ||
                                    type == Urbies.UrbieType.STRIPE_VERTICAL) {
                                if (!specials.isEmpty()) {
                                    //convert specials to map positions
                                    for (int i = 0; i < specials.size(); i++) {
                                        userMatchOne.add(gameMethods.findMapLocationOfBitmap(tileLocations, Urbs, specials.get(i)));
                                    }

                                    if (type == Urbies.UrbieType.WHITE_CHOCOLATE) {
                                        userMatchOne.add(temp);
                                    }

                                    Collections.sort(userMatchOne, reverseOrder());


                                    objectsToMoveDown();
                                    if (levelManager.isWood() || levelManager.isCement()) {
                                        findDamagedObstacles(obstacleTiles);
                                    }
                                    clearDamagedObstacle(obstacleTiles);

                                    if (!updateMoveDownElements.isEmpty()) {
                                        addEmptyTilesAfterBrokenObstacleRemoved(updateMoveDownElements);
                                    }

                                    if (!objectsToMoveDown.isEmpty()) {
                                        for (int i = 0; i < objectsToMoveDown.size(); i++) {
                                            urbsToMoveDown.add(gameMethods.findBitmapByMapLocation(Urbs, tileLocations, objectsToMoveDown.get(i)));
                                        }
                                    }

                                    for (int i = 0; i < userMatchOne.size(); i++) {
                                        urbMatchOne.add(findObjectByPosition(userMatchOne.get(i), Urbs));
                                    }

                                    Urbs.get(urbTemp).clearPath();
                                    Urbs.get(urbSp).clearPath();

                                    setMatchedUrbs();

                                    sortFuturePositionsInDescendingOrderOfY();

                                    initialise = 0;
                                    pState = Procedure.MATCH;
                                }
                            }
                        }
                        break;

                    case MATCH:

                        if (initialise == 0) {
                            clearUserSelection();
                            urbOneType = null;
                            urbTwoType = null;

                            //define the move down path for remaining Urbs
                            if (!urbsToMoveDown.isEmpty()) {
                                for (int i = 0; i < urbsToMoveDown.size(); i++) {
                                    Urbs.get(urbsToMoveDown.get(i)).findLine(Urbs.get(urbsToMoveDown.get(i)).getX(),
                                            Urbs.get(urbsToMoveDown.get(i)).getY(), coordinatesToMoveTo.get(i).x, coordinatesToMoveTo.get(i).y);
                                }
                            }

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
                            freedomSounds();
                            startBounceOutTime = System.currentTimeMillis();

                            if (magicBomb > -1) {
                                Urbs.get(magicBomb).changeBitmapProperties(Assets.gobstopperBomb, 10, 2, 2000, true, true);
                            }

                            initialise = 1;

                        } else if (initialise == 2) {

                            for (int i = 0; i < urbsToMoveDown.size(); i++) {
                                Urbs.get(urbsToMoveDown.get(i)).clearPath();
                            }

                            initialise = 3;


                        } else if (initialise == 3) {

                            ArrayList<ObjectPathCreator> creators = gameMethods.replaceObjects(Urbs, userMatchOne, obstacleTiles, tileWidth, tileLocations, levelManager.getLevelTileMap().getMapLevel(), matchesOffScreen);

                            if (userMatchOne.isEmpty()) {
                                urbMatchOne.clear();
                                initialise = 0;
                                pState = Procedure.REPLACE_OBJECTS;
                            } else {
                                for (int i = 0; i < creators.size(); i++) {
                                    futurePositions.add(creators.get(i).getElement());
                                    futureCoordinates.add(creators.get(i).getPosition());
                                    Urbs.get(creators.get(i).getElement()).setSpritePath(creators.get(i).getPath());
                                    Urbs.get(creators.get(i).getElement()).setLocation(userMatchOne.get(i));
                                }
                                initialise = 4;
                            }
                        } else if (initialise == 5) {
                            for (int i = 0; i < urbMatchOne.size(); i++) {
                                Urbs.get(urbMatchOne.get(i)).clearPath();
                            }
                            clearDamagedObstacle(obstacleTiles);

                            initialise = 0;
                            pState = Procedure.REPLACE_OBJECTS;
                        }

                        break;


                    case REPLACE_OBJECTS:

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
                            if (magicBomb > -1) {
                                ArrayList<Integer> colourBombList = gameMethods.collectElementsForColourBomb(
                                        levelManager.getLevelTileMap().getMapLevel(),
                                        gameMethods.findMapLocationOfBitmap(tileLocations, Urbs, magicBomb),
                                        tileWidth
                                );

                                for (int i = 0; i < colourBombList.size(); i++) {
                                    colourBombUrbs.add(findObjectByPosition(colourBombList.get(i), Urbs));
                                }

                                colourBombExplosion = new BitmapAnimation(
                                        Assets.colourBombExplosion,
                                        new Point(Urbs.get(magicBomb).getX() + Urbs.get(magicBomb).getWidth() / 2,
                                                Urbs.get(magicBomb).getY() + Urbs.get(magicBomb).getHeight() / 2),
                                        30,
                                        5,
                                        2000,
                                        false,
                                        -1, true
                                );
                                colourBombExplosion.setX(colourBombExplosion.getX() - colourBombExplosion.getWidth() / 2);
                                colourBombExplosion.setY(colourBombExplosion.getY() - colourBombExplosion.getHeight() / 2);
                                initialise = 3;
                            } else {
                                initialise = 4;
                            }
                        }

                        if (initialise == 3) {
                            int rndType = new Random().nextInt(urbTypesInLevel.size());

                            for (int i = 0; i < colourBombUrbs.size(); i++) {
                                if (Urbs.get(colourBombUrbs.get(i)).getFrameCount() != 5) {
                                    Urbs.get(colourBombUrbs.get(i)).setFrameCount(5);
                                }
                                Urbs.get(colourBombUrbs.get(i)).setType(urbTypesInLevel.get(rndType));
                                Urbs.get(colourBombUrbs.get(i)).setBitmap(gameMethods.findBitmapFromType(urbTypesInLevel.get(rndType)));
                            }
                        }

                        if (initialise == 4) {
                            canBounce = false;
                            urbMatchOne.clear();
                            userMatchOne.clear();
                            matchedUrbs.clear();
                            futureCoordinates.clear();
                            futurePositions.clear();
                            urbsToMoveDown.clear();
                            objectsToMoveDown.clear();
                            coordinatesToMoveTo.clear();
                            specials.clear();
                            specialUrbs.clear();
                            specialFX.clear();
                            specialUrbTypes.clear();
                            specSpecials.clear();
                            initialise = 0;
                            effects.clear();
                            if (magicBomb > -1) {
                                Urbs.get(magicBomb).setFrameCount(5);
                            }
                            magicBomb = -1;
                            colourBombUrbs.clear();
                            if (!matchesOffScreen.isEmpty()) {
                                //Collections.addAll(matchesOffScreen);
                                notInPlay.addAll(matchesOffScreen);
                                matchesOffScreen.clear();
                            }
                            matchState = MatchState.AUTO;
                            pState = Procedure.CHECK;
                        }
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

        if (levelManager.isGlass() || levelManager.isWood() || levelManager.isCement()) {
            if (!obstacleTiles.isEmpty()) {
                for (int i = 0; i < obstacleTiles.size(); i++) {
                    obstacleTiles.get(i).getObstacle().draw(graphics);
                }
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


        /*if(matchState == MatchState.READY && initialise == 0){
            System.out.println("Sprite path values");
            for(int h = 0; h < Urbs.size(); h++) {
                System.out.println(Urbs.get(h).getSpritePath().size());
            }
        }*/
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
                if (urbsToMoveDown.isEmpty()) {// && replacements == urbMatchOne.size()) {
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

        if (matchState == MatchState.SPECIAL_URBS) {

            if (!magicianSelects.isEmpty()) {
                for (int i = 0; i < magicianSelects.size(); i++) {
                    magicianSelects.get(i).draw(graphics);
                }
            }

            int checker = 0;

            for (int i = 0; i < specialFX.size(); i++) {
                if (specialFX.get(i).isAnimationFinished()) {
                    checker++;
                }
            }

            if (checker == specialFX.size()) {
                if (pState == Procedure.MATCH && initialise == 1) {
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
                    if (urbsToMoveDown.isEmpty()) {// && replacements == urbMatchOne.size()) {
                        if (!matchesOffScreen.isEmpty()) {
                            if (System.currentTimeMillis() > startBounceOutTime + 2000) {
                                initialise = 2;
                            }
                        } else {
                            initialise = 2;
                        }
                    }
                    //There are urbs to move down and replacements are equal to the size of the urb matches
                    else if (counter == urbsToMoveDown.size()) {// && replacements == urbMatchOne.size()) {
                        initialise = 2;
                    }
                }

            }
            if (pState == Procedure.MATCH && initialise == 4) {
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
                    initialise = 5;
                }
            }

            if (pState == Procedure.REPLACE_OBJECTS && initialise == 3) {
                colourBombExplosion.draw(graphics);
                if (colourBombExplosion.animFinished()) {
                    initialise = 4;
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
            if (Urbs.get(k).isSelected(x1, y1 - OFFSET_Y)) {
                one = Urbs.get(k).getLocation();
                urbOne = k;
                if (one + moveValue > 0 && one + moveValue < levelManager.getLevelTileMap().getMapLevel().size()
                        && levelManager.getLevelTileMap().getMapLevel().get(one + moveValue) == 1
                        && ((((one + moveValue) / tileWidth == one / tileWidth) || ((one + moveValue) % tileWidth == one % tileWidth)))) {
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
        if (matchState == MatchState.SPECIAL_URBS && allowSpecialUserInput) {

            int event_action = event.getAction();

            switch (event_action) {
                case MotionEvent.ACTION_DOWN:
                    startRotateAvailableUrbs = false;
                    specials = gameMethods.getMatchingObjectsByType(Urbs, gameMethods.findTypeBasedOnBitmap(bmpSelected));
                    specials.add(specialUrbUserObject);
                    specials.add(userClicksUrb);

                    levelManager.addSpecialUrbBonusToScore(Urbs.get(specialUrbUserObject).getType(), specials.size());

                    //take a course of action depending on whether the magic happens with a basic urb
                    //or with a special object
                    if (urbOneType != null && urbTwoType != null) {
                        for (int j = 0; j < specials.size(); j++) {
                            changeToSpecialUrb(specials.get(j), Urbs, userSwapType);
                        }
                    } else {

                        for (int i = 0; i < specials.size(); i++) {
                            Urbs.get(specials.get(i)).changeBitmapProperties(
                                    bmpSelected,
                                    10, 5, new Random().nextInt(12000) + 3001, true, true
                            );
                            Urbs.get(specials.get(i)).setType(gameMethods.findTypeBasedOnBitmap(bmpSelected));
                        }
                    }

                    urbOneType = null;
                    urbTwoType = null;
                    initialise = 0;
                    urbMatchOne.clear();
                    userMatchOne.clear();
                    matchedUrbs.clear();
                    futureCoordinates.clear();
                    futurePositions.clear();
                    urbsToMoveDown.clear();
                    objectsToMoveDown.clear();
                    coordinatesToMoveTo.clear();
                    Urbs.get(specialUrbUserObject).clearPath();
                    Urbs.get(userClicksUrb).clearPath();
                    specialUrbs.clear();
                    specialUrbTypes.clear();
                    setSpecialPhase = 0;
                    startRotateAvailableUrbs = false;
                    startRotateTimer = 0;
                    urbCounter = 0;
                    bmpSelected = null;
                    specials.clear();
                    specialUrbUserObject = -1;
                    userClicksUrb = -1;
                    magicianSelects.clear();
                    userSwapType = Urbies.UrbieType.NONE;
                    clearUserSelection();
                    matchState = MatchState.AUTO;
                    pState = Procedure.CHECK;

                    if (!matchesOffScreen.isEmpty()) {
                        notInPlay.addAll(matchesOffScreen);
                        matchesOffScreen.clear();
                    }

                    allowSpecialUserInput = false;
                    break;
            }
        }
    }


    private void findDamagedObstacles(ArrayList<Obstacles> obstacles) {

        if (!obstacles.isEmpty()) {
            int damage;

            for (int i = 0; i < obstacles.size(); i++) {
                if (obstacles.get(i).getOldStatus() != GLASS) {
                    damage = gameMethods.isMatchNextToObstacle(obstacles.get(i).getObstacle().getLocation(),
                            userMatchOne, levelManager.getLevelTileMap().getMapLevel().size(), tileWidth);

                    if (damage != -1) {
                        switch (obstacles.get(i).getOldStatus()) {
                            case WOODEN:
                                if (obstacles.get(i).getDestroyCounter() == 0) {
                                    obstacles.get(i).getObstacle().changeBitmapProperties(Assets.wood_break_anim, 30, 7, 4000, false, true);
                                } else if (obstacles.get(i).getDestroyCounter() == 1) {
                                    obstacles.get(i).getObstacle().changeBitmapProperties(Assets.wood_25, 20, 1, 2000, true, true);
                                }
                                break;
                            case CEMENT:
                                if (obstacles.get(i).getDestroyCounter() == 0) {
                                    obstacles.get(i).getObstacle().changeBitmapProperties(Assets.cement_break_anim, 30, 6, 4000, false, true);
                                } else if (obstacles.get(i).getDestroyCounter() == 1) {
                                    obstacles.get(i).getObstacle().changeBitmapProperties(Assets.cement_25, 20, 1, 2000, true, true);
                                } else if (obstacles.get(i).getDestroyCounter() == 2) {
                                    obstacles.get(i).getObstacle().changeBitmapProperties(Assets.cement_50, 20, 1, 2000, true, true);
                                } else if (obstacles.get(i).getDestroyCounter() == 3) {
                                    obstacles.get(i).getObstacle().changeBitmapProperties(Assets.cement_75, 20, 1, 2000, true, true);
                                }
                                break;
                        }
                    }
                }
            }
        }
    }


    private void clearDamagedObstacle(ArrayList<Obstacles> obstacles) {
        if (!obstacles.isEmpty()) {
            for (int i = obstacles.size() - 1; i >= 0; i--) {

                //has already been accounted for and is awaiting deletion
                if (obstacles.get(i).getStatus() == NONE && obstacles.get(i).getObstacle().animFinished()) {
                    switch (obstacles.get(i).getOldStatus()) {
                        case GLASS:
                            levelManager.addToGlassCounter();
                            break;
                        case CEMENT:
                            levelManager.addToCementCounter();
                            break;
                        case WOODEN:
                            levelManager.addToWoodenCounter();
                            break;
                    }
                    obstacles.remove(i);
                }
            }
        }
    }

    /**************************************************************
     Changes the replaced urbs (the original matched urbs) into a
     random urb bitmap, also sorts the urbs into the
     right position off screen so that it is in the right position
     when falling down. Also stores the path to the right location.
     ***************************************************************/
    private void sortFuturePositionsInDescendingOrderOfY() {
        int yLoc = 0;
        gameMethods.associateCoordinatesWithPosition(futureCoordinates, tileLocations, futurePositions);

        //ArrayList<Integer> sorted = gameMethods.sortPointArrayInDescendingOrderByY(futureCoordinates);

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

            //remove userMatches if they contain matchesOffScreen
            for (int i = userMatchOne.size() - 1; i >= 0; i--) {
                if (matchesOffScreen.contains(userMatchOne.get(i))) {
                    userMatchOne.remove(i);
                }
            }
        }

        for (int i = 0; i < urbMatchOne.size(); i++) {

            changeToRandomBitmap(urbMatchOne.get(i), Urbs);
            Urbs.get(urbMatchOne.get(i)).setY((int) ((0 - Urbs.get(urbMatchOne.get(i)).getHeight() + yLoc) * AndroidGame.GAME_SCALE_X));
            Urbs.get(urbMatchOne.get(i)).setX(-200);
            Urbs.get(urbMatchOne.get(i)).setLocation(-200);
            /*Urbs.get(urbMatchOne.get(i)).findLine(Urbs.get(urbMatchOne.get(i)).getX(), Urbs.get(urbMatchOne.get(i)).getY(),
                    futureCoordinates.get(i).x, futureCoordinates.get(i).y);*//*
            yLoc = yLoc - 50;*/

        }
    }

    /***********************************************************************************************
     Given an array which holds the map element of the empty tiles, merge the elements and position
     into the arrays which will be used to move elements down
     ***********************************************************************************************/
    private void addEmptyTilesAfterBrokenObstacleRemoved(ArrayList<Integer> elementsToAdd) {

        if (elementsToAdd.size() > 1) {
            //make sure empty tile array starts at the highest element
            Collections.sort(elementsToAdd, Collections.<Integer>reverseOrder());
        }

        if (!futurePositions.isEmpty()) {
            futurePositions.addAll(elementsToAdd);

            //make sure array starts at the highest element
            Collections.sort(futurePositions, Collections.<Integer>reverseOrder());

            for (int i = 1; i < futurePositions.size(); i++) {
                int key = futurePositions.get(i);
                int j = i - 1;

                while (j >= 0 && (futurePositions.get(j).compareTo(key)) < 0) {
                    futurePositions.set(j + 1, futurePositions.get(j));
                    j--;
                }
                futurePositions.set(j + 1, key);
            }
        }

        if (!futureCoordinates.isEmpty()) {
            for (int i = 0; i < elementsToAdd.size(); i++) {
                int index = futurePositions.indexOf(elementsToAdd.get(i));
                futureCoordinates.add(index, tileLocations.get(elementsToAdd.get(i)));
            }
        }
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

    private void changeToEffectAnimation(int num, List<UrbieAnimation> objects, Urbies.UrbieType type) {
        switch (type) {
            case GOBSTOPPER:
                objects.get(num).changeBitmapProperties(Assets.gobstopper_fade_in_out, 30, 5, 500, false, false);
                objects.get(num).setType(Urbies.UrbieType.GOBSTOPPER);
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

    private void changeToSpecialUrb(int num, List<UrbieAnimation> objects, Urbies.UrbieType type) {
        int duration = new Random().nextInt(1200) + 3001;
        objects.get(num).resetAnimation();
        switch (type) {
            case GOBSTOPPER:
                objects.get(num).changeBitmapProperties(Assets.gobstopper, 30, 5, new Random().nextInt(12000) + 3001, true, true);
                objects.get(num).setType(Urbies.UrbieType.GOBSTOPPER);
                break;
            case WHITE_CHOCOLATE:
                objects.get(num).changeBitmapProperties(Assets.whiteChocolate, 30, 5, duration, true, true);
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


    private void clearUserSelection() {
        urbOne = -1;
        one = -1;
        urbTwo = -1;
        two = -1;
        noValidSwap = false;
    }


    private void visualiseSpecialUrb(int position) {
        switch (Urbs.get(position).getType()) {
            case GOBSTOPPER:
                Urbs.get(position).setBitmap(Assets.gobstopperBounce);
                break;
            case WHITE_CHOCOLATE:
                Urbs.get(position).setBitmap(Assets.whiteChocolateBounce);
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
            case GOBSTOPPER:
                Urbs.get(position).setBitmap(Assets.gobstopper);
                break;
            case WHITE_CHOCOLATE:
                Urbs.get(position).setBitmap(Assets.whiteChocolate);
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
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.whiteChocolateBounce);
                        break;
                    case GOBSTOPPER:
                        Urbs.get(possibleMatches.get(loop)).setBitmap(Assets.gobstopperBounce);
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


    private int findObjectByPosition(int index, List<UrbieAnimation> objects) {
        int found = -1;

        for (int i = 0; i < objects.size(); i++) {
            if (objects.get(i).getLocation() == index) {
                found = i;
                break;
            }
        }

        return found;
    }


    private void storeMatchList(ArrayList<Integer> matchOne, ArrayList<Integer> matchTwo) {
        userMatchOne.addAll(matchOne);
        userMatchTwo.addAll(matchTwo);
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


    private void freedomSounds() {
        ArrayList<Urbies.UrbieType> type = new ArrayList<>();
        float volume = 0.1f;
        if (!urbMatchOne.isEmpty()) {
            for (int i = 0; i < urbMatchOne.size(); i++) {
                if (!type.contains(Urbs.get(urbMatchOne.get(i)).getType())) {
                    type.add(Urbs.get(urbMatchOne.get(i)).getType());
                }
            }
        } else {
            if (!matchesOffScreen.isEmpty()) {
                for (int i = 0; i < matchesOffScreen.size(); i++) {
                    if (!type.contains(Urbs.get(matchesOffScreen.get(i)).getType())) {
                        type.add(Urbs.get(matchesOffScreen.get(i)).getType());
                    }
                }
            }
        }

        for (int i = 0; i < type.size(); i++) {
            Urbies.UrbieType showType = type.get(i);

            switch (showType) {
                case BABY:
                    Assets.babyFreed.play(volume);
                    break;
                case PAC:
                    Assets.pacFreed.play(volume);
                    break;
                case NERD:
                    Assets.nerdFreed.play(volume);
                    break;
                case PIGTAILS:
                    Assets.pigtailsFreed.play(volume);
                    break;
                case PUNK:
                    Assets.punkFreed.play(volume);
                    break;
                case GIRL_NERD:
                    Assets.girlNerdFreed.play(volume);
                    break;
                case LADY:
                    Assets.ladyFreed.play(volume);
                    break;
                case ROCKER:
                    Assets.rockerFreed.play(volume + 0.1f);
                    break;
            }
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

    private boolean hasSpecialObjectBeenSwapped(List<UrbieAnimation> objects, int element) {
        boolean result = false;

        switch (objects.get(element).getType()) {
            case GOBSTOPPER:
            case STRIPE_HORIZONTAL:
            case STRIPE_VERTICAL:
            case WHITE_CHOCOLATE:
                result = true;
                break;
        }
        return result;
    }


    private ArrayList<List<Integer>> setSpecialUrbPattern(Urbies.UrbieType type1, int urbPos1, int onePos, Urbies.UrbieType type2, int urbPos2, int twoPos) {

        if (type1 == type2) {
            switch (type1) {
                case GOBSTOPPER:
                    setMagicAndMagic(urbPos1, urbPos2, twoPos);
                    break;
                case STRIPE_HORIZONTAL:
                    setDoubleHorizontalStripedUrbs(urbPos1, onePos, urbPos2, twoPos);
                    break;
                case STRIPE_VERTICAL:
                    setDoubleVerticalStripedUrbs(urbPos1, onePos, urbPos2, twoPos);
                    break;
                case WHITE_CHOCOLATE:
                    setChameleonAndChameleon(urbPos1, onePos, urbPos2, twoPos);
                    break;
            }
        } else if (type1 == Urbies.UrbieType.GOBSTOPPER || type2 == Urbies.UrbieType.GOBSTOPPER) {

            if (type1 == Urbies.UrbieType.GOBSTOPPER) {
                specialUrbUserObject = urbPos1;
                userClicksUrb = urbPos2;
                userSwapType = type2;
            } else {
                specialUrbUserObject = urbPos2;
                userClicksUrb = urbPos1;
                userSwapType = type1;
            }
            setMagicianAndOther(userClicksUrb);

        } else if (type1 == Urbies.UrbieType.WHITE_CHOCOLATE || type2 == Urbies.UrbieType.WHITE_CHOCOLATE) {
            if (type1 == Urbies.UrbieType.STRIPE_HORIZONTAL || type2 == Urbies.UrbieType.STRIPE_HORIZONTAL) {
                int urbSp;
                int urbTemp;
                int posTemp;
                int posSp;

                if (type1 == Urbies.UrbieType.WHITE_CHOCOLATE) {
                    urbSp = urbPos1;
                    urbTemp = urbPos2;
                    posTemp = twoPos;
                    posSp = onePos;
                } else {
                    urbSp = urbPos2;
                    urbTemp = urbPos1;
                    posTemp = onePos;
                    posSp = twoPos;
                }
                setChameleonAndHStripe(urbSp, urbTemp, posTemp, posSp);

            } else if (type1 == Urbies.UrbieType.STRIPE_VERTICAL || type2 == Urbies.UrbieType.STRIPE_VERTICAL) {
                int urbSp;
                int urbTemp;
                int pos;
                int posSp;

                if (type1 == Urbies.UrbieType.WHITE_CHOCOLATE) {
                    urbSp = urbPos1;
                    urbTemp = urbPos2;
                    pos = twoPos;
                    posSp = onePos;
                } else {
                    urbSp = urbPos2;
                    urbTemp = urbPos1;
                    pos = onePos;
                    posSp = twoPos;
                }
                setChameleonAndVStripe(urbSp, urbTemp, pos, posSp);
            }
        } else if (type1 == Urbies.UrbieType.STRIPE_HORIZONTAL || type2 == Urbies.UrbieType.STRIPE_HORIZONTAL) {
            setDoubleStripedUrbs(type1, urbPos1, onePos, type2, urbPos2, twoPos);
        }

        return null;
    }

    private void setMagicianAndOther(int urbTemp) {
        if (setSpecialPhase == 0) {
            allowSpecialUserInput = true;
            startRotateAvailableUrbs = true;
            startRotateTimer = System.currentTimeMillis();
            urbCounter = 0;
            setSpecialPhase = 1;
            Urbs.get(urbTemp).setFrameCount(5);
        }

        if (startRotateAvailableUrbs) {
            if (System.currentTimeMillis() > startRotateTimer + 300) {
                if (urbCounter < urbTypesInLevel.size()) {
                    urbCounter++;
                }
                if (urbCounter >= urbTypesInLevel.size()) {
                    urbCounter = 0;
                }

                switch (urbTypesInLevel.get(urbCounter)) {
                    case PAC:
                        Urbs.get(urbTemp).setBitmap(Assets.pac);
                        bmpSelected = Assets.pac;
                        break;
                    case PIGTAILS:
                        Urbs.get(urbTemp).setBitmap(Assets.pigtails);
                        bmpSelected = Assets.pigtails;
                        break;
                    case PUNK:
                        Urbs.get(urbTemp).setBitmap(Assets.punk);
                        bmpSelected = Assets.punk;
                        break;
                    case ROCKER:
                        Urbs.get(urbTemp).setBitmap(Assets.rocker);
                        bmpSelected = Assets.rocker;
                        break;
                    case NERD:
                        Urbs.get(urbTemp).setBitmap(Assets.nerd);
                        bmpSelected = Assets.nerd;
                        break;
                    case GIRL_NERD:
                        Urbs.get(urbTemp).setBitmap(Assets.nerd_girl);
                        bmpSelected = Assets.nerd_girl;
                        break;
                    case BABY:
                        Urbs.get(urbTemp).setBitmap(Assets.baby);
                        bmpSelected = Assets.baby;
                        break;
                    case LADY:
                        Urbs.get(urbTemp).setBitmap(Assets.lady);
                        bmpSelected = Assets.lady;
                        break;
                }
                startRotateTimer = System.currentTimeMillis();
            }
        }
    }

    private void setChameleonAndChameleon(int urbSp, int pos1, int urbTemp, int pos2) {
        List temp = new ArrayList<>(urbTypesInLevel);
        Collections.shuffle(temp);
        temp = temp.subList(0, 2);

        for (int i = 0; i < temp.size(); i++) {
            specials.addAll(gameMethods.getMatchingObjectsByType(Urbs, (Urbies.UrbieType) temp.get(i)));
        }

        for (int i = 0; i < specials.size(); i++) {
            specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                    Assets.lightning0, Assets.lightning1)),
                    Urbs.get(urbSp).getX(),
                    Urbs.get(urbSp).getY(),
                    Urbs.get(specials.get(i)).getX(),
                    Urbs.get(specials.get(i)).getY(),
                    80, tileH));
        }
        Urbs.get(urbSp).changeBitmapProperties(Assets.white_chocolate_fade, 30, 5, 800, false, true);
        Urbs.get(urbTemp).changeBitmapProperties(Assets.white_chocolate_fade, 30, 5, 800, false, true);

        if (!specials.isEmpty()) {
            levelManager.addSpecialUrbBonusToScore(Urbs.get(urbSp).getType(), specials.size() - 1);
            for (int i = 0; i < specials.size(); i++) {
                levelManager.addUrbCounter(Urbs.get(specials.get(i)).getType(), 1);
            }
            //convert specials to map positions
            for (int i = 0; i < specials.size(); i++) {
                userMatchOne.add(gameMethods.findMapLocationOfBitmap(tileLocations, Urbs, specials.get(i)));
            }

            userMatchOne.add(pos1);
            userMatchOne.add(pos2);
            Collections.sort(userMatchOne, reverseOrder());


            objectsToMoveDown();

            if (levelManager.isWood() || levelManager.isCement()) {
                findDamagedObstacles(obstacleTiles);
            }

            if (!objectsToMoveDown.isEmpty()) {
                for (int i = 0; i < objectsToMoveDown.size(); i++) {
                    urbsToMoveDown.add(gameMethods.findBitmapByMapLocation(Urbs, tileLocations, objectsToMoveDown.get(i)));
                }
            }

            for (int i = 0; i < userMatchOne.size(); i++) {
                urbMatchOne.add(findObjectByPosition(userMatchOne.get(i), Urbs));
            }

            Urbs.get(urbTemp).clearPath();
            Urbs.get(urbSp).clearPath();

            setMatchedUrbs();

            sortFuturePositionsInDescendingOrderOfY();

            initialise = 0;
            pState = Procedure.MATCH;
        }
    }

    private void setChameleonAndHStripe(int urbSp, int urbTemp, int pos, int posSp) {
        ArrayList<Integer> temp = gameMethods.findObjectsInRow(Urbs, pos, levelManager.getLevelTileMap().getMapLevel(),
                tileLocations, tileWidth);

        for (int i = 0; i < temp.size(); i++) {
            specials.addAll(gameMethods.findMatchingObjectTypes(Urbs, temp.get(i)));
        }

        gameMethods.uniqueArrayIntegerList(specials);

        if (specials.contains(urbSp)) {
            specials.remove(specials.indexOf(urbSp));
        }

        for (int i = 0; i < specials.size(); i++) {
            specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                    Assets.lightning0, Assets.lightning1)),
                    Urbs.get(urbSp).getX(),
                    Urbs.get(urbSp).getY(),
                    Urbs.get(specials.get(i)).getX(),
                    Urbs.get(specials.get(i)).getY(),
                    80, tileH));
        }
        Urbs.get(urbSp).changeBitmapProperties(Assets.white_chocolate_fade, 30, 5, 800, false, true);
        Urbs.get(urbTemp).changeBitmapProperties(Assets.horizontal_fade, 30, 5, 800, false, true);
        levelManager.addSpecialUrbBonusToScore(Urbs.get(urbSp).getType(), specials.size() - 1);
        for (int i = 0; i < specials.size(); i++) {
            levelManager.addUrbCounter(Urbs.get(specials.get(i)).getType(), 1);
        }

        if (!specials.isEmpty()) {
            //convert specials to map positions
            for (int i = 0; i < specials.size(); i++) {
                userMatchOne.add(gameMethods.findMapLocationOfBitmap(tileLocations, Urbs, specials.get(i)));
            }

            userMatchOne.add(posSp);
            Collections.sort(userMatchOne, reverseOrder());

            //identify if matches are next to any obstacles
            /**/

            objectsToMoveDown();
            if (levelManager.isWood() || levelManager.isCement()) {
                findDamagedObstacles(obstacleTiles);
            }

            if (!objectsToMoveDown.isEmpty()) {
                for (int i = 0; i < objectsToMoveDown.size(); i++) {
                    urbsToMoveDown.add(gameMethods.findBitmapByMapLocation(Urbs, tileLocations, objectsToMoveDown.get(i)));
                }
            }

            for (int i = 0; i < userMatchOne.size(); i++) {
                urbMatchOne.add(findObjectByPosition(userMatchOne.get(i), Urbs));
            }

            Urbs.get(urbTemp).clearPath();
            Urbs.get(urbSp).clearPath();

            setMatchedUrbs();
            sortFuturePositionsInDescendingOrderOfY();

            initialise = 0;
            pState = Procedure.MATCH;
        }
    }

    private void setMagicAndMagic(int urbPos1, int urbPos2, int pos2) {
        Urbs.get(urbPos1).changeBitmapProperties(Assets.gobstopper_fade_in_out, 30, 5, 800, false, true);
        Urbs.get(urbPos1).setType(Urbies.UrbieType.GOBSTOPPER_BOMB);
        Urbs.get(urbPos2).changeBitmapProperties(Assets.gobstopper_fade_in_out, 30, 5, 800, false, true);
        magicBomb = urbPos1;

        userMatchOne.add(pos2);

        /**/

        objectsToMoveDown();
        if (levelManager.isWood() || levelManager.isCement()) {
            findDamagedObstacles(obstacleTiles);
        }

        if (!objectsToMoveDown.isEmpty()) {
            for (int i = 0; i < objectsToMoveDown.size(); i++) {
                urbsToMoveDown.add(gameMethods.findBitmapByMapLocation(Urbs, tileLocations, objectsToMoveDown.get(i)));
            }
        }

        for (int i = 0; i < userMatchOne.size(); i++) {
            urbMatchOne.add(findObjectByPosition(userMatchOne.get(i), Urbs));
        }

        Urbs.get(urbPos2).clearPath();
        Urbs.get(urbPos1).clearPath();
        setMatchedUrbs();

        sortFuturePositionsInDescendingOrderOfY();

        initialise = 0;
        pState = Procedure.MATCH;
    }

    private void objectsToMoveDown() {
        gameMethods.moveRemainingObjects(Urbs, userMatchOne, obstacleTiles, tileWidth, tileLocations, levelManager.getLevelTileMap().getMapLevel(), matchesOffScreen, entrance);
        /*ArrayList<ObjectPathCreator> creators = gameMethods.separateTheMadness(Urbs, userMatchOne, obstacleTiles, tileWidth, tileLocations, levelManager.getLevelTileMap().getMapLevel(), matchesOffScreen, entrance);

        for (int i = 0; i < creators.size(); i++) {
            objectsToMoveDown.add(creators.get(i).getElement());
            coordinatesToMoveTo.add(creators.get(i).getPath().get(creators.get(i).getPath().size() - 1));
        }*/

        System.out.println("objectsToMoveDown = "+objectsToMoveDown);
    }

    private void setChameleonAndVStripe(int urbSp, int urbTemp, int pos, int posSp) {
        ArrayList<Integer> temp = gameMethods.findObjectsInColumn(Urbs, pos, levelManager.getLevelTileMap().getMapLevel(),
                tileLocations, tileWidth);

        for (int i = 0; i < temp.size(); i++) {
            specials.addAll(gameMethods.findMatchingObjectTypes(Urbs, temp.get(i)));
        }

        if (specials.contains(urbSp)) {
            specials.remove(specials.indexOf(urbSp));
        }

        gameMethods.uniqueArrayIntegerList(specials);

        for (int i = 0; i < specials.size(); i++) {
            specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                    Assets.lightning0, Assets.lightning1)),
                    Urbs.get(urbSp).getX(),
                    Urbs.get(urbSp).getY(),
                    Urbs.get(specials.get(i)).getX(),
                    Urbs.get(specials.get(i)).getY(),
                    80, tileH));
        }
        Urbs.get(urbSp).changeBitmapProperties(Assets.white_chocolate_fade, 30, 5, 800, false, true);
        Urbs.get(urbTemp).changeBitmapProperties(Assets.vertical_fade, 30, 5, 800, false, true);
        levelManager.addSpecialUrbBonusToScore(Urbs.get(urbSp).getType(), specials.size() - 1);
        for (int i = 0; i < specials.size(); i++) {
            levelManager.addUrbCounter(Urbs.get(specials.get(i)).getType(), 1);
        }

        if (!specials.isEmpty()) {
            //convert specials to map positions
            for (int i = 0; i < specials.size(); i++) {
                userMatchOne.add(gameMethods.findMapLocationOfBitmap(tileLocations, Urbs, specials.get(i)));
            }

            userMatchOne.add(posSp);
            Collections.sort(userMatchOne, reverseOrder());

            //identify if matches are next to any obstacles
            /**/

            objectsToMoveDown();
            if (levelManager.isWood() || levelManager.isCement()) {
                findDamagedObstacles(obstacleTiles);
            }
            if (!objectsToMoveDown.isEmpty()) {
                for (int i = 0; i < objectsToMoveDown.size(); i++) {
                    urbsToMoveDown.add(gameMethods.findBitmapByMapLocation(Urbs, tileLocations, objectsToMoveDown.get(i)));
                }
            }

            for (int i = 0; i < userMatchOne.size(); i++) {
                urbMatchOne.add(findObjectByPosition(userMatchOne.get(i), Urbs));
            }

            Urbs.get(urbTemp).clearPath();
            Urbs.get(urbSp).clearPath();

            setMatchedUrbs();
            sortFuturePositionsInDescendingOrderOfY();

            initialise = 0;
            pState = Procedure.MATCH;
        }
    }

    private void setDoubleStripedUrbs(Urbies.UrbieType type1, int urbPos1, int onePos, Urbies.UrbieType type2, int urbPos2, int twoPos) {
        if (type1 == Urbies.UrbieType.STRIPE_VERTICAL || type2 == Urbies.UrbieType.STRIPE_VERTICAL) {
            if (type1 == Urbies.UrbieType.STRIPE_VERTICAL) {
                specSpecials.add(gameMethods.findObjectsInColumn(Urbs, onePos, levelManager.getLevelTileMap().getMapLevel(),
                        tileLocations, tileWidth));

                levelManager.addSpecialUrbBonusToScore(Urbs.get(urbPos1).getType(), specSpecials.get(0).size() - 1);

                for (int i = 0; i < specSpecials.get(0).size(); i++) {
                    levelManager.addUrbCounter(Urbs.get(specSpecials.get(0).get(i)).getType(), 1);
                }

                int size = specSpecials.get(0).size();

                specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                        Assets.lightning0, Assets.lightning1)),
                        Urbs.get(specSpecials.get(0).get(0)).getX(),
                        Urbs.get(specSpecials.get(0).get(0)).getY(),
                        Urbs.get(specSpecials.get(0).get(size - 1)).getX(),
                        Urbs.get(specSpecials.get(0).get(size - 1)).getY(),
                        80, tileH)
                );

                Urbs.get(urbPos1).changeBitmapProperties(Assets.vertical_fade, 30, 5, 800, false, true);

                specSpecials.add(gameMethods.findObjectsInRow(Urbs, twoPos, levelManager.getLevelTileMap().getMapLevel(),
                        tileLocations, tileWidth));
                levelManager.addSpecialUrbBonusToScore(Urbs.get(urbPos2).getType(), specSpecials.get(1).size() - 1);

                for (int i = 0; i < specSpecials.get(1).size(); i++) {
                    levelManager.addUrbCounter(Urbs.get(specSpecials.get(1).get(i)).getType(), 1);
                }


                size = specSpecials.get(1).size();
                specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                        Assets.lightning0, Assets.lightning1)),
                        Urbs.get(specSpecials.get(1).get(0)).getX(),
                        Urbs.get(specSpecials.get(1).get(0)).getY(),
                        Urbs.get(specSpecials.get(1).get(size - 1)).getX(),
                        Urbs.get(specSpecials.get(1).get(size - 1)).getY(),
                        80, tileH)
                );

                Urbs.get(urbPos2).changeBitmapProperties(Assets.horizontal_fade, 30, 5, 800, false, true);
            } else {
                specSpecials.add(gameMethods.findObjectsInColumn(Urbs, twoPos, levelManager.getLevelTileMap().getMapLevel(),
                        tileLocations, tileWidth));

                levelManager.addSpecialUrbBonusToScore(Urbs.get(urbPos2).getType(), specSpecials.get(0).size() - 1);

                for (int i = 0; i < specSpecials.get(0).size(); i++) {
                    levelManager.addUrbCounter(Urbs.get(specSpecials.get(0).get(i)).getType(), 1);
                }


                int size = specSpecials.get(0).size();

                specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                        Assets.lightning0, Assets.lightning1)),
                        Urbs.get(specSpecials.get(0).get(0)).getX(),
                        Urbs.get(specSpecials.get(0).get(0)).getY(),
                        Urbs.get(specSpecials.get(0).get(size - 1)).getX(),
                        Urbs.get(specSpecials.get(0).get(size - 1)).getY(),
                        80, tileH)
                );

                Urbs.get(urbPos2).changeBitmapProperties(Assets.horizontal_fade, 30, 5, 800, false, true);
                //============================================================================

                specSpecials.add(gameMethods.findObjectsInRow(Urbs, onePos, levelManager.getLevelTileMap().getMapLevel(),
                        tileLocations, tileWidth));

                levelManager.addSpecialUrbBonusToScore(Urbs.get(urbPos1).getType(), specSpecials.get(1).size() - 1);

                for (int i = 0; i < specSpecials.get(1).size(); i++) {
                    levelManager.addUrbCounter(Urbs.get(specSpecials.get(1).get(i)).getType(), 1);
                }


                size = specSpecials.get(1).size();
                specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                        Assets.lightning0, Assets.lightning1)),
                        Urbs.get(specSpecials.get(1).get(0)).getX(),
                        Urbs.get(specSpecials.get(1).get(0)).getY(),
                        Urbs.get(specSpecials.get(1).get(size - 1)).getX(),
                        Urbs.get(specSpecials.get(1).get(size - 1)).getY(),
                        80, tileH)
                );

                Urbs.get(urbPos1).changeBitmapProperties(Assets.vertical_fade, 30, 5, 800, false, true);
            }
        }

        if (!specSpecials.isEmpty()) {
            for (int i = 0; i < specSpecials.size(); i++) {
                for (int j = 0; j < specSpecials.get(i).size(); j++) {
                    userMatchOne.add(gameMethods.findMapLocationOfBitmap(tileLocations, Urbs, specSpecials.get(i).get(j)));
                }
            }
        }

        gameMethods.uniqueArrayIntegerList(userMatchOne);
        Collections.sort(userMatchOne, reverseOrder());

        //identify if matches are next to any obstacles
       /* */

        objectsToMoveDown();
        if (levelManager.isWood() || levelManager.isCement()) {
            findDamagedObstacles(obstacleTiles);
        }
        if (!objectsToMoveDown.isEmpty()) {
            for (int i = 0; i < objectsToMoveDown.size(); i++) {
                urbsToMoveDown.add(gameMethods.findBitmapByMapLocation(Urbs, tileLocations, objectsToMoveDown.get(i)));
            }
        }

        for (int i = 0; i < userMatchOne.size(); i++) {
            urbMatchOne.add(findObjectByPosition(userMatchOne.get(i), Urbs));
        }

        Urbs.get(urbPos1).clearPath();
        Urbs.get(urbPos2).clearPath();

        setMatchedUrbs();

        sortFuturePositionsInDescendingOrderOfY();

        initialise = 0;
        pState = Procedure.MATCH;
    }


    private void setDoubleHorizontalStripedUrbs(int urbPos1, int onePos, int urbPos2, int twoPos) {
        int size;

        specSpecials.add(gameMethods.findObjectsInRow(Urbs, onePos, levelManager.getLevelTileMap().getMapLevel(),
                tileLocations, tileWidth));

        levelManager.addSpecialUrbBonusToScore(Urbs.get(urbPos1).getType(), specSpecials.get(0).size() - 1);

        for (int i = 0; i < specSpecials.get(0).size(); i++) {
            levelManager.addUrbCounter(Urbs.get(specSpecials.get(0).get(i)).getType(), 1);
        }


        size = specSpecials.get(0).size();
        specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                Assets.lightning0, Assets.lightning1)),
                Urbs.get(specSpecials.get(0).get(0)).getX(),
                Urbs.get(specSpecials.get(0).get(0)).getY(),
                Urbs.get(specSpecials.get(0).get(size - 1)).getX(),
                Urbs.get(specSpecials.get(0).get(size - 1)).getY(),
                80, tileH)
        );
        Urbs.get(urbPos1).changeBitmapProperties(Assets.horizontal_fade, 30, 5, 800, false, true);

        specSpecials.add(gameMethods.findObjectsInRow(Urbs, twoPos, levelManager.getLevelTileMap().getMapLevel(),
                tileLocations, tileWidth));

        levelManager.addSpecialUrbBonusToScore(Urbs.get(urbPos2).getType(), specSpecials.get(1).size() - 1);

        for (int i = 0; i < specSpecials.get(1).size(); i++) {
            levelManager.addUrbCounter(Urbs.get(specSpecials.get(1).get(i)).getType(), 1);
        }

        size = specSpecials.get(1).size();
        specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                Assets.lightning0, Assets.lightning1)),
                Urbs.get(specSpecials.get(1).get(0)).getX(),
                Urbs.get(specSpecials.get(1).get(0)).getY(),
                Urbs.get(specSpecials.get(1).get(size - 1)).getX(),
                Urbs.get(specSpecials.get(1).get(size - 1)).getY(),
                80, tileH)
        );
        Urbs.get(urbPos2).changeBitmapProperties(Assets.horizontal_fade, 30, 5, 800, false, true);

        if (!specSpecials.isEmpty()) {
            for (int i = 0; i < specSpecials.size(); i++) {
                for (int j = 0; j < specSpecials.get(i).size(); j++) {
                    userMatchOne.add(gameMethods.findMapLocationOfBitmap(tileLocations, Urbs, specSpecials.get(i).get(j)));
                }
            }
        }

        gameMethods.uniqueArrayIntegerList(userMatchOne);
        Collections.sort(userMatchOne, reverseOrder());

        //identify if matches are next to any obstacles
        /**/

        objectsToMoveDown();
        if (levelManager.isWood() || levelManager.isCement()) {
            findDamagedObstacles(obstacleTiles);
        }
        if (!objectsToMoveDown.isEmpty()) {
            for (int i = 0; i < objectsToMoveDown.size(); i++) {
                urbsToMoveDown.add(gameMethods.findBitmapByMapLocation(Urbs, tileLocations, objectsToMoveDown.get(i)));
            }
        }

        for (int i = 0; i < userMatchOne.size(); i++) {
            urbMatchOne.add(findObjectByPosition(userMatchOne.get(i), Urbs));
        }

        Urbs.get(urbPos1).clearPath();
        Urbs.get(urbPos2).clearPath();

        setMatchedUrbs();

        sortFuturePositionsInDescendingOrderOfY();

        initialise = 0;
        pState = Procedure.MATCH;
    }


    private void setDoubleVerticalStripedUrbs(int urbPos1, int onePos, int urbPos2, int twoPos) {
        specSpecials.add(gameMethods.findObjectsInColumn(Urbs, onePos, levelManager.getLevelTileMap().getMapLevel(),
                tileLocations, tileWidth));

        levelManager.addSpecialUrbBonusToScore(Urbs.get(urbPos1).getType(), specSpecials.get(0).size() - 1);

        for (int i = 0; i < specSpecials.get(0).size(); i++) {
            levelManager.addUrbCounter(Urbs.get(specSpecials.get(0).get(i)).getType(), 1);
        }

        int size = specSpecials.get(0).size();

        specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                Assets.lightning0, Assets.lightning1)),
                Urbs.get(specSpecials.get(0).get(0)).getX(),
                Urbs.get(specSpecials.get(0).get(0)).getY(),
                Urbs.get(specSpecials.get(0).get(size - 1)).getX(),
                Urbs.get(specSpecials.get(0).get(size - 1)).getY(),
                80, tileH)
        );

        Urbs.get(urbPos1).changeBitmapProperties(Assets.vertical_fade, 30, 5, 800, false, true);

        specSpecials.add(gameMethods.findObjectsInColumn(Urbs, twoPos, levelManager.getLevelTileMap().getMapLevel(),
                tileLocations, tileWidth));

        levelManager.addSpecialUrbBonusToScore(Urbs.get(urbPos2).getType(), specSpecials.get(1).size() - 1);
        for (int i = 0; i < specSpecials.get(1).size(); i++) {
            levelManager.addUrbCounter(Urbs.get(specSpecials.get(1).get(i)).getType(), 1);
        }
        size = specSpecials.get(1).size();

        specialFX.add(new SpecialFX(new ArrayList<>(Arrays.asList(
                Assets.lightning0, Assets.lightning1)),
                Urbs.get(specSpecials.get(1).get(0)).getX(),
                Urbs.get(specSpecials.get(1).get(0)).getY(),
                Urbs.get(specSpecials.get(1).get(size - 1)).getX(),
                Urbs.get(specSpecials.get(1).get(size - 1)).getY(),
                80, tileH)
        );

        Urbs.get(urbPos2).changeBitmapProperties(Assets.vertical_fade, 30, 5, 800, false, true);

        if (!specSpecials.isEmpty()) {
            for (int i = 0; i < specSpecials.size(); i++) {
                for (int j = 0; j < specSpecials.get(i).size(); j++) {
                    userMatchOne.add(gameMethods.findMapLocationOfBitmap(tileLocations, Urbs, specSpecials.get(i).get(j)));
                }
            }
        }

        gameMethods.uniqueArrayIntegerList(userMatchOne);
        Collections.sort(userMatchOne, reverseOrder());

        //identify if matches are next to any obstacles
        /**/

        objectsToMoveDown();
        if (levelManager.isWood() || levelManager.isCement()) {
            findDamagedObstacles(obstacleTiles);
        }
        if (!objectsToMoveDown.isEmpty()) {
            for (int i = 0; i < objectsToMoveDown.size(); i++) {
                urbsToMoveDown.add(gameMethods.findBitmapByMapLocation(Urbs, tileLocations, objectsToMoveDown.get(i)));
            }
        }

        for (int i = 0; i < userMatchOne.size(); i++) {
            urbMatchOne.add(findObjectByPosition(userMatchOne.get(i), Urbs));
        }

        Urbs.get(urbPos1).clearPath();
        Urbs.get(urbPos2).clearPath();

        setMatchedUrbs();

        sortFuturePositionsInDescendingOrderOfY();

        initialise = 0;
        pState = Procedure.MATCH;
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

    private void sameAsOne() {
        objectsToMoveDown();
        if (levelManager.isWood() || levelManager.isCement()) {
            findDamagedObstacles(obstacleTiles);
        }
        clearDamagedObstacle(obstacleTiles);

        if (!objectsToMoveDown.isEmpty()) {
            for (int i = 0; i < objectsToMoveDown.size(); i++) {
                urbsToMoveDown.add(gameMethods.findBitmapByMapLocation(Urbs, tileLocations, objectsToMoveDown.get(i)));
            }
        }

        //NOT SURE IF I NEED THIS HERE
        //add the contents of userMatchOne into another arraylist which holds the urb value
        for (int i = 0; i < userMatchOne.size(); i++) {
            urbMatchOne.add(findObjectByPosition(userMatchOne.get(i), Urbs));
        }
        //System.out.println("urbMatchOne = "+urbMatchOne);
        //count number of urbs by type
        for (int i = 0; i < urbMatchOne.size(); i++) {
            levelManager.addUrbCounter(Urbs.get(urbMatchOne.get(i)).getType(), 1);
        }

        setMatchedUrbs();

        //move urbs that were part of the match off screen ready to move back into place
        sortFuturePositionsInDescendingOrderOfY();

        initialise = 0;
        pState = Procedure.MATCH;

    }

    private void sameMatch() {

        if (initialise == 0) {

            //define the move down path for remaining Urbs
            if (!urbsToMoveDown.isEmpty()) {
                for (int i = 0; i < urbsToMoveDown.size(); i++) {
                    Urbs.get(urbsToMoveDown.get(i)).findLine(Urbs.get(urbsToMoveDown.get(i)).getX(),
                            Urbs.get(urbsToMoveDown.get(i)).getY(), coordinatesToMoveTo.get(i).x, coordinatesToMoveTo.get(i).y);
                }
            }

            //define the bounce out path for matched Urbs
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
                freedomSounds();
                startBounceOutTime = System.currentTimeMillis();
            }

            initialise = 1;
        } else if (initialise == 1) {
            if (!specialUrbs.isEmpty()) {

                for (int k = 0; k < specialUrbs.size(); k++) {
                    changeToEffectAnimation(specialUrbs.get(k), Urbs, specialUrbTypes.get(k));
                }
            }

            initialise = 2;
        } else if (initialise == 2) {
            if (!specialUrbs.isEmpty()) {
                for (int k = 0; k < specialUrbs.size(); k++) {
                    if (Urbs.get(specialUrbs.get(k)).animFinished()) {
                        changeToSpecialUrb(specialUrbs.get(k), Urbs, specialUrbTypes.get(k));
                    }
                }
            }
        } else if (initialise == 3) {

            if (!urbsToMoveDown.isEmpty()) {
                for (int i = 0; i < urbsToMoveDown.size(); i++) {
                    Urbs.get(urbsToMoveDown.get(i)).clearPath();
                }
            }
            initialise = 4;

        } else if (initialise == 4) {
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
                    System.out.println("last path = " + creators.get(i).getPath().get(0));
                }


                initialise = 5;
            }
        }

        else if (initialise == 6) {
            for (int i = 0; i < urbMatchOne.size(); i++) {
                Urbs.get(urbMatchOne.get(i)).clearPath();
            }

            clearDamagedObstacle(obstacleTiles);

            initialise = 0;
            pState = Procedure.REPLACE_OBJECTS;

        }
    }

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
            if (!matchesOffScreen.isEmpty()) {
                notInPlay.addAll(matchesOffScreen);
                matchesOffScreen.clear();
            }
        }
    }
}
