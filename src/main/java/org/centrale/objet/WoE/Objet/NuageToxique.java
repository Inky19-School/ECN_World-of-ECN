package org.centrale.objet.WoE.Objet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.centrale.objet.WoE.Action.Deplacable;
import org.centrale.objet.WoE.Action.Effect;
import org.centrale.objet.WoE.Point2D;
import org.centrale.objet.WoE.World.Chunk;
import org.centrale.objet.WoE.World.World;

/**
 *
 * @author Rémi
 */
public class NuageToxique extends Objet implements Deplacable{
    private Effect effect;

    public Effect getEffect() {
        return effect;
    }

    public NuageToxique(Point2D pos, Point2D chPos, Effect effect) {
        super(pos,chPos);
        this.effect = effect;
    }
    
    public NuageToxique(Point2D pos, Point2D chPos) {
        super(pos,chPos);
        this.effect = new Effect(5,Effect.HP,-3);
    }
    
    public NuageToxique() {
        super();
        this.effect = new Effect(5,Effect.HP,-3);
    }

    @Override
    public void deplacer(Chunk chunk) {
        
    }

    @Override
    public void affiche() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
    
}
