package com.example.bruce.snake_startercode;

public class SnakeSegment {

    private BodyParts mBodyParts;
    private int mDegrees, mXLoc, mYLoc;

    public SnakeSegment(BodyParts bodyParts, int degrees, int xLoc, int yLoc) {
        mBodyParts = bodyParts;
        mDegrees = degrees;
        mXLoc = xLoc;
        mYLoc = yLoc;
    }

    public enum BodyParts{
        HEAD, BODY, TAIL
    }

    /************************************************
     * Getters and Setters
     ***********************************************/

    public BodyParts getBodyParts() {
        return mBodyParts;
    }

    public void setDegrees(int degrees){
        mDegrees = degrees;
    }

    public int getDegrees() {
        return mDegrees;
    }

    public void setXLoc(int xLoc){
        mXLoc = xLoc;
    }

    public int getXLoc() {
        return mXLoc;
    }

    public void setYLoc(int yLoc){
        mYLoc = yLoc;
    }

    public int getYLoc() {
        return mYLoc;
    }
}
