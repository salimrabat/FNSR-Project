package com.projinda.fnsr;

import com.badlogic.gdx.ApplicationListener;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.InputListener;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.utils.viewport.ScreenViewport;


/**
 * This Screen represents the Main Menu Screen, where you can click on start to start the game or choose between
 * easy mode, normal mode and hard mode.
 *
 * @author SR
 * @version 1.0
 */
 class MainMenuScreen implements Screen {

    private final RandomRhythm game;
    private OrthographicCamera camera;
    private Stage stage;

    private final int EASY = 200;
    private final int NORMAL = 250;
    private final int HARD = 350;

    private final long EASYTIME = 1000000000;
    private final long NORMALTIME = 1000000000/2;
    private final long HARDTIME = 1000000000/3;


    MainMenuScreen(final RandomRhythm game) {
        this.game = game;
        camera = new OrthographicCamera();
        camera.setToOrtho(false, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        //initialise a stage that will contain the buttons of the main menu
        stage = new Stage(new ScreenViewport());

        //initialise a skin for the main menu that will give our UI a "good look"
        Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));//glassy-ui is one of libgdx free skins.

        //Title
        Label label = new Label("Welcome to Random Rhythm", skin);
        label.setFontScale(2);
        label.setPosition(Gdx.graphics.getWidth()/2 - label.getWidth()/2 - 100, Gdx.graphics.getHeight() - 50);
        stage.addActor(label);

        //EASY MODE which set the speed of the falling note to 100
        Button easy = new TextButton("Easy Mode", skin, "small");
        easy.setPosition(Gdx.graphics.getWidth()/2 - easy.getWidth()/2, Gdx.graphics.getHeight()/2 - easy.getHeight()/2 + 100);
        easy.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new GameScreen(game, EASY, EASYTIME));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(easy);

        //NORMAL MODE which set the speed of the falling note to 200
        Button normal = new TextButton("Normal Mode", skin, "small");
        normal.setPosition(Gdx.graphics.getWidth()/2 - normal.getWidth()/2, Gdx.graphics.getHeight()/2 - normal.getHeight()/2 - easy.getHeight() + 50);
        normal.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new GameScreen(game, NORMAL, NORMALTIME));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(normal);

        //HARD MODE which set the speed of the falling note to 300
        Button hard = new TextButton("Hard Mode", skin, "small");
        hard.setPosition(Gdx.graphics.getWidth()/2 - hard.getWidth()/2, Gdx.graphics.getHeight()/2 - hard.getHeight()/2 - easy.getHeight() - normal.getHeight());
        hard.addListener(new InputListener(){
            @Override
            public void touchUp (InputEvent event, float x, float y, int pointer, int button) {
                dispose();
                game.setScreen(new GameScreen(game, HARD, HARDTIME));
            }
            @Override
            public boolean touchDown (InputEvent event, float x, float y, int pointer, int button) {
                return true;
            }
        });
        stage.addActor(hard);

        Label label2 = new Label("Game buttons:" +
                "\nClick A to get the notes to the left" +
                "\nClick S to get the notes to the middle left" +
                "\nClick D to get the notes to the middle right" +
                "\nClick F to get the notes to the right" +
                "\n Click ESC to get back the main menu", skin);
        stage.addActor(label2);



    }
    /**
     * Called when this screen becomes the current screen for a {@link Game}.
     */
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    /**
     * Called when the screen should render itself.
     *
     * @param delta The time in seconds since the last render.
     */
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0.5f, 0, 0.9f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        camera.update();
        game.batch.setProjectionMatrix(camera.combined);

        game.batch.begin();

        stage.act();
        stage.draw();

        game.batch.end();
    }

    /**
     * @param width of screen
     * @param height of screen
     * @see ApplicationListener#resize(int, int)
     */
    @Override
    public void resize(int width, int height) {
        //no need to implement
    }

    /**
     * @see ApplicationListener#pause()
     */
    @Override
    public void pause() {
        //no need to implement
    }

    /**
     * @see ApplicationListener#resume()
     */
    @Override
    public void resume() {
        //no need to implement
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
        stage.dispose();
    }
}
