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
import com.badlogic.gdx.math.Vector2;
import org.centrale.objet.WoE.World;

/**
 *
 * @author inky19
 */
public class GameScreen extends ScreenAdapter{

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 640;
    public static final float SMOOTHNESS = 10f;
    
    private int cameraSpeedX = 0;
    private int cameraSpeedY = 0;
    private int cameraBaseSpeed = 3;
    
    
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private IsometricRenderer renderer;
    private PlayerInput input;
    private World monde;
    private int x, y; // Vraie position caméra
    
    private long timer;
    
    
    
    public GameScreen(SpriteBatch batch, World monde){
        this.batch = batch;
        this.input = new PlayerInput(this);
        this.monde = monde;
        timer = System.currentTimeMillis();
    }
    

    @Override
    public void show(){
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        camera.position.set(0, 0, 0);
        camera.zoom = 0.5f;
        
        Gdx.input.setInputProcessor(input);
        
        renderer = new IsometricRenderer(monde);
    }
    
    @Override
    public void render(float delta) {
        System.out.println(delta);
        Gdx.gl.glClearColor(0,0,0,1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);
        
        batch.setProjectionMatrix(camera.combined);
        
        camera.update();

        updatePosition(cameraSpeedX,cameraSpeedY);
        
        camera.translate((x - (float)camera.position.x)/SMOOTHNESS, (y - (float)camera.position.y)/SMOOTHNESS);
        
        //monde.wolfie.deplace();
        if (System.currentTimeMillis()>timer+500){
            monde.wolfie.deplace();
            timer = timer = System.currentTimeMillis();
        }
        
        batch.begin();
        
        renderer.drawGrid(batch);
        
        
        batch.end();
    }
    
    @Override
    public void dispose(){
        
    }
    
    public void moveCameraVertical(int dy){ 
        cameraSpeedY = dy;
    }
    
    public void moveCameraHorizontal(int dx){
        cameraSpeedX = dx;
    }
    public void updatePosition(int dx, int dy) {
        x += dx;
        y += dy;
    }
        
    public void zoomCamera(float delta){
        if (camera.zoom+delta > 0.1f ){
            camera.zoom += delta;
        }
    }

    public int getCameraSpeedX() {
        return cameraSpeedX;
    }

    public int getCameraSpeedY() {
        return cameraSpeedY;
    }

    public int getCameraBaseSpeed() {
        return cameraBaseSpeed;
    }
    
}
