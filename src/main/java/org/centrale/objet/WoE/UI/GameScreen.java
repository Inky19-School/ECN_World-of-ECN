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
import java.util.logging.Level;
import java.util.logging.Logger;
import org.centrale.objet.WoE.Creature.Creature;
import org.centrale.objet.WoE.Creature.Entite;
import org.centrale.objet.WoE.TestWoE;
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
    private InfoMenu infobox;
    private int x, y; // Vraie position caméra
    
    private Vector3 mousePos;
    private Vector2 selectedTile;
    
    private long timer;
    private long timerCamera;
    private long timerTurn;
    
    private boolean z;
    private boolean q;
    private boolean s;
    private boolean d;
    
    
    
    public GameScreen(SpriteBatch batch, World monde){
        z = false; q = false; s = false; d = false;
        this.batch = batch;
        this.input = new PlayerInput(this);
        this.monde = monde;
        timer = System.currentTimeMillis();
        timerCamera = System.currentTimeMillis();
        timerTurn = System.currentTimeMillis();
        mousePos = new Vector3();
        infobox = new InfoMenu();
        selectedTile = new Vector2();
    }
    

    @Override
    public void show(){
        boolean sql = false;
        
        if (sql){
            DatabaseTools database = new DatabaseTools();
            database.connect();
            System.out.println("getname");
            Integer playerId=0;
            try {
                playerId = database.getPlayerID("Saegusa", "Mayumi");
            } catch (SQLException ex) {
                Logger.getLogger(TestWoE.class.getName()).log(Level.SEVERE, null, ex);
            }
            /*
            System.out.println(playerId);
            try {
                database.saveWorld(playerId, "Test Game 1", "Test1", monde);
            } catch (SQLException ex) {
                Logger.getLogger(TestWoE.class.getName()).log(Level.SEVERE, null, ex);
            }
            */

            try {
                // Retreive World
                database.readWorld(playerId, "Test Game 1", "Test1", monde);
                database.removeWorld(0, "Test Game 1", "Start");
            } catch (SQLException ex) {
                Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
            }


            database.disconnect();
        }

        
    
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
        infobox.update(selectedTile, monde.mapCreature, monde.mapObjets);
        
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
        
        if (System.currentTimeMillis()>timerTurn+300){
            timerTurn = System.currentTimeMillis();
            monde.getJoueur().deplacer(monde);
        }
        
        //monde.wolfie.deplacer();
        if (System.currentTimeMillis()>timer+500){
            movePlayer();
            monde.wolfie.deplacer(monde);
            
            //((Creature)(monde.entites.get(2))).deplace(monde);
            timer = timer = System.currentTimeMillis();
        }
    }
    
    @Override
    public void dispose(){
        
    }
    
    private void movePlayer(){
        if (z){
            monde.getJoueur().setDy(1);
            monde.getJoueur().setDx(1);
        } else if (s) {
            monde.getJoueur().setDy(-1);
            monde.getJoueur().setDx(-1);
        } else {
            monde.getJoueur().setDy(0);
            monde.getJoueur().setDx(0);
        }
        
        if (d){
            monde.getJoueur().addDx(1);
            monde.getJoueur().addDy(-1);
        } else if (q) {
            monde.getJoueur().addDx(-1);
            monde.getJoueur().addDy(1);
        }
    }
    
    void fightPlayer(){
        Entite e = monde.mapCreature[(int)selectedTile.x][(int)selectedTile.y];
        if (e instanceof Creature){
            monde.getJoueur().combattre((Creature) e);
        }
    }
    
    public void movePlayerVertical(int dy){
        monde.getJoueur().setDy(dy);
    }
    
    public void movePlayerHorizontal(int dx){
        monde.getJoueur().setDy(dx);
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

    public void setZ(boolean z) {
        this.z = z;
    }

    public void setQ(boolean q) {
        this.q = q;
    }

    public void setS(boolean s) {
        this.s = s;
    }

    public void setD(boolean d) {
        this.d = d;
    }
    
    
}
