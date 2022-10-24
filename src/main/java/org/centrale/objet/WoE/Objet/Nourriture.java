/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Objet;

import org.centrale.objet.WoE.Action.Effect;
import org.centrale.objet.WoE.Action.HasEffect;
import org.centrale.objet.WoE.Point2D;

/**
 *
 * @author inky19
 */
public abstract class Nourriture extends Objet implements Utilisable, HasEffect {
    
    Effect effect;
    
    public Nourriture(){
        effect = new Effect();
    }

    public Nourriture(Point2D pos){
        super(pos,1);
    }    

    public Nourriture(Point2D pos, int duration, int effect, int modifier){
        super(pos,1);
        this.effect = new Effect(duration, effect, modifier);
    }

    public Nourriture(int duration, int effect, int modifier){
        this.effect = new Effect(duration, effect, modifier);
    }
    
    
    public Effect getEffect() {
        return effect;
    }
    
    
}
