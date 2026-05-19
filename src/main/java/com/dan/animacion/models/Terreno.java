package com.dan.animacion.models;

import com.dan.animacion.utils.Constantes;
import com.dan.animacion.utils.RuidoSimplex;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Random;

public class Terreno {
    private static final Poblado[] POBLADOS = construirPoblados();

    private static Poblado[] construirPoblados() {
        float[][] coords = Constantes.COORDENADAS_POBLADOS;
        Poblado[] result = new Poblado[coords.length];
        for (int i = 0; i < coords.length; i++)
            result[i] = new Poblado(coords[i][0], coords[i][1], coords[i][2], coords[i][3], coords[i][4]);
        return result;
    }

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

            boolean enPoblado = false;
            for (Poblado p : POBLADOS) {
                float dxP = x - p.x;
                float dzP = z - p.z;
                if (dxP * dxP + dzP * dzP < p.radioTransicion * p.radioTransicion) { enPoblado = true; break; }
            }
            if (enPoblado) continue;

            float densidad = RuidoSimplex.evaluar(
                x * Constantes.ESCALA_DENSIDAD_BOSQUE + Constantes.OFFSET_RUIDO_BOSQUE,
                z * Constantes.ESCALA_DENSIDAD_BOSQUE + Constantes.OFFSET_RUIDO_BOSQUE
            );
            if (densidad < Constantes.UMBRAL_DENSIDAD_BOSQUE) continue;

            float y = calcularAltura(x, z);
            if (y > Constantes.ALTURA_MIN_BOSQUE && y < Constantes.ALTURA_MAX_BOSQUE) {
                float escala = Constantes.ESCALA_MIN_ARBOL + (rand.nextFloat() * Constantes.ESCALA_RANGO_ARBOL);
                bosque.add(new Arbol(x, y, z, escala, rand.nextFloat() * (float)(Math.PI * 2)));
                arbolesGenerados++;
            }
        }
    }

    public float calcularAltura(float x, float z) {
        float fbm = calcularFBM(x, z, Constantes.OCTAVAS_RUIDO, Constantes.ESCALA_RUIDO, 1.0f);
        float normalizado = (fbm + 1.0f) / 2.0f;
        float erosionado = (float) Math.pow(normalizado, Constantes.EXPONENTE_EROSION);
        float altura = erosionado * Constantes.ALTURA_MAXIMA_TERRENO;

        float maxInfluencia = 0.0f;
        for (Poblado p : POBLADOS) {
            float dx = x - p.x;
            float dz = z - p.z;
            float dist = (float) Math.sqrt(dx * dx + dz * dz);
            if (dist >= p.radioTransicion) continue;
            float t = suavizar(p.radio, p.radioTransicion, dist);
            float influencia = 1.0f - t;
            if (influencia <= maxInfluencia) continue;
            maxInfluencia = influencia;
            float micro = RuidoSimplex.evaluar(
                x * Constantes.ESCALA_VARIACION_POBLADO + Constantes.OFFSET_VARIACION_POBLADO,
                z * Constantes.ESCALA_VARIACION_POBLADO + Constantes.OFFSET_VARIACION_POBLADO
            ) * Constantes.AMPLITUD_VARIACION_POBLADO;
            altura = (p.altura + micro) + (altura - (p.altura + micro)) * t;
        }

        return altura;
    }

    // Smoothstep: devuelve 0 en borde0, 1 en borde1, con curva suave entre medias.
    private static float suavizar(float borde0, float borde1, float x) {
        float t = Math.max(0.0f, Math.min(1.0f, (x - borde0) / (borde1 - borde0)));
        return t * t * (3 - 2 * t);
    }

    private float calcularFBM(float x, float z, int octavas, float frecuenciaBase, float amplitudBase) {
        float total = 0;
        float amplitud = amplitudBase;
        float frecuencia = frecuenciaBase;
        float amplitudMaxima = 0;
        for (int i = 0; i < octavas; i++) {
            total += RuidoSimplex.evaluar(x * frecuencia, z * frecuencia) * amplitud;
            amplitudMaxima += amplitud;
            amplitud *= Constantes.PERSISTENCIA_RUIDO;
            frecuencia *= 2.0f;
        }
        return total / amplitudMaxima;
    }

    public float getTamanoMalla() {return tamanoMalla;}
    public float getTamanoCelda() {return tamanoCelda;}
    public int getVerticesPorLado() {return verticesPorLado;}
    public float[][] getMapaAlturas() {return mapaAlturas;}
    public List<Arbol> getBosque() {return Collections.unmodifiableList(bosque);}
}
