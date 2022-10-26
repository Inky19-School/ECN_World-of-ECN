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
import java.util.Arrays;
import java.util.Comparator;
import java.util.LinkedList;
import java.util.List;
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
import org.centrale.objet.WoE.World.Chunk;
import org.centrale.objet.WoE.World.World;

/**
 *
 * @author François MARIE et Rémi RAVELLI
 */
public class SaveManager {
    private static final String SEP = "_"; // Caractère délimiteur dans le fichier texte.
    /**
     * Retourne la liste des mondes, sauvegardés dans le dossier save, triés par date de dernière modification
     * @return worlds 
     */
    public static File[] getWorlds() {
        File[] worlds = new File("save").listFiles();
        Arrays.sort(worlds, Comparator.comparingLong(File::lastModified).reversed());
        return worlds;
    }

    public SaveManager() {
    }
    
    /**
     * Sauvegarde un effet d'un objet
     * @param effect
     * @return 
     */
    private static String saveEffect(Effect effect) {
        return effect.getDuration() + SEP + effect.getType() + SEP + effect.getModifier();
    }  
    
    /**
     * Sauvegarde une entité dans son fichier de chunk
     * @param e
     * @return 
     */
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
                Integer distAttMax = ((Personnage) c).getDistAttMax();
                if ((((Personnage) c).getNom()).equals("")) {
                    line += SEP + distAttMax + SEP + " " + SEP + ((Personnage) c).getDistAttMax();
                } else {
                    line += SEP + distAttMax + SEP + ((Personnage) c).getNom() + SEP + ((Personnage) c).getDistAttMax(); //A MODIFIER
                }
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
                line += SEP + ((PotionSoin) o).getPtVieRegen();
            }
            if (o instanceof NuageToxique) {
                line += SEP + saveEffect(((NuageToxique) o).getEffect());
            }
        }
        return line;
    }
    
    /**
     * Sauvegarde le fichier du joueur
     * @param folder
     * @param player
     * @throws IOException 
     */
    private static void savePlayer(File folder, Joueur player) throws IOException {
        // Creating player folder
        File playerFolder = new File(folder+"/player");
        if (!playerFolder.exists()){
            playerFolder.mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(playerFolder + "/" + player.getName()))) {
            bw.write("Player" + SEP + player.getPlayer().getChPos().getX() + SEP + player.getPlayer().getChPos().getX());
            bw.write(SEP + saveEntity(player.getPlayer()));
            bw.newLine();
            LinkedList<Objet> inventaire = player.getInventaire();
            LinkedList<Effect> effects = player.getEffects();
            for (Objet o : inventaire) {
                bw.write("Inventaire" + SEP + saveEntity(o));
                bw.newLine();
            }
            for (Effect eff : effects) {
                bw.write("Effets" + SEP + saveEffect(eff));
                bw.newLine();
            }
            bw.close();
        }
    }
    
    /**
     * Sauvegarde le monde dans un dossier, décomposé en chunks
     * @param monde Monde à sauvegarder
     */
    public static void saveWorld(World monde) {
        // Creating main folder
        File folder = new File("save/"+ monde.getName());
        if (!folder.exists()){
            folder.mkdirs();
        }
        // Saving chunks
        for (int i=0;i<3;i++) {
            for (int j=0;j<3;j++) {
                try {
                    saveChunk(folder,monde,monde.getActiveChunks()[i][j], new Point2D(i,j));
                } catch(IOException e) {
                    
                }
            }
        }
        try {
            savePlayer(folder,monde.getJoueur());
        } catch (IOException ex) {
            System.err.println("Could not save player "+monde.getJoueur().getName());
        }
        
    }
    
    /**
     * Sauvegarde un chunk dans un fichier
     * @param folder Dossier cible
     * @param monde Monde du chunk
     * @param chunk Chunk à sauvegarder
     * @param chPos Position du chunk
     * @throws IOException 
     */
    public static void saveChunk(File folder,World monde,Chunk chunk, Point2D chPos) throws IOException {
        // Creating chunk folder
        File chunkFolder = new File(folder+"/chunk");
        if (!chunkFolder.exists()){
            chunkFolder.mkdirs();
        }
        try (BufferedWriter bw = new BufferedWriter(new FileWriter(chunkFolder + "/" + folder.getName() + "_" + chunk.getPos().getX() + "_" + chunk.getPos().getY() + ".woe"))) {
            List<Entite> entities = chunk.getEntites();
            Personnage player = monde.getJoueur().getPlayer();
            for (Entite e : entities) {
                if (e != player) {
                    bw.write(saveEntity(e));
                    bw.newLine();
                }
            }
            bw.close();
        }

    }
    
    /**
     * Transforme une array de String en array de int.
     * Insère null si la conversion échoue.
     * @param arrStr
     * @return Array de int
     */
    private static Integer[] toIntArray(String[] arrStr) {
        Integer[] arrInt = new Integer[arrStr.length];
        Integer arg;
        for (int i=0; i < arrStr.length;i++) {
            try {
                arg = Integer.parseInt(arrStr[i]);
            } catch(NumberFormatException e) {
                arg = null;
            }
            arrInt[i] = arg;
        }
        return arrInt;
    }
    
    
    /**
     * Charge une entité
     * @param line Ligne d'un fichier de chunk comportant une entité
     * @param chPos Position du chunk
     * @return L'entité
     */
    private static Entite loadEntity(String line, Point2D chPos) {
        String[] argStr = line.split(SEP);
        Integer[] argInt = toIntArray(argStr);
        Point2D pos = new Point2D(argInt[1],argInt[2]);
        switch (argStr[0]) {
            // Personnages
            case "Archer" :;
                return new Archer(pos,chPos,argInt[3],argInt[4],argInt[5],argInt[6],argInt[7],argInt[8],argStr[9],argInt[10], argInt[11]);
            case "Guerrier" : 
                return new Guerrier(pos,chPos,argInt[3],argInt[4],argInt[5],argInt[6],argInt[7],argInt[8],argStr[9]);
            case "Paysan" :
                return new Paysan(pos,chPos,argInt[3],argInt[4],argInt[5],argInt[6],argInt[7],argInt[8],argStr[9]);
            // Monstres
            case "Lapin" :
                return new Lapin(pos,chPos,argInt[3],argInt[4],argInt[5],argInt[6],argInt[7]);
            case "Loup" :
                return new Loup(pos,chPos,argInt[3],argInt[4],argInt[5],argInt[6],argInt[7]); 
            // Objets
            case "NuageToxique" :
                return new NuageToxique(pos, chPos, new Effect(argInt[3],argInt[4],argInt[5]));
            case "PotionSoin" : 
                return new PotionSoin(pos, chPos, argInt[3]);
            case "SuperMushroom" :
                return new SuperMushroom(pos, chPos, new Effect(argInt[3],argInt[4],argInt[5]));
            case "ToxicMushroom" :
                return new ToxicMushroom(pos, chPos, new Effect(argInt[3],argInt[4],argInt[5]));
            case "Epee" :
                return new Epee(pos, chPos,argInt[3],argInt[4],argInt[5]);
            default :
                return null;
        }
    }
    
    /**
     * Charge un monde
     * @param folder Dossier cible
     * @return Le monde chargé
     */
    public static World loadWorld(File folder) {
        System.out.println("Loading " + folder);
        Joueur player = new Joueur();
        World monde = new World(10, player ,folder.getName());
        Point2D initChunkPos = new Point2D(0,0);
        // For safety
        for (int i=0; i<3; i++){
            for (int j=0; j<3; j++){
                monde.getActiveChunks()[i][j] = new Chunk(i-1+initChunkPos.getX(), j-1+initChunkPos.getY());
            }
        }
        int loaded = 0;
        File chunks = new File(folder.getPath()+"/chunk");
        for (File file : chunks.listFiles()) {
            if (file.isFile()) {
                String[] fileNameInfo = file.getName().replaceFirst("[.][^.]+$", "").split(SEP); //Remove extension and get info
                int chX = Integer.parseInt(fileNameInfo[1]);
                int chY = Integer.parseInt(fileNameInfo[2]);
                int i = chX + 1 - initChunkPos.getX();
                int j = chY + 1 - initChunkPos.getY();
                if ( 0<=i && i<3 && 0<=j && j<3) {
                    try {
                        monde.getActiveChunks()[i][j] = loadChunk(file,new Point2D(chX,chY),monde);
                        System.out.print(".");
                        loaded++;
                    } catch(IOException e) {
                        System.out.print("X");
                    }
                }
            }
        }
        System.out.print(" || "+loaded+"/"+chunks.listFiles().length+" chunks succesfully loaded\n");
        File playerFolder = new File(folder.getPath()+"/player");
        try {
            player = loadPlayer(playerFolder.listFiles()[0]);
            monde.setPlayer(player);
            Point2D chPos = player.getPlayer().getChPos();
            int i = chPos.getX() + 1 - initChunkPos.getX();
            int j = chPos.getY() + 1 - initChunkPos.getY();
            if ( 0<=i && i<3 && 0<=j && j<3) {
                monde.getActiveChunks()[i][j].addEntity(player.getPlayer());
            }
        } catch (IOException ex) {
            System.out.println("Player couldn't be loaded");
        }
        return monde;
    }
    
    /**
     * Charge un chunk depuis un fichier
     * @param file Fichier cible
     * @param chPos Position du chunk
     * @param monde Monde du chunk
     * @return Le chunk chargé
     * @throws FileNotFoundException
     * @throws IOException 
     */
    public static Chunk loadChunk(File file, Point2D chPos, World monde) throws FileNotFoundException, IOException  {
        Chunk chunk = new Chunk(chPos.getX(),chPos.getY());
        BufferedReader br = new BufferedReader(new FileReader(file.getAbsoluteFile()));
        String line = br.readLine();
        Joueur p = new Joueur();
        while (line != null) {
            if (line.split(SEP)[0].equals("Player")) { 
                Entite e = loadEntity(line.substring(("Player"+SEP).length()),chPos);
                p = new Joueur((Personnage)e);
                monde.setPlayer(p);
                chunk.addEntity(e);
            }else if(line.split(SEP)[0].equals("Inventaire")) {
                Entite e = loadEntity(line.substring(("Inventaire"+SEP).length()),chPos);
                p.getInventaire().add((Objet)e);
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
    
    /**
     * Charge le fichier du joueur
     * @param file Fichier cible
     * @return Le joueur chargé
     * @throws FileNotFoundException
     * @throws IOException 
     */
    private static Joueur loadPlayer(File file) throws FileNotFoundException, IOException {
        BufferedReader br = new BufferedReader(new FileReader(new File(file.getAbsolutePath())));
        String line = br.readLine();
        String[] info = line.split(SEP,4);
        Point2D chPos = new Point2D(Integer.parseInt(info[1]), Integer.parseInt(info[2]));
        Joueur p = new Joueur((Personnage)loadEntity(info[3], chPos));
        System.out.println(p.getPlayer());
        while (line != null) {
            if(line.split(SEP)[0].equals("Inventaire")) {
                Entite e = loadEntity(line.substring(("Inventaire"+SEP).length()),chPos);
                p.getInventaire().add((Objet)e);          
            } else if (line.split(SEP)[0].equals("Effect")) {
                
            }
            line = br.readLine();
        }
        br.close();
        return p;
    }
}
