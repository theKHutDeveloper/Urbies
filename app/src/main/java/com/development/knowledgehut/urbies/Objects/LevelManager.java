package com.development.knowledgehut.urbies.Objects;


import com.development.knowledgehut.urbies.Screens.Urbies;

import java.util.ArrayList;
import java.util.Collections;

public class LevelManager {
    private int level;
    private int urbsInLevel;

    private int pacCounter, babyCounter, nerdCounter;
    private int ladyCounter, punkCounter, rockerCounter;
    private int girlNerdCounter, pigTailsCounter, score;
    private int glassCounter, woodenCounter;
    private int cementCounter;//, singleCounter, doubleCounter;
    //private int cagedCounter, poisonedCounter;


    private int minScore;// pac, baby, nerd, lady, punk, rocker;
    private int maxTimer, moves, glass;//nerd_girl, pig_tails,
    private int wooden, cement;//, single_tile, double_tile;
    //private int cage, poison;
    private long timer;

    public class MapData{
        ArrayList<Integer> mapLevel = new ArrayList<>();
        int width;
        int height;

        /****************************************************************************
         MapData Constructor
         ****************************************************************************/
        MapData(ArrayList<Integer>mapLevel, int width, int height){
            this.mapLevel = mapLevel;
            this.width = width;
            this.height = height;
        }

        public ArrayList<Integer> getMapLevel(){
            return mapLevel;
        }

        public int getWidth(){
            return width;
        }

        public  int getHeight(){
            return height;
        }
    }

    /****************************************************************************
     Constructor
     ****************************************************************************/
    public LevelManager(int level_number){
        this.level = level_number;
        this.urbsInLevel = setUrbsInLevel();
        score = 0;
        pacCounter = babyCounter = 0;
        nerdCounter = ladyCounter = 0;
        punkCounter = rockerCounter = 0;
        girlNerdCounter = pigTailsCounter = 0;
        glassCounter = woodenCounter = cementCounter = 0;//singleCounter = doubleCounter = 0;

        minScore = 0;//pac =baby = nerd = lady = punk = rocker = 0;
        maxTimer = 0;//nerd_girl = pig_tails = moves = glass = 0;
        wooden = cement = 0;//single_tile = double_tile = 0;
        //cage =  poison = 0;

        levelCreator();
    }

    /****************************************************************************
     Start timer for timed levels
     ****************************************************************************/
    public void startTimer(){
        switch(level){
            case 50:
            case 80:
                timer = System.currentTimeMillis();
                break;
        }
    }

    /****************************************************************************
     Return time remaining
     ****************************************************************************/
    public int getTimeRemaining(){
        return (int)(System.currentTimeMillis() - (timer + maxTimer));
    }


    /****************************************************************************
     Set the number of urbs used in the level
     ****************************************************************************/
    private int setUrbsInLevel(){
        int total = 0;

        switch(level){
            case -1:
            case -2:
            case -3:
            case -4:
            case 1:
            case 3:
            case 4:
            case 7:
            case 8:
            case 9:
            case 10: total = 5; break; //5

            case 2:
            case 5:
            case 6:
            case 11:
                total = 6; break;
            case 100: total = 7; break;
        }
        return total;
    }

    /****************************************************************************
     Return the urb types that must be included in the level
     ****************************************************************************/
    public ArrayList<Urbies.UrbieType>requiredUrbs(){
        ArrayList<Urbies.UrbieType>required = new ArrayList<>();

        switch(level){
            case 30:
                required.add(Urbies.UrbieType.PAC); break;
            case 40:
                required.add(Urbies.UrbieType.NERD); break;
            case 60:
                required.add(Urbies.UrbieType.GIRL_NERD);
                required.add(Urbies.UrbieType.BABY);
            case 70:
                required.add(Urbies.UrbieType.PIGTAILS);
                required.add(Urbies.UrbieType.PUNK); break;
            case 80:
                required.add(Urbies.UrbieType.LADY);
                required.add(Urbies.UrbieType.ROCKER); break;
            case 90:
                required.add(Urbies.UrbieType.NERD);
                required.add(Urbies.UrbieType.GIRL_NERD); break;
        }
        return required;
    }

    /****************************************************************************
     Return the number of urbs used in the level
     ****************************************************************************/
    public int getUrbsInLevel(){
        return this.urbsInLevel;
    }

    /****************************************************************************
     Set goals for each level
     ****************************************************************************/
    private void levelCreator(){
        switch(level){
            case 1: moves = 5; minScore = 1000;break;
            case 2: moves = 8; minScore = 2000; break;
            case 3: moves = 15; minScore = 3500; break;
            case 4: moves = 20; minScore = 4500; break;
            case 5: moves = 100; minScore = 6000; break;
            case 6: moves = 100; minScore = 7000; break;
            case 7: moves = 100; glass = 5; break;
            case 8: moves = 100; glass = 6; break;
            case 9: moves = 100; glass = 8; break;
            case 10: moves = 100; glass = 13; break;
            case 11: moves = 100; cement = 5; break;
        }
    }

    /****************************************************************************
     Return whether the level is timed
     ****************************************************************************/
    public boolean isTimedLevel(){
        boolean result = false;

        switch(level){
            case 50:
            case 80: result = true;
        }
        return result;
    }

    /****************************************************************************
     Return whether the level is a moved based level
     ****************************************************************************/
    public boolean isMoveLevel(){
        boolean result = false;

        switch(level){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
            case 7:
            case 8:
            case 9:
            case 10:
            case 11:
                result = true; break;
        }
        return result;
    }

    /****************************************************************************
     Does the level contain glass tiles
     ****************************************************************************/
    public boolean isGlass(){
        return glass > 0;
    }


    /****************************************************************************
     Does this level contain wood tiles
     ****************************************************************************/
    public boolean isWood() { return wooden > 0; }


    /****************************************************************************
     Does this level contain cement tiles
     ****************************************************************************/
    public boolean isCement() { return cement >0; }


    /****************************************************************************
     Return the glass target count the player must aim for to pass the level
     ****************************************************************************/
    @SuppressWarnings("unused")
    public int glassTarget(){
        return glass;
    }


    /****************************************************************************
     Return the wood target count the player must aim for to pass the level
     ****************************************************************************/
    @SuppressWarnings("unused")
    public int woodTarget() { return wooden; }


    /****************************************************************************
     Return the cement target count the player must aim for to pass the level
     ****************************************************************************/
    @SuppressWarnings("unused")
    public int cementTarget() { return cement; }


    /****************************************************************************
     Sets the locations of where the obstacle tiles will be placed
     ****************************************************************************/
    public ArrayList<Integer>obstacleTileLocation(){
        ArrayList<Integer>locations = new ArrayList<>();

        switch(level){
            case 7:
                locations.add(7);
                locations.add(11);
                locations.add(12);
                locations.add(13);
                locations.add(17);
                break;
            case 8:
                locations.add(2);
                locations.add(7);
                locations.add(12);
                locations.add(17);
                locations.add(22);
                locations.add(27);
                break;
            case 9:
                locations.add(12);
                locations.add(14);
                locations.add(15);
                locations.add(17);
                locations.add(18);
                locations.add(20);
                locations.add(21);
                locations.add(23);
                break;
            case 10:
                locations.add(0);
                locations.add(1);
                locations.add(2);
                locations.add(3);
                locations.add(4);
                locations.add(5);
                locations.add(9);
                locations.add(10);
                locations.add(14);
                locations.add(15);
                locations.add(19);
                locations.add(20);
                locations.add(24);
                break;
            case 11:
                locations.add(18);
                locations.add(19);
                locations.add(20);
                locations.add(21);
                locations.add(22);
                locations.add(23);
                break;
            case 13:
                locations.add(20);
                locations.add(21);
                locations.add(22);
                locations.add(23);
                locations.add(24);
                break;
        }
        return locations;
    }

    /****************************************************************************
     Return whether player failed the level
     ****************************************************************************/
    public boolean failing(){
        boolean failed = false;

        switch(level){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:if(moves == 0 && score < minScore) failed = true; break;
            case 7:
            case 8:
            case 9:
            case 10:
                if(moves == 0 && glassCounter < glass) failed = true; break;
            case 11:
                if(moves == 0 && cementCounter < cement) failed = true; break;
        }

        return failed;
    }

    /****************************************************************************
     Return whether level has finished successfully
     ****************************************************************************/
    public boolean success(){
        boolean successful = false;

        switch(level){
            case 1:
            case 2:
            case 3:
            case 4:
            case 5:
            case 6:
                if(moves >=0 && score >= minScore)  successful = true; break;
            case 7:
            case 8:
            case 9:
            case 10: if(moves >= 0 && glassCounter >= glass) successful = true; break;
            case 11: if(moves >= 0 && cementCounter >= cement) successful = true; break;
        }
        return successful;
    }

    /****************************************************************************
     Add to score based upon the size of match
     ****************************************************************************/
    public void addMatchesToScore(Urbies.MatchShape shape, int matchSize){
        int value = 50 * matchSize;
        int bonus = matchSize * 30;

        switch(shape){
            case LINE:  score = score + value; break;
            case LINE_OF_FOUR_VERTICAL:
            case LINE_OF_FOUR_HORIZONTAL:
            case LINE_OF_FIVE_OR_MORE:
            case L_OR_T_SHAPE: score = score + value + bonus; break;
        }
    }

    /****************************************************************************
     Value to add to score if all urbs are removed from tile map
     ****************************************************************************/
    public void addBigPopToScore(int matchSize){
        int value = 50 * matchSize;
        int bonus = 30 * matchSize;
        score = value + bonus + 5000;
    }

    /****************************************************************************
     Value to add to score if a special gift has been used
     ****************************************************************************/
    public void addSpecialUrbBonusToScore(Urbies.UrbieType type, int matchSize){
        score = score + (matchSize * 50);

        switch(type){
            case GOBSTOPPER:
            case STRIPE_HORIZONTAL:
            case STRIPE_VERTICAL:
            case WHITE_CHOCOLATE:
                score = score + 1000;
                break;
        }
    }

    /****************************************************************************
     Return game score
     ****************************************************************************/
    public int getScore(){
        return score;
    }


    /****************************************************************************
     Return moves available
     ****************************************************************************/
    public int getMoves(){ return moves;}


    /****************************************************************************
     Tally scores for each urb type
     ****************************************************************************/
    public void addUrbCounter(Urbies.UrbieType type, int size){
        switch(type){
            case PAC:
                pacCounter += size;
                break;
            case PIGTAILS:
                pigTailsCounter += size;
                break;
            case PUNK:
                punkCounter += size;
                break;
            case NERD:
                nerdCounter += size;
                break;
            case GIRL_NERD:
                girlNerdCounter += size;
                break;
            case LADY:
                ladyCounter += size;
                break;
            case BABY:
                babyCounter += size;
                break;
            case ROCKER:
                rockerCounter += size;
                break;
        }
    }

    /****************************************************************************
     Increment glass counter
     ****************************************************************************/
    public void addToGlassCounter() {
        switch(level){
            case 7:
            case 8:
            case 9:
            case 10:
                glassCounter++;
                break;
        }
    }

    /****************************************************************************
     Increment wood counter
     ****************************************************************************/
    public void addToWoodenCounter(){
        switch(level){
            case 21:
                woodenCounter++; break;
        }
    }

    /****************************************************************************
     Increment cement counter
     ****************************************************************************/
    public void addToCementCounter(){
        switch(level){
            case 11:
            case 30: cementCounter++; break;
        }
    }

    /****************************************************************************
     Return glass counter
     ****************************************************************************/
    @SuppressWarnings("unused")
    public int getGlassCounter(){
        return glassCounter;
    }


    /****************************************************************************
     Return wood counter
     ****************************************************************************/
    @SuppressWarnings("unused")
    public int getWoodenCounter() { return woodenCounter; }


    /****************************************************************************
     Return cement counter
     ****************************************************************************/
    public int getCementCounter() { return cementCounter; }


    /****************************************************************************
     Deduct move counter
     ****************************************************************************/
    public void deductMoveCounter(){
        if(level < 12){
            moves--;
        }
    }

    /****************************************************************************
     Return urb counter
     ****************************************************************************/
    public int getUrbCounter(Urbies.UrbieType type){
        int counter = 0;

        switch(type){
            case PAC:
                counter = pacCounter;
                break;
            case PIGTAILS:
                counter =  pigTailsCounter;
                break;
            case PUNK:
                counter =  punkCounter;
                break;
            case NERD:
                counter =  nerdCounter;
                break;
            case GIRL_NERD:
                counter =  girlNerdCounter;
                break;
            case LADY:
                counter =  ladyCounter;
                break;
            case BABY:
                counter =  babyCounter;
                break;
            case ROCKER:
                counter =  rockerCounter;
                break;
        }
        return counter;
    }

    /****************************************************************************
     Set the level map, height and width
     ****************************************************************************/
    public MapData getLevelTileMap(){
        MapData mapData;
        ArrayList<Integer>mapLevel = new ArrayList<>();
        int mapWidth = 0;
        int mapHeight = 0;

        switch(level){
            case -1:
            case 1:
                Collections.addAll(mapLevel,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,1,1,1,1);
                mapWidth = 5;
                mapHeight = 4;
                break;
            case -2:
            case 2:
                Collections.addAll(mapLevel,
                        0,1,1,1,0,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        0,1,1,1,0);
                mapWidth = 5;
                mapHeight = 5;
                break;
            case -3:
                Collections.addAll(mapLevel,
                        1,1,0,1,1,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,1,0,1,1);
                mapWidth = 5;
                mapHeight = 4;
                break;
            case -4:
                Collections.addAll(mapLevel,
                        1,1,1,1,1,
                        1,1,0,1,1,
                        1,1,0,1,1,
                        1,1,1,1,1);
                mapWidth = 5;
                mapHeight = 4;
                break;
            case 3:
                Collections.addAll(mapLevel,
                        1,1,1,1,1,1,
                        1,1,0,0,1,1,
                        1,1,1,1,1,1,
                        1,1,0,0,1,1,
                        1,1,1,1,1,1);
                mapWidth = 6;
                mapHeight = 5;
                break;
            case 4:
                Collections.addAll(mapLevel,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1);
                mapWidth = 6;
                mapHeight = 5;
                break;
            case 5:
                Collections.addAll(mapLevel,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1,
                        0,1,1,1,1,0,
                        0,1,1,1,1,0);
                mapWidth = 6;
                mapHeight = 5;
                break;
            case 6:
                Collections.addAll(mapLevel,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,0,0,0,1,
                        1,1,1,1,1,
                        1,1,1,1,1);
                mapWidth = 5;
                mapHeight = 5;
                break;
            case 7:
                Collections.addAll(mapLevel,
                        0,1,1,1,0,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        0,1,1,1,0);
                mapWidth = 5;
                mapHeight = 5;
                break;
            case 8:
                Collections.addAll(mapLevel,
                        1,0,1,0,1,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,0,1,0,1);
                mapWidth = 5;
                mapHeight = 6;
                break;
            case 9:
                Collections.addAll(mapLevel,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1,
                        1,0,1,1,0,1,
                        1,0,1,1,0,1,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1);
                mapWidth = 6;
                mapHeight = 6;
                break;
            case 10:
                Collections.addAll(mapLevel,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,1,1,1,1,
                        1,1,1,1,1);
                mapWidth = 5;
                mapHeight = 5;
                break;
            case 11:
                Collections.addAll(mapLevel,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1,
                        1,1,1,1,1,1);
                mapWidth = 6;
                mapHeight = 6;
                break;

        }

        mapData = new MapData(mapLevel, mapWidth, mapHeight);
        return mapData;
    }
}
