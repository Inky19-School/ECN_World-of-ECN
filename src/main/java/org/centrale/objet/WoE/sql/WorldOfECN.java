/* --------------------------------------------------------------------------------
 * ECN Tools
 * 
 * Ecole Centrale Nantes - Septembre 2022
 * Equipe pédagogique Informatique et Mathématiques
 * JY Martin
 * -------------------------------------------------------------------------------- */

package org.centrale.objet.WoE.sql;

import org.centrale.objet.WoE.World.World;
import java.sql.SQLException;
import org.centrale.objet.WoE.*;

/**
 *
 * @author ECN
 */
public class WorldOfECN {

    /**
     * main program
     * @param args
     */
    public static void main(String[] args) throws SQLException {
        World world = new World();
        //world.setPlayer("Saegusa");
        
        // Test phase
        DatabaseTools database = new DatabaseTools();

        // Save world
        database.connect();
        System.out.println("getname");
        Integer playerId = database.getPlayerID("Saegusa", "Mayumi");
        System.out.println(playerId);
        database.saveWorld(playerId, "Test Game 1", "Start", world);
        
        // Retreive World
        database.readWorld(playerId, "Test Game 1", "Start", world);
        database.disconnect();
    }
}
