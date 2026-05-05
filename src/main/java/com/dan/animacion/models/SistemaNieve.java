package com.dan.animacion.models;

import com.dan.animacion.utils.Constantes;

import java.util.Random;

public class SistemaNieve {
    private static final int N = Constantes.CANTIDAD_PARTICULAS_NIEVE;

    private final float[] px  = new float[N];
    private final float[] py  = new float[N];
    private final float[] pz  = new float[N];
    private final float[] fase = new float[N];

    private float tiempo = 0;
    private final Random rand = new Random();

    public SistemaNieve(float cx, float cy, float cz) {
        for (int i = 0; i < N; i++) {
            spawnaleatorio(i, cx, cy, cz);
        }
    }

    public void actualizar(float cx, float cy, float cz) {
        tiempo += 0.02f;

        for (int i = 0; i < N; i++) {
            py[i] -= Constantes.VELOCIDAD_NIEVE;
            px[i] += (float) Math.sin(tiempo + fase[i]) * 0.025f;

            if (py[i] < Constantes.NIVEL_PASTO) {
                spawnaleatorio(i, cx, cy, cz);
            }
        }
    }

    private void spawnaleatorio(int i, float cx, float cy, float cz) {
        px[i] = cx + (rand.nextFloat() * 2 - 1) * Constantes.RADIO_NIEVE;
        pz[i] = cz + (rand.nextFloat() * 2 - 1) * Constantes.RADIO_NIEVE;
        py[i] = cy + rand.nextFloat() * Constantes.ALTURA_NIEVE;
        fase[i] = rand.nextFloat() * (float) (Math.PI * 2);
    }

    public int getCantidad() {return N;}
    public float[] getPx() {return px;}
    public float[] getPy() {return py;}
    public float[] getPz() {return pz;}
}
