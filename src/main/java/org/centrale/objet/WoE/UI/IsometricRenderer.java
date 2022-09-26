/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.math.Vector2;
import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.World;

/**
 *
 * @author inky19
 */
public class IsometricRenderer {

    public static final int TILE_WIDTH = 64;
    public static final int TILE_HEIGHT = 32;

    private Texture empty;
    private Texture tileGrass1;
    private Texture tileStoneConcentric;
    private World monde;
    private Texture wolf;
    private Texture paysan;
    private Texture lapin;
    private Texture guerrier;
    private Texture archer;

    public IsometricRenderer(World monde) {
        tileGrass1 = new Texture(Gdx.files.internal("data/textures/tiles/grass1.png"));
        tileStoneConcentric = new Texture(Gdx.files.internal("data/textures/tiles/stone_concentric.png"));
        wolf = new Texture(Gdx.files.internal("data/textures/entity/monster/wolf.png"));
        paysan = new Texture(Gdx.files.internal("data/textures/entity/personnage/paysan.png"));
        archer = new Texture(Gdx.files.internal("data/textures/entity/personnage/archer.png"));
        guerrier = new Texture(Gdx.files.internal("data/textures/entity/personnage/guerrier.png"));
        lapin = new Texture(Gdx.files.internal("data/textures/entity/monster/lapin.png"));
        this.monde = monde;
    }

    public Vector2 toWindowPos(int x, int y) {
        float winX = (x - y) * (TILE_WIDTH / 2f);
        float winY = (x + y) * (TILE_HEIGHT / 2f);
        return new Vector2(winX, winY);
    }

    public Vector2 toIsometric(float x, float y) {
        float tileX = -x / (2*TILE_WIDTH) - y / (2*TILE_HEIGHT);
        float tileY = y / (2*TILE_HEIGHT) - x / (2*TILE_WIDTH);
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
        }
    }

    
    
    public void drawGrid(SpriteBatch batch) {
        Vector2 pos, mousePos;
        for (int i = monde.SIZE - 1; i >= 0; i--) { // Ancien Y = ligne
            for (int j = monde.SIZE - 1; j >= 0; j--) { // Ancien X = colonne
                pos = this.toWindowPos(j, i);
                mousePos = toIsometric(Gdx.input.getX()-700,Gdx.input.getY()-285);
                System.out.println(Gdx.input.getX()+" "+Gdx.input.getY());
                System.out.println(mousePos.x+" "+mousePos.y);
                if (i == mousePos.x && j == mousePos.y) {
                    batch.draw(tileStoneConcentric, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
                }
                if (i==0 && j==0) {
                    batch.draw(tileStoneConcentric, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
                } else {
                    batch.draw(tileGrass1, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
                }

            }
        }
        
        // Affichage des entités
        // start = x de départ (point ([x,SIZE])
        // x = x le long de la diagonale
        // Diagonale haute
        for (int start = monde.SIZE - 1; start >= 0; start--) {
            for (int x = start; x < monde.SIZE; x++) {
                if (monde.mapEntites[x][monde.SIZE - 1 - x + start] != null) {
                    Entite e = monde.mapEntites[x][monde.SIZE - 1 - x + start];
                    this.drawEntite(e, batch);
                }
            }
        }
        
        // Diagonale basse
        for (int start = monde.SIZE - 2; start >= 0; start--) {
            for (int x = 0; x <= start; x++) {
                if (monde.mapEntites[x][start - x] != null) {
                    Entite e = monde.mapEntites[x][start - x];
                    this.drawEntite(e, batch);
                }
            }
        }

        /*
        for (Entite p : monde.entites) {
            pos = this.toWindowPos(p.getPos().getX(), p.getPos().getY());
            
        }
         */
        float wolfx = monde.wolfie.getPos().getX();
        float wolfy = monde.wolfie.getPos().getY();
        //batch.draw(wolf, (wolfx - wolfy) * (TILE_WIDTH / 2f), (wolfx + wolfy) * (TILE_HEIGHT / 2f) + TILE_HEIGHT / 4, TILE_WIDTH, TILE_HEIGHT);

        //pos = this.toWindowPos(monde.peon.getPos().getX(), monde.peon.getPos().getY());
        //batch.draw(paysan, pos.x, pos.y + TILE_HEIGHT/4f, TILE_WIDTH, TILE_HEIGHT*2);
    }
}
