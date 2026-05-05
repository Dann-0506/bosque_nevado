# Bosque Nevado

Animación 3D interactiva de un bosque nevado con montañas, niebla y ciclo de día y noche. Diseñada para ser una experiencia contemplativa y tranquila.

Desarrollada en Java con OpenGL (pipeline fijo, GL2) usando JOGL y ventanas NEWT.

## Controles

| Tecla | Acción |
|-------|--------|
| W / S | Avanzar / Retroceder |
| A / D | Desplazarse lateralmente |
| Q / E | Bajar / Subir |
| Mouse | Mirar alrededor |
| M / ESC | Capturar o liberar el cursor |

## Requisitos

- Java 21
- Maven 3.x

## Compilar y ejecutar

```bash
mvn compile
mvn exec:java
```

## Estructura del proyecto

```
src/main/java/com/dan/animacion/
├── Main.java                    — punto de entrada, configuración de ventana
├── core/
│   └── Escena.java              — GLEventListener, orquesta la lógica y el render
├── input/
│   ├── EstadoEntrada.java       — estado compartido de teclado y mouse
│   └── Panel.java               — listeners de teclado y mouse
├── models/
│   ├── Camara.java              — posición, rotación y movimiento de la cámara
│   ├── Terreno.java             — generación procedural del heightmap y el bosque
│   ├── Arbol.java               — datos de posición y escala de cada árbol
│   └── CicloDiaNoche.java       — estado del sol y colores de iluminación
├── render/
│   ├── RendererEntorno.java     — aplica iluminación y color de cielo a OpenGL
│   └── RendererTerreno.java     — dibuja la malla del terreno y los árboles
└── utils/
    ├── Constantes.java          — todos los parámetros configurables del proyecto
    └── RuidoSimplex.java        — generador de ruido Simplex 2D
```

## Generación procedural

El terreno se genera con **Fractional Brownian Motion (FBM)** sobre ruido Simplex en 5 octavas. El resultado se normaliza y se aplica una función de erosión (`pow(x, 2.5)`) que aplana las zonas bajas y resalta las cimas.

Los biomas se asignan por altura:

| Altura | Bioma |
|--------|-------|
| < 0 | Arena |
| 0 – 8 | Pasto |
| 8 – 15 | Roca |
| > 15 | Nieve |

Los bosques se generan en manchas compactas usando una segunda capa de ruido Simplex independiente. Solo se colocan árboles donde el valor de densidad supera el umbral configurado y la altura corresponde a zona de pasto.

## Parámetros principales (`Constantes.java`)

| Constante | Valor | Descripción |
|-----------|-------|-------------|
| `TAMANO_MUNDO` | 200 | Mitad del lado del mapa (total 400×400) |
| `ALTURA_MAXIMA_TERRENO` | 50 | Altura máxima en unidades |
| `ESCALA_RUIDO` | 0.015 | Frecuencia base del FBM |
| `EXPONENTE_EROSION` | 2.5 | Controla la forma de las montañas |
| `CANTIDAD_ARBOLES` | 2000 | Árboles a colocar en el mundo |
| `ESCALA_DENSIDAD_BOSQUE` | 0.025 | Tamaño de los parches forestales |
| `UMBRAL_DENSIDAD_BOSQUE` | 0.0 | Fracción del mapa cubierta por bosque |
| `VELOCIDAD_TIEMPO` | 0.001745 | Velocidad del ciclo de día y noche |
