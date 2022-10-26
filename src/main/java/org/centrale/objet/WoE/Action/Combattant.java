/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Interface.java to edit this template
 */
package org.centrale.objet.WoE.Action;

import org.centrale.objet.WoE.Creature.Creature;

/**
 *
 * @author François MARIE et Rémi RAVELLI
 */
public interface Combattant {

    /**
     * Combat avec une créature
     * @param c Créature adverse
     * @return boolean : si l'attaque est un succès
     */
    public boolean combattre(Creature c);
}
