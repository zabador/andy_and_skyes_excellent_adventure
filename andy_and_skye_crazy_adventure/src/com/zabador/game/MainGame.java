package com.zabador.game;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.Texture;
import com.zabador.game.screen.Splash;

public class MainGame extends Game {
	
	
	@Override
	public void create() {		
		Texture.setEnforcePotImages(false);
		setScreen(new Splash());
	}

	@Override
	public void dispose() {
		super.dispose();
	}

	@Override
	public void render() {		
		super.render();
	}

	@Override
	public void resize(int width, int height) {
		super.resize(width, height);
	}

	@Override
	public void pause() {
		super.pause();
	}

	@Override
	public void resume() {
		super.resume();
	}
}
