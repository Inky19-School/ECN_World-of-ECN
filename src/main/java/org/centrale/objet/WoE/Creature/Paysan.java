/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Creature;

import org.centrale.objet.WoE.Creature.Personnage;
import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author inky19
 */
public class Paysan extends Personnage {
    
    /**
     *
     */
    public Paysan() {
        super();
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
     * @param pos Position
     */
    public Paysan(String nom, int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, int distAttMax, Point2D pos) {
        super(nom, ptVie, degAtt, ptPar, pageAtt, pagePar, distAttMax, pos);
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
