package com.dan.animacion.models;

public class Edificio {
    public static final int CABANA = 0;
    public static final int POZO   = 1;

    public final float x, y, z, escala, rotacionY;
    public final int tipo;

    public Edificio(float x, float y, float z, float escala, float rotacionY, int tipo) {
        this.x = x;
        this.y = y;
        this.z = z;
        this.escala = escala;
        this.rotacionY = rotacionY;
        this.tipo = tipo;
    }
}
