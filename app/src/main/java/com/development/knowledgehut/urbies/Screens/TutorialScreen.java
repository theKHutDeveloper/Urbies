package com.development.knowledgehut.urbies.Screens;


import android.graphics.Paint;
import android.graphics.Point;
import android.view.MotionEvent;

import com.development.knowledgehut.urbies.Behaviours.GameMethods;
import com.development.knowledgehut.urbies.DrawableObjects.BitmapAnimation;
import com.development.knowledgehut.urbies.DrawableObjects.Images;
import com.development.knowledgehut.urbies.DrawableObjects.SpeechBubbles;
import com.development.knowledgehut.urbies.DrawableObjects.UrbieAnimation;
import com.development.knowledgehut.urbies.Frameworks.Game;
import com.development.knowledgehut.urbies.Frameworks.Graphics;
import com.development.knowledgehut.urbies.Frameworks.Screen;
import com.development.knowledgehut.urbies.Implementations.AndroidGame;
import com.development.knowledgehut.urbies.Objects.BaseTiles;
import com.development.knowledgehut.urbies.Objects.LevelManager;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

import static com.development.knowledgehut.urbies.Implementations.AndroidGame.OFFSET_Y;
import static com.development.knowledgehut.urbies.Screens.Urbies.UrbieStatus.NONE;

public class TutorialScreen extends Screen {

    private enum MatchState {
        AUTO, READY, SWAP, RESET_SWAP, USER_MATCH,
        SHUFFLE, SPECIAL_URBS, TUTORIAL, SWAP_TUTORIAL,
        TUTORIAL_RESET
    }

    private LevelManager levelManager;
    private GameMethods gameMethods = new GameMethods();
    private Paint text;
    private int wizard_anim_counter = 0;
    private int[] tutorialUrbs;
    private int[] tutorialLocations;
    private int tutorialCounter = 0;
    private int initialise;
    private SpeechBubbles speechBubble, speechTitle, speechCelebrate;
    private MatchState matchState;
    private ArrayList<Point> tileLocations = new ArrayList<>();
    private ArrayList<Integer> validTiles = new ArrayList<>();
    private int tileH, tileWidth, tileHeight;
    private int one = -1, two = -1;
    private int urbOne, urbTwo;
    private Images pause, help, board, playOn, menu;
    private ArrayList<Urbies.UrbieType> urbTypesInLevel = new ArrayList<>();
    private ArrayList<Integer> possibleMatches = new ArrayList<>();

    private List<UrbieAnimation> Urbs = new ArrayList<>();
    private BitmapAnimation wizard_idle;
    private ArrayList<Images> urbGUI = new ArrayList<>();
    private ArrayList<Integer> urbPossibleMatches = new ArrayList<>();

    private boolean showPossibleMove = false;
    private long startBounceOutTime, readyToMoveTimer, moveToNextScreenTimer;

    public TutorialScreen(Game game) {
        super(game);

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

        String msg1 = "";
        String msg2 = "";

        if (Urbies.level == -1) {
            msg1 = "Match four in a row vertically ";
            msg2 = "to create a striped sweet surprise";
            tutorialUrbs = new int[]{13, 14, 14, 19};
            tutorialLocations = new int[]{13, 14, 19, 14};
        } else if (Urbies.level == -2) {
            msg1 = "Match four in a row horizontally";
            msg2 = "to create a striped sweet surprise";
            tutorialUrbs = new int[]{5, 10, 5, 4};
            tutorialLocations = new int[]{7, 12, 7, 6};
        } else if (Urbies.level == -3) {
            msg1 = "Match five in a row ";
            msg2 = "to create a Gobstopper surprise";
            tutorialUrbs = new int[]{6, 11, 11, 10};
            tutorialLocations = new int[]{7, 12, 12, 11};
        } else if (Urbies.level == -4) {
            msg1 = "Create a L shaped match ";
            msg2 = "to create a white chocolate surprise";
            tutorialUrbs = new int[]{13, 14, 14, 10};
            tutorialLocations = new int[]{15, 16, 16, 11};
        }

        Point playOnPosition = new Point((int) (140 * AndroidGame.GAME_SCALE_X),
                (int) (410 * AndroidGame.GAME_SCALE_X));

        Point menuOnPosition = new Point((int) (20 * AndroidGame.GAME_SCALE_X),
                (int) (460 * AndroidGame.GAME_SCALE_X));

        Urbs = fakeUrbies(Urbs, validTiles, validTiles.size(), 10, 5);

        speechTitle = new SpeechBubbles(Assets.speechTitle, msg1, msg2, "",
                (int) (50 * AndroidGame.GAME_SCALE_X),
                (int) (100 * AndroidGame.GAME_SCALE_X), 15);

        speechBubble = new SpeechBubbles(Assets.speechBubble,
                "Swap the", "highlighted urbs", "",
                (int) (110 * AndroidGame.GAME_SCALE_X),
                (int) (400 * AndroidGame.GAME_SCALE_X), 30);

        speechCelebrate = new SpeechBubbles(Assets.speechCelebrate, "Congratulations! ", "Let's learn more", "to free the Urbs",
                (int) (110 * AndroidGame.GAME_SCALE_X),
                (int) (340 * AndroidGame.GAME_SCALE_X), 40);

        if (Urbies.level == -4) {
            speechCelebrate.setMessage("Well Done!! Now", " you know what", " to do, let's play!");
        }

        wizard_idle = new BitmapAnimation(Assets.wizard_idle, new Point((int) (160 * AndroidGame.GAME_SCALE_X),
                (int) (380 * AndroidGame.GAME_SCALE_X)), 10, 4, 10000, true, -1, true);

        playOn = new Images(Assets.playOn, playOnPosition);
        menu = new Images(Assets.menu, menuOnPosition);
        matchState = MatchState.TUTORIAL;

        tileH = baseTiles.getIndividualTileWidth();
        loadGui();
        initialise = 0;
    }

    private List<UrbieAnimation> fakeUrbies(List<UrbieAnimation> objects, ArrayList<Integer> valid, int size, int fps, int frames) {
        ArrayList<Integer> values = new ArrayList<>();

        if (Urbies.level == -1) {
            //Vertical
            Collections.addAll(values,
                    1, 1, 4, 3, 2,
                    4, 3, 7, 4, 2,
                    7, 2, 4, 2, 3,
                    3, 1, 3, 7, 2);

            urbTypesInLevel.add(Urbies.UrbieType.BABY);
            urbTypesInLevel.add(Urbies.UrbieType.NERD);
            urbTypesInLevel.add(Urbies.UrbieType.PIGTAILS);
            urbTypesInLevel.add(Urbies.UrbieType.PAC);
            urbTypesInLevel.add(Urbies.UrbieType.PUNK);

        } else if (Urbies.level == -2) {
            //Horizontal swap
            Collections.addAll(values,
                    2, 6, 7,
                    8, 8, 5, 8, 7,
                    2, 7, 8, 2, 6,
                    5, 6, 6, 7, 5,
                    8, 2, 5);

            urbTypesInLevel.add(Urbies.UrbieType.ROCKER);
            urbTypesInLevel.add(Urbies.UrbieType.GIRL_NERD);
            urbTypesInLevel.add(Urbies.UrbieType.LADY);
            urbTypesInLevel.add(Urbies.UrbieType.PAC);
            urbTypesInLevel.add(Urbies.UrbieType.PUNK);

        } else if (Urbies.level == -3) {
            //Colour Changer
            Collections.addAll(values,
                    1, 3, 4, 5,
                    8, 4, 5, 8, 1,
                    5, 5, 1, 5, 5,
                    3, 8, 3, 4);

            urbTypesInLevel.add(Urbies.UrbieType.BABY);
            urbTypesInLevel.add(Urbies.UrbieType.ROCKER);
            urbTypesInLevel.add(Urbies.UrbieType.NERD);
            urbTypesInLevel.add(Urbies.UrbieType.PIGTAILS);
            urbTypesInLevel.add(Urbies.UrbieType.GIRL_NERD);

        } else if (Urbies.level == -4) {
            //White Chocolate
            Collections.addAll(values,
                    4, 3, 8, 4, 5,
                    8, 1, 8, 1,
                    5, 1, 3, 3,
                    1, 8, 1, 1, 5);

            urbTypesInLevel.add(Urbies.UrbieType.BABY);
            urbTypesInLevel.add(Urbies.UrbieType.ROCKER);
            urbTypesInLevel.add(Urbies.UrbieType.NERD);
            urbTypesInLevel.add(Urbies.UrbieType.PIGTAILS);
            urbTypesInLevel.add(Urbies.UrbieType.GIRL_NERD);

        } else if (Urbies.level == 11) {
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

                initialise = 0;
                matchState = MatchState.TUTORIAL;
                tutorialCounter++;
                break;


            case TUTORIAL:
                if (Urbies.level == -1 || Urbies.level == -2 || Urbies.level == -3 || Urbies.level == -4) {
                    if (tutorialCounter == 2) {
                        wizard_anim_counter = 0;
                        tutorialCounter = 3;
                    }
                }

                if (Urbies.level == -1 && tutorialCounter == 1) {
                    if (initialise == 0) {
                        speechBubble.setMessage("Use the striped ", "sweet to free", "urbs in column");
                        initialise = 1;
                    }
                } else if (Urbies.level == -2 && tutorialCounter == 1) {
                    if (initialise == 0) {
                        speechBubble.setMessage("Use the striped ", "sweet to free ", "urbs in row");
                        initialise = 1;
                    }
                } else if (Urbies.level == -3 && tutorialCounter == 1) {
                    if (initialise == 0) {
                        speechBubble.setMessage("Use gobstopper", "to change urbs", "to another type ");
                        initialise = 1;
                    }
                } else if (Urbies.level == -4 && tutorialCounter == 1) {
                    if (initialise == 0) {
                        speechBubble.setMessage("Use white ", "chocolate to free ", "selected urbs");
                        initialise = 1;
                    }
                }

                if (tutorialCounter == 1 && initialise == 1) {
                    if (wizard_anim_counter == 0) {
                        wizard_idle.changeBitmapProperties(Assets.wizard_attack, 10, 3, 3000, false, true);
                        wizard_anim_counter = 1;
                    }

                    if (wizard_anim_counter == 1 && wizard_idle.animFinished()) {
                        wizard_idle.changeBitmapProperties(Assets.wizard_idle, 10, 4, 10000, true, true);
                        wizard_anim_counter = 2;
                    }
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

        speechTitle.draw(graphics);

        for (int i = 0; i < validTiles.size(); i++) {
            graphics.drawBitmap(Assets.tile, tileLocations.get(validTiles.get(i)).x, tileLocations.get(validTiles.get(i)).y);
        }


        for (int i = 0; i < Urbs.size(); i++) {
            if (Urbs.get(i).getStatus() == NONE) {
                Urbs.get(i).draw(graphics);
            }
        }

            for (int i = 0; i < validTiles.size(); i++) {
                if (tutorialCounter == 0) {
                    if (i != tutorialUrbs[0] && i != tutorialUrbs[1]) {
                        graphics.drawBitmap(Assets.tileShade, tileLocations.get(validTiles.get(i)).x, tileLocations.get(validTiles.get(i)).y);
                    }
                } else if (tutorialCounter == 1) {
                    if (i != tutorialUrbs[2] && i != tutorialUrbs[3]) {
                        graphics.drawBitmap(Assets.tileShade, tileLocations.get(validTiles.get(i)).x, tileLocations.get(validTiles.get(i)).y);
                    }
                }
            }

            if (tutorialCounter == 0) {
                if (matchState == MatchState.TUTORIAL) {
                    graphics.drawBitmap(Assets.bright_selector, Urbs.get(tutorialUrbs[0]).getX(), Urbs.get(tutorialUrbs[0]).getY());
                    graphics.drawBitmap(Assets.bright_selector, Urbs.get(tutorialUrbs[1]).getX(), Urbs.get(tutorialUrbs[1]).getY());
                }
            }

            if (tutorialCounter == 1) {
                if (matchState == MatchState.TUTORIAL) {
                    graphics.drawBitmap(Assets.bright_selector, Urbs.get(tutorialUrbs[2]).getX(), Urbs.get(tutorialUrbs[2]).getY());
                    graphics.drawBitmap(Assets.bright_selector, Urbs.get(tutorialUrbs[3]).getX(), Urbs.get(tutorialUrbs[3]).getY());
                }
            }

            if (tutorialCounter < 3) {
                speechBubble.draw(graphics);
            } else {
                speechCelebrate.draw(graphics);
                if (Urbies.level != -4) {
                    playOn.draw(graphics);
                }
                menu.draw(graphics);
            }
            wizard_idle.draw(graphics);


        if (matchState == MatchState.SWAP || matchState == MatchState.SWAP_TUTORIAL) {
            if (initialise == 1) {
                if (Urbs.get(urbOne).updatePath(deltaTime) && Urbs.get(urbTwo).updatePath(deltaTime)) {
                    initialise = 2;
                }
            }
        }

        if (matchState == MatchState.RESET_SWAP || matchState == MatchState.TUTORIAL_RESET) {
            if (initialise == 1) {
                if (Urbs.get(urbOne).updatePath(deltaTime) && Urbs.get(urbTwo).updatePath(deltaTime)) {
                    initialise = 2;
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
            case 1: moveValue = -tileWidth;

                break;
            case 2: moveValue = -1; break;
            case 3: moveValue = tileWidth; break;
            case 4: moveValue = 1;break;
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
                    if (matchState != MatchState.TUTORIAL) {
                        if (Urbs.get(urbOne).getStatus() == NONE && Urbs.get(urbTwo).getStatus() == NONE) {
                            matchState = MatchState.SWAP;
                        } else {
                            one = -1;
                            urbOne = -1;
                            two = -1;
                            urbTwo = -1;
                            matchState = MatchState.READY;
                        }

                    } else {
                        tutorialSwaps();
                    }
                    break;
                }
            }
        }
    }

    @Override
    public void input(MotionEvent event) {

    }

    private void loadGui() {
        Coordinates coordinates = new Coordinates();
        pause = new Images(Assets.pause, new Point(coordinates.getX(270), coordinates.getY(30) + OFFSET_Y));
        help = new Images(Assets.help, new Point(coordinates.getX(6), coordinates.getY(30) + OFFSET_Y));
        board = new Images(Assets.board, new Point(coordinates.getX(0), coordinates.getY(458) + OFFSET_Y));

        urbGUI.add(new Images(Assets.small_pac, new Point(coordinates.getX(0), coordinates.getY(460) + OFFSET_Y)));//0, (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_baby, new Point(coordinates.getX(40), coordinates.getY(460) + OFFSET_Y)));//(int) (40 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_lady, new Point(coordinates.getX(80), coordinates.getY(460) + OFFSET_Y)));//(int) (80 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_nerd, new Point(coordinates.getX(120), coordinates.getY(460) + OFFSET_Y)));//(int) (120 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_punk, new Point(coordinates.getX(160), coordinates.getY(460) + OFFSET_Y)));//(int) (160 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_pigtails, new Point(coordinates.getX(200), coordinates.getY(460) + OFFSET_Y)));//(int) (200 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_nerdgirl, new Point(coordinates.getX(240), coordinates.getY(460) + OFFSET_Y)));//(int) (240 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
        urbGUI.add(new Images(Assets.small_rocker, new Point(coordinates.getX(280), coordinates.getY(460) + OFFSET_Y)));//(int) (280 * AndroidGame.GAME_SCALE_X), (int) (460 * AndroidGame.GAME_SCALE_X)));
    }

    private void tutorialSwaps(){
        if (Urbies.level == -1) {
            if (tutorialCounter == 0) {
                if (one == 13 && two == 14 || one == 14 && two == 13) {
                    matchState = MatchState.SWAP;
                }
            } else if (tutorialCounter == 1) {
                if (one == 14 && two == 19 || one == 19 && two == 14) {
                    matchState = MatchState.SWAP;
                }
            }
        } else if (Urbies.level == -2) {
            if (tutorialCounter == 0) {
                if (one == 7 && two == 12 || one == 12 && two == 7) {
                    matchState = MatchState.SWAP;
                }
            } else if (tutorialCounter == 1) {
                if (one == 7 && two == 6 || one == 6 && two == 7) {
                    matchState = MatchState.SWAP;
                }
            }
        } else if (Urbies.level == -3) {
            if (tutorialCounter == 0) {
                if (one == 7 && two == 12 || one == 12 && two == 7) {
                    matchState = MatchState.SWAP;
                }
            } else if (tutorialCounter == 1) {
                if (one == 12 && two == 11 || one == 11 && two == 12) {
                    matchState = MatchState.SWAP;
                }
            }
        } else if (Urbies.level == -4) {
            if (tutorialCounter == 0) {
                if (one == 15 && two == 16 || one == 16 && two == 15) {
                    matchState = MatchState.SWAP;
                }
            } else if (tutorialCounter == 1) {
                if (one == 16 && two == 11 || one == 11 && two == 16) {
                    matchState = MatchState.SWAP;
                }
            }
        }
    }

}
