package com.projinda.fnsr;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.math.Rectangle;

/**
 * One column of falling beats which corresponds to one give keyboard key.
 */
public class Column {

    // Part of game screen for this column
    private Rectangle columnSize;
    // Keyboard key
    private Input.Keys clickKey;
    // Area for beats to reach before player should click
    private Rectangle targetRec;

    /**
     * Constructor for Column to call from some Screen instance.
     * @param key keyboard button to press for target.
     */
    public Column(Input.Keys key) {
        clickKey = key;
    }
}
