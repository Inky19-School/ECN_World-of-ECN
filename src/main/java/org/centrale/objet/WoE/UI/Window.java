package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.backends.lwjgl3.Lwjgl3Application;
import com.badlogic.gdx.backends.lwjgl3.Lwjgl3ApplicationConfiguration;

public class Window {

    public void fen(){
        Lwjgl3ApplicationConfiguration config = new Lwjgl3ApplicationConfiguration();
        config.setIdleFPS(60);
        config.useVsync(true);
        config.setTitle("WoE");
        
        config.setWindowedMode(GameScreen.WIDTH, GameScreen.HEIGHT);
        
        //new Lwjgl3Application(new Boot(), config);
    }
}
