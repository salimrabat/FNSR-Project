package com.projinda.fnsr;

import com.projinda.fnsr.test_utils.GdxTest;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

import static org.junit.Assert.*;

/**
 * Test class for Column.
 * Part of the testing is visual to see if it's player-friendly.
 * This is because most methods in Column (except score counting)
 * return void and are there to change the game visually;
 * and since some elements such as colours are random.
 * Other tests are assertion tests,
 * which are appropriate when possible bugs are invisible.
 * Assertion tests are however only made when that is feasible.
 *
 * Correct visual series of events should be: TODO
 *
 * @author FN
 * @version 0.2
 */
public class ColumnTest extends GdxTest {

    // Test parameters
    private Column[] columnTests;
    private final int numCol = 2;
    // Measure falling speed by counting all possible beats on one column
    private final int max_n_beats_falling = 3;
    // change of frame per fall method call
    private int beatYPosChange;
    // number of beats spawned on screen per second
    // Determines how fast notes spawn
    private int spawningRate = 2;
    // time when test starts
    private long testStart;
    // time between spawns of beats.
    private final long spawnInterval = (long) 1e9 / max_n_beats_falling * spawningRate;
    // stop testing after this time
    // Determines how long this test will take
    private final long max_spawns = 5 * max_n_beats_falling / spawningRate;

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
        // One beat takes 1 second to fall all the way
        beatYPosChange = Gdx.graphics.getHeight() / spawningRate;
        testStart = TimeUtils.nanoTime();
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
        if (TimeUtils.nanoTime() - lastSpawnTime > spawnInterval) {
            testSpawnBeat();
        }
        for (Column col : columnTests ) col.drawBeats();
        gameScreen.batch.end();

        // Test beats animation
        boolean all_beats = false;
        long sinceStart = lastSpawnTime - testStart;
        // All beats should have spawned after some time
        if (sinceStart > spawnInterval * (max_n_beats_falling - 1)) all_beats = true;
        testFall(all_beats);

        // end of test
        if (sinceStart > spawnInterval * max_spawns) Gdx.app.exit();
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
     * Ensure speed of falling by counting the number of beats, assertion test.
     * Let only one column call fall.
     *
     * @param all_beats true when we expect all beats to have spawned
     */
    private void testFall(boolean all_beats) throws RuntimeException {
        int fallColIndex = 1;
        Column fallCol = columnTests[fallColIndex];

        fallCol.fall(beatYPosChange);
        fallCol.checkEndOfScreen();
        // Test falling once we expect all beats have spawned.
        int n_beats = fallCol.getBeats().size();
        if (all_beats) {
            // Cover for the case where a beat is just removed
            assertTrue("either too few or too many beats",
                    max_n_beats_falling >= n_beats && n_beats >= max_n_beats_falling - 1);
        } else {
            assertTrue("too many beats," + n_beats,n_beats < max_n_beats_falling);
        }
    }
}
