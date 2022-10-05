/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE;

import java.lang.reflect.InvocationTargetException;
import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.Objet.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;


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
     * Matrice des Créatures pour un accès rapide à partir d'une position
     */
    public Entite[][] mapCreature;// Matrice des entités à leur position
    
    /**
     * Matrice des objets pour un accès rapide à partir d'une position
     */
    public Objet[][] mapObjets;
    
    private Joueur joueur;
    
    
    /**
     * Taille du monde.
     */
    public int SIZE; 
    
    /**
     * Liste des objets sur la carte.
     * (Doit être remplacée à terme)
     */
    private ArrayList<Objet> ObjetsMap;

    /**
     *  Reste là car devenu la mascotte du débogage 🐺 
     */
    private Joueur player;
    
    
    public Loup wolfie;

    public World() {
        joueur = new Joueur();
        entites = new LinkedList<>();
        mapCreature = new Entite[SIZE][SIZE];
        mapObjets = new Objet[SIZE][SIZE];
        wolfie = new Loup();
        player = new Joueur();
        try {
            ajoutCreature(player.chooseClass());
        } catch(Exception e) {   
        }
        //mapCreatures[0][0] = wolfie;
    }
    
    public World(int size, Joueur p) {
        SIZE = size;
        entites = new LinkedList<>();
        mapCreature = new Entite[SIZE][SIZE];
        mapObjets = new Objet[SIZE][SIZE];
        wolfie = new Loup();
        wolfie.setPos(new Point2D(3,4));
        entites.add(wolfie);
        this.player = p;
        entites.add(player.getPlayer());
        mapCreature[player.getPlayer().getPos().getX()][player.getPlayer().getPos().getY()]  = player.getPlayer();
        //mapCreatures[0][0] = wolfie;
    } 
    
    
    
    
    
    public void creerPlayer(Joueur player, int size) {
        
    }
    
    public void ajoutCreature(Creature c){
        Point2D posC = c.getPos();
        mapCreature[posC.getX()][posC.getY()] = c;
        entites.add(c);
    }
    
    /**
     * Crée un monde avec des positions de départ aléatoires et distinctes
     */
    public void creerMondeAlea(){
        
        
        // Générateur de nombre aléatoire
        Random alea = new Random();
        int rand;
        int max = SIZE;
        // Génération d'un nombre de créatures aléatoires
        rand = alea.nextInt(max); 
        System.out.println("Nb Archer :" + rand);
        for (int i=0; i<rand; i++){
            entites.add(new Archer());
        }
        rand = alea.nextInt(max); 
        System.out.println("Nb Paysan :" + rand);
        for (int i=0; i<rand; i++){
            entites.add(new Paysan());
        }
        
        rand = alea.nextInt(max); 
        System.out.println("Nb Lapin :" + rand);
        for (int i=0; i<rand; i++){
            entites.add(new Lapin());
        }
        
        rand = alea.nextInt(max); 
        System.out.println("Nb Guerrier :" + rand);
        for (int i=0; i<rand; i++){
            entites.add(new Guerrier());
        }
        rand = alea.nextInt(max); 
        System.out.println("Nb Loup :" + rand);
        for (int i=0; i<rand; i++){
            entites.add(new Loup());
        }
        
        rand = alea.nextInt(max);
        System.out.println("Nb Potion de soin :" + rand);
        for (int i = 0; i < rand; i++) {
            entites.add(new PotionSoin());
        }
        System.out.println("Nb total de personnage : " + entites.size());
        
        /*
        Personnage pJ = null;
        try {
            pJ = joueur.chooseClass();
        } catch (InstantiationException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalAccessException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (NoSuchMethodException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (IllegalArgumentException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        } catch (InvocationTargetException ex) {
            Logger.getLogger(World.class.getName()).log(Level.SEVERE, null, ex);
        }
        */
        // Placement aléatoire des entités
        boolean notValide;
        for (Entite p: entites){
            notValide = true;
            while (notValide){
                // Tirage d'une position aléatoire sur la carte
                int x = alea.nextInt(SIZE);
                int y = alea.nextInt(SIZE);
                // Vérification que la case est libre
                if ((p instanceof Creature)&&(mapCreature[x][y] == null)) {
                    p.setPos(new Point2D(x, y));
                    mapCreature[x][y] = (Creature) p;
                    notValide = false;
                }
                if ((p instanceof Objet)&&(mapObjets[x][y] == null)) {
                    p.setPos(new Point2D(x, y));
                    mapObjets[x][y] = (Objet) p;
                    notValide = false;
                }
            }
        }
        
        /*
        
        System.out.println(pJ.getClass().getName());
        ajoutCreature(pJ);
        System.out.println(mapCreature[2][2].getClass().getName());
        System.out.println(pJ.getPos().getX() + " " + pJ.getPos().getY());
        System.out.println(entites.get(entites.size()-1).getClass().getName());
        */
        PotionSoin p = new PotionSoin(new Point2D(1,1), 1, 6);
        entites.add(p);
        mapObjets[1][1] = p;
        ajoutCreature(new Loup(new Point2D(1,1), 20, 20, 20, 20, 20));
        //Loup w = ;
        //entites.add(w);
        //mapCreature[1][1] = w;
        
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
    public void interactionObjet(Creature creature) {
        int x = creature.getPos().getX();
        int y = creature.getPos().getY();
        if (mapObjets[x][y] != null) {
            mapObjets[x][y].interagir(creature);
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

    public Joueur getJoueur() {
        return player;
    }
    
    
}
