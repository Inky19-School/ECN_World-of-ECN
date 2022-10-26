/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;
import org.centrale.objet.WoE.SaveManager;

/**
 *
 * @author François MARIE et Rémi RAVELLI
 */
public class PlayerInput implements InputProcessor{
    
    private final GameScreen gScreen;
    
    public PlayerInput(GameScreen g){
        gScreen = g;
    }

    @Override
    public boolean keyDown(int keycode){
        switch(keycode){
            case Input.Keys.RIGHT:
                gScreen.moveCameraHorizontal(gScreen.getCameraBaseSpeed());
                break;
            case Input.Keys.LEFT:
                gScreen.moveCameraHorizontal(-gScreen.getCameraBaseSpeed());
                break;
            case Input.Keys.UP:
                gScreen.moveCameraVertical(gScreen.getCameraBaseSpeed());
                break;
            case Input.Keys.DOWN:
                gScreen.moveCameraVertical(-gScreen.getCameraBaseSpeed());
                break;
            case Input.Keys.W:
                gScreen.setZ(true);
                break;
            case Input.Keys.A:
                gScreen.setQ(true);
                break;
            case Input.Keys.S:
                gScreen.setS(true);
                break;
            case Input.Keys.D:
                gScreen.setD(true);
                break;
            case Input.Keys.E:
                gScreen.interact();
                break;
            case Input.Keys.I:
                gScreen.useInventory();
                break;
            case Input.Keys.P:
                gScreen.goToPlayer();
                break;
            case (Input.Keys.CONTROL_LEFT) :
                gScreen.setCtrl(true);
                break;
        }
        return true;
    }
    
    @Override
    public boolean keyUp(int keycode){
        switch(keycode){
            case Input.Keys.RIGHT:
                if (gScreen.getCameraSpeedX()>0){
                    gScreen.moveCameraHorizontal(0);
                }   
                break;
            case Input.Keys.LEFT:
                if (gScreen.getCameraSpeedX()<0){
                    gScreen.moveCameraHorizontal(0);
                }  
                break;
            case Input.Keys.UP:
                if (gScreen.getCameraSpeedY()>0){
                    gScreen.moveCameraVertical(0);
                }  
                break;
            case Input.Keys.DOWN:
                if (gScreen.getCameraSpeedY()<0){
                    gScreen.moveCameraVertical(0);
                }  
                break;
            case Input.Keys.W:
                gScreen.setZ(false);
                break;
            case Input.Keys.A:
                gScreen.setQ(false);
                break;
            case Input.Keys.S:
                gScreen.setS(false);
                break;
            case Input.Keys.D:
                gScreen.setD(false);
                break;
            case Input.Keys.I:
                gScreen.setShowInv(false);
                break;
            case (Input.Keys.CONTROL_LEFT) :
                gScreen.setCtrl(false);
                break;
            case (Input.Keys.ESCAPE) :
                gScreen.pause();
                break;
        }
        return true;
    }
    
    @Override
    public boolean keyTyped(char character) {
        return false;
    }
        
    @Override
    public boolean touchDown(int screenX, int screenY, int pointer, int button){
        return false;
    }

    @Override
    public boolean touchUp(int screenX, int screenY, int pointer, int button) {
        return false;
    }

    @Override
    public boolean touchDragged(int screenX, int screenY, int pointer) {
        return false;
    }

    @Override
    public boolean mouseMoved(int screenX, int screenY) {
        return false;
    }

    @Override
    public boolean scrolled(float amountX, float amountY) {
        gScreen.zoomCamera(amountY);
        return true;
    }
}
