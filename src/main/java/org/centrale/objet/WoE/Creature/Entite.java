/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Creature;

import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author inky19
 */
public abstract class Entite {

    private Point2D pos;
    
    /**
     *
     */
    public Entite(){
        pos = new Point2D();
    }
    
    /**
     *
     * @param pos Position de l'entité
     */
    public Entite(Point2D pos) {
        this.pos = new Point2D(pos);
    }
    
    /**
     * 
     * @param entite Entité à copier
     */
    public Entite(Entite entite){
        this.pos = new Point2D(entite.pos);
    }

    /**
     * Renvoie la position de l'entité
     * @return Postion de l'entité
     */
    public Point2D getPos() {
        return pos;
    }

    /**
     * Modifie la position
     * @param pos Nouvelle position
     */
    public void setPos(Point2D pos) {
        this.pos = pos;
    }
    
    /**
     * Affiche des informations sur l'entité
     */
    abstract public void affiche();

}
