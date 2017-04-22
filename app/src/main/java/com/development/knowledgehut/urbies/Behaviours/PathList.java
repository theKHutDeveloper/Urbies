package com.development.knowledgehut.urbies.Behaviours;


import android.graphics.Point;

import java.util.ArrayList;

public class PathList {
    private int location_id;
    private ArrayList<Point>position;

    public PathList(int location_id){
        this.location_id = location_id;
        position = new ArrayList<>();
    }

    public int getLocation_id(){
        return location_id;
    }

    public void setPosition(Point p){
        position.add(p);
    }

    public ArrayList<Point> getPosition(){
        return position;
    }
}
