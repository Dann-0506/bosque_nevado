package com.dan.animacion.models;

import com.dan.animacion.utils.Constantes;
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
        float ruidoFBM = calcularFBM(x, z, Constantes.OCTAVAS_RUIDO, Constantes.ESCALA_RUIDO, 1.0f);
        float ruidoNormalizado = (ruidoFBM + 1.0f) / 2.0f;
        float relieveConErosion = (float) Math.pow(ruidoNormalizado, Constantes.EXPONENTE_EROSION);
        
        return relieveConErosion * Constantes.ALTURA_MAXIMA_TERRENO;
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
        float ux = x2 - x1;
        float uy = y2 - y1;
        float uz = z2 - z1;
        float vx = x3 - x1;
        float vy = y3 - y1;
        float vz = z3 - z1;
        float nx = (uy * vz) - (uz * vy);
        float ny = (uz * vx) - (ux * vz);
        float nz = (ux * vy) - (uy * vx);
        
        float longitud = (float) Math.sqrt(nx * nx + ny * ny + nz * nz);
        if (longitud == 0) return new float[]{0, 1, 0};
        return new float[]{nx / longitud, ny / longitud, nz / longitud};
    }

    public void dibujar(GL2 gl) {
        gl.glPolygonMode(GL.GL_FRONT_AND_BACK, GL2.GL_FILL); 
        gl.glBegin(GL2.GL_TRIANGLES); 

        for (int z = 0; z < verticesPorLado - 1; z++) {
            for (int x = 0; x < verticesPorLado - 1; x++) {
                float mundoX = -tamanoMalla + (x * tamanoCelda);
                float mundoZ = -tamanoMalla + (z * tamanoCelda);
                float y1 = mapaAlturas[x][z];           
                float y2 = mapaAlturas[x + 1][z];       
                float y3 = mapaAlturas[x + 1][z + 1];   
                float y4 = mapaAlturas[x][z + 1];       

                float[] normalT1 = calcularNormal(
                    mundoX, y1, mundoZ,
                    mundoX, y4, mundoZ + tamanoCelda,
                    mundoX + tamanoCelda, y2, mundoZ
                );
                gl.glNormal3f(normalT1[0], normalT1[1], normalT1[2]);
                asignarColorPorAltura(gl, y1);
                gl.glVertex3f(mundoX, y1, mundoZ);
                asignarColorPorAltura(gl, y4);
                gl.glVertex3f(mundoX, y4, mundoZ + tamanoCelda);
                asignarColorPorAltura(gl, y2);
                gl.glVertex3f(mundoX + tamanoCelda, y2, mundoZ);

                float[] normalT2 = calcularNormal(
                    mundoX, y4, mundoZ + tamanoCelda,
                    mundoX + tamanoCelda, y3, mundoZ + tamanoCelda,
                    mundoX + tamanoCelda, y2, mundoZ
                );
                gl.glNormal3f(normalT2[0], normalT2[1], normalT2[2]);
                asignarColorPorAltura(gl, y4);
                gl.glVertex3f(mundoX, y4, mundoZ + tamanoCelda);
                asignarColorPorAltura(gl, y3);
                gl.glVertex3f(mundoX + tamanoCelda, y3, mundoZ + tamanoCelda);
                asignarColorPorAltura(gl, y2);
                gl.glVertex3f(mundoX + tamanoCelda, y2, mundoZ);
            }
        }
        gl.glEnd();
    }
}