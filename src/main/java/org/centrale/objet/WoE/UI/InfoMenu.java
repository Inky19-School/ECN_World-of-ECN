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
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import java.util.ArrayList;
import org.centrale.objet.WoE.Creature.*;
import org.centrale.objet.WoE.Objet.*;
import org.centrale.objet.WoE.Point2D;
import org.centrale.objet.WoE.World.Chunk;
import org.centrale.objet.WoE.World.World;

/**
 *
 * @author inky19
 */
public class InfoMenu extends ShapeRenderer {

    private Vector2 pos;
    private float width;
    private float height;
    private float radius;

    private InfoBox boxCrea;
    private InfoBox boxObj;
    private Texture texture;
    private Texture heart;
    private int ptVie;
    
    public static final int LINE_SIZE = 40;
    
    private BitmapFont font;

    public InfoMenu() {
        width = 100;
        height = 100;
        radius = 10;
        ptVie = -1;
        pos = new Vector2(50, 50);
        Skin skin = new Skin(Gdx.files.internal("data/gui/uiskin.json"));
        font = skin.getFont("small-font");
        font.setColor(Color.BLACK);
        
        boxCrea = new InfoBox();
        boxObj = new InfoBox();
        
        heart = new Texture(Gdx.files.internal("data/textures/ui/heart.png"));
        texture = new Texture(Gdx.files.internal("data/textures/entity/monster/wolf.png"));
    }

    public void draw() {
        float x = pos.x - width;
        float y = pos.y - height;

        if (width > 0 && height > 0) {
            // Central rectangle
            super.rect(x + radius, y + radius, width - 2 * radius, height - 2 * radius);

            // Four side rectangles, in clockwise order
            super.rect(x + radius, y, width - 2 * radius, radius);
            super.rect(x + width - radius, y + radius, radius, height - 2 * radius);
            super.rect(x + radius, y + height - radius, width - 2 * radius, radius);
            super.rect(x, y + radius, radius, height - 2 * radius);

            // Four arches, clockwise too
            super.arc(x + radius, y + radius, radius, 180f, 90f);
            super.arc(x + width - radius, y + radius, radius, 270f, 90f);
            super.arc(x + width - radius, y + height - radius, radius, 0f, 90f);
            super.arc(x + radius, y + height - radius, radius, 90f, 90f);
        }

    }
    
    private void drawBox(SpriteBatch batch, float x, float y, InfoBox b){
        if (b.getClassName() != null) {
            int line = 1;
             
            font.draw(batch, b.getClassName(), x-width, y-20);
            Texture texture = b.getTexture();
            int txtrHeight = 0;
            try {
                txtrHeight = texture.getHeight();
            } catch (NullPointerException ex) {
                txtrHeight = 0;
                System.out.println("Texture invalide !");
                texture = heart;
            }
            
            
            if (b.getName() != ""){
                font.draw(batch, b.getName(), x-width+(b.getClassName().length()/2)*12, y-50-txtrHeight * 2);
                line++;
            }
            batch.draw(texture, x - width / 2 - 10 - texture.getWidth(), y - 50 - txtrHeight * 2, texture.getWidth() * 2, txtrHeight * 2);
            ArrayList<ArrayList<Texture>> icons = b.getIcons();
            ArrayList<ArrayList<String>> infos = b.getInfos();
            float textY = y-50-(txtrHeight*2);
            for (int i=0; i<icons.size(); i++){
                
                batch.draw(icons.get(i).get(0), x-width, textY-(line)*LINE_SIZE, 32, 32);
                font.draw(batch, infos.get(i).get(0), x-width+40, textY-(line)*LINE_SIZE+20);
                if (icons.get(i).size()>1){
                    batch.draw(icons.get(i).get(1), x-width/2-10, textY-(line)*LINE_SIZE, 32, 32);
                    font.draw(batch, infos.get(i).get(1), x-width/2+30, textY-(line)*LINE_SIZE+20);
                }
                line++;
            }
        }
    }

    public void drawTextures(SpriteBatch batch) {
        float x = pos.x/2;
        float y = pos.y/2;
        
        drawBox(batch, x, y, boxCrea);
        if (boxCrea.getHeight() > 0){
            drawBox(batch, x, y-boxCrea.getHeight()-40, boxObj);
        } else {
            drawBox(batch, x, y, boxObj);
        }
        
        
        if (ptVie >= 0) {
            x = (pos.x) / 2 - width; 
            y = (pos.y) / 2 - height;
            
            batch.draw(heart, x, y, 32, 32);
            font.draw(batch, " " + ptVie, x+32, y+25);
        }
        if (texture != null) {
            
        }
    }

    public void update(Vector2 tile, World monde) {
        height = 0;
        width = 0;
        Point2D posCh = monde.getActiveChunks()[1][1].getPos();
        int chx = posCh.getX();
        int chy = posCh.getY();
        if ((tile.x >= (chx-1)*Chunk.SIZE && tile.x < (chx+1)*Chunk.SIZE*2) && (tile.y >= (chy-1)*Chunk.SIZE && tile.y < (chy+1)*Chunk.SIZE*2)) {
            Entite e = monde.getCrea((int) (tile.x), (int) (tile.y));
            Objet o = monde.getObj((int) (tile.x),(int) (tile.y));
            
            if (e instanceof Creature) {
                boxCrea.update((Creature)(e));
                height += boxCrea.getHeight() + 20;
                
            } else {
                boxCrea = new InfoBox();
            }    
            
            if (o != null){
                boxObj.update(o);
                height += boxObj.getHeight() + 20;
                if (e != null){
                    height += 20;
                }
            } else {
                boxObj = new InfoBox();
            }    
            
            if (height != 0){
                width = 178;
                height += 20;
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
