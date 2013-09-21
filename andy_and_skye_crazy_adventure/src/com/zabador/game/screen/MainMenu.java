package com.zabador.game.screen;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;

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
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        stage.act(delta);
        Table.drawDebug(stage); // TODO delete when done debugging menu layout
        stage.draw();

    }

    @Override
    public void show() {

        stage = new Stage();

        Gdx.input.setInputProcessor(stage); // listen for the button presses

        //create area to draw the buttons
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"));
        black = new BitmapFont(Gdx.files.internal("fonts/black.fnt"));

        table = new Table(skin);
        // table will fill whole screen
        table.setBounds(0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());

        // define the button styles for up and down
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");
        textButtonStyle.pressedOffsetX = 1; // move the text on the button down
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        buttonExit = new TextButton("EXIT", textButtonStyle);
        buttonExit.pad(20);

        buttonPlay = new TextButton("PLAY", textButtonStyle);
        buttonPlay.pad(20);

        table.debug(); //TODO delete when done debugging menu design
        table.add(buttonPlay);
        table.add(buttonExit);

        stage.addActor(table);

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
