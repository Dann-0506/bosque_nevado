package com.dan.animacion.models;

import com.jogamp.opengl.GL2;

public class CicloDiaNoche {
    private float tiempo = 0.0f;
    private float velocidadTiempo = 0.005f;

    public void actualizar() {
        tiempo += velocidadTiempo;
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

        float[] colorCielo = new float[3];
        float[] colorSol = new float[3];
        float[] colorAmbiente = new float[3];

        if (solY > 0.3f) {

            colorCielo = new float[]{ 0.4f, 0.6f, 0.9f };
            colorSol = new float[]{ 1.0f, 1.0f, 0.9f };
            colorAmbiente = new float[]{ 0.4f, 0.4f, 0.45f };
        } 
        else if (solY >= 0.0f) {
            float t = solY / 0.3f;
            colorCielo = mezclarColores(new float[]{0.8f, 0.4f, 0.2f}, new float[]{0.4f, 0.6f, 0.9f}, t);
            colorSol = mezclarColores(new float[]{1.0f, 0.5f, 0.2f}, new float[]{1.0f, 1.0f, 0.9f}, t);
            colorAmbiente = mezclarColores(new float[]{0.2f, 0.1f, 0.1f}, new float[]{0.4f, 0.4f, 0.45f}, t);
        } 
        else if (solY >= -0.3f) {
            float t = (solY + 0.3f) / 0.3f;
            colorCielo = mezclarColores(new float[]{0.05f, 0.05f, 0.15f}, new float[]{0.8f, 0.4f, 0.2f}, t);
            colorSol = mezclarColores(new float[]{0.1f, 0.1f, 0.3f}, new float[]{1.0f, 0.5f, 0.2f}, t);
            colorAmbiente = mezclarColores(new float[]{0.05f, 0.05f, 0.1f}, new float[]{0.2f, 0.1f, 0.1f}, t);
        } 
        else {
            // PLENA NOCHE
            colorCielo = new float[]{ 0.05f, 0.05f, 0.15f };
            colorSol = new float[]{ 0.1f, 0.1f, 0.3f };
            colorAmbiente = new float[]{ 0.05f, 0.05f, 0.1f };
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