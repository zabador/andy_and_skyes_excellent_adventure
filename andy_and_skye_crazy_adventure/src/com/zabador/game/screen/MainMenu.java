package com.zabador.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;

public class MainMenu implements Screen {

    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private Table table;
    private TextButton buttonPlay, buttonExit;
    private Label heading;
    private BitmapFont white,black;

    @Override
    public void render(float delta) {

    }

    @Override
    public void show() {

        //create area to draw the buttons
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"));
        black = new BitmapFont(Gdx.files.internal("fonts/black.fnt"));
    }

    @Override
    public void resize(int width, int height) {

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

    }
}
