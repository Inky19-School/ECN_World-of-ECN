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
import com.badlogic.gdx.scenes.scene2d.ui.ScrollPane;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton.TextButtonStyle;
import com.badlogic.gdx.scenes.scene2d.ui.VerticalGroup;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import com.badlogic.gdx.utils.viewport.ScreenViewport;
import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.nio.file.Path;
import java.nio.file.attribute.BasicFileAttributes;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.Set;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.centrale.objet.WoE.SaveManager;

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
    private final SpriteBatch sb;

    public WorldSelection(Boot game) {
        this.game = game;
        this.sb = new SpriteBatch();
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
        openButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent event, Actor actor) {
                game.loadWorld(selected.getFile());
            }
        });
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
        File[] folders = SaveManager.getWorlds();
        int n = folders.length;
        WorldSelectionListItem[] listItems = new WorldSelectionListItem[n];

        for (int i = 0; i < n; i++) {
            String fileName = folders[i].getName();
            Path file = (Path) Paths.get("save/" + fileName);
            String name = fileName.split("_", 2)[0];
            String fileSize = "error";
            String lastModified = "error";
            String mode = "solo";
            try {
                Path folder = Paths.get(folders[i].getAbsolutePath());
                long bytes = Files.walk(folder).filter(p -> p.toFile().isFile()).mapToLong(p -> p.toFile().length()).sum();
                fileSize = String.format("%d o", bytes);
                BasicFileAttributes attr = Files.readAttributes(file, BasicFileAttributes.class);
                // Last modified time
                Date date = new Date(attr.lastModifiedTime().toMillis());
                String pattern = "dd/MM/yyyy HH:mm";
                SimpleDateFormat simpleDateFormat = new SimpleDateFormat(pattern);
                lastModified = simpleDateFormat.format(date);
            } catch (IOException ex) {
                ex.printStackTrace();
            }

            listItems[i] = new WorldSelectionListItem(this, folders[i], skin, name, lastModified, fileSize, mode);

        }
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
        sb.begin();
        sb.draw(MainMenu.background,0,0,Gdx.graphics.getWidth(),Gdx.graphics.getHeight());
        sb.end();
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
