package com.dan.animacion;

import com.dan.animacion.models.Camara;
import com.dan.animacion.models.CicloDiaNoche;
import com.dan.animacion.models.Terreno;
import com.dan.animacion.utils.Constantes;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

public class App extends GLJPanel implements GLEventListener, KeyListener {
    private Terreno terreno;
    private CicloDiaNoche ciclo;
    private Camara camara;

    public App() {
        this.addGLEventListener(this);
        this.addKeyListener(this);
        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

        this.terreno = new Terreno(Constantes.TAMANO_MUNDO, Constantes.TAMANO_CELDA);
        this.ciclo = new CicloDiaNoche();

        this.camara = new Camara(0.0f, -15.0f, -60.0f);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App canvas = new App();
            canvas.setPreferredSize(new Dimension(800, 600));
            FPSAnimator animator = new FPSAnimator(canvas, 60, true);
            JFrame frame = new JFrame("Bosque Nevado");
            frame.setLayout(new BorderLayout());
            frame.getContentPane().add(canvas, BorderLayout.CENTER);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
            frame.setVisible(true);
            animator.start();
        });
    }

    @Override
    public void init(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        
        gl.glEnable(GL.GL_DEPTH_TEST);

        gl.glEnable(GL2.GL_LIGHTING);
        gl.glShadeModel(GL2.GL_FLAT);

        gl.glEnable(GL2.GL_LIGHT0);

        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) { }

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
        glu.gluPerspective(45.0f, aspecto, 1.0, 100.0);
        gl.glMatrixMode(GL2.GL_MODELVIEW);
        gl.glLoadIdentity();
    }

    @Override public void keyTyped(java.awt.event.KeyEvent e) { }
    
    @Override 
    public void keyPressed(java.awt.event.KeyEvent e) {camara.registrarTeclaPresionada(e.getKeyCode());}

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {camara.registrarTeclaSoltada(e.getKeyCode());}
}