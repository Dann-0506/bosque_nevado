package com.dan.animacion.render;

import com.dan.animacion.models.SistemaNieve;
import com.jogamp.opengl.GL2;

public class RendererNieve {

    public void dibujar(GL2 gl, SistemaNieve nieve) {
        int n      = nieve.getCantidad();
        float[] px = nieve.getPx();
        float[] py = nieve.getPy();
        float[] pz = nieve.getPz();

        gl.glDisable(GL2.GL_LIGHTING);
        gl.glPointSize(2.0f);
        gl.glColor3f(1.0f, 1.0f, 1.0f);

        gl.glBegin(GL2.GL_POINTS);
        for (int i = 0; i < n; i++) {
            gl.glVertex3f(px[i], py[i], pz[i]);
        }
        gl.glEnd();

        gl.glEnable(GL2.GL_LIGHTING);
    }
}
