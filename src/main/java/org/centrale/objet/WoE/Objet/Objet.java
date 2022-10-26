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
 * @author François MARIE et Rémi RAVELLI
 */
public abstract class Objet extends Entite{

    private int qte;

    /**
     *
     * @param pos Position
     * @param chPos Position du chunk
     */
    public Objet(Point2D pos, Point2D chPos) {
        super(pos,chPos);
        this.qte = 1;
    }
    
    /**
     *
     * @param pos Position
     * @param chPos Position du chunk
     * @param qte Quantité
     */
    public Objet(Point2D pos, Point2D chPos, int qte) {
        super(pos,chPos);
        this.qte = qte;
    }
    
    public Objet() {
        super();
        qte = 1;
    }
    
    
    /**
     *
     * @param creature
     */
    public void interagir(Creature creature){
        
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
