/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE;

import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.Objet.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;


/**
 *
 * @author inky19
 */
public class World {
    /**
     * Objets sur la carte
     */
   
    public List<Entite> entites;
    private Entite[][] mapEntites;
    private int size = 50;
    
    
    private ArrayList<Objet> ObjetsMap;

    public Loup wolfie;

    public World() {
        entites = new LinkedList<>();
        mapEntites = new Entite[size][size];
        
        wolfie = new Loup();

    }
    
    /**
     * Crée un monde avec des positions de départ aléatoires et distinctes
     */
    public void creerMondeAlea(){
        
        
        
        Random alea = new Random();
        int size = 10;
        int rand = 0;
        long timer = System.currentTimeMillis();
        
        for (int i=0; i<alea.nextInt(10); i++){
            entites.add(new Archer());
        }
        rand = alea.nextInt(10); 
        for (int i=0; i<rand; i++){
            System.out.println("Nb Paysan :" + rand);
            entites.add(new Paysan());
        }
        for (int i=0; i<alea.nextInt(10); i++){
            entites.add(new Lapin());
        }
        for (int i=0; i<alea.nextInt(10); i++){
            entites.add(new Guerrier());
        }
        rand = alea.nextInt(10); 
        for (int i=0; i<rand; i++){
            System.out.println("Nb Loup :" + rand);
            entites.add(new Loup());
        }
        int PVtotal = 0;
        for(Entite p: entites){
            if (p instanceof Creature){
                PVtotal += ((Creature) (p)).getPtVie();
            }
        }
        
        System.out.println("Somme des PVs : " + PVtotal);
        
       boolean notValide = true;
        for (Entite p: entites){
            notValide = true;
            while (notValide){
                int x = alea.nextInt(size);
                int y = alea.nextInt(size);
                if (mapEntites[x][y] == null){
                   p.setPos(new Point2D(x,y));
                   mapEntites[x][y]=p;
                   notValide = false;
                }
            }
        }
        
        /**
        for (int i=0; i < entites.size(); i++){
            notValide = true; // Indique si l'emplacement est occupé (true = occupé)
            while (notValide){
                int x = alea.nextInt(size);
                int y = alea.nextInt(size);
                Point2D point = new Point2D(x, y);
                entites.get(i).setPos(point);
                notValide = false;
                for (int j=0; j < i; j++){ // Parcours la liste des entités déjà générées
                    if (entites.get(i).getPos().equals(entites.get(j).getPos()))
                        notValide = true;
                }
            }
        }
        */
        long fin = System.currentTimeMillis();
        System.out.println("Temps d'exécution : " + (fin-timer) +"ms");

        
    }
    
    /**
     *
     */
    public void tourDeJeu(){
        
    }
    
    /**
     * Vérifie si une créature peut intéragir avec un objet sur la carte, et le supprime après l'interaction.
     * @param creature Créature avec laquelle intéragir
     */
    public void interactionObjet(Creature creature){

        for (int i=0; i<ObjetsMap.size(); i++){
            if (ObjetsMap.get(i).getPos().equals(creature.getPos())){
                ObjetsMap.get(i).interagir(creature);
                ObjetsMap.remove(i);
            }
        }
    }
    
    /**
     * Affiche la liste des objets présents sur la carte
     */
    public void afficheObjetMap(){
        System.out.println("OBJETS SUR LA CARTE :");
        for (int i=0; i<ObjetsMap.size(); i++){
            ObjetsMap.get(i).affiche();
        }
    }
    
    /**
     *
     */
    public void afficheWorld(){
        
    }
}