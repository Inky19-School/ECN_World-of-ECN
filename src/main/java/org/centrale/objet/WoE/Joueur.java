/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.centrale.objet.WoE.Action.Combattant;
import org.centrale.objet.WoE.Action.Deplacable;
import org.centrale.objet.WoE.Creature.*;

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
    
    public Joueur(){
        name = "";
        player = new Guerrier();
        
    }
    
    public Joueur(String pClass, String name){
        this.name = name;
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
    
    public Personnage chooseClass() throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException{
        Personnage p = null;
        String choix;
        Scanner sc;
        sc = new Scanner(System.in);
        System.out.println("Entrer un nom :");
        String name = sc.next();
        System.out.println(" ");
        boolean invalide = true;
        while (invalide){
            System.out.print("Bonjour, entrez un nom de classe parmi :");
            for (Class c:JOUABLES){
                System.out.print(c.getSimpleName()+ " ");
            }
            System.out.println("");
            choix = sc.next();
            for (Class c:JOUABLES){
                if (choix.equals(c.getSimpleName())){
                    p = (Personnage) (c.getDeclaredConstructor().newInstance());
                    invalide = false;
                }
            }
        }
        p.setPos(new Point2D(2,2));
        p.setNom(name);
        player = p;
        System.out.println(player.getNom());
        return p;
    }
    
    public void deplacer(World monde){
        System.out.println(player.getNom());

        System.out.println(dx + " " + dy);
        Point2D pos = player.getPos();
        try {
            if (monde.mapCreature[(pos.getX()+dx)][(pos.getY()+dy)] == null){
                System.out.println("B");
                monde.mapCreature[(pos.getX()+dx)][(pos.getY()+dy)] = player;
                monde.mapCreature[(pos.getX())][(pos.getY())] = null;
                player.setPos(new Point2D(pos.getX()+dx, pos.getY()+dy));
                monde.interactionObjet(this.player);
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
    
    
    
    public void combattre(Creature c){
        if (player instanceof Combattant){
            ((Combattant)(player)).combattre(c);
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
    
}
