/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Objet;

/**
 *
 * @author inky19
 */
public abstract class Nourriture extends Objet {
    
    private int duration;
    private int effect;
    private int modifier;
    
    public Nourriture(){
        duration = 0;
        effect = 0;
        modifier = 0;
    }
    
    public Nourriture(int duration, int effect, int modifier){
        this.duration = duration;
        this.effect = effect;
        this.modifier = modifier;
    }

    public int getDuration() {
        return duration;
    }

    public int getEffect() {
        return effect;
    }

    public int getModifier() {
        return modifier;
    }
    
    
}
