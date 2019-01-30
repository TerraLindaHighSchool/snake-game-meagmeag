package com.example.bruce.snake_startercode;

public class PivotPoint {
    private int mXLoc, mYLoc, mDegrees;

    public PivotPoint(int xLoc, int yLoc, int degrees){
        mXLoc = xLoc;
        mYLoc = yLoc;
        mDegrees = degrees;
    }

    /**************************************
     * Getters
     **********************************/

    public int getXLoc(){
        return mXLoc;
    }

    public int getYLoc(){
        return  mYLoc;
    }

    public int getDegrees(){
        return mDegrees;
    }
}
