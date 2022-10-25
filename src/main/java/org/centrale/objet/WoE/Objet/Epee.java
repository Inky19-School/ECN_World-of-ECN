/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Objet;

import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author inky19
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
     * @param ptDgt
     * @param coolDown
     * @param durabilite
     * @param pos
     * @param qte
     */
    public Epee(Point2D pos, Point2D chPos,int ptDgt, int coolDown, int durabilite) {
        super(pos, chPos);
        this.ptDgt = ptDgt;
        this.coolDown = coolDown;
        this.durabilite = durabilite;
    }

    /**
     *
     * @param ptDgt
     * @param coolDown
     * @param durabilite
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
