package com.projinda.fnsr;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.math.Rectangle;
import com.badlogic.gdx.utils.Array;

/**
 * Created by Salim Rabat and Frans Nyberg on 07/05/2017.
 * This class represents the logic of the game.
 * note.png is an image that has the shape of a musical note, these note will fall down at different times
 * and different speeds. We might have some music playing in the background.
 */
public class GameScreen implements Screen {

    final UnnamedGame game;
    OrthographicCamera camera;
    Rectangle note;
    Array<Rectangle> notes;

    /** Falling object */
    Texture noteImage;
    /** The goal of the falling objects. Player should click when object reaches this goal. */
    Texture noteContourImage;
    /** Store the goals */
    Rectangle[] noteContourImages;

    /** Number of columns where beats fall **/
    private final int Columns = 3;

    public GameScreen(UnnamedGame game) {
        this.game = game;

        // initialize objects in game
        initCamera();
        initImages();

        // TODO
    }

    private void spawnNotes() {
        // TODO
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

        game.batch.begin();
        drawGoals();
        game.batch.end();

        // TODO
    }

    /**
     * @param width
     * @param height
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
        camera.setToOrtho(true, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());
    }

    /**
     * Image settings when starting game screen
     */
    private void initImages() {

        int canvasWidth = Gdx.graphics.getWidth();
        int canvasHeight = Gdx.graphics.getHeight();

        // Contours
        noteContourImage = new Texture(Gdx.files.internal("noteContour.png"));
        noteContourImages = new Rectangle[Columns];
        // Place each in separate rectangles
        for (int i = 0; i < Columns; i++) {
            Rectangle noteContour = new Rectangle();
            noteContour.x = canvasWidth / 2 * i;
            noteContour.y = 20;
            // In case we switch image, make it all of canvas size
            noteContour.width = canvasWidth;
            noteContour.height = canvasHeight;
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
