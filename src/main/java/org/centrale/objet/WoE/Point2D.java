/*
 * Click nbfs://nbhost/SystemFileSystem/Templates/Licenses/license-default.txt to change this license
 * Click nbfs://nbhost/SystemFileSystem/Templates/Project/Maven2/JavaApp/src/main/java/${packagePath}/${mainClassName}.java to edit this template
 */

package org.centrale.objet.WoE;

/**
 *
 * @author François MARIE & Rémi RAVELLI
 */
public class Point2D {
    private int x;
    private int y;

    /**
     *
     * @return
     */
    public int getX() {
        return x;
    }

    /**
     *
     * @param x
     */
    public void setX(int x) {
        this.x = x;
    }

    /**
     *
     * @return
     */
    public int getY() {
        return y;
    }

    /**
     *
     * @param y
     */
    public void setY(int y) {
        this.y = y;
    }
    
    /**
     *
     */
    public Point2D(){
        this.x = 0;
        this.y = 0;
    }
    
    /**
     *
     * @param x
     * @param y
     */
    public Point2D(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     *
     * @param p
     */
    public Point2D(Point2D p) {
        this.x = p.getX();
        this.y = p.getY();
    }
    
    /**
     *
     * @param x
     * @param y
     */
    public void setPosition(int x, int y) {
        this.x = x;
        this.y = y;
    }
    
    /**
     *
     * @param dx
     * @param dy
     */
    public void translate(int dx, int dy) {
        x += dx;
        y += dy;
    }
    
    /**
     *
     */
    public void affiche(){
        System.out.println("[" + x + ";" + y + "]");
    }
    
    /**
     *
     * @param p
     * @return
     */
    public float distance(Point2D p){
        int dx = this.x-p.getX();
        double lx = dx*dx;
        int dy = this.y-p.getY();
        double ly = dy*dy;
        return (float) Math.sqrt(lx+ly);
    }
    
    /**
     *
     * @param p
     * @return
     */
    public boolean equals(Point2D p){
        return ((p.getX() == this.x) && (p.getY() == this.y));
    }
}
