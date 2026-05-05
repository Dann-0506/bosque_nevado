# Ideas y plan de desarrollo

Registro de ideas, mejoras pendientes y decisiones de diseño para el proyecto Bosque Nevado.

---

## Prioridad alta

### Montañas en el horizonte
Generar un segundo heightmap de baja resolución con escala de ruido mucho menor (frecuencia más baja, montañas más anchas) como telón de fondo estático. Renderizarlo antes que el terreno principal, desplazado lejos de la cámara, sin árboles.

Alternativamente, ajustar los parámetros del FBM del terreno actual para que en los bordes del mapa suban montañas naturalmente (aumentar `EXPONENTE_EROSION` en las zonas perimetrales).

---

## Prioridad media

### Agua / lagos
En las zonas con altura menor que `NIVEL_PASTO`, renderizar un plano translúcido azulado con `GL_BLEND`. El nivel del agua sería fijo. Con reflexión falsa (invertir la escena verticalmente bajo el plano) quedaría bastante convincente en pipeline fijo.

---

### Viento en los árboles
Animar el follaje desplazando los vértices superiores de cada capa con una función `sin(tiempo + offsetPorArbol)`. El offset por árbol evita que todos se muevan en sincronía. Amplitud pequeña (0.05–0.1 unidades) para que sea sutil.

Requiere pasar el tiempo actual al `RendererTerreno`.

---

### Estrellas de noche
Durante la fase nocturna del ciclo, renderizar un domo de puntos blancos en el cielo. Se pueden generar proceduralmente una sola vez al inicio: posiciones aleatorias en una esfera de radio grande centrada en el origen, dibujadas con `GL_POINTS` sin iluminación.

La opacidad debería interpolarse con el ciclo (invisible en día, visibles en noche).

---

### Sombras simples bajo los árboles
Una elipse oscura semitransparente en el suelo bajo cada árbol. Es una sombra falsa pero da sensación de contact shadow. Con `GL_BLEND` y un quad escalado según el tamaño del árbol.

---

## Prioridad baja / largo plazo

### Migración a OpenGL 3.3 core + shaders GLSL
El pipeline fijo (GL2) limita la calidad visual. Con shaders se habilitan:

- **Iluminación per-pixel (Phong/Blinn-Phong)** — actualmente es per-vertex, lo que produce artefactos en polígonos grandes.
- **Niebla basada en altura Y** — independiente de la posición de la cámara, niebla volumétrica real.
- **Normal mapping** — detalle de superficie sin geometría adicional.
- **Atmospheric scattering** — cielo físicamente plausible con gradiente horizon/zenith.
- **SSAO** — oclusión ambiental en tiempo real para dar profundidad a las zonas bajo los árboles.

Implicaría reescribir `RendererTerreno` y `RendererEntorno` completamente, pero los modelos (`Terreno`, `Arbol`, `CicloDiaNoche`) no cambiarían.

---

### VBO/IBO para el terreno
Actualmente el terreno envía ~160k vértices a la GPU en cada frame con `glBegin/glEnd`. Con `VertexBufferObject` se sube una sola vez y se reutiliza. Mejora el rendimiento x10–100 y es el prerequisito para añadir más detalle geométrico.

---

### Instanced rendering para los árboles
En lugar de llamar `dibujarArbol()` 4000 veces, enviar la geometría del árbol una vez y la lista de transformaciones (posición, escala) en un buffer. Con `glDrawArraysInstanced` (requiere GL3.3) todo el bosque se dibuja en una sola llamada.

---

### LOD (Level of Detail)
Árboles lejanos renderizados como billboards (un quad con textura) en lugar de geometría 3D. La transición entre LOD a una distancia configurable. Permite aumentar `CANTIDAD_ARBOLES` varias veces sin caída de framerate.

---

### Audio ambiental
Con OpenAL (biblioteca `joal`):
- Viento constante de fondo
- Pájaros al amanecer (cuando `solY` cruza 0 subiendo)
- Silencio total en noche profunda
- Crujido de nieve al moverse (pasos)

---

## Notas de diseño

- **Experiencia contemplativa**: la velocidad de la cámara y del ciclo de día/noche deben mantenerse lentas. Evitar cambios bruscos de luz.
- **Procedural first**: preferir generación matemática sobre assets manuales. El mundo debe verse diferente en cada seed.
- **Sin HUD**: la pantalla debe estar completamente libre de interfaz. Nada que rompa la inmersión.
- **Paleta fría**: azules, grises y blancos dominan. El atardecer es el único momento de color cálido.
