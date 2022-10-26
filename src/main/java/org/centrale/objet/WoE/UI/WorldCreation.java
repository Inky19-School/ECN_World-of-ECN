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
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.g2d.TextureAtlas;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
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
import org.centrale.objet.WoE.SaveManager;

/**
 *
 * @author Rémi
 */
public class WorldCreation implements Screen {

    protected Stage stage;
    private final Boot game;
    private final Table table;
    private final Container cont;
    private static final int MAXLENGTH = 30;
    private final Slider sizeSlider;
    private final Label sizeLabel;
    private final SpriteBatch sb;
    private final TextField worldNameField; 
    private final TextButton createButton;
    private final TextField playerNameField;

    public WorldCreation(Boot game) {
        this.game = game;
        this.sb = new SpriteBatch();
        stage = new Stage(new ScreenViewport());
        Skin skin = new Skin(Gdx.files.internal("data/gui/uiskin.json"));
        //Slider
        sizeSlider = new Slider(5, 100, 1, false, skin);
        //Label
        sizeLabel = new Label("0", skin);

        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("data/textures/ui/button.atlas"));
        Skin skin2 = new Skin(buttonAtlas);
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.up = skin2.getDrawable("blue_button");
        textButtonStyle.over = skin2.getDrawable("blue_button_selected");
        textButtonStyle.disabledFontColor = Color.GRAY;
        SelectBox<String> selectBox = new SelectBox<>(skin, "small");
        worldNameField = new TextField("", skin);
        worldNameField.setMaxLength(MAXLENGTH);
        playerNameField = new TextField("", skin);
        playerNameField.setMaxLength(MAXLENGTH);

        table = new Table();
        table.setFillParent(true);
        table.setDebug(false);
        cont = new Container();
        cont.setFillParent(true);
        cont.align(Align.topLeft);
        cont.pad(20);
        stage.addActor(cont);
        stage.addActor(table);
        //SelectBox
        selectBox.setAlignment(Align.left);
        selectBox.getList().setAlignment(Align.left);
        selectBox.getStyle().listStyle.selection.setRightWidth(10);
        selectBox.getStyle().listStyle.selection.setLeftWidth(20);
        String[] items = new String[2];
        for (int i = 0; i < Joueur.JOUABLES.length; i++) {
            items[i] = Joueur.JOUABLES[i].getSimpleName();
        }
        selectBox.setItems(items);
        //Buttons      
        createButton = new TextButton("Create New World", textButtonStyle);
        
        TextButton backButton = new TextButton("Back", skin);
        createButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                String playerType = selectBox.getSelected();
                String worldName = worldNameField.getText().replaceAll("_"," ");
                String playerName = playerNameField.getText().replaceAll("_"," ");
                int size = (int) sizeSlider.getValue();

                Joueur player = new Joueur(playerType, playerName);
                game.createNewWorld(size , player, worldName);
            }
        });
        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.goToMenu();
            }
        });
        /* Layout */
        //Back button
        cont.setActor(backButton);
        //World     
        table.add(new Label("World settings", skin, "default-font", Color.WHITE)).colspan(3).pad(40).row();
        table.add(new Label("Name ", skin, "small-font", Color.WHITE)).padBottom(20).left();
        table.add(worldNameField).width(300).height(40).colspan(2).right().padBottom(20).row();
        /*
        table.add(new Label("Size ", skin, "small-font", Color.WHITE)).left();
        table.add(sizeSlider).fill();
        table.add(sizeLabel).width(50).right().row();
        */
        //Player
        table.add(new Label("Player settings", skin)).colspan(3).pad(40).row();

        table.add(new Label("Name ", skin, "small-font", Color.WHITE)).padBottom(20).left();
        table.add(playerNameField).width(300).height(40).colspan(2).right().padBottom(20).row();

        table.add(new Label("Type ", skin, "small-font", Color.WHITE)).left();
        table.add(selectBox).width(170).height(40).colspan(2).right().row();

        table.add(createButton).colspan(3).pad(60).bottom();
    }

    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        //update sliders label;
        sizeLabel.setText((int) sizeSlider.getValue());
        sb.begin();
        sb.draw(MainMenu.background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        sb.end();
        // tell our stage to do actions and draw itself
        if (worldNameField.getText().equals("") || playerNameField.getText().equals("")) {
            createButton.setDisabled(true);
            createButton.setTouchable(Touchable.disabled);
            createButton.setColor(Color.GRAY);
        } else {
            createButton.setDisabled(false);
            createButton.setTouchable(Touchable.enabled);
            createButton.setColor(Color.WHITE);
        }
        
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
