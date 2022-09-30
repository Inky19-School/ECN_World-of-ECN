/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.centrale.objet.WoE.Creature.*;

/**
 *
 * @author inky19
 */
public class EntityTexture {
    
    private static final Texture wolf = new Texture(Gdx.files.internal("data/textures/entity/monster/wolf.png"));;
    static private final Texture paysan = new Texture(Gdx.files.internal("data/textures/entity/personnage/paysan.png"));
    static private final Texture archer = new Texture(Gdx.files.internal("data/textures/entity/personnage/archer.png"));
    static private final Texture guerrier = new Texture(Gdx.files.internal("data/textures/entity/personnage/guerrier.png"));
    static private final Texture lapin = new Texture(Gdx.files.internal("data/textures/entity/monster/lapin.png"));
    
    
    void EntityTexture()
    {
         

    }

    
    static Texture getTexture(Entite e){
        if (e instanceof Loup){
            return wolf;
        } else if (e instanceof Archer){
            return archer;
        } else if (e instanceof Guerrier){
            return guerrier;
        } else if (e instanceof Paysan){
            return paysan;
        } else if (e instanceof Lapin){
            return lapin;
        }
        return null;
    }
}
