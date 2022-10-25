/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Objet;

import org.centrale.objet.WoE.Action.Effect;
import org.centrale.objet.WoE.Action.HasEffect;
import org.centrale.objet.WoE.Creature.Creature;
import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author inky19
 */
public class PotionSoin extends Objet implements Utilisable, HasEffect{
    
      
    private int ptVieRegen;
    //private Effect effect;
    private static int BASE_PV_REGEN = 5;

    
    
    /**
     *
     * @param pos Position
     * @param qte Quantité
     * @param ptVieRegen Points de vie régénérés
     */
    public PotionSoin(Point2D pos, Point2D chPos, int ptVieRegen) {
        super(pos, chPos);
        this.ptVieRegen = ptVieRegen;
    }
    
        public PotionSoin(Point2D pos, int qte) {
        super(pos, qte);
        this.ptVieRegen = BASE_PV_REGEN;
    }

    /**
     *
     */
    public PotionSoin() {
        super();
        ptVieRegen = 5;
        //effect = new Effect
        ptVieRegen = BASE_PV_REGEN;

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

    @Override
    public void utiliser() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
    public Effect getEffect(){
        return new Effect(1, Effect.HP, ptVieRegen);
    }
}
