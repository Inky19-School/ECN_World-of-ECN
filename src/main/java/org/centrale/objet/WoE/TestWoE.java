/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import java.util.ArrayList;
import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.UI.*;
/**
 *
 * @author François MARIE & Rémi RAVELLI
 */
public class TestWoE {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        World monde = new World();
        monde.creerMondeAlea();
        ArrayList<Entite> Entities =  new ArrayList<>();
        Entities.add(monde.robin);
        Entities.add(monde.peon);
        Entities.add(monde.bugs1);
        Entities.add(monde.bugs2);
        Entities.add(monde.wolfie);
        Entities.add(monde.grosBill);
        Entities.add(monde.P1);
        Entities.add(monde.P2);
        
        for (int i=0; i< Entities.size(); i++){
            Entities.get(i).affiche();
        }
        System.out.println("\n=====================================\nAttaque forcée de Wolfie sur Lapin1 :");
        
        monde.bugs1.setPtVie(20);
        monde.bugs1.setPagePar(50);
        monde.bugs1.setPtPar(3);
        monde.wolfie.setPageAtt(50);
        monde.wolfie.setDegAtt(5);
        monde.wolfie.affiche();
        monde.bugs1.affiche();
        System.out.println("Distance avant attaque :" + monde.wolfie.getPos().distance(monde.bugs1.getPos()));
        monde.wolfie.combattre(monde.bugs1);
        
        System.out.println("Déplacement forcé :\n-----------------------");
        monde.wolfie.setPos(new Point2D(0,0));
        monde.bugs1.setPos(new Point2D(0,1));
        System.out.println("Distance avant attaque :" + monde.wolfie.getPos().distance(monde.bugs1.getPos()));
        monde.wolfie.affiche();
        monde.bugs1.affiche();
        monde.wolfie.combattre(monde.bugs1);
        monde.bugs1.affiche();
        
        System.out.println("\n\n=====================================\nAttaque Archer sur Lapin1 :");
        
        monde.robin.setDistAttMax(4);
        monde.robin.setNbFleches(40);
        monde.robin.setPageAtt(90);
        monde.robin.setDegAttDist(7);
        monde.robin.setDegAtt(4);
        monde.robin.setPos(new Point2D(20,1));
        System.out.println("Distance avant attaque :" + monde.robin.getPos().distance(monde.bugs1.getPos()));
        monde.robin.affiche();
        monde.robin.combattre(monde.bugs1);
        monde.bugs1.affiche();
        monde.robin.setPos(new Point2D(1,2));
        System.out.println("\nRepositionnement Archer. \nDistance avant attaque :" + monde.robin.getPos().distance(monde.bugs1.getPos()));
        monde.robin.affiche();
        monde.robin.combattre(monde.bugs1);
        monde.bugs1.affiche();
        monde.robin.setPos(new Point2D(0,2));
        System.out.println("\nRepositionnement Archer. \nDistance avant attaque :" + monde.robin.getPos().distance(monde.bugs1.getPos()));
        monde.robin.affiche();
        monde.robin.combattre(monde.bugs1);
        monde.bugs1.affiche();
        
        
        monde.P1.setPos(new Point2D(4,4));
        monde.P1.setPtVieRegen(10);
        monde.afficheObjetMap();
        System.out.print("Déplacement de LAPIN1 sur la potion en (4,4) :");
        monde.bugs1.setPos(new Point2D(4,4));
        monde.interactionObjet(monde.bugs1);
        monde.bugs1.affiche();
        monde.afficheObjetMap();
       
        
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        
        config.setTitle("WoE");
        config.setIdleFPS(60);
        
        config.setWindowedMode(GameScreen.WIDTH, GameScreen.HEIGHT);
        new Lwjgl3Application(new Boot(monde), config);
        
    }
}
