package com.dan.animacion.models;

import com.jogamp.opengl.GL2;
import java.awt.event.KeyEvent;

public class Camara {
    private float x, y, z;
    private float rotX, rotY;

    private boolean[] teclasActivas = new boolean[256];

    private float velocidad = 0.5f; 

    private float sensibilidadRaton = 0.1f;

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

        if (teclasActivas[KeyEvent.VK_Q]) y -= velocidad; // Subir
        if (teclasActivas[KeyEvent.VK_E]) y += velocidad; // Bajar
    }

    public void aplicarTransformaciones(GL2 gl) {
        gl.glRotated(rotX, 1.0f, 0.0f, 0.0f);
        gl.glRotated(rotY, 0.0f, 1.0f, 0.0f);
        
        gl.glTranslatef(x, y, z);
    }

    public void procesarRaton(float deltaX, float deltaY) {
        rotY += deltaX * sensibilidadRaton;
        rotX += deltaY * sensibilidadRaton; 

        if (rotX > 90.0f) rotX = 90.0f;
        if (rotX < -90.0f) rotX = -90.0f;
    }
}