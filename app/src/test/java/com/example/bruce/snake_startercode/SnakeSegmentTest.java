package com.example.bruce.snake_startercode;

import org.junit.Test;

import static org.junit.Assert.*;

public class SnakeSegmentTest {

    SnakeSegment seg1 = new SnakeSegment(SnakeSegment.BodyParts.HEAD, 0, 1, 1);

    @Test
    public void getBodyParts(){
        SnakeSegment.BodyParts expected = SnakeSegment.BodyParts.HEAD;
        SnakeSegment.BodyParts results = seg1.getBodyParts();
        assertEquals(expected, results);
    }

    @Test
    public void getDegrees(){
        SnakeSegment seg2;
        for(int i = 0; i < 270; i+= 90) {
            seg2 = new SnakeSegment(SnakeSegment.BodyParts.HEAD, i, 1, 1);
            int expected = i;
            int results = seg2.getDegrees();
            assertEquals(expected, results);
        }
    }

    @Test
    public void setDegrees(){
        for(int i = 0; i < 270; i += 90) {
            seg1.setDegrees(i);
            int expected = i;
            int results = seg1.getDegrees();
            assertEquals(expected, results);
        }
    }

    @Test
    public void getXLoc(){
        int expected = 1;
        int results = seg1.getXLoc();
        assertEquals(expected, results);
    }

    @Test
    public void setXLoc(){
        seg1.setXLoc(10);
        int expected = 10;
        int results = seg1.getXLoc();
        assertEquals(expected, results);
    }

    @Test
    public void getYLoc(){
        int expected = 1;
        int results = seg1.getYLoc();
        assertEquals(expected, results);
    }

    @Test
    public void setYLoc(){
        seg1.setYLoc(10);
        int expected = 10;
        int results = seg1.getYLoc();
        assertEquals(expected, results);
    }
}