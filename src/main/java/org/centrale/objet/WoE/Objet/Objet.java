/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Objet;
import org.centrale.objet.WoE.Creature.Creature;
import org.centrale.objet.WoE.Creature.Entite;
import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author inky19
 */
public class Objet extends Entite{

    private Point2D pos;
    private int qte;

    /**
     *
     * @param pos Position
     * @param qte Quantité
     */
    public Objet(Point2D pos, int qte) {
        this.pos = pos;
        this.qte = qte;
    }
    
    /**
     *
     */
    public Objet(){
        qte = 1;
        pos = new Point2D();
    }
    
    /**
     *
     * @param creature
     */
    public void interagir(Creature creature){
        
    }

    /**
     * Renvoie la position
     * @return Position
     */
    public Point2D getPos() {
        return pos;
    }

    /**
     * Modifie la postion
     * @param pos Nouvelle position
     */
    public void setPos(Point2D pos) {
        this.pos = pos;
    }

    /**
     * Renvoie la quantité de l'objet
     * @return Quantité de l'objet
     */
    public int getQte() {
        return qte;
    }

    /**
     * Modifie la quantité
     * @param qte Nouvelle quantité
     */
    public void setQte(int qte) {
        this.qte = qte;
    }
    
    
}
