package com.dan.animacion.models;

import com.jogamp.opengl.GL2;
import java.awt.event.KeyEvent;

public class Camara {
    private float x, y, z;
    private float rotX, rotY;

    private boolean[] teclasActivas = new boolean[256];

    private float velocidad = 0.5f; 
    private float velocidadRotacion = 1.0f;

    public Camara(float startX, float startY, float startZ) {
        this.x = startX;
        this.y = startY;
        this.z = startZ;
    }

    public void registrarTeclaPresionada(int keyCode) {
        if (keyCode >= 0 && keyCode < 256) {
            teclasActivas[keyCode] = true;
        }
    }

    public void registrarTeclaSoltada(int keyCode) {
        if (keyCode >= 0 && keyCode < 256) {
            teclasActivas[keyCode] = false;
        }
    }

    public void procesarEntrada() {
        if (teclasActivas[KeyEvent.VK_W]) z += velocidad;
        if (teclasActivas[KeyEvent.VK_S]) z -= velocidad;
        if (teclasActivas[KeyEvent.VK_A]) x += velocidad;
        if (teclasActivas[KeyEvent.VK_D]) x -= velocidad;

        if (teclasActivas[KeyEvent.VK_Q]) y -= velocidad;
        if (teclasActivas[KeyEvent.VK_E]) y += velocidad;

        if (teclasActivas[KeyEvent.VK_I]) rotX -= velocidadRotacion;
        if (teclasActivas[KeyEvent.VK_K]) rotX += velocidadRotacion;
        if (teclasActivas[KeyEvent.VK_J]) rotY -= velocidadRotacion;
        if (teclasActivas[KeyEvent.VK_L]) rotY += velocidadRotacion;
    }

    public void aplicarTransformaciones(GL2 gl) {
        gl.glTranslatef(x, y, z);
        gl.glRotatef(rotX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotY, 0.0f, 1.0f, 0.0f);
    }
}