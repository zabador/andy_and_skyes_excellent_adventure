package com.zabador.game.screen;

import java.nio.ByteBuffer;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Preferences;
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
import com.badlogic.gdx.utils.Base64Coder;
import com.zabador.game.entities.Player;

public class SaveScreen implements Screen {

    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private Table table;
    private TextButton buttonCANCEL, buttonSave;
    private Label heading;
    private BitmapFont white, black;
	private Player player;

	public SaveScreen(Player player) {
		this.player = player;
	}

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
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
        table.setBounds(0, 0, Gdx.graphics.getWidth(), Gdx.graphics.getHeight());

        // define the button styles for up and down
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.up = skin.getDrawable("up");
        textButtonStyle.down = skin.getDrawable("down");
        textButtonStyle.pressedOffsetX = 1; // move the text on the button down
        textButtonStyle.pressedOffsetY = -1;
        textButtonStyle.font = black;

        buttonCANCEL = new TextButton("CANCEL", textButtonStyle);
        buttonCANCEL.pad(20);
        buttonCANCEL.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new Play(player));
            }
        });

        buttonSave = new TextButton("SAVE and EXIT", textButtonStyle);
        buttonSave.pad(20);
        buttonSave.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
				Preferences prefs = Gdx.app.getPreferences("SaveState");
				prefs.putString("playerX",Base64Coder.encodeString(Float.toString(player.getX())));
				prefs.putString("playerY",Base64Coder.encodeString(Float.toString(player.getY())));
				prefs.flush(); Gdx.app.exit();
            }
        });

        LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);

        heading = new Label("Battle sceen", headingStyle); heading.setFontScale(2);

        table.debug(); //TODO delete when done debugging menu design
        table.add(heading);
        table.row();
		table.add(buttonSave);
        table.add(buttonCANCEL);

        stage.addActor(table);

    }

    @Override
    public void resize(int width, int height) {

    }
    @Override
    public void hide() {
		this.dispose();

    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void dispose() {
		skin.dispose();
		atlas.dispose();
		stage.dispose();
    }
}
