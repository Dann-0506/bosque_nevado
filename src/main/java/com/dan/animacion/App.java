package com.dan.animacion;

import com.dan.animacion.models.CicloDiaNoche;
import com.dan.animacion.models.Terreno;

import java.awt.BorderLayout;
import java.awt.Dimension;
import java.awt.event.KeyListener;

import javax.swing.JFrame;
import javax.swing.SwingUtilities;

import com.jogamp.opengl.glu.GLU;
import com.jogamp.newt.event.KeyEvent;
import com.jogamp.opengl.GL;
import com.jogamp.opengl.GL2;
import com.jogamp.opengl.GLAutoDrawable;
import com.jogamp.opengl.GLEventListener;
import com.jogamp.opengl.awt.GLJPanel;
import com.jogamp.opengl.util.FPSAnimator;

public class App extends GLJPanel implements GLEventListener, KeyListener {
    private final float TAMANO_MUNDO = 100.0f;
    private final float TAMANO_CELDA = 1.0f;

    private Terreno terreno;
    private CicloDiaNoche cicloDiaNoche;

    private float camX = 0.0f;
    private float camY = -15.0f;
    private float camZ = -60.0f;
    private float rotX = 25.0f;
    private float rotY = 0.0f;

    public App() {
        this.addGLEventListener(this);
        this.addKeyListener(this);

        this.setFocusable(true);
        this.setFocusTraversalKeysEnabled(false);

        this.terreno = new Terreno(TAMANO_MUNDO, TAMANO_CELDA);
        this.cicloDiaNoche = new CicloDiaNoche();
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
        gl.glEnable(GL2.GL_FLAT);
        gl.glEnable(GL2.GL_LIGHT0);

        gl.glEnable(GL2.GL_COLOR_MATERIAL);
        gl.glColorMaterial(GL.GL_FRONT_AND_BACK, GL2.GL_AMBIENT_AND_DIFFUSE);
    }

    @Override
    public void dispose(GLAutoDrawable drawable) { }

    @Override
    public void display(GLAutoDrawable drawable) {
        GL2 gl = drawable.getGL().getGL2();
        
        cicloDiaNoche.actualizar();
        cicloDiaNoche.aplicarEntorno(gl);
        
        gl.glClear(GL.GL_COLOR_BUFFER_BIT | GL.GL_DEPTH_BUFFER_BIT);
        gl.glLoadIdentity();

        gl.glTranslatef(camX, camY, camZ);
        gl.glRotatef(rotX, 1.0f, 0.0f, 0.0f);
        gl.glRotatef(rotY, 0.0f, 1.0f, 0.0f);
        
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

    @Override
    public void keyTyped(java.awt.event.KeyEvent e) { }

    @Override
    public void keyPressed(java.awt.event.KeyEvent e) { }

    @Override
    public void keyReleased(java.awt.event.KeyEvent e) {
        float velocidad = 2.0f;
        float velocidadRotacion = 3.0f;

        switch (e.getKeyCode()) {
            // --- MOVIMIENTO (WASD) ---
            case KeyEvent.VK_W: camZ += velocidad; break; // Adelante
            case KeyEvent.VK_S: camZ -= velocidad; break; // Atrás
            case KeyEvent.VK_A: camX += velocidad; break; // Izquierda
            case KeyEvent.VK_D: camX -= velocidad; break; // Derecha
            
            // --- ALTURA (Q y E) ---
            case KeyEvent.VK_Q: camY -= velocidad; break; // Subir
            case KeyEvent.VK_E: camY += velocidad; break; // Bajar

            // --- ROTACIÓN DE CÁMARA (I, J, K, L) ---
            case KeyEvent.VK_I: rotX -= velocidadRotacion; break; // Mirar arriba
            case KeyEvent.VK_K: rotX += velocidadRotacion; break; // Mirar abajo
            case KeyEvent.VK_J: rotY -= velocidadRotacion; break; // Girar izquierda
            case KeyEvent.VK_L: rotY += velocidadRotacion; break; // Girar derecha
        }
    }
}