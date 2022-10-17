/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

import java.io.BufferedWriter;
import java.io.File;
import java.io.FileWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import org.centrale.objet.WoE.Action.Effect;
import org.centrale.objet.WoE.Creature.Archer;
import org.centrale.objet.WoE.Creature.Creature;
import org.centrale.objet.WoE.Creature.Entite;
import org.centrale.objet.WoE.Creature.Monstre;
import org.centrale.objet.WoE.Creature.Personnage;
import org.centrale.objet.WoE.Objet.Nourriture;
import org.centrale.objet.WoE.Objet.Objet;
import org.centrale.objet.WoE.Objet.PotionSoin;
import org.centrale.objet.WoE.World.World;

/**
 *
 * @author Rémi
 */
public class SaveManager {
    private static final String SEP = " ";

    public static File[] getWorlds() {
        File folder = new File("save");
        return folder.listFiles();
    }
    
    
    public SaveManager() {
    }
    
    private static String saveEffect(Effect effect) {
        return "Effect" + SEP + effect.getDuration() + SEP + effect.getEffect() + SEP + effect.getModifier();
    }  
    
    private static String saveEntity(Entite e) {
        String line = e.getClass().getSimpleName() + SEP + e.getPos().getX() + SEP + e.getPos().getY();
        
        if (e instanceof Creature) {
            Creature c = (Creature) e;
            Integer ptVie = c.getPtVie();
            Integer ptPar = c.getPtPar();
            Integer pagePar = c.getPagePar();
            Integer pageAtt = c.getPageAtt();
            Integer degAtt = c.getDegAtt();
            line += SEP + ptVie + SEP + ptPar + SEP + pagePar + SEP + pageAtt + SEP + degAtt;
            if (c instanceof Personnage) {
                line += " " + ((Personnage) c).getNom();
                
                if (c instanceof Archer) {
                    line += SEP + ((Archer) c).getDegAttDist() + SEP + ((Archer) c).getNbFleches();
                }
            } else if (c instanceof Monstre) {
            }
        } else if (e instanceof Objet) {
            Objet o = (Objet) e;
            if (o instanceof Nourriture) {
                line += SEP + saveEffect(((Nourriture) o).getEffect());
            }
            if (o instanceof PotionSoin) {
                line += SEP + ((PotionSoin) o).getPtVieRegen() + SEP + saveEffect(((PotionSoin) o).getEffect());
            }
        }
        return line;
    }
    
    private static String savePlayer(Joueur player) {
        String lines = "Player" + SEP + saveEntity(player.getPlayer()) + "\n";
        LinkedList<Objet> inventaire = player.getInventaire();
        LinkedList<Effect> effects = player.getEffects();
        for (Objet o : inventaire) {
            lines += "Inventaire" + SEP + saveEntity(o) + "\n";
        }
        for (Effect eff : effects) {
            lines += "Inventaire" + SEP + saveEffect(eff) + "\n";
        }
        return lines;
    }
    
    
    public static void saveWorld(World monde) {
        saveChunk(monde, new Point2D(1,1));
    }
    
    
    public static void saveChunk(World monde, Point2D chPos) {
        try (BufferedWriter bw = new BufferedWriter(new FileWriter("save/" + monde.getName() + " " + chPos.getX() + " " + chPos.getY() + ".txt"))) {
            List<Entite> entities = monde.getActiveChunks()[chPos.getX()][chPos.getY()].getEntites();
            Joueur player = monde.getJoueur();
            for (Entite e : entities) {
                if (e != player.getPlayer()) {
                    bw.write(saveEntity(e));
                    bw.newLine();
                }
            }
            bw.write(savePlayer(player));
            bw.close();
        } catch (IOException e) {
            e.printStackTrace();
        }

    }
    
}
