/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Objet;

import org.centrale.objet.WoE.Creature.Creature;
import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author inky19
 */
public class PotionSoin extends Objet {


    private int ptVieRegen;

    /**
     *
     * @param pos Position
     * @param qte Quantité
     * @param ptVieRegen Points de vie régénérés
     */
    public PotionSoin(Point2D pos, int qte, int ptVieRegen) {
        super(pos, qte);
        this.ptVieRegen = ptVieRegen;
    }

    /**
     *
     */
    public PotionSoin() {
        super();
        ptVieRegen = 5;
    }
    
    /**
     * Affiche des informations sur la potion
     */
    public void affiche(){
        System.out.println("POTION :\n[" + this.getPos().getX() + ";" + this.getPos().getY() + "]    PV Regen :" + ptVieRegen);
    }

    /**
     * Intéragit avec une créature
     * @param creature Créature avec laquelle intéragir
     */
    public void interagir(Creature creature){
        creature.addPV(ptVieRegen);
    }
    
    /**
     * Renvoie les points de vie régénérés
     * @return Points de vie régénérés
     */
    public int getPtVieRegen() {
        return ptVieRegen;
    }

    /**
     * Modifie les points de vie régénérés
     * @param ptVieRegen Nouvelle valeur des points de vie régénérés
     */
    public void setPtVieRegen(int ptVieRegen) {
        this.ptVieRegen = ptVieRegen;
    }
    
    
}
