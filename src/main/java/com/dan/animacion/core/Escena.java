package com.dan.animacion.core;

import com.dan.animacion.models.Camara;
import com.dan.animacion.models.CicloDiaNoche;
import com.dan.animacion.models.Terreno;
import com.dan.animacion.utils.Constantes;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class Escena implements GLEventListener {
    private Terreno terreno;
    private CicloDiaNoche ciclo;
    private Camara camara;

    public Escena(Camara camaraCompartida) {
        this.camara = camaraCompartida;
        this.terreno = new Terreno(Constantes.TAMANO_MUNDO, Constantes.TAMANO_CELDA);
        this.ciclo = new CicloDiaNoche();
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glEnable(GL.GL_DEPTH_TEST);
        gl.glEnable(GL2.GL_LIGHTING);
        gl.glShadeModel(GL2.GL_SMOOTH);
        gl.glEnable(GL2.GL_LIGHT0);
        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        ciclo.actualizar();
        ciclo.aplicarEntorno(gl);

        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();
        
        camara.procesarEntrada();
        camara.aplicarTransformaciones(gl);

        terreno.dibujar(gl);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        if (height <= 0) height = 1;
        final float aspecto = (float) width / (float) height;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, aspecto, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) { }
}