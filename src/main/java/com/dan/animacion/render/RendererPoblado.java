package com.dan.animacion.render;

import com.dan.animacion.models.Edificio;
import com.dan.animacion.models.Terreno;
import com.dan.animacion.utils.CargadorOBJ;
import com.jogamp.opengl.GL2;

import java.util.List;

public class RendererPoblado {
    private static final String[][] MODELOS = {
        {"/models/cabins/cabin.obj",      "/models/cabins/cabin.mtl"},
        {"/models/water-well/well.obj",   "/models/water-well/well.mtl"},
    };

    private int[] listas;

    public void dibujar(GL2 gl, Terreno terreno) {
        if (listas == null) construirListas(gl);
        for (Edificio e : terreno.getEdificios())
            dibujarEdificio(gl, e);
    }

    private void construirListas(GL2 gl) {
        listas = new int[MODELOS.length];
        for (int i = 0; i < MODELOS.length; i++) {
            List<CargadorOBJ.Grupo> grupos = CargadorOBJ.cargar(MODELOS[i][0], MODELOS[i][1]);
            listas[i] = gl.glGenLists(1);
            gl.glNewList(listas[i], GL2.GL_COMPILE);
            for (CargadorOBJ.Grupo grupo : grupos) {
                gl.glColor3fv(grupo.color, 0);
                gl.glBegin(GL2.GL_TRIANGLES);
                float[] datos = grupo.datos;
                for (int j = 0; j < datos.length; j += 6) {
                    gl.glNormal3f(datos[j + 3], datos[j + 4], datos[j + 5]);
                    gl.glVertex3f(datos[j],     datos[j + 1], datos[j + 2]);
                }
                gl.glEnd();
            }
            gl.glEndList();
        }
    }

    private void dibujarEdificio(GL2 gl, Edificio e) {
        gl.glPushMatrix();
        gl.glTranslatef(e.x, e.y, e.z);
        gl.glRotatef((float) Math.toDegrees(e.rotacionY), 0, 1, 0);
        gl.glScalef(e.escala, e.escala, e.escala);
        gl.glCallList(listas[e.tipo]);
        gl.glPopMatrix();
    }
}
