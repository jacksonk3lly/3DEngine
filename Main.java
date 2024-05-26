import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;

public class Main {
    static JFrame frame = new JFrame(
            "Jacksons 3D Engine");

    public static void main(String[] args) {
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new MeshLoadingPanel());
        frame.pack();
        frame.setVisible(true);

    }
}