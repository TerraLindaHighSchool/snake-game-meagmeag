package com.example.bruce.snake_startercode;

import android.content.SharedPreferences;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Matrix;
import android.graphics.Rect;
import android.os.Handler;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.Gravity;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;
import android.widget.ImageView;
import android.widget.Toast;

import java.util.List;

public class GameActivity extends AppCompatActivity {
    private ImageView mImageView;
    private TextView mTextScore, mTextHighScore, mTextCountdown;
    private int mBOARD_WIDTH, mBOARD_HEIGHT;
    private SnakeGame mGame;
    private Bitmap mHeadBitmap, mBodyBitmap, mTailBitmap, mAppleBitmap, mYellowAppleBit, mGreenAppleBit;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_game);

        //get view IDs and dimension resources
        mImageView = findViewById(R.id.imageView);
        mTextScore = findViewById(R.id.textView_score);
        mTextHighScore = findViewById(R.id.textView_highScore);
        mTextCountdown = findViewById(R.id.textView_level);
        mBOARD_WIDTH = (int) getResources().getDimension(R.dimen.width);
        mBOARD_HEIGHT = (int) getResources().getDimension(R.dimen.height);

        //instantiate the game
        mGame = new SnakeGame(0,80,3,4, mBOARD_WIDTH, mBOARD_HEIGHT);

        //add bitmaps
        mHeadBitmap = BitmapFactory.decodeResource(mImageView.getResources(), R.drawable.head);
        mBodyBitmap = BitmapFactory.decodeResource(mImageView.getResources(), R.drawable.body);
        mTailBitmap = BitmapFactory.decodeResource(mImageView.getResources(), R.drawable.tail);
        mAppleBitmap = BitmapFactory.decodeResource(mImageView.getResources(), R.drawable.apple);
        mYellowAppleBit = BitmapFactory.decodeResource(mImageView.getResources(), R.drawable.appleyellow);
        mGreenAppleBit = BitmapFactory.decodeResource(mImageView.getResources(), R.drawable.applegreen);

        //listen for touches
        mImageView.setOnTouchListener(new View.OnTouchListener() {
            @Override
            public boolean onTouch(View v, MotionEvent event) {
                float xTouch = event.getX();
                float yTouch = event.getY();
                mGame.touched(xTouch, yTouch);
                return false;
            }
        });

        //snake animation
        final Handler mHandler = new Handler();
        final Runnable r = new Runnable(){
            @Override
            public void run() {
                boolean localStop = mGame.play();
                paintCanvas();


                mHandler.postDelayed(this, mGame.getMillisDelay());
                if(localStop) {
                    mHandler.removeCallbacks(this);
                    showToast("Game Over");
                }
                else if(mGame.getReset()) {
                    showToast("Next Level");
                    if (mGame.getLevel() == 3) {
                        showToast("Yellow is double");
                    } else if (mGame.getLevel() == 4) {
                        showToast("Green is poison");
                    }
                }
                else if(mGame.getWin()) {
                    mHandler.removeCallbacks(this);
                    showToast("You win!");
                }
                else
                    updateAndDeclareScores();
            }
        };
        if(mGame.getGameOver()) mHandler.removeCallbacks(r);
        mHandler.postDelayed(r, mGame.getMillisDelay());
    }

    public void paintCanvas(){
        List<SnakeSegment> snake = mGame.getSnake();
        int[] appleCoord = mGame.getAppleCoord();
        int[] yellowAppleCoord = mGame.getYellowAppleCoord();
        int[] greenAppleCoord = mGame.getGreenAppleCoord();
        int appleLeft = appleCoord[0];
        int appleTop = appleCoord[1];
        int yellowAppleLeft = yellowAppleCoord[0];
        int yellowAppleTop = yellowAppleCoord[1];
        int greenAppleLeft = greenAppleCoord[0];
        int greenAppleTop = greenAppleCoord[1];
        Bitmap ourBitmap = Bitmap.createBitmap(mBOARD_WIDTH, mBOARD_HEIGHT, Bitmap.Config.ARGB_8888);
        Canvas window = new Canvas(ourBitmap);
        Rect rectangle = null;
        Bitmap currentBitmap = null;
        window.drawColor(Color.BLACK); //Background color

        //draw snake
        for(int segment = 0; segment < snake.size(); segment++){
            rectangle = new Rect(snake.get(segment).getXLoc() * mGame.getSpriteDim(),
                    snake.get(segment).getYLoc() * mGame.getSpriteDim(), (snake.get(segment).getXLoc() + 1) * mGame.getSpriteDim(),
                    (snake.get(segment).getYLoc() + 1) * mGame.getSpriteDim());
            switch(snake.get(segment).getBodyParts()){
                case HEAD:
                    currentBitmap = mHeadBitmap;
                    break;
                case BODY:
                    currentBitmap = mBodyBitmap;
                    break;
                case TAIL:
                    currentBitmap = mTailBitmap;
                    break;
            }
            if(snake.get(segment).getDegrees() == 0)
                window.drawBitmap(currentBitmap, null, rectangle, null);
            else
                window.drawBitmap(rotateBitmap(currentBitmap, snake.get(segment).getDegrees()), null, rectangle, null);
        }

    //draw apple
    rectangle = new Rect(appleLeft, appleTop, appleLeft + mGame.getSpriteDim(), appleTop + mGame.getSpriteDim());
    window.drawBitmap(mAppleBitmap, null, rectangle, null);
    mImageView.setImageBitmap(ourBitmap);

    if(mGame.getYellowApple()){
            rectangle = new Rect(yellowAppleLeft, yellowAppleTop, yellowAppleLeft + mGame.getSpriteDim(), yellowAppleTop + mGame.getSpriteDim());
            window.drawBitmap(mYellowAppleBit, null, rectangle, null);
            mImageView.setImageBitmap(ourBitmap);
        }

        if(mGame.getGreenApple()){
            rectangle = new Rect(greenAppleLeft, greenAppleTop, greenAppleLeft + mGame.getSpriteDim(), greenAppleTop + mGame.getSpriteDim());
            window.drawBitmap(mGreenAppleBit, null, rectangle, null);
            mImageView.setImageBitmap(ourBitmap);
        }

    }





    public Bitmap rotateBitmap(Bitmap original, float degrees){
        int width = original.getWidth();
        int height = original.getHeight();
        Matrix matrix = new Matrix();
        if(degrees == 180)
            matrix.postScale(-1, 1, width / 2, height /2);
        else
            matrix.postRotate(degrees);
        return Bitmap.createBitmap(original, 0, 0, width, height, matrix, true);
    }

    /*********************************
     * misc concrete methods
     ***********************************/
    private void showToast(String toast){
        Toast correct = Toast.makeText(this, toast, Toast.LENGTH_LONG);
        correct.setGravity(Gravity.TOP|Gravity.CENTER_HORIZONTAL, 0, 200);
        correct.show();
    }

    protected void updateAndDeclareScores(){
        SharedPreferences prefs = getSharedPreferences("Snake", MODE_PRIVATE);
        SharedPreferences.Editor editor = prefs.edit();
        int highScore = prefs.getInt("High Score", 0);
        int currentScore = mGame.getScore();
        String score = "Score: " + currentScore;
        mTextScore.setText(score);
        String highScoreStr = "High Score: " + highScore;
        if (currentScore > highScore) {
            editor.putInt("High score: ", currentScore);
            editor.commit();
            highScoreStr = "High score: " + currentScore;
        }
        mTextHighScore.setText(highScoreStr);
        String countdown = "Level " + mGame.getLevel() + " Countdown " + mGame.getCountdown();
        mTextCountdown.setText(countdown);
    }
}
