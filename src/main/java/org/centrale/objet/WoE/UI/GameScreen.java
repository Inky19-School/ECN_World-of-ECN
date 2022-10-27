/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Gdx;
import com.badlogic.gdx.InputMultiplexer;
import com.badlogic.gdx.ScreenAdapter;
import com.badlogic.gdx.graphics.GL20;
import com.badlogic.gdx.graphics.OrthographicCamera;
import com.badlogic.gdx.graphics.g2d.SpriteBatch;
import com.badlogic.gdx.graphics.glutils.ShapeRenderer.ShapeType;
import com.badlogic.gdx.math.Vector2;
import com.badlogic.gdx.math.Vector3;
import com.badlogic.gdx.scenes.scene2d.Actor;
import com.badlogic.gdx.scenes.scene2d.Stage;
import com.badlogic.gdx.scenes.scene2d.ui.Container;
import com.badlogic.gdx.scenes.scene2d.ui.Skin;
import com.badlogic.gdx.scenes.scene2d.ui.TextButton;
import com.badlogic.gdx.scenes.scene2d.utils.ChangeListener;
import com.badlogic.gdx.utils.Align;
import static java.lang.Math.abs;
import static java.lang.Math.max;
import static java.lang.Math.min;
import java.sql.SQLException;
import java.util.logging.Level;
import java.util.logging.Logger;
import org.centrale.objet.WoE.Creature.Creature;
import org.centrale.objet.WoE.Objet.Objet;
import org.centrale.objet.WoE.Point2D;
import org.centrale.objet.WoE.SaveManager;
import org.centrale.objet.WoE.TestWoE;
import static org.centrale.objet.WoE.UI.IsometricRenderer.TILE_WIDTH;
import org.centrale.objet.WoE.World.Chunk;
import org.centrale.objet.WoE.World.World;
import org.centrale.objet.WoE.sql.DatabaseTools;

/**
 *
 * @author François MARIE et Rémi RAVELLI
 */
public class GameScreen extends ScreenAdapter {

    public static final int WIDTH = 1280;
    public static final int HEIGHT = 640;
    public static final float SMOOTHNESS = 10f;

    private float cameraSpeedX = 0;
    private float cameraSpeedY = 0;
    private float cameraBaseSpeed = 10;

    private boolean turnPassed;
    
    private final Boot game;
    private SpriteBatch batch;
    private OrthographicCamera camera;
    private OrthographicCamera fixedCamera;
    private IsometricRenderer renderer;
    private final PlayerInput input;
    private World monde;
    private InfoMenu infomenu;
    private final Stage stage;
    private int x, y; // Vraie position caméra

    private Vector3 mousePos;
    private Vector2 selectedTile;

    private boolean showInv;

    private long timer;
    private long timerCamera;
    private long timerTurn;

    private boolean z;
    private boolean q;
    private boolean s;
    private boolean d;
    private boolean ctrl;

    private float zoom;
    private final InGameMenu menu;
    
    
    public GameScreen(SpriteBatch batch, World monde, Boot game){
        z = false; 
        q = false; 
        s = false; 
        d = false; 
        ctrl = false;
        this.batch = batch;
        this.input = new PlayerInput(this);
        this.monde = monde;
        this.game = game;
        timer = System.currentTimeMillis();
        timerCamera = System.currentTimeMillis();
        timerTurn = System.currentTimeMillis();
        mousePos = new Vector3();
        infomenu = new InfoMenu();
        selectedTile = new Vector2();
        zoom = 0.5f;
        turnPassed = false;
        showInv = false;
        stage = new Stage();
        menu = new InGameMenu(game, this);
        stage.addActor(menu);
    }

    @Override
    public void show() {
        // Active le test de chargement SQL
        boolean sql = false;

        if (sql) {
            // Pour la sauvegarde en SQL, seul le chunk central est pris en compte pour le moment.
            monde.getActiveChunks()[1][1] = new Chunk(0, 0);

            // Initialisation de la connexion à la base de données.
            DatabaseTools database = new DatabaseTools();
            database.connect();

            // Test de requête pour obtenir l'ID Steam d'un joueur.
            System.out.println("getname");
            Integer playerId = 0;
            try {
                playerId = database.getPlayerID("Saegusa", "Mayumi");
            } catch (SQLException ex) {
                Logger.getLogger(TestWoE.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Sauvegarde le monde dans la base de données. (À commenter pour tester la charge précèdente)
            /*
            System.out.println(playerId);
            try {
                database.saveWorld(playerId, "Test Game 1", "Test1", monde);
            } catch (SQLException ex) {
                Logger.getLogger(TestWoE.class.getName()).log(Level.SEVERE, null, ex);
            }
             */
            // Charge le monde depuis la base de données. (À commenter pour tester la sauvegarde)
            try {
                // Retreive World
                database.readWorld("Test Game 1", "Test1", monde);
                database.removeWorld("Test Game 1", "Start");
            } catch (SQLException ex) {
                Logger.getLogger(GameScreen.class.getName()).log(Level.SEVERE, null, ex);
            }

            // Déconnexion de la base de données.
            database.disconnect();
        }

        // Initialisation du moteur graphique.
        camera = new OrthographicCamera(WIDTH, HEIGHT);
        fixedCamera = new OrthographicCamera(WIDTH, HEIGHT);

        camera.position.set(0, 0, 0);
        camera.zoom = 0.5f;
        fixedCamera.position.set(-WIDTH, WIDTH, 0);
        fixedCamera.zoom = 0.5f;
        
        infomenu.setPos(new Vector2(WIDTH-20,HEIGHT-20));
        
        Gdx.input.setInputProcessor(input);
        menu.setVisible(false);
        
        renderer = new IsometricRenderer(monde);
    }

    @Override
    public void render(float delta) {
        Gdx.gl.glClearColor(0, 0, 0, 1);
        Gdx.gl.glClear(GL20.GL_COLOR_BUFFER_BIT);

        mousePos = new Vector3(Gdx.input.getX(), Gdx.input.getY(), 0);
        camera.unproject(mousePos);
        batch.setProjectionMatrix(camera.combined);

        selectedTile = renderer.toIsometric(mousePos.x - TILE_WIDTH / 2, mousePos.y);

        update();

        batch.begin();

        renderer.drawGrid(batch, mousePos);

        batch.end();

        batch.setProjectionMatrix(fixedCamera.combined);
        Gdx.gl.glEnable(GL20.GL_BLEND);
        Gdx.gl.glBlendFunc(GL20.GL_SRC_ALPHA, GL20.GL_ONE_MINUS_SRC_ALPHA);
        infomenu.begin(ShapeType.Filled);
        infomenu.setColor(0.8f, 0.8f, 0.8f, 0.7f);
        infomenu.update(selectedTile, monde);

        infomenu.draw();
        if (showInv) {
            infomenu.drawInventoryBox(monde.getJoueur().getInventaire());
        }
        infomenu.end();

        Gdx.gl.glDisable(GL20.GL_BLEND);

        batch.begin();
        infomenu.drawTextures(batch);
        batch.end();
        
        stage.act(Math.min(Gdx.graphics.getDeltaTime(), 1 / 30f));
        stage.draw();
        
    }

    /**
     * Met à jour le jeu avant de l'afficher à l'écran
     */
    public void update() {
        camera.update();
        if (System.currentTimeMillis() > timerCamera) {
            updatePosition(cameraSpeedX, cameraSpeedY);
            timerCamera = System.currentTimeMillis();
        }

        camera.translate((x - (float) camera.position.x) / SMOOTHNESS, (y - (float) camera.position.y) / SMOOTHNESS);
        camera.zoom += (zoom - camera.zoom) / SMOOTHNESS;
        
        if (ctrl && s) {
            SaveManager.saveWorld(monde);
            ctrl = false;
            s = false;
        }
        
        if ((z || q || s || d) && System.currentTimeMillis() > timerTurn + 300) {
            boolean temp = movePlayer();
            turnPassed = temp || turnPassed;
            timerTurn = System.currentTimeMillis();
        }
        if (turnPassed) {
            monde.tourDeJeu();
            turnPassed = false;
        }
        //monde.wolfie.deplacer();
        if (turnPassed && System.currentTimeMillis() > timer + 500) {
            turnPassed = false;
            //monde.wolfie.deplacer(monde);

            //((Creature)(monde.entites.get(2))).deplace(monde);
            timer = timer = System.currentTimeMillis();
        }
    }

    @Override
    public void dispose() {

    }

    @Override
    public void resume() {
        super.resume();
        menu.setVisible(false); 
        Gdx.input.setInputProcessor(input);
    }

    @Override
    public void pause() {
        super.pause();
        menu.setVisible(true);
        Gdx.input.setInputProcessor(stage);
    }
    
    private boolean movePlayer() {
        Point2D pos = new Point2D(monde.getJoueur().getPlayer().getPos());
        if (z) {
            monde.getJoueur().setDy(1);
            monde.getJoueur().setDx(1);
        } else if (s) {
            monde.getJoueur().setDy(-1);
            monde.getJoueur().setDx(-1);
        } else {
            monde.getJoueur().setDy(0);
            monde.getJoueur().setDx(0);
        }

        if (d) {
            monde.getJoueur().addDx(1);
            monde.getJoueur().addDy(-1);
        } else if (q) {
            monde.getJoueur().addDx(-1);
            monde.getJoueur().addDy(1);
        }
        monde.getJoueur().deplacer(monde);
        return !pos.equals(monde.getJoueur().getPlayer().getPos());
    }

    public void interact() {
        Creature c = monde.getCrea((int) selectedTile.x, (int) selectedTile.y);
        Objet o = monde.getObj((int) selectedTile.x, (int) selectedTile.y);
        if (c != null) {
            turnPassed = monde.getJoueur().combattre(c) || turnPassed;

            if (c.getPtVie() <= 0) {
                monde.delEnt(c);
            }
        }
        if (o != null) {
            turnPassed = monde.getJoueur().useMap(o, monde) || turnPassed;
        }

    }

    public void useInventory() {
        monde.getJoueur().useInventory();
        //showInv = true; // Permet d'afficher l'interface graphique de l'inventaire. En cours d'implémentation
    }

    public void setShowInv(boolean b) {
        showInv = b;
    }

    public void goToPlayer() {
        Point2D localPos = this.monde.getJoueur().getPlayer().getPos();
        Point2D chPos = this.monde.getJoueur().getPlayer().getChPos();
        Point2D tile_pos = new Point2D(localPos.getX() + chPos.getX() * Chunk.SIZE, localPos.getY() + chPos.getY() * Chunk.SIZE);
        Vector2 pos = renderer.toWindowPos(tile_pos.getX(), tile_pos.getY());
        this.x = (int) pos.x;
        this.y = (int) pos.y;
        zoom = 0.5f;

    }

    public void movePlayerVertical(int dy) {
        monde.getJoueur().setDy(dy);
    }

    public void movePlayerHorizontal(int dx) {
        monde.getJoueur().setDy(dx);
    }

    public void moveCameraVertical(float dy) {
        cameraSpeedY = (float) (dy * min(1f, max(camera.zoom * 2, 0.1f)));
    }

    public void moveCameraHorizontal(float dx) {
        cameraSpeedX = (float) (dx * min(1f, max(camera.zoom * 2, 0.1f)));
    }

    public void updatePosition(float dx, float dy) {
        x += dx;
        y += dy;

    }

    public void zoomCamera(float delta) {
        if (delta > 0) {
            zoom /= 0.8;
        } else {
            zoom *= 0.8;
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

    public void setCtrl(boolean ctrl) {
        this.ctrl = ctrl;
    }

    public World getMonde() {
        return monde;
    }

    
}
