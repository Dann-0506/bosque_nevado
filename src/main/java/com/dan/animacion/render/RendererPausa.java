package com.dan.animacion.render;

import com.dan.animacion.utils.Constantes;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.util.gl2.GLUT;

public class RendererPausa {
    private final GLUT glut = new GLUT();

    private static final float STROKE_ALTO = 119.05f;

    public void dibujar(GL2 gl, int ventanaAncho, int ventanaAlto) {
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPushMatrix();
        gl.glLoadIdentity();
        gl.glOrtho(0, ventanaAncho, 0, ventanaAlto, -1, 1);

        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPushMatrix();
        gl.glLoadIdentity();

        gl.glDisable(GL2.GL_LIGHTING);
        gl.glDisable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);

        gl.glColor4f(0.0f, 0.0f, 0.0f, 0.6f);
        gl.glBegin(GL2.GL_QUADS);
        gl.glVertex2f(0, 0);
        gl.glVertex2f(ventanaAncho, 0);
        gl.glVertex2f(ventanaAncho, ventanaAlto);
        gl.glVertex2f(0, ventanaAlto);
        gl.glEnd();

        int cx = ventanaAncho / 2;
        int cy = ventanaAlto / 2;

        float alturaTitulo = STROKE_ALTO * Constantes.ESCALA_TITULO_PAUSA;
        float alturaOpcion  = STROKE_ALTO * Constantes.ESCALA_OPCION_PAUSA;
        float totalAltura   = alturaTitulo + Constantes.PADDING_MENU_PAUSA + alturaOpcion;

        int yTitulo = (int) (cy + totalAltura / 2 - alturaTitulo / 2);
        int yOpcion  = (int) (cy - totalAltura / 2 + alturaOpcion  / 2);

        dibujarTexto(gl, "PAUSA", cx, yTitulo, Constantes.ESCALA_TITULO_PAUSA);
        dibujarTexto(gl, "SALIR", cx, yOpcion,  Constantes.ESCALA_OPCION_PAUSA);

        gl.glDisable(GL2.GL_BLEND);
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);

        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glPopMatrix();
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glPopMatrix();
    }

    private void dibujarTexto(GL2 gl, String texto, int cx, int cy, float escala) {
        float anchoTexto = glut.glutStrokeLength(GLUT.STROKE_ROMAN, texto) * escala;
        float alturaTexto = 119.05f * escala;
        float x = cx - anchoTexto / 2;
        float y = cy - alturaTexto / 2;

        gl.glLineWidth(2.0f);

        gl.glColor3f(0.0f, 0.0f, 0.0f);
        gl.glPushMatrix();
        gl.glTranslatef(x + 1, y - 1, 0);
        gl.glScalef(escala, escala, 1.0f);
        glut.glutStrokeString(GLUT.STROKE_ROMAN, texto);
        gl.glPopMatrix();

        gl.glColor3f(1.0f, 1.0f, 1.0f);
        gl.glPushMatrix();
        gl.glTranslatef(x, y, 0);
        gl.glScalef(escala, escala, 1.0f);
        glut.glutStrokeString(GLUT.STROKE_ROMAN, texto);
        gl.glPopMatrix();

        gl.glLineWidth(1.0f);
    }
}
