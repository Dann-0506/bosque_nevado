package com.dan.animacion.models;

import com.dan.animacion.utils.Constantes;

public class CicloDiaNoche {
    private float tiempo = 0.0f;

    private float[] colorCielo;
    private float[] colorSol;
    private float[] colorAmbiente;
    private float[] posicionSol = {1.0f, 0.0f, 0.5f, 0.0f};

    public CicloDiaNoche() {
        colorCielo = Constantes.CIELO_DIA.clone();
        colorSol = Constantes.SOL_DIA.clone();
        colorAmbiente = Constantes.AMBIENTE_DIA.clone();
    }

    public void actualizar() {
        tiempo += Constantes.VELOCIDAD_TIEMPO;
        if (tiempo > Math.PI * 2) tiempo -= (float) (Math.PI * 2);

        float solX = (float) Math.cos(tiempo);
        float solY = (float) Math.sin(tiempo);
        posicionSol = new float[]{ solX, solY, 0.0f, 0.0f };

        if (solY > 0.3f) {
            colorCielo = Constantes.CIELO_DIA;
            colorSol = Constantes.SOL_DIA;
            colorAmbiente = Constantes.AMBIENTE_DIA;
        } else if (solY >= 0.0f) {
            float t = solY / 0.3f;
            colorCielo = mezclar(Constantes.CIELO_ATARDECER, Constantes.CIELO_DIA, t);
            colorSol = mezclar(Constantes.SOL_ATARDECER, Constantes.SOL_DIA, t);
            colorAmbiente = mezclar(Constantes.AMBIENTE_ATARDECER, Constantes.AMBIENTE_DIA, t);
        } else if (solY >= -0.3f) {
            float t = (solY + 0.3f) / 0.3f;
            colorCielo = mezclar(Constantes.CIELO_NOCHE, Constantes.CIELO_ATARDECER, t);
            colorSol = mezclar(Constantes.SOL_NOCHE, Constantes.SOL_ATARDECER, t);
            colorAmbiente = mezclar(Constantes.AMBIENTE_NOCHE, Constantes.AMBIENTE_ATARDECER, t);
        } else {
            colorCielo = Constantes.CIELO_NOCHE;
            colorSol = Constantes.SOL_NOCHE;
            colorAmbiente = Constantes.AMBIENTE_NOCHE;
        }
    }

    private float[] mezclar(float[] a, float[] b, float t) {
        return new float[]{
            a[0] + (b[0] - a[0]) * t,
            a[1] + (b[1] - a[1]) * t,
            a[2] + (b[2] - a[2]) * t
        };
    }

    public float[] getColorCielo() {return colorCielo;}
    public float[] getColorSol() {return colorSol;}
    public float[] getColorAmbiente() {return colorAmbiente;}
    public float[] getPosicionSol() {return posicionSol;}
    public float getSolY() {return posicionSol[1];}
    public float getTiempo() {return tiempo;}
}
