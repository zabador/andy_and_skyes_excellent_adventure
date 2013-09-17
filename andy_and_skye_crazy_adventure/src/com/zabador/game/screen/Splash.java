package com.zabador.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;

public class Splash implements Screen {

	private Sprite splash;
	private SpriteBatch batch;

	@Override
	public void render(float delta) {
	
		// clear screen
		Gdx.gl.glClearColor(0,0,0,1);
		Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

		// draw the splash on the sprite batch
		batch.begin();
		splash.draw(batch);
		batch.end();
	}

	@Override
	public void resize(int width, int height) {

	}

	@Override
	public void show() {
		Texture.setEnforcePotImages(false);
		batch = new SpriteBatch();

		// load splash image
		splash = new Sprite(new Texture("imgs/splash.png"));
		splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

	}

	@Override
	public void hide() {

	}

	@Override
	public void pause() {

	}

	@Override
	public void resume() {

	}

	@Override
	public void dispose() {
		//resource management
		batch.dispose();
	}
	
}
