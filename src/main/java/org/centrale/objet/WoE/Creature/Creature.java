/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.Creature to edit this template
 */
package org.centrale.objet.WoE.Creature;

import org.centrale.objet.WoE.Point2D;
import java.util.ArrayList;
import java.util.Random;

/**
 *
 * @author inky19
 */
public class Creature extends Entite{
    private int ptVie;
    private int ptPar;
    private int pagePar;
    private int pageAtt;
    private int degAtt;

    /**
     *
     */
    public Creature() {
        super();
        ptVie = 0;
        ptPar = 0;
        pagePar = 0;
        pageAtt = 0;
        degAtt = 0;
    }
    
    /**
     *
     * @param creature Créature à copier
     */
    public Creature(Creature creature){
        super(creature);
        this.ptVie = creature.ptVie;
        this.ptPar = creature.ptPar;
        this.pagePar = creature.pagePar;
        this.pageAtt = creature.pageAtt;
        this.degAtt = creature.degAtt;
    }

    /**
     *
     * @param pos Position de la créature dans le monde
     * @param ptVie Nombre de points de vie
     * @param ptPar Nombre de point de parade
     * @param pagePar Pourcentade de chance de parade
     * @param pageAtt Pourcentage de chance d'attaque
     * @param degAtt Dégâts infligés lors d'une attaque
     */
    public Creature(Point2D pos, int ptVie, int ptPar, int pagePar, int pageAtt, int degAtt) {
        super(pos);
        this.ptVie = ptVie;
        this.ptPar = ptPar;
        this.pagePar = pagePar;
        this.pageAtt = pageAtt;
        this.degAtt = degAtt;
    }
    
    
    /**
     * Ajouter des points de vie
     * La valeur peut être négative pour soustraire.
     * @param pv Point de vie à ajouter
     */
    
    public void addPV(int pv){
        ptVie += pv;
    }
    
    /**
     * Affiche des informations sur la créature.
     */
    @Override
    public void affiche(){
        System.out.println("Position : [" + this.getPos().getX() + ";" + getPos().getY() + "] | HP : " + ptVie + "\n%Parade : " + pagePar + "   Points parade : " + pagePar + "\n%Attaque : " + pageAtt + "   Points attaque : " + degAtt);
    }
    
    /**
     * Déplace la créature de manière aléatoire.
     */
    public void deplace(){
        ArrayList<Point2D> casesCibles = new ArrayList<>();
        for (int i=-1;i<2;i++){
            for (int j=-1;j<2;j++){
                if (!(j==0 && i==0)){
                    casesCibles.add(new Point2D(i,j));
                }
            }
        }
        Random alea = new Random();
        int indCase = alea.nextInt(casesCibles.size());
        Point2D cible = casesCibles.get(indCase);
        Point2D newPos = new Point2D(this.getPos());
        newPos.translate(cible.getX(),cible.getY());
        this.setPos(newPos);
    }   
  

    /**
     * Renvoie le nombre de points de vie de la créature
     * @return Nombre de points de vie de la créature
     */
    public int getPtVie() {
        return ptVie;
    }

    /**
     * Modifie le nombre de points de vie
     * @param ptVie Nouvelle valeur des points de vie
     */
    public void setPtVie(int ptVie) {
        this.ptVie = ptVie;
    }

    /**
     * Renvoie le nombre de points de parade de la créature
     * @return Nombre des points de parade de la créature
     */
    public int getPtPar() {
        return ptPar;
    }

    /**
     * Modifie le nombre de points de parade
     * @param ptPar Nouvelle valeur des points de parade de la créature
     */
    public void setPtPar(int ptPar) {
        this.ptPar = ptPar;
    }

    /**
     * Renvoie le pourcentage de parade de la créature
     * @return Pourcentage de parade de la créature
     */
    public int getPagePar() {
        return pagePar;
    }

    /**
     * Modifie le pourcentage de parade
     * @param pagePar Nouveau pourcentage de parade de la créature
     */
    public void setPagePar(int pagePar) {
        this.pagePar = pagePar;
    }

    /**
     * Renvoie le pourcentage d'attaque de la créature
     * @return Pourcentage d'attaque de la créature
     */
    public int getPageAtt() {
        return pageAtt;
    }

    /**
     * Modifie le pourcentage d'attaque
     * @param pageAtt Nouveau pourcentage d'attaque de la créature
     */
    public void setPageAtt(int pageAtt) {
        this.pageAtt = pageAtt;
    }

    /**
     * Renvoie le nombre de points de dégâts infligés lors d'une attaque
     * @return Nombre de dégâts lors d'une attaque
     */
    public int getDegAtt() {
        return degAtt;
    }

    /**
     * Modifie le nombre de dégâts à infliger
     * @param degAtt Nouvelle valeur du nombre de points de dégâts à infliger
     */
    public void setDegAtt(int degAtt) {
        this.degAtt = degAtt;
    }
    
    
}
