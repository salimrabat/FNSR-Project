package com.projinda.fnsr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;

import java.awt.*;

/**
 * One column of falling beats which corresponds to one give keyboard key.
 *
 * @author FN
 * @version 0.1
 */
class Column {

    // Game screen
    private RandomRhythm game;
    // Part of game screen for this column
    private Rectangle columnRec;
    // Keyboard key
    private int clickKey;
    // Area for beats to reach before player should click
    private Rectangle targetRec;

    // Target coordinates
    float TXPos;
    float TYPos;

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
    Column(RandomRhythm gameScreen, Rectangle columnArea, int key) {
        game = gameScreen;
        columnRec = columnArea;
        clickKey = key;

        // Center target
        TXPos = columnArea.x + columnArea.width / 2;
        // Position above lower boundary
        TYPos = 20;

        // Images
        noteC = new Texture(Gdx.files.internal("noteContour.png"));
        // size of image
        sizeNoteCImage = new Point(48, 48);
        // target area
        targetRec = new Rectangle(TXPos, TYPos, sizeNoteCImage.x, sizeNoteCImage.y);
    }

    /**
     * Draw the boundaries of the target for beats.
     */
    void drawTarget() {

        game.batch.draw(noteC, TXPos, TYPos, sizeNoteCImage.x, sizeNoteCImage.y);
    }

    /**
     * Get the area in which the target is active at.
     * @return Area of target.
     */
    Rectangle getTargetRec() { return targetRec; }

    void dispose() {
        noteC.dispose();
    }
}
