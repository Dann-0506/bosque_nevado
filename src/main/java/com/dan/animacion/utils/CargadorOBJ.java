package com.dan.animacion.utils;

import java.io.*;
import java.util.*;

public final class CargadorOBJ {

    public static class Grupo {
        public final float[] color;
        public final float[] datos; // triángulos: [x,y,z, nx,ny,nz] por vértice

        Grupo(float[] color, float[] datos) {
            this.color = color;
            this.datos = datos;
        }
    }

    public static List<Grupo> cargar(String rutaObj, String rutaMtl) {
        Map<String, float[]> materiales = parsearMtl(rutaMtl);

        List<float[]> vertices = new ArrayList<>();
        List<float[]> normales = new ArrayList<>();

        List<Grupo> grupos = new ArrayList<>();
        String matActual = null;
        List<float[]> trisActuales = null;

        try (BufferedReader br = abrir(rutaObj)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("v ")) {
                    String[] t = linea.split("\\s+");
                    vertices.add(new float[]{f(t[1]), f(t[2]), f(t[3])});
                } else if (linea.startsWith("vn ")) {
                    String[] t = linea.split("\\s+");
                    normales.add(new float[]{f(t[1]), f(t[2]), f(t[3])});
                } else if (linea.startsWith("usemtl ")) {
                    if (trisActuales != null && !trisActuales.isEmpty()) {
                        grupos.add(construirGrupo(matActual, materiales, trisActuales));
                    }
                    matActual = linea.substring(7).trim();
                    trisActuales = new ArrayList<>();
                } else if (linea.startsWith("f ") && trisActuales != null) {
                    String[] parts = linea.split("\\s+");
                    int[][] refs = new int[parts.length - 1][2]; // [vi, ni]
                    for (int i = 0; i < refs.length; i++) {
                        String[] idx = parts[i + 1].split("/");
                        refs[i][0] = Integer.parseInt(idx[0]) - 1;
                        refs[i][1] = (idx.length > 2 && !idx[2].isEmpty())
                                ? Integer.parseInt(idx[2]) - 1 : -1;
                    }
                    for (int i = 1; i < refs.length - 1; i++) {
                        float[] tri = new float[18];
                        llenarVertice(tri, 0,  refs[0],     vertices, normales);
                        llenarVertice(tri, 6,  refs[i],     vertices, normales);
                        llenarVertice(tri, 12, refs[i + 1], vertices, normales);
                        trisActuales.add(tri);
                    }
                }
            }
        } catch (IOException e) {
            System.err.println("CargadorOBJ: error al leer " + rutaObj + ": " + e.getMessage());
            return Collections.emptyList();
        }

        if (trisActuales != null && !trisActuales.isEmpty()) {
            grupos.add(construirGrupo(matActual, materiales, trisActuales));
        }

        return grupos;
    }

    private static Grupo construirGrupo(String mat, Map<String, float[]> mats, List<float[]> tris) {
        float[] color = mats.getOrDefault(mat, new float[]{1f, 1f, 1f});
        float[] datos = new float[tris.size() * 18];
        int idx = 0;
        for (float[] tri : tris) {
            System.arraycopy(tri, 0, datos, idx, 18);
            idx += 18;
        }
        return new Grupo(color, datos);
    }

    private static Map<String, float[]> parsearMtl(String ruta) {
        Map<String, float[]> mats = new HashMap<>();
        String actual = null;
        try (BufferedReader br = abrir(ruta)) {
            String linea;
            while ((linea = br.readLine()) != null) {
                linea = linea.trim();
                if (linea.startsWith("newmtl ")) {
                    actual = linea.substring(7).trim();
                    mats.put(actual, new float[]{1f, 1f, 1f});
                } else if (linea.startsWith("Kd ") && actual != null) {
                    String[] t = linea.split("\\s+");
                    mats.put(actual, new float[]{f(t[1]), f(t[2]), f(t[3])});
                }
            }
        } catch (IOException e) {
            System.err.println("CargadorOBJ: error al leer " + ruta + ": " + e.getMessage());
        }
        return mats;
    }

    private static BufferedReader abrir(String ruta) throws IOException {
        InputStream is = CargadorOBJ.class.getResourceAsStream(ruta);
        if (is == null) throw new IOException("Recurso no encontrado: " + ruta);
        return new BufferedReader(new InputStreamReader(is));
    }

    private static void llenarVertice(float[] tri, int off, int[] ref,
                                      List<float[]> vertices, List<float[]> normales) {
        float[] v = vertices.get(ref[0]);
        tri[off]     = v[0];
        tri[off + 1] = v[1];
        tri[off + 2] = v[2];
        if (ref[1] >= 0) {
            float[] n = normales.get(ref[1]);
            tri[off + 3] = n[0];
            tri[off + 4] = n[1];
            tri[off + 5] = n[2];
        }
    }

    private static float f(String s) { return Float.parseFloat(s); }
}
