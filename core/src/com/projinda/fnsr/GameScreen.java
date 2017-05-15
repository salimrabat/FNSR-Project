package com.projinda.fnsr;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.TimeUtils;

/**
 * This class represents the logic of the game.
 * note.png is an image that has the shape of a musical note, these note will fall down at different times
 * and different speeds. We might have some music playing in the background.
 *
 * @author SR
 * @author FN
 * @version 1.0
 */
 class GameScreen implements Screen {

    private final RandomRhythm game;
    private OrthographicCamera camera;


    // Falling object
    private Texture noteImage;

    // Collection of column areas
    private Rectangle[] colAreas;
    // Collection of columns, order matters
    private Column columns[];

    // Keyboard keys from left to right
    private static final int[] keyboardKeys = new int[]{
            Input.Keys.A,
            Input.Keys.S,
            Input.Keys.D,
            Input.Keys.F
    };

    // Number of columns where beats fall
    private final int n_COL = 4;
    // Track when to spawn beats
    private long lastSpawnTime;
    // Score counter
    private int score;
    private int difficulty;
    private long timeDifficulty;
    // Game over when attempts reaches 0
    private int attempts = 5;
    // at this score, increase difficulty
    private int rampDifficulty = 30;
    // stop animations
    private boolean gameIsOver = false;

    GameScreen(RandomRhythm game, int difficulty, long timeDifficulty) {
        this.game = game;
        this.difficulty = difficulty;
        this.timeDifficulty = timeDifficulty;

        // initialize objects in game
        initCamera();
        initColumns();
        // spawn first beat and start timer for next beat
        spawnBeats();
        score = 0;
        // in case it was set to false
        Gdx.graphics.setContinuousRendering(true);
    }

    private void spawnBeats() {
        // Choose a random column
        int i = MathUtils.random(n_COL - 1);
        columns[i].spawnBeat();
        // start time ticker to track when to spawn next
        lastSpawnTime = TimeUtils.nanoTime();
    }

    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        if (!gameIsOver) {
            // Background and (our stationary) camera
            renderScreen();
            noteImage = new Texture(Gdx.files.internal("note.png"));

            // check if we need to create a new note depending on the timeDifficulty
            if (TimeUtils.nanoTime() - lastSpawnTime > timeDifficulty && score <= rampDifficulty) {
                spawnBeats();
            }

            // increase the number of spawned notes when your score is more than a given limit
            if (score > rampDifficulty && TimeUtils.nanoTime() - lastSpawnTime > timeDifficulty / 2) {
                spawnBeats();
            }

            // make the notes fall, remove any that are beneath the bottom edge of
            // the screen or are pressed.
            int scoreChange = 0;
            int attemptsChange = 0;
            for (Column col : columns) {
                col.fall(difficulty);
                attemptsChange += col.checkEndOfScreen();
                scoreChange += col.checkInput();
            }
            score += scoreChange;
            attempts += attemptsChange;
        }
        // Check escape press
        if (Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }
        // Handle batch for this game, which is every "saved thing" in the game.
        game.batch.begin();
        // number of pixels below top of screen
        int scorePosition = 30;
        game.font.draw(game.batch, "Score: " + score +
                "\nAttemps: " + attempts, 0, Gdx.graphics.getHeight() - scorePosition);
        drawTargets();
        // Beats
        for (Column col : columns) col.drawBeats();
        // Game over
        if (attempts <= 0) gameOver(false);
        // Game win
        else if (score >= rampDifficulty * 2) gameOver(true);
        game.batch.end();
    }

    /**
     * @param width int
     * @param height int
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        //no need to implement at the moment
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {
        //no need to implement at the moment
    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {
        //no need to implement at the moment
    }

    /**
     * Called when this screen is no longer the current screen for a {@link Game}.
     */
    @Override
    public void hide() {
        //no need to implement
    }

    /**
     * Called when this screen should release all resources.
     */
    @Override
    public void dispose() {
        // images
        noteImage.dispose();
        for (Column col : columns) { col.dispose(); }

        // screen
        game.dispose();
    }

    /**
     * Camera settings when starting game screen.
     */
    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Column settings when starting game screen.
     */
    private void initColumns() {
        colAreas = new Rectangle[n_COL];
        columns = new Column[n_COL];

        for (int i = 0; i < colAreas.length; i++) {
            // Create areas for columns
            Rectangle colArea = new Rectangle();
            // Equidistant division of game screen
            // i.e. cover the entire screen with exact same rectangles.
            colArea.x = Gdx.graphics.getWidth() / n_COL * i;
            // Cover all of screen
            colArea.y = 0;
            colArea.width = Gdx.graphics.getWidth() / n_COL;
            colArea.height = Gdx.graphics.getHeight();
            colAreas[i] = colArea;

            // Create columns
            Column column = new Column(game, colAreas[i], keyboardKeys[i]);
            columns[i] = column;
        }
    }

    /**
     * Render screen info. It's good practice apparently to update camera once per frame.
     */
    private void renderScreen() {
        // clear the screen with a different color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0.5f, 0.9f, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell the camera to update its matrices.
        camera.update();

        // tell the SpriteBatch to render in the
        // coordinate system specified by the camera.
        game.batch.setProjectionMatrix(camera.combined);
    }

    /**
     * Display goals where player should click when falling object reaches
     */
    private void drawTargets() {
        for (Column col : columns) col.drawTarget();
    }

    /**
     * Handles a game over, either loss or win kind of over.
     *
     * @param beatTheGame affects end message.
     */
    private void gameOver(boolean beatTheGame) {
        gameIsOver = true;
        // message depending if you beat it or if you failed
        String ending;
        ending = beatTheGame ? "Stage Clear" : "Game Over";
        // Stop the render loop for internal calls (inputs are still read)
        Gdx.graphics.setContinuousRendering(false);
        // top of game over messages
        int messageXPos = Gdx.graphics.getWidth() / 4;
        int messageYPos = Gdx.graphics.getHeight() / 2;
        game.font.draw(game.batch,
                ending + "\nPress esc to return to main menu",
                messageXPos, messageYPos);
    }
}
