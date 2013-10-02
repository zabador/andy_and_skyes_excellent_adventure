package com.zabador.game.screen;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;
import java.nio.file.Files;
import java.util.ArrayList;

import aurelienribon.tweenengine.BaseTween;
import aurelienribon.tweenengine.Tween;
import aurelienribon.tweenengine.TweenCallback;
import aurelienribon.tweenengine.TweenManager;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Input.Keys;
import com.badlogic.gdx.InputProcessor;
import com.badlogic.gdx.Preferences;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.audio.Music;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.Sprite;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.maps.tiled.TiledMap;
import com.badlogic.gdx.maps.tiled.TiledMapTileLayer;
import com.badlogic.gdx.maps.tiled.TmxMapLoader;
import com.badlogic.gdx.maps.tiled.renderers.OrthogonalTiledMapRenderer;
import com.badlogic.gdx.math.MathUtils;
import com.badlogic.gdx.utils.Base64Coder;
import com.zabador.game.StartBattle;
import com.zabador.game.entities.Enemy;
import com.zabador.game.entities.Player;
import com.zabador.game.entities.Warp;
import com.zabador.game.tween.ScreenAccessor;
import com.zabador.game.tween.SpriteAccessor;
import com.zabador.game.ui.MainUi;

import org.json.JSONArray;
import org.json.JSONObject;

public class Play implements Screen, StartBattle, InputProcessor{

    private TiledMap map;
    private OrthogonalTiledMapRenderer renderer;
    private OrthographicCamera camera;
    private TiledMapTileLayer collisionLayer;
    private Player player;
    private MainUi mainui;
    private Preferences prefs;
    private boolean loading = false; // for when user is loading a saved game
    private Music music;
    private ArrayList<Enemy> enemies;
    private TweenManager tweenManager; // used to fade the splash screen
    public boolean collisionX = false, collisionY = false;
    private boolean inBattle;
    private String mapName;
    private ArrayList<Warp> portals;
    private int mapX = 0;
    private int mapY = 0;
    private String mapFile;
    private Warp warpObject;

    JSONObject jsonObject;
    JSONArray jsonArray;

    //returning from battle scene
    public Play(String mapName, Player player) {
        this.player = player;
        this.mapName = mapName;
    }

    // loading a saved game
    public Play(Preferences prefs) {
        this.prefs = prefs;
        this.mapName =  new String(Base64Coder.decodeString(prefs.getString("map")));
        loading = true;
    }

    // new game
    public Play(String mapName) {
        this.mapName = mapName;
    }

    // warping
    public Play(Warp warp) {
        this.mapName = warp.getName();
        this.mapX = warp.getMapX();
        this.mapY = warp.getMapY();
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        tweenManager.update(delta);

        renderer.setView(camera);
        renderer.render();

        // -100 becuase of the size of the character ui screen at the botton
        camera.position.set(player.getX() + player.getWidth() / 2,
                (player.getY() + player.getHeight() / 2) - 100, 0);
        camera.update();

        player.update(delta);
        checkforBattle();
        checkForCollisions();

        renderer.getSpriteBatch().begin();
        if(!inBattle)
            player.draw(renderer.getSpriteBatch());
        renderer.getSpriteBatch().end();
        mainui.draw();
    }

    private void checkforBattle() {
        if(player.getStepsToEncounter() <= 0){
            inBattle = true;
            player.up = player.down = player.left = player.right = false;
            player.velocity.x = 0;
            player.velocity.y = 0;
            player.setStepsToEncounter(MathUtils.random(player.LOWBOUNDSTEPS, player.HIGHBOUNTSTEPS));
            goToBattle();
        }

    }

    private void checkForCollisions() {
        float oldX = player.getX(), oldY = player.getY();
        float tileWidth = collisionLayer.getTileWidth(), tileHeight = collisionLayer.getTileHeight();
        boolean warp = false;
        String portalName = null;

        if(player.velocity.x < 0) {

            try {
                collisionX = collisionLayer.getCell((int)(player.getX()/tileWidth), (int)((player.getY()+player.getHeight()/2)/tileHeight))
                    .getTile().getProperties().containsKey("blocked");
            }catch(NullPointerException npe) { // player is off map
                collisionX = true;
            }

            // loop through portals to see if we need to warp
            if(!warp){
                for (int i = 0;i < portals.size();i++ ) {
                    try {
                        if(collisionLayer.getCell((int)(player.getX()/tileWidth), (int)((player.getY()+player.getHeight()/2)/tileHeight))
                                .getTile().getProperties().containsKey(portals.get(i).getName())) {
                            warp = true;
                            warpObject = portals.get(i);
                            break;
                                }
                    }catch(NullPointerException npe) {
                        collisionX = true;
                    }
                }
            }

        }else if(player.velocity.x > 0) {
            try {
                collisionX = collisionLayer.getCell((int)((player.getX() + player.getWidth())/tileWidth), (int)((player.getY()+player.getHeight()/2)/tileHeight))
                    .getTile().getProperties().containsKey("blocked");      
            }catch(NullPointerException npe) { // player is off map
                collisionX = true;
            }

            // loop through portals to see if we need to warp
            if(!warp){
                for (int i = 0;i < portals.size();i++ ) {
                    try {
                        if(collisionLayer.getCell((int)((player.getX() + player.getWidth())/tileWidth), (int)((player.getY()+player.getHeight()/2)/tileHeight))
                                .getTile().getProperties().containsKey(portals.get(i).getName())){
                            warp = true;
                            warpObject = portals.get(i);
                            break;
                                }
                    }catch(NullPointerException npe) { // player is off map
                        collisionX = true;
                    }
                }
            }
        }

        if(collisionX) {
            player.setX(oldX);
            player.velocity.x = 0;
        }

        if(player.velocity.y < 0) {
            try {
                collisionY = collisionLayer.getCell((int)((player.getX() + player.getWidth()/2) / tileWidth), (int) (player.getY() / tileHeight))
                    .getTile().getProperties().containsKey("blocked");
            }catch(NullPointerException npe) { // player is off map
                collisionY = true;
            }

            // loop through portals to see if we need to warp
            if(!warp){
                for (int i = 0;i < portals.size();i++ ) {
                    try {
                        if(collisionLayer.getCell((int)((player.getX() + player.getWidth()/2) / tileWidth), (int) (player.getY() / tileHeight))
                                .getTile().getProperties().containsKey(portals.get(i).getName())) {
                            warp = true;
                            warpObject = portals.get(i);
                            break;
                                }
                    }catch(NullPointerException npe) { // player is off map
                        collisionY = true;
                    }
                }
            }

        }else if(player.velocity.y > 0) {
            try {
                collisionY = collisionLayer.getCell((int)((player.getX() + player.getWidth()/2) / tileWidth), (int) ((player.getY() + player.getHeight()) / tileHeight))
                    .getTile().getProperties().containsKey("blocked");
            }catch(NullPointerException npe) { // player is off map
            }

            // loop through portals to see if we need to warp
            if(!warp){
                for (int i = 0;i < portals.size();i++ ) {
                    try {
                        if(collisionLayer.getCell((int)((player.getX() + player.getWidth()/2) / tileWidth), (int) ((player.getY() + player.getHeight()) / tileHeight))
                                .getTile().getProperties().containsKey(portals.get(i).getName())) {
                            warp = true;
                            warpObject = portals.get(i);
                            break;
                                }
                    }catch(NullPointerException npe) { // player is off map
                        collisionY = true;
                    }
                }
            }

        }

        if(collisionY) {
            player.setY(oldY);
            player.velocity.y = 0;
        }

        if(warp) {
            ((Game)Gdx.app.getApplicationListener()).setScreen(new Play(warpObject));
        }
    }

    public void goToBattle() {
        System.out.println("go to battle was called");
        Tween.set(this, ScreenAccessor.ALPHA).target(1).start(tweenManager);
        System.out.println("after set");
        Tween.to(this, ScreenAccessor.ALPHA, 1f).target(0).delay(0)
            .setCallback(new TweenCallback() {
                @Override
                public void onEvent(int type, BaseTween<?> source) {
                    System.out.println("in onevent");
                    int i = MathUtils.random(enemies.size() - 1);
                    ((Game) Gdx.app.getApplicationListener())
                .setScreen(new Battle(mapName, player, enemies.get(i)));
                }

            }).start(tweenManager);

        System.out.println("after tween");
    }

    public SpriteBatch getSpriteBatch() {
        return renderer.getSpriteBatch();
    }
    @Override
    public void resize(int width, int height) {

        camera.viewportHeight = height;
        camera.viewportWidth = width;
        camera.update();
    }
    @Override
    public void show() {

        enemies = new ArrayList<Enemy>();
        portals = new ArrayList<Warp>();

        tweenManager = new TweenManager();
        Tween.registerAccessor(Play.class, new ScreenAccessor());

        // build the array of enemies to pass to the player
        try {
            JSONObject monstersFeed = new JSONObject(Gdx.files.internal("feeds/monster.json").readString());
            JSONArray monsterArray = monstersFeed.getJSONArray("monsters");
            for (int i = 0; i<monsterArray.length(); i++ ) {
                JSONObject monster = monsterArray.getJSONObject(i);
                enemies.add(new Enemy(monster.getString("name"), 
                            monster.getInt("level"), 
                            monster.getInt("attack"),
                            monster.getInt("defense"),
                            monster.getInt("exp"),
                            monster.getInt("hp")));
            }
        }catch(Exception e){System.out.println("Error getting feed " + e.getStackTrace());}

        // build the array of portals on the map
        try {
            JSONObject mapObject = new JSONObject();
            JSONObject mapsFeed = new JSONObject(Gdx.files.internal("feeds/maps.json").readString());
            JSONArray mapsArray = mapsFeed.getJSONArray("maps");
            for (int i = 0; i<mapsArray.length(); i++ ) {
                if(mapsArray.getJSONObject(i).getString("name").equals(mapName)) {
                    mapObject = mapsArray.getJSONObject(i);
                    break;
                }
            }
            mapFile = mapObject.getString("file");
            if(mapX == 0 && mapY == 0) {
                mapX = mapObject.getInt("X");
                mapY = mapObject.getInt("Y");
            }
            JSONArray portalArray = mapObject.getJSONArray("portals");
            for(int i = 0;i<portalArray.length();i++) {
                JSONObject warp = portalArray.getJSONObject(i);
                portals.add(new Warp(warp.getString("name"),
                            warp.getInt("mapX"),
                            warp.getInt("mapY")));

            }
        }catch(Exception e){System.out.println("Error getting feed " + e.getStackTrace());}

        map = new TmxMapLoader().load(mapFile);
        collisionLayer = (TiledMapTileLayer) map.getLayers().get(0);

        renderer = new OrthogonalTiledMapRenderer(map);

        camera = new OrthographicCamera();
        //camera.zoom = .5f;

        if(player == null){ // it is a brand new game
            player = new Player(new Sprite(new Texture("imgs/player.png")));
            if(loading) { // load player from saved preferences
                player.setPosition(Float.parseFloat(
                            new String(Base64Coder.decodeString(prefs
                                    .getString("playerX")))), Float.parseFloat(
                            new String(Base64Coder.decodeString(prefs
                                    .getString("playerY")))));
            }else {
                player.setPosition(mapX * collisionLayer.getTileWidth(), (collisionLayer.getHeight()-mapY) * collisionLayer.getTileHeight());

            }
        }
        else {// player has returned from a battle scene
            inBattle = false;
            player.setPosition(player.getX(),player.getY());
        }

        // tell game where the input processor is
        Gdx.input.setInputProcessor(this);

        music = Gdx.audio.newMusic(Gdx.files.internal("sound/Zelda_Theme.mp3"));
        music.setLooping(true);
        music.play();

        mainui = new MainUi();

    }

    @Override
    public void hide() {
        music.stop();
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
        music.dispose();
    }

    @Override
    public boolean keyDown(int keycode) {
        if(!inBattle) {
            switch (keycode) {
                case Keys.W:
                case Keys.UP:
                    player.up = true;
                    player.velocity.y = player.getSpeed();
                    break;
                case Keys.S:
                case Keys.DOWN:
                    player.down = true;
                    player.velocity.y = -player.getSpeed();
                    break;
                case Keys.A:
                case Keys.LEFT:
                    player.left = true;
                    player.velocity.x = -player.getSpeed();
                    break;
                case Keys.D:
                case Keys.RIGHT:
                    player.right = true;
                    player.velocity.x = player.getSpeed();
                    break;
                case Keys.ESCAPE:
                    ((Game)Gdx.app.getApplicationListener()).setScreen(new SaveScreen(mapName,player));
                    break;

                default:
                    break;
            }

        }
        return true;
    }

    @Override
    public boolean keyUp(int keycode) {
        switch (keycode) {
            case Keys.W:
            case Keys.UP:
                player.velocity.y = 0;
                player.up = false;
                break;
            case Keys.S:
            case Keys.DOWN:
                player.down = false;
                player.velocity.y = 0;
                break;
            case Keys.A:
            case Keys.LEFT:
                player.velocity.x = 0;
                player.left = false;
                break;
            case Keys.D:
            case Keys.RIGHT:
                player.right = false;
                player.velocity.x = 0;
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
        if(!inBattle) {
            if(screenX < 200) {
                player.velocity.x = -player.getSpeed();
                player.left = true;
            }
            else if(screenX > Gdx.graphics.getWidth()-200){
                player.velocity.x = player.getSpeed();
                player.right = true;
            }
            else if(screenY < 200) {
                player.velocity.y = player.getSpeed();
                player.up = true;
            }
            else if(screenY > Gdx.graphics.getHeight()-200){
                player.velocity.y = -player.getSpeed();
                player.down = true;
            }

        }
        return true;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        player.velocity.x = 0;
        player.velocity.y = 0;
        player.left = false;
        player.right = false;
        player.up = false;
        player.down = false;

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
