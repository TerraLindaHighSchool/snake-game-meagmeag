package com.example.bruce.snake_startercode;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SnakeGame {
  private int mSpriteDim, mBOARD_WIDTH, mBOARD_HEIGHT, mScore,
          mLevel, mCountdown, mMillisDelay, mXMax, mYMax;
  int[] mAppleCoord;
  ArrayList<SnakeSegment> mSnake = new ArrayList();
  boolean mGameOver = false;
  
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
    mXMax = mBOARD_WIDTH / mSpriteDim;
    mYMax = mBOARD_HEIGHT / mSpriteDim;
    setAppleCoord();
  }
  
  protected void touched(float xTouched, float yTouched){
  
  }
    
  protected void eatApple(){
  
  }
    
  protected boolean play(){
      SnakeSegment seg;
      int xLoc, yLoc;
      for(int i = 0; i < mSnake.size(); i++) {
          seg = mSnake.get(i);
          xLoc = seg.getXLoc();
          yLoc = seg.getYLoc();
          switch (seg.getDegrees()) {
              case 270:
                  seg.setYLoc(--yLoc);
                  break;
              case 90:
                  seg.setYLoc(++yLoc);
                  break;
              case 180:
                  seg.setXLoc(--xLoc);
                  break;
              case 0:
                  seg.setXLoc(++xLoc);
                  break;
          }

          if(mSnake.get(0).getXLoc() >= mXMax || mSnake.get(0).getYLoc() >= mYMax)
              mGameOver = true;
      }
      return mGameOver;
  }

  /*******************************************
   * Getters
   ***************************************/
  public int[] getAppleCoord(){
        return mAppleCoord;
    }

  private void setAppleCoord(){
      mAppleCoord[0] = (int) ((mXMax - 1) * Math.random() + 1) * mSpriteDim;
      mAppleCoord[1] = (int) ((mYMax - 1) * Math.random() + 1) * mSpriteDim;
  }


  public int getSpriteDim(){
    return mSpriteDim;
  }

  public int getMillisDelay(){
    return mMillisDelay;
  }

  public ArrayList getSnake(){
    return mSnake;
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
