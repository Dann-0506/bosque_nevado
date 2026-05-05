package com.dan.animacion.render;

import com.dan.animacion.models.CicloDiaNoche;
import com.jogamp.opengl.GL2;

public class RendererEntorno {

    public void aplicar(GL2 gl, CicloDiaNoche ciclo) {
        float[] cielo    = ciclo.getColorCielo();
        float[] sol      = ciclo.getColorSol();
        float[] ambiente = ciclo.getColorAmbiente();

        gl.glClearColor(cielo[0], cielo[1], cielo[2], 1.0f);

        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_POSITION,
            ciclo.getPosicionSol(), 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_DIFFUSE,
            new float[]{ sol[0], sol[1], sol[2], 1.0f }, 0);
        gl.glLightfv(GL2.GL_LIGHT0, GL2.GL_AMBIENT,
            new float[]{ ambiente[0], ambiente[1], ambiente[2], 1.0f }, 0);
    }
}
