package com.projinda.fnsr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

import java.awt.Point;
import java.util.Iterator;
import java.util.LinkedList;

/**
 * One column of falling beats and a target which corresponds to one given keyboard key.
 *
 * @author FN
 * @author SR
 * @version 0.3
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
    private Texture[] notesTextures; //this is the array where the note images will be stored.
    private Array<Texture> notesImages; // this the arraylist where the randomly chosen note images will be stored.
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
        notesTextures = new Texture[8];

        notesTextures[0] = new Texture(Gdx.files.internal("note.png")); //black note
        notesTextures[1] = new Texture(Gdx.files.internal("note_red.png")); //red note
        notesTextures[2] = new Texture(Gdx.files.internal("note_blue.png")); //blue note
        notesTextures[3] = new Texture(Gdx.files.internal("note_green.png")); //green note
        notesTextures[4] = new Texture(Gdx.files.internal("note_yellow.png")); //yellow note
        notesTextures[5] = new Texture(Gdx.files.internal("note_orange.png")); // orange note
        notesTextures[6] = new Texture(Gdx.files.internal("note_pink.png")); //pink note
        notesTextures[7] = new Texture(Gdx.files.internal("note_purple.png")); // purple note

        notesImages = new Array<Texture>(4);
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
        if(beats.size() != notesImages.size) {
            int random = MathUtils.random(0, 7);
            notesImages.add(notesTextures[random]);
        }
    }

    /**
     * Draw all beats in column.
     */
    void drawBeats() {
        for (int i = 0; i < beats.size(); i++) {
            game.batch.draw(notesImages.get(i), beats.get(i).x, beats.get(i).y);
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
            Iterator<Texture> imgIterator = notesImages.iterator();
            while (beatIterator.hasNext()) {
                // next beat
                Rectangle beat = beatIterator.next();
                imgIterator.next();
                if (beat.overlaps(targetRec)) {
                    beatIterator.remove();
                    imgIterator.remove();
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
        Iterator<Texture> imgIterator = notesImages.iterator();
        while (beatIterator.hasNext()) {
            imgIterator.next();
            // next beat
            if (beatIterator.next().y < -sizeNoteImage.y){
                beatIterator.remove();
                imgIterator.remove();
            }
        }
    }

    void dispose() {
        noteCImage.dispose();
        for (Texture noteimg : notesImages) {
            noteimg.dispose();
        }
        for (Texture notex : notesTextures) {
            notex.dispose();
        }
    }
}
