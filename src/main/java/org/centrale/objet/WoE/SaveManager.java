/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Classes/Class.java to edit this template
 */
package org.centrale.objet.WoE;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.lang.reflect.Constructor;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.sql.DriverManager;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.Set;
import java.util.StringTokenizer;
import java.util.logging.Level;
import java.util.logging.Logger;
import java.util.stream.Collectors;
import java.util.stream.Stream;
import javax.swing.text.html.parser.Entity;
import org.centrale.objet.WoE.Action.Effect;
import org.centrale.objet.WoE.Creature.Archer;
import org.centrale.objet.WoE.Creature.Creature;
import org.centrale.objet.WoE.Creature.Entite;
import org.centrale.objet.WoE.Creature.Guerrier;
import org.centrale.objet.WoE.Creature.Lapin;
import org.centrale.objet.WoE.Creature.Loup;
import org.centrale.objet.WoE.Creature.Monstre;
import org.centrale.objet.WoE.Creature.Paysan;
import org.centrale.objet.WoE.Creature.Personnage;
import org.centrale.objet.WoE.Objet.Epee;
import org.centrale.objet.WoE.Objet.Nourriture;
import org.centrale.objet.WoE.Objet.NuageToxique;
import org.centrale.objet.WoE.Objet.Objet;
import org.centrale.objet.WoE.Objet.PotionSoin;
import org.centrale.objet.WoE.Objet.SuperMushroom;
import org.centrale.objet.WoE.Objet.ToxicMushroom;
import org.centrale.objet.WoE.UI.PlayerInput;
import org.centrale.objet.WoE.World.Chunk;
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
                if ((((Personnage) c).getNom()).equals("")) {
                    line += SEP + "Jean" + SEP + ((Personnage) c).getDistAttMax();
                }
                line += SEP + ((Personnage) c).getNom() + SEP + ((Personnage) c).getDistAttMax(); //A MODIFIER
                if (c instanceof Archer) {
                    line += SEP + ((Archer) c).getDegAttDist() + SEP + ((Archer) c).getNbFleches() + SEP + ((Archer) c).getDistAttMax();
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
    
    private static Entite loadEntity(String line, Point2D chPos) {
        StringTokenizer tokenizer = new StringTokenizer(line, SEP);
        String type = tokenizer.nextToken();
        if (type.equals("Player")) {
            type = tokenizer.nextToken();
            System.out.println(tokenizer.toString());
        }
        int x = Integer.parseInt(tokenizer.nextToken());
        int y = Integer.parseInt(tokenizer.nextToken());
        Point2D pos = new Point2D(x,y);
        
        Class entityType = Paysan.class;// = Class.forName("org.centrale.objet.WoE." + type);
        switch (type) {
            case "Archer" :
                entityType = Archer.class;
                break;
            case "Guerrier" : 
                entityType = Guerrier.class;
                break;
            case "NuageToxique" :
                entityType = NuageToxique.class;
                break;
            case "PotionSoin" : 
                entityType = PotionSoin.class;
                break;
            case "SuperMushroom" :
                entityType = SuperMushroom.class;
                break;
            case "ToxicMushroom" :
                entityType = ToxicMushroom.class; 
                break;
            case "Epee" :
                entityType = Epee.class;
                break;
            case "Lapin" :
                entityType = Lapin.class;
                break;
            case "Loup" :
                entityType = Loup.class;
                break;
            case "Paysan" :
                entityType = Paysan.class;
                break;          
        }
        // If Creature
        if (Creature.class.isAssignableFrom(entityType)) {
            Integer ptVie = Integer.parseInt(tokenizer.nextToken());
            Integer ptPar = Integer.parseInt(tokenizer.nextToken());
            Integer pagePar = Integer.parseInt(tokenizer.nextToken());
            Integer pageAtt = Integer.parseInt(tokenizer.nextToken());
            Integer degAtt = Integer.parseInt(tokenizer.nextToken());
            // Personnage
            if (Personnage.class.isAssignableFrom(entityType)) {
                String nom = tokenizer.nextToken(); 
                Integer distAttMax = Integer.parseInt(tokenizer.nextToken());
                switch (type) {
                    case ("Archer") :
                        Integer degAttDist = Integer.parseInt(tokenizer.nextToken());
                        Integer nbFleches = Integer.parseInt(tokenizer.nextToken());
                        return new Archer(nom, ptVie, degAtt, ptPar, pageAtt, pagePar, distAttMax, pos, chPos, nbFleches, degAttDist);
                    case ("Guerrier") :
                        return new Guerrier(nom, ptVie, degAtt, ptPar, pageAtt, pagePar, distAttMax, pos, chPos);
                    case ("Paysan") :
                        return new Paysan(nom, ptVie, degAtt, ptPar, pageAtt, pagePar, distAttMax, pos, chPos);                       
                }
            } else if (Monstre.class.isAssignableFrom(entityType)) {
                switch (type) {
                    case ("Loup") :
                        return new Loup(pos, chPos, ptVie, ptPar, pagePar, pageAtt, degAtt);
                    case ("Lapin") :
                        return new Lapin(pos, chPos, ptVie, ptPar, pagePar, pageAtt, degAtt);                       
                }               
            }
        } else if (Objet.class.isAssignableFrom(entityType)) {
            if (Nourriture.class.isAssignableFrom(entityType)) {
                tokenizer.nextToken(); 
                Integer duration = Integer.parseInt(tokenizer.nextToken());
                Integer effect = Integer.parseInt(tokenizer.nextToken());
                Integer modifier = Integer.parseInt(tokenizer.nextToken());
                Effect eff = new Effect(duration,effect,modifier);
                switch (type) {
                    case("ToxicMushroom") :
                        return new ToxicMushroom(pos);
                    case("SuperMushRoom") :
                        return new SuperMushroom(pos);    
                }       
            } else {
                switch (type) {
                    case "PotionSoin" :
                        Integer ptVieRegen = Integer.parseInt(tokenizer.nextToken());
                        tokenizer.nextToken();
                        Integer duration = Integer.parseInt(tokenizer.nextToken());
                        Integer effect = Integer.parseInt(tokenizer.nextToken());
                        Integer modifier = Integer.parseInt(tokenizer.nextToken());
                        Effect eff = new Effect(duration,effect,modifier);
                        return new PotionSoin(pos,1,ptVieRegen);
                    case "NuageToxique" :
                        return new NuageToxique(pos);
                }
            }
        } 
        return null;
    }
    
    
    public static World loadWorld(File file) {
        Joueur player = new Joueur("Archer", "Jean-Pierre");
        World monde = new World(10, player , file.getName());
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                if (i==1&&j==1) {
                    try {
                        monde.getActiveChunks()[i][j] = loadChunk(file,new Point2D(i-1,j-1),monde);
                    } catch(IOException e) {
                        monde.getActiveChunks()[i][j] = new Chunk(i-1, j-1);
                    }
                } else {
                    monde.getActiveChunks()[i][j] = new Chunk(i-1, j-1);
                }
            }
        }
        return monde;
    }
    
    
    public static Chunk loadChunk(File file, Point2D chPos, World monde) throws FileNotFoundException, IOException  {
        Chunk chunk = new Chunk(chPos.getX(),chPos.getY());
        BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
        String line = br.readLine();
        Joueur p;
        while (line != null) {
            if (line.split(SEP)[0].equals("Player")) { 
                Entite e = loadEntity(line,chPos);
                monde.setPlayer(new Joueur((Personnage)e));
                chunk.addEntity(e);
            }  else {
                Entite e = loadEntity(line,chPos);
                if (e != null) {
                    chunk.addEntity(e);    
                }
            }

            line = br.readLine();
        }
        br.close();
        
        return chunk;
    }
    
}
