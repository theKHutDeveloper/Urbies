package com.development.knowledgehut.urbies.Objects;


import android.graphics.Point;

public class DataStore {
    private int element;
    private Point position;

    public int getElement(){
        return element;
    }

    public Point getPosition(){
        return position;
    }

    public void setElement(int element){
        this.element = element;
    }

    public void setPosition(Point position){
        this.position = position;
    }
}
