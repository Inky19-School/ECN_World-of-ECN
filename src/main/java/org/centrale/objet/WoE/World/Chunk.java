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
 * @author inky19
 */
public class Chunk {
    
    private Point2D pos;

    public static final int SIZE = 32;
    
    public Tile[][] chTile;
    public Creature[][] chCrea;
    public List<Entite> entites;
    public Objet[][] chObj;
    
    
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
        rand = alea.nextInt(maxType); 
        for (int i=0; i<1; i++){
            ch.entites.add(new Archer());
        }
        
        rand = alea.nextInt(maxType); 
        for (int i=0; i<rand; i++){
            ch.entites.add(new Paysan());
        }
        
        rand = alea.nextInt(maxType); 
        for (int i=0; i<rand; i++){
            ch.entites.add(new Lapin());
        }
        
        rand = alea.nextInt(maxType); 
        for (int i=0; i<rand; i++){
            ch.entites.add(new Guerrier());
        }
        
        rand = alea.nextInt(maxType); 
        for (int i=0; i<rand; i++){
            ch.entites.add(new Loup());
        }
        
        rand = alea.nextInt(maxType/2);
        for (int i = 0; i < rand; i++) {
            ch.entites.add(new PotionSoin());
        }
        rand = alea.nextInt(maxType/2);
        for (int i = 0; i < rand; i++) {
            ch.entites.add(new NuageToxique());
        }
        rand = alea.nextInt(maxType);
        for (int i = 0; i < rand; i++) {
            ch.entites.add(new SuperMushroom());
        }
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
                }
                if ((p instanceof Objet)&&(ch.chObj[x][y] == null)) {
                    p.setPos(new Point2D(x, y));
                    ch.chObj[x][y] = (Objet) p;
                    notValide = false;
                }
            }
        }
        
        return ch;
    }
    
    public static Point2D toChunkCoordinates(int x, int y){
        int chx = x/SIZE;
        int chy = y/SIZE;
        if (x<0){
            chx--;
        }
        if (y<0){
            chy--;
        }
        return new Point2D(chx, chy);
    }
    
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

    public void setCrea(int x, int y, Creature e){
        chCrea[x][y] = e;      
    }
    
    public Objet[][] getChObj() {
        return chObj;
    }

    public List<Entite> getEntites() {
        return entites;
    }
    
}
