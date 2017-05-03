package com.development.knowledgehut.urbies.Behaviours;


import android.graphics.Point;

import java.util.ArrayList;

public class PositionList {
    private int location_id;
    private ArrayList<Point>position;

    public PositionList(int location_id){
        this.location_id = location_id;
        position = new ArrayList<>();
    }

    public int getLocation_id(){
        return location_id;
    }

    public void setPosition(Point p){
        position.add(p);
    }

    public void setPositionAt(Point p, int index){
        position.add(index, p);
    }
    public ArrayList<Point> getPosition(){
        return position;
    }

    public Point getPositionAt(int index){
        if(index < position.size()){
            return position.get(index);
        }
        return new Point(-1,-1);
    }
}
