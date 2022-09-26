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
   /**
    * Liste des entités
    */
    public List<Entite> entites; 
    /**
     * Matrice des entités pour un accès rapide à partir d'une position
     */
    public Entite[][] mapEntites;// Matrice des entités à leur position
    
    /**
     * Taille du monde.
     */
    public final int SIZE = 15; 
    
    /**
     * Liste des objets sur la carte.
     * (Doit être remplacée à terme)
     */
    private ArrayList<Objet> ObjetsMap;

    /**
     *  Reste là car devenu la mascotte du débogage 🐺 
     */
    public Loup wolfie;

    public World() {
        entites = new LinkedList<>();
        mapEntites = new Entite[SIZE][SIZE];
        
        wolfie = new Loup();
        mapEntites[0][0] = wolfie; 

    }
    
    /**
     * Crée un monde avec des positions de départ aléatoires et distinctes
     */
    public void creerMondeAlea(){
        
        
        // Générateur de nombre aléatoire
        Random alea = new Random();
        int rand = 0;
        
        long timer = System.currentTimeMillis();
        
        // Génération d'un nombre de créatures aléatoires
        rand = alea.nextInt(10); 
        System.out.println("Nb Archer :" + rand);
        for (int i=0; i<rand; i++){
            entites.add(new Archer());
        }
        rand = alea.nextInt(10); 
        System.out.println("Nb Paysan :" + rand);
        for (int i=0; i<rand; i++){
            entites.add(new Paysan());
        }
        
        rand = alea.nextInt(10); 
        System.out.println("Nb Lapin :" + rand);
        for (int i=0; i<rand; i++){
            entites.add(new Lapin());
        }
        
        rand = alea.nextInt(10); 
        System.out.println("Nb Guerrier :" + rand);
        for (int i=0; i<rand; i++){
            entites.add(new Guerrier());
        }
        rand = alea.nextInt(10); 
        System.out.println("Nb Loup :" + rand);
        for (int i=0; i<rand; i++){
            entites.add(new Loup());
        }
        
        // Calcul du nombre total de points de vie
        int PVtotal = 0;
        for(int i=0;i<entites.size();i++){
            PVtotal += ((Creature) (entites.get(i))).getPtVie();
        }
        System.out.println("Nb total de personnage : " + entites.size());
        long fin = System.currentTimeMillis();
        System.out.println("Temps d'exécution : " + (fin-timer) +"ms");
        System.out.println("Somme des PVs : " + PVtotal);
        
        // Placement aléatoire des entités
        boolean notValide;
        for (Entite p: entites){
            notValide = true;
            while (notValide){
                // Tirage d'une position aléatoire sur la carte
                int x = alea.nextInt(SIZE);
                int y = alea.nextInt(SIZE);
                // Vérification que la case est libre
                if (mapEntites[x][y] == null){
                   p.setPos(new Point2D(x,y));
                   mapEntites[x][y]=p;
                   notValide = false;
                }
            }
        }     
    }
    
    /**
     * Non implémentée
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
     * Non implémentée
     */
    public void afficheWorld(){
        
    }
}
