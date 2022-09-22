/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Creature;

import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author inky19
 */
public class Monstre extends Creature{
    
    /**
     *
     */
    public Monstre() {
        super();
    }
    
    /**
     *
     * @param monstre
     */
    public Monstre(Monstre monstre){
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
    public Monstre(Point2D pos, int ptVie, int ptPar, int pagePar, int pageAtt, int degAtt) {
        super(pos, ptVie, ptPar, pagePar, pageAtt, degAtt);
    }
    
    /**
     * Affiche des informations sur le monstre
     */
    public void affiche(){
        super.affiche();
    }
}
