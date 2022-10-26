/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Objet;

import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author François MARIE et Rémi RAVELLI
 */
public class Epee extends Objet implements Utilisable {

    private int ptDgt;
    private int coolDown;
    private int durabilite;

    /**
     *
     */
    public Epee(){
        super();
        ptDgt = 0;
        coolDown = 0;
        durabilite = 0;
    }
    
    /**
     *
     * @param ptDgt Point de dégâts
     * @param coolDown Attente entre deux coups
     * @param durabilite Durabilité de l'épée
     * @param pos Position
     * @param chPos Position du chunk
     * @param qte Quantité
     */
    public Epee(Point2D pos, Point2D chPos,int ptDgt, int coolDown, int durabilite) {
        super(pos, chPos);
        this.ptDgt = ptDgt;
        this.coolDown = coolDown;
        this.durabilite = durabilite;
    }

    /**
     *
     * @param ptDgt Point de dégâts
     * @param coolDown Attente entre deux coups
     * @param durabilite Durabilité de l'épée
     */
    public Epee(int ptDgt, int coolDown, int durabilite) {
        this.ptDgt = ptDgt;
        this.coolDown = coolDown;
        this.durabilite = durabilite;
    }

    /**
     *
     * @return
     */
    public int getPtDgt() {
        return ptDgt;
    }

    /**
     *
     * @param ptDgt
     */
    public void setPtDgt(int ptDgt) {
        this.ptDgt = ptDgt;
    }

    /**
     *
     * @return
     */
    public int getCoolDown() {
        return coolDown;
    }

    /**
     *
     * @param coolDown
     */
    public void setCoolDown(int coolDown) {
        this.coolDown = coolDown;
    }

    /**
     *
     * @return
     */
    public int getDurabilite() {
        return durabilite;
    }

    /**
     *
     * @param durabilite
     */
    public void setDurabilite(int durabilite) {
        this.durabilite = durabilite;
    }

    @Override
    public void utiliser() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void affiche() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    
}
