package com.dan.animacion.utils;

public final class Constantes {
    
    private Constantes() {}

    // --- CONFIGURACIÓN DEL MUNDO ---
    public static final float TAMANO_MUNDO = 200.0f;
    public static final float TAMANO_CELDA = 1.0f;


    // --- CÁMARA ---
    public static final float VELOCIDAD_CAMARA = 0.5f;
    public static final float SENSIBILIDAD_RATON = 0.1f;
    

    // --- GENERACIÓN DEL TERRENO ---
    public static final float ALTURA_MAXIMA_TERRENO = 50.0f;
    public static final int OCTAVAS_RUIDO = 5;
    public static final float ESCALA_RUIDO = 0.015f;
    public static final float EXPONENTE_EROSION = 2.5f;
    
    // Biomas
    public static final float NIVEL_NIEVE = 15.0f;
    public static final float NIVEL_ROCA = 8.0f;
    public static final float NIVEL_PASTO = 0.0f;

    // Colores del terreno
    public static final float[] COLOR_NIEVE = {1.0f, 1.0f, 1.0f};
    public static final float[] COLOR_ROCA  = {0.6f, 0.6f, 0.65f};
    public static final float[] COLOR_PASTO = {0.2f, 0.4f, 0.2f};
    public static final float[] COLOR_ARENA = {0.7f, 0.7f, 0.5f};

    // Niebla
    public static final float DENSIDAD_NIEBLA = 0.010f;

    // Nieve
    public static final int   CANTIDAD_PARTICULAS_NIEVE = 9000;
    public static final float VELOCIDAD_NIEVE = 0.05f;
    public static final float RADIO_NIEVE = 80.0f;
    public static final float ALTURA_NIEVE = 30.0f;


    // --- Generación del bosque ---
    public static final int CANTIDAD_ARBOLES = 4000;
    public static final int MAX_INTENTOS_BOSQUE = 80000;
    public static final float ESCALA_DENSIDAD_BOSQUE = 0.025f;
    public static final float UMBRAL_DENSIDAD_BOSQUE = 0.0f;
    public static final float OFFSET_RUIDO_BOSQUE = 500.0f;
    public static final float ALTURA_MIN_BOSQUE = NIVEL_PASTO + 1.0f;
    public static final float ALTURA_MAX_BOSQUE = NIVEL_ROCA - 1.0f;

    
    // --- Ciclo día y noche ---
    public static final float VELOCIDAD_TIEMPO = 0.001745f;
    
    // Iluminación: Día
    public static final float[] CIELO_DIA = {0.4f, 0.6f, 0.9f};
    public static final float[] SOL_DIA = {1.0f, 1.0f, 0.9f};
    public static final float[] AMBIENTE_DIA = {0.4f, 0.4f, 0.45f};

    // Iluminación: Atardecer
    public static final float[] CIELO_ATARDECER = {0.8f, 0.4f, 0.2f};
    public static final float[] SOL_ATARDECER = {1.0f, 0.5f, 0.2f};
    public static final float[] AMBIENTE_ATARDECER = {0.2f, 0.1f, 0.1f};

    // Iluminación: Noche
    public static final float[] CIELO_NOCHE = {0.05f, 0.05f, 0.15f};
    public static final float[] SOL_NOCHE = {0.1f, 0.1f, 0.3f};
    public static final float[] AMBIENTE_NOCHE = {0.05f, 0.05f, 0.1f};
}