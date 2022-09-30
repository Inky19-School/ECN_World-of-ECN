/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.graphics.Color;
import com.badlogic.gdx.graphics.Texture;
import com.badlogic.gdx.graphics.g2d.BitmapFont;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.utils.viewport.FitViewport;
import org.centrale.objet.WoE.Creature.*;

/**
 *
 * @author inky19
 */
public class Tileinfo extends ShapeRenderer{
    
    private FitViewport infoViewport;

    private Vector2 pos;
    private float width;
    private float height;
    private float radius;
    
    private Texture texture;
    private Texture heart;
    private int ptVie;
    
    
    private BitmapFont font;
    
    
    public Tileinfo(){
        infoViewport = new FitViewport(1280, 640);
        width = 100;
        height = 100;
        radius = 10;
        ptVie = -1;
        pos = new Vector2(50, 50);
        font = new BitmapFont();
        font.setColor(Color.BLACK);
        heart = new Texture(Gdx.files.internal("data/textures/ui/heart.png"));
        texture = new Texture(Gdx.files.internal("data/textures/entity/monster/wolf.png"));
    }
    
    public void draw(){
        float x = pos.x-width;
        float y = pos.y-height;
        
        if (width>0 && height>0){
            // Central rectangle
            super.rect(x + radius, y + radius, width - 2*radius, height - 2*radius);

            // Four side rectangles, in clockwise order
            super.rect(x + radius, y, width - 2*radius, radius);
            super.rect(x + width - radius, y + radius, radius, height - 2*radius);
            super.rect(x + radius, y + height - radius, width - 2*radius, radius);
            super.rect(x, y + radius, radius, height - 2*radius);

            // Four arches, clockwise too
            super.arc(x + radius, y + radius, radius, 180f, 90f);
            super.arc(x + width - radius, y + radius, radius, 270f, 90f);
            super.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
            super.arc(x + radius, y + height - radius, radius, 90f, 90f);
        }
        

        

    }
    
    public void drawTextures(SpriteBatch batch){
        if (ptVie>=0){
            batch.draw(heart, (pos.x)/2-width, (pos.y)/2-height, 32, 32);
        }
        if (texture!=null){
            batch.draw(texture, (pos.x)/2-width/2-10-texture.getWidth(), (pos.y)/2-30-texture.getHeight()*2, texture.getWidth()*2, texture.getHeight()*2);
        }
    }

    public void update(Vector2 tile, Entite[][] mapEntites){
        if ((tile.x>=0 && tile.x<mapEntites.length) && (tile.y>0 && tile.y<mapEntites[0].length)){
            
            Entite e = mapEntites[(int)(tile.x)][(int)(tile.y)];
            if (!(e == null)){
                width = 178;
                if (e instanceof Creature) {
                   ptVie = ((Creature) (e)).getPtVie();
                   height = 250;
                   texture = EntityTexture.getTexture(e);
                } else {
                    ptVie = -1;
                }
            } else {
                texture = null;
                ptVie = -1;
                width = 0;
                height = 0;
            }
        }

                 
    }
    public Vector2 getPos() {
        return pos;
    }

    public void setPos(Vector2 pos) {
        this.pos.x = pos.x;
        this.pos.y = pos.y;
    }

    public float getWidth() {
        return width;
    }

    public void setWidth(float width) {
        this.width = width;
    }

    public float getHeight() {
        return height;
    }

    public void setHeight(float height) {
        this.height = height;
    }

    public float getRadius() {
        return radius;
    }

    public void setRadius(float radius) {
        this.radius = radius;
    }
    
    
}
