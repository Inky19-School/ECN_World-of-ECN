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
import org.centrale.objet.WoE.Objet.Objet;
import org.centrale.objet.WoE.Objet.PotionSoin;
import org.centrale.objet.WoE.World;

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
    private final World monde;
    private final Texture wolf;
    private final Texture paysan;
    private final Texture lapin;
    private final Texture guerrier;
    private final Texture archer;
    private final Texture selected;
    private final Texture healPotion;

    public IsometricRenderer(World monde) {
        tileGrass1 = new Texture(Gdx.files.internal("data/textures/tiles/grass1.png"));
        tileStoneConcentric = new Texture(Gdx.files.internal("data/textures/tiles/stone_concentric.png"));
        wolf = new Texture(Gdx.files.internal("data/textures/entity/monster/wolf.png"));
        paysan = new Texture(Gdx.files.internal("data/textures/entity/personnage/paysan.png"));
        archer = new Texture(Gdx.files.internal("data/textures/entity/personnage/archer.png"));
        guerrier = new Texture(Gdx.files.internal("data/textures/entity/personnage/guerrier.png"));
        lapin = new Texture(Gdx.files.internal("data/textures/entity/monster/lapin.png"));
        selected = new Texture(Gdx.files.internal("data/textures/tiles/select.png"));
        healPotion = new Texture(Gdx.files.internal("data/textures/entity/object/healPotion.png"));
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

    public void drawEntite(Entite e, SpriteBatch batch) {
        Vector2 pos = this.toWindowPos(e.getPos().getX(), e.getPos().getY());
        if (e instanceof Loup) {
            batch.draw(wolf, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT);
        } else if (e instanceof Paysan) {
            batch.draw(paysan, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT * 2);
        } else if (e instanceof Paysan) {
            batch.draw(paysan, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT * 2);
        } else if (e instanceof Guerrier) {
            batch.draw(guerrier, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT * 2);
        } else if (e instanceof Archer) {
            batch.draw(archer, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT * 2);
        } else if (e instanceof Lapin) {
            batch.draw(lapin, pos.x, pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT);
        } else if (e instanceof PotionSoin) {
            batch.draw(healPotion, pos.x , pos.y + TILE_HEIGHT / 4f, TILE_WIDTH, TILE_HEIGHT);
        }
        
    }

    public void drawGrid(SpriteBatch batch, Vector3 mousePos) {
        Vector2 pos;
        for (int i = monde.SIZE - 1; i >= 0; i--) { // Ancien Y = ligne
            for (int j = monde.SIZE - 1; j >= 0; j--) { // Ancien X = colonne
                pos = this.toWindowPos(j, i);
                if (i == 0 && j == 0) {
                    batch.draw(tileStoneConcentric, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
                } else {
                    batch.draw(tileGrass1, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
                }

            }
        }
        pos = this.toIsometric(mousePos.x-TILE_WIDTH/2, mousePos.y);
        pos = this.toWindowPos(pos.x, pos.y);
        batch.draw(selected, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
        
        // Affichage des entités
        // start = x de départ (point ([x,SIZE])
        // x = x le long de la diagonale
        // Diagonale haute
        for (int start = monde.SIZE - 1; start >= 0; start--) {
            for (int x = start; x < monde.SIZE; x++) {
                //affichage créature
                if (monde.mapCreature[x][monde.SIZE - 1 - x + start] != null) {
                    Entite e = monde.mapCreature[x][monde.SIZE - 1 - x + start];
                    this.drawEntite(e, batch);
                }
                //affichage objet
                if (monde.mapObjets[x][monde.SIZE - 1 - x + start] != null) {
                    Objet e = monde.mapObjets[x][monde.SIZE - 1 - x + start];
                    this.drawEntite(e, batch);
                }
            }
        }

        // Diagonale basse
        for (int start = monde.SIZE - 2; start >= 0; start--) {
            for (int x = 0; x <= start; x++) {
                //affichage créature
                if (monde.mapCreature[x][start - x] != null) {
                    Entite e = monde.mapCreature[x][start - x];
                    this.drawEntite(e, batch);
                }
                //affichage objet
                if (monde.mapObjets[x][start-x] != null) {
                    Objet e = monde.mapObjets[x][start-x];
                    this.drawEntite(e, batch);
                }
            }
        }
    }
}
