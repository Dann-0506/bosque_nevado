package com.dan.animacion.core;

import com.dan.animacion.input.EstadoInput;
import com.dan.animacion.models.Camara;
import com.dan.animacion.models.CicloDiaNoche;
import com.dan.animacion.models.SistemaNieve;
import com.dan.animacion.models.Terreno;
import com.dan.animacion.render.RendererAmbiente;
import com.dan.animacion.render.RendererNieve;
import com.dan.animacion.render.RendererTerreno;
import com.dan.animacion.utils.Constantes;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.glu.GLU;

public class Escena implements GLEventListener {
    private final Camara camara;
    private final EstadoInput estadoEntrada;
    private final Terreno terreno;
    private final CicloDiaNoche ciclo;
    private final SistemaNieve nieve;
    private final RendererAmbiente rendererEntorno;
    private final RendererTerreno rendererTerreno;
    private final RendererNieve rendererNieve;

    public Escena(Camara camara, EstadoInput estadoEntrada) {
        this.camara = camara;
        this.estadoEntrada = estadoEntrada;
        this.terreno = new Terreno(Constantes.TAMANO_MUNDO, Constantes.TAMANO_CELDA);
        this.ciclo = new CicloDiaNoche();
        this.nieve = new SistemaNieve(camara.getMundoX(), camara.getMundoY(), camara.getMundoZ());
        this.rendererEntorno = new RendererAmbiente();
        this.rendererTerreno = new RendererTerreno();
        this.rendererNieve = new RendererNieve();
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

        gl.glEnable(GL2.GL_FOG);
        gl.glFogi(GL2.GL_FOG_MODE, GL2.GL_EXP2);
    }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();

        if (estadoEntrada.salirSolicitado) {
            drawable.destroy();
            return;
        }

        ciclo.actualizar();
        camara.procesarEntrada(estadoEntrada);
        if (camara.isModoSuelo()) {
            float h = terreno.calcularAltura(camara.getMundoX(), camara.getMundoZ());
            camara.ajustarAlSuelo(h);
        }
        nieve.actualizar(camara.getMundoX(), camara.getMundoY(), camara.getMundoZ());

        rendererEntorno.prepararCielo(gl, ciclo);
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        camara.aplicarTransformaciones(gl);

        rendererEntorno.aplicarIluminacionYAstros(gl, ciclo,
                camara.getMundoX(), camara.getMundoY(), camara.getMundoZ());

        rendererTerreno.dibujar(gl, terreno);
        rendererNieve.dibujar(gl, nieve);
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) {
        GL2 gl = drawable.getGL().getGL2();
        GLU glu = new GLU();
        if (height <= 0) height = 1;
        gl.glMatrixMode(GL2.GL_PROJECTION);
        gl.glLoadIdentity();
        glu.gluPerspective(45.0f, (float) width / height, 1.0, 1000.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override
    public void dispose(GLAutoDrawable drawable) { }
}
