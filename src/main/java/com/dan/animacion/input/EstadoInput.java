package com.dan.animacion.input;

public class EstadoInput {
    public final boolean[] teclas = new boolean[512];
    public float mouseDeltaX = 0;
    public float mouseDeltaY = 0;
    public boolean pausado = false;

    public void registrarTeclaPresionada(int keyCode) {
        if (keyCode >= 0 && keyCode < teclas.length) teclas[keyCode] = true;
    }

    public void registrarTeclaSoltada(int keyCode) {
        if (keyCode >= 0 && keyCode < teclas.length) teclas[keyCode] = false;
    }

    public void acumularMouse(float dx, float dy) {
        mouseDeltaX += dx;
        mouseDeltaY += dy;
    }
}
