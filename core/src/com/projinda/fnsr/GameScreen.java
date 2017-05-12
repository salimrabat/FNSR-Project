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
 * This class represents the logic of the game.
 * note.png is an image that has the shape of a musical note, these note will fall down at different times
 * and different speeds. We might have some music playing in the background.
 *
 * @author SR
 * @author FN
 * @version 0.2
 */
public class GameScreen implements Screen {

    private final RandomRhythm game;
    private OrthographicCamera camera;
    private Rectangle note;
    private Array<Rectangle> notes;

    // Falling object
//    private Texture blackNoteImage;
//    private Texture redNoteImage;
//    private Texture blueNoteImage;
//    private Texture greenNoteImage;
//    private Texture yellowNoteImage;
//    private Texture limeNoteImage;
//    private Texture orangeNoteImage;
//    private Texture pinkNoteImage;
//    private Texture purpleNoteImage;
    private Texture[] notesTextures; //this is the array where the note images will be stored.
    private Array<Texture> notesImages; // this the arraylist where the randomly chosen note images will be stored.

    // Collection of column areas
    private Rectangle[] colAreas;
    // Collection of columns
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
    private long lastSpawnTime;
    private int score;
    private int difficulty;
    private long timeDifficulty;

    GameScreen(RandomRhythm game, int difficulty, long timeDifficulty) {
        this.game = game;
        this.difficulty = difficulty;
        this.timeDifficulty = timeDifficulty;

        // initialize objects in game
        initCamera();
        initColumns();

        notes = new Array<Rectangle>(n_COL);
        spawnNotes();
        score = 0;

        notesTextures = new Texture[8];

        notesTextures[0] = new Texture(Gdx.files.internal("note.png")); //black note
        notesTextures[1] = new Texture(Gdx.files.internal("note_red.png")); //red note
        notesTextures[2] = new Texture(Gdx.files.internal("note_blue.png")); //blue note
        notesTextures[3] = new Texture(Gdx.files.internal("note_green.png")); //green note
        notesTextures[4] = new Texture(Gdx.files.internal("note_yellow.png")); //yellow note
        notesTextures[5] = new Texture(Gdx.files.internal("note_orange.png")); // orange note
        notesTextures[6] = new Texture(Gdx.files.internal("note_pink.png")); //pink note
        notesTextures[7] = new Texture(Gdx.files.internal("note_purple.png")); // purple note

        notesImages = new Array<Texture>(n_COL);
    }

    private void spawnNotes() {
        int i = MathUtils.random(3);
        note = new Rectangle();
        note.x = colAreas[i].x + colAreas[i].width / 2;
        note.y = Gdx.graphics.getHeight();
        note.width = 48;
        note.height = 48;
        notes.add(note);
        // start time ticker
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

        // Background and (our stationary) camera
        renderScreen();

        if(notes.size != notesImages.size) {
            int random = MathUtils.random(0, 7);
            notesImages.add(notesTextures[random]);
        }


        game.batch.begin();
        game.font.draw(game.batch, "Score: " + score, 0, Gdx.graphics.getHeight() - 30);
        drawTargets();
        for(int i = 0; i < notes.size; i++) {
            game.batch.draw(notesImages.get(i), notes.get(i).x, notes.get(i).y);

        }

        game.batch.end();

        // check if we need to create a new note depending on the timeDifficulty
        if (TimeUtils.nanoTime() - lastSpawnTime > timeDifficulty && score <= 50) {
            spawnNotes();
        }

        // increase the number of spawned notes when your score is more than 50
        if (score > 50 && TimeUtils.nanoTime() - lastSpawnTime > timeDifficulty/2) {
            spawnNotes();
        }

        // make the notes fall, remove any that are beneath the bottom edge of
        // the screen or are pressed.
        Iterator<Rectangle> it = notes.iterator();
        Iterator<Texture> iter = notesImages.iterator();
        while (it.hasNext() && iter.hasNext()) {
            Rectangle note = it.next();
            iter.next();
            note.y -= difficulty * Gdx.graphics.getDeltaTime();
            if (note.y + 48 < 0) {
                it.remove();
                iter.remove();
            }
            if (note.overlaps(columns[0].getTargetRec()) && Gdx.input.isKeyPressed(Input.Keys.A)) {
                score++;
                it.remove();
                iter.remove();
            }
            if (note.overlaps(columns[1].getTargetRec()) && Gdx.input.isKeyPressed(Input.Keys.S)) {
                score++;
                it.remove();
                iter.remove();
            }
            if (note.overlaps(columns[2].getTargetRec()) && Gdx.input.isKeyPressed(Input.Keys.D)) {
                score++;
                it.remove();
                iter.remove();
            }
            if (note.overlaps(columns[3].getTargetRec()) && Gdx.input.isKeyPressed(Input.Keys.F)) {
                score++;
                it.remove();
                iter.remove();
            }
        }

        if(Gdx.input.isKeyPressed(Input.Keys.ESCAPE)) {
            game.setScreen(new MainMenuScreen(game));
        }

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
        for (Texture noteImage : notesTextures) { noteImage.dispose(); }
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
}
