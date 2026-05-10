package com.dan.animacion;

import com.dan.animacion.core.Escena;
import com.dan.animacion.input.EstadoInput;
import com.dan.animacion.input.VentanaDisplay;
import com.dan.animacion.models.Camara;
import com.dan.animacion.utils.Constantes;

import com.jogamp.newt.opengl.GLWindow;
import com.jogamp.opengl.GLCapabilities;
import com.jogamp.opengl.GLProfile;
import com.jogamp.opengl.util.FPSAnimator;

public class Main {
    public static void main(String[] args) {
        GLProfile profile = GLProfile.get(GLProfile.GL2);
        GLCapabilities caps = new GLCapabilities(profile);

        GLWindow window = GLWindow.create(caps);
        window.setTitle("Bosque Nevado");
        window.setSize(800, 600);
        window.setFullscreen(true);

        FPSAnimator animator = new FPSAnimator(window, Constantes.FPS_OBJETIVO, true);

        EstadoInput estadoEntrada = new EstadoInput();
        Camara camara = new Camara(-18.9f, -10.0f, -31.2f);
        Escena escena = new Escena(camara, estadoEntrada);
        VentanaDisplay controlador = new VentanaDisplay(estadoEntrada, window, animator);

        window.addGLEventListener(escena);
        window.addKeyListener(controlador);
        window.addMouseListener(controlador);

        window.setVisible(true);
        window.setPointerVisible(false);
        window.confinePointer(true);

        animator.start();
    }
}
