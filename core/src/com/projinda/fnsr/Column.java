package com.projinda.fnsr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import java.awt.*;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * One column of falling beats and a target which corresponds to one given keyboard key.
 *
 * @author FN
 * @version 0.2
 */
class Column {

    // Game
    private RandomRhythm game;
    // Part of game screen for this column
    private Rectangle columnRec;
    // Keyboard key
    private int clickKey;
    // Area for beats to reach before player should click
    private Rectangle targetRec;

    // Target coordinates
    private float TXPos;
    private float TYPos;

    // Store beats
    // Use Linked List since beats are often added or removed
    // and stepped through from start from finish without order
    private LinkedList<Rectangle> beats;
    // Handle beats one-by-one
    private Iterator<Rectangle> beatIterator;

    // Images
    // Note
    private Texture noteImage;
    // size of note image
    private Point sizeNoteImage;
    // Note contour
    private Texture noteCImage;
    // size of note contour image
    private Point sizeNoteCImage;

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

        beats = new LinkedList<Rectangle>();

        // Images
        noteImage = new Texture(Gdx.files.internal("note.png"));
        sizeNoteImage = new Point(48,48);
        noteCImage = new Texture(Gdx.files.internal("noteContour.png"));
        sizeNoteCImage = new Point(48, 48);
        // target area
        targetRec = new Rectangle(TXPos, TYPos, sizeNoteCImage.x, sizeNoteCImage.y);
    }

    /**
     * Draw the boundaries of the target for beats.
     */
    void drawTarget() {
        game.batch.draw(noteCImage, TXPos, TYPos, sizeNoteCImage.x, sizeNoteCImage.y);
    }

    /**
     * Initiate a beat where it will start falling.
     */
    void spawnBeat() {
        // Contain a beat within a rectangle
        Rectangle note = new Rectangle();
        // Line up with target
        note.x = TXPos;
        // Spawn at the top
        note.y = columnRec.y + columnRec.height - sizeNoteImage.y;
        // Same size as image
        note.width = sizeNoteImage.x;
        note.height = sizeNoteImage.y;
        beats.add(note);
    }

    /**
     * Draw all beats in column.
     */
    void drawBeats() {
        for (Rectangle beat : beats) {
            game.batch.draw(noteImage, beat.x, beat.y);
        }
    }

    /**
     * Animate all beats falling by moving a constant distance down.
     * @param rate, increase for faster falling
     */
    void fall(int rate) {
        for (Rectangle beat : beats) {
            // 200 pixels per second
            beat.y -= rate * Gdx.graphics.getDeltaTime();
        }
    }

    /**
     * Handle input from player.
     * @return change in score.
     */
    int checkInput() {
        int scoreChange = 0;
        if (Gdx.input.isKeyJustPressed(clickKey)) {
            scoreChange = checkTarget();
        }
        return scoreChange;
    }

    /**
     * Reads all beats within target range and appends one score per beat.
     * If no beats are within target range, score is reduced.
     * @return change of score.
     */
    private int checkTarget() {
        int scoreChange = 0;
        if (beats.isEmpty()) {
            scoreChange--;
        } else {
            // Look for possible beats within target range
            boolean hitTarget = false;
            beatIterator = beats.iterator();
            while (beatIterator.hasNext()) {
                // next beat
                Rectangle beat = beatIterator.next();
                if (beat.overlaps(targetRec)) {
                    beatIterator.remove();
                    scoreChange++;
                    hitTarget = true;
                }
            }
            if (!hitTarget) scoreChange--;
        }
        return scoreChange;
    }

    /**
     * Removes beats if they fall outside the visible screen.
     */
    void checkEndOfScreen() {
        beatIterator = beats.iterator();
        while (beatIterator.hasNext()) {
            // next beat
            if (beatIterator.next().y < -sizeNoteImage.y) beatIterator.remove();
        }
    }

    void dispose() {
        noteCImage.dispose();
        noteImage.dispose();
    }
}
