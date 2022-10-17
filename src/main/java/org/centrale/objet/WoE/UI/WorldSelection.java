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
import com.badlogic.gdx.scenes.scene2d.Touchable;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;

/**
 *
 * @author Rémi
 */
public class WorldSelection implements Screen {
    private static final Skin skin = new Skin(Gdx.files.internal("data/gui/uiskin.json"));
    private static final Skin customSkin = new Skin(Gdx.files.internal("data/gui/customui.json"));
    private final Boot game;
    private final Stage stage;
    private WorldSelectionListItem selected;
    private final TextButton openButton;

    public WorldSelection(Boot game) {
        this.game = game;
        stage = new Stage(new ScreenViewport());
        Table mainLayout = new Table();
        Container c1 = new Container();
        TextureAtlas buttonAtlas = new TextureAtlas(Gdx.files.internal("data/textures/ui/button.atlas"));
        Skin skin2 = new Skin(buttonAtlas);
        TextButtonStyle textButtonStyle = new TextButtonStyle();
        textButtonStyle.font = new BitmapFont();
        textButtonStyle.up = skin2.getDrawable("blue_button");
        textButtonStyle.over = skin2.getDrawable("blue_button_selected");
        textButtonStyle.disabledFontColor = Color.GRAY;
        
        stage.addActor(c1);
        stage.addActor(mainLayout);

        TextButton backButton = new TextButton("Back", skin);
        openButton = new TextButton("Open", textButtonStyle);
        openButton.setDisabled(true);
        openButton.setColor(Color.GRAY);
        openButton.setTouchable(Touchable.disabled);

        backButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.goToMenu();
            }
        });
        c1.setActor(backButton);
        c1.setFillParent(true);
        c1.align(Align.topLeft).pad(20);
        mainLayout.setFillParent(true);
        mainLayout.add(new Label("Select world", skin)).pad(20).row();
        Table list = new Table();
        VerticalGroup v = new VerticalGroup();
        
        //Item Creation
        int size = 900;
        v.setWidth(size);
        WorldSelectionListItem[] items = loadWorlds();
        for (WorldSelectionListItem item : items) {
            list.add(item).width(size).row();
        }

        ScrollPane s = new ScrollPane(list, skin);
        s.setScrollbarsOnTop(false);
        s.setFadeScrollBars(false);
        s.setScrollingDisabled(true, false);
        s.setOverscroll(false, false);     

        mainLayout.add(s).width(size + 30).height(400).center().row();
        mainLayout.add(openButton).pad(40);

    }
    
    /**
     * loadWorlds from files
     *
     * @return list of worlds
     */
    
    public WorldSelectionListItem[] loadWorlds() {     
        WorldSelectionListItem b1 = new WorldSelectionListItem(this, skin, "MyWorld1", "01/01/2022");
        WorldSelectionListItem b2 = new WorldSelectionListItem(this, skin, "MyPrettyWorld2", "01/01/2022");
        WorldSelectionListItem b3 = new WorldSelectionListItem(this, skin, "JYM le best", "02/05/2222");
        WorldSelectionListItem b4 = new WorldSelectionListItem(this, skin, "Not Minecraft Related", "01/01/2022");
        WorldSelectionListItem b5 = new WorldSelectionListItem(this, skin, "MyWorld1 Again", "01/01/2022");
        WorldSelectionListItem b6 = new WorldSelectionListItem(this, skin, "Stop","01/01/2022");
        WorldSelectionListItem b7 = new WorldSelectionListItem(this, skin, "Oups America", "11/09/2001");
        WorldSelectionListItem b8 = new WorldSelectionListItem(this, skin, "This is too long and this needs to stop", "01/01/2022");
        WorldSelectionListItem[] listItems = new WorldSelectionListItem[] {b1,b2,b3,b4,b5,b6,b7,b8};
        return listItems;
    }
    
    
    
    @Override
    public void show() {
        Gdx.input.setInputProcessor(stage);
    }

    @Override
    public void render(float f) {
        Gdx.gl.glClearColor(0f, 0f, 0f, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        // tell our stage to do actions and draw itself
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
    }
    
    public void setSelection(WorldSelectionListItem b) {
        if (selected != null) {
           selected.unselect();
        }
        if (selected != b) {
            selected = b;
            openButton.setDisabled(false);
            openButton.setTouchable(Touchable.enabled);
            openButton.setColor(Color.WHITE);
        } else {
            openButton.setDisabled(true);
            openButton.setColor(Color.GRAY);
            openButton.setTouchable(Touchable.disabled);
            selected = null;
        }
        
    }
    
    
    
    
    @Override
    public void resize(int width, int height) {
        stage.getViewport().update(width, height, true);
    }

    @Override
    public void pause() {

    }

    @Override
    public void resume() {

    }

    @Override
    public void hide() {

    }

    @Override
    public void dispose() {
        stage.dispose();
    }

}
