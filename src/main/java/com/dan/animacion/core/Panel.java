package com.dan.animacion.core;

import com.dan.animacion.models.Camara;

import java.awt.event.KeyListener;
import java.awt.event.MouseEvent;
import java.awt.event.MouseMotionListener;
import java.awt.Robot;
import java.awt.Point;
import java.awt.Toolkit;
import java.awt.image.BufferedImage;
import java.awt.Cursor;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.awt.GLJPanel;


public class Panel extends GLJPanel implements KeyListener, MouseMotionListener {
    private Camara camara;
    private Robot robot;
    private boolean mouseCapturado = false;

    public Panel() {
        this.camara = new Camara(0.0f, -15.0f, -60.0f);

        this.addGLEventListener(new Escena(this.camara));
        
        this.addKeyListener(this);
        this.addMouseMotionListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

        try {
            robot = new Robot();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    // --- MANEJO DE TECLADO ---
    @Override public void keyTyped(java.awt.event.KeyEvent e) { }

    @Override 
    public void keyPressed(java.awt.event.KeyEvent e) {
        camara.registrarTeclaPresionada(e.getKeyCode());
        // Usamos M para evitar el conflicto con GNOME que mencionaste antes
        if (e.getKeyCode() == KeyEvent.VK_M) { 
            alternarCapturaRaton();
        }
    }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        camara.registrarTeclaSoltada(e.getKeyCode());
    }

    // --- MANEJO DE RATÓN Y ROBOT ---
    @Override
    public void mouseDragged(MouseEvent e) {
        mouseMoved(e);
    }

    @Override
    public void mouseMoved(MouseEvent e) {
        if (!mouseCapturado) return;

        int centroX = this.getWidth() / 2;
        int centroY = this.getHeight() / 2;
        int deltaX = e.getX() - centroX;
        int deltaY = e.getY() - centroY;

        if (deltaX == 0 && deltaY == 0) return; 

        camara.acumularMovimientoRaton(deltaX, deltaY);
        centrarRaton();
    }

    private void alternarCapturaRaton() {
        mouseCapturado = !mouseCapturado;
        if (mouseCapturado) {
            BufferedImage cursorImg = new BufferedImage(16, 16, BufferedImage.TYPE_INT_ARGB);
            Cursor cursorInvisible = Toolkit.getDefaultToolkit().createCustomCursor(cursorImg, new Point(0, 0), "Cursor Invisible");
            this.setCursor(cursorInvisible);
            centrarRaton();
        } else {
            this.setCursor(Cursor.getDefaultCursor());
        }
    }

    private void centrarRaton() {
        if (robot != null && this.isShowing()) {
            Point localizacionVentana = this.getLocationOnScreen();
            int centroX = localizacionVentana.x + this.getWidth() / 2;
            int centroY = localizacionVentana.y + this.getHeight() / 2;
            robot.mouseMove(centroX, centroY);
        }
    }
}