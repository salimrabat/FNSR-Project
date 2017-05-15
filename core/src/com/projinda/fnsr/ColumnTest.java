package com.projinda.fnsr;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
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

    // Test parameters
    private Column[] columnTests;
    private final int numCol = 2;
    private final int steps = 2;    // exactly 2 beats will be on the fall test column
    private int beatYPoxChange;

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
            Rectangle columnArea = new Rectangle();
            columnArea.width = Gdx.graphics.getWidth() / numCol;
            columnArea.height = Gdx.graphics.getHeight();
            columnArea.x= Gdx.graphics.getWidth() / numCol * i;
            columnArea.y = 0;
            // Create columns
            columnTests[i] = new Column(gameScreen, columnArea, keyboardKeys[i]);
        }

        // Define test specific constants.
        beatYPoxChange = Gdx.graphics.getHeight() / steps;
    }

    /**
     * Start the game tests
     */
    @Override
    public void render() {
        // Put a grey background
        Gdx.gl.glClearColor(0.5f, 0.5f, 0.5f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        gameScreen.batch.begin();
        testDrawTarget();
        if (TimeUtils.nanoTime() - lastSpawnTime > 1e9) { // 1 second
            testSpawnBeat();
        }
        for (Column col : columnTests ) col.drawBeats();
        gameScreen.batch.end();

        testFall();
    }

    /**
     * Draw targets, visual test.
     */
    private void testDrawTarget() {
        for (Column col : columnTests) col.drawTarget();
    }

    /**
     * Draw beats at start, visual test.
     * Should draw symmetrically and change colours.
     */
    private void testSpawnBeat() {
        // Spawn on both columns
        for (Column col : columnTests) col.spawnBeat();
        // start timer
        lastSpawnTime = TimeUtils.nanoTime();
    }

    /**
     * Draw animation of beat falling, visual test.
     * Let only first (index 0) column fall.
     * Colour should not change on beat.
     */
    private void testFall() {
        columnTests[0].fall(beatYPoxChange);
    }
}
