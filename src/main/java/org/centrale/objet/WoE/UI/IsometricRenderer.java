/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
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
    
    public IsometricRenderer(World monde){
        tileGrass1 = new Texture(Gdx.files.internal("data/textures/tiles/grass1.png"));
        tileStoneConcentric = new Texture(Gdx.files.internal("data/textures/tiles/stone_concentric.png"));
        wolf = new Texture(Gdx.files.internal("data/textures/entity/monster/wolf.png"));
        this.monde = monde;
    }
    
    public void drawGrid(SpriteBatch batch){

        for (int i=15; i>=-15;i--){ // Ancien Y = ligne
            for (int j=15; j>=-15; j--){ // Ancien X = colonne
                float x = (j - i) * (TILE_WIDTH / 2f);
                float y = (j + i) * (TILE_HEIGHT / 2f);
                if (i==0 && j==0){
                    batch.draw(tileStoneConcentric, x, y, TILE_WIDTH, TILE_HEIGHT);
                } else {
                    batch.draw(tileGrass1, x, y, TILE_WIDTH, TILE_HEIGHT);
                }
                
            }
        }
        
        
        float wolfx = monde.wolfie.getPos().getX();
        float wolfy = monde.wolfie.getPos().getY();
        batch.draw(wolf,(wolfx-wolfy)* (TILE_WIDTH / 2f),(wolfx + wolfy) * (TILE_HEIGHT / 2f), TILE_WIDTH, TILE_HEIGHT);
    }
}
