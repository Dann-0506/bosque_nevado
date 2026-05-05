package com.dan.animacion.models;

import com.dan.animacion.utils.Constantes;
import com.dan.animacion.utils.RuidoSimplex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Terreno {
    private final float tamanoMalla;
    private final float tamanoCelda;
    private final int verticesPorLado;
    private final float[][] mapaAlturas;
    private final List<Arbol> bosque;

    public Terreno(float tamanoMalla, float tamanoCelda) {
        this.tamanoMalla = tamanoMalla;
        this.tamanoCelda = tamanoCelda;
        this.verticesPorLado = (int) ((tamanoMalla * 2) / tamanoCelda) + 1;
        this.mapaAlturas = new float[verticesPorLado][verticesPorLado];
        generarMapa();
        this.bosque = new ArrayList<>();
        poblarBosque(Constantes.CANTIDAD_ARBOLES);
    }

    private void generarMapa() {
        for (int z = 0; z < verticesPorLado; z++) {
            for (int x = 0; x < verticesPorLado; x++) {
                float mundoX = -tamanoMalla + (x * tamanoCelda);
                float mundoZ = -tamanoMalla + (z * tamanoCelda);
                mapaAlturas[x][z] = calcularAltura(mundoX, mundoZ);
            }
        }
    }

    private void poblarBosque(int cantidadArboles) {
        Random rand = new Random();
        int arbolesGenerados = 0;
        int intentos = 0;

        while (arbolesGenerados < cantidadArboles && intentos < Constantes.MAX_INTENTOS_BOSQUE) {
            intentos++;

            float x = -tamanoMalla + (rand.nextFloat() * (tamanoMalla * 2));
            float z = -tamanoMalla + (rand.nextFloat() * (tamanoMalla * 2));

            float densidad = RuidoSimplex.evaluar(
                x * Constantes.ESCALA_DENSIDAD_BOSQUE + Constantes.OFFSET_RUIDO_BOSQUE,
                z * Constantes.ESCALA_DENSIDAD_BOSQUE + Constantes.OFFSET_RUIDO_BOSQUE
            );
            if (densidad < Constantes.UMBRAL_DENSIDAD_BOSQUE) continue;

            float y = calcularAltura(x, z);
            if (y > Constantes.ALTURA_MIN_BOSQUE && y < Constantes.ALTURA_MAX_BOSQUE) {
                float escala = 0.6f + (rand.nextFloat() * 0.8f);
                bosque.add(new Arbol(x, y, z, escala));
                arbolesGenerados++;
            }
        }
    }

    public float calcularAltura(float x, float z) {
        float fbm = calcularFBM(x, z, Constantes.OCTAVAS_RUIDO, Constantes.ESCALA_RUIDO, 1.0f);
        float normalizado = (fbm + 1.0f) / 2.0f;
        float erosionado = (float) Math.pow(normalizado, Constantes.EXPONENTE_EROSION);
        return erosionado * Constantes.ALTURA_MAXIMA_TERRENO;
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

    public float getTamanoMalla()      { return tamanoMalla; }
    public float getTamanoCelda()      { return tamanoCelda; }
    public int getVerticesPorLado()    { return verticesPorLado; }
    public float[][] getMapaAlturas()  { return mapaAlturas; }
    public List<Arbol> getBosque()     { return Collections.unmodifiableList(bosque); }
}
