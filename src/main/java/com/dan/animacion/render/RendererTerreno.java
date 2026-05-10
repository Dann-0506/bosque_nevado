package com.dan.animacion.render;

import com.dan.animacion.models.Arbol;
import com.dan.animacion.models.Terreno;
import com.dan.animacion.utils.Constantes;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class RendererTerreno {
    private int listaTerreno = -1;

    public void dibujar(GL2 gl, Terreno terreno, float tiempo) {
        if (listaTerreno == -1) {
            listaTerreno = gl.glGenLists(1);
            gl.glNewList(listaTerreno, GL2.GL_COMPILE);
            dibujarMalla(gl, terreno);
            gl.glEndList();
        }

        gl.glCallList(listaTerreno);
        dibujarBosque(gl, terreno, tiempo);
    }

    private void dibujarMalla(GL2 gl, Terreno terreno) {
        float[][] alturas = terreno.getMapaAlturas();
        float tamano = terreno.getTamanoMalla();
        float celda = terreno.getTamanoCelda();
        int vertices = terreno.getVerticesPorLado();

        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
        gl.glBegin(GL2.GL_TRIANGLES);

        for (int z = 0; z < vertices - 1; z++) {
            for (int x = 0; x < vertices - 1; x++) {
                float mx = -tamano + (x * celda);
                float mz = -tamano + (z * celda);
                float y1 = alturas[x][z];
                float y2 = alturas[x + 1][z];
                float y3 = alturas[x + 1][z + 1];
                float y4 = alturas[x][z + 1];

                float[] n1 = calcularNormal(mx, y1, mz,  mx, y4, mz + celda,  mx + celda, y2, mz);
                gl.glNormal3f(n1[0], n1[1], n1[2]);
                asignarColor(gl, y1); gl.glVertex3f(mx,         y1, mz);
                asignarColor(gl, y4); gl.glVertex3f(mx,         y4, mz + celda);
                asignarColor(gl, y2); gl.glVertex3f(mx + celda, y2, mz);

                float[] n2 = calcularNormal(mx, y4, mz + celda,  mx + celda, y3, mz + celda,  mx + celda, y2, mz);
                gl.glNormal3f(n2[0], n2[1], n2[2]);
                asignarColor(gl, y4); gl.glVertex3f(mx,         y4, mz + celda);
                asignarColor(gl, y3); gl.glVertex3f(mx + celda, y3, mz + celda);
                asignarColor(gl, y2); gl.glVertex3f(mx + celda, y2, mz);
            }
        }
        gl.glEnd();
    }

    private void dibujarBosque(GL2 gl, Terreno terreno, float tiempo) {
        for (Arbol arbol : terreno.getBosque()) {
            dibujarArbol(gl, arbol, tiempo);
        }
    }

    private void dibujarArbol(GL2 gl, Arbol arbol, float tiempo) {
        gl.glPushMatrix();
        gl.glTranslatef(arbol.x, arbol.y, arbol.z);
        gl.glScalef(arbol.escala, arbol.escala, arbol.escala);

        float oscilacion = (float) Math.sin(tiempo * Constantes.FRECUENCIA_VIENTO + arbol.offsetViento);
        float desplazX   = Constantes.VIENTO_X * Constantes.AMPLITUD_VIENTO * oscilacion;
        float desplazZ   = Constantes.VIENTO_Z * Constantes.AMPLITUD_VIENTO * oscilacion;

        dibujarTronco(gl);
        dibujarFollaje(gl, desplazX, desplazZ);

        gl.glPopMatrix();
    }

    private void dibujarTronco(GL2 gl) {
        float ancho = 0.2f;
        float alto  = 1.0f;

        float[] nF = calcularNormal(-ancho, 0,  ancho,  ancho, 0,  ancho,  ancho, alto,  ancho);
        float[] nB = calcularNormal(-ancho, 0, -ancho, -ancho, alto, -ancho,  ancho, alto, -ancho);
        float[] nL = calcularNormal(-ancho, 0, -ancho, -ancho, 0,  ancho, -ancho, alto,  ancho);
        float[] nR = calcularNormal( ancho, 0, -ancho,  ancho, alto, -ancho,  ancho, alto,  ancho);

        gl.glColor3fv(Constantes.COLOR_TRONCO, 0);
        gl.glBegin(GL2.GL_QUADS);

        gl.glNormal3fv(nF, 0);
        gl.glVertex3f(-ancho, 0, ancho);
        gl.glVertex3f(ancho, 0, ancho);
        gl.glVertex3f(ancho, alto, ancho);
        gl.glVertex3f(-ancho, alto, ancho);

        gl.glNormal3fv(nB, 0);
        gl.glVertex3f(-ancho, 0, -ancho);
        gl.glVertex3f(-ancho, alto, -ancho);
        gl.glVertex3f(ancho, alto, -ancho);
        gl.glVertex3f(ancho, 0, -ancho);

        gl.glNormal3fv(nL, 0);
        gl.glVertex3f(-ancho, 0, -ancho);
        gl.glVertex3f(-ancho, 0, ancho);
        gl.glVertex3f(-ancho, alto, ancho);
        gl.glVertex3f(-ancho, alto, -ancho);

        gl.glNormal3fv(nR, 0);
        gl.glVertex3f(ancho, 0, -ancho);
        gl.glVertex3f(ancho, alto, -ancho);
        gl.glVertex3f(ancho, alto, ancho);
        gl.glVertex3f(ancho, 0, ancho);

        gl.glEnd();
    }

    private void dibujarFollaje(GL2 gl, float desplazX, float desplazZ) {
        dibujarCapaFollaje(gl, 0.8f, 1.5f, 1.0f, desplazX * 0.5f, desplazZ * 0.5f);
        dibujarCapaFollaje(gl, 1.6f, 1.2f, 0.8f, desplazX * 0.75f, desplazZ * 0.75f);
        dibujarCapaFollaje(gl, 2.4f, 1.0f, 0.6f, desplazX, desplazZ);
    }

    private void dibujarCapaFollaje(GL2 gl, float baseY, float alto, float ancho, float puntaX, float puntaZ) {
        float puntaY = baseY + alto;

        float[] nF = calcularNormal(-ancho, baseY,  ancho,  ancho, baseY,  ancho,  puntaX, puntaY, puntaZ);
        float[] nB = calcularNormal(-ancho, baseY, -ancho,  puntaX, puntaY, puntaZ,  ancho, baseY, -ancho);
        float[] nL = calcularNormal(-ancho, baseY, -ancho, -ancho, baseY,  ancho,  puntaX, puntaY, puntaZ);
        float[] nR = calcularNormal( ancho, baseY, -ancho,  puntaX, puntaY, puntaZ,  ancho, baseY,  ancho);

        gl.glBegin(GL2.GL_TRIANGLES);

        gl.glNormal3fv(nF, 0);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE, 0);
        gl.glVertex3f(-ancho, baseY,  ancho);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE, 0);
        gl.glVertex3f( ancho, baseY,  ancho);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE_PUNTA, 0);
        gl.glVertex3f(puntaX, puntaY, puntaZ);

        gl.glNormal3fv(nB, 0);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE, 0);
        gl.glVertex3f(-ancho, baseY, -ancho);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE_PUNTA, 0);
        gl.glVertex3f(puntaX, puntaY, puntaZ);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE, 0);
        gl.glVertex3f( ancho, baseY, -ancho);

        gl.glNormal3fv(nL, 0);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE, 0);
        gl.glVertex3f(-ancho, baseY, -ancho);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE, 0);
        gl.glVertex3f(-ancho, baseY,  ancho);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE_PUNTA, 0);
        gl.glVertex3f(puntaX, puntaY, puntaZ);

        gl.glNormal3fv(nR, 0);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE, 0);
        gl.glVertex3f( ancho, baseY, -ancho);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE_PUNTA, 0);
        gl.glVertex3f(puntaX, puntaY, puntaZ);
        gl.glColor3fv(Constantes.COLOR_FOLLAJE, 0);
        gl.glVertex3f( ancho, baseY,  ancho);

        gl.glEnd();
    }

    private void asignarColor(GL2 gl, float altura) {
        if (altura > Constantes.NIVEL_NIEVE) {
            gl.glColor3fv(Constantes.COLOR_NIEVE, 0);
        } else if (altura > Constantes.NIVEL_ROCA) {
            gl.glColor3fv(Constantes.COLOR_ROCA, 0);
        } else if (altura > Constantes.NIVEL_PASTO) {
            gl.glColor3fv(Constantes.COLOR_PASTO, 0);
        } else {
            gl.glColor3fv(Constantes.COLOR_ARENA, 0);
        }
    }

    private float[] calcularNormal(float x1, float y1, float z1,
                                   float x2, float y2, float z2,
                                   float x3, float y3, float z3) {
        float ux = x2 - x1, uy = y2 - y1, uz = z2 - z1;
        float vx = x3 - x1, vy = y3 - y1, vz = z3 - z1;

        float nx = uy * vz - uz * vy;
        float ny = uz * vx - ux * vz;
        float nz = ux * vy - uy * vx;

        float len = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);

        if (len == 0) return new float[]{ 0, 1, 0 };

        return new float[]{ nx / len, ny / len, nz / len };
    }
}
