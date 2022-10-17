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


/**
 * Classe Personnage
 * Sous-classe de Creature
 * @author inky19
 */
public class Personnage extends Creature{
    private String nom;
    private int distAttMax;

    /**
     *
     */
    public Personnage() {
        super();
        nom = "";
        distAttMax = 0;
    }
    
    /**
     *
     * @param perso
     */
    public Personnage(Personnage perso){
        super(perso);
        this.nom = perso.nom;
        this.distAttMax = perso.distAttMax;
    }

    /**
     *
     * @param nom  Nom
     * @param ptVie Points de vie
     * @param degAtt Dégâte lors d'une attaque
     * @param ptPar Points de parade
     * @param pageAtt Pourcentage d'attaque
     * @param pagePar Pourcentage de parade
     * @param distAttMax Distance maximale d'attaque
     * @param pos Position de la créature dans le chunk
     * @param chPos Position du chunk de la créature
     */
    public Personnage(String nom, int ptVie, int degAtt, int ptPar, int pageAtt, int pagePar, int distAttMax, Point2D pos, Point2D chPos) {
        super(pos, chPos, ptVie, ptPar, pagePar, pageAtt, degAtt);
        this.nom = nom;
        this.distAttMax = distAttMax;
    }
    
    /**
     * Renvoie le nom
     * @return nom
     */
    public String getNom() {
        return nom;
    }

    /**
     * Modifie le nom
     * @param nom Nouveau nom
     */
    public void setNom(String nom) {
        this.nom = nom;
    }

    /**
     * Renvoie la distance maximale d'attaque
     * @return Distance maximale d'attaque
     */
    public int getDistAttMax() {
        return distAttMax;
    }

    /**
     * Modifie la distance maximale d'attaque
     * @param distAttMax Nouvelle distance maximale d'attaque
     */
    public void setDistAttMax(int distAttMax) {
        this.distAttMax = distAttMax;
    }
    
    /**
     * Affiche des informations sur le personnage
     */
    @Override
    public void affiche(){
        super.affiche();
    }

}
