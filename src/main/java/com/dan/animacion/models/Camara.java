package com.dan.animacion.models;

import com.dan.animacion.input.EstadoEntrada;
import com.dan.animacion.utils.Constantes;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.GL2;

public class Camara {
    private float x, y, z;
    private float rotX, rotY;
    private boolean modoSuelo = true;
    private boolean tabPresionado = false;

    public Camara(float x, float y, float z) {
        this.x = x;
        this.y = y;
        this.z = z;
    }

    public void procesarEntrada(EstadoEntrada estado) {
        rotY += estado.mouseDeltaX * Constantes.SENSIBILIDAD_RATON;
        rotX += estado.mouseDeltaY * Constantes.SENSIBILIDAD_RATON;
        rotX = Math.max(-90.0f, Math.min(90.0f, rotX));
        estado.mouseDeltaX = 0;
        estado.mouseDeltaY = 0;

        boolean tabAhora = estado.teclas[KeyEvent.VK_TAB];
        if (tabAhora && !tabPresionado) modoSuelo = !modoSuelo;
        tabPresionado = tabAhora;

        float yawRad = (float) Math.toRadians(rotY);
        float sin = (float) Math.sin(yawRad);
        float cos = (float) Math.cos(yawRad);

        if (estado.teclas[KeyEvent.VK_W]) { x -= sin * Constantes.VELOCIDAD_CAMARA; z += cos * Constantes.VELOCIDAD_CAMARA; }
        if (estado.teclas[KeyEvent.VK_S]) { x += sin * Constantes.VELOCIDAD_CAMARA; z -= cos * Constantes.VELOCIDAD_CAMARA; }
        if (estado.teclas[KeyEvent.VK_A]) { x += cos * Constantes.VELOCIDAD_CAMARA; z += sin * Constantes.VELOCIDAD_CAMARA; }
        if (estado.teclas[KeyEvent.VK_D]) { x -= cos * Constantes.VELOCIDAD_CAMARA; z -= sin * Constantes.VELOCIDAD_CAMARA; }
        if (!modoSuelo) {
            if (estado.teclas[KeyEvent.VK_Q]) y -= Constantes.VELOCIDAD_CAMARA;
            if (estado.teclas[KeyEvent.VK_E]) y += Constantes.VELOCIDAD_CAMARA;
        }
    }

    public void ajustarAlSuelo(float alturaTerreno) {
        y = -(alturaTerreno + Constantes.ALTURA_OJO);
    }

    public boolean isModoSuelo() {return modoSuelo;}

    public float getMundoX() {return -x;}
    public float getMundoY() {return -y;}
    public float getMundoZ() {return -z;}

    public void aplicarTransformaciones(GL2 gl) {
        gl.glRotated(rotX, 1.0, 0.0, 0.0);
        gl.glRotated(rotY, 0.0, 1.0, 0.0);
        gl.glTranslatef(x, y, z);
    }
}
