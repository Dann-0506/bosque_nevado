package com.dan.animacion;

import com.dan.animacion.core.Escena;
import com.dan.animacion.core.Panel;
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
        window.setTitle("Bosque Nevado - Motor NEWT");
        window.setSize(800, 600);
        window.setFullscreen(true);

        Camara camara = new Camara(0.0f, -15.0f, -60.0f);
        Escena escena = new Escena(camara);
        Panel controlador = new Panel(camara, window);

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