/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Creature;

import java.util.Random;
import org.centrale.objet.WoE.Action.Combattant;
import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author François MARIE et Rémi RAVELLI
 */
public class Guerrier extends Personnage implements Combattant {

    /**
     *
     */
    public Guerrier() {
        super();
        this.setDegAtt(10);
        this.setPageAtt(83);
        this.setPagePar(8);
        this.setPtPar(7);
        this.setPtVie(42);
        super.setNom("Robert");
    }

    /**
     *
     * @param guerrier
     */
    public Guerrier(Guerrier guerrier) {
        super(guerrier);
    }

    /**
     *
     * @param nom Nom
     * @param ptVie Points de vie
     * @param degAtt Dégâts infligés
     * @param ptPar Points de parade
     * @param pageAtt Pourcentage d'attaque
     * @param pagePar Pourcentage de parade
     * @param distAttMax Distance maximale d'attaque
     * @param chPos Position du chunk
     * @param pos Positon
     */
    public Guerrier(Point2D pos, Point2D chPos, int ptVie, int ptPar, int pagePar, int pageAtt, int degAtt, int distAttMax, String nom) {
        super(pos, chPos, ptVie, ptPar, pagePar, pageAtt, degAtt, distAttMax, nom);
    }

    /**
     * Combat contre une autre créature et inflige des dégâts
     * @param c Crétature à combattre
     */
    @Override
    public boolean combattre(Creature c){
        if (c.getPos().distance(this.getPos())==1){
            Random alea = new Random();
            int rand = alea.nextInt(100)+1; // Lance le dé pour savoir si l'attaque a lieu
            if (rand <= this.getPageAtt()){
                rand = alea.nextInt(100)+1; // Lance le dé pour savoir si la parade adverse a lieu
                if (rand <= c.getPagePar()){
                    int dgt = this.getDegAtt()-c.getPtPar();
                    if (dgt>0){ // Empêche de régénérer si la parade est plus grande que les dégâts
                        c.addPV(-dgt);
                    }
                } else {
                    c.addPV(-this.getDegAtt());
                }
            }
            return true;
        }
        return false;
    }
    
    /**
     * Affiche des informations sur le guerrier
     */
    @Override
    public void affiche(){
        System.out.println("\nGUERRIER :");
        super.affiche();
    }
}
