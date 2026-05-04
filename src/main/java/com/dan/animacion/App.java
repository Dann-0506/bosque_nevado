package com.dan.animacion;

import com.dan.animacion.models.Terreno;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

public class App extends GLJPanel implements GLEventListener {
    private final float TAMANO_MUNDO = 20.0f;
    private final float TAMANO_CELDA = 0.5f;

    private Terreno terreno;

    public App() {
        this.addGLEventListener(this);
        this.terreno = new Terreno(TAMANO_MUNDO, TAMANO_CELDA);
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
        gl.glClearColor(0.05f, 0.05f, 0.15f, 1.0f); 
    }

    @Override
    public void dispose(GLAutoDrawable drawable) { }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);

        gl.glLoadIdentity();

        float distanciaCamara = -((TAMANO_MUNDO * 2) + 5.0f);

        gl.glTranslatef(0.0f, 2.0f, distanciaCamara);
        gl.glRotatef(20.0f, 1.0f, 0.0f, 0.0f);

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
}