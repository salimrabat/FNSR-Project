package com.projinda.fnsr;

import com.badlogic.gdx.graphics.Pixmap;

/**
 *  Target is the visual representation of the boundaries to which
 *  the player should time beats in game.
 *
 *  The targets are Pixel maps instead of images for:
 * <ul>
 *     Colourizing on the target, e.g. red if beat did not hit target.
 *     Simpler shaping and colourizing of objects to suit possible better design ideas.
 * </ul>
 *
 * @author FN
 * @version 0.1
 */
public class Target {

    // Handles drawing
    private Pixmap pixmap;

    /**
     * Constructor generates the initial target.
     */
    public Target(int targetWid, int targetHei) {
        // TODO which pixel map format?
        pixmap = new Pixmap(targetWid, targetHei, Pixmap.Format.Alpha);
        drawTarget();
    }

    /**
     * Draw a suitable target.
     */
    private void drawTarget() {

    }
}
