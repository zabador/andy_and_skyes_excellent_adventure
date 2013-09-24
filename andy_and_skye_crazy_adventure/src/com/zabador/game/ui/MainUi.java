package com.zabador.game.ui;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Label.LabelStyle;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;

public class MainUi {

    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private Table table;
    private Label heading;
    private BitmapFont white, black;

    public void draw() {

        stage.act();
        Table.drawDebug(stage); // TODO delete when done debugging menu layout
        stage.draw();

    }

    public MainUi() {

        stage = new Stage();

        //create area to draw the buttons
        atlas = new TextureAtlas("ui/button.pack");
        skin = new Skin(atlas);

        white = new BitmapFont(Gdx.files.internal("fonts/white.fnt"));
        black = new BitmapFont(Gdx.files.internal("fonts/black.fnt"));

        table = new Table(skin);
        // table will fill whole screen
        table.setBounds(0, 0, Gdx.graphics.getWidth(), 200);

        LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);

        heading = new Label("This will be the main UI for the characters", headingStyle);
        heading.setFontScale(2);

        table.debug(); //TODO delete when done debugging menu design
        table.add(heading);
        table.row();

        stage.addActor(table); 
    }
}
