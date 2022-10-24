/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.scenes.scene2d.Event;
import com.badlogic.gdx.scenes.scene2d.InputEvent;
import com.badlogic.gdx.scenes.scene2d.ui.Button;
import com.badlogic.gdx.scenes.scene2d.ui.Label;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.utils.ClickListener;
import com.badlogic.gdx.scenes.scene2d.utils.FocusListener;
import java.io.File;
import java.nio.file.Path;

/**
 *
 * @author Rémi
 */
public class WorldSelectionListItem extends Button {

    private final String worldName;
    private final File file;

    public WorldSelectionListItem(WorldSelection selectScreen, File file, Skin skin, String worldName, String date, String size, String mode) {
        super(skin, "toggle");
        this.worldName = worldName;
        this.file = file;
        //Layout
        this.center().row();
        this.add(new Label(file.getName(), skin)).left();

        this.add(new Label(date, skin, "small-font", Color.GRAY)).right().expandX().row();
        this.add(new Label(mode, skin, "small-font", Color.ROYAL)).left();
        this.add(new Label(size, skin, "small-font", Color.GRAY)).right().row();

        this.addListener(new ClickListener() {
            @Override
            public void clicked(InputEvent event, float x, float y) {
                selectScreen.setSelection(WorldSelectionListItem.this);
            }
        });

    }

    public void unselect() {
        WorldSelectionListItem.this.setColor(Color.WHITE);
        this.setChecked(false);
    }
    
    public File getFile() {
        return file;
    }

}
