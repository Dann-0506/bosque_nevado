package com.dan.animacion.core;

import com.dan.animacion.models.Camara;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;

public class Panel implements KeyListener, MouseListener {
    private Camara camara;
    private GLWindow window;

    private boolean mouseCapturado = true;
    private int lastX, lastY;
    private boolean primerMovimiento = true;

    public Panel(Camara camara, GLWindow window) {
        this.camara = camara;
        this.window = window;
    }

    // --- Manejo del teclado ---
    @Override
    public void keyPressed(KeyEvent e) {
        camara.registrarTeclaPresionada(e.getKeyCode());

        if (e.getKeyCode() == KeyEvent.VK_M || e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            mouseCapturado = !mouseCapturado;
            window.setPointerVisible(!mouseCapturado);
            window.confinePointer(mouseCapturado);
            primerMovimiento = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        camara.registrarTeclaSoltada(e.getKeyCode());
    }

    // --- Manejo del mouse ---
    @Override
    public void mouseMoved(MouseEvent e) { procesarMouse(e); }
    
    @Override
    public void mouseDragged(MouseEvent e) { procesarMouse(e); }

    private void procesarMouse(MouseEvent e) {
        if (!mouseCapturado) return;

        int x = e.getX();
        int y = e.getY();

        if (primerMovimiento) {
            lastX = x;
            lastY = y;
            primerMovimiento = false;
        }

        int deltaX = x - lastX;
        int deltaY = y - lastY;

        lastX = x;
        lastY = y;

        if (deltaX != 0 || deltaY != 0) {
            camara.acumularMovimientoRaton(deltaX, deltaY);
        }

        int margen = 100;
        if (x < margen || x > window.getWidth() - margen ||
            y < margen || y > window.getHeight() - margen) {
            
            int centroX = window.getWidth() / 2;
            int centroY = window.getHeight() / 2;

            window.warpPointer(centroX, centroY);

            lastX = centroX;
            lastY = centroY;
        }
    }

    @Override public void mouseClicked(MouseEvent e) { }
    @Override public void mouseEntered(MouseEvent e) { }
    @Override public void mouseExited(MouseEvent e) { }
    @Override public void mousePressed(MouseEvent e) { }
    @Override public void mouseReleased(MouseEvent e) { }
    @Override public void mouseWheelMoved(MouseEvent e) { }
}