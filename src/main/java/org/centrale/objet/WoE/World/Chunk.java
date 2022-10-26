/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.World;

import java.util.LinkedList;
import java.util.List;
import java.util.Random;
import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.Objet.*;
import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author François MARIE et Rémi RAVELLI
 */
public class Chunk {
    
    // Position du chunk
    private Point2D pos;

    // Taille d'un chunk
    public static final int SIZE = 32;
    
    // Liste des entités présentes dans le chunk
    public List<Entite> entites;
    
    //Des matrices sont utilisées pour mieux représenter le problème et accèder aux entités voisines facilement.
    /**
     * Matrice des cases du chunk
     */
    public Tile[][] chTile;
    
    /**
     * Matrice des Créature (contient des pointeurs de entites)
     */
    public Creature[][] chCrea;
    
    /**
     * Matrice des Créature (contient des pointeurs de entites)
     */
    public Objet[][] chObj;
    
    /**
     *
     * @param chx Coordonnées x du chunk
     * @param chy Coordonnées y du chunk
     */
    public Chunk(int chx, int chy){
        chTile = new Tile[SIZE][SIZE];
        chCrea = new Creature[SIZE][SIZE];
        chObj = new Objet[SIZE][SIZE];
        entites = new LinkedList<Entite>();
        pos = new Point2D(chx,chy);
    }
    
     /**
     * Génère un chunk aléatoire avec des créatures.
     * @param chx Coordonnées x du chunk
     * @param chy Coordonnées y du chunk
     * @return Renvoie un chunk avec des entités générées aléatoirement
     */
    public static Chunk chunkAlea(int chx, int chy){
        
        Chunk ch = new Chunk(chx, chy);
        
        for (int i=0; i<SIZE;i++){
            for (int j=0; j<SIZE; j++){
                ch.chTile[i][j] = new Tile(1,0);
            }
        }
        
        
        int maxType = SIZE/8;
        
        // Générateur de nombre aléatoire
        Random alea = new Random();
        int rand;
            
        
        // Génération d'un nombre de créatures aléatoires
        
        // Archer
        rand = alea.nextInt(maxType); 
        for (int i=0; i<1; i++){
            ch.entites.add(new Archer());
        }
        
        // Paysan
        rand = alea.nextInt(maxType); 
        for (int i=0; i<rand; i++){
            ch.entites.add(new Paysan());
        }
        
        // Lapin
        rand = alea.nextInt(maxType); 
        for (int i=0; i<rand; i++){
            ch.entites.add(new Lapin());
        }
        
        // Guerrier
        rand = alea.nextInt(maxType); 
        for (int i=0; i<rand; i++){
            ch.entites.add(new Guerrier());
        }
        
        // Loup
        rand = alea.nextInt(maxType); 
        for (int i=0; i<rand; i++){
            ch.entites.add(new Loup());
        }
        
        // Potion de vie
        rand = alea.nextInt(maxType/2);
        for (int i = 0; i < rand; i++) {
            ch.entites.add(new PotionSoin());
        }
        
        // Nuage toxique
        rand = alea.nextInt(maxType/2);
        for (int i = 0; i < rand; i++) {
            ch.entites.add(new NuageToxique());
        }
        
        // Super Champignon
        rand = alea.nextInt(maxType);
        for (int i = 0; i < rand; i++) {
            ch.entites.add(new SuperMushroom());
        }
        
        // Champignon Toxique
        rand = alea.nextInt(maxType);
        for (int i = 0; i < rand; i++) {
            ch.entites.add(new ToxicMushroom());
        }
        
        // Placement aléatoire des entités
        boolean notValide;
        for (Entite p: ch.entites){
            notValide = true;
            while (notValide){
                // Tirage d'une position aléatoire sur la carte
                int x = alea.nextInt(SIZE);
                int y = alea.nextInt(SIZE);
                p.setChPos(ch.getPos());
                // Vérification que la case est libre
                if ((p instanceof Creature)&&(ch.chCrea[x][y] == null)) {
                    p.setPos(new Point2D(x, y));
                    ch.chCrea[x][y] = (Creature) p;
                    notValide = false;
                } else if ((p instanceof Objet)&&(ch.chObj[x][y] == null)) {
                    p.setPos(new Point2D(x, y));
                    ch.chObj[x][y] = (Objet) p;
                    notValide = false;
                }
            }
        }
        
        return ch;
    }
    
    /**
     * Convertit des coordonnées globales en coordonnées de chunk.
     * @param x
     * @param y
     * @return Couple de coordonnées du chunk
     */
    public static Point2D toChunkCoordinates(int x, int y){
        int chx = x/SIZE;
        int chy = y/SIZE;
        // Il faut prendre la partie entière.
        // Dans le cas des nombres négatifs, l'opération de modulo crée un offset de +1 qu'il faut compenser pour avoir la partie entière. 
        if (x<0){
            chx--;
        }
        if (y<0){
            chy--;
        }
        return new Point2D(chx, chy);
    }
    
    /**
     *Convertit des coordonnées globales en coordonnées de chunk.
     * @param p
     * @return Couple de coordonnées du chunk
     */
    public static Point2D toChunkCoordinates(Point2D p){
        return toChunkCoordinates(p.getX(), p.getY());
    }

    public Point2D getPos() {
        return pos;
    }

    public Tile[][] getChTile() {
        return chTile;
    }

    public Creature[][] getChCrea() {
        return chCrea;
    }

    /**
     * Place une créature dans la matrice de Créature
     * @param x
     * @param y
     * @param e Entité à placer
     */
    public void setCrea(int x, int y, Creature e){
        chCrea[x][y] = e;      
    }
    
    public Objet[][] getChObj() {
        return chObj;
    }

    public List<Entite> getEntites() {
        return entites;
    }
    
    /**
     * Ajoute une entité dans le chunk.
     * La créature est ajouter dans la liste entites et dans la matrice correspondante à son type (Créature ou Objet).
     * @param e Entité à ajouter
     */
    public void addEntity(Entite e) {
        Point2D p = e.getPos();
        entites.add(e);
        if (e instanceof Creature) {
            chCrea[p.getX()][p.getY()] = (Creature)e;
        } else {
            chObj[p.getX()][p.getY()] = (Objet)e;
        }
    }
    
}
