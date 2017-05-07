package com.projinda.fnsr;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
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

    /**
     * Images (graphical objects)
     */
    // Falling object
    Texture noteImage;
    // Goal marker
    Texture noteContourImage;

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
        // camera centered at Gdx.graphics.getWidth()/2
        // and ..getHeight()/2
        camera.setToOrtho(true);
    }

    private void initImages() {
        noteContourImage = new Texture(Gdx.files.internal("noteContour.png"));
        // represent contours by one canvas (a rectangle)
        Rectangle noteContour = new Rectangle();
        noteContour.x = Gdx.graphics.getWidth();
        noteContour.y = Gdx.graphics.getHeight();
    }
}
