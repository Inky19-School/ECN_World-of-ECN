/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE;

import org.centrale.objet.WoE.World.World;
import java.lang.reflect.InvocationTargetException;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.centrale.objet.WoE.Action.Combattant;
import org.centrale.objet.WoE.Action.Deplacable;
import org.centrale.objet.WoE.Action.Effect;
import org.centrale.objet.WoE.Action.HasEffect;
import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.Objet.Objet;
import org.centrale.objet.WoE.Objet.Utilisable;
import org.centrale.objet.WoE.UI.EntityInfo;
import org.centrale.objet.WoE.World.Chunk;

/**
 *
 * @author inky19
 */
public class Joueur {

    public static final Class[] JOUABLES = new Class[]{Guerrier.class, Archer.class};
    
    private Personnage player;
    private String name;
    
    private int dx;
    private int dy;
    private LinkedList<Effect> effects;
    
    private LinkedList<Objet> inventaire;
    
    public Joueur(){
        name = "";
        player = new Guerrier();
        
    }
    
    public Joueur(Personnage p) {
        name = p.getNom();
        player = p;
        effects = new LinkedList<>();
    }
    
    
    
    public Joueur(String pClass, String name){
        inventaire = new LinkedList<Objet>();
        this.name = name;
        this.effects = new LinkedList<>();
        for (Class c:JOUABLES){
                if (pClass.equals(c.getSimpleName())){
                    try {
                        try {
                            player = (Personnage) (c.getDeclaredConstructor().newInstance());
                        } catch (NoSuchMethodException ex) {
                            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
                        } catch (SecurityException ex) {
                            Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
                        }
                    } catch (InstantiationException ex) {
                        Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalAccessException ex) {
                        Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (IllegalArgumentException ex) {
                        Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
                    } catch (InvocationTargetException ex) {
                        Logger.getLogger(Joueur.class.getName()).log(Level.SEVERE, null, ex);
                    }
                }
            }
        player.setNom(this.name);
    }
    
    public void updateEffects() {
        for (Effect effect : effects) {
            effect.decreaseDuration();
            if (effect.isFinished()) {
                effects.remove(effect);
            } else {
                if (effect.getEffect() == Effect.HP) {
                    this.getPlayer().addPV(effect.getModifier());
                }
            }
        }
    }
    
    
    
    public void deplacer(World monde){
        Point2D pos = player.getPos();
        Point2D oldChPos = player.getChPos();
        try {
            Point2D newPos = new Point2D((pos.getX()+player.getChPos().getX()*Chunk.SIZE+dx),(pos.getY()+player.getChPos().getY()*Chunk.SIZE+dy));
            if (monde.getCrea(newPos.getX(), newPos.getY()) == null){
                monde.setEnt(newPos.getX(), newPos.getY(),player);
                monde.setEnt(pos.getX()+oldChPos.getX()*Chunk.SIZE, pos.getY()+oldChPos.getY()*Chunk.SIZE,null);
                //player.setPos(new Point2D(pos.getX()+dx, pos.getY()+dy));
                //monde.interactionObjet(this.player);
            }   
        } catch (ArrayIndexOutOfBoundsException ex) {
            System.out.println("Invalide Position");
        }
        

    }

    public String getName() {
        return name;
    }

    
    
    public Personnage getPlayer() {
        return player;
    }
    
    
    
    public boolean combattre(Creature c){
        if (player instanceof Combattant){
            return ((Combattant)(player)).combattre(c);
        }

        return false;
    }
    
    public boolean useMap(Objet o, World monde){
        if (o.getPos().equals(player.getPos()) && o instanceof Utilisable){
            inventaire.add(o);
            monde.delEnt(o);
            return true;
        }
        return false;
    }
    
    public void useInventory(){
        System.out.println("Choississez un objet à utiliser :");
        int i = 0;
        for (Objet o: inventaire){
            System.out.println(""+i+" : " + EntityInfo.getClassName(o));
        }
        System.out.print("Numéro obj : ");
        Scanner sc = new Scanner(System.in);
        if (sc.hasNextInt()){
            int ind = sc.nextInt();
            if (ind > 0 && ind < inventaire.size()){
                Objet o = inventaire.get(ind);
                if (o instanceof HasEffect){
                effects.add(((HasEffect) o).getEffect());
                }
                inventaire.remove(o);
            }
        }


    }

    public int getDx() {
        return dx;
    }

    public void setDx(int dx) {
        this.dx = dx;
    }

    public int getDy() {
        return dy;
    }

    public void setDy(int dy) {
        this.dy = dy;
    }
    
    public void addDx(int dx){
        this.dx += dx;
        if (this.dx>1){
            this.dx = 1;
        } else if (this.dx<-1){
            this.dx = -1;
        }
    }
    
    public void addDy(int dy){
        this.dy += dy;
        if (this.dy>1){
            this.dy = 1;
        } else if (this.dy<-1){
            this.dy = -1;
        }
    }

    public LinkedList<Effect> getEffects() {
        return effects;
    }

    public LinkedList<Objet> getInventaire() {
        return inventaire;
    }
    
    
}
