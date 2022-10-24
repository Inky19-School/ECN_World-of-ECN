package org.centrale.objet.WoE.Objet;

/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */

import org.centrale.objet.WoE.Action.Deplacable;
import org.centrale.objet.WoE.Point2D;
import org.centrale.objet.WoE.World.Chunk;
import org.centrale.objet.WoE.World.World;

/**
 *
 * @author Rémi
 */
public class NuageToxique extends Objet implements Deplacable{

    public NuageToxique(Point2D pos) {
        super(pos,1);
    }
    
    public NuageToxique() {
        super();
    }

    @Override
    public void deplacer(Chunk chunk) {
        
    }
    
}
