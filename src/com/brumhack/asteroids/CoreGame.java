package com.brumhack.asteroids;

import java.util.logging.Level;
import java.util.logging.Logger;
import java.lang.Math;

import org.newdawn.slick.*;


/**
 * @author Iridann.
 */
public class CoreGame extends BasicGame {
    // Counter variable declaration
    int ms;
    // Ship coordinates
    float shipx;
    float shipy;
    // Ship movement variables
    float accel;
    float speed;
    static int maxSpeed = 15;
    float xspeed;
    float yspeed;
    float xrot;
    float yrot;
    float rotation;
    // Hit Box
    // Image declaration
    private Image ship = null;
    // Input declaration
    private Input input;

    public CoreGame(String gamename) {
        super(gamename);
    }

    @Override
    public void init(GameContainer gc) throws SlickException {
        // Initialises counter variables at 0
        ms = 0;
        // Load in graphics
        ship = new Image("res/ship.png").getScaledCopy(0.05f);
        // Initialises ship coordinates
        shipx = 400;
        shipy = 300;
        // Initialises acceleration and speed
        accel = 0;
        speed = 0;
        // Initialises rotation
        rotation = 0;
        // Set centre of image
        xrot = (float)(ship.getWidth() * 0.5);
        yrot = (float)(ship.getHeight() * 0.5);
        ship.setCenterOfRotation(xrot,yrot);
    }

    @Override
    public void update(GameContainer gc, int delta) throws SlickException {
            if (ms > 10) {
                movement();
                ms = 0;
            } else {
                ms += delta;
            }
    }

    @Override
    public void render(GameContainer gc, Graphics g) throws SlickException {
        ship.drawCentered(shipx, shipy);
        ship.setRotation(rotation);
        g.drawString("Angle: " + Float.toString(rotation) + "\nSpeed: " + Float.toString(speed) + "\nAccel: " + Float.toString(accel) + "\nx speed: " + xspeed + "\ny speed: " + yspeed, 10, 10);
    }

    public void movement() {
        input = new Input(100);
        if (input.isKeyDown(Input.KEY_W)) {
            accel++;
            if (speed < maxSpeed) {
                speed = speed + accel;
            } else {
                speed = maxSpeed;
            }
        } else if (input.isKeyDown(Input.KEY_S)) {
            accel = 0;
            speed = 0;
            xspeed = 0;
            yspeed = 0;
        }

        if (input.isKeyDown(Input.KEY_A)) {
            rotation = rotation - (float) (45.0 / 6.0);
            if (rotation < 0) {
                rotation = 360 - (0 - rotation);
            }
        } else if (input.isKeyDown(Input.KEY_D)) {
            rotation = rotation + (float)(45.0 / 6.0);
            if (rotation > 360) {
                rotation = 0 + (rotation - 360);
            }
        }
        if (speed > 0) {
                yspeed = (float) (Math.cos(Math.toRadians(rotation)) * speed);
                xspeed = (float) (Math.sin(Math.toRadians(rotation)) * speed);
            }
        if (shipx > 800) {
            shipx = 0 + Math.abs(shipx - (shipx + xspeed));
        } else if (shipx < 0) {
            shipx = 800 - Math.abs(shipx - (shipx + xspeed));
        } else {
            shipx = shipx + xspeed;
        }

        if (shipy > 600) {
            shipy = 0 + Math.abs(shipy - (shipy - yspeed));
        } else if (shipy < 0) {
             shipy = 600 - Math.abs(shipy - (shipy - yspeed));
        } else {
            shipy = shipy - yspeed;
        }
    }

    public static void main(String[] args) {
        try {
            AppGameContainer gameContainer;
            gameContainer = new AppGameContainer(new CoreGame("Stockstroids"));
            gameContainer.setDisplayMode(800, 600, false);
            gameContainer.setTargetFrameRate(120);
            gameContainer.setAlwaysRender(true);
            gameContainer.setShowFPS(false);
            gameContainer.setAlwaysRender(true);
            gameContainer.start();
        } catch (SlickException ex) {
            Logger.getLogger(CoreGame.class.getName()).log(Level.SEVERE, null, ex);
        }
    }
}