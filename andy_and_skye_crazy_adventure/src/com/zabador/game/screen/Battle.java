package com.zabador.game.screen;

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
import com.zabador.game.entities.Enemy;
import com.zabador.game.entities.Player;

public class Battle implements Screen {

    private Stage stage;
    private Skin skin;
    private TextureAtlas atlas;
    private Table table;
    private TextButton buttonExit;
    private Label heading, monsterName, monsterLevel, monsterAttack, monsterDefense, monsterExp, monsterHP;
    private BitmapFont white, black;
	private Player player;
    private Enemy enemy;
	private String mapName;

	public Battle(String mapName, Player player, Enemy enemy) {
		System.out.println("it made it to battle");
		this.mapName = mapName;
		this.player = player;
        this.enemy = enemy;
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

        buttonExit = new TextButton("EXIT", textButtonStyle);
        buttonExit.pad(20);
        buttonExit.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
				((Game)Gdx.app.getApplicationListener()).setScreen(new Play(mapName, player));
            }
        });

        LabelStyle headingStyle = new LabelStyle(white, Color.WHITE);

        heading = new Label("Battle screen", headingStyle); heading.setFontScale(2);
        monsterName = new Label("Name = " + enemy.getName(), headingStyle); heading.setFontScale(2);
        monsterLevel = new Label("Level = " + new Integer(enemy.getLevel()).toString(), headingStyle); heading.setFontScale(2);
        monsterAttack = new Label("Attack = " + new Integer(enemy.getAttack()).toString(), headingStyle); heading.setFontScale(2);
        monsterDefense = new Label("Defense = " + new Integer(enemy.getDefense()).toString(), headingStyle); heading.setFontScale(2);
        monsterExp = new Label("EXP = " + new Integer(enemy.getExp()).toString(), headingStyle); heading.setFontScale(2);
        monsterHP = new Label("HP = " + new Integer(enemy.getHp()).toString(), headingStyle); heading.setFontScale(2);

        table.debug(); //TODO delete when done debugging menu design
        table.add(heading);
        table.row();
        table.add(monsterName);
        table.row();
        table.add(monsterLevel);
        table.row();
        table.add(monsterAttack);
        table.row();
        table.add(monsterDefense);
        table.row();
        table.add(monsterExp);
        table.row();
        table.add(monsterHP);
        table.row();
        table.add(buttonExit);

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
