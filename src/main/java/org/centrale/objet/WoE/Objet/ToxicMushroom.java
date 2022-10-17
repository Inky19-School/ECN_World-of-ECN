/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.Objet;
import org.centrale.objet.WoE.Action.Effect;
/**
 *
 * @author inky19
 */
public class ToxicMushroom extends Nourriture{

    private int modhp;
    public ToxicMushroom(){
        super(4,Effect.ATK,-10);
    }

    @Override
    public void utiliser() {
        throw new UnsupportedOperationException("Not supported yet."); // Generated from nbfs://nbhost/SystemFileSystem/Templates/Classes/Code/GeneratedMethodBody
    }
}
