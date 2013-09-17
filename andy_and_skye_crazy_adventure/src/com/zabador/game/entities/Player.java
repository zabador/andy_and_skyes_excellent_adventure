package com.zabador.game.entities;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.Vector2;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;

public class Player extends Sprite implements InputProcessor {
	
	// movement velocity
	private Vector2 velocity = new Vector2();

	// the number of rows and col in the player sprite sheet
	private final int FRAME_COLS = 3, FRAME_ROWS = 4;

	private Animation leftAnimation, rightAnimation, upAnimation, downAnimation;
	private Texture walkSheet;
	private TextureRegion[] leftWalkFrames, rightWalkFrames, upWalkFrames, downWalkFrames;
	private TextureRegion currentFrame;
	private boolean left, right, up, down, moving;

	private float stateTime;
	
	private float speed = 60 * 2;
	
	private TiledMapTileLayer collisionLayer;
	
	public Player(Sprite sprite, TiledMapTileLayer collisionLayer) {
		super(sprite);
		Texture.setEnforcePotImages(false);
		this.collisionLayer = collisionLayer;
		walkSheet = new Texture(Gdx.files.internal("imgs/figure_sheet.png"));
		TextureRegion[][] tmp = TextureRegion.split(walkSheet, walkSheet.getWidth() / FRAME_COLS, walkSheet.getHeight() / FRAME_ROWS);
		leftWalkFrames = new TextureRegion[FRAME_COLS];
		rightWalkFrames = new TextureRegion[FRAME_COLS];
		upWalkFrames = new TextureRegion[FRAME_COLS];
		downWalkFrames = new TextureRegion[FRAME_COLS];

		// load the sprites for each direction
		int index = 0;
		for (int i = 0; i<FRAME_ROWS; i++) {
			for (int j=0; j<FRAME_COLS; j++) {
				if(i==0)
					upWalkFrames[index++] = tmp[i][j];
				else if (i == 1)
					rightWalkFrames[index++] = tmp[i][j];
				else if (i == 2)
					downWalkFrames[index++] = tmp[i][j];
				else if (i == 3)
					leftWalkFrames[index++] = tmp[i][j];
			}
			index = 0;
		}

		// set the animation with the correct direction frames
		leftAnimation = new Animation(0.3f, leftWalkFrames);
		rightAnimation = new Animation(0.3f, rightWalkFrames);
		upAnimation = new Animation(0.3f, upWalkFrames);
		downAnimation = new Animation(0.3f, downWalkFrames);
		stateTime = 0f;
		currentFrame = downAnimation.getKeyFrame(stateTime, true);
	}
	
	@Override
	public void draw(SpriteBatch spriteBatch) {
		update(Gdx.graphics.getDeltaTime());

		// get the correct facing direction
		if(up)
			currentFrame = upAnimation.getKeyFrame(stateTime, true);
		else if(down)
			currentFrame = downAnimation.getKeyFrame(stateTime, true);
		else if(left)
			currentFrame = leftAnimation.getKeyFrame(stateTime, true);
		else if(right)
			currentFrame = rightAnimation.getKeyFrame(stateTime, true);

		
		if(up || down || left || right)
			stateTime += Gdx.graphics.getDeltaTime();
			
		spriteBatch.draw(currentFrame, getX(), getY());
//		super.draw(spriteBatch);
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
			up = true;
			down = false;
			left = false;
			right = false;
			velocity.y = speed;
			break;
		case Keys.S:
		case Keys.DOWN:
			up = false;
			down = true;
			left = false;
			right = false;
			velocity.y = -speed;
			break;
		case Keys.A:
		case Keys.LEFT:
			up = false;
			down = false;
			left = true;
			right = false;
			velocity.x = -speed;
			break;
		case Keys.D:
		case Keys.RIGHT:
			up = false;
			down = false;
			left = false;
			right = true;
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
			velocity.y = 0;
			up = false;
			break;
		case Keys.S:
		case Keys.DOWN:
			down = false;
			velocity.y = 0;
			break;
		case Keys.A:
		case Keys.LEFT:
			velocity.x = 0;
			left = false;
			break;
		case Keys.D:
		case Keys.RIGHT:
			right = false;
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
		if(screenX < 200) {
			velocity.x = -speed;
			left = true;
		}
		else if(screenX > Gdx.graphics.getWidth()-200){
			velocity.x = speed;
			right = true;
		}
		else if(screenY < 200) {
			velocity.y = speed;
			down = true;
		}
		else if(screenY > Gdx.graphics.getHeight()-200){
			velocity.y = -speed;
			up = true;
		}
		return true;
	}

	@Override
	public boolean touchUp(int screenX, int screenY, int pointer, int button) {
		velocity.x = 0;
		velocity.y = 0;
		left = false;
		right = false;
		up = false;
		down = false;
		
		return true;
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
