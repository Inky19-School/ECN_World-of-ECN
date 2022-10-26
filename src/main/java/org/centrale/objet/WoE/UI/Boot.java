/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Game;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import java.io.File;
import org.centrale.objet.WoE.Joueur;
import org.centrale.objet.WoE.Point2D;
import org.centrale.objet.WoE.SaveManager;
import org.centrale.objet.WoE.World.World;
/**
 *
 * @author inky19
 */
public class Boot extends Game {
    
    private SpriteBatch batch;
    public GameScreen gScreen;
    private MainMenu menu;
    public World monde;
    public WorldCreation settings;
    public WorldSelection worldSelection;
 
    public Boot(){
        super();     
    }
    
    
    public Boot(World m){
        super();
        monde = m;
    }
    
    @Override
    public void create(){
        batch = new SpriteBatch();
        menu = new MainMenu(this);
        settings = new WorldCreation(this);
        worldSelection = new WorldSelection(this);
        setScreen(menu);
    }
    
    public void goToMenu() {
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
    
    public void createNewWorld(int size, Joueur player, String name) {
        monde = new World(size,player,name);
        monde.creerMondeAlea();
        SaveManager.saveWorld(monde);
        gScreen = new GameScreen(batch, monde, this);
        setScreen(gScreen);
    }
    
    public void loadWorld(File file) {
        World monde = SaveManager.loadWorld(file);
        gScreen = new GameScreen(batch, monde, this);
        setScreen(gScreen);
    }
}
