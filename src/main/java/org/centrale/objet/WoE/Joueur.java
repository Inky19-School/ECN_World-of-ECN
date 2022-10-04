/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

import java.lang.reflect.InvocationTargetException;
import java.util.Scanner;
import org.centrale.objet.WoE.Action.Deplacable;
import org.centrale.objet.WoE.Creature.Archer;
import org.centrale.objet.WoE.Creature.Guerrier;
import org.centrale.objet.WoE.Creature.Personnage;
import org.centrale.objet.WoE.UI.PlayerInput;

/**
 *
 * @author Rémi
 */
public class Joueur implements Deplacable {
    public static final Class[] JOUABLES = new Class[] {Archer.class, Guerrier.class};
    public Personnage character;
    private String name;
    private World monde;
    
    public Joueur(World monde) {
        character = null;
        this.monde = monde; 
    }
    
    public Joueur(String type, String name) {
        if (type.equals("Archer")) {
            character = new Archer();
        }
        else {
            character = new Guerrier();
        }
        character.setNom(name);
        character.setPos(new Point2D(4,4));
    }
    
    public Joueur(String name, Personnage player) {
        this.name = name;
        this.character = player;
    }
    
    public Personnage chooseClass() throws InstantiationException, IllegalAccessException, NoSuchMethodException, IllegalArgumentException, InvocationTargetException {
        int input = -1;
        int i;
        Scanner sc = new Scanner(System.in);
        while (!((0<=input)&&(input<JOUABLES.length))) {
            System.out.println("Choisir personnage parmi :");
            i = 0;
            for (Class c : JOUABLES) {
                System.out.println(i + " : " + c.getSimpleName()+" ");
                i++;
            }
            input = Integer.parseInt(sc.nextLine());
        }
        //player = (Personnage) JOUABLES[input].getConstructor().newInstance();
        character = new Archer();
        character.setNom("Me");
        character.setPos(new Point2D(0,1));
        return this.character;
    }

    @Override
    public void deplacer(World monde) {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

}
