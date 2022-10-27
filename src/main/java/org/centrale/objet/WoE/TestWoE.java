/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;
import org.centrale.objet.WoE.UI.*;
/**
 *
 * @author François MARIE et Rémi RAVELLI
 */
public class TestWoE {

    /**
     * @param args the command line arguments
     */
    public static void main(String args[]) {
        
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        
        //World monde = new World();
        //monde.creerMondeAlea();
        config.setTitle("World of ECN");
        config.setIdleFPS(60);
        config.setWindowIcon("data/gui/icon3.png");
        config.setWindowedMode(GameScreen.WIDTH, GameScreen.HEIGHT);
        //new Lwjgl3Application(new Boot(monde), config);
        new Lwjgl3Application(new Boot(), config);
    }
}
