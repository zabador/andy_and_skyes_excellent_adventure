package com.zabador.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class Player extends Sprite implements InputProcessor {
	
	// movement velocity
	private Vector2 velocity = new Vector2();
	
	private float speed = 60 * 2;
	
	private TiledMapTileLayer collisionLayer;
	
	public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(sprite);
		this.collisionLayer = collisionLayer;
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());
		super.draw(spriteBatch);
	}
	
	public void update(float delta) {
		
		float oldX = getX(), oldY = getY();
		float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
		
		boolean collisionX = false, collisionY = false;
		
		setX(getX() + velocity.x * delta);
		
		if(velocity.x < 0) {
			
			collisionX = collisionLayer.getCell((int)(getX()/tileWidth), (int)((getY()+getHeight()/2)/tileHeight))
					.getTile().getProperties().containsKey("blocked");
			
		}else if(velocity.x > 0) {
				collisionX = collisionLayer.getCell((int)((getX() + getWidth())/tileWidth), (int)((getY()+getHeight()/2)/tileHeight))
					.getTile().getProperties().containsKey("blocked");		
		}
		
		if(collisionX) {
			setX(oldX);
			velocity.x = 0;
		}


		setY(getY() + velocity.y * delta);

		if(velocity.y < 0) {
			
			collisionY = collisionLayer.getCell((int)((getX() + getWidth()/2) / tileWidth), (int) (getY() / tileHeight))
					.getTile().getProperties().containsKey("blocked");
			
		}else if(velocity.y > 0) {
			collisionY = collisionLayer.getCell((int)((getX() + getWidth()/2) / tileWidth), (int) ((getY() + getHeight()) / tileHeight))
					.getTile().getProperties().containsKey("blocked");

		}

		if(collisionY) {
			setY(oldY);
			velocity.y = 0;
		}
	}

	public Vector2 getVelocity() {
		return velocity;
	}

	public void setVelocity(Vector2 velocity) {
		this.velocity = velocity;
	}

	public float getSpeed() {
		return speed;
	}

	public void setSpeed(float speed) {
		this.speed = speed;
	}

	public TiledMapTileLayer getCollisionLayer() {
		return collisionLayer;
	}

	public void setCollisionLayer(TiledMapTileLayer collisionLayer) {
		this.collisionLayer = collisionLayer;
	}

	@Override
	public boolean keyDown(int keycode) {
		switch (keycode) {
		case Keys.W:
		case Keys.UP:
			velocity.y = speed;
			break;
		case Keys.S:
		case Keys.DOWN:
			velocity.y = -speed;
			break;
		case Keys.A:
		case Keys.LEFT:
			velocity.x = -speed;
			break;
		case Keys.D:
		case Keys.RIGHT:
			velocity.x = speed;
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyUp(int keycode) {
		switch (keycode) {
		case Keys.W:
		case Keys.UP:
		case Keys.S:
		case Keys.DOWN:
			velocity.y = 0;
			break;
		case Keys.A:
		case Keys.LEFT:
		case Keys.D:
		case Keys.RIGHT:
			velocity.x = 0;
			break;

		default:
			break;
		}
		return true;
	}

	@Override
	public boolean keyTyped(char character) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDown(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean touchDragged(int screenX, int screenY, int pointer) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean mouseMoved(int screenX, int screenY) {
		// TODO Auto-generated method stub
		return false;
	}

	@Override
	public boolean scrolled(int amount) {
		// TODO Auto-generated method stub
		return false;
	}

}