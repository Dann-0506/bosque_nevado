package com.dan.animacion.models;

import com.dan.animacion.utils.Constantes;

import com.jogamp.opengl.GL2;

public class CicloDiaNoche {
    private float tiempo = 0.0f; 

    public void actualizar() {
        tiempo += Constantes.VELOCIDAD_TIEMPO;
        if (tiempo > Math.PI * 2) {
            tiempo -= (float) (Math.PI * 2);
        }
    }

    public void aplicarEntorno(GL2 gl) {
        float solX = (float) Math.cos(tiempo);
        float solY = (float) Math.sin(tiempo);
        float solZ = 0.5f; 

        float[] posicionLuz = { solX, solY, solZ, 0.0f };
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION, posicionLuz, 0);

        float[] colorCielo;
        float[] colorSol;
        float[] colorAmbiente;

        if (solY > 0.3f) {
            colorCielo = Constantes.CIELO_DIA;
            colorSol = Constantes.SOL_DIA;
            colorAmbiente = Constantes.AMBIENTE_DIA;
        } 
        else if (solY >= 0.0f) {
            float t = solY / 0.3f; 
            colorCielo = mezclarColores(Constantes.CIELO_ATARDECER, Constantes.CIELO_DIA, t);
            colorSol = mezclarColores(Constantes.SOL_ATARDECER, Constantes.SOL_DIA, t);
            colorAmbiente = mezclarColores(Constantes.AMBIENTE_ATARDECER, Constantes.AMBIENTE_DIA, t);
        } 
        else if (solY >= -0.3f) {
            float t = (solY + 0.3f) / 0.3f; 
            colorCielo = mezclarColores(Constantes.CIELO_NOCHE, Constantes.CIELO_ATARDECER, t);
            colorSol = mezclarColores(Constantes.SOL_NOCHE, Constantes.SOL_ATARDECER, t);
            colorAmbiente = mezclarColores(Constantes.AMBIENTE_NOCHE, Constantes.AMBIENTE_ATARDECER, t);
        } 
        else {
            colorCielo = Constantes.CIELO_NOCHE;
            colorSol = Constantes.SOL_NOCHE;
            colorAmbiente = Constantes.AMBIENTE_NOCHE;
        }

        gl.glClearColor(colorCielo[0], colorCielo[1], colorCielo[2], 1.0f); 

        float[] difusa = { colorSol[0], colorSol[1], colorSol[2], 1.0f };
        float[] ambiente = { colorAmbiente[0], colorAmbiente[1], colorAmbiente[2], 1.0f };
        
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE, difusa, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT, ambiente, 0);
    }

    private float[] mezclarColores(float[] colorA, float[] colorB, float t) {
        return new float[]{
            colorA[0] + (colorB[0] - colorA[0]) * t,
            colorA[1] + (colorB[1] - colorA[1]) * t,
            colorA[2] + (colorB[2] - colorA[2]) * t
        };
    }
}