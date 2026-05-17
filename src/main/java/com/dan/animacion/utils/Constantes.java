package com.dan.animacion.utils;

public final class Constantes {

    private Constantes() {}

    // --- CONFIGURACIÓN DEL MUNDO ---
    // TAMANO_MUNDO: radio del mapa en unidades de mundo; el mapa total mide 2×TAMANO_MUNDO por lado.
    //   Aumentarlo extiende el mapa en todas direcciones; el número de vértices crece al cuadrado,
    //   lo que impacta el tiempo de carga y la memoria.
    // TAMANO_CELDA: tamaño de cada celda de la malla. Valores menores dan más detalle pero más vértices.
    public static final float TAMANO_MUNDO = 200.0f;
    public static final float TAMANO_CELDA = 1.0f;


    // --- GENERACIÓN DEL TERRENO ---
    // ALTURA_MAXIMA_TERRENO: techo de las montañas en unidades de mundo.
    // OCTAVAS_RUIDO: capas de ruido superpuestas; más octavas = más detalle fino,
    //   pero cada octava adicional duplica el costo de generación.
    // ESCALA_RUIDO: frecuencia base del ruido. Valores menores producen colinas
    //   más suaves y amplias; valores mayores, terreno más accidentado y fragmentado.
    // EXPONENTE_EROSION: aplica una curva de potencia a la altura normalizada.
    //   Valores > 1 aplastan los valles y exageran las cimas; 1.0 desactiva el efecto.
    // PERSISTENCIA_RUIDO: factor por el que se multiplica la amplitud en cada octava del FBM.
    //   Rango (0, 1): valores cercanos a 1 dan más peso al detalle fino; cercanos a 0 lo suprimen.
    public static final float ALTURA_MAXIMA_TERRENO = 80.0f;
    public static final int OCTAVAS_RUIDO = 5;
    public static final float ESCALA_RUIDO = 0.015f;
    public static final float EXPONENTE_EROSION = 2.5f;
    public static final float PERSISTENCIA_RUIDO = 0.45f;

    // Biomas — definen a qué altura cambia el color del terreno.
    // Deben mantenerse en orden ascendente: NIVEL_PASTO < NIVEL_ROCA < NIVEL_NIEVE.
    public static final float NIVEL_NIEVE = 24.0f;
    public static final float NIVEL_ROCA = 13.0f;
    public static final float NIVEL_PASTO = 2.1f;

    // Colores del terreno en formato RGB normalizado {R, G, B} con valores entre 0.0 y 1.0.
    public static final float[] COLOR_NIEVE = {0.88f, 0.92f, 0.96f};
    public static final float[] COLOR_ROCA  = {0.32f, 0.26f, 0.24f};
    public static final float[] COLOR_PASTO = {0.059f, 0.188f, 0.122f};
    public static final float[] COLOR_FONDO = {0.204f, 0.098f, 0.114f};

    // NIVEL_AGUA: altura del plano de agua en unidades de mundo. Debe ser mayor que el mínimo
    //   real del terreno (EXPONENTE_EROSION aplana los valles por encima de 0); ajustar hasta
    //   que los lagos aparezcan en las zonas de arena. Rango útil: 2–6.
    // COLOR_AGUA: tono azulado en RGB normalizado.
    // ALPHA_AGUA: opacidad del plano; 0.0 = invisible, 1.0 = sólido.
    public static final float NIVEL_AGUA  = 1.9f;
    public static final float[] COLOR_AGUA  = {0.04f, 0.11f, 0.20f};
    public static final float ALPHA_AGUA  = 0.65f;

    // DISTANCIA_NIEBLA: distancia en unidades de mundo a la que un objeto es casi invisible (5% visible).
    // DENSIDAD_NIEBLA: coeficiente derivado para GL_EXP2. No modificar directamente.
    public static final float DISTANCIA_NIEBLA = 100.0f;
    public static final float DENSIDAD_NIEBLA = 1.731f / DISTANCIA_NIEBLA;

    // CANTIDAD_PARTICULAS_NIEVE: total de copos activos simultáneamente.
    // VELOCIDAD_NIEVE: unidades de caída por frame.
    // RADIO_NIEVE: radio del área de spawn alrededor de la cámara, en unidades de mundo.
    // ALTURA_NIEVE: altura máxima de spawn sobre la posición Y de la cámara.
    // TAMANO_PARTICULA_NIEVE: tamaño visual del copo en píxeles (glPointSize).
    // VELOCIDAD_DERIVA_NIEVE: desplazamiento constante por frame en la dirección del viento.
    // AMPLITUD_DERIVA_NIEVE: amplitud de la turbulencia oscilatoria sobre la deriva base.
    public static final int CANTIDAD_PARTICULAS_NIEVE = 10000;
    public static final float VELOCIDAD_NIEVE = 0.05f;
    public static final float RADIO_NIEVE = 80.0f;
    public static final float ALTURA_NIEVE = 30.0f;
    public static final float TAMANO_PARTICULA_NIEVE = 1.5f;
    public static final float VELOCIDAD_DERIVA_NIEVE = 0.015f;
    public static final float AMPLITUD_DERIVA_NIEVE = 0.025f;


    // --- Generación del bosque ---
    // CANTIDAD_ARBOLES: objetivo de árboles a colocar en el mapa.
    // MAX_INTENTOS_BOSQUE: límite de candidatos aleatorios evaluados; evita un bucle infinito
    //   si el terreno tiene pocas zonas válidas. Debe ser al menos 10× CANTIDAD_ARBOLES.
    // ESCALA_DENSIDAD_BOSQUE: frecuencia del ruido de densidad que agrupa los árboles en manchas.
    //   Valores menores forman bosques más grandes y continuos.
    // UMBRAL_DENSIDAD_BOSQUE: valor mínimo de ruido [-1, 1] para aceptar una posición.
    //   Subir el umbral (ej. 0.2) reduce la cobertura y crea claros más amplios.
    // OFFSET_RUIDO_BOSQUE: desplazamiento aplicado al sampleo del ruido de densidad para que
    //   el patrón del bosque sea independiente del patrón de altura del terreno.
    // ALTURA_MIN/MAX_BOSQUE: rango de altura válido para plantar árboles; derivado de los biomas.
    // ESCALA_MIN_ARBOL: escala mínima de un árbol. ESCALA_RANGO_ARBOL: variación aleatoria adicional.
    //   Escala final = ESCALA_MIN_ARBOL + random(0, 1) × ESCALA_RANGO_ARBOL → rango [0.6, 1.4].
    // OFFSET_Y_ARBOL: desplazamiento vertical del modelo OBJ sobre el terreno.
    //   Aumentar si los árboles parecen incrustados; reducir si flotan.
    public static final int CANTIDAD_ARBOLES = 4000;
    public static final int MAX_INTENTOS_BOSQUE = 80000;
    public static final float ESCALA_DENSIDAD_BOSQUE = 0.025f;
    public static final float UMBRAL_DENSIDAD_BOSQUE = 0.0f;
    public static final float OFFSET_RUIDO_BOSQUE = 500.0f;
    public static final float ALTURA_MIN_BOSQUE = NIVEL_PASTO;
    public static final float ALTURA_MAX_BOSQUE = NIVEL_ROCA - 1.0f;
    public static final float ESCALA_MIN_ARBOL = 0.4f;
    public static final float ESCALA_RANGO_ARBOL = 0.6f;
    public static final float OFFSET_Y_ARBOL = 2.0f;

    // Colores de los árboles en formato RGB normalizado {R, G, B}.
    // COLOR_FOLLAJE_PUNTA representa la nieve acumulada en la punta de cada capa del follaje.
    public static final float[] COLOR_TRONCO = {0.4f, 0.2f, 0.0f};
    public static final float[] COLOR_FOLLAJE = {0.1f, 0.4f, 0.1f};
    public static final float[] COLOR_FOLLAJE_PUNTA = {0.8f, 0.9f, 0.9f};


    // --- Viento ---
    // ANGULO_VIENTO: dirección del viento en radianes; 0 = norte, π/2 = este.
    // VIENTO_X / VIENTO_Z: componentes de la dirección unitaria del viento; derivados — no modificar.
    // AMPLITUD_VIENTO: desplazamiento máximo en unidades locales de la punta del follaje.
    // FRECUENCIA_VIENTO: multiplicador del tiempo solar para la oscilación del follaje.
    //   80 ≈ ciclo de ~1.5 segundos a 24 FPS. Aumentar para viento más rápido.
    public static final float ANGULO_VIENTO = (float)(Math.PI / 4);
    public static final float VIENTO_X = (float) Math.sin(ANGULO_VIENTO);
    public static final float VIENTO_Z = (float) Math.cos(ANGULO_VIENTO);
    public static final float AMPLITUD_VIENTO   = 0.2f;
    public static final float FRECUENCIA_VIENTO = 20.0f;


    // --- Menú de pausa ---
    // BOTON_PAUSA_ANCHO / ALTO: dimensiones en píxeles de la zona de click invisible del botón SALIR.
    // ESCALA_TITULO_PAUSA / ESCALA_OPCION_PAUSA: la fuente stroke de GLUT mide 119 unidades de alto.
    //   La escala es un multiplicador directo: altura en pantalla = 119 × escala (en píxeles).
    //   Ejemplos: 0.20 ≈ 24 px | 0.30 ≈ 36 px | 0.50 ≈ 60 px.
    // PADDING_MENU_PAUSA: espacio en píxeles entre el borde inferior del título y el borde superior
    //   de la opción. Aumentarlo separa visualmente ambos textos.
    public static final int BOTON_PAUSA_ANCHO = 160;
    public static final int BOTON_PAUSA_ALTO = 40;
    public static final float ESCALA_TITULO_PAUSA = 0.50f;
    public static final float ESCALA_OPCION_PAUSA = 0.30f;
    public static final int PADDING_MENU_PAUSA = 30;


    // --- Ciclo día y noche ---
    // FPS_OBJETIVO: framerate objetivo; usado por FPSAnimator y como base para derivar VELOCIDAD_TIEMPO.
    // DURACION_DIA_SEGUNDOS: duración de un ciclo completo de día/noche en segundos de tiempo real.
    // VELOCIDAD_TIEMPO: incremento de ángulo (radianes) por frame; derivado — no modificar directamente.
    public static final int FPS_OBJETIVO = 24;
    public static final float DURACION_DIA_SEGUNDOS = 120.0f;
    public static final float VELOCIDAD_TIEMPO = (float)(2 * Math.PI) / (DURACION_DIA_SEGUNDOS * FPS_OBJETIVO);

    // Iluminación: Día
    public static final float[] CIELO_DIA     = {0.32f, 0.50f, 0.72f};
    public static final float[] SOL_DIA       = {0.95f, 0.90f, 0.78f};
    public static final float[] AMBIENTE_DIA  = {0.18f, 0.22f, 0.20f};

    // Iluminación: Atardecer
    public static final float[] CIELO_ATARDECER    = {0.62f, 0.22f, 0.08f};
    public static final float[] SOL_ATARDECER      = {0.92f, 0.42f, 0.10f};
    public static final float[] AMBIENTE_ATARDECER = {0.15f, 0.06f, 0.04f};

    // Iluminación: Noche
    public static final float[] CIELO_NOCHE    = {0.02f, 0.04f, 0.10f};
    public static final float[] SOL_NOCHE      = {0.06f, 0.08f, 0.18f};
    public static final float[] AMBIENTE_NOCHE = {0.02f, 0.03f, 0.03f};

    // Cuerpos celestes — color base compartido por el cuerpo y el halo de cada astro.
    public static final float[] COLOR_LUNA = {0.78f, 0.82f, 0.92f};

    // CANTIDAD_ESTRELLAS: puntos estelares generados proceduralmente al inicio.
    // RADIO_ESTRELLAS: radio del domo estelar; debe ser mayor que la distancia del sol/luna (400)
    //   y menor que el plano lejano de la cámara (1000).
    // TAMANO_ESTRELLA: tamaño en píxeles de cada punto estelar (glPointSize).
    public static final int CANTIDAD_ESTRELLAS = 1500;
    public static final float RADIO_ESTRELLAS = 450.0f;
    public static final float TAMANO_ESTRELLA = 2.0f;


    // --- CÁMARA ---
    // VELOCIDAD_CAMARA_UPS: velocidad de movimiento en unidades de mundo por segundo. 15 ≈ paso humano lento.
    // VELOCIDAD_CAMARA: valor por frame derivado de VELOCIDAD_CAMARA_UPS; no modificar directamente.
    // SENSIBILIDAD_RATON: grados de rotación por píxel de desplazamiento del cursor.
    // ALTURA_OJO: distancia en unidades sobre el suelo donde se posiciona la cámara.
    public static final float VELOCIDAD_CAMARA_UPS = 5.0f;
    public static final float VELOCIDAD_CAMARA = VELOCIDAD_CAMARA_UPS / FPS_OBJETIVO;
    public static final float SENSIBILIDAD_RATON = 0.05f;
    public static final float ALTURA_OJO = 1.7f;
}
