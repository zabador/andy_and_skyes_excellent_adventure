package com.zabador.game;

import com.badlogic.gdx.Game;
import com.zabador.game.screen.Play;

public class MainGame extends Game {
	
	
	@Override
	public void create() {		
		setScreen(new Play());
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
