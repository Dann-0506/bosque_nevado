package com.dan.animacion.models;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class Terreno {
    private float tamanoMalla = 20.0f;
    private float tamanoCelda = 1.0f;

    public Terreno (float tamanoMalla, float tamanoCelda) {
        this.tamanoMalla = tamanoMalla;
        this.tamanoCelda = tamanoCelda;
    }

    public void dibujar (GL2 gl) {
        gl.glColor3f(0.85f, 0.9f, 0.95f);

        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_LINE);

        gl.glBegin(GL2.GL_QUADS);

        for (float z = -tamanoMalla; z < tamanoMalla; z += tamanoCelda) {
            for (float x = -tamanoMalla; x < tamanoMalla; x += tamanoCelda) {

                float y1 = calcularAltura(x, z);
                float y2 = calcularAltura(x + tamanoCelda, z);
                float y3 = calcularAltura(x + tamanoCelda, z + tamanoCelda);
                float y4 = calcularAltura(x, z + tamanoCelda);

                gl.glVertex3f(x, y1, z);
                gl.glVertex3f(x + tamanoCelda, y2, z);
                gl.glVertex3f(x + tamanoCelda, y3, z + tamanoCelda);
                gl.glVertex3f(x, y4, z + tamanoCelda);
            }
        }
        gl.glEnd();

        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }

    private float calcularAltura (float x, float z) {
        float altura = (float) ((Math.sin(x * 0.1) + Math.cos(z * 0.1)) * 1.8);
        return altura;
    }
}
