package com.dan.animacion.render;

import com.dan.animacion.models.CicloDiaNoche;
import com.dan.animacion.utils.Constantes;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.glu.GLUquadric;

public class RendererAmbiente {
    private GLU glu = new GLU();

    public void prepararCielo(GL2 gl, CicloDiaNoche ciclo) {
        float[] cielo = ciclo.getColorCielo();
        gl.glClearColor(cielo[0], cielo[1], cielo[2], 1.0f);
        gl.glFogfv(GL2.GL_FOG_COLOR, new float[]{ cielo[0], cielo[1], cielo[2], 1.0f }, 0);
        gl.glFogf(GL2.GL_FOG_DENSITY, Constantes.DENSIDAD_NIEBLA);
    }

    public void aplicarIluminacionYAstros(GL2 gl, CicloDiaNoche ciclo, float camX, float camY, float camZ) {
        float[] sol      = ciclo.getColorSol();
        float[] ambiente = ciclo.getColorAmbiente();
        float[] posSol   = ciclo.getPosicionSol();

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posSol, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE,  new float[]{ sol[0],      sol[1],      sol[2],      1.0f }, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT,  new float[]{ ambiente[0], ambiente[1], ambiente[2], 1.0f }, 0);

        dibujarAstros(gl, posSol, sol, camX, camY, camZ);
    }

    private void dibujarAstros(GL2 gl, float[] posSol, float[] colorSol, float camX, float camY, float camZ) {
        final float  distancia  = 400.0f;
        final double RADIO_SOL  = 10.0;
        final double HALO_CERCA = 13.5;
        final double HALO_LEJOS = 18.0;
        final double RADIO_LUNA = 8.0;
        final double HALO_LUNA  = 11.0;

        gl.glDisable(GL2.GL_LIGHTING);
        gl.glDisable(GL2.GL_FOG);
        gl.glEnable(GL2.GL_BLEND);
        gl.glBlendFunc(GL2.GL_SRC_ALPHA, GL2.GL_ONE_MINUS_SRC_ALPHA);
        gl.glDepthMask(false);

        GLUquadric quadric = glu.gluNewQuadric();

        // Sol
        gl.glPushMatrix();
        gl.glTranslatef(camX + posSol[0] * distancia,
                        camY + posSol[1] * distancia,
                        camZ + posSol[2] * distancia);
        gl.glColor4f(colorSol[0], colorSol[1], colorSol[2], 0.08f);
        glu.gluSphere(quadric, HALO_LEJOS, 16, 16);
        gl.glColor4f(colorSol[0], colorSol[1], colorSol[2], 0.22f);
        glu.gluSphere(quadric, HALO_CERCA, 16, 16);
        gl.glColor3f(colorSol[0], colorSol[1], colorSol[2]);
        glu.gluSphere(quadric, RADIO_SOL, 16, 16);
        gl.glPopMatrix();

        // Luna
        gl.glPushMatrix();
        gl.glTranslatef(camX - posSol[0] * distancia,
                        camY - posSol[1] * distancia,
                        camZ - posSol[2] * distancia);
        gl.glColor4f(0.85f, 0.87f, 0.95f, 0.12f);
        glu.gluSphere(quadric, HALO_LUNA, 16, 16);
        gl.glColor3f(0.85f, 0.87f, 0.95f);
        glu.gluSphere(quadric, RADIO_LUNA, 16, 16);
        gl.glPopMatrix();

        glu.gluDeleteQuadric(quadric);

        gl.glDepthMask(true);
        gl.glDisable(GL2.GL_BLEND);
        gl.glEnable(GL2.GL_FOG);
        gl.glEnable(GL2.GL_LIGHTING);
    }
}