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
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
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
    
    private final Slider sizeSlider;
    private final Label sizeLabel;
    private final TextureAtlas buttonAtlas;
    private final Skin skin2;
    private final SelectBox selectBox;
    private final TextField playerNameField;
    private TextButtonStyle textButtonStyle;
    //private final TextureAtlas buttonAtlas;
    //private final SpriteBatch batch;

    public WorldCreation(Boot game)
    {
        this.game = game;
        skin = new Skin(Gdx.files.internal("data/gui/uiskin.json"));
        stage = new Stage(new ScreenViewport());
                
        //Slider
        sizeSlider = new Slider(5, 100, 1, false, skin);
        //Label
        sizeLabel = new Label("0",skin);
        
        buttonAtlas = new TextureAtlas(Gdx.files.internal("data/textures/ui/button.atlas"));
        skin2 = new Skin(buttonAtlas);
        textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.up = skin2.getDrawable("blue_button");
        textButtonStyle.over = skin2.getDrawable("blue_button_selected");
        selectBox = new SelectBox(skin,"small");
        playerNameField = new TextField("", skin);
        playerNameField.setMaxLength(16);
    }

    
    
    
    @Override
    public void show() {
	Gdx.input.setInputProcessor(stage);	
        Table table = new Table();
	table.setFillParent(true);
        table.setDebug(false);
        stage.addActor(table);
        
        //World     
        table.add(new Label("World settings",skin,"default-font",Color.WHITE)).colspan(3).pad(40).row();
        
        table.add(new Label("Size ",skin,"small-font",Color.WHITE)).left();
        table.add(sizeSlider).fill();
        table.add(sizeLabel).width(50).right().row();
        
        //Player
        table.add(new Label("Player settings", skin)).colspan(3).pad(40).row();
        
        selectBox.setAlignment(Align.left);
        selectBox.getList().setAlignment(Align.left);
        selectBox.getStyle().listStyle.selection.setRightWidth(10);
        selectBox.getStyle().listStyle.selection.setLeftWidth(20);
        String[] items = new String[2];
        for (int i=0; i < Joueur.JOUABLES.length; i++) {
            items[i] = Joueur.JOUABLES[i].getSimpleName();
        }
        selectBox.setItems(items);
               
        TextButton createButton = new TextButton("Create New World",textButtonStyle);
        createButton.addListener(new ChangeListener() {
	@Override
	public void changed(ChangeListener.ChangeEvent event, Actor actor) {
            Joueur player = new Joueur((String) selectBox.getSelected(),playerNameField.getText());
            game.createNewWorld((int) sizeSlider.getValue(),player);
            //game.setScreen(game.gScreen);
        }
	});
        
        
        table.add(new Label("Name ",skin,"small-font",Color.WHITE)).padBottom(20).left();
        table.add(playerNameField).width(250).height(40).colspan(2).right().padBottom(20).row();
 
        table.add(new Label("Type ",skin,"small-font",Color.WHITE)).left();
        table.add(selectBox).width(170).height(40).colspan(2).right().row();
        
        table.add(createButton).colspan(3).pad(60).bottom();
        

    }

    @Override
    public void render(float f) {
	Gdx.gl.glClearColor(0f, 0f, 0f, 1);
	Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
	//update sliders label;
        sizeLabel.setText((int) sizeSlider.getValue());
        
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
        skin.dispose();
        stage.dispose();
    }
        
}
