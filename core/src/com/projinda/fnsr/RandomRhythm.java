package com.projinda.fnsr;

import com.badlogic.gdx.Application;
import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class RandomRhythm extends Game {

	SpriteBatch batch;
	BitmapFont font;

	/**
	 * Called when the {@link Application} is first created.
	 */
	@Override
	public void create() {
		batch = new SpriteBatch();

		font = new BitmapFont();

		this.setScreen(new MainMenuScreen(this));
	}

	public void render() {
		super.render();
	}

	public void dispose() {
		batch.dispose();
		font.dispose();
	}
}
