/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.EventListener;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.Table;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import org.centrale.objet.WoE.SaveManager;

/**
 *
 * @author Rémi
 */
public class InGameMenu extends Table {
    private static final Skin skin = new Skin(Gdx.files.internal("data/gui/uiskin.json"));
    private final static int WIDTH = 350;
    /**
     *
     * @param game
     * @param gScreen
     */
    public InGameMenu(Boot game, GameScreen gScreen) {
        super(skin);
        TextButton backToGameButton = new TextButton("Back to game", skin);
        backToGameButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                gScreen.resume();
            }
        });
        TextButton quitButton = new TextButton("Save and quit", skin);
        quitButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                SaveManager.saveWorld(gScreen.getMonde());
                gScreen.dispose();
                game.goToMenu();
            }
        });
        TextButton settingsButton = new TextButton("Settings", skin);
        settingsButton.addListener(new ChangeListener() {
            @Override
            public void changed(ChangeListener.ChangeEvent ce, Actor actor) {
                // NOTHING YET
            }
        });
        // Layout
        setFillParent(true);
        add(backToGameButton).width(WIDTH).row();
        add(settingsButton).padTop(20).width(WIDTH).row();
        add(quitButton).padTop(20).width(WIDTH).row();
    }
    
    
    
}
