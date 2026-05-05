package com.dan.animacion.models;

import com.jogamp.opengl.GL2;

public class Arbol {
    private float x, y, z;
    private float escala;

    public Arbol(float x, float y, float z, float escala) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.escala = escala;
    }

    public void dibujar(GL2 gl) {
        gl.glPushMatrix();
        
        gl.glTranslatef(x, y, z);
        gl.glScalef(escala, escala, escala);

        dibujarTronco(gl);
        dibujarFollaje(gl);

        gl.glPopMatrix();
    }

    private void dibujarTronco(GL2 gl) {
        gl.glColor3f(0.4f, 0.2f, 0.0f);
        
        gl.glBegin(GL2.GL_QUADS);
        float ancho = 0.2f;
        float alto = 1.0f;
        
        gl.glNormal3f(0.0f, 0.0f, 1.0f);
        gl.glVertex3f(-ancho, 0.0f, ancho);
        gl.glVertex3f(ancho, 0.0f, ancho);
        gl.glVertex3f(ancho, alto, ancho);
        gl.glVertex3f(-ancho, alto, ancho);
        
        gl.glNormal3f(0.0f, 0.0f, -1.0f);
        gl.glVertex3f(-ancho, 0.0f, -ancho);
        gl.glVertex3f(-ancho, alto, -ancho);
        gl.glVertex3f(ancho, alto, -ancho);
        gl.glVertex3f(ancho, 0.0f, -ancho);
        
        gl.glNormal3f(-1.0f, 0.0f, 0.0f);
        gl.glVertex3f(-ancho, 0.0f, -ancho);
        gl.glVertex3f(-ancho, 0.0f, ancho);
        gl.glVertex3f(-ancho, alto, ancho);
        gl.glVertex3f(-ancho, alto, -ancho);
        
        gl.glNormal3f(1.0f, 0.0f, 0.0f);
        gl.glVertex3f(ancho, 0.0f, -ancho);
        gl.glVertex3f(ancho, alto, -ancho);
        gl.glVertex3f(ancho, alto, ancho);
        gl.glVertex3f(ancho, 0.0f, ancho);
        gl.glEnd();
    }

    private void dibujarFollaje(GL2 gl) {
        dibujarCapaFollaje(gl, 0.8f, 1.5f, 1.0f);
        dibujarCapaFollaje(gl, 1.6f, 1.2f, 0.8f);
        dibujarCapaFollaje(gl, 2.4f, 1.0f, 0.6f);
    }

    private void dibujarCapaFollaje(GL2 gl, float alturaBase, float alto, float ancho) {
        gl.glBegin(GL2.GL_TRIANGLES);
        

        gl.glNormal3f(0.0f, 0.5f, 1.0f);
        gl.glColor3f(0.1f, 0.4f, 0.1f);
        gl.glVertex3f(-ancho, alturaBase, ancho);
        gl.glVertex3f(ancho, alturaBase, ancho);
        gl.glColor3f(0.8f, 0.9f, 0.9f);
        gl.glVertex3f(0.0f, alturaBase + alto, 0.0f);
     
        gl.glNormal3f(0.0f, 0.5f, -1.0f);
        gl.glColor3f(0.1f, 0.4f, 0.1f);
        gl.glVertex3f(-ancho, alturaBase, -ancho);
        gl.glColor3f(0.8f, 0.9f, 0.9f);
        gl.glVertex3f(0.0f, alturaBase + alto, 0.0f);
        gl.glColor3f(0.1f, 0.4f, 0.1f);
        gl.glVertex3f(ancho, alturaBase, -ancho);


        gl.glNormal3f(-1.0f, 0.5f, 0.0f);
        gl.glColor3f(0.1f, 0.4f, 0.1f);
        gl.glVertex3f(-ancho, alturaBase, -ancho);
        gl.glVertex3f(-ancho, alturaBase, ancho);
        gl.glColor3f(0.8f, 0.9f, 0.9f);
        gl.glVertex3f(0.0f, alturaBase + alto, 0.0f);
        

        gl.glNormal3f(1.0f, 0.5f, 0.0f);
        gl.glColor3f(0.1f, 0.4f, 0.1f);
        gl.glVertex3f(ancho, alturaBase, -ancho);
        gl.glColor3f(0.8f, 0.9f, 0.9f);
        gl.glVertex3f(0.0f, alturaBase + alto, 0.0f);
        gl.glColor3f(0.1f, 0.4f, 0.1f);
        gl.glVertex3f(ancho, alturaBase, ancho);
        
        gl.glEnd();
    }
}