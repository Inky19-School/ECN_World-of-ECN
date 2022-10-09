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

/**
 *
 * @author Rémi
 */
public class WorldSelectionListItem extends Button{
    private final WorldSelection selectScreen;
    private final String worldName;
    
    
    public WorldSelectionListItem(WorldSelection selectScreen, Skin skin,String worldName,String date) {
        super(skin,"toggle");
        this.selectScreen = selectScreen;
        this.worldName = worldName;
        //Layout
        this.center().row();
        this.add(new Label(worldName,skin)).left();
        
        
        this.add(new Label(date,skin,"small-font",Color.GRAY)).right().expandX().row();
        this.add(new Label("Survival",skin,"small-font",Color.ROYAL)).left();
        this.add(new Label("3.6MB",skin,"small-font",Color.GRAY)).right().row();
        
        this.addListener(new ClickListener() {     
	@Override
	public void clicked(InputEvent event, float x , float y) {
            selectScreen.setSelection(WorldSelectionListItem.this);
        }});
        
        
        
    }
    
    public void unselect() {
        WorldSelectionListItem.this.setColor(Color.WHITE);
        this.setChecked(false);
    }
    

}
