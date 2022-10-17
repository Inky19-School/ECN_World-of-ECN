/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE.Action;

/**
 *
 * @author Rémi
 */
public class Effect {
    public static final int ATK = 0;
    public static final int HP = 1;
    
    private int duration;
    private int effect;
    private int modifier;
    

    public int getDuration() {
        return duration;
    }

    public int getEffect() {
        return effect;
    }

    public int getModifier() {
        return modifier;
    }

    public Effect(int duration, int effect, int modifier) {
        this.duration = duration;
        this.effect = effect;
        this.modifier = modifier;
    }
    
    public void decreaseDuration() {
        duration--;
    }
    
    public boolean isFinished() {
        return duration <= 0;
    }
           
    public Effect() {
    }
    
}
