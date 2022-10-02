/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.Objet.*;

/**
 *
 * @author inky19
 */
public class InfoBox {
    
    private static Texture heart = new Texture(Gdx.files.internal("data/textures/ui/heart.png"));
    private static Texture shield = new Texture(Gdx.files.internal("data/textures/ui/shield.png"));
    private static Texture shield_luck = new Texture(Gdx.files.internal("data/textures/ui/shield_luck.png"));
    private static Texture sword = new Texture(Gdx.files.internal("data/textures/ui/sword.png"));
    private static Texture sword_luck = new Texture(Gdx.files.internal("data/textures/ui/sword_luck.png"));
    private static Texture bow = new Texture(Gdx.files.internal("data/textures/ui/bow.png"));
    private static Texture arrow = new Texture(Gdx.files.internal("data/textures/ui/arrow.png"));
    
    private Texture texture;
    private ArrayList<ArrayList<Texture>> icons;
    private ArrayList<ArrayList<String>> infos;
    private String name;
    private String className;
    private int height;

    public InfoBox(){
        texture = null;
        icons = new ArrayList<>();
        infos = new ArrayList<>();
        name = "";
        className = null;
        height = 0;
        
    }
    
    private void addElement(Texture t1, String info1){
        addElement(t1, info1, null, null);
    }
    
    private void addElement(Texture t1, String info1, Texture t2, String info2){
        height += InfoMenu.LINE_SIZE;
        ArrayList<Texture> ico = new ArrayList<>();
        ArrayList<String> str = new ArrayList<>();
        
        ico.add(t1);
        str.add(info1);
        if (t2 != null && info2 != null){
            ico.add(t2);
            str.add(info2);
        }
        icons.add(ico);
        infos.add(str);
    }
    
    private void initUpdate(Entite e){
        icons = new ArrayList<>();
        infos = new ArrayList<>();
        texture = EntityInfo.getTexture(e);
        height = texture.getHeight()*2;
        className = EntityInfo.getClassName(e);
    }
    
    public void update(Creature c){
        initUpdate(c);
        addElement(heart, String.valueOf(c.getPtVie()));
        addElement(sword, String.valueOf(c.getDegAtt()), sword_luck, String.valueOf(c.getPageAtt())+"%");
        addElement(shield, String.valueOf(c.getPtPar()), shield_luck, String.valueOf(c.getPagePar())+"%");
        if (c instanceof Personnage){
            name = ((Personnage) c).getNom();
            if (name != ""){
                height += 40;
            }

            if (c instanceof Archer){
                addElement(bow, String.valueOf(((Archer) c).getDegAttDist()), arrow, String.valueOf(((Archer) c).getNbFleches()));
            }
        }
    }
    
    public void update(Objet o){
        initUpdate(o);
        if (o instanceof PotionSoin){
            addElement(heart, String.valueOf(((PotionSoin) o).getPtVieRegen()));
        }
    }

    public Texture getTexture() {
        return texture;
    }

    public void setTexture(Texture texture) {
        this.texture = texture;
    }

    public int getHeight() {
        return height;
    }

    public ArrayList<ArrayList<Texture>> getIcons() {
        return icons;
    }

    public ArrayList<ArrayList<String>> getInfos() {
        return infos;
    }

    public String getClassName() {
        return className;
    }

    

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }
    
    
    
}
