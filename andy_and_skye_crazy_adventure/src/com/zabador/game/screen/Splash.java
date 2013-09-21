package com.zabador.game.screen;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.zabador.game.tween.SpriteAccessor;

public class Splash implements Screen {

    private Sprite splash;
    private SpriteBatch batch;
    private TweenManager tweenManager; // used to fade the splash screen

    @Override
    public void render(float delta) {

        // clear screen
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

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

        tweenManager = new TweenManager();
        // tell the tween manager what accessor class to manage
        Tween.registerAccessor(Sprite.class, new SpriteAccessor());
        batch = new SpriteBatch();

        // load splash image
        splash = new Sprite(new Texture(Gdx.files.internal("imgs/splash.png")));
        splash.setSize(Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // set the starting alpha for the tween which is transparent
        Tween.set(splash, SpriteAccessor.ALPHA).target(0).start(tweenManager);
        // start animation it will take 2 seconds to reach 1 alpha which is opaque
        Tween.to(splash, SpriteAccessor.ALPHA, 2f).target(1)
                .start(tweenManager);
        // fade back out after delaying for 3 seconds
        Tween.to(splash, SpriteAccessor.ALPHA, 3f).target(0).delay(2f)
                .setCallback(new TweenCallback() {

                    @Override
                    public void onEvent(int arg0, BaseTween<?> arg1) {
                        ((Game)Gdx.app.getApplicationListener()).setScreen(new MainMenu());
                    }

        }).start(tweenManager);
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
        splash.getTexture().dispose();
    }

}
