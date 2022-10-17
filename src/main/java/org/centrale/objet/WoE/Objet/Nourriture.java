/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Objet;

import org.centrale.objet.WoE.Action.Effect;
import org.centrale.objet.WoE.Action.HasEffect;

/**
 *
 * @author inky19
 */
public abstract class Nourriture extends Objet implements Utilisable, HasEffect {
    
    Effect effect;
    
    public Nourriture(){
        effect = new Effect();
    }
    
    public Nourriture(int duration, int effect, int modifier){
        this.effect = new Effect(duration, effect, modifier);
    }

    public Effect getEffect() {
        return effect;
    }
    
    
}
