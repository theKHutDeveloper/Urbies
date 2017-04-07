package com.development.knowledgehut.urbies.Objects;


import android.graphics.Bitmap;
import android.graphics.Point;

import com.development.knowledgehut.urbies.Screens.Coordinates;

import java.util.ArrayList;

import static com.development.knowledgehut.urbies.Implementations.AndroidGame.GAME_SCALE_X;
import static com.development.knowledgehut.urbies.Implementations.AndroidGame.OFFSET_Y;
import static com.development.knowledgehut.urbies.Implementations.AndroidGame.USABLE_AREA_X;
import static com.development.knowledgehut.urbies.Implementations.AndroidGame.USABLE_AREA_Y;

public class BaseTiles {
    private final ArrayList<Integer> tileMap;
    private final int tileMapWidth, tileMapHeight;
    private ArrayList<Point> tileLocations = new ArrayList<>();
    private Point startingPosition;
    private int individualTileWidth, individualTileHeight;
    private ArrayList<Bitmap>tileMapImages = new ArrayList<>();
    private ArrayList<Integer>validTileLocations = new ArrayList<>();
    private Bitmap tileImage;
    private int tileGap = 2;


    public BaseTiles(ArrayList<Integer>tileMap, int tileMapWidth, int tileMapHeight, Bitmap image){
        this.tileMap = tileMap;
        this.tileMapWidth = tileMapWidth;
        this.tileMapHeight = tileMapHeight;

        this.individualTileHeight = image.getHeight();
        this.individualTileWidth = image.getWidth();
        this.tileImage = image;
        this.startingPosition = new Point(startingXTilePosition(), startingYTilePosition());

        setTileMap();
        setTileMapImages();
    }

    public int getIndividualTileWidth(){
        return individualTileWidth;
    }

    @SuppressWarnings("unused")
    public int getIndividualTileHeight(){
        return individualTileHeight;
    }

    @SuppressWarnings("unused")
    private void createTileMap(){
        Point position = new Point(startingPosition);


        for(int i = 0; i < tileMap.size(); i++){

            if(i == 0){
                position.x = startingPosition.x;
                position.y = startingPosition.y;
            }
            else if(i % tileMapWidth == 0){
                position.x = startingPosition.x;
                position.y = position.y + (individualTileHeight + tileGap);
            } else {
                position.x = position.x + (individualTileWidth + tileGap);
            }

            if(tileMap.get(i) == 1) tileLocations.add(new Point(position));
        }
    }

    //gives all the elements of the tileMap a stored position
    private void setTileMap(){
        Point position = new Point(startingPosition);

        for(int i = 0; i < tileMap.size(); i++) {
            if(i == 0){
                position.x = startingPosition.x;
                position.y = startingPosition.y;
            }
            else if(i % tileMapWidth == 0){
                position.x = startingPosition.x;
                position.y = position.y + (individualTileHeight + tileGap);
            } else {
                position.x = position.x + (individualTileWidth + tileGap);
            }

            tileLocations.add(new Point(position));
        }

    }

    //set the number of tile bitmaps and their location in the tileMap
    private void setTileMapImages(){
        for(int i = 0; i < tileMap.size(); i++){
            if(tileMap.get(i) == 1){
                tileMapImages.add(tileImage);
                validTileLocations.add(i);
            }
        }
    }

    //need to start removing GAME_SCALE_X in favour of coordinates
    private int startingXTilePosition(){
        int startX, sum;

        sum = (Math.round((tileMapWidth * individualTileWidth) + (tileGap * (tileMapWidth - 1)) * GAME_SCALE_X));
        startX = (USABLE_AREA_X - sum) / 2;

        /*sum = (Math.round((tileMapWidth * individualTileWidth) + (tileGap * (tileMapWidth - 1))));
        startX = (USABLE_AREA_X - sum) / 2;
*/
        return startX;
    }

    private int startingYTilePosition(){
        int startY, sum;

        sum =(Math.round ((tileMapHeight * individualTileHeight) + (tileGap * (tileMapHeight - 1)) * GAME_SCALE_X));
        startY = ((USABLE_AREA_Y  - sum) / 2) + OFFSET_Y;

        /*sum =(Math.round ((tileMapHeight * individualTileHeight) + (tileGap * (tileMapHeight - 1))));
        startY = ((USABLE_AREA_Y  - sum) / 2) + OFFSET_Y;*/

        System.out.println("startY = " +startY);
        return startY;
    }

    public ArrayList<Point> getTileLocations(){
        return tileLocations;
    }

    public ArrayList<Integer>getValidTileLocations(){ return validTileLocations;}

    @SuppressWarnings("unused")
    public ArrayList<Bitmap> getTileMapImages(){
        return tileMapImages;
    }
}
