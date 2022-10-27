/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.World;

import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.Objet.*;
import java.util.ArrayList;
import org.centrale.objet.WoE.Joueur;
import org.centrale.objet.WoE.Point2D;


/**
 *
 * @author François MARIE et Rémi RAVELLI
 */
public class World {
    /**
     * Objets sur la carte
     */
   /**
    * Liste des entités
    */
    private Chunk[][] activeChunks;


    
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
    
    
    //public Loup wolfie;

    public World(String name) {
        //wolfie = new Loup();
        player = new Joueur();
        activeChunks = new Chunk[3][3];
        this.name = name;
        //mapCreatures[0][0] = wolfie;
    }
    
    public World(int size, Joueur p, String name) {
        SIZE = size;
        this.name = name;
        activeChunks = new Chunk[3][3];
        //wolfie = new Loup();
        //wolfie.setPos(new Point2D(3,4));
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
    
    /**
     * Convertit des coordonnées globales en coordonnées locales d'un chunk.
     * @param x
     * @param y
     * @return Couple des coordonnées locales dans un chunk
     */
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
    
     /**
     * Convertit des coordonnées globales en coordonnées locales d'un chunk.
     * @param p
     * @return Couple des coordonnées locales dans un chunk
     */
    private Point2D toLocalCoordinates(Point2D p){
        return toLocalCoordinates(p.getX(), p.getY());
    }
    
     /**
     * Convertit des coordonnées globales en coordonnées de chunk.
     * @param p
     * @return Couple des coordonnées locales d'un chunk
     */
    public static Point2D toChunkCoordiantes(Point2D p){
        int chx = p.getX()/Chunk.SIZE;
        int chy = p.getY()/Chunk.SIZE;
        if (p.getX()<0){
            chx--;
        }
        if (p.getY()<0){
            chy--;
        }
        return new Point2D(chx, chy);
    }
    
    /**
     * Retourne une entité à l'aide des coordonées globales.
     * Doit se trouver dans un chunk actif.
     * @param x
     * @param y
     * @return Entité
     */
    public Creature getCrea(int x, int y){
        Point2D chPos = Chunk.toChunkCoordinates(x, y);
        if (isActive(chPos)){
            Point2D relPos = chRelativePos(chPos);
            return activeChunks[relPos.getX()+1][relPos.getY()+1].getChCrea()[x-relPos.getX()*Chunk.SIZE][y-relPos.getY()*Chunk.SIZE];
        } else {
            return null;
        }
    }
    
    /**
     * Retourne un objet à l'aide des coordonées globales.
     * Doit se trouver dans un chunk actif.
     * @param x
     * @param y
     * @return Objet
     */
    public Objet getObj(int x, int y){
        Point2D chPos = Chunk.toChunkCoordinates(x, y);
        if (isActive(chPos)){
            Point2D relPos = chRelativePos(chPos);
            return activeChunks[relPos.getX()+1][relPos.getY()+1].getChObj()[x-relPos.getX()*Chunk.SIZE][y-relPos.getY()*Chunk.SIZE];
        } else {
            return null;
        }
    }
    
     /**
     * Place une entité à l'aide des coordonées globales.
     * Doit se trouver dans un chunk actif.
     * @param x
     * @param y
     * @param e Entité à placer
     */
    public void setEnt(int x, int y, Entite e){
        Point2D newChPos = Chunk.toChunkCoordinates(x, y);
        //System.out.println("====="+x + ":" + y+" "+newChPos.getX() + " " + newChPos.getY()+"=====");
        Point2D currentChPos;
        if (e!=null){
            currentChPos = e.getChPos();
            
        } else {
            currentChPos = activeChunks[1][1].getPos();
        }
        if (isActive(newChPos) && isActive(currentChPos)){
            Point2D relPos = chRelativePos(newChPos);
            
            activeChunks[relPos.getX()+1][relPos.getY()+1].setCrea(x-relPos.getX()*Chunk.SIZE, y-relPos.getY()*Chunk.SIZE, (Creature) e);
            
            if (e != null){
                Point2D oldRelPos = chRelativePos(e.getChPos());
                e.setPos(new Point2D(x-relPos.getX()*Chunk.SIZE, y-relPos.getY()*Chunk.SIZE));
                if (!e.getChPos().equals(newChPos)){
                    
                    activeChunks[relPos.getX()+1][relPos.getY()+1].entites.add((Creature) e);
                    activeChunks[oldRelPos.getX()+1][oldRelPos.getY()+1].entites.remove(e);
                    e.setChPos(newChPos);
                }
                
            }
            
        }
    }
    
    /**
     * Supprime une entité du monde (i.e. de son chunk).
     * @param e Entité à supprimer
     */
    public void delEnt(Entite e){
       Point2D chPos = e.getChPos();
        if (isActive(chPos)){
            Point2D relPos = chRelativePos(chPos);
            Chunk ch = activeChunks[relPos.getX()+1][relPos.getY()+1];
            ch.getEntites().remove(e);
            if (e instanceof Objet){
                ch.getChObj()[e.getPos().getX()][e.getPos().getY()] = null;
            } else if (e instanceof Creature){
                ch.getChCrea()[e.getPos().getX()][e.getPos().getY()] = null;
            }
            
        }
    }
    
    public void setPlayer(Joueur player) {
        this.player = player;
    }
    
    
    /**
     * Crée un monde avec des positions de départ aléatoires et distinctes
     */
    public void creerMondeAlea(){
        
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                activeChunks[i][j] = Chunk.chunkAlea(i-1, j-1);
            }
        }
        activeChunks[1][1].entites.add(player.getPlayer());
        activeChunks[1][1].chCrea[player.getPlayer().getPos().getX()][player.getPlayer().getPos().getY()]  = player.getPlayer();
        
    }
    
    /**
     * Tour de jeu entre chaque action du joueur.
     */
    public void tourDeJeu(){
        player.updateEffects();
        for (int i=0;i<3;i++){
            for (int j=0; j<3; j++){
                for (Entite e: activeChunks[i][j].getEntites()){
                    if (e instanceof Creature && e!=player.getPlayer()){
                        ((Creature) e).deplacer(activeChunks[i][j]);
                    }
                }
            }
        }
        
        /* PARTIE DU CODE EN TRAVAIL POUR GÉRER LA GÉNÉRATION DE NOUVEAU CHUNK. NON FINIE POUR LE RAPPORT FINAL.
        Point2D playerChPos = player.getPlayer().getChPos();
        Point2D relPosCh = chRelativePos(playerChPos);
        if (!relPosCh.equals(new Point2D(0,0))){
            Chunk[][] newActiveChunks = new Chunk[3][3];
            for (int i=0;i<3;i++){
                for (int j=0; j<3; j++){
                    if((i+relPosCh.getX()<3 && i+relPosCh.getX()>=0) && (j+relPosCh.getY()<3 && j+relPosCh.getY()>=0)){
                        newActiveChunks[i][j] = activeChunks[i+relPosCh.getX()][i+relPosCh.getY()];
                    } else {
                        newActiveChunks[i][j] = Chunk.chunkAlea(playerChPos.getX()+i, playerChPos.getX()+j);
                    }
                }
            }
            activeChunks = newActiveChunks;
            for (int i=0; i<3; i++){
                for (int j=0; j<3; j++){
                System.out.println(activeChunks[i][j].entites.size());
            }
        }
        }
        */
    }
    
    /**
     * Vérifie si une créature peut intéragir avec un objet sur la carte, et le supprime après l'interaction.
     * @param creature Créature avec laquelle intéragir
     */
    public void interactionObjet(Creature creature) {
        Point2D posCrea = creature.getPos();
        Point2D posCh = creature.getChPos();
        int x = posCrea.getX() + posCh.getX()*Chunk.SIZE;
        int y = posCrea.getY() + posCh.getY()*Chunk.SIZE;
        if (getObj(x, y) != null) {
            getObj(x, y).interagir(creature);
        }
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
