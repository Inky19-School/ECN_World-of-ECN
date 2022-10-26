/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Creature;

import org.centrale.objet.WoE.Creature.Personnage;
import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author François MARIE et Rémi RAVELLI
 */
public class Paysan extends Personnage {
    
    /**
     *
     */
    public Paysan() {
        super();
        this.setDegAtt(0);
        this.setPageAtt(0);
        this.setPagePar(0);
        this.setPtPar(0);
        this.setPtVie(22);
    }

    /**
     *
     * @param perso
     */
    public Paysan(Paysan perso) {
        super(perso);
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
     * @param pos Position
     */
    public Paysan(Point2D pos, Point2D chPos, int ptVie, int ptPar, int pagePar, int pageAtt, int degAtt, int distAttMax, String nom) {
        super(pos, chPos, ptVie, ptPar, pagePar, pageAtt, degAtt, distAttMax, nom);
    }
    
    /**
     * Affiche des informations sur la paysan
     */
    @Override
    public void affiche(){
        System.out.println("\nPAYSAN :");
        super.affiche();
    }
}
