# Planeación y Roadmap — Bosque Nevado

Registro de ideas, mejoras pendientes y decisiones de diseño.

---

## Roadmap

### v0.1 — Estado actual ✓
- Terreno procedural con FBM (5 octavas de Simplex noise)
- Ciclo de día y noche con sol, luna y halos atmosféricos
- Sistema de partículas de nieve
- Bosque procedural de 4,000 árboles low-poly
- Cámara en primera persona con modos suelo y vuelo libre
- Niebla volumétrica
- Menú de pausa con salida limpia

---

### v0.2 — Detalle del mundo ✓

**Estrellas de noche** ✓
Domo de 1,500 puntos generados proceduralmente en hemisferio superior. Opacidad interpolada con el ciclo solar (`GL_POINT_SMOOTH` para distinguirlas de la nieve).

**Viento en los árboles** ✓
Follaje animado con `sin(tiempo × FRECUENCIA_VIENTO + offsetViento)`. Desplazamiento graduado por capa (50/75/100%). Viento global con dirección configurable por `ANGULO_VIENTO`. Nieve afectada por el mismo vector de viento.

**Montañas en el horizonte** — descartado
La niebla (`DISTANCIA_NIEBLA = 173`) oculta completamente cualquier geometría a partir de ~200 unidades. Un segundo heightmap nunca sería visible. Podría revisarse si se aumenta la distancia de visión en el futuro.

---

### v0.3 — Vida y atmósfera

**Agua / Lagos**
En las zonas con altura menor que `NIVEL_PASTO`, renderizar un plano translúcido azulado con `GL_BLEND`. El nivel del agua sería fijo. Con reflexión falsa (invertir la escena verticalmente bajo el plano) quedaría bastante convincente en pipeline fijo.

**Fogatas**
Fogatas en puntos específicos del mapa (coordenadas fijas o generadas por seed). Cada fogata consistiría en geometría simple (troncos, brasas) y un sistema de partículas de fuego/chispa. Por la noche emitirían una luz puntual dinámica (`GL_LIGHT1`) que ilumina el terreno y árboles cercanos.

**Animales**
Fauna simple que habite el bosque: ciervos o aves. Movimiento con waypoints aleatorios dentro de zonas de pasto. Geometría low-poly consistente con el estilo visual del proyecto.

---

### v1.0 — Migración a pipeline moderno

Requiere reescribir los renderers completamente. Los modelos (`Terreno`, `Arbol`, `CicloDiaNoche`, `SistemaNieve`) no cambiarían.

**OpenGL 3.3 Core + Shaders GLSL**
El pipeline fijo (GL2) limita la calidad visual. Con shaders se habilitan:
- Iluminación per-pixel (Phong/Blinn-Phong) — actualmente es per-vertex, lo que produce artefactos en polígonos grandes
- Niebla basada en altura Y — independiente de la posición de la cámara
- Normal mapping — detalle de superficie sin geometría adicional
- Atmospheric scattering — cielo físicamente plausible con gradiente horizon/zenith
- SSAO — oclusión ambiental en tiempo real para dar profundidad a las zonas bajo los árboles

**VBO/IBO para el terreno**
Actualmente el terreno envía ~160k vértices a la GPU en cada frame con `glBegin/glEnd`. Con `VertexBufferObject` se sube una sola vez y se reutiliza. Mejora el rendimiento significativamente y es prerequisito para añadir más detalle geométrico.

**Instanced rendering para los árboles**
En lugar de llamar `dibujarArbol()` 4,000 veces, enviar la geometría del árbol una vez y la lista de transformaciones (posición, escala) en un buffer. Con `glDrawArraysInstanced` todo el bosque se dibuja en una sola llamada.

**LOD (Level of Detail)**
Árboles lejanos renderizados como billboards (un quad con textura) en lugar de geometría 3D. La transición entre LOD a una distancia configurable. Permite aumentar `CANTIDAD_ARBOLES` varias veces sin caída de framerate.

**Audio ambiental**
Con OpenAL (biblioteca `joal`):
- Viento constante de fondo
- Pájaros al amanecer (cuando `solY` cruza 0 subiendo)
- Silencio total en noche profunda
- Crujido de nieve al moverse (pasos)
- Crepitar del fuego en las fogatas

---

## Notas de diseño

- **Experiencia contemplativa**: la velocidad de la cámara y del ciclo de día/noche deben mantenerse lentas. Evitar cambios bruscos de luz.
- **Procedural first**: preferir generación matemática sobre assets manuales. El mundo debe verse diferente en cada seed.
- **Sin HUD**: la pantalla debe estar completamente libre de interfaz. Nada que rompa la inmersión.
- **Paleta fría**: azules, grises y blancos dominan. El atardecer es el único momento de color cálido.
- **Consistencia low-poly**: cualquier geometría nueva (animales, fogatas) debe respetar el estilo de facetas planas del terreno y los árboles.
