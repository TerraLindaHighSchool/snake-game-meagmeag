package com.example.bruce.snake_startercode;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SnakeGame {
  private int mSpriteDim, mBOARD_WIDTH, mBOARD_HEIGHT, mScore,
          mLevel, mCountdown, mMillisDelay, mXMax, mYMax;
  int[] mAppleCoord;
  ArrayList<SnakeSegment> mSnake = new ArrayList();
  boolean mGameOver = false;
  ArrayList<PivotPoint> mPivotPoints;
  
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
    mPivotPoints = new ArrayList();
    setAppleCoord();
  }
  
  protected void touched(float xTouched, float yTouched){
     SnakeSegment seg = mSnake.get(0);
     int x = seg.getXLoc();
     int y = seg.getYLoc();
     int deg = seg.getDegrees();

     if(deg == 180 || deg == 0) {
         if (yTouched > y * mSpriteDim)
             mPivotPoints.add(new PivotPoint(x , y, 90));
         else
             mPivotPoints.add(new PivotPoint(x , y, 270));
     }
     else if(deg == 90 || deg == 270){
          if (xTouched > x * mSpriteDim)
              mPivotPoints.add(new PivotPoint(x, y, 0));
          else
              mPivotPoints.add(new PivotPoint(x, y, 180));
      }
  }
    
  protected void eatApple(){
      SnakeSegment seg = mSnake.get(0);
      if(seg.getXLoc() == mAppleCoord[0] / mSpriteDim && seg.getYLoc() == mAppleCoord[1] / mSpriteDim) {
          setAppleCoord();
          growSnake();
      }
  }

  private void growSnake(){
        SnakeSegment seg = mSnake.get(mSnake.size() - 1);
        mSnake.add(mSnake.size() - 2, new SnakeSegment(SnakeSegment.BodyParts.BODY, seg.getDegrees(), seg.getXLoc(), seg.getYLoc()));
        switch(seg.getDegrees()){
            case 0:
                seg.setXLoc(seg.getXLoc() - 1);
                break;
            case 180:
                seg.setXLoc(seg.getXLoc() + 1);
                break;
            case 90:
                seg.setYLoc(seg.getYLoc() - 1);
                break;
            case 270:
                seg.setYLoc(seg.getYLoc() + 1);
                break;
        }
    }

  protected boolean play(){
      eatApple();
      SnakeSegment seg;
      int xLoc, yLoc;
      for(int i = 0; i < mSnake.size(); i++) {
          seg = mSnake.get(i);
          xLoc = seg.getXLoc();
          yLoc = seg.getYLoc();

          for(int y = 0; y < mPivotPoints.size(); y++) {
              PivotPoint piv = mPivotPoints.get(y);
              if (xLoc == piv.getXLoc() && yLoc == piv.getYLoc()) {
                  seg.setDegrees(piv.getDegrees());
                  if (seg.getBodyParts() == SnakeSegment.BodyParts.TAIL)
                      mPivotPoints.remove(y);
                  break;
              }
          }

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


          if(xLoc >= mXMax || yLoc >= mYMax || xLoc <= 0 || yLoc <= 0)
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
      SnakeSegment seg = mSnake.get(0);
      do{
          mAppleCoord[0] = (int) ((mXMax - 1) * Math.random() + 1) * mSpriteDim;
          mAppleCoord[1] = (int) ((mYMax - 1) * Math.random() + 1) * mSpriteDim;
      } while(mAppleCoord[0] == seg.getXLoc() && mAppleCoord[1] == seg.getYLoc());
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
