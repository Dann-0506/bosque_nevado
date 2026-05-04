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
        float alturaBase = calcularFBM(x, z, 4, 0.05f, 3.0f);

        float radioMontana = 15.0f;
        float distCentro = (float) Math.sqrt(Math.pow(x - 10, 2) + Math.pow(z + 10, 2));
        float mascaraMontana = Math.max(0, radioMontana - distCentro) / radioMontana;
        float alturaMontana = mascaraMontana * 18.0f * Math.abs(calcularFBM(x, z, 3, 0.1f, 1.0f));

        float ruidoRio = Math.abs(RuidoSimplex.evaluar(x * 0.03f, z * 0.03f));
        float profundidadRio = 0.0f;
        if (ruidoRio < 0.08f) {
            profundidadRio = (0.08f - ruidoRio) * 40.0f;
        }

        return alturaBase + alturaMontana - profundidadRio;
    }

    private float calcularFBM(float x, float z, int octavas, float frecuenciaBase, float amplitudBase) {
        float total = 0;
        float amplitud = amplitudBase;
        float frecuencia = frecuenciaBase;

        for (int i = 0; i < octavas; i++) {
            total += RuidoSimplex.evaluar(x * frecuencia, z * frecuencia) * amplitud;
            amplitud *= 0.5f;
            frecuencia *= 2.0f;
        }
        return total;
    }

    public void dibujar(GL2 gl) {
        gl.glColor3f(0.85f, 0.9f, 0.95f);
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_LINE); 
        gl.glBegin(GL2.GL_QUADS);

        for (int z = 0; z < verticesPorLado - 1; z++) {
            for (int x = 0; x < verticesPorLado - 1; x++) {
                float mundoX = -tamanoMalla + (x * tamanoCelda);
                float mundoZ = -tamanoMalla + (z * tamanoCelda);

                float y1 = mapaAlturas[x][z];
                float y2 = mapaAlturas[x + 1][z];
                float y3 = mapaAlturas[x + 1][z + 1];
                float y4 = mapaAlturas[x][z + 1];

                gl.glVertex3f(mundoX, y1, mundoZ);
                gl.glVertex3f(mundoX + tamanoCelda, y2, mundoZ);
                gl.glVertex3f(mundoX + tamanoCelda, y3, mundoZ + tamanoCelda);
                gl.glVertex3f(mundoX, y4, mundoZ + tamanoCelda);
            }
        }
        gl.glEnd();
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL);
    }
}
