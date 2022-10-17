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
 * @author inky19
 */
public class Loup extends Monstre implements Combattant {

    /**
     *
     */

    public Loup() {
        super();
        this.setDegAtt(15);
        this.setPageAtt(59);
        this.setPagePar(9);
        this.setPtPar(6);
        this.setPtVie(25);
    }

    /**
     *
     * @param loup
     */
    public Loup(Loup loup) { 
        super(loup);
    }

    /**
     *
     * @param pos Position de la créature dans le chunk
     * @param chPos Position du chunk de la créature
     * @param ptVie Points de vie
     * @param ptPar Points de parade
     * @param pagePar Pourcentage de parade
     * @param pageAtt Pourcentage d'attaque
     * @param degAtt Dégâts 
     */
    public Loup(Point2D pos, Point2D chPos, int ptVie, int ptPar, int pagePar, int pageAtt, int degAtt) {
        super(pos, chPos, ptVie, ptPar, pagePar, pageAtt, degAtt);
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
                System.out.println("Attaque réussite");
                rand = alea.nextInt(100)+1; // Lance le dé pour savoir si la parade adverse a lieu
                if (rand <= c.getPagePar()){
                    int dgt = this.getDegAtt()-c.getPtPar();
                    if (dgt>0){ // Empêche de régénérer si la parade est plus grande que les dégâts
                        c.addPV(-dgt);
                        System.out.println("Parade réussite");
                    }
                } else {
                    c.addPV(-this.getDegAtt());
                    System.out.println("Parade ratée");
                }
            } else {
                System.out.println("Attaque ratée");
            }
            return true;
        }
        return false;
    }
    
    /**
     * Affiche des informations sur le loup
     */
    @Override
    public void affiche(){
        System.out.println("\nLOUP :");
        super.affiche();
    }
}
