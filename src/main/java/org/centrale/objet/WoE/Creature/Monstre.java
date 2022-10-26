/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Creature;

import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author François MARIE et Rémi RAVELLI
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
     * @param pos Position de la créature dans le chunk
     * @param chPos Position du chunk de la créature
     * @param ptVie Points de vie
     * @param ptPar Points de parade
     * @param pagePar Pourcentage de parade
     * @param pageAtt Pourcentage d'attaque
     * @param degAtt Dégâts infligés
     */
    public Monstre(Point2D pos, Point2D chPos, int ptVie, int ptPar, int pagePar, int pageAtt, int degAtt) {
        super(pos, chPos, ptVie, ptPar, pagePar, pageAtt, degAtt);
    }
    
    /**
     * Affiche des informations sur le monstre
     */
    @Override
    public void affiche(){
        super.affiche();
    }
}
