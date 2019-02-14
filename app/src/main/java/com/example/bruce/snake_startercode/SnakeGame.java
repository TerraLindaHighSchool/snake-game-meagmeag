package com.example.bruce.snake_startercode;

import java.lang.reflect.Array;
import java.util.ArrayList;

public class SnakeGame {
  private int mSpriteDim, mBOARD_WIDTH, mBOARD_HEIGHT, mScore,
          mLevel, mCountdown, mMillisDelay, mXMax, mYMax, yellowAppleCount, greenAppleCount;
  private long mStartTime, mEndTime;
  int[] mAppleCoord, mYellowAppleCoord, mGreenAppleCoord;
  ArrayList<SnakeSegment> mSnake = new ArrayList();
  boolean mGameOver, reset, win, yellowAppleBoo, greenAppleBoo;
  ArrayList<PivotPoint> mPivotPoints;
  int[] snakeParams = new int[3];
  
  public SnakeGame(int beginningDirection, int beginningSpriteDim, int beginningX, int beginningY, int width, int height){
    mSpriteDim = beginningSpriteDim;
    mBOARD_WIDTH = width;
    mBOARD_HEIGHT = height;
    mScore = 0;
    mLevel = 2;
    mCountdown = 5;
    mMillisDelay = 400;
    mAppleCoord = new int[2];

    mSnake.add(new SnakeSegment(SnakeSegment.BodyParts.HEAD, beginningDirection, beginningX, beginningY));
    mSnake.add(new SnakeSegment(SnakeSegment.BodyParts.BODY, beginningDirection, beginningX - 1, beginningY));
    mSnake.add(new SnakeSegment(SnakeSegment.BodyParts.TAIL, beginningDirection, beginningX - 2, beginningY));

    mXMax = mBOARD_WIDTH / mSpriteDim;
    mYMax = mBOARD_HEIGHT / mSpriteDim;
    mPivotPoints = new ArrayList();

    mAppleCoord[0] = (beginningX + 5) * mSpriteDim;
    mAppleCoord[1] = beginningY * mSpriteDim;

    mYellowAppleCoord = new int[2];
    mGreenAppleCoord = new int[2];

    snakeParams[0] = beginningDirection;
    snakeParams[1] = beginningX;
    snakeParams[2] = beginningY;
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
          if((!yellowAppleBoo || yellowAppleCount > 2) && mLevel > 3 && !(mLevel == 4 && mCountdown == 1)) {
              setYellowAppleCoord();
          }
          if((!greenAppleBoo || greenAppleCount > 2) && mLevel > 4) {
              setGreenAppleCoord();
          }
          growSnake();
          mEndTime = System.currentTimeMillis();
          mCountdown--;
          yellowAppleCount ++;
          greenAppleCount++;
          score("red");

      }

      else if(seg.getXLoc() == mYellowAppleCoord[0] / mSpriteDim && seg.getYLoc() == mYellowAppleCoord[1] / mSpriteDim) {
          setYellowAppleCoord();
          setGreenAppleCoord();
          growSnake();
          mEndTime = System.currentTimeMillis();
          greenAppleCount++;
          mCountdown--;
          score("yellow");
      }

      else if(seg.getXLoc() == mGreenAppleCoord[0] / mSpriteDim && seg.getYLoc() == mGreenAppleCoord[1] / mSpriteDim) {
          setGreenAppleCoord();
          setYellowAppleCoord();
          growSnake();
          mEndTime = System.currentTimeMillis();
          yellowAppleCount++;
          mCountdown--;
          score("green");
      }

      reset();
  }

    private void score(String apple){
      int time = (int) ((mEndTime - mStartTime) / 100) * mLevel;
      switch(apple){
          case "red":
              mScore += 5 * mLevel + time;
            break;
          case "yellow":
            mScore += 10 * mLevel + time;
            break;
          case "green":
            mScore -= 5 * mLevel;
            break;
        }
    }

  private void reset(){
      SnakeSegment seg;
      if(mCountdown == 0 && mLevel < 10) {
          mLevel++;
          mCountdown = 5;
          if(mLevel > 4)
          mMillisDelay -= 25;
          if(mLevel > 4)
          mSpriteDim -= 5;
          mXMax = mBOARD_WIDTH / mSpriteDim;
          mYMax = mBOARD_HEIGHT / mSpriteDim;
          while(mSnake.size() > 3 ){
              mSnake.remove(1);
          }
          for (int i = 0; i < mSnake.size(); i++) {
              seg = mSnake.get(i);
              seg.setDegrees(snakeParams[0]);
              seg.setXLoc(snakeParams[1] - i);
              seg.setYLoc(snakeParams[2]);
          }
          mAppleCoord[0] = (snakeParams[1] + 5) * mSpriteDim;
          mAppleCoord[1] = snakeParams[2] * mSpriteDim;
          reset = true;

          if(mLevel == 3){
              mYellowAppleCoord[0] = (snakeParams[1] + 7) * mSpriteDim;
              mYellowAppleCoord[1] = snakeParams[2] * mSpriteDim;
              yellowAppleBoo = true;
          }

          else if (mLevel == 4){
              mGreenAppleCoord[0] = (snakeParams[1] + 7) * mSpriteDim;
              mGreenAppleCoord[1] = snakeParams[2] * mSpriteDim;
              greenAppleBoo = true;
          }

          else {
              mYellowAppleCoord = new int[2];
              mGreenAppleCoord = new int[2];
          }

          mPivotPoints = new ArrayList<>();

      }
      if(mLevel == 10)
          win = true;
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
      reset = false;
      win = false;
      eatApple();
      mStartTime = System.currentTimeMillis();
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

          for(int z = 1; z < mSnake.size() - 1; z++) {
            SnakeSegment seg2 = mSnake.get(z);
            if(mSnake.get(0).getXLoc() == seg2.getXLoc() && mSnake.get(0).getYLoc() == seg2.getYLoc()) {
                mGameOver = true;
                break;
            }
          }

          if(xLoc >= mXMax || yLoc >= mYMax || xLoc <= 0 || yLoc <= 0)
              mGameOver = true;
      }
      return mGameOver;

  }

  /*******************************************
   * Getters
   ***************************************/
  public boolean getReset(){
      return reset;
  }

  public boolean getWin(){
      return win;
  }

  public int[] getAppleCoord(){
        return mAppleCoord;
    }

  private void setAppleCoord(){
      SnakeSegment seg;
      mAppleCoord[0] = (int) ((mXMax - 1) * Math.random() + 1) * mSpriteDim;
      mAppleCoord[1] = (int) ((mYMax - 1) * Math.random() + 1) * mSpriteDim;
      for(int i = 0; i < mSnake.size(); i ++){
          seg = mSnake.get(i);
          if(mAppleCoord[0] == seg.getXLoc() && mAppleCoord[1] == seg.getYLoc()) {
              setAppleCoord();
              break;
          }
      }

  }

  private void setYellowAppleCoord(){
    //1 in 5 chance to get a yellow apple
    int chance = (int) (5 * Math.random());
    if(chance == 0) {
        SnakeSegment seg;
        mYellowAppleCoord[0] = (int) ((mXMax - 1) * Math.random() + 1) * mSpriteDim;
        mYellowAppleCoord[1] = (int) ((mYMax - 1) * Math.random() + 1) * mSpriteDim;
        for (int i = 0; i < mSnake.size(); i++) {
            seg = mSnake.get(i);
            if (mYellowAppleCoord[0] == seg.getXLoc() && mYellowAppleCoord[1] == seg.getYLoc()) {
                setYellowAppleCoord();
                break;
            }
        }
        yellowAppleBoo = true;
        yellowAppleCount = 0;
    }
    else {
        mYellowAppleCoord = new int[2];
        yellowAppleBoo = false;
    }
  }

  public int[] getYellowAppleCoord(){
      return mYellowAppleCoord;
  }

    private void setGreenAppleCoord(){
        //1 in 3 chance to get a green apple
        int chance = (int) (3 * Math.random());
        if(chance == 0) {
            SnakeSegment seg;
            mGreenAppleCoord[0] = (int) ((mXMax - 1) * Math.random() + 1) * mSpriteDim;
            mGreenAppleCoord[1] = (int) ((mYMax - 1) * Math.random() + 1) * mSpriteDim;
            for (int i = 0; i < mSnake.size(); i++) {
                seg = mSnake.get(i);
                if (mGreenAppleCoord[0] == seg.getXLoc() && mGreenAppleCoord[1] == seg.getYLoc()) {
                    setYellowAppleCoord();
                    break;
                }
            }
            greenAppleBoo = true;
            greenAppleCount = 0;
        }
        else {
            mGreenAppleCoord = new int[2];
            greenAppleBoo = false;
        }
    }

    public boolean getYellowApple(){
      return yellowAppleBoo;
    }

    public boolean getGreenApple(){
      return greenAppleBoo;
    }

    public int[] getGreenAppleCoord(){
        return mGreenAppleCoord;
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
