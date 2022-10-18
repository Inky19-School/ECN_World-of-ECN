/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.World;

import java.lang.reflect.InvocationTargetException;
import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.Objet.*;
import java.util.Random;
import java.util.ArrayList;
import java.util.LinkedList;
import java.util.List;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.centrale.objet.WoE.Joueur;
import org.centrale.objet.WoE.Point2D;
import org.centrale.objet.WoE.SaveManager;


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
    private Chunk[][] activeChunks;
    public List<Entite> entites; 
    /**
     * Matrice des Créatures pour un accès rapide à partir d'une position
     */
    public Entite[][] mapCreature;// Matrice des entités à leur position
    
    /**
     * Matrice des objets pour un accès rapide à partir d'une position
     */
    public Objet[][] mapObjets;

    
    private String name;
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

    public World(String name) {
        entites = new LinkedList<>();
        mapCreature = new Entite[SIZE][SIZE];
        mapObjets = new Objet[SIZE][SIZE];
        wolfie = new Loup();
        player = new Joueur();
        activeChunks = new Chunk[3][3];
        this.name = name;
        //mapCreatures[0][0] = wolfie;
    }
    
    public World(int size, Joueur p, String name) {
        SIZE = size;
        this.name = name;
        entites = new LinkedList<>();
        activeChunks = new Chunk[3][3];
        wolfie = new Loup();
        wolfie.setPos(new Point2D(3,4));
        player = p;
    } 
    
    private boolean isActive(Point2D chPos){
        Point2D active = activeChunks[1][1].getPos();
        return ((active.getX()-2 < chPos.getX() && chPos.getX() < active.getX()+2) && (active.getY()-2 < chPos.getY() && chPos.getY() < active.getY()+2));
    }
    
    private Point2D chRelativePos(Chunk ch){
        Point2D pos = ch.getPos();
        Point2D active = activeChunks[1][1].getPos();
        return new Point2D(active.getX()-pos.getX(), active.getY()-pos.getY());
    }
    
    private Point2D chRelativePos(Point2D p){
        Point2D active = activeChunks[1][1].getPos();
        return new Point2D(p.getX()-active.getX(), p.getY()-active.getY());
    }
    
    private Point2D chRelativePos(int x, int y){
        Point2D active = activeChunks[1][1].getPos();
        return new Point2D(active.getX()-x, active.getY()-y);
    }
    
    private Point2D toLocalCoordinates(int x, int y){
        int nX = x%Chunk.SIZE;
        int nY = y%Chunk.SIZE;
        if (nX < 0){
            nX = -nX;
        }
        if (nY < 0){
            nY = -nY;
        }
        return new Point2D(nX, nY);
    }
    
    private Point2D toLocalCoordinates(Point2D p){
        return toLocalCoordinates(p.getX(), p.getY());
    }
    
    
    public Creature getCrea(int x, int y){
        Point2D chPos = Chunk.toChunkCoordinates(x, y);
        if (isActive(chPos)){
            Point2D relPos = chRelativePos(chPos);
            return activeChunks[relPos.getX()+1][relPos.getY()+1].getChCrea()[x-relPos.getX()*Chunk.SIZE][y-relPos.getY()*Chunk.SIZE];
        } else {
            System.out.println("ça déconne getCrea");
            return null;
        }
    }
    
    public Objet getObj(int x, int y){
        Point2D chPos = Chunk.toChunkCoordinates(x, y);
        if (isActive(chPos)){
            Point2D relPos = chRelativePos(chPos);
            return activeChunks[relPos.getX()+1][relPos.getY()+1].getChObj()[x-relPos.getX()*Chunk.SIZE][y-relPos.getY()*Chunk.SIZE];
        } else {
            System.out.println("ça déconne getObj");
            return null;
        }
    }
    
    public void setEnt(int x, int y, Entite e){
        Point2D chPos = Chunk.toChunkCoordinates(x, y);
        if (isActive(chPos)){
            Point2D relPos = chRelativePos(chPos);
            activeChunks[relPos.getX()+1][relPos.getY()+1].setCrea(x-relPos.getX()*Chunk.SIZE, y-relPos.getY()*Chunk.SIZE, (Creature) e);

            if (e != null){
                e.setPos(new Point2D(x-relPos.getX()*Chunk.SIZE, y-relPos.getY()*Chunk.SIZE));
                activeChunks[relPos.getX()+1][relPos.getY()+1].entites.add((Creature) e);
            }
            
        } else {
            System.out.println("ça déconne setEnt");
        }
    }
    
    
    public void delEnt(Entite e){
       Point2D chPos = e.getChPos();
        if (isActive(chPos)){
            Point2D relPos = chRelativePos(chPos);
            Chunk ch = activeChunks[relPos.getX()+1][relPos.getY()+1];
            ch.getEntites().remove(e);
            System.out.println("oui oui luuig");
            if (e instanceof Objet){
                ch.getChObj()[e.getPos().getX()][e.getPos().getY()] = null;
            } else if (e instanceof Creature){
                ch.getChCrea()[e.getPos().getX()][e.getPos().getY()] = null;
            }
            
            
        } else {
            System.out.println("ça déconne delEnt");
        }
    }
    
    public void creerPlayer(Joueur player, int size) {
        
    }
    
    // À RETRAVAILLER !!!!
    public void ajoutCreature(Creature c){
        Point2D posC = c.getPos();
        mapCreature[posC.getX()][posC.getY()] = c;
        entites.add(c);
    }
    
    /**
     * Crée un monde avec des positions de départ aléatoires et distinctes
     */
    public void creerMondeAlea(){
        
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                activeChunks[i][j] = Chunk.chunkAlea(i-1, j-1);
                System.out.println(activeChunks[i][j].entites.size());
            }
        }
        activeChunks[1][1].entites.add(player.getPlayer());
        activeChunks[1][1].chCrea[player.getPlayer().getPos().getX()][player.getPlayer().getPos().getY()]  = player.getPlayer();
        
        PotionSoin p = new PotionSoin(new Point2D(1,1), 1, 6);
        activeChunks[1][1].entites.add(p);
        activeChunks[1][1].chObj[1][1] = p;
        Loup l1 = new Loup(new Point2D(1,1), new Point2D(0,0), 20, 20, 20, 20, 20);
        activeChunks[1][1].chCrea[1][1] = l1;
        activeChunks[1][1].entites.add(l1);
        
        activeChunks[1][1].chCrea[3][4] = wolfie;
        activeChunks[1][1].entites.add(wolfie);
        
        SaveManager.saveWorld(this);
    }
    
    /**
     * Non implémentée
     */
    public void tourDeJeu(){
        player.updateEffects();
        for (Entite e: activeChunks[1][1].getEntites()){
            if (e instanceof Creature && e!=player.getPlayer()){
                ((Creature) e).deplacer(activeChunks[1][1]);
            }
        }
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

    public Chunk[][] getActiveChunks() {
        return activeChunks;
    }
    
    public String getName() {
        return name;
    }
    
}
