package com.zabador.game.entities;

import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Animation;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureRegion;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.math.Vector2;
import com.sun.corba.se.impl.oa.poa.ActiveObjectMap.Key;
import com.zabador.game.StartBattle;
import com.zabador.game.screen.Battle;
import com.zabador.game.screen.MainMenu;
import com.zabador.game.screen.Play;
import com.zabador.game.screen.SaveScreen;
import com.zabador.game.tween.SpriteAccessor;

public class Player extends Sprite  {

    // movement velocity
    public Vector2 velocity = new Vector2();

    // the number of rows and col in the player sprite sheet
    private final int FRAME_COLS = 3, FRAME_ROWS = 4;

    private Animation leftAnimation, rightAnimation, upAnimation, downAnimation;
    private Texture walkSheet;
    private TextureRegion[] leftWalkFrames, rightWalkFrames, upWalkFrames, downWalkFrames;
    private TextureRegion currentFrame;
    public boolean left, right, up, down;

    // asign the correct rows for each direction
    private final int UP_ROW = 0;	
    private final int DOWN_ROW = 2;	
    private final int RIGHT_ROW = 1;	
    private final int LEFT_ROW = 3;	

	public final int LOWBOUNDSTEPS = 200;
	public final int HIGHBOUNTSTEPS = 800;

	private boolean isBlocked;

	// steps to next encounter
	private int stepsToEncounter;

    private float stateTime;

    private float speed = 60 * 2;

    public Player(Sprite sprite) {

        super(sprite);

        Texture.setEnforcePotImages(false); // sprite sheets do not need to have dimensions of Power Of Two anymore

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
                if(i == UP_ROW)
                    upWalkFrames[index++] = tmp[i][j];
                else if (i == RIGHT_ROW)
                    rightWalkFrames[index++] = tmp[i][j];
                else if (i == DOWN_ROW)
                    downWalkFrames[index++] = tmp[i][j];
                else if (i == LEFT_ROW)
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

		//set the random chance of the next encount
		stepsToEncounter = MathUtils.random(LOWBOUNDSTEPS, HIGHBOUNTSTEPS);
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

		spriteBatch.draw(currentFrame, getX(), getY());
    }

    public void update(float delta) {
        setX(getX() + velocity.x * delta);
        setY(getY() + velocity.y * delta);
        if((up || down || left || right) && (velocity.x != 0 || velocity.y != 0)) {
			stepsToEncounter--;
			stateTime += delta;
		}
    }

	public int getStepsToEncounter() {
		return this.stepsToEncounter;
	}

	public void setStepsToEncounter(int stepsToEncounter) {
		this.stepsToEncounter = stepsToEncounter;
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

}
