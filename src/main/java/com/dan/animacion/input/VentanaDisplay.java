package com.dan.animacion.input;

import com.jogamp.newt.event.KeyEvent;
import com.jogamp.newt.event.KeyListener;
import com.jogamp.newt.event.MouseEvent;
import com.jogamp.newt.event.MouseListener;
import com.jogamp.newt.opengl.GLWindow;

public class VentanaDisplay implements KeyListener, MouseListener {
    private final EstadoInput estado;
    private final GLWindow window;

    private boolean mouseCapturado = true;
    private int lastX, lastY;
    private boolean primerMovimiento = true;
    private boolean ignorarWarp = false;

    public VentanaDisplay(EstadoInput estado, GLWindow window) {
        this.estado = estado;
        this.window = window;
    }

    @Override
    public void keyPressed(KeyEvent e) {
        estado.registrarTeclaPresionada(e.getKeyCode());
        if (e.getKeyCode() == KeyEvent.VK_ESCAPE) {
            estado.salirSolicitado = true;
        }
        if (e.getKeyCode() == KeyEvent.VK_M) {
            mouseCapturado = !mouseCapturado;
            window.setPointerVisible(!mouseCapturado);
            window.confinePointer(mouseCapturado);
            primerMovimiento = true;
        }
    }

    @Override
    public void keyReleased(KeyEvent e) {
        estado.registrarTeclaSoltada(e.getKeyCode());
    }

    @Override
    public void mouseMoved(MouseEvent e) {procesarMouse(e);}

    @Override
    public void mouseDragged(MouseEvent e) {procesarMouse(e);}

    private void procesarMouse(MouseEvent e) {
        if (!mouseCapturado) return;

        int x = e.getX();
        int y = e.getY();

        if (primerMovimiento || ignorarWarp) {
            lastX = x;
            lastY = y;
            primerMovimiento = false;
            ignorarWarp = false;
            return;
        }

        int deltaX = x - lastX;
        int deltaY = y - lastY;
        lastX = x;
        lastY = y;

        if (deltaX != 0 || deltaY != 0) {
            estado.acumularMouse(deltaX, deltaY);
        }

        int margen = 100;
        if (x < margen || x > window.getWidth() - margen ||
            y < margen || y > window.getHeight() - margen) {
            int cx = window.getWidth() / 2;
            int cy = window.getHeight() / 2;
            ignorarWarp = true;
            lastX = cx;
            lastY = cy;
            window.warpPointer(cx, cy);
        }
    }

    @Override public void mouseClicked(MouseEvent e) {}
    @Override public void mouseEntered(MouseEvent e) {}
    @Override public void mouseExited(MouseEvent e) {}
    @Override public void mousePressed(MouseEvent e) {}
    @Override public void mouseReleased(MouseEvent e) {}
    @Override public void mouseWheelMoved(MouseEvent e) {}
}
