package com.projinda.fnsr;

import com.badlogic.gdx.*;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;
import com.badlogic.gdx.utils.TimeUtils;

import java.util.Iterator;

/**
 * Created by Salim Rabat and Frans Nyberg on 07/05/2017.
 * This class represents the logic of the game.
 * note.png is an image that has the shape of a musical note, these note will fall down at different times
 * and different speeds. We might have some music playing in the background.
 */
public class GameScreen implements Screen {

    private final UnnamedGame game;
    private OrthographicCamera camera;
    private Rectangle note;
    private Array<Rectangle> notes;

    // Falling object
    private Texture noteImage;
    // The goal of the falling objects. Player should click when object reaches this goal
    private Texture noteContourImage;
    // Store the goals
    private Rectangle[] noteContourImages;

    // Number of columns where beats fall
    private final int COLUMNS = 4;
    private long lastSpawnTime;
    private int score;

    GameScreen(UnnamedGame game) {
        this.game = game;

        // initialize objects in game
        initCamera();
        initImages();

        // TODO
        notes = new Array<Rectangle>(COLUMNS);
//        for (int i = 0; i < COLUMNS; i++) {
//            note = new Rectangle();
//            note.x = Gdx.graphics.getWidth() / (COLUMNS + 1) * (i + 1);
//            note.y = Gdx.graphics.getHeight();
//            note.width = 48;
//            note.height = 48;
//            notes.add(note);
//            lastSpawnTime = TimeUtils.nanoTime();
//        }
        spawnNotes();
        score = 0;
    }

    private void spawnNotes() {
        // TODO
        int i = MathUtils.random(3);
        note = new Rectangle();
        note.x = noteContourImages[i].x;
        note.y = Gdx.graphics.getHeight();
        note.width = 48;
        note.height = 48;
        notes.add(note);
        lastSpawnTime = TimeUtils.nanoTime();
    }


    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        // TODO
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {

        // Background and (our stationary) camera
        renderScreen();
        noteImage = new Texture(Gdx.files.internal("note.png"));

        game.batch.begin();
        game.font.draw(game.batch, "Drops Collected: " + score, 0, Gdx.graphics.getHeight() - 30);
        drawGoals();
        for (Rectangle note : notes) {
            game.batch.draw(noteImage, note.x, note.y);
        }
        game.batch.end();

        // check if we need to create a new note
        if (TimeUtils.nanoTime() - lastSpawnTime > 1000000000) {
            spawnNotes();
        }

        // make the notes fall, remove any that are beneath the bottom edge of
        // the screen or are pressed.
        Iterator<Rectangle> iter = notes.iterator();
        while (iter.hasNext()) {
            Rectangle note = iter.next();
            note.y -= 200 * Gdx.graphics.getDeltaTime();
            if (note.y + 48 < 0)
                iter.remove();
            if (note.overlaps(noteContourImages[0]) && Gdx.input.isKeyPressed(Input.Keys.A)) {
                score++;
                iter.remove();
            }
            if (note.overlaps(noteContourImages[1]) && Gdx.input.isKeyPressed(Input.Keys.S)) {
                score++;
                iter.remove();
            }
            if (note.overlaps(noteContourImages[2]) && Gdx.input.isKeyPressed(Input.Keys.D)) {
                score++;
                iter.remove();
            }
            if (note.overlaps(noteContourImages[3]) && Gdx.input.isKeyPressed(Input.Keys.F)) {
                score++;
                iter.remove();
            }
        }

        // TODO
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
        // TODO
    }

    /**
     * Camera settings when starting game screen
     */
    private void initCamera() {
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Image settings when starting game screen
     */
    private void initImages() {

        // Contours
        noteContourImage = new Texture(Gdx.files.internal("noteContour.png"));
        noteContourImages = new Rectangle[COLUMNS];
        // Place each in separate rectangles
        for (int i = 0; i < COLUMNS; i++) {
            Rectangle noteContour = new Rectangle();
            noteContour.x = Gdx.graphics.getWidth() / (COLUMNS +1) * (i + 1);
            noteContour.y = 20;
            // Hardcoded, unfortunately
            noteContour.width = 48;
            noteContour.height = 48;
            noteContourImages[i] = noteContour;
        }
    }

    /**
     * Render screen info. It's good practice apparently to update camera once per frame.
     */
    private void renderScreen() {
        // clear the screen with a dark blue color. The
        // arguments to glClearColor are the red, green
        // blue and alpha component in the range [0,1]
        // of the color to be used to clear the screen.
        Gdx.gl.glClearColor(0.2f, 0.2f, 0.5f, 1);
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
    private void drawGoals() {
        for (Rectangle contour : noteContourImages) {
            float xpos = contour.x;
            float ypos = contour.y;
            float wei = contour.width;
            float hei = contour.height;
            game.batch.draw(noteContourImage, xpos, ypos, wei, hei);
        }
    }
}
