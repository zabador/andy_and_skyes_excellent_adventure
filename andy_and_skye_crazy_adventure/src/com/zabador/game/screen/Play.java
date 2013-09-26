package com.zabador.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.utils.Base64Coder;
import com.zabador.game.entities.Player;
import com.zabador.game.ui.MainUi;

public class Play implements Screen {

	private TiledMap map;
	private OrthogonalTiledMapRenderer renderer;
	private OrthographicCamera camera;
	private Player player;
	private MainUi mainui;
	private Preferences prefs;
	private boolean loading = false; // for when user is loading a saved game

	//returning from battle scene
	public Play(Player player) {
		this.player = player;
	}

	// loading a saved game
	public Play(Preferences prefs) {
		this.prefs = prefs;
		loading = true;
	}

	// new game
	public Play() {

	}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        
        renderer.setView(camera);
        renderer.render();

        
		// -100 becuase of the size of the character ui screen at the botton
        camera.position.set(player.getX() + player.getWidth() / 2, (player.getY() + player.getHeight() / 2)-100, 0);
        camera.update();
        
        renderer.getSpriteBatch().begin();
        player.draw(renderer.getSpriteBatch());
        renderer.getSpriteBatch().end();
		mainui.draw();
    }

    @Override
    public void resize(int width, int height) {
        
        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }
    @Override
    public void show() {
        map = new TmxMapLoader().load("maps/mountains.tmx");
        
        renderer = new OrthogonalTiledMapRenderer(map);
        
        camera = new OrthographicCamera();
        //camera.zoom = .5f;
    
		if(player == null){ // it is a brand new game
			player = new Player(new Sprite(new Texture("imgs/player.png")), (TiledMapTileLayer) map.getLayers().get(0));
			if(loading) { // load player from saved preferences
				String x = new String(Base64Coder.decode(prefs.getString("playerX").toCharArray()));
				String y = new String(Base64Coder.decode(prefs.getString("playerY").toCharArray()));
				player.setPosition(Float.parseFloat(x), Float.parseFloat(y));
			}else
				player.setPosition(46 * player.getCollisionLayer().getTileWidth(), 10 * player.getCollisionLayer().getTileHeight());
		}
		else // player has returned from a battle scene
			player.setPosition(player.getX(),player.getY());

        // tell game where the input processor is
        Gdx.input.setInputProcessor(player);
		
		mainui = new MainUi();
    }

    @Override
    public void hide() {
        dispose();
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
        map.dispose();
        renderer.dispose();
        player.getTexture().dispose();
    }

}
