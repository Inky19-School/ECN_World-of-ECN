/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import org.centrale.objet.WoE.World;
/**
 *
 * @author inky19
 */
public class Boot extends Game {
    
    private SpriteBatch batch;
    public GameScreen gScreen;
    private MainMenu menu;
    private World monde;
    
    public Boot(World m){
        super();
        monde = m;
    }
    
    @Override
    public void create(){
        batch = new SpriteBatch();
        menu = new MainMenu(this);
        gScreen = new GameScreen(batch, monde);
        //setScreen(gScreen);
        setScreen(menu);
    }
    
    @Override
    public void render(){
        super.render();
    }
    
    @Override
    public void dispose(){
        batch.dispose();
        super.dispose();
    }
}
