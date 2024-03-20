package paintproject;

import java.awt.BorderLayout;
import java.awt.Color;
import java.awt.Dimension;
import javax.swing.JFrame;

public class PaintProject {

    public static void main(String[] args) {
        JFrame frm = new JFrame();
        frm.setTitle("Painting Brush Application");
        
         Paintpanel pn = new Paintpanel();
         pn.setPreferredSize(new Dimension(50, 50));
        // Set the panel background color to white
        pn.setBackground(Color.WHITE);
        frm.add(pn, BorderLayout.CENTER);

        frm.getContentPane();
        frm.setSize(900, 800);
        frm.setResizable(false);
        frm.setLocationRelativeTo(null); // Centers the frame on the screen
        frm.setVisible(true);
    }
}
