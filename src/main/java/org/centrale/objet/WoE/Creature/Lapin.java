/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Creature;

import org.centrale.objet.WoE.Creature.Monstre;
import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author inky19
 */
public class Lapin extends Monstre {

    /**
     *
     */
    public Lapin() {
    }

    /**
     *
     * @param monstre
     */
    public Lapin(Lapin monstre) {
        super(monstre);
    }

    /**
     *
     * @param pos Position
     * @param ptVie Points de vie
     * @param ptPar Points de parade
     * @param pagePar Pourcentage de parade
     * @param pageAtt Pourcentage d'attaque
     * @param degAtt Dégâts infligés
     */
    // TODO : Définir automatiquement l'attaque à 0 car inutile
    public Lapin(Point2D pos, int ptVie, int ptPar, int pagePar, int pageAtt, int degAtt) {
        super(pos, ptVie, ptPar, pagePar, pageAtt, degAtt);
    }

    /**
     * Affiche des informations sur le lapin
     */
    public void affiche(){
        System.out.println("\nLAPIN");
        super.affiche();
    }
}
