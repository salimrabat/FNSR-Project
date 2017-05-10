package com.projinda.fnsr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

/**
 * One column of falling beats which corresponds to one give keyboard key.
 *
 * @author FN
 * @version 10 may 2017
 */
public class Column {

    // Game screen
    RandomRhythm game;
    // Part of game screen for this column
    private Rectangle columnRec;
    // Keyboard key
    private Input.Keys clickKey;
    // Area for beats to reach before player should click
    private Rectangle targetRec;

    // Images
    // Note contour
    Texture noteC;
    // size of note contour image
    Point sizeNoteCImage;

    /**
     * Constructor for Column to call from some Screen instance.
     * @param gameScreen Screen to draw on.
     * @param columnArea Rectangle representing the area which this column covers.
     * @param key keyboard button to press for target.
     */
    public Column(RandomRhythm gameScreen, Rectangle columnArea, Input.Keys key) {
        game = gameScreen;
        columnRec = columnArea;
        clickKey = key;

        // Images
        noteC = new Texture(Gdx.files.internal("noteContour.png"));
        sizeNoteCImage = new Point(48, 48);
    }

    /**
     * Draw the boundaries of the target for beats.
     */
    public void drawTarget() {
        // Center target
        float xPos = columnRec.x + columnRec.width / 2;
        // Position above lower boundary
        float yPos = columnRec.y + 20;

        game.batch.draw(noteC, xPos, yPos, sizeNoteCImage.x, sizeNoteCImage.y);
    }
}
