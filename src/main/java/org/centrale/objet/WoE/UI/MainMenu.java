/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Image;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 *
 * @author Rémi
 */
public class MainMenu implements Screen {
    protected Stage stage;
    private final Boot game;
    public final static Texture background = new Texture(Gdx.files.internal("data/gui/menuBackground.jpg"));
    private final SpriteBatch sb;
    //private final SpriteBatch batch;

    public MainMenu(Boot game)
    {
        this.game = game;
        this.sb = new SpriteBatch();
    }
    
    @Override
    public void show() {
	Skin skin = new Skin();
        //batch = new SpriteBatch();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("data/textures/ui/button.atlas"));
        skin.addRegions(buttonAtlas);
        stage = new Stage(new ScreenViewport());
        Gdx.input.setInputProcessor(stage);
        Texture img = new Texture(Gdx.files.internal("data/gui/icon3.png"));	
        Table table = new Table();
	table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);

        //Skin skin = new Skin(Gdx.files.internal("skin/glassy-ui.json"));
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.up = skin.getDrawable("blue_button");
        textButtonStyle.over = skin.getDrawable("blue_button_selected");
        //create buttons and image
        Image logo = new Image(img);
        TextButton newGame = new TextButton("New Game",textButtonStyle);
        TextButton openGame = new TextButton("Open Game", textButtonStyle);
        TextButton exit = new TextButton("Exit", textButtonStyle);

        //add buttons and image to table
        table.add(logo).padBottom(50);
        table.row();
        table.add(newGame).pad(20);
	table.row();
        table.add(openGame);
        table.row();
	table.add(exit).pad(20);
        
	// create button listeners
	exit.addListener(new ChangeListener() {
	@Override
	public void changed(ChangeEvent event, Actor actor) {
            Gdx.app.exit();				
	}});
		
	newGame.addListener(new ChangeListener() {
	@Override
	public void changed(ChangeEvent event, Actor actor) {
		game.setScreen(game.settings);
        }
	});
        
        openGame.addListener(new ChangeListener() {
	@Override
	public void changed(ChangeListener.ChangeEvent event, Actor actor) {
		game.setScreen(game.worldSelection);
        }
	});
		
    }

    @Override
    public void render(float f) {
	Gdx.gl.glClearColor(0f, 0f, 0f, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
		
        // tell our stage to do actions and draw itself
	stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
	sb.begin();
        sb.draw(MainMenu.background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        sb.end();
        stage.draw();
    }

    @Override
    public void resize(int width, int height) {
	stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void resume() {
        //throw new UnsupportedOperationException("Not supported yet."); 
    }

    @Override
    public void hide() {
        //throw new UnsupportedOperationException("Not supported yet.");
    }

    @Override
    public void dispose() {
        stage.dispose();
    }
    
}
