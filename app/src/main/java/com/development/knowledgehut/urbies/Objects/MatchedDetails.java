package com.development.knowledgehut.urbies.Objects;


import com.development.knowledgehut.urbies.Screens.Urbies;

import java.util.ArrayList;

public class MatchedDetails {
    private ArrayList<Integer> returnedMatches;
    private int intersecting_element;
    private Urbies.MatchShape matchShape;
    private Urbies.UrbieType urbieType;


    public void setReturnedMatches(ArrayList<Integer>matches){
        this.returnedMatches = matches;
    }

    public ArrayList<Integer>getReturnedMatches(){
        return this.returnedMatches;
    }

    public void setIntersecting_element(int index){
        this.intersecting_element = index;
    }

    public int getIntersecting_element(){
        return this.intersecting_element;
    }

    public void setMatchShape(Urbies.MatchShape shape){
        this.matchShape = shape;
    }

    public Urbies.MatchShape getMatchShape(){
        return this.matchShape;
    }

    public void setSpecialType(){
        switch(matchShape){
            case LINE: urbieType = Urbies.UrbieType.NONE; break;
            case LINE_OF_FOUR_HORIZONTAL: urbieType = Urbies.UrbieType.STRIPE_HORIZONTAL; break;
            case LINE_OF_FOUR_VERTICAL: urbieType = Urbies.UrbieType.STRIPE_VERTICAL; break;
            case LINE_OF_FIVE_OR_MORE: urbieType = Urbies.UrbieType.GOBSTOPPER; break;
            case L_OR_T_SHAPE: urbieType = Urbies.UrbieType.WHITE_CHOCOLATE; break;
        }
    }

    public Urbies.UrbieType getUrbieType(){
        return this.urbieType;
    }
}
