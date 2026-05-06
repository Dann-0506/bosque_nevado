package com.dan.animacion.utils;

public final class Constantes {

    private Constantes() {}

    // --- CONFIGURACIÓN DEL MUNDO ---
    // Aumentar extiende el mapa en todas direcciones; el número de vértices crece al cuadrado,
    // lo que impacta el tiempo de carga y la memoria. TAMANO_CELDA controla la resolución
    // de la malla: valores menores dan más detalle pero más vértices.
    public static final float TAMANO_MUNDO = 200.0f;
    public static final float TAMANO_CELDA = 1.0f;


    // --- CÁMARA ---
    // VELOCIDAD_CAMARA: unidades de mundo por frame a 30 FPS. 0.5 ≈ paso humano lento.
    // SENSIBILIDAD_RATON: grados de rotación por píxel de desplazamiento del cursor.
    // ALTURA_OJO: distancia en unidades sobre el suelo donde se posiciona la cámara.
    public static final float VELOCIDAD_CAMARA = 0.5f;
    public static final float SENSIBILIDAD_RATON = 0.1f;
    public static final float ALTURA_OJO = 1.7f;


    // --- GENERACIÓN DEL TERRENO ---
    // ALTURA_MAXIMA_TERRENO: techo de las montañas en unidades de mundo.
    // OCTAVAS_RUIDO: capas de ruido superpuestas; más octavas = más detalle fino,
    //   pero cada octava adicional duplica el costo de generación.
    // ESCALA_RUIDO: frecuencia base del ruido. Valores menores producen colinas
    //   más suaves y amplias; valores mayores, terreno más accidentado y fragmentado.
    // EXPONENTE_EROSION: aplica una curva de potencia a la altura normalizada.
    //   Valores > 1 aplastan los valles y exageran las cimas; 1.0 desactiva el efecto.
    public static final float ALTURA_MAXIMA_TERRENO = 50.0f;
    public static final int OCTAVAS_RUIDO = 5;
    public static final float ESCALA_RUIDO = 0.015f;
    public static final float EXPONENTE_EROSION = 2.5f;

    // Biomas — definen a qué altura cambia el color del terreno.
    // Deben mantenerse en orden ascendente: NIVEL_PASTO < NIVEL_ROCA < NIVEL_NIEVE.
    public static final float NIVEL_NIEVE = 15.0f;
    public static final float NIVEL_ROCA = 8.0f;
    public static final float NIVEL_PASTO = 0.0f;

    // Colores del terreno en formato RGB normalizado {R, G, B} con valores entre 0.0 y 1.0.
    public static final float[] COLOR_NIEVE = {1.0f, 1.0f, 1.0f};
    public static final float[] COLOR_ROCA = {0.6f, 0.6f, 0.65f};
    public static final float[] COLOR_PASTO = {0.2f, 0.4f, 0.2f};
    public static final float[] COLOR_ARENA = {0.7f, 0.7f, 0.5f};

    // DENSIDAD_NIEBLA: coeficiente de la niebla exponencial (GL_EXP2).
    // 0.0 = sin niebla; 0.03 ya cubre casi todo el horizonte visible.
    public static final float DENSIDAD_NIEBLA = 0.010f;

    // CANTIDAD_PARTICULAS_NIEVE: total de copos activos simultáneamente.
    // VELOCIDAD_NIEVE: unidades de caída por frame.
    // RADIO_NIEVE: radio del área de spawn alrededor de la cámara, en unidades de mundo.
    // ALTURA_NIEVE: altura máxima de spawn sobre la posición Y de la cámara.
    public static final int CANTIDAD_PARTICULAS_NIEVE = 9000;
    public static final float VELOCIDAD_NIEVE = 0.05f;
    public static final float RADIO_NIEVE = 80.0f;
    public static final float ALTURA_NIEVE = 30.0f;


    // --- Generación del bosque ---
    // CANTIDAD_ARBOLES: objetivo de árboles a colocar en el mapa.
    // MAX_INTENTOS_BOSQUE: límite de candidatos aleatorios evaluados; evita un bucle infinito
    //   si el terreno tiene pocas zonas válidas. Debe ser al menos 10× CANTIDAD_ARBOLES.
    // ESCALA_DENSIDAD_BOSQUE: frecuencia del ruido de densidad que agrupa los árboles en manchas.
    //   Valores menores forman bosques más grandes y continuos.
    // UMBRAL_DENSIDAD_BOSQUE: valor mínimo de ruido [-1, 1] para aceptar una posición.
    //   Subir el umbral (ej. 0.2) reduce la cobertura y crea claros más amplios.
    public static final int CANTIDAD_ARBOLES = 4000;
    public static final int MAX_INTENTOS_BOSQUE = 80000;
    public static final float ESCALA_DENSIDAD_BOSQUE = 0.025f;
    public static final float UMBRAL_DENSIDAD_BOSQUE = 0.0f;
    public static final float OFFSET_RUIDO_BOSQUE = 500.0f;
    public static final float ALTURA_MIN_BOSQUE = NIVEL_PASTO + 1.0f;
    public static final float ALTURA_MAX_BOSQUE = NIVEL_ROCA - 1.0f;


    // --- Menú de pausa ---
    // BOTON_PAUSA_ANCHO / ALTO: dimensiones en píxeles de la zona de click invisible del botón SALIR.
    // ESCALA_TITULO_PAUSA / ESCALA_OPCION_PAUSA: la fuente stroke de GLUT mide 119 unidades de alto.
    //   La escala es un multiplicador directo: altura en pantalla = 119 × escala (en píxeles).
    //   Ejemplos: 0.20 ≈ 24 px | 0.30 ≈ 36 px | 0.50 ≈ 60 px.
    // PADDING_MENU_PAUSA: espacio en píxeles entre el borde inferior del título y el borde superior
    //   de la opción. Aumentarlo separa visualmente ambos textos.
    public static final int   BOTON_PAUSA_ANCHO = 160;
    public static final int   BOTON_PAUSA_ALTO = 40;
    public static final float ESCALA_TITULO_PAUSA = 0.50f;
    public static final float ESCALA_OPCION_PAUSA = 0.30f;
    public static final int   PADDING_MENU_PAUSA = 30;


    // --- Ciclo día y noche ---
    // Incremento de ángulo (en radianes) aplicado al ciclo solar cada frame a 30 FPS.
    // El ciclo completo es 2π radianes. Tiempo de un día completo = 2π / VELOCIDAD_TIEMPO / 30 segundos.
    // Valor actual: 2π / 0.001745 / 30 ≈ 120 segundos por día completo.
    // Para un día más lento duplicar el denominador (ej. 0.000873); para más rápido, duplicar el valor.
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
