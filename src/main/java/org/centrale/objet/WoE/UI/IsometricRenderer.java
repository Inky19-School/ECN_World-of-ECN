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
    
    public IsometricRenderer(World monde){
        tileGrass1 = new Texture(Gdx.files.internal("data/textures/tiles/grass1.png"));
        tileStoneConcentric = new Texture(Gdx.files.internal("data/textures/tiles/stone_concentric.png"));
        wolf = new Texture(Gdx.files.internal("data/textures/entity/monster/wolf.png"));
        paysan = new Texture(Gdx.files.internal("data/textures/entity/personnage/paysan.png"));
        this.monde = monde;
    }
    
    public Vector2 toWindowPos(int x, int y){
        float winX= (x - y) * (TILE_WIDTH / 2f);
        float winY = (x + y) * (TILE_HEIGHT / 2f);
        return new Vector2(winX, winY);
    }
    
    public Vector2 toIsometric(int x, int y) {
       float tileX = x/TILE_WIDTH + y/TILE_HEIGHT;
       float tileY = y/TILE_HEIGHT - x/TILE_WIDTH;
       return new Vector2((int) tileX,(int) tileY);
    }
    
    public void drawGrid(SpriteBatch batch){
        Vector2 pos;
        for (int i=25; i>=-25;i--){ // Ancien Y = ligne
            for (int j=25; j>=-25; j--){ // Ancien X = colonne
                //float x = (j - i) * (TILE_WIDTH / 2f);
                //float y = (j + i) * (TILE_HEIGHT / 2f);
                pos = this.toWindowPos(j, i);
                if (i==0 && j==0){
                    
                    batch.draw(tileStoneConcentric, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
                } else {
                    batch.draw(tileGrass1, pos.x, pos.y, TILE_WIDTH, TILE_HEIGHT);
                }
                
            }
        }
        

        for (Entite p: monde.entites){
            pos = this.toWindowPos(p.getPos().getX(), p.getPos().getY());
            if (p instanceof Loup){   
                batch.draw(wolf, pos.x, pos.y + TILE_HEIGHT/4f, TILE_WIDTH, TILE_HEIGHT);
            } else if (p instanceof Paysan){
                batch.draw(paysan, pos.x, pos.y + TILE_HEIGHT/4f, TILE_WIDTH, TILE_HEIGHT*2);
            }
        }

        
        float wolfx = monde.wolfie.getPos().getX();
        float wolfy = monde.wolfie.getPos().getY();
        batch.draw(wolf,(wolfx-wolfy)* (TILE_WIDTH / 2f),(wolfx + wolfy) * (TILE_HEIGHT / 2f)  + TILE_HEIGHT/4, TILE_WIDTH, TILE_HEIGHT);
        
        //pos = this.toWindowPos(monde.peon.getPos().getX(), monde.peon.getPos().getY());
        //batch.draw(paysan, pos.x, pos.y + TILE_HEIGHT/4f, TILE_WIDTH, TILE_HEIGHT*2);
    }
}
