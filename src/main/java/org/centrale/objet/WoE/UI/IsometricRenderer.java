
/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.Objet.NuageToxique;
import org.centrale.objet.WoE.Objet.Objet;
import org.centrale.objet.WoE.Objet.PotionSoin;
import org.centrale.objet.WoE.Objet.SuperMushroom;
import org.centrale.objet.WoE.Objet.ToxicMushroom;
import org.centrale.objet.WoE.Point2D;
import org.centrale.objet.WoE.World.Chunk;
import org.centrale.objet.WoE.World.World;

/**
 *
 * @author inky19
 */
public class IsometricRenderer {

    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 32;

    //private final Texture empty;
    private final Texture tileGrass1;
    private final Texture tileStoneConcentric;
    private final Texture tileStoneChiseled;
    private final World monde;
    private final Texture wolf;
    private final Texture paysan;
    private final Texture lapin;
    private final Texture guerrier;
    private final Texture archer;
    private final Texture selected;
    private final Texture healPotion;
    private final Texture toxicCloud;

    public IsometricRenderer(World monde) {
        tileGrass1 = new Texture(Gdx.files.internal("data/textures/tiles/grass1.png"));
        tileStoneConcentric = new Texture(Gdx.files.internal("data/textures/tiles/stone_concentric.png"));
        tileStoneChiseled = new Texture(Gdx.files.internal("data/textures/tiles/stone_chiseled.png"));
        wolf = new Texture(Gdx.files.internal("data/textures/entity/monster/wolf.png"));
        paysan = new Texture(Gdx.files.internal("data/textures/entity/personnage/paysan.png"));
        archer = new Texture(Gdx.files.internal("data/textures/entity/personnage/archer.png"));
        guerrier = new Texture(Gdx.files.internal("data/textures/entity/personnage/guerrier.png"));
        lapin = new Texture(Gdx.files.internal("data/textures/entity/monster/lapin.png"));
        selected = new Texture(Gdx.files.internal("data/textures/tiles/select.png"));
        healPotion = new Texture(Gdx.files.internal("data/textures/entity/object/healPotion.png"));
        toxicCloud = new Texture(Gdx.files.internal("data/textures/entity/object/toxicCloud.png"));
        //superMushroom = new Texture(Gdx.files.internal("data/textures/entity/object/super_mushroom.png"));
        //toxicM = new Texture(Gdx.files.internal("data/textures/entity/object/toxicCloud.png"));
        this.monde = monde;
    }

    public Vector2 toWindowPos(float x, float y) {
        float winX = (x - y) * (TILE_WIDTH / 2f);
        float winY = (x + y) * (TILE_HEIGHT / 2f);
        return new Vector2(winX, winY);
    }

    public Vector2 toIsometric(float x, float y) {
        float tileX = x / TILE_WIDTH + y / TILE_HEIGHT;
        float tileY = y / TILE_HEIGHT - x / TILE_WIDTH;
        if (tileX < 0){
            tileX--;
        }
        if (tileY < 0){
            tileY--;
        }
        return new Vector2((int) tileX, (int) tileY);
    }

    public void drawEntite(Entite e, SpriteBatch batch, Point2D chPos) {
        int chx = chPos.getX()*Chunk.SIZE;
        int chy = chPos.getY()*Chunk.SIZE;
        Texture texture = EntityInfo.getTexture(e);
        Vector2 pos = this.toWindowPos(e.getPos().getX()+chx, e.getPos().getY()+chy);
        if (e instanceof Loup) {
            batch.draw(texture, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT);
        } else if (e instanceof Paysan) {
            batch.draw(texture, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT * 2);
        } else if (e instanceof Paysan) {
            batch.draw(texture, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT * 2);
        } else if (e instanceof Guerrier) {
            batch.draw(texture, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT * 2);
        } else if (e instanceof Archer) {
            batch.draw(texture, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT * 2);
        } else if (e instanceof Lapin) {
            batch.draw(texture, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT);
        } else if (e instanceof PotionSoin) {
            batch.draw(texture, pos.x , pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT);
        } else if (e instanceof NuageToxique) {
            batch.draw(texture, pos.x , pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT);
        } else if (e instanceof SuperMushroom) {
            batch.draw(texture, pos.x , pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT);
        } else if (e instanceof ToxicMushroom) {
            batch.draw(texture, pos.x , pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT);
        }
        
    }

    public void drawGrid(SpriteBatch batch, Vector3 mousePos) {
        Vector2 pos;
        Chunk[][] activeChunks = monde.getActiveChunks();
        Chunk ch;
        Point2D chPos;
        int chx;
        int chy;
        for (int cx=0; cx<3; cx++){
            for (int cy=0; cy<3; cy++){
                ch = activeChunks[cx][cy];
                chPos = ch.getPos();
                chx = chPos.getX()*Chunk.SIZE;
                chy = chPos.getY()*Chunk.SIZE;
                for (int i = Chunk.SIZE - 1+chx; i >= chx; i--) { // Ancien Y = ligne
                    for (int j = Chunk.SIZE - 1+chy; j >= chy; j--) { // Ancien X = colonne
                        pos = this.toWindowPos(j, i);
                        if ((i == 0 || j == 0) || (i == 31 || j == 31)) {
                            batch.draw(tileStoneConcentric, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
                            if (i==0 && j==0){
                                batch.draw(tileStoneChiseled, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
                            }
                        } else {
                            batch.draw(tileGrass1, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
                        }

                    }
                }
            }
        }    
        pos = this.toIsometric(mousePos.x-TILE_WIDTH/2, mousePos.y);
        pos = this.toWindowPos(pos.x, pos.y);
        batch.draw(selected, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
        for (int cx=0; cx<3; cx++){
            for (int cy=0; cy<3; cy++){
                ch = activeChunks[cx][cy];
                // Affichage des entités
                // start = x de départ (point ([x,SIZE])
                // x = x le long de la diagonale
                // Diagonale haute
                for (int start = Chunk.SIZE - 1; start >= 0; start--) {
                    for (int x = start; x < Chunk.SIZE; x++) {
                        //affichage créature
                        if (ch.chCrea[x][Chunk.SIZE - 1 - x + start] != null) {
                            Entite e = ch.chCrea[x][Chunk.SIZE - 1 - x + start];
                            this.drawEntite(e, batch, ch.getPos());
                        }
                        //affichage objet
                        if (ch.chObj[x][Chunk.SIZE - 1 - x + start] != null) {
                            Objet e = ch.chObj[x][Chunk.SIZE - 1 - x + start];
                            this.drawEntite(e, batch, ch.getPos());
                        }
                    }
                }
                
                // Diagonale basse
                for (int start = Chunk.SIZE - 2; start >= 0; start--) {
                    for (int x = 0; x <= start; x++) {
                        //affichage créature
                        if (ch.chCrea[x][start - x] != null) {
                            Entite e = ch.chCrea[x][start - x];
                            this.drawEntite(e, batch, ch.getPos());
                        }
                        //affichage objet
                        if (ch.chObj[x][start-x] != null) {
                            Objet e = ch.chObj[x][start-x];
                            this.drawEntite(e, batch, ch.getPos());
                        }
                    }
                }
            }

        }


        /*
        for (Entite p : monde.entites) {
            pos = this.toWindowPos(p.getPos().getX(), p.getPos().getY());
            
        }
         */
        //float wolfx = monde.wolfie.getPos().getX();
        //float wolfy = monde.wolfie.getPos().getY();
        //batch.draw(wolf, (wolfx - wolfy) * (TILE_WIDTH / 2f), (wolfx + wolfy) * (TILE_HEIGHT / 2f) + TILE_HEIGHT / 4, TILE_WIDTH, TILE_HEIGHT);

        //pos = this.toWindowPos(monde.peon.getPos().getX(), monde.peon.getPos().getY());
        //batch.draw(paysan, pos.x, pos.y + TILE_HEIGHT/4f, TILE_WIDTH, TILE_HEIGHT*2);
    }
}