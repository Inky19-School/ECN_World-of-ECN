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
    private int type;
    private int modifier;
    

    public int getDuration() {
        return duration;
    }

    public int getType() {
        return type;
    }

    public int getModifier() {
        return modifier;
    }

    public Effect(int duration, int type, int modifier) {
        this.duration = duration;
        this.type = type;
        this.modifier = modifier;
    }
    
    public Effect(Effect effect) {
        this.duration = effect.getDuration();
        this.type = effect.getType();
        this.modifier = effect.getModifier();
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
