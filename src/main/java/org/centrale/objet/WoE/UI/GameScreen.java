/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import static java.lang.Math.max;
import static java.lang.Math.min;
import org.centrale.objet.WoE.Creature.Creature;
import static org.centrale.objet.WoE.UI.IsometricRenderer.TILE_WIDTH;
import org.centrale.objet.WoE.World;
import org.lwjgl.input.Mouse;

/**
 *
 * @author inky19
 */
public class GameScreen extends ScreenAdapter{

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 640;
    public static final float SMOOTHNESS = 10f;
    
    private float cameraSpeedX = 0;
    private float cameraSpeedY = 0;
    private float cameraBaseSpeed = 10;
    
    
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthographicCamera fixedCamera;
    private IsometricRenderer renderer;
    private PlayerInput input;
    private World monde;
    private Tileinfo infobox;
    private int x, y; // Vraie position caméra
    
    private Vector3 mousePos;
    private Vector2 selectedTile;
    
    private long timer;
    private long timerCamera;
    
    
    
    public GameScreen(SpriteBatch batch, World monde){
        this.batch = batch;
        this.input = new PlayerInput(this);
        this.monde = monde;
        timer = System.currentTimeMillis();
        timerCamera = System.currentTimeMillis();
        mousePos = new Vector3();
        infobox = new Tileinfo();
        selectedTile = new Vector2();
    }
    

    @Override
    public void show(){
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        fixedCamera = new OrthographicCamera(WIDTH, HEIGHT);
        
        camera.position.set(0, 0, 0);
        camera.zoom = 0.5f;
        fixedCamera.position.set(-WIDTH, WIDTH, 0);
        fixedCamera.zoom = 0.5f;
        
        infobox.setPos(new Vector2(WIDTH-20,HEIGHT-20));
        
        Gdx.input.setInputProcessor(input);
        
        renderer = new IsometricRenderer(monde);
    }
    
    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);
        batch.setProjectionMatrix(camera.combined);
        
        selectedTile = renderer.toIsometric(mousePos.x-TILE_WIDTH/2, mousePos.y);
   
        
        update();
        
        batch.begin();
        
        renderer.drawGrid(batch,mousePos);

        batch.end();
        
        batch.setProjectionMatrix(fixedCamera.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        infobox.begin(ShapeType.Filled);
        infobox.setColor(0.8f, 0.8f, 0.8f, 0.7f);
        infobox.update(selectedTile, monde.mapCreature);
        
        infobox.draw();
        infobox.end();
        
        Gdx.gl.glDisable(GL20.GL_BLEND);
        
        batch.begin();
        infobox.drawTextures(batch);
        batch.end();
        
        
    }
    /**
     * Met à jour le jeu avant de l'afficher à l'écran
     */
    public void update() {
        camera.update();
        if (System.currentTimeMillis() > timerCamera){
            updatePosition(cameraSpeedX,cameraSpeedY);
            timerCamera = System.currentTimeMillis();
        }
        
        camera.translate((x - (float)camera.position.x)/SMOOTHNESS, (y - (float)camera.position.y)/SMOOTHNESS);
        
        //monde.wolfie.deplace();
        if (System.currentTimeMillis()>timer+500){
            monde.wolfie.deplace(monde);
            ((Creature)(monde.entites.get(2))).deplace(monde);
            timer = timer = System.currentTimeMillis();
        }
    }
    
    @Override
    public void dispose(){
        
    }
    
    public void moveCameraVertical(float dy){ 
        cameraSpeedY = (float) (dy*min(1f, max(camera.zoom*2, 0.1f)));
    }
    
    public void moveCameraHorizontal(float dx){
        cameraSpeedX = (float) (dx*min(1f, max(camera.zoom*2, 0.1f)));
    }
    public void updatePosition(float dx, float dy) {
        x += dx;
        y += dy;

    }
        
    public void zoomCamera(float delta){
        if (delta > 0){
            camera.zoom /=0.8;
        } else {
            camera.zoom *=0.8;
        }
    }

    public float getCameraSpeedX() {
        return cameraSpeedX;
    }

    public float getCameraSpeedY() {
        return cameraSpeedY;
    }

    public float getCameraBaseSpeed() {
        return cameraBaseSpeed;
    }

    public Vector3 getMousePos() {
        return mousePos;
    }
    
    
}
