package com.dan.animacion.render;

import com.dan.animacion.utils.Constantes;
import com.jogamp.opengl.GL2;

public class RendererAgua {

    public void dibujar(GL2 gl, float tamano) {
        float y = Constantes.NIVEL_AGUA;
        float[] c = Constantes.COLOR_AGUA;

        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        gl.glNormal3f(0, 1, 0);
        gl.glColor4f(c[0], c[1], c[2], Constantes.ALPHA_AGUA);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex3f(-tamano, y, -tamano);
        gl.glVertex3f( tamano, y, -tamano);
        gl.glVertex3f( tamano, y,  tamano);
        gl.glVertex3f(-tamano, y,  tamano);
        gl.glEnd();

        gl.glDisable(GL2.GL_BLEND);
    }
}
