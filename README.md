# Bosque Nevado

Animación 3D interactiva de un bosque nevado con montañas, niebla y ciclo de día y noche. Diseñada para ser una experiencia contemplativa y tranquila.

Desarrollada en Java con OpenGL (pipeline fijo, GL2) usando JOGL y ventanas NEWT.

## Controles

### Movimiento

| Tecla | Acción |
|-------|--------|
| W / S | Avanzar / Retroceder |
| A / D | Desplazarse lateralmente |
| Q / E | Bajar / Subir *(solo en modo libre)* |
| Mouse | Mirar alrededor |

### Modos y sistema

| Tecla | Acción |
|-------|--------|
| Tab | Alternar modo suelo / modo libre |
| M | Capturar o liberar el cursor |
| Escape | Cerrar la aplicación |

**Modo suelo** (por defecto): la cámara se mantiene a nivel del terreno como si caminara sobre él.  
**Modo libre**: vuelo sin restricciones, con `Q`/`E` para bajar y subir.

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
│   ├── EstadoInput.java         — estado compartido de teclado y mouse
│   └── VentanaDisplay.java      — listeners de teclado y mouse
├── models/
│   ├── Arbol.java               — datos de posición y escala de cada árbol
│   ├── Camara.java              — posición, rotación y movimiento de la cámara
│   ├── CicloDiaNoche.java       — estado del sol y colores de iluminación
│   ├── SistemaNieve             — sistema de generacion de las particulas para la nieve
│   └── Terreno.java             — generación procedural del heightmap y el bosque
├── render/
│   ├── RendererAmbiente.java    — dibuja los elementos del cielo y del ambiente
│   ├── RendererNieve.java       — dibuja las particulas de nieve antes de la iluminación
│   └── RendererTerreno.java     — dibuja la malla del terreno y los árboles
└── utils/
    ├── Constantes.java          — todos los parámetros configurables del proyecto
    └── RuidoSimplex.java        — generador de ruido Simplex 2D de ken Perlin
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
| `CANTIDAD_ARBOLES` | 4000 | Árboles a colocar en el mundo |
| `ESCALA_DENSIDAD_BOSQUE` | 0.025 | Tamaño de los parches forestales |
| `UMBRAL_DENSIDAD_BOSQUE` | 0.0 | Fracción del mapa cubierta por bosque |
| `VELOCIDAD_TIEMPO` | 0.001745 | Velocidad del ciclo de día y noche |
