/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Objet;
import org.centrale.objet.WoE.Action.Effect;
import org.centrale.objet.WoE.Point2D;
/**
 *
 * @author inky19
 */
public class ToxicMushroom extends Nourriture{
    
    public ToxicMushroom(Point2D pos, Point2D chPos, Effect effect){
        super(pos, chPos,effect);
    } 

    public ToxicMushroom(Point2D pos, Point2D chPos){
        super(pos, chPos,4,Effect.ATK,-10);
    } 

    public ToxicMushroom(){
        super(4,Effect.ATK,-10);
    }

    @Override
    public void utiliser() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }

    @Override
    public void affiche() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
