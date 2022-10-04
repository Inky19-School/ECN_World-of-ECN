/* --------------------------------------------------------------------------------
 * WoE Tools
 * 
 * Ecole Centrale Nantes - Septembre 2022
 * Equipe pédagogique Informatique et Mathématiques
 * JY Martin
 * -------------------------------------------------------------------------------- */
package org.centrale.objet.WoE.sql;

import java.sql.Connection;
import java.sql.Driver;
import java.sql.DriverManager;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Timestamp;
import java.util.List;
import java.util.ResourceBundle;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.Objet.PotionSoin;
import org.centrale.objet.WoE.Point2D;
import org.centrale.objet.WoE.UI.EntityInfo;

import org.centrale.objet.WoE.World;

/**
 *
 * @author ECN
 */
public class DatabaseTools {

    private String login;
    private String password;
    private String url;
    private Connection connection;

    /**
     * Load infos
     */
    public DatabaseTools() {
        try {
            // Get Properties file
            ResourceBundle properties = ResourceBundle.getBundle(DatabaseTools.class.getPackage().getName() + ".database");


            // USE config parameters
            login = properties.getString("login");
            password = properties.getString("password");
            String server = properties.getString("server");
            String database = properties.getString("database");
            url = "jdbc:postgresql://" + server + "/" + database;

            // Mount driver
            Driver driver = DriverManager.getDriver(url);
            if (driver == null) {
                Class.forName("org.postgresql.Driver");
            }
        } catch (ClassNotFoundException | SQLException ex) {
            Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
        }
        this.connection = null;
    }

    /**
     * Get connection to the database
     */
    public void connect() {
        if (this.connection == null) {
            try {
                this.connection = DriverManager.getConnection(url, login, password);
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * Disconnect from database
     */
    public void disconnect() {
        if (this.connection != null) {
            try {
                this.connection.close();
                this.connection = null;
            } catch (SQLException ex) {
                Logger.getLogger(DatabaseTools.class.getName()).log(Level.SEVERE, null, ex);
            }
        }
    }

    /**
     * get Player ID
     * @param nomJoueur
     * @param password
     * @return
     */
    public Integer getPlayerID(String nomJoueur, String password) throws SQLException {
        String query = "SELECT steamid FROM Player WHERE Login=? AND Password=?";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, nomJoueur);
        stmt.setString(2, password);
        ResultSet res = stmt.executeQuery();
        if (res.next()){
            return res.getInt("steamid");
        }
        return null;
    }

    private int insertEntity(int save_id, Creature p) throws SQLException{
        String query = "INSERT INTO Entity(x,y,save_id) VALUES (?,?,?) RETURNING entity_id";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setInt(1, p.getPos().getX());
        stmt.setInt(2, p.getPos().getY());
        stmt.setInt(3, save_id);
        ResultSet res = stmt.executeQuery();
        if (res.next()){
            return res.getInt("entity_id");
        }
        return -1;
    }
    
    private void insertPerso(int save_id, Personnage p) throws SQLException{
        int entity_id = insertEntity(save_id, p);
        if (entity_id >= 0){
            String query = "INSERT INTO Humanoid(save_id, entity_id, hp, Type, Attack_pourcentage_cold_weapon, Block_pourcentage, Max_attack_distance, Attack_point_cold_weapon, inventory_id, arrow_nb, block_point) VALUES (?,?,?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, save_id);
            stmt.setInt(2, entity_id);
            stmt.setInt(3, p.getPtVie());
            stmt.setString(4, EntityInfo.getClassName(p));
            stmt.setInt(5, p.getPageAtt());
            stmt.setInt(6, p.getPagePar());
            stmt.setInt(7, p.getDistAttMax());
            stmt.setInt(8, p.getDegAtt());
            stmt.setInt(9, 0);
            if (p instanceof Archer){
                stmt.setInt(10, ((Archer) p).getNbFleches());
            } else {
                stmt.setInt(10, 0);
            }
            stmt.setInt(11, p.getPtPar());
            stmt.executeUpdate();
        }
    }
        
    private void insertMonstre(int save_id, Monstre m) throws SQLException{
        int entity_id = insertEntity(save_id, m);
        if (entity_id >= 0){
            String query = "INSERT INTO Monster(save_id, entity_id, hp, Type, Attack_pourcentage_natural_weapon, Dodge_pourcentage, inventory_id, Attack_point_natural_weapon, Dodge_point) VALUES (?,?,?,?,?,?,?,?,?)";
            PreparedStatement stmt = connection.prepareStatement(query);
            stmt.setInt(1, save_id);
            stmt.setInt(2, entity_id);
            stmt.setInt(3, m.getPtVie());
            stmt.setString(4, EntityInfo.getClassName(m));
            stmt.setInt(5, m.getPageAtt());
            stmt.setInt(6, m.getPagePar());
            stmt.setInt(7, 0);
            stmt.setInt(8,m.getDegAtt());
            stmt.setInt(9, m.getPtPar());
            stmt.executeUpdate();
        }
    }

    /**
     * save world to database
     * @param idJoueur
     * @param nomPartie
     * @param nomSauvegarde
     * @param monde
     */
    public void saveWorld(Integer idJoueur, String nomPartie, String nomSauvegarde, World monde) throws SQLException {
        String query = "SELECT game_id FROM Game WHERE Name = ? ";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, nomPartie);
        ResultSet res = stmt.executeQuery();
        int game_id = 0;
        if (res.next()){
            game_id =  res.getInt("game_id");
        }
        
        query = "INSERT INTO Save (game_id, Name, Date) VALUES (?,?,?) RETURNING save_id";
        stmt = connection.prepareStatement(query);
        stmt.setInt(1, game_id);
        stmt.setString(2, nomSauvegarde);
        stmt.setTimestamp(3, new Timestamp(System.currentTimeMillis()));
        res = stmt.executeQuery();
        int save_id = 0;
        if (res.next()){
            save_id =  res.getInt("save_id");
        }
        
        List<Entite> entites = monde.entites;
        
        for (Entite e: entites){
            if (e instanceof Personnage ){
                this.insertPerso(save_id, (Personnage) e);
            } else if (e instanceof Monstre){
                this.insertMonstre(save_id, (Monstre) e);
            }
        }
        
    }
       

    /**
     * get world from database
     * @param idJoueur
     * @param nomPartie
     * @param nomSauvegarde
     * @param monde
     */
    public void readWorld(Integer idJoueur, String nomPartie, String nomSauvegarde, World monde) throws SQLException {
        String query = "SELECT game_id FROM Game WHERE Name = ? ";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, nomPartie);
        ResultSet res = stmt.executeQuery();
        int game_id = 0;
        if (res.next()){
            game_id =  res.getInt("game_id");
        }
        
        query = "SELECT MAX(save_id) AS last_save_id FROM Save WHERE (Name = ? AND game_id = ?)";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, nomSauvegarde);
        stmt.setInt(2, game_id);
        res = stmt.executeQuery();
        int save_id = 0;
        if (res.next()){
            save_id = res.getInt("last_save_id");
        }
        
        query = "SELECT * FROM Humanoid JOIN Entity USING (entity_id) WHERE Entity.save_id = ?";
        stmt = connection.prepareStatement(query);
        stmt.setInt(1, save_id);
        System.out.println(stmt);
        res = stmt.executeQuery();
        
        Entite e = null;
        String type;
        Point2D pos;
        while (res.next()){
            type = res.getString("Type");
            pos = new Point2D(res.getInt("x"), res.getInt("y"));
            switch (type){
                case "Paysan":       
                    e = new Paysan("",res.getInt("hp"),res.getInt("Attack_point_cold_weapon"),res.getInt("Block_pourcentage"), res.getInt("Attack_pourcentage_cold_weapon"),res.getInt("Block_pourcentage"),res.getInt("Max_attack_distance"), pos);
                    break;
                case "Guerrier":
                    e = new Guerrier("",res.getInt("hp"),res.getInt("Attack_point_cold_weapon"),res.getInt("Block_pourcentage"), res.getInt("Attack_pourcentage_cold_weapon"),res.getInt("Block_pourcentage"),res.getInt("Max_attack_distance"), pos);
                    break;
                case "Archer":
                    e = new Archer("",res.getInt("hp"),res.getInt("Attack_point_cold_weapon"),res.getInt("Block_pourcentage"), res.getInt("Attack_pourcentage_cold_weapon"),res.getInt("Block_pourcentage"),res.getInt("Max_attack_distance"), pos, res.getInt("arrow_nb"),10);
                    break;
            }   
            monde.entites.add(e);
            monde.mapCreature[pos.getX()][pos.getY()] = e;

        }
                    
        query = "SELECT * FROM Monster JOIN Entity USING (entity_id) WHERE Entity.save_id = ?";
        stmt = connection.prepareStatement(query);
        stmt.setInt(1, save_id);
        res = stmt.executeQuery();
        while (res.next()){
            type = res.getString("Type");
            pos = new Point2D(res.getInt("x"), res.getInt("y"));
            switch (type){
                case "Loup":
                    e = new Loup(pos, res.getInt("hp"), res.getInt("Dodge_point"), res.getInt("Dodge_pourcentage"), res.getInt("Attack_pourcentage_natural_weapon"), res.getInt("Attack_point_natural_weapon"));
                    break;
                case "Lapin":
                    e = new Lapin(pos, res.getInt("hp"), res.getInt("Dodge_point"), res.getInt("Dodge_pourcentage"), res.getInt("Attack_pourcentage_natural_weapon"), res.getInt("Attack_point_natural_weapon"));
                    break;
            }   
            monde.entites.add(e);
            monde.mapCreature[pos.getX()][pos.getY()] = e;

        }
        
        /*
        query = "SELECT * FROM Object_on_map JOIN Entity USING (entity_id) WHERE Entity.save_id = ?";
        stmt = connection.prepareStatement(query);
        stmt.setInt(1, save_id);
        res = stmt.executeQuery();
        while (res.next()){
            type = res.getString("Type");
            pos = new Point2D(res.getInt("x"), res.getInt("y"));
            switch (type){
                case "Potion de soin":
                    e = new PotionSoin(pos,1,res.getInt(""));
                    break;
            monde.entites.add(e);
            monde.mapEntites[pos.getX()][pos.getY()] = e;
        }
         */
        
        
        
    }
    
    public void removeWorld(int idJoueur, String nomPartie, String nomSauvegarde) throws SQLException{
        String query = "DELETE FROM Save WHERE save_id IN (SELECT save_id FROM Save JOIN Game WHERE Game.Name = ? AND Save.Name = ?) ";
        PreparedStatement stmt = connection.prepareStatement(query);
        stmt.setString(1, nomPartie);
        stmt.setString(2, nomSauvegarde);
        stmt.executeUpdate();
        query = "DELETE FROM Humanoid WHERE save_id IN (SELECT save_id FROM Save JOIN Game WHERE Game.Name = ? AND Save.Name = ?) ";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, nomPartie);
        stmt.setString(2, nomSauvegarde);
        stmt.executeUpdate();
        query = "DELETE FROM Monster WHERE save_id IN (SELECT save_id FROM Save JOIN Game WHERE Game.Name = ? AND Save.Name = ?) ";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, nomPartie);
        stmt.setString(2, nomSauvegarde);
        stmt.executeUpdate();
       query = "DELETE FROM Entity WHERE save_id IN (SELECT save_id FROM Save JOIN Game WHERE Game.Name = ? AND Save.Name = ?) ";
        stmt = connection.prepareStatement(query);
        stmt.setString(1, nomPartie);
        stmt.setString(2, nomSauvegarde);
        stmt.executeUpdate();
        
    }
}
