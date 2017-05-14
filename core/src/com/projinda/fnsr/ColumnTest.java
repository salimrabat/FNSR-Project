package com.projinda.fnsr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;
import com.projinda.fnsr.test_utils.GdxTest;

/**
 * Test class for Column.
 * Part of the testing is visual to see if it's player-friendly.
 * This is because most methods in Column (except score counting)
 * return void and are there to change the game visually.
 *
 * @author FN
 * @version 0.1
 */
public class ColumnTest extends GdxTest {

    private Column[] columnTests;

    // Test parameters
    private final int numCol = 2;

    // Game parameters
    private RandomRhythm gameScreen;
    private static final int[] keyboardKeys = new int[]{
            Input.Keys.A,
            Input.Keys.S,
            Input.Keys.D,
            Input.Keys.F
    };
    private long lastSpawnTime;

    /**
     * Create column instances, which requires creating a game instance.
     */
    @Override
    public void create() {
        gameScreen = new RandomRhythm();
        gameScreen.batch = new SpriteBatch();
        columnTests = new Column[numCol];
        for ( int i = 0; i < numCol; i++ ) {
            // Setup columns equidistantly
            int colWidth = Gdx.graphics.getWidth() / numCol;
            int colHeight = Gdx.graphics.getHeight();
            int col_x = Gdx.graphics.getWidth() * i / numCol;
            int col_y = 0;
            // Create columns
            Rectangle columnArea = new Rectangle(colWidth, colHeight, col_x, col_y);
            columnTests[i] = new Column(gameScreen, columnArea, keyboardKeys[i]);
        }
    }

    /**
     * Start the game tests
     */
    @Override
    public void render() {
        gameScreen.batch.begin();
        if (TimeUtils.nanoTime() - lastSpawnTime > 1e9) { // 1 second
            testSpawnBeat();
        }
        gameScreen.batch.end();
    }

    /**
     * Test spawning and drawing beats.
     */
    private void testSpawnBeat() {
        // Spawn on both columns
        for (Column col : columnTests) {
            col.spawnBeat();
            col.drawBeats();
        }
        // start timer
        lastSpawnTime = TimeUtils.nanoTime();
    }
}
