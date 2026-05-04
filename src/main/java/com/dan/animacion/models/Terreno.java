package com.dan.animacion.models;

import com.dan.animacion.utils.RuidoSimplex;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;

public class Terreno {
    private float tamanoMalla;
    private float tamanoCelda;
    private float[][] mapaAlturas;
    private int verticesPorLado;

    public Terreno (float tamanoMalla, float tamanoCelda) {
        this.tamanoMalla = tamanoMalla;
        this.tamanoCelda = tamanoCelda;

        this.verticesPorLado = (int) ((tamanoMalla * 2) / tamanoCelda) + 1;
        this.mapaAlturas = new float[verticesPorLado][verticesPorLado];

        generarMapa();
    }

    private void generarMapa() {
        for (int z = 0; z < verticesPorLado; z++) {
            for (int x = 0; x < verticesPorLado; x++) {
                float mundoX = -tamanoMalla + (x * tamanoCelda);
                float mundoZ = -tamanoMalla + (z * tamanoCelda);

                mapaAlturas[x][z] = calcularAlturaComposicion(mundoX, mundoZ);
            }
        }
    }

    private float calcularAlturaComposicion(float x, float z) {
        float escalaGlobal = 0.015f; 

        float ruidoFBM = calcularFBM(x, z, 5, escalaGlobal, 1.0f);

        float ruidoNormalizado = (ruidoFBM + 1.0f) / 2.0f;

        float exponente = 2.5f; 
        float relieveConErosion = (float) Math.pow(ruidoNormalizado, exponente);

        float alturaMaxima = 35.0f;
        
        return relieveConErosion * alturaMaxima;
    }

    private float calcularFBM(float x, float z, int octavas, float frecuenciaBase, float amplitudBase) {
        float total = 0;
        float amplitud = amplitudBase;
        float frecuencia = frecuenciaBase;
        float amplitudMaxima = 0;

        for (int i = 0; i < octavas; i++) {
            total += RuidoSimplex.evaluar(x * frecuencia, z * frecuencia) * amplitud;
            amplitudMaxima += amplitud;

            amplitud *= 0.45f; 
            frecuencia *= 2.0f;
        }

        return total / amplitudMaxima; 
    }

    private void asignarColorPorAltura(GL2 gl, float altura) {
        if (altura > 15.0f) {
            gl.glColor3f(1.0f, 1.0f, 1.0f);
        } else if (altura > 8.0f) {
            gl.glColor3f(0.6f, 0.6f, 0.65f);
        } else if (altura > 0.0f) {
            gl.glColor3f(0.2f, 0.4f, 0.2f);
        } else {
            gl.glColor3f(0.7f, 0.7f, 0.5f);
        }
    }

    public void dibujar(GL2 gl) {
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL); 
        gl.glBegin(GL2.GL_QUADS);

        for (int z = 0; z < verticesPorLado - 1; z++) {
            for (int x = 0; x < verticesPorLado - 1; x++) {
                float mundoX = -tamanoMalla + (x * tamanoCelda);
                float mundoZ = -tamanoMalla + (z * tamanoCelda);

                float y1 = mapaAlturas[x][z];
                float y2 = mapaAlturas[x + 1][z];
                float y3 = mapaAlturas[x + 1][z + 1];
                float y4 = mapaAlturas[x][z + 1];

                asignarColorPorAltura(gl, y1);
                gl.glVertex3f(mundoX, y1, mundoZ);

                asignarColorPorAltura(gl, y2);
                gl.glVertex3f(mundoX + tamanoCelda, y2, mundoZ);

                asignarColorPorAltura(gl, y3);
                gl.glVertex3f(mundoX + tamanoCelda, y3, mundoZ + tamanoCelda);

                asignarColorPorAltura(gl, y4);
                gl.glVertex3f(mundoX, y4, mundoZ + tamanoCelda);
            }
        }
        gl.glEnd();
    }
}
