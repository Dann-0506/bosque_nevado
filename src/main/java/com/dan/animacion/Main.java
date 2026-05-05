package com.dan.animacion;

import com.dan.animacion.core.Escena;
import com.dan.animacion.input.EstadoEntrada;
import com.dan.animacion.input.Panel;
import com.dan.animacion.models.Camara;
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

        EstadoEntrada estadoEntrada = new EstadoEntrada();
        Camara camara = new Camara(-18.9f, -10.0f, -31.2f);
        Escena escena = new Escena(camara, estadoEntrada);
        Panel controlador = new Panel(estadoEntrada, window);

        window.addGLEventListener(escena);
        window.addKeyListener(controlador);
        window.addMouseListener(controlador);

        window.setVisible(true);
        window.setPointerVisible(false);
        window.confinePointer(true);

        FPSAnimator animator = new FPSAnimator(window, 30, true);
        animator.start();
    }
}
