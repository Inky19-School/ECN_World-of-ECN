/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Other/File.java to edit this template
 */
package org.centrale.objet.WoE.UI;

import com.badlogic.gdx.Input;
import com.badlogic.gdx.InputProcessor;

/**
 *
 * @author inky19
 */
public class PlayerInput implements InputProcessor{
    
    private GameScreen gScreen;
    
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
