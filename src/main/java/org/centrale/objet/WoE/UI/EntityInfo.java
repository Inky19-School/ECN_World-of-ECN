/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.Objet.*;

/**
 *
 * @author François MARIE et Rémi RAVELLI
 */
public class EntityInfo {
    
    private static final Texture wolf = new Texture(Gdx.files.internal("data/textures/entity/monster/wolf.png"));;
    static private final Texture paysan = new Texture(Gdx.files.internal("data/textures/entity/personnage/paysan.png"));
    static private final Texture archer = new Texture(Gdx.files.internal("data/textures/entity/personnage/archer.png"));
    static private final Texture guerrier = new Texture(Gdx.files.internal("data/textures/entity/personnage/guerrier.png"));
    static private final Texture lapin = new Texture(Gdx.files.internal("data/textures/entity/monster/lapin.png"));
    
    static private final Texture healthPotion = new Texture(Gdx.files.internal("data/textures/entity/object/healPotion.png"));
    static private final Texture superMushroom = new Texture(Gdx.files.internal("data/textures/entity/object/super_mushroom.png"));
    static private final Texture toxicMushroom = new Texture(Gdx.files.internal("data/textures/entity/object/toxic_mushroom.png"));
    static private final Texture toxicCloud = new Texture(Gdx.files.internal("data/textures/entity/object/toxicCloud.png"));    
    

    /**
     * Renvoie la texture correspondante d'une entité
     * @param e
     * @return Texture de l'entité
     */
    public static Texture getTexture(Entite e){
        if (e instanceof Creature ){
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
        } else if (e instanceof Objet){
            if (e instanceof PotionSoin){
                return healthPotion;
            } else if (e instanceof NuageToxique){
                return toxicCloud;
            } else if (e instanceof SuperMushroom){
                return superMushroom;
            } else if (e instanceof ToxicMushroom){
                return toxicMushroom;
            }
        }
        return null;
    }
        
    /**
     * Renvoie le nom de la classe d'une entité
     * @param e
     * @return Nom de la classe 
     */
    public static String getClassName(Entite e){
        if (e instanceof Creature ){
            if (e instanceof Loup){
                return "Loup";
            } else if (e instanceof Archer){
                return "Archer";
            } else if (e instanceof Guerrier){
                return "Guerrier";
            } else if (e instanceof Paysan){
                return "Paysan";
            } else if (e instanceof Lapin){
                return "Lapin";
            }
        } else if (e instanceof Objet){
            if (e instanceof PotionSoin){
                return "Potion de soin";
            }
            if (e instanceof NuageToxique){
                return "Nuage Toxique";
            }
            if (e instanceof SuperMushroom){
                return "Super Champi";
            }
            if (e instanceof ToxicMushroom){
                return "Champi Toxique";
            }
            
        }
        return null;
    }
}
