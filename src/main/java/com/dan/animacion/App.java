package com.dan.animacion;

import static com.jogamp.opengl.GL2ES3.GL_QUADS;

import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

public class App extends GLJPanel implements GLEventListener {

    public App() {
        this.addGLEventListener(this);
    }

    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            App canvas = new App();
            canvas.setPreferredSize(new Dimension(800, 600));

            FPSAnimator animator = new FPSAnimator(canvas, 60, true);

            JFrame frame = new JFrame("Proyecto graficación");
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

        gl.glColor3f(1.0f, 0.5f, 0f);
        gl.glBegin(GL_QUADS);
        gl.glVertex3f(-0.5f, -0.5f, 0.0f);
        gl.glVertex3f(0.5f, -0.5f, 0.0f);
        gl.glVertex3f(0.5f, 0.5f, 0.0f);
        gl.glVertex3f(-0.5f, 0.5f, 0.0f);
        gl.glEnd();
    }

    @Override
    public void reshape(GLAutoDrawable drawable, int x, int y, int width, int height) { }
}