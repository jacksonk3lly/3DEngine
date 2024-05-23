import javax.swing.JFrame;
import javax.swing.JLabel;

import java.awt.BorderLayout;
import java.awt.Color;

public class Main {
    static JFrame frame = new JFrame(
            "Enter to play/pause animation. J and K to Zoom and A, S, D to rotate cube when frozen.");

    public static void main(String[] args) {
        frame.setBackground(Color.black);
        frame.setDefaultCloseOperation(JFrame.EXIT_ON_CLOSE);
        frame.add(new DemoPanel());
        frame.pack();
        frame.setVisible(true);

    }
}