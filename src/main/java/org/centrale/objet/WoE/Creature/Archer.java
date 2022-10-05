/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Creature;

import java.util.Random;
import org.centrale.objet.WoE.Action.Combattant;
import org.centrale.objet.WoE.Creature.Personnage;
import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author inky19
 */
public class Archer extends Personnage implements Combattant {

    private int nbFleches;
    private int degAttDist;
    
    /**
     *
     */
    public Archer() {
        super();
        nbFleches = 20;
        degAttDist = 4;
        this.setDegAtt(6);
        this.setPageAtt(90);
        this.setPagePar(5);
        this.setPtPar(4);
        this.setPtVie(27);
    }

    /**
     *
     * @param perso Archer à copier
     */
    public Archer(Archer perso) {
        super(perso);
        this.nbFleches = perso.nbFleches;
        this.degAttDist = perso.degAttDist;
    }

    /**
     *
     * @param nom Nom 
     * @param ptVie Points de vie
     * @param degAtt Dégâts infligés au corps à corps
     * @param ptPar Points de parade
     * @param pageAtt Pourcentage d'attaque
     * @param pagePar Pourcentage de parade
     * @param distAttMax Distance maximale d'attaque
     * @param pos Position
     * @param nbFleches Nombre de flèches
     * @param degAttDist Dégâts infligés à distance
     */
    public Archer(String nom, int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, int distAttMax, Point2D pos, int nbFleches, int degAttDist) {
        super(nom, ptVie, degAtt, ptPar, pageAtt, pagePar, distAttMax, pos);
        this.nbFleches = nbFleches;
        this.degAttDist = degAttDist;
    }

    /**
     * Combat contre une autre créature et inflige des dégâts
     * @param c Crétature à combattre
     */
    public void combattre(Creature c){
        float distance = c.getPos().distance(this.getPos());
        if (distance ==1){
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
        } else if (distance>1 && distance < this.getDistAttMax() && nbFleches > 0){
            Random alea = new Random();
            nbFleches --;
            int rand = alea.nextInt(100)+1;
            if (rand <= this.getPageAtt()){
                    c.addPV(-degAttDist);
                }
        }
    }

    /**
     * Affiche des informations sur l'archer
     */
    @Override
    public void affiche(){
        System.out.println("\nARCHER :");
        super.affiche();
    }

    /**
     * Renvoie le nombre de flèches
     * @return Nomnbre de flèches
     */
    public int getNbFleches() {
        return nbFleches;
    }

    /**
     * Modifie le bombre de flèches
     * @param nbFleches Noubeau nombre de flèches
     */
    public void setNbFleches(int nbFleches) {
        this.nbFleches = nbFleches;
    }

    /**
     * Renvoie la distance maximake d'attaque
     * @return Distance maximale d'attaque
     */
    public int getDegAttDist() {
        return degAttDist;
    }

    /**
     * Modifie la distance maximale d'attaque
     * @param degAttDist Nouvelle valeur de la distance maximale d'attaque
     */
    public void setDegAttDist(int degAttDist) {
        this.degAttDist = degAttDist;
    }
    
    
}
