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
                gl.glVertex3f(x, 0.0f, z);
                gl.glVertex3f(x + tamanoCelda, 0.0f, z);
                gl.glVertex3f(x + tamanoCelda, 0.0f, z + tamanoCelda);
                gl.glVertex3f(x, 0.0f, z + tamanoCelda);
            }
        }
        gl.glEnd();

        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }
}
