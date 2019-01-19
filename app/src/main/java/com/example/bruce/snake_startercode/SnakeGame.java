package com.example.bruce.snake_startercode;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SnakeGame {
  private int mSpriteDim, mBOARD_WIDTH, mBOARD_HEIGHT, mScore,
          mLevel, mCountdown, mMillisDelay;
  int[] mAppleCoord;
  ArrayList<SnakeSegment> mSnake = new ArrayList();
  boolean mGameOver;

  
  public SnakeGame(int beginningDirection, int beginningSpriteDim, int beginningX, int beginningY, int width, int height){
    mSpriteDim = beginningSpriteDim;
    mBOARD_WIDTH = width;
    mBOARD_HEIGHT = height;
    mScore = 0;
    mLevel = 1;
    mCountdown = 12;
    mMillisDelay = 400;
    mAppleCoord = new int[2];
    mSnake.add(new SnakeSegment(SnakeSegment.BodyParts.HEAD, beginningDirection, beginningX, beginningY));
    mSnake.add(new SnakeSegment(SnakeSegment.BodyParts.BODY, beginningDirection, beginningX - 1, beginningY));
    mSnake.add(new SnakeSegment(SnakeSegment.BodyParts.TAIL, beginningDirection, beginningX - 2, beginningY));
    mGameOver = false;
  }
  
  protected void touched(float xTouched, float yTouched){
  
  }
    
  protected void eatApple(){
  
  }
    
  protected boolean play(){
        return false;
  }

  /*******************************************
   * Getters
   ***************************************/

  public int getSpriteDim(){
    return mSpriteDim;
  }

  public int getMillisDelay(){
    return mMillisDelay;
  }

  public ArrayList getSnake(){
    return mSnake;
  }

  public int[] getAppleCoord(){
    return mAppleCoord;
  }

  public int getScore(){
    return mScore;
  }

  public int getLevel(){
    return mLevel;
  }

  public int getCountdown(){
    return mCountdown;
  }

  public boolean getGameOver(){
    return mGameOver;
  }
}
