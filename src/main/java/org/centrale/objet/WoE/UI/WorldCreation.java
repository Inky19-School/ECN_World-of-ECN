/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.Screen;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.SelectBox;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Slider;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextField;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import org.centrale.objet.WoE.Joueur;

/**
 *
 * @author Rémi
 */
public class WorldCreation implements Screen {
    protected Stage stage;
    private final Skin skin;
    private final Boot game;
    
    private final Slider widthSlider;
    private final Slider heightSlider;
    private final Label widthLabel;
    private final Label heightLabel;
    private final TextureAtlas buttonAtlas;
    private final Skin skin2;
    private final SelectBox selectBox;
    private final TextField playerNameField;
    //private final TextureAtlas buttonAtlas;
    //private final SpriteBatch batch;

    public WorldCreation(Boot game)
    {
        this.game = game;
        skin = new Skin(Gdx.files.internal("data/gui/uiskin.json"));
        stage = new Stage(new ScreenViewport());
                
        //Slider
        widthSlider = new Slider(5, 100, 1, false, skin);
        heightSlider = new Slider(5, 100, 1, false, skin);
        //Label
        widthLabel = new Label("0",skin);
        heightLabel = new Label("0",skin);
        
        buttonAtlas = new TextureAtlas(Gdx.files.internal("data/textures/ui/button.atlas"));
        skin2 = new Skin(buttonAtlas);
        selectBox = new SelectBox(skin);
        playerNameField = new TextField("", skin);
    }

    
    
    
    @Override
    public void show() {
	Gdx.input.setInputProcessor(stage);	
        Table table = new Table();
	table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
        
        //World     
        table.add(new Label("Select world settings :",skin)).colspan(3).row();
        
        table.add(new Label("Size : ",skin));
        table.add(widthSlider).pad(10);
        table.add(widthLabel).width(50).row();
        
        //table.add(new Label("Height : ",skin));
        //table.add(heightSlider).pad(20);
        //table.add(heightLabel).width(100).row();
        
        //Player
        table.add(new Label("Select player settings : ", skin)).colspan(3).row();
        
        
        selectBox.setAlignment(Align.center);
        selectBox.getList().setAlignment(Align.center);
        selectBox.getStyle().listStyle.selection.setRightWidth(10);
        selectBox.getStyle().listStyle.selection.setLeftWidth(20);
        selectBox.addListener(new ChangeListener() {
        public void changed (ChangeEvent event, Actor actor) {
                 System.out.println(selectBox.getSelected());}});
        String[] items = new String[2];
        for (int i=0; i < Joueur.JOUABLES.length; i++) {
            items[i] = Joueur.JOUABLES[i].getSimpleName();
        }
        selectBox.setItems(items);
        
        
        TextButton.TextButtonStyle textButtonStyle = new TextButton.TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.up = skin2.getDrawable("blue_button");
        textButtonStyle.over = skin2.getDrawable("blue_button_selected");
        
        TextButton createButton = new TextButton("Create World",textButtonStyle);
        createButton.setColor(Color.BLUE);
        
        table.add(new Label("Name :",skin));
        table.add(playerNameField).width(200);
        table.add(selectBox).width(170).pad(20).row();
        table.add(createButton).colspan(3).pad(20);
        
        createButton.addListener(new ChangeListener() {
            
	@Override
	public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            Joueur player = new Joueur((String) selectBox.getSelected(),playerNameField.getText());
            game.createNewWorld((int) widthSlider.getValue(),player);
            //game.setScreen(game.gScreen);
        }
	});

    }

    @Override
    public void render(float f) {
	Gdx.gl.glClearColor(0f, 0f, 0f, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	//update sliders label;
        widthLabel.setText((int) widthSlider.getValue());
        heightLabel.setText((int) heightSlider.getValue());
        // tell our stage to do actions and draw itself
	stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
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
