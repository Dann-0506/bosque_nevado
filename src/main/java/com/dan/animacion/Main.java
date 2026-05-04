package com.dan.animacion;

import com.dan.animacion.core.Panel;
import java.awt.BorderLayout;
import java.awt.Dimension;
import javax.swing.JFrame;
import javax.swing.SwingUtilities;
import com.jogamp.opengl.util.FPSAnimator;

public class Main {
    public static void main(String[] args) {
        SwingUtilities.invokeLater(() -> {
            Panel lienzo = new Panel();
            lienzo.setPreferredSize(new Dimension(800, 600));

            FPSAnimator animator = new FPSAnimator(lienzo, 60, true);

            JFrame frame = new JFrame("Bosque Nevado");
            frame.setLayout(new BorderLayout());
            frame.getContentPane().add(lienzo, BorderLayout.CENTER);
            frame.pack();
            frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);

            frame.setExtendedState(JFrame.MAXIMIZED_BOTH);
            
            frame.setVisible(true);
            animator.start();
        });
    }
}