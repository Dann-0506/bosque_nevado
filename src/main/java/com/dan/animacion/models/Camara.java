package com.dan.animacion.models;

import com.jogamp.opengl.GL2;
import com.jogamp.newt.event.KeyEvent;

public class Camara {
    private float x, y, z;
    private float rotX, rotY;

    private boolean[] teclasActivas = new boolean[512];

    private float velocidad = 0.5f; 

    private float sensibilidadRaton = 0.1f;
    private float mouseDeltaX = 0;
    private float mouseDeltaY = 0;

    public Camara(float startX, float startY, float startZ) {
        this.x = startX;
        this.y = startY;
        this.z = startZ;
    }

    public void acumularMovimientoRaton(float dx, float dy) {
        this.mouseDeltaX += dx;
        this.mouseDeltaY += dy;
    }

    public void registrarTeclaPresionada(int keyCode) {
        if (keyCode >= 0 && keyCode < 512) {
            teclasActivas[keyCode] = true;
        }
    }

    public void registrarTeclaSoltada(int keyCode) {
        if (keyCode >= 0 && keyCode < 512) {
            teclasActivas[keyCode] = false;
        }
    }

    public void procesarEntrada() {
        if (mouseDeltaX != 0 || mouseDeltaY != 0) {
            rotY += mouseDeltaX * sensibilidadRaton;
            rotX += mouseDeltaY * sensibilidadRaton;
            
            if (rotX > 90.0f) rotX = 90.0f;
            if (rotX < -90.0f) rotX = -90.0f;

            mouseDeltaX = 0;
            mouseDeltaY = 0;
        }

        float yawRad = (float) Math.toRadians(rotY);

        float sinYaw = (float) Math.sin(yawRad);
        float cosYaw = (float) Math.cos(yawRad);

        if (teclasActivas[KeyEvent.VK_W]) {
            x -= sinYaw * velocidad;
            z += cosYaw * velocidad;

        }
        if (teclasActivas[KeyEvent.VK_S]) {
            x += sinYaw * velocidad;
            z -= cosYaw * velocidad;
        }

        if (teclasActivas[KeyEvent.VK_A]) {
            x += cosYaw * velocidad;
            z += sinYaw * velocidad;
        }
        if (teclasActivas[KeyEvent.VK_D]) {
            x -= cosYaw * velocidad; 
            z -= sinYaw * velocidad;
        }

        if (teclasActivas[KeyEvent.VK_Q]) y -= velocidad;
        if (teclasActivas[KeyEvent.VK_E]) y += velocidad;
    }

    public void aplicarTransformaciones(GL2 gl) {
        gl.glRotated(rotX, 1.0f, 0.0f, 0.0f);
        gl.glRotated(rotY, 0.0f, 1.0f, 0.0f);
        
        gl.glTranslatef(x, y, z);
    }
}